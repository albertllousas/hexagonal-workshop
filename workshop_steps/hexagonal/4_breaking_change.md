# Breaking change

AS we already saw in the nonhexagonal part, the external user system has been split.
Membership-related fields are deprecated and now handled by a new external membership system, also http. 

```
GET {USERS_HOST}/v2/users/{id} -> { "id": "string", "name": "string", "email": "string" }
GET {MEMBERSHIP_HOST}/v2/{user_id}/memberships -> { "id": "string", "name": BASIC|PREMIUM, "features": ["string"] }
```

## Preparing the code with hexagonal

Where do we start?

Follow the same thing as the previous step:

1. Create an interface `UserFetcher` (outbound port) and the `User` in the same file `src/main/kotlin/hexagonal/domain/UserFetcher.kt` that will be responsible for fetching the user data.
```kotlin
interface UserFetcher {
    fun fetchUser(id: UUID): User
}

data class User(val id: UUID, val name: String, val email: String, val membership: Membership)

enum class Membership { BASIC, PREMIUM }
```
2. Open `AccountService` and replace the dependency of `UserHttpClient` by the new interface `UserFetcher`. Replace the `UserDto` references by the `User` domain class.
3. Fix compilation issues and remove unused packages from infra.
4. Open `UserHttpClient` and force it to implement the `UserFetcher` interface.
5. Fix the code
6Optional: Now that we are free of infrastructure dtos in the Service, we can safely move the logic (buildAccount) to the domain class `Account`. 
Why? we don't want anemic domains, we want rich ones if we want to follow OOP.
```kotlin
data class Account(...) {
    
    companion object {
        fun build(name: String, user: User) =
            Account(
                id = UUID.randomUUID(),
                userId = user.id,
                accountName = name,
                email = user.email,
                billingType = when (user.membership) {
                    BASIC -> MONTHLY
                    PREMIUM -> YEARLY
                }
            )
    }
}
```

## Tech change

The code is hexa-ready, let's do the tech change:

1. Create a new class `MembershipHttpClient` that will be responsible for fetching the membership data in `src/hexagonal/infra/outbound`.
```kotlin
class MembershipHttpClient() {
    override fun fetchMembership(userId: UUID): MembershipDto =
        // Simulate fetching membership data from an external service
         MembershipDto(id = UUID.randomUUID(), name = MembershipName.BASIC, features = listOf("feature1", "feature2"))
}

data class MembershipDto(val id: UUID, val name: MembershipName, val features: List<String>)

enum class MembershipName { BASIC, PREMIUM }
```
2. Unimplement the `UserFetcher` interface in the `UserHttpClient` class, returning back the dto again (UserDto), now without `membership` field.
```kotlin
class UserHttpClient {
    fun fetchUser(userId: UUID): UserDto { ... }
}
```
3. Create a new class `src/hexagonal/infra/outbound/CompositeUserFetcherAdapter` that will implement `UserFetcher` interface and will be responsible for call the user client, 
call the membership client and merge it in our domain class.

```kotlin
class CompositeUserFetcherAdapter(
    private val userHttpClient: UserHttpClient,
    private val membershipHttpClient: MembershipHttpClient
) : UserFetcher {
    override fun fetchUser(id: UUID): User {
        val user = userHttpClient.fetchUser(id)
        val membership = membershipHttpClient.fetchMembership(id)
        return User(id, user.name, user.email, Membership.valueOf(membership.name))
    }
}
```
4. We have done a lot of changes, right? But did we change our domain? No, we just changed the implementation of the `UserFetcher` interface.

## What did we do here?
 
As the previous example, we have applied dependency inversion, proving that **even breaking changes can be transparent to our domain** unlike the non-hexa code by which the ripple effect broke our code everywhere.

```
                BUSINESS CORE                                 INFRASTRUCTURE
+----------------+        +-------------------+           +----------------------+
| AccountService | -----> | UserFetcher       | <-------- | CompositeUserFetcher |
| (Usecase)      |  uses  | (OutboundPort)    |  impl by  | (Adapter)            |
+----------------+        +-------------------+           +----------------------+
                                                             |      uses      |
                                                             v                v
                                                      +--------------+ +--------------------+
                                                      |UserHttpClient| |MembershipHttpClient|
                                                      +--------------+ +--------------------+

```

BONUS: 