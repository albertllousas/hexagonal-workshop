package hexagonal.domain

interface AccountRepository {
    fun save(account: Account): Account
}
