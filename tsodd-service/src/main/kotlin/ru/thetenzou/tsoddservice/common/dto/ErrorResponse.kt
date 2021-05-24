package ru.thetenzou.tsoddservice.common.dto

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import java.util.*

/**
 * Error response structure
 *
 * @param httpStatus response status
 * @param message text of the message
 */
class ErrorResponse(
    httpStatus: HttpStatus,
    val message: String,
) {

    val code: Int
    val status: String

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss")
    val timestamp: Date = Date()

    init {
        code = httpStatus.value()
        status = httpStatus.name
    }
}
