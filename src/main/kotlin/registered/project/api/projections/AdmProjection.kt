package registered.project.api.projections

import registered.project.api.dtos.UserDTO
import registered.project.api.dtos.UsersToDeleteDTO
import registered.project.api.entities.User

interface AdmProjection {

    fun deleteUser(id: Long?)

    fun listUsers(offset: Int): MutableList<UserDTO>?

    fun alertAdmToDeleteUser(user: User)

    fun listUsersToDelete(offset:Int):MutableList<UsersToDeleteDTO>?
}