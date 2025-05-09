package hexagonal.infra.outbound

import hexagonal.domain.Membership
import hexagonal.domain.User
import hexagonal.domain.UserFetcher
import java.util.UUID

class CompositeUserFetcherAdapter(
    private val userHttpClient: UserHttpClient,
    private val membershipHttpClient: MembershipHttpClient
) : UserFetcher {
    override fun fetchUser(id: UUID): User {
        val user = userHttpClient.fetchUser(id)
        val membership = membershipHttpClient.fetchMembership(id)
        return User(id, user.name, user.email, Membership.valueOf(membership.name.name))
    }
}
