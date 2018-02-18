package account;

public class Account{
    private int balance;

    public Account(int x){
        setBalance(x);
    }

    public int getBalance() {
        return this.balance;
    }

    public void changeBalance(int x){
        this.balance += x;
    }

    public void setBalance(int b) {
        this.balance = b;
    }

    @Override
    public String toString() {
        return String.valueOf(balance);
    }
}
