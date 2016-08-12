package br.com.casadocodigo.loja.resources;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;

import br.com.casadocodigo.loja.oauth.TokenRepository;
import br.com.casadocodigo.loja.resources.annotations.OAuthRequest;

@Provider
@OAuthRequest
public class OAuthFilter implements ContainerRequestFilter {

	@Inject 
	private HttpServletRequest request;
	@Inject
	private TokenRepository tokenDAO;
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		try {
			OAuthAccessResourceRequest oAuthToken = new OAuthAccessResourceRequest(request);
			String token = oAuthToken.getAccessToken();
			if (!tokenDAO.hasToken(token)) {
				requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
			}
		} catch (Exception e) {
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}

}
