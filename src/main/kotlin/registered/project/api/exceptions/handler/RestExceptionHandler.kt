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
                    title = "senha invalida, quantidade de caracteres deve ser entre 8 e 20",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value()
                )
            )

    }

    @ExceptionHandler(InvalidHashAndPasswordAdm::class)
    fun handlerAdmRegisterInvalid(ex: InvalidHashAndPasswordAdm): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "dados invalidos para registrar um ADM",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value()
                )
            )

    }

    @ExceptionHandler(LengthNameInvalidException::class)
    fun handlerNameInvalid(ex: LengthNameInvalidException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "tamanho de nome invalido",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value()
                )
            )

    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handlerUserAlreadyExists(ex: UserAlreadyExistsException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "usuario já existe",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value()
                )
            )

    }

    @ExceptionHandler(LengthDescriptionInvalidException::class)
    fun handlerDescriptionInvalid(ex: LengthDescriptionInvalidException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "descrição invalida, tamanho do texto",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value()
                )
            )

    }

    @ExceptionHandler(UserNotExistsException::class)
    fun handlerUserNotExists(ex: UserNotExistsException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "usuario nao existe",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value()
                )
            )

    }

    @ExceptionHandler(TokenInvalidException::class)
    fun handlerTokenInvalid(ex: TokenInvalidException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "Token invalido",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value()
                )
            )

    }

    @ExceptionHandler(CodeNotEqualsVerifyEmailException::class)
    fun handlerCodeNotEquals(ex: CodeNotEqualsVerifyEmailException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "os códigos informados não são iguais",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value()
                )
            )

    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handlerFieldInvalid(ex: HttpMessageNotReadableException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "campo invalido",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value()
                )
            )

    }

    @ExceptionHandler(OffSetInvalidException::class)
    fun handlerOffsetInvalid(ex: OffSetInvalidException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "deslocamento invalido ",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value()
                )
            )

    }

    @ExceptionHandler(DateCardInvalidException::class)
    fun handlerDateCardInvalid(ex: DateCardInvalidException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "data de card invalida, a data deve ser após a data atual",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value()
                )
            )

    }

    @ExceptionHandler(IdCardInvalidException::class)
    fun handlerIdCardInvalid(ex: IdCardInvalidException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "id não pode ser nulo e deve ser maior que 0",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value()
                )
            )

    }

    @ExceptionHandler(EmailInvalidException::class)
    fun handlerEmailInvalid(ex: EmailInvalidException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "email invalido",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value()
                )
            )

    }

}