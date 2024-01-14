package registered.project.api.exceptions

data class IdCardInvalidException(override val message: String?) : RuntimeException(message) {
}