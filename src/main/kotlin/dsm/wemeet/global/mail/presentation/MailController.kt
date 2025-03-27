package dsm.wemeet.global.mail.presentation

import dsm.wemeet.global.mail.presentation.dto.request.CheckMailRequest
import dsm.wemeet.global.mail.presentation.dto.request.SendMailRequest
import dsm.wemeet.global.mail.usecase.SendMailUseCase
import dsm.wemeet.global.mail.usecase.VerifyMailServiceUseCase
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mail")
class MailController(
    private val sendMailUseCase: SendMailUseCase,
    private val verifyMailServiceUseCase: VerifyMailServiceUseCase
) {

    @PostMapping
    fun sendMail(
        @Valid @RequestBody
        request: SendMailRequest
    ) = sendMailUseCase.execute(request)

    @PostMapping("/check")
    fun verify(
        @Valid @RequestBody
        request: CheckMailRequest
    ) = verifyMailServiceUseCase.execute(request)
}
