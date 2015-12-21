package ua.pti.myatm;

import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.*;

/**
 *
 * @author bohdan
 */
public class ATMTest {


     @Test(expected = IllegalArgumentException.class)
    public void testATMWithLowerThanZeroStartBalance() {
        System.out.println("ATM with balance < 0");
        ATM instance = new ATM(-22.0);
    }
    
 
    
    @Test
    public void testGetMoneyInATM() {
        System.out.println("get Money");
        ATM instance = new ATM(30.0);
        double expResult = 30.0;
        double result = instance.getMoneyInATM();
        assertEquals(expResult, result, 0.0);
        
    }

    
    @Test(expected = ATM.ATMisEmptyException.class)
    public void testValidateCardEmptyATM() {
        System.out.println("Without inserted card");
        ATM instance = new ATM(30.0);
        boolean expResult = false;
        boolean result = instance.validateCard(null, 1234);
        assertEquals(expResult, result);
    }
  

    @Test
    public void testValidateBlockedCardValidPin() {
        System.out.println("with blocked card");
        Card card = mock(Card.class);
        int pinCode = 3210;
        when(card.checkPin(pinCode)).thenReturn(true);
        when(card.isBlocked()).thenReturn(true);
        ATM instance = new ATM(30.0);
        boolean expResult = false;
        boolean result = instance.validateCard(card, pinCode);
        assertEquals(expResult, result);
    }
    
      @Test
    public void testValidateCardUnblockedCardValidPin() {
        System.out.println("validateCard no block, true pin");
        Card card = mock(Card.class);
        int pinCode = 3210;
        when(card.checkPin(pinCode)).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        ATM instance = new ATM(30.0);
        boolean expResult = true;
        boolean result = instance.validateCard(card, pinCode);
        assertEquals(expResult, result);
    }

    @Test
    public void testValidateCardUnblockedCardInvalidPin() {
        System.out.println("validateCard with wrong pin");
        Card card = mock(Card.class);
        when(card.checkPin(anyInt())).thenReturn(false);
        when(card.isBlocked()).thenReturn(false);
        ATM instance = new ATM(30.0);
        boolean expResult = false;
        boolean result = instance.validateCard(card, 3210);
        assertEquals(expResult, result);
    }


  

    @Test
    public void testCheckBalance() {
        System.out.println("check balance");
        Account account = mock(Account.class);
        

        double accountBalance = 1000.0;
        when(account.getBalance()).thenReturn(accountBalance);
        Card card = mock(Card.class);
        InOrder inOrder = inOrder(card);
        when(card.getAccount()).thenReturn(account);
        when(card.checkPin(anyInt())).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);

        double startBalanceInATM = 100;
        ATM instance = new ATM(startBalanceInATM);
        instance.validateCard(card,3210);
        double result = instance.checkBalance();
        inOrder.verify(card).getAccount();
        assertEquals(accountBalance, result, 0.0);
    }

    @Test(expected=ATM.ATMisEmptyException.class)
    public void testCheckBalanceWithoutCard(){
    	System.out.println("check balance with no card inserted");
    	double ATMBalance =100.0;
    	ATM instance = new ATM(ATMBalance);
    	double result = instance.checkBalance();
    }

    @Test
    public void testGetCash() {
        System.out.println("getCash");
        double accountBalanceBefore = 1000.0;
        double amountForWithdraw = 100.0;
        double accountBalanceAfter = 900.0;
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(accountBalanceBefore).thenReturn(accountBalanceAfter);
        when(account.withdraw(amountForWithdraw)).thenReturn(amountForWithdraw);
        Card card = mock(Card.class);
        when(card.getAccount()).thenReturn(account);
        when(card.checkPin(anyInt())).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        ATM instance = new ATM(1000.0);
        instance.validateCard(card,3210);
        double expResult = accountBalanceAfter;
        double result = instance.getCash(amountForWithdraw);
        assertEquals(expResult, result, 0.0);
    }

    @Test(expected = ATM.ATMisEmptyException.class)
    public void textGetCashWitoutCard() {
        System.out.println("getCash without inserted(valid) card");
        double ATMBalance = 200.0;
        ATM instance = new ATM(ATMBalance);
        double accountWithdraw = 1.0;
        double result = instance.getCash(accountWithdraw);
    }

    @Test(expected = ATM.NotEnoughBalanceException.class)
    public void testGetCashWithNotEnoughMoneyInAccount(){
    	System.out.println("getCash with not enough money in account");
    	double ATMBalance = 100.0;
    	ATM instance = new ATM(ATMBalance);
    	double accountBalance = 50.0;
        double accountWithdraw = 70.0;
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(accountBalance);
        when(account.withdraw(accountWithdraw)).thenReturn(accountWithdraw);
        Card card = mock(Card.class);
        when(card.getAccount()).thenReturn(account);
        when(card.checkPin(anyInt())).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        instance.validateCard(card, 3210);
        double result = instance.getCash(accountWithdraw);
    }
  
    @Test(expected = ATM.NotEnoughMoneyInATMException.class)
    public void textGetCashWithNotEnoughMoneyInATM() {
        System.out.println("getCash with not enough money in ATM");
        double Balance = 100.0;
        double Withdraw = 90.0;
        double ATMBalance = 20.0;
        ATM instance = new ATM(ATMBalance);
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(Balance);
        when(account.withdraw(Withdraw)).thenReturn(Withdraw);
        Card card = mock(Card.class);
        
        when(card.getAccount()).thenReturn(account);
        when(card.checkPin(anyInt())).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        instance.validateCard(card, 3210);
        double result = instance.getCash(Withdraw);
    }

    @Test
    public void testGetCashMethodsOrderCheck() {
        System.out.println("Method order check for getCash method");
        double ATMBalance = 200.0;
        double accountBalance = 100.0;
        double accountWithdraw = 90.0;
        double accountBalanceAfter = 10.0;
        
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(accountBalance).thenReturn(accountBalanceAfter);
        when(account.withdraw(accountWithdraw)).thenReturn(accountWithdraw);
        
        Card card = mock(Card.class);
        when(card.getAccount()).thenReturn(account);
        when(card.checkPin(anyInt())).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        
        ATM instance = new ATM(ATMBalance);
        instance.validateCard(card, 1234);
        double result = instance.getCash(accountWithdraw);
        InOrder order = inOrder(account);
        order.verify(account).getBalance();
        order.verify(account).withdraw(accountWithdraw);
    }
     
     
}
