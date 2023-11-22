package com.fernandoalvarez.endpoint;

import com.fernandoalvarez.configuration.TCPServerConfiguration;
import com.fernandoalvarez.service.TcpServerMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

import java.io.IOException;

/**
 * @author fernando.alvarez
 */

@Slf4j
@MessageEndpoint
public class TcpServerEndpoint {

    private final TcpServerMessageService messageService;

    public TcpServerEndpoint(TcpServerMessageService messageService) {
        this.messageService = messageService;
    }

    @ServiceActivator(inputChannel = TCPServerConfiguration.TCP_DEFAULT_CHANNEL)
    public byte[] process(byte[] message) throws IOException {
        String response = messageService.process(message);
        log.info("Send message to client");
        return response.getBytes();
    }

}
