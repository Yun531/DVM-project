public class CardCompany {

    public CardCompany() {
    }

    private static Card[] cardList = {
            new Card("0000111122223333", 0),
            new Card("1111222233334444",1000),
            new Card("2222333344445555",5000),
            new Card("3333444455556666",10000),
            new Card("4444555566667777",10000)
    };

    public static boolean isValidCard(String cardInfo, int totalPrice) {
        boolean test = false;

        for(int i = 0; i < 5; i++) {
            if (cardInfo == cardList[i].getInformation()) {
                if(isEnoughBalance(totalPrice, i)) {
                    test = true;
                }
            }
        }
        return test;
    }

    public static boolean isEnoughBalance(int totalPrice, int i) {
        boolean test = false;

        if(totalPrice >= cardList[i].getBalance()) {
            test = true;
        }
        return test;
    }

    public static int deductMoney(String cardInfo, int totalPrice) {
        int i = 0;
        int temp;
        for (i = 0; i < 5; i++) {
            if(cardInfo == cardList[i].getInformation()){
                break;
            }
        }

        temp=cardList[i].getBalance() - totalPrice;
        if(temp >= 0) {
            cardList[i].setBalance(temp);
        }
        else {
            return -1;
        }
        return 0;
    }

    public static void refund(String cardInfo, int totalPrice) {
        int i = 0;
        for (i = 0; i < 5; i++) {
            if (cardInfo == cardList[i].getInformation()) {
                break;
            }
        }
        cardList[i].setBalance(cardList[i].getBalance() + totalPrice);
        return ;
    }
}