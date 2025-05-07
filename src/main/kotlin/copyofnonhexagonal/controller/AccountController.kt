package copyofnonhexagonal.controller

import copyofnonhexagonal.service.AccountService
import java.util.UUID

// fake class to simulate a controller
class AccountController(
    private val accountService: AccountService
) {

    fun create(request: CreateAccountHttpRequest): CreateAccountHttpResponse {
        val newId = accountService.createAccount(request)
        return CreateAccountHttpResponse(newId)
    }
}

data class CreateAccountHttpRequest(val userId: UUID, val accountName: String)

data class CreateAccountHttpResponse(val accountId: UUID)
