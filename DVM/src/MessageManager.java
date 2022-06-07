import DVM_Client.DVMClient;
import GsonConverter.Serializer;
import Model.Message;

/**
 * about network MESSAGE
 * - msg 요청
 * - msg 응답
 * - DVM ip 조회.
 */
public class MessageManager {
    private MessageManager() {}

    private static MessageManager uniqueMessageManager;
    private Serializer mySerializer = new Serializer();

    public static MessageManager getInstance() {
        if (uniqueMessageManager == null) {
            uniqueMessageManager = new MessageManager();
        }
        return uniqueMessageManager;
    }

    /**
     * (dstsrc) type: "StockCheckResponse" == 재고확인응답
     */
    public void sendResMsg(String type, String dCode, int count, String dstId, Location myLocation) {
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
            DVMClient myDVMClient = new DVMClient(setDVMIp(dstId), jsonMsg);
            myDVMClient.run();
        } catch (Exception exception) {
            System.out.println("request 실패");
        }
    }

    /**
     * (dstsrc) type: "SalesCheckResponse" == 음료판매응답
     */
    public void sendResMsg(String type, String dCode, String dstId, Location myLocation) {
        // 1. raw --> Message
        Message message = new Message();
        message.setSrcId("Team4");
        message.setDstID(dstId);
        message.setMsgType(type);
        Message.MessageDescription messageDescription = new Message.MessageDescription();
        messageDescription.setItemCode(dCode);
        messageDescription.setDvmXCoord(myLocation.getX());
        messageDescription.setDvmYCoord(myLocation.getY());
        messageDescription.setAuthCode("");
        message.setMsgDescription(messageDescription);

        // 2. Message --> json
        String jsonMsg = mySerializer.message2Json(message);

        try {
            DVMClient myDVMClient = new DVMClient(setDVMIp(dstId), jsonMsg);
            myDVMClient.run();
        } catch (Exception exception) {
            System.out.println("request 실패");
        }
    }

    /**
     * (broadcast) type: "StockCheckRequest" == 재고확인요청
     * (broadcast) type: "SalesCheckRequest" == 음료판매확인
     */
    public void sendReqMsg(String type, String dCode, int count) {
        // 1. raw --> Message
        Message message = new Message();
        message.setSrcId("Team4");
        message.setDstID("0"); // broadcast
        message.setMsgType(type);
        Message.MessageDescription messageDescription = new Message.MessageDescription();
        messageDescription.setItemCode(dCode);
        messageDescription.setItemNum(count);
        messageDescription.setAuthCode("");
        message.setMsgDescription(messageDescription);

        // 2. Message --> json
        String jsonMsg = mySerializer.message2Json(message);
        System.out.println(jsonMsg);

        try {
            DVMClient myDVMClient1 = new DVMClient("Team1", jsonMsg);
            DVMClient myDVMClient2 = new DVMClient("Team2", jsonMsg);
            DVMClient myDVMClient3 = new DVMClient("Team3", jsonMsg);
            DVMClient myDVMClient5 = new DVMClient("Team5", jsonMsg);
            DVMClient myDVMClient6 = new DVMClient("Team6", jsonMsg);
            myDVMClient1.run();
            myDVMClient2.run();
            myDVMClient3.run();
            myDVMClient5.run();
            myDVMClient6.run();
        } catch (Exception exception) {
            System.out.println("request 실패");
        }
    }

    /**
     * (srcdst) type: "PrepaymentCheck" == 선결제확인
     */
    public void sendReqMsg(String type, String dCode, int count, String vCode, String dstId) {
        // 1. raw --> Message
        Message message = new Message();
        message.setSrcId("Team4");
        message.setDstID(dstId);
        message.setMsgType(type);
        Message.MessageDescription messageDescription = new Message.MessageDescription();
        messageDescription.setItemCode(dCode);
        messageDescription.setItemNum(count);
        messageDescription.setAuthCode(vCode);
        message.setMsgDescription(messageDescription);

        // 2. Message --> json
        String jsonMsg = mySerializer.message2Json(message);

        try {
            DVMClient myDVMClient = new DVMClient(setDVMIp(dstId), jsonMsg);
            myDVMClient.run();
        } catch (Exception exception) {
            System.out.println("request 실패");
        }
    }

    public String setDVMIp(String teamId) {
        String ip;
        switch (teamId) {
            case "Team1": ip = "192.168.1";
                break;
            case "Team2": ip = "192.168.2";
                break;
            case "Team3": ip = "192.168.3";
                break;
            case "Team5": ip = "192.168.5";
                break;
            case "Team6": ip = "192.168.6";
                break;
            default:
                System.out.println("dst dvm의 Team Id가 잘못 설정되어 있습니다.");
                ip = "";
                break;
        } return ip;
    }
}