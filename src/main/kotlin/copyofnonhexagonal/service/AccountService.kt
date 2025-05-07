package copyofnonhexagonal.service

import copyofnonhexagonal.clients.MembershipLevel.BASIC
import copyofnonhexagonal.clients.MembershipLevel.PREMIUM
import copyofnonhexagonal.clients.UserDTO
import copyofnonhexagonal.clients.UserHttpClient
import copyofnonhexagonal.controller.CreateAccountHttpRequest
import copyofnonhexagonal.persistence.AccountDao
import copyofnonhexagonal.persistence.OrmBillingType.MONTHLY
import copyofnonhexagonal.persistence.OrmBillingType.YEARLY
import copyofnonhexagonal.persistence.SomeOrmAccountDto
import java.util.UUID

class AccountService(
    private val userHttpClient: UserHttpClient,
    private val accountDao: AccountDao,
) {

    fun createAccount(request: CreateAccountHttpRequest): UUID {
        val user = userHttpClient.fetchUser(request.userId)
        val newAccount = buildAccountDto(request.accountName, user)
        accountDao.insert(newAccount)
        return newAccount.id
    }

    private fun buildAccountDto(name: String, user: UserDTO) =
        SomeOrmAccountDto(
            id = UUID.randomUUID(),
            userId = user.id,
            accountName = name,
            email = user.email,
            billingType = when (user.membership) {
                BASIC -> MONTHLY
                PREMIUM -> YEARLY
            }
        )
}
