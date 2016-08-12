package br.com.casadocodigo.loja.oauth;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TokenRepository {
	
	private List<String> tokens = new ArrayList<>();
	
	public void add(String token){
		tokens.add(token);
	}
	
	public boolean hasToken(String token){
		return tokens.contains(token);
	}

}
