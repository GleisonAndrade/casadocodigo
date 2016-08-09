package br.com.casadocodigo.loja.client;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.casadocodigo.loja.models.Book;

public class BookList {
	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();
		Response response = client.target("http://localhost:8080/casadocodigo/services/books")
				.request(MediaType.APPLICATION_JSON).get();
		
		String json = (response.readEntity(String.class));
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			List<Book> books = objectMapper.readValue(json, new TypeReference<List<Book>>() {});
			books.forEach(
				(Book book) -> 
					System.out.println("Livro "+book.getId()+":\n - Titulo: " + book.getTitle()
							+"\n - Autor: " + book.getAuthors().get(0).getName())
			);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
