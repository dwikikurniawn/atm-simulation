package com.dwiki.atmsimulation.service;

import com.dwiki.atmsimulation.model.Account;
import com.dwiki.atmsimulation.repository.AccountRepository;
import com.dwiki.atmsimulation.util.DataUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private DataUtil dataUtil;

    @Test
    @DisplayName("Search Account By Account Number And Pin Return an account when data is available")
    void whenSearchAccountByAccountNumberAndPin_willReturnAnAccount_whenDataIsAvailable() {
    String accountNumber = "112233";
    String pin = "111111";
    Account account = createAccount();

    when(accountRepository.findByAccountNumberAndPin(account.getAccountNumber(), account.getPin()))
            .thenReturn(Optional.ofNullable(account));
    when(dataUtil.accountNumberValidationFormat(accountNumber)).thenReturn(true);
    when(dataUtil.pinValidationFormat(pin)).thenReturn(true);

    Account result = accountService.searchAccountByAccountNumberAndPin(accountNumber, pin);
    assertThat(result.getAccountNumber()).isEqualTo(accountNumber);
    }

    @Test
    @DisplayName("Search Account By Account Number Return an account when data is available")
    void whenSearchAccountByAccountNumber_willReturnAnAccount_whenDataIsAvailable() {
        String accountNumber = "112233";
        Account account = createAccount();

        when(accountRepository.findById(account.getAccountNumber()))
                .thenReturn(Optional.ofNullable(account));
        when(dataUtil.accountNumberValidationFormat(accountNumber)).thenReturn(true);

        Account result = accountService.searchAccountByAccountNumber(accountNumber);
        assertThat(result.getAccountNumber()).isEqualTo(accountNumber);
    }

    @Test
    @DisplayName("Search Account By Account Number throw exception when  data is not exist")
    void whenSearchAccountByAccountNumber_willThrowAnException_whenDataIsNotExist() {
        String accountNumber = "112233";
        Optional<Account> invalidAccount = Optional.empty();

        when(dataUtil.accountNumberValidationFormat(accountNumber)).thenReturn(true);
        when(accountRepository.findById(accountNumber))
                .thenReturn(invalidAccount);

        assertThrows(EntityNotFoundException.class, () -> accountService.searchAccountByAccountNumber(accountNumber));
        verify(accountRepository, times(1)).findById(accountNumber);
    }

    @Test
    @DisplayName("Search Account By Account Number and Pin throw exception when  data is not exist")
    void whenSearchAccountByAccountNumberAndPin_willThrowAnException_whenDataIsNotExist() {
        String accountNumber = "112233";
        String pin = "111111";
        Optional<Account> account = Optional.empty();

        when(dataUtil.accountNumberValidationFormat(accountNumber)).thenReturn(true);
        when(dataUtil.pinValidationFormat(pin)).thenReturn(true);
        when(accountRepository.findByAccountNumberAndPin(accountNumber, pin))
                .thenReturn(account);

        assertThrows(EntityNotFoundException.class, () -> accountService.searchAccountByAccountNumberAndPin(accountNumber, pin));
        verify(accountRepository, times(1)).findByAccountNumberAndPin(accountNumber, pin);
    }

    @Test
    @DisplayName("Search Account By Account Number throw exception when account number nor pin invalid")
    void whenSearchAccountByAccountNumber_willThrowAnException_whenAccountNumberInvalid() {
        String accountNumber = "112233";

        when(dataUtil.accountNumberValidationFormat(accountNumber)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> accountService.searchAccountByAccountNumber(accountNumber));
        verify(dataUtil, times(1)).accountNumberValidationFormat(accountNumber);
    }

    @Test
    @DisplayName("Search Account By Account Number and Pin throw exception when account number nor pin invalid")
    void whenSearchAccountByAccountNumberAndPin_willThrowAnException_whenAccountNumberOrPinInvalid() {
        String accountNumber = "123456";
        String pin = "qwerty";

        when(dataUtil.accountNumberValidationFormat(accountNumber)).thenReturn(true);
        when(dataUtil.pinValidationFormat(pin)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> accountService.searchAccountByAccountNumberAndPin(accountNumber, pin));
        verify(dataUtil, times(1)).accountNumberValidationFormat(accountNumber);
        verify(dataUtil, times(1)).pinValidationFormat(pin);
    }



    private Account createAccount(){
        return new Account("112233", "Dwiki", "111111", 100);
    }
}