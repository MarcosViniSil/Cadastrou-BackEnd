package registered.project.api.exceptions

data class PasswordInvalidException(override val message: String?) : RuntimeException(message) {
}