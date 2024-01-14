package registered.project.api.exceptions

data class EmailInvalidException(override val message: String?) : RuntimeException(message) {
}