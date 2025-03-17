package dsm.wemeet.domain.friend.repository

import dsm.wemeet.domain.friend.repository.model.Friend
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface FriendJpaRepository : JpaRepository<Friend, UUID>
