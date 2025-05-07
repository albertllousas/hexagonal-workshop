# The second non-domain-related change, a database change

The billingType field previously modeled as an `enum` is now stored as an `Int`, referencing a shared lookup table (e.g., billing_types). 
Our DTO is tightly coupled to the underlying ORM, let's see how this change hits our current code.

1. Open our fake data access object in: `src/main/kotlin/nonhexagonal/persistence/AccountDao.kt`
2. Change the `billingType` field in the `SomeOrmAccountDto` class to `Int`, 0 is MONTHLY, 1 is YEARLY now.
3. Compile the project
4. Check how many classes are impacted by this change, remember to multiply by 2 (tests, even they are not there, would reflect the same changes)
5. Fix the code, and make sure the project compiles

Things are getting interesting, right? let's move to the [third change](/workshop_steps/feel_the_pain/3_breaking_change.md). 



