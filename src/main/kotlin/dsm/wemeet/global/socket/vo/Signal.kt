package dsm.wemeet.global.socket.vo

import com.fasterxml.jackson.databind.JsonNode

data class Signal(
    val type: String,
    val to: String? = null,
    val data: JsonNode? = null
)
