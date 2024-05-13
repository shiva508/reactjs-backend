package com.comrade.reactjsbackend.model.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiErrorResponse implements Serializable {
    private String message;
    private Integer status;
    private Date timestamp;
    private String error;
}
