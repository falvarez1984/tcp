package com.fernandoalvarez.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author fernando.alvarez
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TcpClientMessageDto implements Serializable {

    private String message;
    private String sender;
    private String timestamp;

}
