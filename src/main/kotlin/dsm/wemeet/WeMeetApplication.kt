package dsm.wemeet

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class WeMeetApplication

fun main(args: Array<String>) {
    runApplication<WeMeetApplication>(*args)
}
