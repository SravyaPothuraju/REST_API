 package com.example.book.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.book.entity.BookRequest;
import com.example.book.entity.BookResponse;
import com.example.book.service.BookService;
 
public class ControllerTest {
 
    @Mock
    private BookService bookService;
 
    @InjectMocks
    private BookController controllerClass;
 
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    void post_create() {
    	BookRequest book =new BookRequest((long)1, "sample", "author", "desc",3.5);
    	BookResponse expectedresponse = new BookResponse((long)1, "sample", "author", "desc",3.5);
    	when(bookService.createBook(book)).thenReturn(expectedresponse);
    	BookResponse actualResponse = controllerClass.postMethod(book);
    	assertEquals(expectedresponse,actualResponse);
    }
    
    @Test
    void get_book_found() {
    	long id=1;
    	BookResponse expectedresponse = new BookResponse((long)1, "sample", "author", "desc",3.5);
    	when(bookService.fetchBook(id)).thenReturn(expectedresponse);
        Object actualResponse = controllerClass.getMethod(id);
    	assertEquals(expectedresponse,actualResponse);
 
    }
    @Test
    void get_book_notfound() {
    	long id=2;
    	when(bookService.fetchBook(id)).thenReturn(null);
        Object actualResponse = controllerClass.getMethod(id);
    	assertEquals("Book with ID " + id + " not exist !",actualResponse);
 
    }
}

