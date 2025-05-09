package hexagonal.infra.outbound

import hexagonal.domain.Membership
import hexagonal.domain.User
import hexagonal.domain.UserFetcher
import java.util.UUID

// fake class to simulate an HTTP client
class UserHttpClient {

    fun fetchUser(id: UUID): UserDto =
        // Simulate fetching user data from an external service

        // fetch UserDto and map it to User
        UserDto(id = id, name = "John Doe", email = "john.doe@gmail.com")
}

data class UserDto(
    val id: UUID,
    val name: String,
    val email: String,
)
