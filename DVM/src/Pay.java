import java.util.*;

public abstract class Pay {
    Scanner scan=new Scanner(System.in);
    DVM myDVM = DVM.getInstance();
    int totalPrice;
    String Info;
    String dCode;
    int count;
    String dstID;


    void pay(String dCode, int count, int totalPrice, String dstID){
        this.totalPrice = totalPrice;
        this.dCode = dCode;
        this.count = count;
        this.dstID = dstID;

        showPage();
        int errno = inputInfo();  //-1: 비정상 입력, 0: 입력 종료, 1: 카드 정상 입력, 2: 인증코드 정상 입력
        proceedPay(errno);

    }
    abstract void showPage();
    abstract int inputInfo();
    void proceedPay(int errno){
        switch(errno){
            case -1:
                printErr();
                break;
            case 0:
                printExit();
                break;
            case 1:
                payment();
                break;
        }
    }
    abstract void printErr();
    void printExit(){
        System.out.println("사용자가 '0'을 입력하여 메뉴 선택으로 돌아갑니다.\n");
    }
    abstract void payment();

    public void getOutDrink(int Calc) {
        int dCode_ = Calc % 100;
        int count_ = Calc / 100;

        Item item = myDVM.getItemList()[dCode_ -1];

        System.out.println("음료: " + item.getName() + ", " + count_ + "개");
        System.out.println("음료가 모두 배출되었습니다.\n 감사합니다.");
    }



}
