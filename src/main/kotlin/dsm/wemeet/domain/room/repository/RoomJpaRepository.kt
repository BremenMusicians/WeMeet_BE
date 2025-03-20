package dsm.wemeet.domain.room.repository

import dsm.wemeet.domain.room.repository.model.Room
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RoomJpaRepository : JpaRepository<Room, UUID>
