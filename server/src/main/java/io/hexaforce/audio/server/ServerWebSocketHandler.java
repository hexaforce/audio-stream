package io.hexaforce.audio.server;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import io.hexaforce.audio.common.AbstractWebSocketHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ServerWebSocketHandler extends AbstractWebSocketHandler {

	@Autowired
	private WebSocketServerApplication.ApplicationProperties prop;

	@Override
	protected void openSession(WebSocketSession session) {
		log.info(session.getId());

	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message, String text) throws Exception {
		try {
			session.close(CloseStatus.NOT_ACCEPTABLE.withReason("Text messages not supported"));
		} catch (IOException ex) {
			// ignore
		}
	}

	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message, byte[] binary) throws Exception {
		prop.getXxx();
		System.out.println(Arrays.toString(binary));
	}

	@Override
	protected void closeSession(WebSocketSession session) {
		log.info(session.getId());
	}


}