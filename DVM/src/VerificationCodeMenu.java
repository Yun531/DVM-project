import java.util.*;

public class VerificationCodeMenu extends Pay{

    void showPage(){
        System.out.println("선결제 후 받은 인증코드를 입력해주세요\n" +
                "(메뉴 선택으로 돌아가려면 “0”를 입력해주세요)\n");
        System.out.print(">");
    }

    int inputInfo(){
        this.Info=scan.nextLine();

        if(Info.equals("0")) {
            return 0; //showMenu로 돌아감
        }
        else if(isRightVerificationCode(Info) && myDVM.isValidVerificationCode(Info)){
            getOutDrink(myDVM.reqVerificationCodeItem(Info));
            return 1;
        }
        else {
            return -1; //showMenu로 돌아감
        }
    }
    void payment(){
        getOutDrink(myDVM.reqVerificationCodeItem(Info));
    }
    void printErr(){
        System.out.println("입력하신 인증코드가 입력형식에 맞지 않거나, 유효하지 않은 인증코드입니다.");
    }


    //getOutDrink(myDVM.reqVerificationCodeItem(vCode));
    public boolean isRightVerificationCode(String vCode){
        boolean vCodeTR = false;
        boolean numberFlag = false, letterFlag = false;
        char chrInput;

        if(vCode.equals("0")){
            return true;
        }
        else if(vCode.length() == 10){
            for(int i = 0; i < vCode.length(); i++){
                chrInput = vCode.charAt(i);
                if (chrInput >= 0x61 && chrInput <= 0x7A) {
                    letterFlag = true;
                } else if (chrInput >= 0x30 && chrInput <= 0x39) {
                    numberFlag = true;
                } else{
                    break;
                }
                if((i + 1 == vCode.length()) && (numberFlag && letterFlag)) {
                    vCodeTR = true;
                }
            }
        }
        return vCodeTR;
    }
}
