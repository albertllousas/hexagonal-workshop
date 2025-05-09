package hexagonal.application

import hexagonal.domain.Account
import hexagonal.domain.AccountRepository
import hexagonal.domain.BillingType
import hexagonal.infra.outbound.MembershipLevel
import hexagonal.infra.outbound.UserDto
import hexagonal.infra.outbound.UserHttpClient
import java.util.UUID

class AccountService(
    private val userHttpClient: UserHttpClient,
    private val repository: AccountRepository,
) {

    fun createAccount(userId: UUID, accountName: String): UUID {
        val user = userHttpClient.fetchUser(userId)
        val newAccount = buildAccount(accountName, user)
        repository.save(newAccount)
        return newAccount.id
    }


    private fun buildAccount(name: String, user: UserDto) =
        Account(
            id = UUID.randomUUID(),
            userId = user.id,
            name = name,
            email = user.email,
            billingType = when (user.membership) {
                MembershipLevel.BASIC -> BillingType.MONTHLY
                MembershipLevel.PREMIUM -> BillingType.YEARLY
            }
        )
}
