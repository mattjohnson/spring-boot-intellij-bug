package com.example.demo

import org.jooq.DSLContext
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
  runApplication<DemoApplication>(*args)
}

@Configuration
class Config(
  private val dslContext: DSLContext,
) {

  @Bean
  fun hasDslContext(): HasDslContext {
    return HasDslContext(dslContext)
  }
}

class HasDslContext(val dslContext: DSLContext) {
  fun message(): String = "I have a dslContext! ${dslContext.javaClass}"
}

@Controller
class WillItAutowireController(val hasDslContext: HasDslContext) {
  @RequestMapping(method = [RequestMethod.GET], value = ["/will-it-autowire"], produces = [MediaType.TEXT_PLAIN_VALUE])
  fun willItAutowire(): ResponseEntity<String> {
    return ResponseEntity.ok(hasDslContext.message())
  }
}
