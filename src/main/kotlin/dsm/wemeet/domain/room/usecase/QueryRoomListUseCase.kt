package dsm.wemeet.domain.room.usecase

import dsm.wemeet.domain.room.presentation.dto.response.QueryRoomListResponse
import dsm.wemeet.domain.room.service.QueryRoomService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class QueryRoomListUseCase(
    private val queryRoomService: QueryRoomService
) {

    fun execute(page: Int, name: String?): QueryRoomListResponse {
        val rooms = queryRoomService.queryRoomListByNameContainsAndOffsetByPage(page, name)
        val count = queryRoomService.countByNameContains(name)

        return QueryRoomListResponse(rooms, count)
    }
}
