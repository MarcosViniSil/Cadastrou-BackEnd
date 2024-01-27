package registered.project.api.exceptions

data class EmailCodeException(override val message: String?) : RuntimeException(message) {
}