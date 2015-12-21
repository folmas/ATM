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
        ATM instance = new ATM(-22);
    }
    
 
    
    @Test
    public void testGetMoneyInATM() {
        ATM instance = new ATM(30);
        double expResult = 30;
        System.out.println("get Money");
        double result = instance.getMoneyInATM();
        assertEquals(expResult, result, 0);
        
    }

    
    @Test(expected = ATM.ATMisEmptyException.class)
    public void testValidateCardEmptyATM() {
        System.out.println("Without inserted card");
        Card card = null;
        ATM instance = new ATM(30);
        boolean expResult = false;
        boolean result = instance.validateCard(card, 1234);
        assertEquals(expResult, result);
    }
  

    @Test
    public void testValidateBlockedCardValidPin() {
        System.out.println("with blocked card");
        boolean expResult = false;
        Card card = mock(Card.class);
        int pinCode = 3210;
        ATM instance = new ATM(30);
        when(card.checkPin(pinCode)).thenReturn(true);
        when(card.isBlocked()).thenReturn(true);
        boolean result = instance.validateCard(card, pinCode);
        assertEquals(expResult, result);
    }
    
      @Test
    public void testValidateCardUnblockedCardValidPin() {
        System.out.println("validateCard no block, true pin");
        Card card = mock(Card.class);
        int pinCode = 3210;
        ATM instance = new ATM(30);
        boolean expResult = true;
        when(card.checkPin(pinCode)).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        boolean result = instance.validateCard(card, pinCode);
        assertEquals(expResult, result);
    }

    @Test
    public void testValidateCardUnblockedCardInvalidPin() {
        System.out.println("validateCard with wrong pin");
        ATM instance = new ATM(30);
        boolean expResult = false;
        Card card = mock(Card.class);
        when(card.checkPin(anyInt())).thenReturn(false);
        when(card.isBlocked()).thenReturn(false);
        boolean result = instance.validateCard(card, 3210);
        assertEquals(expResult, result);
    }


  

    @Test
    public void testCheckBalanceValidPinUnblockCard() {
        System.out.println("check balance");
        Account account = mock(Account.class);
        double accountBalance = 1000;
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
        assertEquals(accountBalance, result, 0);
    }

    @Test(expected=ATM.ATMisEmptyException.class)
    public void testCheckBalanceNoCard(){
    	System.out.println("balance with Empty ATM");
    	double ATMBalance =300;
    	ATM instance = new ATM(ATMBalance);
    	double result = instance.checkBalance();
    }

    @Test
    public void testGetCash() {
        System.out.println("get Cash allright");
        double expResult = accountBalanceAfter;
        double accountBalanceBefore = 1000;
        double amountForWithdraw = 100;
        double accountBalanceAfter = 900;
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(accountBalanceBefore).thenReturn(accountBalanceAfter);
        when(account.withdraw(amountForWithdraw)).thenReturn(amountForWithdraw);
        Card card = mock(Card.class);
        when(card.getAccount()).thenReturn(account);
        when(card.checkPin(anyInt())).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        ATM instance = new ATM(1000);
        instance.validateCard(card,3210);
        double result = instance.getCash(amountForWithdraw);
        assertEquals(expResult, result, 0);
    }
    
    @Test
    public void testGetCashMethodsOrderCheck() {
        System.out.println("order check for getCash method");
        double ATMBalance = 200;
        double accountBalance = 100;
        double accountWithdraw = 90;
        double accountBalanceAfter = 10;
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

    @Test(expected = ATM.ATMisEmptyException.class)
    public void textGetCashWitoutCard() {
       double accountWithdraw = 1;
        double ATMBalance = 200;
        ATM instance = new ATM(ATMBalance);
        System.out.println("getCash with Empty ATM");
        double result = instance.getCash(accountWithdraw);
    }
    

    @Test(expected = ATM.NotEnoughBalanceException.class)
    public void testGetCashWithNotEnoughMoneyInAccount(){
    	System.out.println("getCash with Not Enough Balance");
    	double ATMBalance = 111;
    	double accountBalance = 13;
    	double draw = 40;
    	ATM instance = new ATM(ATMBalance);
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(accountBalance);
        when(account.withdraw(draw)).thenReturn(draw);
        Card card = mock(Card.class);
        when(card.getAccount()).thenReturn(account);
        when(card.checkPin(anyInt())).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        instance.validateCard(card, 3210);
        double result = instance.getCash(draw);
    }
  
    @Test(expected = ATM.NotEnoughMoneyInATMException.class)
    public void textGetCashWithNotEnoughMoneyInATM() {
        System.out.println("getCash with not enough money in ATM");
        double Balance = 100;
        double Withdraw = 90;
        double ATMBalance = 20;
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

    
     
     
}
