package registered.project.api.entities

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import registered.project.api.enums.UserRole
import java.util.Date;


@Entity(name = "tb_user")
class User(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Long? = null,

    var testeN: String? = null,

    @get:JvmName("getName")
    @Column(unique = true)
    var email: String = "",
    @get:JvmName("getPasswordc")
    var password: String = "",

    @Enumerated(EnumType.STRING)
    var role: UserRole? = null,

    var cardsNumbers: Int? = null,
    @OneToMany
    var cards: MutableList<Card>? = null,

    var isToDelete:Boolean=false,

    @Temporal(TemporalType.TIMESTAMP)
    var createdAt:Date?=null,

    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt:Date?=null

) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return if (this.role === UserRole.ADMIN) java.util.List.of<SimpleGrantedAuthority?>(
            SimpleGrantedAuthority("ROLE_ADMIN"),
            SimpleGrantedAuthority("ROLE_USER")
        ) else java.util.List.of<SimpleGrantedAuthority?>(SimpleGrantedAuthority("ROLE_USER"))
    }


    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
       return true
    }

}