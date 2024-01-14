package registered.project.api.exceptions

data class TokenInvalidException(override val message: String?) : RuntimeException(message) {
}