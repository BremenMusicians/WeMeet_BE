package dsm.wemeet.domain.friend.service.impl

import dsm.wemeet.domain.friend.exception.FriendNotFoundException
import dsm.wemeet.domain.friend.repository.FriendJpaRepository
import dsm.wemeet.domain.friend.repository.model.Friend
import dsm.wemeet.domain.friend.service.QueryFriendService
import dsm.wemeet.domain.user.repository.model.User
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

    // 두 사용자 간의 친구관계 또는 친구 요청을 조회
    override fun queryNullableFriendRequestByEmails(email1: String, email2: String) =
        friendJpaRepository.findByAnyEmail(email1, email2)

    override fun queryFriendUserListByEmailContainsAccountId(email: String, accountId: String): List<User> {
        friendJpaRepository.findFriendUsersByEmailContainsAccountId(email, accountId)
    }
}
