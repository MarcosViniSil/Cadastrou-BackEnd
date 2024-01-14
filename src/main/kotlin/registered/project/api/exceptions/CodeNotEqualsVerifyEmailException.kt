package registered.project.api.exceptions

data class CodeNotEqualsVerifyEmailException(override val message: String?) : RuntimeException(message) {
}