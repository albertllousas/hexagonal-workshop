package hexagonal.infra.inbound

import hexagonal.application.AccountService
import java.util.UUID

// fake class to simulate a controller
class AccountHttpAdapter(
    private val accountService: AccountService
) {

    fun create(request: CreateAccountHttpRequest): CreateAccountHttpResponse {
        val newId = accountService.createAccount(request.userId, request.name)
        return CreateAccountHttpResponse(newId)
    }
}

data class CreateAccountHttpRequest(val userId: UUID, val name: String)

data class CreateAccountHttpResponse(val accountId: UUID)
