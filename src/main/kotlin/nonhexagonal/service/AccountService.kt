package nonhexagonal.service

import nonhexagonal.clients.*
import nonhexagonal.controller.CreateAccountHttpRequest
import nonhexagonal.persistence.AccountDao
import nonhexagonal.persistence.SomeOrmAccountDto
import java.util.UUID

class AccountService(
    private val userHttpClient: UserHttpClient,
    private val membershipHttpClient: MembershipHttpClient,
    private val accountDao: AccountDao,
) {

    fun createAccount(request: CreateAccountHttpRequest): UUID {
        val user = userHttpClient.fetchUser(request.userId)
        val membership = membershipHttpClient.fetchMembership(request.userId)
        val newAccount = buildAccountDto(request.name, user, membership)
        accountDao.insert(newAccount)
        return newAccount.id
    }

    private fun buildAccountDto(name: String, user: UserDto, membership: MembershipDto) =
        SomeOrmAccountDto(
            id = UUID.randomUUID(),
            userId = user.id,
            accountName = name,
            email = user.email,
            billingType = when (membership.name) {
                MembershipName.BASIC -> 0
                MembershipName.PREMIUM -> 1
            }
        )
}
