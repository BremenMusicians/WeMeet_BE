package dsm.wemeet.domain.friend.service.impl

import dsm.wemeet.domain.friend.repository.FriendJpaRepository
import dsm.wemeet.domain.friend.service.CheckFriendService
import org.springframework.stereotype.Service

@Service
class CheckFriendServiceImpl(
    private val friendJpaRepository: FriendJpaRepository
) : CheckFriendService {

}