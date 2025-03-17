package dsm.wemeet.domain.friend.service.impl

import dsm.wemeet.domain.friend.repository.FriendJpaRepository
import dsm.wemeet.domain.friend.service.QueryFriendService
import org.springframework.stereotype.Service

@Service
class QueryFriendServiceImpl(
    private val friendJpaRepository: FriendJpaRepository
) : QueryFriendService {

}