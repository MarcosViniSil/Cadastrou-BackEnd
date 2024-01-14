package registered.project.api.exceptions

data class UserNotExistsException(override val message: String?) : RuntimeException(message) {
}