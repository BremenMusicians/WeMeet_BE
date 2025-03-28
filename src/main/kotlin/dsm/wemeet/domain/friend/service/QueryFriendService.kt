package dsm.wemeet.domain.friend.service

import dsm.wemeet.domain.friend.repository.model.Friend

interface QueryFriendService {

    fun queryAcceptedFriendsByUser(userId: String): List<Friend>
}
