# The first tech change, an internal API contract change

Let's introduce the first change to the codebase, the internal endpoint for the internal account API is upgraded to version 2. 
A field in the request changes from `accountName` to `name`.

## Preparing the code with hexagonal

But before that, let's prepare our codebase, how we can shell our service from the dto leaking that we saw before?.

1. Open `src/main/kotlin/hexagonal/application/AccountService.kt`
2. Change the input of the signature of the `createAccount` that accepts an http request to `CreateAccountHttpRequest` to just primitives `fun createAccount(userId: UUID, name: String): UUID`
3. Open `src/main/kotlin/hexagonal/infra/inbound/AccountController.kt`
4. Change the `createAccount` and pass the correct parameters to the service.
5. Compile the project

## Tech change

1. Open `src/main/kotlin/hexagonal/infra/inbound/AccountController.kt`
2. Change the `accountName` field in the `CreateAccountHttpRequest` class to `name` (Remember, without using rename from IDE)
3. Compile the project 
4. No changes right? 
5. Optional: Rename `AccountController` to `AccountHttpAdapter` to be more clear with hexagonal concepts.

## What did we do here?

Hexagonal is also called ports and adapters, so here we have our adapter (the controller) that adapts the external world (http) to our internal world, 
calling the inbound port (Service), protecting it from the external incoming http concerns.

**Why do we need all these new packages for this?**

That was almost a common sense refactor, so move to the [next change](/workshop_steps/hexagonal/3_database_type_change.md). 

NOTE: When our signature grows, we can use a data class defined in the same file, to encapsulate the parameters.
