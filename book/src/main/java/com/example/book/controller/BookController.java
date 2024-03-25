package com.example.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.book.entity.BookRequest;
import com.example.book.entity.BookResponse;
import com.example.book.service.BookService;

@RestController
@RequestMapping("/library")

public class BookController {

	@Autowired
	private BookService bookService;

	@PostMapping("/addbooks")
	public BookResponse postMethod(@RequestBody BookRequest bookRequest) {
	 BookResponse book = bookService.createBook(bookRequest);
	 book.setId(bookRequest.getId());
	 book.setAuthor(bookRequest.getAuthor());
	 book.setDescription(bookRequest.getDescription());
	 book.setName(bookRequest.getName());
	 book.setRating(bookRequest.getRating());
	   return book;
	}

	
	
	@GetMapping("/books/{id}")
	 public Object getMethod(@PathVariable Long id) {
        BookResponse response = bookService.fetchBook(id);
        if (response != null) {
            return response; 
        } else {
         return "Book with ID " + id + " not exist !"; 
        }
    }

}
