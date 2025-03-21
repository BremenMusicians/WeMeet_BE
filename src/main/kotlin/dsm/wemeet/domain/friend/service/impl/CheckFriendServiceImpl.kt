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

    override fun checkIsFriend(user1: User, user2: User) {
        if (friendJpaRepository.findByAnyEmail(user1.email, user2.email) == null) {
            throw FriendAlreadyExistsException
        }
    }
}
