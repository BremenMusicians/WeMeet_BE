package dsm.wemeet.domain.friend.repository.model

import dsm.wemeet.domain.user.repository.model.User
import jakarta.persistence.*
import java.util.UUID

@Entity
class Friend(

    @Id
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    val id: UUID? = null,

    @ManyToOne(optional = false, targetEntity = User::class)
    @JoinColumn(columnDefinition = "VARCHAR(50)", nullable = false, name = "proposer_email")
    val proposerEmail: String,

    @ManyToOne(optional = false, targetEntity = User::class)
    @JoinColumn(columnDefinition = "VARCHAR(50)", nullable = false, name = "receiver_email")
    val receiverEmail: String
)
