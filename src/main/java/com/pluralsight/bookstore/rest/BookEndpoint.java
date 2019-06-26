package com.pluralsight.bookstore.rest;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.pluralsight.bookstore.model.Book;
import com.pluralsight.bookstore.repository.BookRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/books")
@Api("Book")
public class BookEndpoint {

	@Inject
	private BookRepository bookRepository;
	
	@GET
	@Path("/{id : \\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns a book given an id", response = Book.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Book found"),
		@ApiResponse(code = 400, message = "Invalid input. Id cannot be lower than 1"),
		@ApiResponse(code = 404, message = "Book not found")
	})
	public Response getBook(@PathParam("id") @Min(1) Long id) {
		Book book = bookRepository.find(id);
		
		if(book == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		return Response.ok(book).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation("Creates a book given a JSon Book representation")
    @ApiResponses({
            @ApiResponse(code = 201, message = "The book is created"),
            @ApiResponse(code = 415, message = "Format is not JSon")
    })
	public Response createBook(@ApiParam(value = "Book to be created", required = true) Book book, @Context UriInfo uriInfo) {
		book = bookRepository.create(book);
		URI createdURI = uriInfo.getAbsolutePathBuilder().path(book.getId().toString()).build();
		return Response.created(createdURI).build();
	}
	
	@DELETE
	@Path("/{id : \\d+}")
	@ApiOperation("Deletes a book given an id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Book has been deleted"),
            @ApiResponse(code = 400, message = "Invalid input. Id cannot be lower than 1"),
            @ApiResponse(code = 500, message = "Book not found")
    })
	public Response deleteBook(@PathParam("id") @Min(1) Long id) {
		bookRepository.delete(id);
		return Response.noContent().build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns all the books", response = Book.class, responseContainer = "List")
    @ApiResponses( {
            @ApiResponse(code = 200, message = "Books found"),
            @ApiResponse(code = 204, message = "No books found"),
    })
	public Response getBooks() {
		List<Book> books = bookRepository.findAll();
		
		if(books.size() == 0) {
			return Response.noContent().build();
		}
		
		return Response.ok(books).build();
	}
	
	@GET
	@Path("/count")
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Returns the number of books", response = Long.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Number of books found"),
            @ApiResponse(code = 204, message = "No books found"),
    })
	public Response countBooks() {
		Long nbOfBooks = bookRepository.countAll();
		
		if(nbOfBooks == 0) {
			return Response.noContent().build();
		}
		
		return Response.ok(nbOfBooks).build();
	}
}
