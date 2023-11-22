package com.fernandoalvarez.controller;

import com.fernandoalvarez.dto.TcpClientMessageDto;
import com.fernandoalvarez.service.TcpClientMessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fernando.alvarez
 */

@RestController
@RequestMapping(value = "")
public class TcpClientController {

    private final TcpClientMessageService tcpClientMessageService;

    public TcpClientController(TcpClientMessageService messageService) {
        this.tcpClientMessageService = messageService;
    }

    @GetMapping(value = "/send")
    public TcpClientMessageDto send(@RequestParam("message") String message) {
        return tcpClientMessageService.send(message);
    }


}
