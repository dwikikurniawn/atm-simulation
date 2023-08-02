package com.dwiki.atmsimulation.advice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private int status;
    private Date timestamp;
    private String message;
    private String path;

}
