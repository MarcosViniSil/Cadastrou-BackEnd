package registered.project.api.exceptions

data class UserAlreadyExistsException(override val message: String?) : RuntimeException(message) {
}