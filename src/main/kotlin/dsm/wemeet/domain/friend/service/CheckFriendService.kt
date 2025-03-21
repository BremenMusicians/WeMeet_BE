package dsm.wemeet.domain.friend.service

import dsm.wemeet.domain.user.repository.model.User

interface CheckFriendService {

    fun checkIsFriend(user1: User, user2: User)
}
