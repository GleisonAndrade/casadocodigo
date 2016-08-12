package br.com.casadocodigo.loja.resources;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import br.com.casadocodigo.loja.resources.annotations.OAuthRequest;
import br.com.casadocodigo.loja.oauth.TokenRepository;

@OAuthRequest
@Provider
public class OAuthFilter implements ContainerRequestFilter{

	@Inject
	private HttpServletRequest request;
	
	@Inject
	private TokenRepository tokens;
	
	@Override
	public void filter(ContainerRequestContext context) throws IOException {
		try {
			OAuthAccessResourceRequest accessResourceRequest = new OAuthAccessResourceRequest(request);
			String token = accessResourceRequest.getAccessToken();
			
			if (!tokens.hasToken(token)){
				context.abortWith(Response.status(Status.UNAUTHORIZED).build());
			}
			
		} catch (OAuthSystemException | OAuthProblemException e) {
			context.abortWith(Response.status(Status.UNAUTHORIZED).build());
			e.printStackTrace();
		}
	}

}
