package dsm.wemeet.domain.user.usecase

import dsm.wemeet.domain.user.service.QueryUserService
import org.springframework.stereotype.Service

@Service
class ExistAccountIdUseCase(
    private val queryUserService: QueryUserService
) {

    fun execute(accountId: String) =
        queryUserService.existsByAccountId(accountId)
}
