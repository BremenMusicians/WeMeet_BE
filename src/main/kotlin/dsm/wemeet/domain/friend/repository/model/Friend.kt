package dsm.wemeet.domain.friend.repository.model

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
class Friend(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    val id: UUID,

    @ManyToOne(optional = false, targetEntity = User::class)
    @JoinColumn(columnDefinition = "VARCHAR(50)", nullable = false, name = "proposer_email")
    val proposer: User,

    @ManyToOne(optional = false, targetEntity = User::class)
    @JoinColumn(columnDefinition = "VARCHAR(50)", nullable = false, name = "receiver_email")
    val receiver: User,

    @Column(columnDefinition = "BOOLEAN", nullable = false, name = "is_accepted")
    val isAccepted: Boolean,
)
