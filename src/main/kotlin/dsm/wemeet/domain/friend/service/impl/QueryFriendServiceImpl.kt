package dsm.wemeet.domain.friend.service.impl

import dsm.wemeet.domain.friend.exception.FriendNotFoundException
import dsm.wemeet.domain.friend.repository.FriendJpaRepository
import dsm.wemeet.domain.friend.repository.model.Friend
import dsm.wemeet.domain.friend.service.QueryFriendService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class QueryFriendServiceImpl(
    private val friendJpaRepository: FriendJpaRepository
) : QueryFriendService {

    override fun queryAcceptedFriendsByUser(userId: String): List<Friend> =
        friendJpaRepository.findAcceptedFriendsByUser(userId)

    override fun queryFriendById(id: UUID) =
        friendJpaRepository.findByIdOrNull(id) ?: throw FriendNotFoundException

    override fun queryFriendRequest(user1: String, user2: String) =
        friendJpaRepository.findByAnyEmail(user1, user2)
}
