import java.util.LinkedList;

public class DVM {
    private String id;
    private Location location;
    private Item[] itemList;
    private LinkedList<String> vCodeList = new LinkedList<String>();
    private LinkedList<String> prepayItemList = new LinkedList<String>();
    private String adminPassword;

    public DVM() {
        this.location = new Location(0,0);
        this.itemList = new Item[20];
        for(int i = 0; i < 20 ; ++i){
            this.itemList[i] = new Item(i);
        }
        this.id = "Team4";
        this.adminPassword = "1q2w3e4r";
    }

    public String getId(){
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public Item[] getItemList() {
        return itemList;
    }

    public int getItemPrice(int dCode){
        return itemList[dCode - 1].getPrice();
    }

    public boolean checkStock(int dCode, int Count) {
        return itemList[dCode - 1].checkStock(Count);
    }

    public boolean updateStock(int dCode, int Count) {
        return itemList[dCode - 1].updateStock(Count);
    }

    public boolean saveVerificationCode(String vCode, int dCode, int Count) {
        int temp = Count * 100 + dCode;
        vCodeList.add(vCode);
        prepayItemList.add(Integer.toString(temp));
        return false;
    }

    public boolean isValidVerificationCode(String vCode) {
        return vCodeList.contains(vCode);
    }

    public int reqVerificationCodeItem(String vCode) {
        int index = vCodeList.indexOf(vCode);
        vCodeList.remove(index);
        String temp = prepayItemList.get(index);
        int Calc = Integer.parseInt(temp);
        int dCode = Calc%100;
        int Count = Calc/100;
        // getOutDrink 가 dCode랑 Count 정보를 필요로 할 것 같은데..?
        return Calc; //int dCode = Calc%100, int Count = Calc/100
    }

    public boolean isValidPassword(String pw) {
        return pw==this.adminPassword;
    }

    public void saveDVMInfo(String id, Location location) {
        this.id = id;
        this.location.setX(location.getX());
        this.location.setY(location.getY());
    }

    public void saveDrinkInfo(int dCode, int price, int stock, String name) {
        itemList[dCode - 1].setPrice(price);
        itemList[dCode - 1].setStock(stock);
        itemList[dCode - 1].setName(name);
    }

    public void saveDrinkKinds(int[] dCode) {
        // 판매할 7개 짜리 int배열 넘겨주심
        // 받아오는 형식과 저장하는 방식의 문제
        // 팔 음료 코드 7개 - , 나머지 13개 item에 대해 itemList에 stock = -1 << 안판다
        boolean[] temp = new boolean[20];

        for (int i = 0; i < 7 ; ++i) {
            temp[dCode[i]-1] = true;
        }

        for (int i = 0; i < 20 ; ++i) {
            if (!temp[i]) {
                itemList[i].setStock(-1);
            } else {
                if(itemList[i].getStock() == -1) itemList[i].setStock(0);
            }
        }
    }
}