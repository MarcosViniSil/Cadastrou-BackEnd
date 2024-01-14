package registered.project.api.exceptions

data class InvalidHashAndPasswordAdm(override val message: String?) : RuntimeException(message) {
}