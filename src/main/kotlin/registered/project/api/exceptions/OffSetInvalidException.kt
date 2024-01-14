package registered.project.api.exceptions

data class OffSetInvalidException(override val message: String?) : RuntimeException(message) {
}