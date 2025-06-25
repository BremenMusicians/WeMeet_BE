package dsm.wemeet.domain.user.usecase

import dsm.wemeet.domain.user.presentation.dto.response.SimpleInfoResponse
import dsm.wemeet.domain.user.service.QueryUserService
import dsm.wemeet.global.s3.S3Util
import org.springframework.stereotype.Service

@Service
class QueryUserSimpleInfoUseCase(
    private val queryUserService: QueryUserService,
    private val s3Util: S3Util
) {

    fun execute(): SimpleInfoResponse {
        val user = queryUserService.getCurrentUser()
        return SimpleInfoResponse(
            accountId = user.accountId,
            profile = user.profile?.let { s3Util.generateUrl(it) }
        )
    }
}
