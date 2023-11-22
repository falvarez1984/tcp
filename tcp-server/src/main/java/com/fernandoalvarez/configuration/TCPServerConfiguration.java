package com.fernandoalvarez.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNioServerConnectionFactory;
import org.springframework.messaging.MessageChannel;

/**
 * @author fernando.alvarez
 */

@Configuration
public class TCPServerConfiguration {

    @Value("${tcp.server.port}")
    private int port;

    public static final String TCP_DEFAULT_CHANNEL = "tcp-channel-sample";


    //serverConnectionFactory: configuración de conexión al servidor
    @Bean
    public AbstractServerConnectionFactory serverConnectionFactory() {
        TcpNioServerConnectionFactory tcpNioServerConnectionFactory = new TcpNioServerConnectionFactory(port);
        tcpNioServerConnectionFactory.setUsingDirectBuffers(true);
        return tcpNioServerConnectionFactory;
    }


    //messageChannel: para entregar los mensajes directamente a los clientes en el mismo hilo de ejecución del remitente
    @Bean(name = TCP_DEFAULT_CHANNEL)
    public MessageChannel messageChannel() {
        return new DirectChannel();
    }


    //tcpInboundGateway: recibe mensajes de clientes utilizando la conexión configurada en "serverConnectionFactory"
    @Bean
    public TcpInboundGateway tcpInboundGateway(AbstractServerConnectionFactory serverConnectionFactory,
                                               @Qualifier(value = "tcp-channel-sample") MessageChannel messageChannel) {
        TcpInboundGateway gateway = new TcpInboundGateway();
        gateway.setConnectionFactory(serverConnectionFactory);
        gateway.setRequestChannel(messageChannel);
        return gateway;
    }

}
