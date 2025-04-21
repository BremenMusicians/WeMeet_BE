package dsm.wemeet.domain.user.usecase

import dsm.wemeet.domain.user.presentation.dto.response.AccountIdResponse
import dsm.wemeet.domain.user.service.QueryUserService
import org.springframework.stereotype.Service

@Service
class QueryAccountIdUseCase(
    private val queryUserService: QueryUserService
) {

    fun execute(): AccountIdResponse {
        val user = queryUserService.getCurrentUser()
        return AccountIdResponse(
            user.accountId
        )
    }
}
