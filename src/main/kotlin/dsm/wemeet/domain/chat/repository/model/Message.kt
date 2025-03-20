package dsm.wemeet.domain.chat.repository.model

import dsm.wemeet.domain.user.repository.model.User
import jakarta.persistence.*
import java.util.*

@Entity
class Message(

    @Id
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    val id: UUID? = null,

    @ManyToOne(optional = false, targetEntity = Chat::class)
    @JoinColumn(columnDefinition = "BINARY(16)", nullable = false, name = "chat_id")
    val chatId: UUID,

    @ManyToOne(optional = false, targetEntity = User::class)
    @JoinColumn(columnDefinition = "VARCHAR(50)", nullable = false, name = "sender_email")
    val senderEmail: String
)
