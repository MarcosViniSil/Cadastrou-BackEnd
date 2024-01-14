package registered.project.api.exceptions

data class LengthNameInvalidException(override val message: String?) : RuntimeException(message) {
}