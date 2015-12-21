/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pti.myatm;

import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.InOrder;
import static org.mockito.Mockito.*;

/**
 *
 * @author andrii
 */
public class ATMTest {

    @Test
    public void testGetMoneyInATM() {
        System.out.println("getMoneyInATM");
        ATM instance = new ATM(23);
        double expResult = 23;
        double result = instance.getMoneyInATM();
        assertEquals(expResult, result, 0.0);
    }
    
    @Test (expected = NullPointerException.class)
    public void testGetMoneyInATMNullPointer(){
        System.out.println("getMoneyInATM: Null Pointer ATM");
        ATM instance = null;
        instance.getMoneyInATM();
    }
    
    @Test
    public void testValidateCardBLockedCardCorrectPin() {
        System.out.println("validateCard: blocked card, correct PIN");
        Card card = mock(Card.class);
        ATM instance = new ATM(23);
        int pinCode = 0;
        when(card.isBlocked()).thenReturn(true);
        when(card.checkPin(pinCode)).thenReturn(true);
        boolean expResult = false;
        boolean result = instance.validateCard(card, pinCode);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateCardNotBlockedCorrectPin(){
        System.out.println("validateCard: valid card, correct PIN");
        Card card = mock(Card.class);
        ATM instance = new ATM(23);
        int pinCode = 0;
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(pinCode)).thenReturn(true);
        boolean expResult = true;
        boolean result = instance.validateCard(card, pinCode);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateCardBlockedInvalidPin(){
        System.out.println("validateCard: blocked card, invalid PIN");
        Card card = mock(Card.class);
        ATM instance = new ATM(23);
        int pinCode = 0;
        when(card.isBlocked()).thenReturn(true);
        when(card.checkPin(pinCode)).thenReturn(false);
        boolean expResult = false;
        boolean result = instance.validateCard(card, pinCode);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateCardNotBlockedInvalidPin(){
        System.out.println("validateCard: valid card, invalid PIN");
        Card card = mock(Card.class);
        ATM instance = new ATM(23);
        int pinCode = 0;
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(pinCode)).thenReturn(false);
        boolean expResult = false;
        boolean result = instance.validateCard(card, pinCode);
        assertEquals(expResult, result);
    }
    
    @Test (expected = NullPointerException.class)
    public void testValidateCardNullPointerCard(){
        System.out.println("validateCard: Null Pointer Card");
        Card card = null;
        ATM instance = new ATM(23);
        int pinCode = 0;
        instance.validateCard(card, pinCode);
    }
    
    @Test (expected = NullPointerException.class)
    public void testValidateCardNullPointerATM(){
        System.out.println("validateCard: Null Pointer ATM");
        Card card = mock(Card.class);
        ATM instance = null;
        int pinCode = 0;
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(pinCode)).thenReturn(true);
        instance.validateCard(card, pinCode);
    }
    
    @Test
    public void testCardIsNull() throws NoCardInserted{
        System.out.println("checkCard");
        Card card = mock(Card.class);
        ATM instance = new ATM(23);
        int pinCode = 0;
        when(card.checkPin(pinCode)).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        instance.validateCard(card, pinCode);
        instance.cardIsNull();
    }
    
    @Test (expected = NoCardInserted.class)
    public void testCardIsNullNoCardInserted() throws NoCardInserted{
        System.out.println("checkCard: No card inserted");
        Card card = mock(Card.class);
        ATM instance = new ATM(23);
        int pinCode = 0;
        when(card.checkPin(pinCode)).thenReturn(false);
        when(card.isBlocked()).thenReturn(false);
        instance.validateCard(card, pinCode);
        instance.cardIsNull();
    }
    
    @Test
    public void testCheckBalance() throws NoCardInserted{
        System.out.println("checkBalance");
        Card card = mock(Card.class);
        Account account = mock(Account.class);
        InOrder inOrder = inOrder(card);
        double balance=1.00;
        ATM instance = new ATM(23);
        int pinCode = 0;
        when(card.checkPin(pinCode)).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        when(card.getAccount()).thenReturn(account);
        when(account.getBalance()).thenReturn(balance);
        instance.validateCard(card, pinCode);
        inOrder.verify(card).checkPin(pinCode);
        inOrder.verify(card).isBlocked();
        double expResult = 1.00;
        double result = instance.checkBalance();
        inOrder.verify(card).getAccount();
        assertEquals(expResult, result, 0.0);
    }
    
    @Test (expected = NoCardInserted.class)
    public void testCheckBalanceNoInsertedCard() throws NoCardInserted{
        System.out.println("checkBalance: no card inserted");
        Card card = mock(Card.class);
        Account account = mock(Account.class);
        InOrder inOrder = inOrder(card);
        double balance = 1.00;
        ATM instance = new ATM(23);
        int pinCode = 0;
        when(card.checkPin(pinCode)).thenReturn(true);
        when(card.isBlocked()).thenReturn(true);
        when(card.getAccount()).thenReturn(account);
        when(account.getBalance()).thenReturn(balance);
        instance.validateCard(card, pinCode);
        inOrder.verify(card).checkPin(pinCode);
        inOrder.verify(card).isBlocked();
        instance.checkBalance();
        inOrder.verify(card).getAccount();
        instance.checkBalance();
    }
    
    @Test
    public void testGetCash() throws NoCardInserted, NotEnoughMoneyInAccount, NotEnoughMoneyInATM{
        System.out.println("getCash");
        Card card = mock(Card.class);
        Account account = mock(Account.class);
        InOrder inOrder = inOrder(card, account);
        double balance = 20.00;
        double amount = 14.00;
        double expResult = 9.00;
        ATM instance = new ATM(23);
        int pinCode = 0;
        when(card.checkPin(pinCode)).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        when(card.getAccount()).thenReturn(account);
        when(account.getBalance()).thenReturn(balance);
        instance.validateCard(card, pinCode);
        inOrder.verify(card).checkPin(pinCode);
        inOrder.verify(card).isBlocked();
        instance.checkBalance();
        instance.getCash(amount);
        double result = instance.getMoneyInATM();
        inOrder.verify((card), times(3)).getAccount();
        inOrder.verify(account).withdrow(amount);
        assertEquals(expResult, result, 0.0); 
    }
    
    @Test (expected = NoCardInserted.class)
    public void testGetCashNoCardInserted() throws NoCardInserted, NotEnoughMoneyInAccount, NotEnoughMoneyInATM{
        System.out.println("getCash: No card inserted");
        Card card = mock(Card.class);
        Account account = mock(Account.class);
        InOrder inOrder = inOrder(card, account);
        double balance = 23.00;
        double amount = 14.00;
        double expResult = 9.00;
        ATM instance = new ATM(23);
        int pinCode = 0;
        when(card.checkPin(pinCode)).thenReturn(false);
        when(card.isBlocked()).thenReturn(false);
        when(card.getAccount()).thenReturn(account);
        when(account.getBalance()).thenReturn(balance);
        when(account.withdrow(amount)).thenReturn(expResult);
        instance.validateCard(card, pinCode);
        inOrder.verify(card).checkPin(pinCode);
        verifyNoMoreInteractions(card);
        instance.checkBalance();
        double result = instance.getCash(amount);
        assertEquals(expResult, result, 0.0);
    }
    
    @Test (expected = NotEnoughMoneyInATM.class)
    public void testGetCashNotEnoughMoneyInATM() throws NoCardInserted, NotEnoughMoneyInAccount, NotEnoughMoneyInATM{
        System.out.println("getCash: Not enough money in the ATM");
        Card card = mock(Card.class);
        Account account = mock(Account.class);
        InOrder inOrder = inOrder(card, account);
        double balance = 23.00;
        double amount = 14.00;
        double expResult = 9.00;
        ATM instance = new ATM(5);
        int pinCode = 0;
        when(card.checkPin(pinCode)).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        when(card.getAccount()).thenReturn(account);
        when(account.getBalance()).thenReturn(balance);
        when(account.withdrow(amount)).thenReturn(expResult);
        instance.validateCard(card, pinCode);
        inOrder.verify(card).checkPin(pinCode);
        inOrder.verify(card).isBlocked();
        instance.checkBalance();
        inOrder.verify(account).getBalance();
        verifyNoMoreInteractions(account);
        instance.getCash(amount);
    }
    
    @Test (expected = NotEnoughMoneyInAccount.class)
    public void testGetCashNotEnoughMoneyInAccount() throws NoCardInserted, NotEnoughMoneyInAccount, NotEnoughMoneyInATM{
        System.out.println("getCash: Not enough money on account");
        Card card = mock(Card.class);
        Account account = mock(Account.class);
        InOrder inOrder = inOrder(card, account);
        double balance = 3.00;
        double amount = 14.00;
        double expResult = 9.00;
        ATM instance = new ATM(23);
        int pinCode = 0;
        when(card.checkPin(pinCode)).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        when(card.getAccount()).thenReturn(account);
        when(account.getBalance()).thenReturn(balance);
        when(account.withdrow(amount)).thenReturn(expResult);
        instance.validateCard(card, pinCode);
        inOrder.verify(card).checkPin(pinCode);
        inOrder.verify(card).isBlocked();
        instance.checkBalance();
        inOrder.verify(account).getBalance();
        instance.getCash(amount);
        inOrder.verify(account).getBalance();
        verify(account, never()).withdrow(amount);
    }
    
    @Test
    public void testAddCash() throws NoCardInserted, NotEnoughMoneyInATM{
        System.out.println("addCash");
        Card card = mock(Card.class);
        Account account = mock(Account.class);
        double balance = 20.00;
        double amount = 1.00;
        double expResult = 24.00;
        ATM instance = new ATM(23);
        int pinCode = 0;
        when(card.checkPin(pinCode)).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        when(card.getAccount()).thenReturn(account);
        when(account.getBalance()).thenReturn(balance);
        
        instance.validateCard(card, pinCode);
       
        //instance.checkBalance();
        instance.addCash(amount);
        
        double result = instance.getMoneyInATM();
        InOrder inOrder = inOrder(card, account);
        verify(card, atLeast(1)).isBlocked();
        inOrder.verify(account).charge(amount);
        assertEquals(expResult, result, 0.0);
    }
    
    @Test (expected = NoCardInserted.class)
    public void testAddCashNoCardInserted() throws NoCardInserted, NotEnoughMoneyInATM{
        System.out.println("addCash: No card inserted");
        Card card = mock(Card.class);
        Account account = mock(Account.class);
        InOrder inOrder = inOrder(card, account);
        double balance = 23.00;
        double amount = 14.00;
        double expResult = 9.00;
        ATM instance = new ATM(23);
        int pinCode = 0;
        when(card.checkPin(pinCode)).thenReturn(false);
        when(card.isBlocked()).thenReturn(false);
        when(card.getAccount()).thenReturn(account);
        when(account.getBalance()).thenReturn(balance);
        when(account.charge(amount)).thenReturn(expResult);
        instance.validateCard(card, pinCode);
        inOrder.verify(card).checkPin(pinCode);
        verifyNoMoreInteractions(card);
        instance.checkBalance();
        double result = instance.addCash(amount);
        assertEquals(expResult, result, 0.0);
    }
    
    @Test (expected = NotEnoughMoneyInATM.class)
    public void testAddCashNotEnoughMenoyInATM() throws NoCardInserted, NotEnoughMoneyInATM{
        System.out.println("addCash: Not enough money in the ATM");
        Card card = mock(Card.class);
        Account account = mock(Account.class);
        InOrder inOrder = inOrder(card, account);
        double balance = 23.00;
        double amount = 14.00;
        double expResult = 37.00;
        ATM instance = new ATM(5);
        int pinCode = 0;
        when(card.checkPin(pinCode)).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        when(card.getAccount()).thenReturn(account);
        when(account.getBalance()).thenReturn(balance);
        when(account.charge(amount)).thenReturn(expResult);
        instance.validateCard(card, pinCode);
        inOrder.verify(card).checkPin(pinCode);
        inOrder.verify(card).isBlocked();
        instance.checkBalance();
        inOrder.verify(account).getBalance();
        verifyNoMoreInteractions(account);
        instance.addCash(amount);
    }
    
    @Test
    public void testAddCashCheckMoneyATM() throws NoCardInserted, NotEnoughMoneyInATM{
        System.out.println("addCash: Check Money in the ATM");
        Card card = mock(Card.class);
        Account account = mock(Account.class);
        InOrder inOrder = inOrder(card, account);
        double balance = 40.00;
        double amount = 1.00;
        double expResult = 24.00;
        ATM instance = new ATM(23);
        int pinCode = 0;
        when(card.checkPin(pinCode)).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        when(card.getAccount()).thenReturn(account);
        when(account.getBalance()).thenReturn(balance);
        when(account.charge(amount)).thenReturn(expResult);
        instance.validateCard(card, pinCode);
        inOrder.verify(card).checkPin(pinCode);
        inOrder.verify(card).isBlocked();
        instance.checkBalance();
        instance.addCash(amount);
        double result = instance.getMoneyInATM();
        inOrder.verify((card), times(1)).getAccount();
        inOrder.verify(account).charge(amount);
        assertEquals(expResult, result, 0.0);
    }
}
