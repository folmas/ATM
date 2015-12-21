package ua.pti.myatm;

public class ATM {
	
	private double moneyInATM;
	private Card currentCard = null;

    ATM(double moneyInATM){
    	if (moneyInATM  < 0.0)
    		throw new IllegalArgumentException("Start amount should not be less than zero");
    	this.moneyInATM = moneyInATM;
    }


    public double getMoneyInATM() {
    	return this.moneyInATM;
    }

    public boolean validateCard(Card card, int pinCode){
    	if (card == null)
    		throw new NoCardInsertedException();
    	boolean validness = (card.checkPin(pinCode) && !card.isBlocked());
    	if (validness){
    		this.currentCard = card;
    	}
    	return validness;
    }
    
   
    public double checkBalance(){
    	if (this.currentCard == null)
    		throw new NoCardInsertedException();
    	return this.currentCard.getAccount().getBalance();
    	
    }
    

    public double getCash(double amount){
    	if (this.currentCard == null) 	
    		throw new NoCardInsertedException();
    	if(this.currentCard.getAccount().getBalance() < amount)
    		throw new NotEnoughMoneyInAccountException();
    	if (this.moneyInATM < amount)
    		throw new NotEnoughMoneyInATMException();
    	this.currentCard.getAccount().withdraw(amount);
    	this.moneyInATM -= amount;
    	return this.currentCard.getAccount().getBalance();
    }
    	
	public class NoCardInsertedException extends RuntimeException {
		
	}
	public class NotEnoughMoneyInAccountException extends RuntimeException {

	}
	public class NotEnoughMoneyInATMException extends RuntimeException {

	}
}
