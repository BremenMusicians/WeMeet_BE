package dsm.wemeet.domain.room.repository.model

import dsm.wemeet.domain.user.repository.model.Position
import dsm.wemeet.domain.user.repository.model.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime
import java.util.UUID

@Entity
class Member(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    val id: UUID? = null,

    @ManyToOne(optional = false, targetEntity = Room::class)
    @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false)
    val room: Room,

    @ManyToOne(optional = false, targetEntity = User::class)
    @JoinColumn(name = "member_email", referencedColumnName = "email", nullable = false)
    val user: User,

    @Column(columnDefinition = "DATETIME", nullable = false, name = "joined_at")
    val joinedAt: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    @Column(nullable = true, name = "position")
    var position: Position? = null
)
