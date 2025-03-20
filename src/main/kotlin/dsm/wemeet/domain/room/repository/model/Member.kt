package dsm.wemeet.domain.room.repository.model

import dsm.wemeet.domain.user.repository.model.User
import jakarta.persistence.*
import java.util.*

@Entity
class Member(

    @Id
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    val id: UUID,

    @ManyToOne(optional = false, targetEntity = Room::class)
    @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false)
    val roomId: Room,

    @ManyToOne(optional = false, targetEntity = User::class)
    @JoinColumn(name = "member_email", referencedColumnName = "email", nullable = false)
    val memberEmail: User
)
