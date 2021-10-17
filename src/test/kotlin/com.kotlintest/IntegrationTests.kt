package com.kotlintest

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IntegrationTest(@Autowired val restTemplate: TestRestTemplate) {

    @BeforeAll
    fun setup() {
        println(">> Startup")
    }

    @Test
    fun `Testing book endpoint`() {
        val entity = restTemplate.getForEntity<String>("/book")
        Assertions.assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `Testing insertion`() {
        val postForEntity = restTemplate.postForEntity<String>(
            "/book",
            Book(id = null, isbn = "1289123", title = "Java Book"),
            Book::class.java
        )

        Assertions.assertThat(postForEntity.statusCode).isEqualTo(HttpStatus.OK)

        val getForEntity = restTemplate.getForEntity<String>("/book")
        Assertions.assertThat(getForEntity.statusCode).isEqualTo(HttpStatus.OK)

        val mapper = jacksonObjectMapper()
        val listValue = mapper.readValue<List<Book>>(getForEntity.body.toString())

        Assertions.assertThat(listValue.size).isEqualTo(1)
        Assertions.assertThat(listValue.get(0).id).isEqualTo(1)
        Assertions.assertThat(listValue.get(0).title).isEqualTo("Java Book")
        Assertions.assertThat(listValue.get(0).isbn).isEqualTo("1289123")
    }
}