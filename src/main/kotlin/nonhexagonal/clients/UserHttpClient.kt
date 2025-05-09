package nonhexagonal.clients

import java.util.UUID

// fake class to simulate an HTTP client
class UserHttpClient {

    fun fetchUser(userId: UUID): UserDto =
        // Simulate fetching user data from an external service
        UserDto(id = userId, name = "John Doe", email = "john.doe@gmail.com")
}

data class UserDto(
    val id: UUID,
    val name: String,
    val email: String,
)
