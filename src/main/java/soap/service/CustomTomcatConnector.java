package soap.service;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

public class CustomTomcatConnector implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

	private static final Logger LOG = LoggerFactory.getLogger(CustomTomcatConnector.class);

	@SuppressWarnings("rawtypes")
	@Override
	public void customize(TomcatServletWebServerFactory factory) {
		
		factory.addConnectorCustomizers(connector-> {
			
			AbstractHttp11Protocol protoHandler = ((AbstractHttp11Protocol) connector.getProtocolHandler());
			
			//see application.yml
			protoHandler.setMaxKeepAliveRequests(100000); //keep-alive data-based upper-bound
			protoHandler.setKeepAliveTimeout(90000); //keep-alive data-based lower-bound
			protoHandler.setAcceptCount(100000); //default value queued requests
			protoHandler.setConnectionTimeout(60000);
			protoHandler.setMaxConnections(3);
			protoHandler.setMaxThreads(1);
			protoHandler.setPort(8080);

			LOG.info("[KeepAliveTimeout:{}] [MaxKeepAliveRequests:{}] [MaxConnections:{}] [MaxWorkersThreads:{}]",
					protoHandler.getKeepAliveTimeout(),protoHandler.getMaxKeepAliveRequests(),
					protoHandler.getMaxConnections(),protoHandler.getMaxThreads());
		});
	}
}
