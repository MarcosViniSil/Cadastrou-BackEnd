package registered.project.api.exceptions

data class LengthDescriptionInvalidException(override val message: String?) : RuntimeException(message) {
}