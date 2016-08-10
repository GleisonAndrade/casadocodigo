package br.com.casadocodigo.loja.listeners.websockets;

import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/channel/sales")
public class SalesEndpoint {
	@Inject
	private ConnectedUsers users;
	
	@OnOpen
	public void onConnect(Session session) {
		users.add(session);
	}
	
	@OnClose
	public void onDisconnect(Session session) {
		users.remove(session);
	}
	
	@OnMessage
	public void onMessage(String message) {
		System.out.println(message);
	}
}
