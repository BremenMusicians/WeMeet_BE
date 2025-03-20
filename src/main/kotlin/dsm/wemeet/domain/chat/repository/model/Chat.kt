package dsm.wemeet.domain.chat.repository.model

import dsm.wemeet.domain.user.repository.model.User
import jakarta.persistence.*
import java.util.*

@Entity
class Chat(

    @Id
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    val id: UUID? = null,

    @ManyToOne(optional = false, targetEntity = User::class)
    @JoinColumn(columnDefinition = "VARCHAR(50)", nullable = false, name = "user1_email")
    val user1Email: String,

    @ManyToOne(optional = false, targetEntity = User::class)
    @JoinColumn(columnDefinition = "VARCHAR(50)", nullable = false, name = "user2_email")
    val user2Email: String
)
