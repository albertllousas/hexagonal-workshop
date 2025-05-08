# Bonus: Possible approach/solution

Following the same principles we have applied so far, we can implement the new feature in a hexagonal way.

How?

1. Create a new interface `DomainEventPublisher` in `src/main/kotlin/hexagonal/domain` that will be responsible for publishing the event.
```kotlin
interface DomainEventPublisher {
    fun publish(event: DomainEvent)
}
```
2. Create a new class `KafkaDomainEventPublisher` in `src/main/kotlin/hexagonal/infra/outbound` that will implement the `DomainEventPublisher` interface and will be responsible for publishing the event to Kafka.
```kotlin
class KafkaDomainEventPublisher : DomainEventPublisher {
    override fun publish(event: DomainEvent) {
        // Simulate publishing the event to Kafka
        println("Publishing event to Kafka: $event")
    }
}
```
3. Create a new class `AccountCreatedEvent` in the same file as our domain class `src/main/kotlin/hexagonal/domain/Account.kt`.
```kotlin
sealed interface DomainEvent

data class AccountCreatedEvent(val id: UUID): DomainEvent // thin event, excluding the rest if the fields
```
4. Open `AccountService` and add a new constructor dependency of type `DomainEventPublisher`.
5. Publish the event after saving in `AccountService`. 
```kotlin
domainEventPublisher.publish(AccountCreatedEvent(account.id))
```

Note: it's often used with wvent Listeners/Handlers that react to events and trigger side effects (e.g., sending emails, metrics, logging) 
and usually with the outbox Pattern (if events need to be reliably propagated to other systems).