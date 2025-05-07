# Basic hexagonal architecture

Idea, we are going perform the same changes, but now we will prepare our code beforehand and see if our domain.

But first, let's prepare our codebase.

1. Rename, this time using the IDEA refactoring capabilities, the `copyofnonhexagonal` package to `hexagonal`.
2. Create the basic package structure of the hexagonal architecture project:
```text
 hexagonal
    ├── application // Usecases or services, orchestrators
    ├── domain // Domain model, ports
    └── infra // Adapters
        ├── inbound // Adapters for inbound communications, e.g., controllers, http clients
        └── outbound // Adapters for outbound communications, e.g., repositories, message brokers
```
3. Move the `AccountController` package to `src/main/kotlin/hexagonal/infra/inbound`
4. Move the `AccountDao` package to `src/main/kotlin/hexagonal/infra/outbound`
6. Move the `UserHttpClient` package to `src/main/kotlin/hexagonal/infra/outbound`
7. Move the `AccountService` package to `src/main/kotlin/hexagonal/application`
8. Remove empty packages 


Cool, now we have a basic structure for our hexagonal architecture.

Let's move to the [first change](/workshop_steps/hexagonal/2_internal_api_contract_change.md), the internal API contract change.