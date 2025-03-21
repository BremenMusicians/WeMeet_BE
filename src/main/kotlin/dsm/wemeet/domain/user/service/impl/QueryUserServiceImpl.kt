package dsm.wemeet.domain.user.service.impl

import dsm.wemeet.domain.user.exception.UserAlreadyExistException
import dsm.wemeet.domain.user.exception.UserNotFoundException
import dsm.wemeet.domain.user.repository.UserJpaRepository
import dsm.wemeet.domain.user.service.QueryUserService
import org.springframework.stereotype.Service

@Service
class QueryUserServiceImpl(
    private val userJpaRepository: UserJpaRepository
) : QueryUserService {

    override fun queryUserByEmail(email: String) =
        userJpaRepository.findByEmail(email) ?: throw UserNotFoundException

    override fun queryUserByAccountId(accountId: String) =
        userJpaRepository.findByAccountId(accountId) ?: throw UserNotFoundException

    override fun existsByEmail(email: String) =
        checkExists(userJpaRepository.existsByEmail(email))

    override fun existsByAccountId(accountId: String) =
        checkExists(userJpaRepository.existsByAccountId(accountId))

    private fun checkExists(exists: Boolean) {
        if (exists) throw UserAlreadyExistException
    }
}
