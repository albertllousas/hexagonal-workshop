package nonhexagonal.service

import nonhexagonal.clients.MembershipLevel.BASIC
import nonhexagonal.clients.MembershipLevel.PREMIUM
import nonhexagonal.clients.UserDto
import nonhexagonal.clients.UserHttpClient
import nonhexagonal.controller.CreateAccountHttpRequest
import nonhexagonal.persistence.AccountDao
import nonhexagonal.persistence.OrmBillingType.MONTHLY
import nonhexagonal.persistence.OrmBillingType.YEARLY
import nonhexagonal.persistence.SomeOrmAccountDto
import java.util.UUID

class AccountService(
    private val userHttpClient: UserHttpClient,
    private val accountDao: AccountDao,
) {

    fun createAccount(request: CreateAccountHttpRequest): UUID {
        val user = userHttpClient.fetchUser(request.userId)
        val newAccount = buildAccountDto(request.name, user)
        accountDao.insert(newAccount)
        return newAccount.id
    }

    private fun buildAccountDto(name: String, user: UserDto) =
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
