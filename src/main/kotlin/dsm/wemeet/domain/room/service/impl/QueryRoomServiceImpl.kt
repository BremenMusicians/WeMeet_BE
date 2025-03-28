package dsm.wemeet.domain.room.service.impl

import dsm.wemeet.domain.room.repository.RoomJpaRepository
import dsm.wemeet.domain.room.repository.vo.CurrentRoomVo
import dsm.wemeet.domain.room.service.QueryRoomService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class QueryRoomServiceImpl(
    private val roomJpaRepository: RoomJpaRepository
) : QueryRoomService {

    override fun queryRoomListByNameContainsAndOffsetByPage(page: Int, name: String?): List<CurrentRoomVo> {
        val pageable: Pageable = PageRequest.of(page, 6)

        return roomJpaRepository.findAllRoomDetailsByNamePageable(name, pageable)
    }

    override fun countByNameContains(name: String?): Int =
        roomJpaRepository.countByNameContaining(name).toInt()
}
