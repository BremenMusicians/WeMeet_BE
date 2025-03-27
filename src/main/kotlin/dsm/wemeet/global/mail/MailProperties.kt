package dsm.wemeet.global.mail

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "mail")
class MailProperties(
    val host: String,
    val port: Int,
    val username: String,
    val password: String
)
