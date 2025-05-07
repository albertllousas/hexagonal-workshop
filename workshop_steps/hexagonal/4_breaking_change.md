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
2. Replace the dependency of `UserHttpClient` in the `AccountService` with the new interface `UserFetcher`. Replace aswell the `UserDto` in the service by the User domain class that we just created.
3. Fix compilation issues and remove unused packages from infra.
4. Implement the `UserFetcher` interface in the `UserHttpClient` class.
5. Optional: Now that we are free of infrastructure dtos in the Service, we can safely move the logic (buildAccount) to the domain class `Account`. 
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

Code ready for the change, but how we can do it without breaking our domain?:

1. Create a new class `MembershipHttpClient` that will be responsible for fetching the membership data in `src/hexagonal/infra/outbound`.
```kotlin
class MembershipHttpClient() {
    override fun fetchMembership(userId: UUID): MembershipDto =
         MembershipDto(id = UUID.randomUUID(), name = MembershipName.BASIC, features = listOf("feature1", "feature2"))
}

data class MembershipDto(val id: UUID, val name: MembershipName, val features: List<String>)

enum class MembershipName { BASIC, PREMIUM }
```
2. Unimplement the `UserFetcher` interface in the `UserHttpClient` class, returning back the dto again, now without `membership` field.
```kotlin
TODO
```
3. Create a new class `CompositeUserFetcherAdapter` that will implement `UserFetcher` interface and will be responsible for call the user client, 
cal the membership client and merge it in our domain class in `src/hexagonal/infra/outbound`.

```kotlin
class CompositeUserFetcherAdapter(
    private val userHttpClient: UserHttpClient,
    private val membershipHttpClient: MembershipHttpClient
) : UserFetcher {
    override fun fetchUser(id: UUID): User {
        val user = userHttpClient.fetchUser(id)
        val membership = membershipHttpClient.fetchMembership(user.id)
        return User(user.id, user.name, user.email, Membership.valueOf(membership.name))
    }
}
```
4. We have done a lot of changes, right? But did we change our domain? No, we just changed the implementation of the `UserFetcher` interface.

## What did we do here?

As the previous example, we have applied dependency inversion, proving that even breaking changes can be transparent to our domain.

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

Now, let's see how we can mitigate this pain with [hexagonal architecture](/workshop_steps/hexagonal/1_basic_structure.md).