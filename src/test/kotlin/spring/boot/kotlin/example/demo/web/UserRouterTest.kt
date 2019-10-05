package spring.boot.kotlin.example.demo.web

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import spring.boot.kotlin.example.demo.model.User
import spring.boot.kotlin.example.demo.service.UserService
import spring.boot.kotlin.example.demo.web.handler.UserHandler
import spring.boot.kotlin.example.demo.web.router.UserRouter
import java.util.*

@ExtendWith(SpringExtension::class, MockitoExtension::class)
@Import(UserHandler::class)
@WebFluxTest
class UserRouterTest {

    @MockBean
    private lateinit var userService: UserService

    @Autowired
    private lateinit var userHandler: UserHandler

    @Test
    fun givenExistingCustomer_whenGetCustomerByID_thenCustomerFound() {
        val expectedCustomer = User("test", "test")
        val id = expectedCustomer.userID

        `when`(userService.getUserByID(id)).thenReturn(Optional.ofNullable(expectedCustomer))

        val router = UserRouter().userRoutes(userHandler)
        val client = WebTestClient.bindToRouterFunction(router).build()

        client.get()
                .uri("/users/$id")
                .accept(MediaType.ALL)
                .exchange()
                .expectStatus().isOk
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(User::class.java)
    }
}
