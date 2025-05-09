# Breaking change

The external user system has been split. Membership-related fields are deprecated and now handled by a new external membership system, also http. 

```
GET {USERS_HOST}/v2/users/{id} -> { "id": "string", "name": "string", "email": "string" }
GET {MEMBERSHIP_HOST}/v1/{user_id}/memberships -> { "id": "string", "name": BASIC|PREMIUM, "features": ["string"] }
```

Where do we start? 
1. Create a new class `MembershipHttpClient` that will be responsible for fetching the membership data in `src/main/kotlin/nonhexagonal/clients`.
```kotlin
class MembershipHttpClient() {
    override fun fetchMembership(userId: UUID): MembershipDto =
        // Simulate fetching membership data from an external service
        MembershipDto(id = UUID.randomUUID(), name = MembershipName.BASIC, features = listOf("feature1", "feature2"))
}
data class MembershipDto(val id: UUID, val name: MembershipName, val features: List<String>)

enum class MembershipName { BASIC, PREMIUM }
```
2. Open `UserHttpClient` file and remove field `membership` from `UserDto`.
3. Open `AccountService` file and add the `MembershipHttpClient` as a new constructor dependency.
4. Try to fix the compilation errors.
5. How many changes? painful right?

As we can see, **tech usually changes more often than our business part, and the ripple effect of these changes can be really
intrusive.**

Now, let's see how we can mitigate this pain with [hexagonal architecture](/workshop_steps/hexagonal/1_basic_structure.md).