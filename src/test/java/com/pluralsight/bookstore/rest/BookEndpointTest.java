package com.pluralsight.bookstore.rest;

import static org.junit.Assert.*;

import java.util.Date;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.pluralsight.bookstore.model.Book;
import com.pluralsight.bookstore.model.Language;
import com.pluralsight.bookstore.repository.BookRepository;
import com.pluralsight.bookstore.util.IsbnGenerator;
import com.pluralsight.bookstore.util.NumberGenerator;
import com.pluralsight.bookstore.util.TextUtil;

@RunWith(Arquillian.class)
@RunAsClient
public class BookEndpointTest {

	@Test
	public void createBook(@ArquillianResteasyResource("api/books") WebTarget webTarget) throws Exception {
		// Test counting books
		Response response = webTarget.path("count").request().get();
		assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
		
		// Test find all
		response = webTarget.request(MediaType.APPLICATION_JSON).get();
		assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
		
		// Creates a book
		Book book = new Book("isbn", "a  title", 12.83, 123, Language.ENGLISH, new Date(), "imageURL", "description");
		response = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.entity(book, MediaType.APPLICATION_JSON));
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
	}
	
	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addClass(JAXRSConfiguration.class)
				.addClass(BookEndpoint.class)				
				.addClass(BookRepository.class)
				.addClass(Book.class)
				.addClass(Language.class)
				.addClass(TextUtil.class)
				.addClass(NumberGenerator.class)
				.addClass(IsbnGenerator.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsManifestResource("META-INF/test-persistence.xml", "persistence.xml");
	}
}
