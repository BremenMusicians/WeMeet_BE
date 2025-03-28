package dsm.wemeet.domain.room.service.impl

import dsm.wemeet.domain.room.repository.RoomJpaRepository
import dsm.wemeet.domain.room.repository.vo.CurrentRoomVo
import dsm.wemeet.domain.room.service.QueryRoomService
import org.springframework.stereotype.Service

@Service
class QueryRoomServiceImpl(
    private val roomJpaRepository: RoomJpaRepository
) : QueryRoomService {

    override fun queryRoomListByNameAndOffsetByPage(page: Int, name: String?): List<CurrentRoomVo> =
        roomJpaRepository.findAllRoomDetailsByName(name)
            .stream()
            .skip(page * 6L)
            .limit(6)
            .toList()

    override fun queryRoomCount(): Int =
        roomJpaRepository.count().toInt()
}
