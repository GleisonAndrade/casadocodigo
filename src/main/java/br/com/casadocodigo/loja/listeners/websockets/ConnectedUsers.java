package br.com.casadocodigo.loja.listeners.websockets;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ApplicationScoped
public class ConnectedUsers {
	private Set<Session> users = new HashSet<>();
	private Logger logger = LoggerFactory.getLogger(ConnectedUsers.class);
	
	public boolean add(Session remoteUser) {
		System.out.println("chegou um "+remoteUser.getId());
		return users.add(remoteUser);
	}
	public boolean remove(Session remoteUser) {
		System.out.println("saiu um "+remoteUser.getId());
		return users.remove(remoteUser);
	}
	public Set<Session> getUsers() {
		return users;
	}
	
	public void send(String json) {
		System.out.println("Enviando Mensagens para "+users.size()+" clientes\n"+ json);
		for (Session user : users) {
			if (user.isOpen()) {
				try {
					user.getBasicRemote().sendText(json);
					System.out.println("Mensagem enviada a " + user.getId());
				} catch (IOException e) {
					System.out.println("Erro ao enviar");
					throw new RuntimeException(e);
				}
			} else {
				System.out.println("Usuario nao conectado");
			}
		}
	}
	
	
}
