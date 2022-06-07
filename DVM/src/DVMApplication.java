import DVM_Server.DVMServer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * about SERVER.
 */
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

/**
 * main APP.
 */
public class DVMApplication {
    public static void main(String[] args) throws InterruptedException {
        DVMServer server = new DVMServer();
        Thread myDVMServerThread = new Thread(new MyDVMServer(server));
        myDVMServerThread.start();

        Controller controller = new Controller();
        Thread.sleep(5000);
        System.out.println("waiting ...");

        MessageManager messageManager = new MessageManager();

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(()-> {
            while (true) {
                Thread.sleep(100);
                if (server.msgList.size() > 0) {
                    System.out.println("msg received");
                    controller.receiveMsg(server.msgList.get(server.msgList.size() - 1));
                    Thread.sleep(1000);
                    System.out.println("msg removed");
                    server.msgList.remove(server.msgList.size() - 1);
                }
            }
        });

        while(true) {
            controller.showMenu();
        }
    }
}
