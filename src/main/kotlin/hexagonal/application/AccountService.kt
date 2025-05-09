package hexagonal.application

import hexagonal.domain.Account
import hexagonal.domain.AccountRepository
import hexagonal.domain.BillingType
import hexagonal.domain.Membership
import hexagonal.domain.User
import hexagonal.domain.UserFetcher
import java.util.UUID

class AccountService(
    private val userFetcher: UserFetcher,
    private val repository: AccountRepository,
) {

    fun createAccount(userId: UUID, accountName: String): UUID {
        val user = userFetcher.fetchUser(userId)
        val newAccount = buildAccount(accountName, user)
        repository.save(newAccount)
        return newAccount.id
    }


    private fun buildAccount(name: String, user: User) =
        Account(
            id = UUID.randomUUID(),
            userId = user.id,
            name = name,
            email = user.email,
            billingType = when (user.membership) {
                Membership.BASIC -> BillingType.MONTHLY
                Membership.PREMIUM -> BillingType.YEARLY
            }
        )
}
