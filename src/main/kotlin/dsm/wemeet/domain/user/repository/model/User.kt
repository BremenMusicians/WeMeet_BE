package dsm.wemeet.domain.user.repository.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class User(

    @Id
    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    val email: String,

    @Column(columnDefinition = "CHAR(255)", nullable = false)
    val password: String,

    @Column(columnDefinition = "VARCHAR(20)", nullable = false, name = "account_id", unique = true)
    var accountId: String,

    @Column(columnDefinition = "CHAR(255)", nullable = true)
    var profile: String? = null,

    @Column(columnDefinition = "VARCHAR(255)", nullable = true, name = "about_me")
    var aboutMe: String? = null,

    @Column(columnDefinition = "VARCHAR(40)", nullable = false)
    var position: String
)
