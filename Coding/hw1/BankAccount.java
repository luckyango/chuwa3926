
public class BankAccount {
    private final String accountNumber; private double balance;
    public BankAccount(String accountNumber){
        this.accountNumber = accountNumber; this.balance = 0.0;
    }
    public String getAccountNumber(){
        return accountNumber;
    }
    public double getBalance(){
        return balance;
    }
    public boolean deposit(double amount){
        if(amount <= 0) return false; 
        balance+=amount;
        return true;
    }
    public boolean withdraw(double amount){
        if(amount > 0 && balance >= amount){
            balance -= amount;return true;
        }
        return false;
    }
    public static void main(String[] args){
        BankAccount ba = new BankAccount("boa12343");
        System.out.println("Create a new account: "  + "accountNumber: " + ba.getAccountNumber() + "; initial balance: "+ba.getBalance());
        ba.deposit(-200);
        System.out.println("Try to deposit an invalid balance (-200) but will fail, and the current balance is "+ba.getBalance());
        ba.deposit(22.78);
        System.out.println("Try to deposit a valid amount, so the current balance becomes "+ba.getBalance());
        ba.withdraw(-23.67);
        System.out.println("Try to withdraw an invalid balance (-23.67) but will fail, and the current balance is "+ba.getBalance());
        ba.withdraw(10);
        System.out.println("Try to withdraw a valid amount, so the current balance becomes "+ba.getBalance());
    }
}
