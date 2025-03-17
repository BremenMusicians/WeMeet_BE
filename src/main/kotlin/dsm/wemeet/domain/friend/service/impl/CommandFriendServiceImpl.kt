package dsm.wemeet.domain.friend.service.impl

import dsm.wemeet.domain.friend.repository.FriendJpaRepository
import dsm.wemeet.domain.friend.repository.model.Friend
import dsm.wemeet.domain.friend.service.CommandFriendService
import org.springframework.stereotype.Service

@Service
class CommandFriendServiceImpl(
    private val friendJpaRepository: FriendJpaRepository
) : CommandFriendService {

    override fun saveFriend(friend: Friend): Friend =
        friendJpaRepository.save(friend)
}