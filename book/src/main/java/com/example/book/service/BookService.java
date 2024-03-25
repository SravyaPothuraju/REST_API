package com.example.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.book.entity.BookRequest;
import com.example.book.entity.BookResponse;
import com.example.book.repo.BookRepository;

@Service
public class BookService {

	@Autowired
	private final BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public BookResponse createBook(BookRequest bookRequest) {
		BookResponse book = bookRepository.addBook(bookRequest);
		return book;
		}
	
	

	public BookResponse fetchBook(Long id) {
		BookResponse[] response = bookRepository.getBookById(id);
		 if (response != null) {
	            for (BookResponse book : response) {
	                if (book.getId().equals(id)) {
	                    return book; 
	                }
	            }
	        
		      }
	    return null; 
		}
	}

