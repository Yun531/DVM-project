public class Card {
    private String information;
    private int balance;

    public Card(String information, int balance) {
        this.information = information;
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public String getInformation() {
        return information;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}