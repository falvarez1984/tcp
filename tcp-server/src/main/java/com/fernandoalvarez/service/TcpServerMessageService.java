package com.fernandoalvarez.service;

import com.fernandoalvarez.dto.TcpServerMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

/**
 * @author fernando.alvarez
 */

@Slf4j
@Service
public class TcpServerMessageService {

    @Value("${tcp.server.port}")
    private int serverPort;

    public String process(byte[] message) throws IOException {
        String messageJson = new String(message);
        log.info("Receive message as JSON: {}", messageJson);

        Jackson2JsonObjectMapper mapper = new Jackson2JsonObjectMapper();
        TcpServerMessageDto messageDTO = mapper.fromJson(messageJson, TcpServerMessageDto.class);
        log.info("Message: {}, from: {}, at: {}", messageDTO.getMessage(), messageDTO.getSender(), messageDTO.getTimestamp());


        TcpServerMessageDto response = TcpServerMessageDto.builder()
                .message("Message received!!! Answering...")
                .timestamp(LocalDateTime.now().toString())
                .sender(getServerIpAddress() + ":" + serverPort)
                .build();
        return mapper.toJson(response);
    }

    private String getServerIpAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("Error getting server IP address: {}", e.getMessage());
            return "Error getting server IP address";
        }
    }

}
