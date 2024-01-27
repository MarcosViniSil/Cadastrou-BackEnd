package registered.project.api.exceptions

data class EmailAlreadyExistsException(override val message: String?) : RuntimeException(message) {
}