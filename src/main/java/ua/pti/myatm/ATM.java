package ua.pti.myatm;
    
public class ATM {    
    public double moneyInATM;
    public Card insertedCard;
    
    ATM(double moneyInATM){
        this.moneyInATM=moneyInATM;
        insertedCard=null;
    }

    public double getMoneyInATM() {
        return this.moneyInATM;
    }
 
    public boolean validateCard(Card card, int pinCode){
        if (!card.checkPin(pinCode)){
             System.out.println("Invalid PIN");
             return false;
        } else if (card.isBlocked()){
             System.out.println("Card is blocked!");
             return false;
        } else {
             insertedCard=card;
             return true;
        }      
    }
    
    public void cardIsNull() throws NoCardInserted{
        if(insertedCard==null){
            throw new NoCardInserted("No card inserted!");
        }
    }
    
    public double checkBalance() throws NoCardInserted{
        cardIsNull();
        return insertedCard.getAccount().getBalance();
    }
   
    public double getCash(double amount) throws NoCardInserted, NotEnoughMoneyInAccount, NotEnoughMoneyInATM{
        cardIsNull();
        if (getMoneyInATM() < amount){
            throw new NotEnoughMoneyInATM("No money no honey! Not enough money in the ATM");
        } else if (insertedCard.getAccount().getBalance() < amount){
            throw new NotEnoughMoneyInAccount("A fool and his money are soon parted.. Not enough money on your account!");
        } else{
            System.out.println("Sucess!");
            this.moneyInATM-=amount;
            return insertedCard.getAccount().withdrow(amount);
        }  
    }
    
    public double addCash(double amount) throws NoCardInserted,NotEnoughMoneyInATM{
        if (insertedCard.isBlocked()){
            throw new NoCardInserted("Card is blocked!");
        } else if (getMoneyInATM() < amount){
            throw new NotEnoughMoneyInATM("No money no honey! Not enough money in the ATM");
        } else {
        this.moneyInATM+=amount;
        return insertedCard.getAccount().charge(amount);}
    }
}
