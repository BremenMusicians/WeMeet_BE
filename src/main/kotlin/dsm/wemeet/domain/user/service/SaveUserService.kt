package dsm.wemeet.domain.user.service

import dsm.wemeet.domain.user.repository.model.User

interface SaveUserService {

    fun save(user: User): User
}
