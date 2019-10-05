package spring.boot.kotlin.example.demo.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import spring.boot.kotlin.example.demo.model.User
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, UUID>{
}