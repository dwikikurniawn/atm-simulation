package com.dwiki.atmsimulation.service;

import com.dwiki.atmsimulation.model.Account;
import com.dwiki.atmsimulation.model.Transaction;
import com.dwiki.atmsimulation.repository.AccountRepository;
import com.dwiki.atmsimulation.repository.TransactionRepository;
import com.dwiki.atmsimulation.util.FileUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountServiceImpl accountService;

    @Spy
    private FileUtil fileUtil;

    @Test
    @DisplayName("Get Top 10 Last Transaction")
    void whenGetTransactionHistory_willReturnTop10TransactionHistory_whenDataIsAvailable() {
        String accountNumber = "112233";
        String transactionType = "WITHDRAW";
        String referenceNumber = "-";
        Transaction transaction = createTransaction(accountNumber, transactionType, 50,"-", referenceNumber);
        List<Transaction> transactions = List.of(transaction);
        Account account = createAccount(accountNumber);

        when(accountService.searchAccountByAccountNumber(accountNumber)).thenReturn(account);
        when(transactionRepository.findTop10BySourceAccountNumberOrRecipientAccountNumberOrderByTimeDesc
                (account.getAccountNumber(), account.getAccountNumber())).thenReturn(transactions);
        List<Transaction> result = transactionService.lastTransaction(accountNumber);
        assertThat(result.size()).isEqualTo(transactions.size());
    }

    @Test
    @DisplayName("Transfer process succeed")
    void whenDoTransferTransactionProcess_increaseBalanceDestinationAccount_whenTransferSucceed() {
        String sourceAccountNumber = "112233";
        String destinationAccountNumber = "112244";
        String referenceNumber = "1234567890";
        String transactionType = "TRANSFER";
        int amount = 50;
        Transaction transaction = createTransaction(sourceAccountNumber, transactionType, amount, destinationAccountNumber, referenceNumber);
        Account sourceAccount = createAccount(sourceAccountNumber);
        Account destinationAccount = createAccount(destinationAccountNumber);

        when(accountService.searchAccountByAccountNumber(sourceAccountNumber))
                .thenReturn(sourceAccount);
        when(accountService.searchAccountByAccountNumber(destinationAccountNumber))
                .thenReturn(destinationAccount);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(accountRepository.save(any(Account.class))).thenReturn(sourceAccount);

        transactionService.transferTransactionProcess(sourceAccountNumber, destinationAccountNumber, amount, referenceNumber);

        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(accountRepository, times(2)).save(any(Account.class));
        assertThat(sourceAccount.getBalance()).isEqualTo(50);
        assertThat(destinationAccount.getBalance()).isEqualTo(150);
    }

    @Test
    @DisplayName("Withdraw process succeed")
    void whenDoWithdrawTransactionProcess_increaseBalanceDestinationAccount_whenTransferSucceed() {
        String sourceAccountNumber = "112233";
        String destinationAccountNumber = "-";
        String referenceNumber = "-";
        String transactionType = "WITHDRAW";
        int amount = 50;
        Transaction transaction = createTransaction(sourceAccountNumber, transactionType, amount, destinationAccountNumber, referenceNumber);
        Account sourceAccount = createAccount(sourceAccountNumber);

        when(accountService.searchAccountByAccountNumber(sourceAccountNumber))
                .thenReturn(sourceAccount);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(accountRepository.save(any(Account.class))).thenReturn(sourceAccount);

        transactionService.withDrawTransactionProcess(amount, sourceAccountNumber);

        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(accountRepository, times(1)).save(any(Account.class));
        assertThat(sourceAccount.getBalance()).isEqualTo(50);
    }

    private Transaction createTransaction(String sourceAccountNumber, String type, int amount,  String destinationAccountNumber, String referenceNumber){
        return new Transaction(sourceAccountNumber,type,amount,destinationAccountNumber, referenceNumber);
    }

    private Account createAccount(String accountNumber){
        return new Account(accountNumber, "Dwiki", "111111", 100);
    }
}