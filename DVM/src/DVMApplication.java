import DVM_Server.DVMServer;

class MyDVMServer implements Runnable {
    DVMServer server;

    MyDVMServer(DVMServer server) {
        this.server = server;
    }

    @Override
    public void run() {
        try {
            System.out.println("Server is running ...");
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class DVMApplication {
    public static void main(String[] args) throws InterruptedException {
        DVMServer server = new DVMServer();
        Thread myDVMServerThread = new Thread(new MyDVMServer(server));
        myDVMServerThread.start();

        Controller controller = new Controller();
        Thread.sleep(5000);
        System.out.println("waiting ...");

        MessageManager messageManager = new MessageManager();


        while (true) {
            if (server.msgList.size() > 0) {
                controller.receiveMsg(server.msgList.get(server.msgList.size() - 1));
                Thread.sleep(5000);
                server.msgList.remove(server.msgList.size() - 1);

            } else {
                System.out.println("not");
            }
        }
    }

    // Message example
    // {"srcId":"4","dstID":"3","msgType":"StockCheckResponse","msgDescription":{"itemCode":"10","itemNum":0,"dvmXCoord":55,"dvmYCoord":555,"authCode":""}}
    // {"srcId":"4","dstID":"2","msgType":"SalesCheckResponse","msgDescription":{"itemCode":"9","itemNum":1,"dvmXCoord":55,"dvmYCoord":555,"authCode":""}}
    // {"srcId":"4","dstID":"0","msgType":"StockCheckRequest","msgDescription":{"itemCode":"10","itemNum":5,"dvmXCoord":0,"dvmYCoord":0,"authCode":""}}
}
