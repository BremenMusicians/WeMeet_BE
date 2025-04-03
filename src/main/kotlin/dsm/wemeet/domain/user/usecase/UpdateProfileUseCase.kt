package dsm.wemeet.domain.user.usecase

import dsm.wemeet.domain.user.service.QueryUserService
import dsm.wemeet.global.s3.S3Util
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Transactional
@Service
class UpdateProfileUseCase(
    private val s3Util: S3Util,
    private val queryUserService: QueryUserService
) {

    fun execute(file: MultipartFile) {
        val user = queryUserService.getCurrentUser()

        user.profile?.let { s3Util.delete(it) }

        user.profile = s3Util.upload(file)
    }
}
