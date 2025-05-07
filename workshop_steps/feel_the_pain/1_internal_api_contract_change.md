# The first tech change, an internal API contract change

Let's introduce the first change to the codebase, the internal endpoint for the internal account API is upgraded to version 2. 
A field in the request changes from `accountName` to `name`.

1. Open `src/main/kotlin/nonhexagonal/controller/AccountController.kt`
2. Change the `accountName` field in the `CreateAccountHttpRequest` class to `name` (Remember, without using rename from IDE)
3. Compile the project
4. Check how many classes are impacted by this change, keep in mind that even though we don’t have tests here, they would reflect the same changes—so the actual number would effectively double.
5. Fix the code, and make sure the project compiles

Well, that was easy, let's move on to the [next change](/workshop_steps/feel_the_pain/2_database_type_change.md). 




