package com.comrade.reactjsbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile implements Serializable {

    private String email;
    private String password;
    private String firstName;
    private String lastname;


}
