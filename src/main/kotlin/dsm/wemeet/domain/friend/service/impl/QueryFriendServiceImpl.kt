package dsm.wemeet.domain.friend.service.impl

import dsm.wemeet.domain.friend.repository.FriendJpaRepository
import dsm.wemeet.domain.friend.repository.model.Friend
import dsm.wemeet.domain.friend.service.QueryFriendService
import org.springframework.stereotype.Service

@Service
class QueryFriendServiceImpl(
    private val friendJpaRepository: FriendJpaRepository
) : QueryFriendService {

    override fun queryAcceptedFriendsByUser(userId: String): List<Friend> =
        friendJpaRepository.findAcceptedFriendsByUser(userId)
}
