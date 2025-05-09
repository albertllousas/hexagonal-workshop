package hexagonal.domain

import java.util.UUID

data class Account(
    val id: UUID,
    val userId: UUID,
    val name: String,
    val email: String,
    val billingType: BillingType)

enum class BillingType { MONTHLY, YEARLY }
