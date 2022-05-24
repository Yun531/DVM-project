public class Item {
    private int code;
    private String name;
    private int price;
    private int stock;

    public Item(int code) {
        this.code = code + 1;
        switch(this.code) {
            case 1:
                name = "콜라";
                break;
            case 2:
                name = "사이다";
                break;
            case 3:
                name = "녹차";
                break;
            case 4:
                name = "홍차";
                break;
            case 5:
                name = "밀크티";
                break;
            case 6:
                name = "탄산수";
                break;
            case 7:
                name = "보리차";
                break;
            case 8:
                name = "캔커피";
                break;
            case 9:
                name = "물";
                break;
            case 10:
                name = "에너지드링크";
                break;
            case 11:
                name = "바닷물";
                break;
            case 12:
                name = "식혜";
                break;
            case 13:
                name = "아이스티";
                break;
            case 14:
                name = "딸기주스";
                break;
            case 15:
                name = "오렌지주스";
                break;
            case 16:
                name = "포도주스";
                break;
            case 17:
                name = "이온음료";
                break;
            case 18:
                name = "아메리카노";
                break;
            case 19:
                name = "핫초코";
                break;
            case 20:
                name = "카페라떼";
                break;
        }
        price = 1000;
        stock = 10;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }

    public boolean checkStock(int count) {
        if (count > stock)
            return false;
        else
            return true;
    }

    public boolean updateStock(int count) {
        if (count > stock)
            return false;
        else {
            stock -= count;
            return true;
        }
    }
}