package com.kotlintest

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
@JsonSerialize
class Book(
    @Id @GeneratedValue var id: Long?,
    var isbn: String,
    var title: String
)