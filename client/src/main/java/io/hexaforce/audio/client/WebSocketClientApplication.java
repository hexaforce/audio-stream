package io.hexaforce.audio.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.util.UriTemplate;

public class WebSocketClientApplication {

	final UriTemplate TEMPLATE = new UriTemplate("ws://localhost:8080/" + ClientWebSocketHandler.PATH);

	public static void main(String[] args) {
		new WebSocketClientApplication().launch(args);
	}

	private void launch(String[] args) {

		ListenableFuture<WebSocketSession> future = webSocketConnection();
		Microphone recording = attachMicrophone(future);
		new Thread(recording).start();

		new GUI().launch(recording);
	}

	private ListenableFuture<WebSocketSession> webSocketConnection() {

		Map<String, String> param = new HashMap<String, String>();
		param.put("xxx", "xxx");
		
		String uri = TEMPLATE.expand(param).toString();

		ClientWebSocketHandler handler = new ClientWebSocketHandler();
		return new StandardWebSocketClient().doHandshake(handler, uri);

	}

	private Microphone attachMicrophone(ListenableFuture<WebSocketSession> future) {
		return new Microphone(future);
	}

}
