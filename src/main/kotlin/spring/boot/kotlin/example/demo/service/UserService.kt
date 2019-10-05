package spring.boot.kotlin.example.demo.service

import org.springframework.stereotype.Service
import spring.boot.kotlin.example.demo.model.User
import spring.boot.kotlin.example.demo.repository.UserRepository
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class UserService(
        private val userRepository: UserRepository
) {
    fun getUserByID(id: UUID): Optional<User> {
        return Optional.of(
                try {
                    userRepository.getOne(id)
                } catch (e: EntityNotFoundException) {
                    User("test", "test")
                }
        )
    }

    fun addUser(user: User) {
        userRepository.save(user)
    }
}