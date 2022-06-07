import java.util.*;

public class PrePaymentPage extends Pay {

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
        MessageManager myMessageManager = MessageManager.getInstance();

        CardCompany.deductMoney(this.Info,totalPrice);

        String UserVCode;
        UserVCode = createVerificationCode();
        myMessageManager.sendReqMsg("PrepaymentCheck", dCode, count, UserVCode, dstID );
        System.out.println("선결제가 완료되었습니다.\n" +
                "인증코드: " + UserVCode);
    }
    public String createVerificationCode() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String vCode = random.ints(leftLimit,rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return vCode;
    }
}