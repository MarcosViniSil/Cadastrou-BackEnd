package registered.project.api.exceptions.handler

import java.time.LocalDateTime

data class ExceptionDetails(
    val title: String,
    val timestamp: LocalDateTime,
    val status: Int
) {
}