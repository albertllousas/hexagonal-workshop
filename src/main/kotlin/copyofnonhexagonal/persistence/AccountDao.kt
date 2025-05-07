package copyofnonhexagonal.persistence

import java.util.UUID

// fake class to simulate a database access object
// using an ORM or data access framework, could be a JPA, JDBI, JOOQ, Spring data ...
class AccountDao {
    fun insert(newAccount: SomeOrmAccountDto) {
       //database insertion logic
    }
}

// Fake database entity, tightly coupled to the ORM or data access framework
class SomeOrmAccountDto(
    val id: UUID,
    val userId: UUID,
    val accountName: String,
    val email: String,
    val billingType: OrmBillingType
)

enum class OrmBillingType { MONTHLY, YEARLY }