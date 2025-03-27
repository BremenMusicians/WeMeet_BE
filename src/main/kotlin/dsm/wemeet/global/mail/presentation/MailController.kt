package dsm.wemeet.global.mail.presentation

import dsm.wemeet.global.mail.presentation.dto.request.CheckMailRequest
import dsm.wemeet.global.mail.presentation.dto.request.SendMailRequest
import dsm.wemeet.global.mail.service.MailService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mail")
class MailController(
    private val mailService: MailService
) {

    @PostMapping
    fun sendMail(
        @Valid @RequestBody
        request: SendMailRequest
    ) = mailService.sendCode(request)

    @PostMapping("/check")
    fun verify(
        @Valid @RequestBody
        request: CheckMailRequest
    ) = mailService.verifyMailCode(request)
}
