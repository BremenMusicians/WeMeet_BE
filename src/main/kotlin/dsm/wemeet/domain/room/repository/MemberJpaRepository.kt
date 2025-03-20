package dsm.wemeet.domain.room.repository

import dsm.wemeet.domain.room.repository.model.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberJpaRepository : JpaRepository<Member, UUID>
