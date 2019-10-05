package spring.boot.kotlin.example.demo.web.handler

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import spring.boot.kotlin.example.demo.service.UserService
import java.util.*

@Component
class UserHandler(
        private val userService: UserService
) {
    fun getUserWithID(request: ServerRequest): Mono<ServerResponse> {
        val id = try {
            UUID.fromString(request.pathVariable("userID"))
        } catch (e: IllegalArgumentException) {
            return ServerResponse.badRequest().syncBody("Invalid user id")
        }
        val user = userService.getUserByID(id).get()
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(user))
    }
}