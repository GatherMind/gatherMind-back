//package woongjin.gatherMind.config;
//
//import org.apache.catalina.connector.Connector;
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.boot.web.server.WebServerFactoryCustomizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class HttpToHttpsRedirectConfig {
//    @Bean
//    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> redirectToHttps() {
//        return factory -> factory.addAdditionalTomcatConnectors(createHttpConnector());
//    }
//
//    private Connector createHttpConnector() {
//        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//        connector.setScheme("http");
//        connector.setPort(8080);
//        connector.setSecure(false);
//        connector.setRedirectPort(8443); // HTTPS 포트로 리다이렉트
//        return connector;
//    }
//}
