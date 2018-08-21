package bjl.tcp;


import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhangjin on 2017/10/14.
 */
public class StartTcp {

    private final EchoServer echoServer;

    @Autowired
    public StartTcp(EchoServer echoServer) {
        this.echoServer = echoServer;
    }

    public void start() {
        try {
            //启动netty服务
            new Thread(echoServer).start();
        } catch (Exception e){
            System.out.println("-------------TCP服务启动失败,"+e.getMessage()+"-------------");
        }
    }
}
