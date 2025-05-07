package copyofnonhexagonal.clients

import java.util.UUID

// fake class to simulate an HTTP client
class UserHttpClient {

    fun fetchUser(userId: UUID): UserDTO {
        return UserDTO(
            id = userId,
            name = "John Doe",
            email = "john.doe@gmail.com",
            membership = MembershipLevel.PREMIUM
        )
    }
}

data class UserDTO(
    val id: UUID,
    val name: String,
    val email: String,
    val membership: MembershipLevel
)
enum class MembershipLevel { BASIC, PREMIUM }
