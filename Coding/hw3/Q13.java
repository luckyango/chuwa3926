package chuwa3926.Coding.hw3;
public class Q13{
    public static class InsufficientBalanceException extends Exception{
        public InsufficientBalanceException(String message){
            super(message);
        }
    }
    public static class Wallet{
        private double balance;
        public Wallet(double balance){
            this.balance = balance;
        }
        public void deposit(double amount){
            if(amount < 0) throw new IllegalArgumentException("Deposit amount cant be negative");
            balance += amount;
        }
        public void withdraw(double amount){
            if(balance < amount) throw new InsufficientBalanceException("Not enough balance");
            balance -= amount;
        }
        public double getBalance(){
            return balance;
        }
    }

        public static void main(String[] args) {
            Wallet wallet = new Wallet(100); 
            try {
                wallet.deposit(50);
                wallet.withdraw(200);
            } catch (IllegalArgumentException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (InsufficientBalanceException e) {
                System.err.println("Error: " + e.getMessage());
            }
            
            System.out.println("Final balance: " + wallet.getBalance());

    }
}
