package dsm.wemeet.domain.friend.service

import dsm.wemeet.domain.friend.repository.model.Friend
import dsm.wemeet.domain.user.repository.model.User
import java.util.UUID

interface QueryFriendService {

    fun queryAcceptedFriendsByUser(userId: String): List<Friend>

    fun queryFriendById(id: UUID): Friend

    fun queryNullableFriendRequestByEmails(email1: String, email2: String): Friend?

    fun queryFriendUserListByEmailAndContainsAccountIdOffsetByPage(email: String, accountId: String, page: Int): List<User>
}
