package dsm.wemeet.global.mail.usecase

import dsm.wemeet.domain.user.service.QueryUserService
import dsm.wemeet.global.mail.MailProperties
import dsm.wemeet.global.mail.exception.InternalMailServerException
import dsm.wemeet.global.mail.presentation.dto.request.SendMailRequest
import dsm.wemeet.global.redis.RedisUtil
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import org.springframework.core.io.ClassPathResource
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.util.FileCopyUtils
import kotlin.random.Random

@Service
class SendMailUseCase(
    private val mailSender: JavaMailSender,
    private val mailProperties: MailProperties,
    private val queryUserService: QueryUserService,
    private val redisUtil: RedisUtil
) {

    @Async("taskExecutor")
    fun execute(request: SendMailRequest) {
        val cleanEmail = request.mail.replace("\"", "")
        queryUserService.existsByEmail(cleanEmail)

        val authCode = generateAuthCode()
        redisUtil.setDataExpire(cleanEmail, authCode, 600)

        val message: MimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")

        helper.setTo(cleanEmail)
        helper.setSubject("[wemeet] 이메일 인증번호 확인")
        helper.setFrom(InternetAddress(mailProperties.username, "wemeet"))

        val content = loadEmailTemplate().replace("\${authCode}", authCode)

        helper.setText(content, true)
        try {
            mailSender.send(message)
        } catch (e: Exception) {
            redisUtil.deleteData(cleanEmail)
            throw InternalMailServerException
        }
    }

    private fun generateAuthCode(): String {
        return Random.nextInt(1000, 9999).toString()
    }

    private fun loadEmailTemplate(): String {
        val template = ClassPathResource("templates/email_template.html")
        return String(FileCopyUtils.copyToByteArray(template.inputStream))
    }
}
