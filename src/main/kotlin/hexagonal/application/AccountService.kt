package hexagonal.application

import hexagonal.infra.inbound.CreateAccountHttpRequest
import hexagonal.infra.outbound.AccountDao
import hexagonal.infra.outbound.MembershipLevel
import hexagonal.infra.outbound.OrmBillingType
import hexagonal.infra.outbound.SomeOrmAccountDto
import hexagonal.infra.outbound.UserDto
import hexagonal.infra.outbound.UserHttpClient
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

    private fun buildAccountDto(name: String, user: UserDto) =
        SomeOrmAccountDto(
            id = UUID.randomUUID(),
            userId = user.id,
            accountName = name,
            email = user.email,
            billingType = when (user.membership) {
                MembershipLevel.BASIC -> OrmBillingType.MONTHLY
                MembershipLevel.PREMIUM -> OrmBillingType.YEARLY
            }
        )
}
