import java.util.*;

public class PaymentPage extends Pay{

    void showPage(){
        System.out.println("<결제>");
        System.out.println("(메뉴 선택으로 돌아가려면 \"0\"을 입력해주세요)\n");
        System.out.println("카드 번호를 입력하세요.");
        System.out.print(">");
    }

    int inputInfo(){

        this.Info =scan.nextLine();

        if(this.Info.equals("0")) {
            return 0; //showMenu로 돌아감
        }
        else if(CardCompany.isValidCard(this.Info,totalPrice)){
            return 1;
        }
        else {
            return -1; //showMenu로 돌아감
        }
    }
    void printErr(){
        System.out.println("카드 정보가 올바르지 않거나 잔액이 부족하여 메뉴 선택으로 돌아갑니다.");
    }
    void payment(){
        CardCompany.deductMoney(this.Info,totalPrice);
        if(myDVM.updateStock(Integer.parseInt(dCode), count)) {
            getOutDrink(Integer.parseInt(dCode) + count * 100);
        }
    }



}
