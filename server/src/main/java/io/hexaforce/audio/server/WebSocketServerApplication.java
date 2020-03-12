package io.hexaforce.audio.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import io.hexaforce.audio.common.CommonLibrary;
import lombok.Data;

@SpringBootApplication
@EnableAutoConfiguration
public class WebSocketServerApplication {

	@Autowired
	private ServerWebSocketHandler webSocketServerHandler;

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(WebSocketServerApplication.class)//
				.sources(CommonLibrary.class)// Multi-module Spring Application
				.web(WebApplicationType.SERVLET)//
				.listeners(new ApplicationPidFileWriter())//
				.run(args);
		context.getId();
		context.getStartupDate();
	}

	@Configuration
	@EnableWebSocket
	public class WebSocketConfig implements WebSocketConfigurer {
		
		@Override
		public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
			registry.addHandler(webSocketServerHandler, ServerWebSocketHandler.PATH);
		}
		
	}
	
	@Data
	@Component
	@ConfigurationProperties("server.property")
	public class ApplicationProperties {

		private String xxx;
		private int yyy;

	}

}
