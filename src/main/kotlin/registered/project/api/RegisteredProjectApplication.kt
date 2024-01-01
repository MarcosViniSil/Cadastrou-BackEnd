package registered.project.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RegisteredProjectApplication

fun main(args: Array<String>) {
	runApplication<RegisteredProjectApplication>(*args)
}
