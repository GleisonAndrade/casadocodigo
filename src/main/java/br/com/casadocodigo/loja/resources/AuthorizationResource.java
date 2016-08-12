package br.com.casadocodigo.loja.resources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import br.com.casadocodigo.loja.oauth.TokenRepository;

@Path("auth")
public class AuthorizationResource {

	private final String CLIENT_ID = "1";
	private final String CLIENT_SECRET = "1";

	TokenRepository tokenRepository = new TokenRepository();

	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/ropcg")
	public Response ropcg(@Context HttpServletRequest request) {
		OAuthTokenRequest tokenRequest;
		try {
			tokenRequest = new OAuthTokenRequest(request);

			String clientId = tokenRequest.getClientId();
			String clientSecret = tokenRequest.getClientSecret();
			String username = tokenRequest.getUsername();
			String password = tokenRequest.getPassword();

			if (validate(clientId, clientSecret, username, password, request)) {
				OAuthIssuer issuer = new OAuthIssuerImpl(new MD5Generator());
				String token = issuer.accessToken();
				tokenRepository.add(token);

				String json = OAuthASResponse
						.tokenResponse(HttpServletResponse.SC_OK)
						.setAccessToken(token).setTokenType("bearer")
						.buildJSONMessage().getBody();

				return Response.ok(json).build();
			}
			return Response.status(Status.UNAUTHORIZED).build();
		} catch (OAuthSystemException | OAuthProblemException e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	private boolean validate(String clientId, String clientSecret,
			String username, String password, HttpServletRequest request) {

		return CLIENT_ID.equals(clientId) && CLIENT_SECRET.equals(clientSecret)
				&& validate(username, password, request);
	}

	private boolean validate(String username, String password,
			HttpServletRequest request) {

		try {
			request.login(username, password);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
