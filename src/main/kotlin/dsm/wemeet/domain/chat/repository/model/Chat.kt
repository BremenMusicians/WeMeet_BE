package dsm.wemeet.domain.chat.repository.model

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
class Chat(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    val id: UUID,

    @ManyToOne(optional = false, targetEntity = User::class)
    @JoinColumn(name = "user1_email", referencedColumnName = "id", nullable = false)
    val user1: String,

    @ManyToOne(optional = false, targetEntity = User::class)
    @JoinColumn(name = "user2_email", referencedColumnName = "id", nullable = false)
    val user2: String
)
