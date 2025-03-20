package dsm.wemeet.domain.user.service.impl

import dsm.wemeet.domain.user.repository.UserJpaRepository
import dsm.wemeet.domain.user.service.QueryUserService
import org.springframework.stereotype.Service

@Service
class QueryUserServiceImpl(
    private val userJpaRepository: UserJpaRepository
) : QueryUserService {

    override fun queryUserByEmail(email: String) =
        userJpaRepository.findByEmail(email)

    override fun queryUserByAccountId(accountId: String) =
        userJpaRepository.findByAccountId(accountId)

    override fun existByEmail(email: String) =
        userJpaRepository.existsByEmail(email)

    override fun existByAccountId(accountId: String) =
        userJpaRepository.existsByAccountId(accountId)
}
