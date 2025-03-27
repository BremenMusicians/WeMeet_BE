package dsm.wemeet.global.config.mail

import dsm.wemeet.global.mail.MailProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.Properties

@Configuration
class MailConfig(
    private val mailProperties: MailProperties
) {

    @Bean
    fun mailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = mailProperties.host
        mailSender.port = mailProperties.port
        mailSender.username = mailProperties.username
        mailSender.password = mailProperties.password

        val javaMailProperties = Properties()
        javaMailProperties["mail.transport.protocol"] = "smtp"
        javaMailProperties["mail.smtp.auth"] = true
        javaMailProperties["mail.smtp.starttls.enable"] = true
        javaMailProperties["mail.smtp.starttls.required"] = true
        javaMailProperties["mail.debug"] = "true"

        mailSender.javaMailProperties = javaMailProperties

        return mailSender
    }
}
