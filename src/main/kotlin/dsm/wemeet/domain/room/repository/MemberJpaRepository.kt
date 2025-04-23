package dsm.wemeet.domain.room.repository

import dsm.wemeet.domain.room.repository.model.Member
import dsm.wemeet.domain.room.repository.model.Room
import dsm.wemeet.domain.user.repository.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberJpaRepository : JpaRepository<Member, UUID> {

    fun findByUserEmailAndRoomId(email: String, id: UUID): Member?

    fun findAllByRoomIdOrderByJoinedAt(roomId: UUID): List<Member>

    fun existsMemberByUserAndRoom(user: User, room: Room): Boolean

    fun countMemberByRoomId(roomId: UUID): Long
}
