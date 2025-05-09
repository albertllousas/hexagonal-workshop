package hexagonal.domain

import java.util.UUID

interface UserFetcher {
    fun fetchUser(id: UUID): User
}

data class User(val id: UUID, val name: String, val email: String, val membership: Membership)

enum class Membership { BASIC, PREMIUM }


