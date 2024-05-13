package tests.entities;


import entities.Account;
import org.junit.jupiter.api.Test;
import tests.factory.AccountFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTests {

    @Test
    public void deposit_should_increase_balance_when_positive_amount() {
        // Arrange
        final double amount = 200.0;
        final double expectedValue = 196.0;
        Account acc = AccountFactory.createEmptyAccount();

        // Act
        acc.deposit(amount);

        // Assert
        assertEquals(expectedValue, acc.getBalance());
    }

    @Test
    public void deposit_should_do_nothing_when_negative_amount() {
        // Arrange
        final double expectedValue = 100.0;
        Account acc = AccountFactory.createAccount(expectedValue);
        final double amount = -200.0;

        // Act
        acc.deposit(amount);

        // Assert
        assertEquals(expectedValue, acc.getBalance());
    }

    @Test
    public void full_withdraw_should_clear_balance_and_return_full_balance() {
        // Arrange
        final double expectedValue = 0.0;
        final double initialBalance = 800.0;
        Account acc = AccountFactory.createAccount(initialBalance);

        // Act
        double result = acc.fullWithdraw();

        // Assert
        assertEquals(expectedValue, acc.getBalance());
        assertEquals(result, initialBalance);
    }

    @Test
    public void withdraw_should_decrease_balance_when_sufficient_balance() {
        // Arrange
        final double expectedValue = 300.0;
        final double initialBalance = 800.0;
        Account acc = AccountFactory.createAccount(initialBalance);
        final double amount = 500.0;

        // Act
        acc.withdraw(amount);

        // Assert
        assertEquals(expectedValue, acc.getBalance());
    }

    @Test
    public void withdraw_should_throw_exception_when_insufficient_balance() {
        // Arrange
        final double initialBalance = 100.0;
        Account acc = AccountFactory.createAccount(initialBalance);
        final double amount = 200.0;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            acc.withdraw(amount);
        });

    }
}
