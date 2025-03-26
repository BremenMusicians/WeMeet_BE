package dsm.wemeet.global.mail

import dsm.wemeet.global.mail.service.MailService
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
    fun sendMail(@RequestBody to: String) = mailService.sendCode(to)
}
