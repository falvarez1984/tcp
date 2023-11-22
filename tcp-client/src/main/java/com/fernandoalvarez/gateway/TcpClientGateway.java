package com.fernandoalvarez.gateway;

import com.fernandoalvarez.configuration.TcpClientConfiguration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;

/**
 * @author fernando.alvarez
 */

@Component
@MessagingGateway(defaultRequestChannel = TcpClientConfiguration.TCP_DEFAULT_CHANNEL)
public interface TcpClientGateway {

    byte[] send(byte[] message);

}
