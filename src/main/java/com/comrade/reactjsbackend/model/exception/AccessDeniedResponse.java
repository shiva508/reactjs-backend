package com.comrade.reactjsbackend.model.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessDeniedResponse {
    private Date timeStamp;
    private String message;
}
