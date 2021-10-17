package com.kotlintest

import org.springframework.web.bind.annotation.*

@RestController
class BookController(val repo: BookRepository) {

    @PostMapping("/book")
    fun addBooks(@RequestBody book: Book) {
        repo.save(book)
    }

    @GetMapping("/book")
    fun getAllBooks() = repo.findAll().toList()

   @GetMapping("/book/id/{id}")
    fun getBookById(@PathVariable("id") id : Long) : Book {
        return repo.findById(id).get()
    }

    @GetMapping("/book/{title}")
    fun getBookByTitle(@PathVariable("title") title : String) = repo.findByTitle(title)
}