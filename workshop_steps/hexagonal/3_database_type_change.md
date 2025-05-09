# The second non-domain-related change, a database change

Here same change that we did without hexa, the billingType field previously modeled as an `enum` is now stored as an `Int`, 
referencing a shared lookup table (e.g., billing_types).

## Preparing the code with hexagonal

Let's shell our code with hexagonal:

1. Hexagonal is about protecting the domain, but we don't have any domain yet, just dtos. Hence, create a domain class
in `src/main/kotlin/hexagonal/domain/Account.kt`. It has no logic, but no worries, we will add it later.
```kotlin
data class Account(val id: UUID, val userId: UUID, val name: String, val email: String, val billingType: BillingType)

enum class BillingType { MONTHLY, YEARLY }
```
2. Now, create an interface `src/main/kotlin/hexagonal/domain/AccountRepository.kt` that uses our newly created domain class.
```kotlin
interface AccountRepository {
    fun save(account: Account): Account
}
```
3. Once we have this abstraction, open `src/main/kotlin/hexagonal/application/AccountService.kt` and replace the dependency of
AccountDao by the new interface `AccountRepository` we just created. Please, don't use the account dtos in the service, use the domain class `Account` and `BillingType` instead.
4. We are almost there, now someone needs to implement this interface, right? open `src/main/kotlin/hexagonal/infra/outbound/AccountDao.kt`
and implement the interface and remove the old method.
5. Optional: Change your `AccountDao` to `AccountRepositoryAdapter` to be more clear with hexagonal concepts.

## Tech change

1. Open our fake data access object in: `src/main/kotlin/hexagonal/infra/outbound/AccountDao.kt` and change the `billingType` field in the `SomeOrmAccountDto` class to `Int`, 0 is MONTHLY, 1 is YEARLY now.
2. Compile the project
3. Did our business code change by this tech change?

## What did we do here?

We just did the most important thing in hexagonal architecture, apply [dependency inversion principle](https://blog.cleancoder.com/uncle-bob/2016/01/04/ALittleArchitecture.html), now we have:

```
                BUSINESS CORE                             INFRASTRUCTURE 
+----------------+        +------------------ +           +------------+
| AccountService | -----> | AccountRepository | <-------- | AccountDao |
| (Usecase)      |  uses  | (OutboundPort)    |  impl by  | (Adapter)  |
+----------------+        +-------------------+           +------------+
```

Now our infrastructure code for the database depends on our domain, and not the other way around, this mitigates most of the
changes in our db, like changes in the schema or ORMs. 

**Our business code is free of database concerns 😱.**

Let's keep changing stuff, [third change](/workshop_steps/hexagonal/4_breaking_change.md). 

