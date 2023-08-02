package com.dwiki.atmsimulation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginDto {

    private String accountNumber;
    private String pin;
}
