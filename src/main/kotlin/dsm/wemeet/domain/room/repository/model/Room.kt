package dsm.wemeet.domain.room.repository.model

import dsm.wemeet.domain.user.repository.model.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime
import java.util.UUID

@Entity
class Room(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    val id: UUID? = null,

    @ManyToOne(optional = false, targetEntity = User::class)
    @JoinColumn(name = "owner", referencedColumnName = "email", nullable = false)
    var owner: User,

    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    val name: String,

    @Column(columnDefinition = "TINYINT", nullable = false, name = "max_member")
    val maxMember: Int,

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    val info: String,

    @Column(columnDefinition = "CHAR(4)", nullable = true)
    val password: String?,

    @Column(columnDefinition = "DATETIME", nullable = false, name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
)
