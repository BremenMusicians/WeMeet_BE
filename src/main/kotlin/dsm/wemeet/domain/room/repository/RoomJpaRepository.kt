package dsm.wemeet.domain.room.repository

import dsm.wemeet.domain.room.repository.model.Room
import dsm.wemeet.domain.room.repository.vo.CurrentRoomVo
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface RoomJpaRepository : JpaRepository<Room, UUID> {

    @Query(
        """
        SELECT new dsm.wemeet.domain.room.repository.vo.CurrentRoomVo(r.id, r.name, r.info, (r.password IS NOT NULL), COUNT(m.id), r.maxMember)
        FROM Room as r
        LEFT JOIN Member as m ON r.id = m.room.id
        WHERE r.name LIKE CONCAT('%', :name, '%')
        GROUP BY r.id
        ORDER BY r.createdAt desc
    """
    )
    fun findAllRoomDetailsByNamePageable(@Param("name") name: String, pageable: Pageable): List<CurrentRoomVo>

    fun countByNameContaining(name: String): Long
}
