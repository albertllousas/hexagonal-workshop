package hexagonal.infra.outbound

import java.util.UUID

class MembershipHttpClient() {
    fun fetchMembership(userId: UUID): MembershipDto =
        // Simulate fetching membership data from an external service
        MembershipDto(id = UUID.randomUUID(), name = MembershipName.BASIC, features = listOf("feature1", "feature2"))
}

data class MembershipDto(val id: UUID, val name: MembershipName, val features: List<String>)

enum class MembershipName { BASIC, PREMIUM }
