package bjl.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteOrder;

/**
 *
 * Created by zhangjin on 2017/7/4.
 */
public class EchoServer implements Runnable{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    @Override
    public void run(){
        EventLoopGroup group = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //1.创建ServerBootstrap实例来引导绑定和启动服务器
            ServerBootstrap bootstrap = new ServerBootstrap();
            //2.指定NioEventLoopGroup来接受和处理新连接
            //3.指定通道类型为NioServerSocketChannel
            //4.设置InetSocketAddress让服务器监听某个端口已等待客户端连接
            //5.调用childHandler放来指定连接后调用的ChannelHandler,
            //  这个方法传ChannelInitializer类型的参数，ChannelInitializer是个抽象类，
            //  所以需要实现initChannel方法，这个方法就是用来设置ChannelHandler
            bootstrap.group(group,workerGroup).channel(NioServerSocketChannel.class).localAddress(port).childHandler(new ChannelInitializer<Channel>() {
                protected void initChannel(Channel channel) throws Exception {
                    channel.pipeline().addLast(new LengthFieldBasedFrameDecoder(ByteOrder.BIG_ENDIAN,Integer.MAX_VALUE,0,4,2,4,true));
                    channel.pipeline().addLast(new EchoServerHandler());
                    logger.info("TCP-客服端【"+channel.remoteAddress()+"】已新建连接.........");
                    //保存ChannelHandlerContext 推送消息时使用

                    GlobalVariable.addChannelHandler("client",channel);


                }
            });
            //6.最后绑定服务器等待直到绑定完成
            ChannelFuture future = bootstrap.bind().sync();
            logger.info(EchoServer.class.getName() + "started and lis ten on “" + future.channel().localAddress());
            //服务器等待通道关闭，因为使用sync()，所以关闭操作也会被阻塞
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            System.out.println("TCP服务启动失败");
        } finally {
            //关闭EventLoopGroup和释放所有资源，包括创建的线程
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                System.out.println("释放资源失败");
            }
        }
    }
}
