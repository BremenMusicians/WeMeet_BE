package dsm.wemeet.domain.friend.service

import dsm.wemeet.domain.friend.repository.model.Friend
import dsm.wemeet.domain.user.repository.model.User
import java.util.UUID

interface QueryFriendService {

    fun queryFriendsByEmailAndAcceptance(email: String, isAccepted: Boolean): List<Friend>

    fun queryFriendById(id: UUID): Friend

    fun queryNullableFriendRequestByEmails(email1: String, email2: String): Friend?

    fun queryFriendUserListByEmailAndAccountIdContainsOffsetByPage(email: String, accountId: String, page: Int): List<User>

    fun countFriendsByEmailAndAccountIdContains(email: String, accountId: String): Long
}
