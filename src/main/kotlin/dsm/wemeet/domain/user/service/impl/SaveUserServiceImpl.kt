package dsm.wemeet.domain.user.service.impl

import dsm.wemeet.domain.user.repository.UserJpaRepository
import dsm.wemeet.domain.user.repository.model.User
import dsm.wemeet.domain.user.service.SaveUserService
import org.springframework.stereotype.Service

@Service
class SaveUserServiceImpl(
    private val userJpaRepository: UserJpaRepository
) : SaveUserService {

    override fun save(user: User) = userJpaRepository.save(user)
}
