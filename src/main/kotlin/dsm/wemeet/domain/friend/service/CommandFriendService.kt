package dsm.wemeet.domain.friend.service

import dsm.wemeet.domain.friend.repository.model.Friend

interface CommandFriendService {

    fun saveFriend(friend: Friend): Friend
}