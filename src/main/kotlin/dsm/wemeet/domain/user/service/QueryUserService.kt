package dsm.wemeet.domain.user.service

import dsm.wemeet.domain.user.repository.model.User

interface QueryUserService {

    fun getCurrentUser(): User

    fun queryUserByEmail(email: String): User

    fun queryUserByAccountId(accountId: String): User

    fun queryUserListByAccountIdContainsAndOffsetByPage(page: Int, accountId: String): List<User>

    fun countUsersByAccountIdContains(accountId: String): Long

    fun existsByEmail(email: String)

    fun existsByAccountId(accountId: String)
}
