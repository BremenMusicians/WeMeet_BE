package dsm.wemeet.domain.friend.service.impl

import dsm.wemeet.domain.friend.exception.FriendAlreadyExistsException
import dsm.wemeet.domain.friend.repository.FriendJpaRepository
import dsm.wemeet.domain.friend.service.CheckFriendService
import dsm.wemeet.domain.user.repository.model.User
import org.springframework.stereotype.Service

@Service
class CheckFriendServiceImpl(
    private val friendJpaRepository: FriendJpaRepository
) : CheckFriendService {

    override fun checkIsAlreadyFriend(user1: User, user2: User) {
        friendJpaRepository.findByAnyEmail(user1.email, user2.email)
            ?: throw FriendAlreadyExistsException
    }
}
