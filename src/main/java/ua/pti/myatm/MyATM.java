package ua.pti.myatm;

public class MyATM {
	
    public static void main(String[] args) {
        double moneyInATM = 1000;
        ATM atm = new ATM(moneyInATM);
        Card card = null;
        atm.validateCard(card, 3210);
        atm.checkBalance();
        atm.getCash(444);        
    }
}
