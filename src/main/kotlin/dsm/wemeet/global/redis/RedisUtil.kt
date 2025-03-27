package dsm.wemeet.global.redis

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class RedisUtil(private val stringRedisTemplate: StringRedisTemplate) {

    fun getData(key: String): String? {
        val valueOperations = stringRedisTemplate.opsForValue()
        return valueOperations[key]
    }

    fun setDataExpire(key: String, value: String, duration: Long) {
        val valueOperations = stringRedisTemplate.opsForValue()
        valueOperations.set(key, value, duration, TimeUnit.SECONDS)
    }

    fun deleteData(key: String) {
        stringRedisTemplate.delete(key)
    }
}
