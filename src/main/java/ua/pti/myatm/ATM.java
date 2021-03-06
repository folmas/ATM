package ua.pti.myatm;

public class ATM {
	
	private double moneyInATM;
	private Card insertedCard;

    ATM(double moneyInATM){
	insertedCard=null;
    	isPossibleMoney(moneyInATM);
    	this.moneyInATM = moneyInATM;
    }


    public double getMoneyInATM() {
    	return this.moneyInATM;
    }

    public boolean validateCard(Card card, int pinCode){
    	isATMEmpty(card);
    	if ( (card.checkPin(pinCode) && !card.isBlocked())){
    		this.insertedCard = card;
		return true;
    	}
    	return false;

    }
    
   
    public double checkBalance(){
    	isATMEmpty(insertedCard);
    	return this.insertedCard.getAccount().getBalance();
    	
    }
    

    public double getCash(double amount){
    	isATMEmpty(insertedCard);
    	isEnoughBalance(amount);
    	isEnoughMoneyATM(amount);
    	this.insertedCard.getAccount().withdraw(amount);
    	this.moneyInATM -= amount;
    	return this.insertedCard.getAccount().getBalance();
    }
    	
    	public void isATMEmpty(Card card){
    		if (card == null)
    			throw new ATMisEmptyException();
    	}
    	
    	public void isEnoughBalance(double amount){
    		if(this.insertedCard.getAccount().getBalance() < amount)
    			throw new NotEnoughBalanceException();
    	}
    	public void isEnoughMoneyATM(double amount){
    		if (this.moneyInATM < amount)
    			throw new NotEnoughMoneyInATMException();
    	}
    	public void isPossibleMoney(double money){
    		if (money  < 0.0)
    			throw new IllegalArgumentException("Impossible argument");
    	}
    		
    	
	public class ATMisEmptyException extends RuntimeException {
		
	}
	public class NotEnoughBalanceException extends RuntimeException {

	}
	public class NotEnoughMoneyInATMException extends RuntimeException {

	}
}
