package dsm.wemeet.domain.friend.repository.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.UUID

@Entity
class Friend(

    @Id
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    val id: UUID? = null,

    @Column(columnDefinition = "VARCHAR(4)", nullable = false)
    val testColumn: String,
)