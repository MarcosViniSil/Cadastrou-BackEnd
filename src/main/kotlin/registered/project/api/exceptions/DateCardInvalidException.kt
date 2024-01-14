package registered.project.api.exceptions

data class DateCardInvalidException(override val message: String?) : RuntimeException(message) {
}