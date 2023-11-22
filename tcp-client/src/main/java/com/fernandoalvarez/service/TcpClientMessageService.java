package com.fernandoalvarez.service;

import com.fernandoalvarez.dto.TcpClientMessageDto;
import com.fernandoalvarez.gateway.TcpClientGateway;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author fernando.alvarez
 */

@Slf4j
@Service
public class TcpClientMessageService {

    private final TcpClientGateway clientGateway;

    public TcpClientMessageService(TcpClientGateway tcpClientGateway) {
        this.clientGateway = tcpClientGateway;
    }

    @SneakyThrows
    public TcpClientMessageDto send(String message) {
        TcpClientMessageDto messageRequest = TcpClientMessageDto.builder()
                .message(message)
                .sender("tcp-client")
                .timestamp(LocalDateTime.now().toString())
                .build();

        Jackson2JsonObjectMapper mapper = new Jackson2JsonObjectMapper();
        String messageRequestStr = mapper.toJson(messageRequest);

        log.info("Sending message: {}", messageRequestStr);
        byte[] responseByte = clientGateway.send(messageRequestStr.getBytes());
        TcpClientMessageDto response = mapper.fromJson(new String(responseByte), TcpClientMessageDto.class);
        log.info("Receive message: {}, from: {}, at: {}", response.getMessage(), response.getSender(), response.getTimestamp());
        return response;
    }


}
