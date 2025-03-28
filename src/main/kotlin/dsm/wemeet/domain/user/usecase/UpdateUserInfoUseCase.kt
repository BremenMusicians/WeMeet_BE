package dsm.wemeet.domain.user.usecase

import dsm.wemeet.domain.user.presentation.dto.request.UpdateUserInfoRequest
import dsm.wemeet.domain.user.repository.model.User
import dsm.wemeet.domain.user.service.QueryUserService
import dsm.wemeet.domain.user.service.SaveUserService
import org.springframework.stereotype.Service

@Service
class UpdateUserInfoUseCase(
    private val queryUserService: QueryUserService,
    private val saveUserService: SaveUserService
) {

    fun execute(request: UpdateUserInfoRequest) {
        val user = queryUserService.getCurrentUser()

        if (user.accountId != request.accountId) queryUserService.existsByAccountId(request.accountId)

        val newUser = User(
            email = user.email,
            password = user.password,
            accountId = request.accountId,
            profile = user.profile,
            aboutMe = request.aboutMe,
            position = request.position.joinToString(",")
        )

        saveUserService.save(newUser)
    }
}
