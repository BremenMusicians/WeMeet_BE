package dsm.wemeet.domain.user.service.impl

import dsm.wemeet.domain.user.exception.UserAlreadyExistException
import dsm.wemeet.domain.user.exception.UserNotFoundException
import dsm.wemeet.domain.user.repository.UserJpaRepository
import dsm.wemeet.domain.user.repository.model.User
import dsm.wemeet.domain.user.service.QueryUserService
import dsm.wemeet.global.jwt.SecurityService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class QueryUserServiceImpl(
    private val userJpaRepository: UserJpaRepository,
    private val securityService: SecurityService
) : QueryUserService {

    override fun getCurrentUser(): User =
        queryUserByEmail(securityService.getCurrentUserEmail())

    override fun queryUserByEmail(email: String) =
        userJpaRepository.findByEmail(email) ?: throw UserNotFoundException

    override fun queryUserByAccountId(accountId: String) =
        userJpaRepository.findByAccountId(accountId) ?: throw UserNotFoundException

    override fun queryUserListByAccountIdContainsAndOffsetByPage(page: Int, accountId: String): List<User> {
        val pageable: Pageable = PageRequest.of(page, 10)

        return userJpaRepository.findAllByAccountIdContaining(accountId, pageable)
    }

    override fun countUsersByAccountIdContains(accountId: String) =
        userJpaRepository.countByAccountIdContainsIgnoreCase(accountId)

    override fun existsByEmail(email: String) =
        checkExists(userJpaRepository.existsByEmail(email))

    override fun existsByAccountId(accountId: String) =
        checkExists(userJpaRepository.existsByAccountId(accountId))

    private fun checkExists(exists: Boolean) {
        if (exists) throw UserAlreadyExistException
    }
}
