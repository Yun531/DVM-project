import DVM_Client.DVMClient;
import GsonConverter.Serializer;
import Model.Message;

public class MessageManager {

    public MessageManager() {}

    private Serializer mySerializer = new Serializer();
    String host = "1.235.230.3";

    public void sendResMsg(String type, String dCode, int count, String dstId, Location myLocation) {
        // (dstsrc) type: "StockCheckResponse" == 재고확인응답

        // 1. raw --> Message
        Message message = new Message();
        message.setSrcId("Team4");
        message.setDstID(dstId);
        message.setMsgType(type);
        Message.MessageDescription messageDescription = new Message.MessageDescription();
        messageDescription.setItemCode(dCode);
        messageDescription.setItemNum(count);
        messageDescription.setDvmXCoord(myLocation.getX());
        messageDescription.setDvmYCoord(myLocation.getY());
        messageDescription.setAuthCode("");
        message.setMsgDescription(messageDescription);

        // 2. Message --> json
        String jsonMsg = mySerializer.message2Json(message);

        try {
            DVMClient myDVMClient = new DVMClient(host, jsonMsg);
            // Todo: host 대신 수신측 ip 세팅해야 함
            myDVMClient.run();
        } catch (Exception exception) {
            System.out.println("request 실패");
        }
    }

    public void sendResMsg(String type, String dCode, String dstId, Location myLocation) {
        // (dstsrc) type: "SalesCheckResponse" == 음료판매응답

        // 1. raw --> Message
        Message message = new Message();
        message.setSrcId("Team4");
        message.setDstID(dstId);
        message.setMsgType(type);
        Message.MessageDescription messageDescription = new Message.MessageDescription();
        messageDescription.setItemCode(dCode);
        // messageDescription.setItemNum("");
        messageDescription.setDvmXCoord(myLocation.getX());
        messageDescription.setDvmYCoord(myLocation.getY());
        messageDescription.setAuthCode("");
        message.setMsgDescription(messageDescription);

        // 2. Message --> json
        String jsonMsg = mySerializer.message2Json(message);

        try {
            DVMClient myDVMClient = new DVMClient("1.235.230.3", jsonMsg);
            // Todo: host 대신 수신측 ip 세팅해야 함
            myDVMClient.run();
        } catch (Exception exception) {
            System.out.println("request 실패");
        }
    }

    public void sendReqMsg(String type, String dCode, int count) {
        // (broadcast) type: "StockCheckRequest" == 재고확인요청
        // (broadcast) type: "SalesCheckRequest" == 음료판매확인

        // 1. raw --> Message
        Message message = new Message();
        message.setSrcId("Team4");
        message.setDstID("0"); // broadcast
        message.setMsgType(type);
        Message.MessageDescription messageDescription = new Message.MessageDescription();
        messageDescription.setItemCode(dCode);
        messageDescription.setItemNum(count);
        // Todo: 수신측 좌표 (x, y) 세팅? broadcast 어떻게
        messageDescription.setAuthCode("");
        message.setMsgDescription(messageDescription);

        // 2. Message --> json
        String jsonMsg = mySerializer.message2Json(message);
        System.out.println(jsonMsg);

        try {
            DVMClient myDVMClient = new DVMClient("1.235.230.3", jsonMsg);
            // Todo: host 대신 수신측 ip 세팅해야 함
            myDVMClient.run();
        } catch (Exception exception) {
            System.out.println("request 실패");
        }

    }

    public void sendReqMsg(String type, String dCode, int count, String vCode) {
        // (srcdst) type: "PrepaymentCheck" == 선결제확인

        // 1. raw --> Message
        Message message = new Message();
        message.setSrcId("Team4");
        // Todo: 수신측 id 세팅
        message.setMsgType(type);
        Message.MessageDescription messageDescription = new Message.MessageDescription();
        messageDescription.setItemCode(dCode);
        messageDescription.setItemNum(count);
        // Todo: 수신측 좌표 (x, y) 세팅?
        messageDescription.setAuthCode(vCode);
        message.setMsgDescription(messageDescription);

        // 2. Message --> json
        String jsonMsg = mySerializer.message2Json(message);

        try {
            DVMClient myDVMClient = new DVMClient("1.235.230.3", jsonMsg);
            // Todo: host 대신 수신측 ip 세팅해야
            myDVMClient.run();
        } catch (Exception exception) {
            System.out.println("request 실패");
        }
    }
}