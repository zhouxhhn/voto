package bjl.tcp;

import bjl.core.message.MessageCoding;
import bjl.core.util.ByteUtils;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * channel handler
 * Created by zhangjin on 2017/7/4.
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //channel handler必须继承 ChannelInboundHandlerAdapter并且重写channelRead方法，
    // 这个方法在任何时候都会被调用来接收数据
    @Override
    public void channelRead(ChannelHandlerContext context, Object message) throws Exception{

        //业务处理
        BusinessThreadUtil.doBusiness(context,message);

//        //提取消息
//        ByteBuf byteBuf = (ByteBuf) message;
//        int length = byteBuf.readableBytes();
//        ByteBuf msg = byteBuf.slice(0, 2);
//        ByteBuf body  = byteBuf.slice(2,length-2);
//        byte[] msgBytes = new byte[msg.readableBytes()];
//        byte[] bodyBytes = new byte[body.readableBytes()];
//        if (!byteBuf.hasArray()) {
//            msg.getBytes(0, msgBytes);
//            body.getBytes(0, bodyBytes);
//        } else {
//            msgBytes = msg.array();
//            bodyBytes = body.array();
//        }
//        //消息处理
//
//        short messageID = ByteUtils.byteArrayToShort(msgBytes);
//        logger.info("消息处理");
//        JSONObject response = MessageCoding.messageShunt(messageID, bodyBytes);
//        if(response != null){
//            //消息返回
//            ByteBuf returnByteBuf = Unpooled.buffer();
//            byte[] strBytes = JSONObject.toJSONBytes(response);
//            byte[] headBytes = ByteUtils.intToByteArray(strBytes.length);
//            byte[] msgRBytes = ByteUtils.shortToByteArray((short)(messageID+1000));
////        System.out.println("发送消息号："+ByteUtils.byteArrayToInt(msgBytes));
//            returnByteBuf.writeBytes(headBytes);
//            returnByteBuf.writeBytes(msgRBytes);
//            returnByteBuf.writeBytes(strBytes);
//            context.writeAndFlush(returnByteBuf);
//        }
//        logger.info("消息处理完成");
    }

    //使用多个Channel Handler来达到对事件处理的分离
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
//        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    //重写ChannelHandler的exceptionCaught方法可以捕获服务器的异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("客服端已关闭连接。。。");
        ctx.close();
    }
}
