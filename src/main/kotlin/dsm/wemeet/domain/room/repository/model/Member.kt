package dsm.wemeet.domain.room.repository.model

import dsm.wemeet.domain.user.repository.model.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.util.UUID

@Entity
class Member(

    @Id
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    val id: UUID? = null,

    @ManyToOne(optional = false, targetEntity = Room::class)
    @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false)
    val room: Room,

    @ManyToOne(optional = false, targetEntity = User::class)
    @JoinColumn(name = "member_email", referencedColumnName = "email", nullable = false)
    val user: User
)
