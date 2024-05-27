package com.comrade.reactjsbackend.model;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginRequest implements Serializable {

    private String email;
    private String password;
}
