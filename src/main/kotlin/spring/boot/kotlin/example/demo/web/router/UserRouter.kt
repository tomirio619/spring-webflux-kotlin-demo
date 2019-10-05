package spring.boot.kotlin.example.demo.web.router

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import spring.boot.kotlin.example.demo.web.handler.UserHandler

@Configuration
class UserRouter {
    @Bean
    fun userRoutes(userHandler: UserHandler) = router {
        contentType(MediaType.APPLICATION_JSON_UTF8).nest {
            GET("/users/{userID}", userHandler::getUserWithID)
            GET("") { ServerResponse.ok().build() }
        }
    }
}