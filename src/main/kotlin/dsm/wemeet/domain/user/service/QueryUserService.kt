package dsm.wemeet.domain.user.service

import dsm.wemeet.domain.user.repository.model.User

interface QueryUserService {

    fun queryUserByEmail(email: String): User?

    fun queryUserByAccountId(accountId: String): User?

    fun existsByEmail(email: String): Boolean

    fun existsByAccountId(accountId: String): Boolean
}
