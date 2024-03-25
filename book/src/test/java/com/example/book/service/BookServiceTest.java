package com.example.book.service;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.book.entity.BookRequest;
import com.example.book.entity.BookResponse;
import com.example.book.repo.BookRepository;
import com.example.book.service.BookService;
 
public class BookServiceTest {
 
	@Mock
	private BookRepository bookRepository;
 
	@InjectMocks
	private BookService bookService;
 
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
 
	@Test
	void create() {
		BookRequest book = new BookRequest((long) 1, "sample", "sample author", "desc", 3.5);
		BookResponse expectedresponse = new BookResponse((long) 1, "sample", "sample author", "desc", 3.5);
		when(bookRepository.addBook(book)).thenReturn(expectedresponse);
		BookResponse actualResponse = bookService.createBook(book);
		assertEquals(expectedresponse, actualResponse);
	}
 
	@Test
	void fetchbook_found() {
		long id = 1;
		BookResponse[] books = { new BookResponse((long) 1, "sample", "sample author", "desc", 3.5),
				new BookResponse((long) 2, "sample2", "sample author2", "desc2", 4.0) };
		when(bookRepository.getBookById(id)).thenReturn(books);
		BookResponse expectedresponse = books[0];
		BookResponse actualResponse = bookService.fetchBook(id);
		assertEquals(expectedresponse, actualResponse);
 
	}
 
	@Test
	void fetchbook_notfound() {
		long id = 3;
		when(bookRepository.getBookById(id)).thenReturn(null);
		BookResponse actualResponse = bookService.fetchBook(id);
		assertNull(actualResponse);
	}
}