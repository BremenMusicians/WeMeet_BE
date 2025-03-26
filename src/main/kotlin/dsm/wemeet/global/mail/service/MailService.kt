package dsm.wemeet.global.mail.service

import dsm.wemeet.global.mail.MailProperties
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

@Service
class MailService(
    private val mailSender: JavaMailSender,
    private val mailProperties: MailProperties
) {

    @Transactional
    fun sendCode(to: String): String {
        val authCode = generateAuthCode()

        val message: MimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")

        helper.setTo(to)
        helper.setSubject("[wemeet] 이메일 인증번호 확인")
        helper.setFrom(InternetAddress(mailProperties.username, "wemeet"))

        val msgg = """
            <div style='padding: 40px; text-align: center;'>
                <h1 style='font-weight: bold;'>이메일 인증번호 확인</h1>
                <p style='color: gray;'>wemeet 로그인 및 회원가입을 위한 인증번호입니다.</p>
                <p style='color: gray;'>아래 인증번호를 진행 중인 화면에 입력하여 이메일 인증을 완료해 주세요.</p>
                <br>
                <h2 style='font-weight: bold;'>인증번호</h2>
                <div style='font-size: 250%; color: #007bff; background-color: #eef4ff; 
                            padding: 15px 30px; display: inline-block; border-radius: 8px;'>
                    <strong>$authCode</strong>
                </div>
                <br>
                <p style='color: gray; font-size: 12px;'>본 메일은 발신전용이며, 문의에 대한 회신은 처리되지 않습니다.</p>
            </div>
        """.trimIndent()

        helper.setText(msgg, true)
        mailSender.send(message)

        return authCode
    }
}

private fun generateAuthCode(): String {
    return Random.nextInt(1000, 9999).toString()
}
