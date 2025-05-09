package hexagonal.infra.outbound

import hexagonal.domain.Account
import hexagonal.domain.AccountRepository
import java.util.UUID

// fake class to simulate a database access object
// using an ORM or data access framework, could be a JPA, JDBI, JOOQ, Spring data ...
class AccountRepositoryAdapter(): AccountRepository {
    override fun save(account: Account): Account {
        TODO("Not yet implemented")
    }

}

// Fake database entity, tightly coupled to the ORM or data access framework
class SomeOrmAccountDto(
    val id: UUID,
    val userId: UUID,
    val accountName: String,
    val email: String,
    val billingType: Int
)

enum class OrmBillingType { MONTHLY, YEARLY }
