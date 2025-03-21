package dsm.wemeet.domain.friend.service

import dsm.wemeet.domain.user.repository.model.User

interface CheckFriendService {

    fun checkIsNotFriend(user1: User, user2: User)
}
