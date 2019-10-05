package spring.boot.kotlin.example.demo.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class User(var username : String, var password: String) {

    @Id
    val userID = UUID.randomUUID()
}