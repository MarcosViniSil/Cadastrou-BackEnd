package registered.project.api.repositories

import org.springframework.data.jpa.repository.JpaRepository
import registered.project.api.entities.Adm

interface AdmRepository:JpaRepository<Adm,Long> {
}