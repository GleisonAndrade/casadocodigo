package br.com.casadocodigo.loja.resources;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;

import br.com.casadocodigo.loja.daos.AuthorDAO;
import br.com.casadocodigo.loja.models.Author;
import br.com.casadocodigo.loja.oauth.TokenRepository;

//curl -X GET http://localhost:8080/casadocodigo/services/author
//curl -X POST -H "Content-type: application/json" -d '{"name":"Maria Paula"}' http://localhost:8080/casadocodigo/services/author

@Path("author")
@Transactional
public class AuthorResource {
	
	@Inject
	private AuthorDAO authorDAO;
	
	@Inject
	private TokenRepository tokenRepository;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Author> readAll(){
		return authorDAO.list();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response read( @PathParam("id") Integer id,
			@Context HttpServletRequest request) {
		
		try {
			OAuthAccessResourceRequest authRequest = new OAuthAccessResourceRequest(request);
			String token = authRequest.getAccessToken();
			authRequest = new OAuthAccessResourceRequest(request);
			if (tokenRepository.hasToken(token)){
				Author author = authorDAO.findById(id);
				
				return Response.ok(author).build();
			}
		} catch (OAuthSystemException | OAuthProblemException e) {
			return  Response.status(Status.UNAUTHORIZED).build();
		}
		
		return  Response.status(Status.UNAUTHORIZED).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(Author author) throws URISyntaxException{
		authorDAO.save(author);
		return Response.created(new URI("/author/"+author.getId()))
				.entity(author)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(Author author){
		authorDAO.update(author);
		return Response.ok(author).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Integer id){
		Author author = authorDAO.findById(id);
		authorDAO.remove(author);
		
		return Response.accepted().build();
	}
}
