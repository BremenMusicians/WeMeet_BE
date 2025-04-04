package dsm.wemeet.domain.room.service.impl

import dsm.wemeet.domain.room.exception.MemberNotFoundException
import dsm.wemeet.domain.room.exception.RoomNotFoundException
import dsm.wemeet.domain.room.repository.MemberJpaRepository
import dsm.wemeet.domain.room.repository.RoomJpaRepository
import dsm.wemeet.domain.room.repository.model.Member
import dsm.wemeet.domain.room.repository.model.Room
import dsm.wemeet.domain.room.repository.vo.CurrentRoomVo
import dsm.wemeet.domain.room.service.QueryRoomService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class QueryRoomServiceImpl(
    private val roomJpaRepository: RoomJpaRepository,
    private val memberJpaRepository: MemberJpaRepository
) : QueryRoomService {

    override fun queryRoomListByNameContainsAndOffsetByPage(page: Int, name: String?): List<CurrentRoomVo> {
        val pageable: Pageable = PageRequest.of(page, 6)

        return roomJpaRepository.findAllRoomDetailsByNamePageable(name, pageable)
    }

    override fun queryRoomById(id: UUID): Room =
        roomJpaRepository.findByIdOrNull(id) ?: throw RoomNotFoundException

    override fun queryMemberByUserEmailAndRoomId(userEmail: String, roomId: UUID): Member =
        memberJpaRepository.findByUserEmailAndRoomId(userEmail, roomId) ?: throw MemberNotFoundException

    override fun countByNameContains(name: String?): Int =
        roomJpaRepository.countByNameContaining(name).toInt()
}
