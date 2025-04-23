package dsm.wemeet.domain.friend.service

import dsm.wemeet.domain.friend.repository.model.Friend
import java.util.UUID

interface QueryFriendService {

    fun queryAcceptedFriendsByUser(userId: String): List<Friend>

    fun queryFriendById(id: UUID): Friend

    fun queryFriendRequest(user1: String, user2: String): Friend?
}
