import java.util.*;
import Model.Message;

public class Controller {

    public Controller() {
        myDVM = new DVM();
        myMessageManager = new MessageManager();
    }

    private String dCode;
    private int count;
    private DVM myDVM;
    private MessageManager myMessageManager;
    private ArrayList<Message> myMessage= new ArrayList<Message>();

    Scanner scan=new Scanner(System.in);

    public void showMenu() {
        int mode;
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("\n원하시는 메뉴의 번호를 입력해주세요.\n" +
                        "1. 음료 선택\n" +
                        "2. 인증코드 입력\n" +
                        "3. 관리자 모드");
                System.out.print(">");
                mode = sc.nextInt();
                break;
            } catch (InputMismatchException ime) {
                sc.next();
                System.out.println("잘못된 입력입니다. 정수만 입력해주세요.");
            }
        }

        while (!(mode > 0 && mode < 4)) {
            System.out.println("잘못된 입력입니다. 1, 2, 3 중에서만 입력해주세요.");
            System.out.println("원하시는 메뉴의 번호를 입력해주세요.\n" +
                    "1. 음료 선택\n" +
                    "2. 인증코드 입력\n" +
                    "3. 관리자 모드");
            System.out.print(">");
            while (true) {
                try {
                    mode = sc.nextInt();
                    break;
                } catch (InputMismatchException ime) {
                    sc.next();
                    System.out.println("잘못된 입력입니다. 정수만 입력해주세요.");
                    System.out.println("원하시는 메뉴의 번호를 입력해주세요.\n" +
                            "1. 음료 선택\n" +
                            "2. 인증코드 입력\n" +
                            "3. 관리자 모드");
                    System.out.print(">");
                }
            }
        }
        //sc.close();

        switch (mode) {
            case 1:
                showSelectItemPage();
                break;
            case 2:
                showVerificationCodeMenu();
                break;
            case 3:
                showAdminMenu();
                break;
        }
    }

    public int isValidCode() {
        switch(dCode){
            case "0":
                return 0;
            case "1":
                dCode = "01";
                return 1;
            case "2":
                dCode = "02";
                return 1;
            case "3":
                dCode = "03";
                return 1;
            case "4":
                dCode = "04";
                return 1;
            case "5":
                dCode = "05";
                return 1;
            case "6":
                dCode = "06";
                return 1;
            case "7":
                dCode = "07";
                return 1;
            case "8":
                dCode = "08";
                return 1;
            case "9":
                dCode = "09";
                return 1;
            case "01":
            case "02":
            case "03":
            case "04":
            case "05":
            case "06":
            case "07":
            case "08":
            case "09":
            case "10":
            case "11":
            case "12":
            case "13":
            case "14":
            case "15":
            case "16":
            case "17":
            case "18":
            case "19":
            case "20":
                return 1;
            default:
                return -1;
        }
    }

    public int isValidCount() {
        if(count == 0) {
            return 0;
        } else if((count > 0) && ( count < 1000)) {
            return 1;
        } else {
            return -1;
        }
    }

    public Location getClosestDVM() {
        Location returnLoc = new Location();
        Location myLoc = myDVM.getLocation();
        int x = myLoc.getX();
        int y = myLoc.getY();
        int min = 9999;
        String tempID = " ";
        // Todo: Server 내에 msgList에 접근하여 수신(응답)받은 Message Object를 하나씩 가져온다.
        myMessageManager.sendReqMsg("StockCheckRequest",dCode,count);
        try{
            Thread.sleep(5000); //message가 도착하고 처리 되기까지 기다리기
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        while(myMessage.size() > 0){
            Message temp = myMessage.remove(0);
            int compareX = temp.getMsgDescription().getDvmXCoord();
            int compareY = temp.getMsgDescription().getDvmYCoord();
            String compareID = temp.getSrcId();
            //1. 거리를 구하고
            int resX = x - compareX;
            if (resX < 0) resX *= -1;
            int resY = y - compareY;
            if (resY < 0) resY *= -1;
            //2. min과 비교하고
            if( resX + resY < min) {
            //3.
                //더 작다면 other DVM의 위치를 저장한다.
                tempID = compareID;
                returnLoc.setX(compareX);
                returnLoc.setY(compareY);
            }else if (resX + resY == min) {
                //같다면 id를 비교하고
                if(tempID.compareTo(compareID)>0){
                    tempID = compareID;
                    returnLoc.setX(compareX);
                    returnLoc.setY(compareY);
                };
            }
                //더 멀다면 넘어가고


        }


        return returnLoc;
    }

    public boolean showOtherDVM(Location otherDVM) {
        if((otherDVM.getX() == 0) && (otherDVM.getY() == 0)){
            return false;
        }
        else{
            System.out.println("재고가 있는 가장 가까운 DVM의 위치는 " + otherDVM.getX() + ", " + otherDVM.getY());
            return true;
        }
    }

    public void showSelectItemPage() {
        int errno = 0;
        Scanner sc = new Scanner(System.in);

        while(!(errno == 1)) {
            System.out.println("콜라(01)     사이다(02)     녹차(03)      홍차(04)\n" +
                    "밀크티(05)   탄산수(06)     보리차(07)     캔커피(08)\n" +
                    "물(09)      에너지드링크(10) 바닷물(11)    식혜(12)\n" +
                    "아이스티(12) 딸기주스(14)    오렌지주스(15) 포도주스(16)\n" +
                    "이온음료(17) 아메리카노(18)   핫초코(19)    카페라뗴(20)");
            System.out.println("메뉴 선택으로 돌아가려면 \"0\"을 입력해 주세요.\n");
            System.out.println("원하시는 음료의 번호를 입력해주세요.");
            System.out.print(">");
            try{
                dCode = sc.next();
            }
            catch(InputMismatchException ime) {
                System.out.println("잘못된 입력입니다.");
                continue;
            }

            errno = isValidCode();
            if(errno == 0){
                System.out.println("음료 선택을 취소합니다.");
                return ;
            } else if (errno == -1) {
                System.out.println("0~20 사이의 음료코드를 입력해주세요.");
            }
        }

        errno = 0;
        while(!(errno == 1)) {
            System.out.println("수량을 입력해주세요.");
            System.out.print(">");
            try{
                count = sc.nextInt();
            }
            catch(InputMismatchException ime) {
                sc.nextLine();
                System.out.println("잘못된 입력입니다. 정수만 입력해주세요.");
                continue;
            }

            errno = isValidCount();
            if(errno == 0){
                System.out.println("음료 선택을 취소합니다.");
                return ;
            } else if (errno == -1) {
                System.out.println("1~999 사이애서 개수를 입력해주세요.");
            }
        }

        if(myDVM.checkStock(Integer.parseInt(dCode), count)) {
            showPaymentPage(calculateTotalPrice());
        } else {
            if(showOtherDVM(getClosestDVM())) {
                showPaymentPage(calculateTotalPrice());

                String UserVCode;
                UserVCode = createVerificationCode();
                myMessageManager.sendReqMsg("PrepaymentCheck", dCode, count, UserVCode);
                System.out.println("선결제가 완료되었습니다.\n" +
                        "인증코드: " + UserVCode);
            }
            else{
                System.out.println("해당 음료에 대한 재고를 보유한 DVM이 존재하지 않습니다.");
            }
        }
    }

    public void showPaymentPage(int totalPrice) {
        String cardInfo;
        boolean check;

        System.out.println("<결제>");
        System.out.println("(메뉴 선택으로 돌아가려면 \"0\"을 입력해주세요)\n");
        System.out.println("카드 번호를 입력하세요.");
        System.out.print(">");

        cardInfo=scan.nextLine();
        if(cardInfo.equals("0"))
            return; //showMenu로 돌아감
        else {
            check = CardCompany.isValidCard(cardInfo,totalPrice);
            if (check) {
                CardCompany.deductMoney(cardInfo,totalPrice);
                if(myDVM.updateStock(Integer.parseInt(dCode), count)) {
                    getOutDrink(Integer.parseInt(dCode) + count * 100);
                }
                return;
            }
            else {
                System.out.println("카드 정보가 올바르지 않거나 잔액이 부족하여 메뉴 선택으로 돌아갑니다.");
                return; //showMenu로 돌아감
            }
        }
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

    public void showVerificationCodeMenu() {
        String vCode = "입력오류";
        boolean vCodeTR = false;

        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("선결제 후 받은 인증코드를 입력해주세요\n" +
                    "(메뉴 선택으로 돌아가려면 “0”를 입력해주세요)\n");
            System.out.print(">");

            try {
                vCode = sc.next();
            } catch(InputMismatchException ime) {
                System.out.println("잘못된 입력입니다.");
                continue;
            }

            if(isRightVerificationCode(vCode)) {          //인증코드 입력형식 test
                break;
            }
            System.out.println("입력하신 인증코드가 입력형식에 맞지 않습니다.\n");

        }

        vCodeTR = myDVM.isValidVerificationCode(vCode);             //유효한 인증코드 test
        if(vCode.equals("0")) {

        } else if (!vCodeTR) {
            System.out.println("유효하지 않은 인증코드입니다.");
        } else {
            getOutDrink(myDVM.reqVerificationCodeItem(vCode));
        }
        sc.close();
    }

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

    public void showAdminPasswordPage() {
        int menu;

        while(true) {

            System.out.println("<관리자 모드>");
            System.out.println("원하는 작업의 번호를 선택해주세요.");
            System.out.println("(메뉴 선택으로 돌아가려면 \"0\"을 입력해주세요.)");

            System.out.println("1. DVM 정보 관리");
            System.out.println("2. 음료 정보 관리");
            System.out.println("3. 음료 세팅");

            System.out.print(">");

            while(!scan.hasNextInt()) {
                scan.next();
                System.out.println("정확한 번호만 입력하세요");
            }
            menu = scan.nextInt();
            if(menu == 1)
                setDVMInfo();
            else if(menu == 2)
                setDrinkInfo();
            else if(menu == 3)
                setDrinkKinds();
            else if(menu == 0) {
                scan.nextLine();
                return; //showMenu로 돌아감
            }
            else {
                System.out.println("번호는 0~3만 입력하세요");
            }
        }
    }

    public boolean checkAdminPassword(String password) {
        boolean check = myDVM.isValidPassword(password);
        if(!check)
            return false;
        else
            return true;
    }

    public void showAdminMenu() {
        String adminPassword;
        boolean check;



        while(true) {
            System.out.println("Admin password를 입력해 주세요");
            System.out.println("(메뉴 선택으로 돌아가려면 \"0\"을 입력해주세요.)");
            System.out.print(">");

            adminPassword = scan.nextLine();
            if(adminPassword.equals("0")) {
                return; //showMenu로 돌아감
            }
            else {
                check = checkAdminPassword(adminPassword);
                if (check) {
                    showAdminPasswordPage();
                }
                else {
                    System.out.println("비밀번호가 틀렸습니다. 다시 입력하세요");
                }
            }
        }
    }

    public void setDVMInfo() {
        String name = "Team4";
        String inputId;
        int x = 0;
        int y = 0;
        Location loc;

        System.out.println("<DVM 정보 관리>");
        System.out.println("id 입력 후 tab을 눌러 좌표를 입력하세요\n");
        System.out.println("id  좌표");
        System.out.println("EX: Team4   10 20");


        while(true) {
            System.out.print(">");
            inputId = scan.next();
            if(inputId.equals(name)) {
                break;
            } else {
                System.out.println("DVM의 id를 확인 후 입력하세요");
            }
        }

        while(true) {
            while(!scan.hasNextInt()) {
                scan.next();
                System.out.println("정확한 번호만 입력하세요");
            }
            x = scan.nextInt();
            if(x >= 0 && x <= 99)
                break;
            else
                System.out.println("x 좌표의 범위는 0~99입니다. 다시 입력하세요");
        }

        while(true) {
            while(!scan.hasNextInt()) {
                scan.next();
                System.out.println("정확한 번호만 입력하세요");
            }
            y=scan.nextInt();
            if(y >= 0 && y <= 99)
                break;
            else
                System.out.println("y 좌표의 범위는 0~99입니다. 다시 입력하세요");
        }

        loc=new Location(x, y);
        myDVM.saveDVMInfo(inputId, loc);
    }

    public void setDrinkInfo() {
        Item[] myItem = myDVM.getItemList();

        System.out.println("<음료 정보 관리>");
        System.out.println("음료 정보 관리 시 tab을 눌러 다음 정보를 입력 후");
        System.out.println("enter를 눌러 다음 음료를 입력하세요.\n");
        System.out.println("음료코드\t\t가격\t\t\t재고\t\t판매여부\t\t음료이름");

        for(int i=0;i<myItem.length;i++) {
            if(myItem[i].getStock()==-1) {
                if(i >= 0 && i <= 8)
                    System.out.println("0" + (i+1) + "\t\t\t" + myItem[i].getPrice() + "\t\t" + "_" + "\t\t" + "X"+ "\t\t\t" + myItem[i].getName() );
                else
                    System.out.println((i+1)  + "\t\t\t" + myItem[i].getPrice() + "\t\t" + "_" + "\t\t" + "X"+ "\t\t\t" + myItem[i].getName());
            }
            else {
                if(i >= 0 && i <= 8)
                    System.out.println("0" + (i+1) + "\t\t\t" + myItem[i].getName() + "\t\t\t");
                else
                    System.out.println((i+1) + "\t\t\t" + myItem[i].getName() + "\t\t\t");
                System.out.print(">");

                int price = 0;
                int stock = 0;

                for(int j = 0; j < 2; j++) {
                    while(true) {
                        while (!scan.hasNextInt()) {
                            scan.next();
                            System.out.println("정확한 번호만 입력하세요");
                        }
                        if(j==0) {
                            price = scan.nextInt();
                            if (price > 0)
                                break;
                            else
                                System.out.println("가격은 0보다 커야 합니다. 다시 입력하세요");
                        }
                        else {
                            stock = scan.nextInt();
                            if (stock >= 0)
                                break;
                            else
                                System.out.println("재고는 양수여야 합니다. 다시 입력하세요");
                        }
                    }
                }
                myDVM.saveDrinkInfo(i+1, price, stock, myItem[i].getName());
                scan.nextLine();
            }
        }
    }

    public void setDrinkKinds() {
        int count = 0;
        int drinkCode = 0;
        int[] dCodeArr = new int[7];

        System.out.println("<음료 세팅>");
        System.out.println("현재 자판기에서 판매할 7가지 음료의 번호를 입력하고 enter를 눌러주세요");
        System.out.println("콜라(01)     사이다(02)     녹차(03)      홍차(04)\n" +
                "밀크티(05)   탄산수(06)     보리차(07)     캔커피(08)\n" +
                "물(09)      에너지드링크(10) 바닷물(11)    식혜(12)\n" +
                "아이스티(12) 딸기주스(14)    오렌지주스(15) 포도주스(16)\n" +
                "이온음료(17) 아메리카노(18)   핫초코(19)    카페라뗴(20)");

        while(true) {
            System.out.print(">");
            while(!scan.hasNextInt()) {
                scan.next();
                System.out.println("정확한 번호만 입력하세요");
            }
            drinkCode=scan.nextInt();

            if(drinkCode >= 1 && drinkCode <= 20) {

                dCodeArr[count] = drinkCode;
                count++;

                for(int i=0;i<count-1;i++)
                {
                    if(dCodeArr[i]==drinkCode){
                        count--;
                        System.out.println("같은 음료가 이미 세팅되어 있습니다.");
                        break;
                    }
                }

                if(count == 7) {
                    scan.nextLine();
                    break;
                }
            }
            else {
                System.out.println("번호는 01~20만 입력하세요");
            }
        }
        myDVM.saveDrinkKinds(dCodeArr);
    }

    public void receiveMsg(Message msg) {  //myDVM.checkStock() 구현 봐야 함
        String msgType = msg.getMsgType();
        dCode = msg.getMsgDescription().getItemCode();
        count = msg.getMsgDescription().getItemNum();
        switch(msgType){
            case "StockCheckRequest":
                if(myDVM.checkStock(Integer.parseInt(dCode), count)) {
                    myMessageManager.sendResMsg("StockCheckResponse", dCode, count, myDVM.getId(), myDVM.getLocation());
                }
                break;
            case "SalesCheckRequest":
                if(myDVM.checkStock(Integer.parseInt(dCode), count)){
                    if(myDVM.updateStock(Integer.parseInt(dCode), count)){
                        myMessageManager.sendResMsg("SalesCheckResponse", dCode, myDVM.getId(), myDVM.getLocation());
                    }
                }
                break;
            case "PrepaymentCheck":
                myDVM.saveVerificationCode(msg.getMsgDescription().getAuthCode(), Integer.parseInt(dCode), count);
                break;
            case "StockCheckResponse":
            case "SalesCheckResponse":
                myMessage.add(msg);
                break;
        }
    }

    public void getOutDrink(int Calc) {
        int dCode_ = Calc % 100;
        int count_ = Calc / 100;

        Item item = myDVM.getItemList()[dCode_ -1];

        System.out.println("음료: " + item.getName() + ", " + count_ + "개");
        System.out.println("음료가 모두 배출되었습니다.\n 감사합니다.");
    }

    public int calculateTotalPrice() {
        return count * myDVM.getItemPrice(Integer.parseInt(dCode));
    }
}