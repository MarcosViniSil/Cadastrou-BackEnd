package registered.project.api.repositories

import org.springframework.data.jpa.repository.JpaRepository
import registered.project.api.entities.User

interface UserRepository:JpaRepository<User,Long> {
}