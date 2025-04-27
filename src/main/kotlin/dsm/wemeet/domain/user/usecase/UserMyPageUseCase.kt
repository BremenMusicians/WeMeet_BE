package dsm.wemeet.domain.user.usecase

import dsm.wemeet.domain.user.presentation.dto.response.MyPageResponse
import dsm.wemeet.domain.user.repository.model.Position
import dsm.wemeet.domain.user.service.QueryUserService
import dsm.wemeet.global.s3.S3Util
import org.springframework.stereotype.Service

@Service
class UserMyPageUseCase(
    private val queryUserService: QueryUserService,
    private val s3Util: S3Util
) {

    fun execute(): MyPageResponse {
        val user = queryUserService.getCurrentUser()
        val profileUrl = user.profile?.let { s3Util.generateUrl(it) }

        return MyPageResponse(
            accountId = user.accountId,
            profile = profileUrl,
            aboutMe = user.aboutMe,
            position = user.position.split(",").map { Position.valueOf(it) }
        )
    }
}
