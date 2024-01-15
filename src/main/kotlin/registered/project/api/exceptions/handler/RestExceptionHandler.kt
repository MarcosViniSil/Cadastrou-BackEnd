package registered.project.api.exceptions.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import registered.project.api.exceptions.*
import java.time.LocalDateTime


@RestControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(PasswordInvalidException::class)
    fun handlerPasswordInvalid(ex: PasswordInvalidException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "password invalid, count characters",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value(),
                    exception = ex.javaClass.toString(),
                    details = mutableMapOf(ex.cause.toString() to ex.message)
                )
            )

    }

    @ExceptionHandler(InvalidHashAndPasswordAdm::class)
    fun handlerAdmRegisterInvalid(ex: InvalidHashAndPasswordAdm): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "Data invalid register ADM",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value(),
                    exception = ex.javaClass.toString(),
                    details = mutableMapOf(ex.cause.toString() to ex.message)
                )
            )

    }

    @ExceptionHandler(LengthNameInvalidException::class)
    fun handlerNameInvalid(ex: LengthNameInvalidException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "name invalid, count characters",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value(),
                    exception = ex.javaClass.toString(),
                    details = mutableMapOf(ex.cause.toString() to ex.message)
                )
            )

    }

    @ExceptionHandler(LengthDescriptionInvalidException::class)
    fun handlerDescriptionInvalid(ex: LengthDescriptionInvalidException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "Description invalid, count characters",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value(),
                    exception = ex.javaClass.toString(),
                    details = mutableMapOf(ex.cause.toString() to ex.message)
                )
            )

    }

    @ExceptionHandler(UserNotExistsException::class)
    fun handlerUserNotExists(ex: UserNotExistsException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "User not exists",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value(),
                    exception = ex.javaClass.toString(),
                    details = mutableMapOf(ex.cause.toString() to ex.message)
                )
            )

    }

    @ExceptionHandler(TokenInvalidException::class)
    fun handlerTokenInvalid(ex: TokenInvalidException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "Token invalid",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value(),
                    exception = ex.javaClass.toString(),
                    details = mutableMapOf(ex.cause.toString() to ex.message)
                )
            )

    }

    @ExceptionHandler(CodeNotEqualsVerifyEmailException::class)
    fun handlerCodeNotEquals(ex: CodeNotEqualsVerifyEmailException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "codes not equals",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value(),
                    exception = ex.javaClass.toString(),
                    details = mutableMapOf(ex.cause.toString() to ex.message)
                )
            )

    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handlerFieldInvalid(ex: HttpMessageNotReadableException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "field invalid",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value(),
                    exception = ex.javaClass.toString(),
                    details = mutableMapOf(ex.cause.toString() to ex.message)
                )
            )

    }

    @ExceptionHandler(OffSetInvalidException::class)
    fun handlerOffsetInvalid(ex: OffSetInvalidException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "Offset invalid ",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value(),
                    exception = ex.javaClass.toString(),
                    details = mutableMapOf(ex.cause.toString() to ex.message)
                )
            )

    }

    @ExceptionHandler(DateCardInvalidException::class)
    fun handlerDateCardInvalid(ex: DateCardInvalidException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "Date card invalid, must be after date actual",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value(),
                    exception = ex.javaClass.toString(),
                    details = mutableMapOf(ex.cause.toString() to ex.message)
                )
            )

    }

    @ExceptionHandler(IdCardInvalidException::class)
    fun handlerIdCardInvalid(ex: IdCardInvalidException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "id cannot be null and must be bigger then 0",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value(),
                    exception = ex.javaClass.toString(),
                    details = mutableMapOf(ex.cause.toString() to ex.message)
                )
            )

    }

    @ExceptionHandler(EmailInvalidException::class)
    fun handlerEmailInvalid(ex: EmailInvalidException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "Email invalid",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value(),
                    exception = ex.javaClass.toString(),
                    details = mutableMapOf(ex.cause.toString() to ex.message)
                )
            )

    }

}