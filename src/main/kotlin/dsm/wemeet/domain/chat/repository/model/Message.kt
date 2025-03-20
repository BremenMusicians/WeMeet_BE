package dsm.wemeet.domain.chat.repository.model

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
class Message(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    val id: UUID,

    @ManyToOne(optional = false, targetEntity = Chat::class)
    @JoinColumn(name = "chat_id", referencedColumnName = "id", nullable = false)
    val chat: Chat,

    @ManyToOne(optional = false, targetEntity = User::class)
    @JoinColumn(name = "sender_email", referencedColumnName = "email", nullable = false)
    val sender: User,

    @Column(columnDefinition = "DATETIME", nullable = false, name = "send_at")
    val sendAt: LocalDateTime = LocalDateTime.now(),

    @Column(columnDefinition = "TEXT", nullable = false)
    val content: String,


)
