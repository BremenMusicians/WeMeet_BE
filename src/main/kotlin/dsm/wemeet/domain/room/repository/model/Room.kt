package dsm.wemeet.domain.room.repository.model

import dsm.wemeet.domain.user.repository.model.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.util.UUID

@Entity
class Room(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    val id: UUID,

    @ManyToOne(optional = false, targetEntity = User::class)
    @JoinColumn(name = "owner", referencedColumnName = "email", nullable = false)
    val owner: User,

    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    val name: String,

    @Column(columnDefinition = "TINYINT", nullable = false, name = "max_member")
    val maxMember: Int,

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    val info: String,

    @Column(columnDefinition = "BOOLEAN", nullable = false, name = "is_public")
    val isPublic: Boolean,

    @Column(columnDefinition = "SMALLINT", nullable = false)
    val password: Int
)
