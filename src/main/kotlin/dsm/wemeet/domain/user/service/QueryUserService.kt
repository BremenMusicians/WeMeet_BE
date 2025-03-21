package dsm.wemeet.domain.user.service

import dsm.wemeet.domain.user.repository.model.User

interface QueryUserService {

    fun getCurrentUser(): User

    fun queryUserByEmail(email: String): User

    fun queryUserByAccountId(accountId: String): User

    fun existsByEmail(email: String)

    fun existsByAccountId(accountId: String)
}
