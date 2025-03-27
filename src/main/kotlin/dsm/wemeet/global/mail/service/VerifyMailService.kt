package dsm.wemeet.global.mail.service

import dsm.wemeet.global.mail.exception.ExpiredMailCodeException
import dsm.wemeet.global.mail.exception.MailCodeMissMatchException
import dsm.wemeet.global.mail.presentation.dto.request.CheckMailRequest
import dsm.wemeet.global.redis.RedisUtil
import org.springframework.stereotype.Service

@Service
class VerifyMailService(
    private val redisUtil: RedisUtil
) {

    fun execute(request: CheckMailRequest): Boolean {
        val redisCode = redisUtil.getData(request.mail) ?: throw ExpiredMailCodeException

        return if (redisCode == request.code) {
            redisUtil.deleteData(request.mail)
            true
        } else {
            throw MailCodeMissMatchException
        }
    }
}
