package bjl.tcp;

import bjl.core.api.MessageID;
import bjl.core.message.MessageCoding;
import bjl.core.util.ByteUtils;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangjin on 2018/6/5
 */
public class BusinessThreadUtil {

    private static final ExecutorService executor = new ThreadPoolExecutor(2, 10, 100L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(100));//CPU核数4-10倍

    public static void doBusiness(ChannelHandlerContext context, Object message) {

        //提取消息
        ByteBuf byteBuf = (ByteBuf) message;
        int length = byteBuf.readableBytes();
        ByteBuf msg = byteBuf.slice(0, 2);
        ByteBuf body  = byteBuf.slice(2,length-2);
        byte[] msgBytes = new byte[msg.readableBytes()];
        byte[] bodyBytes = new byte[body.readableBytes()];
        if (!byteBuf.hasArray()) {
            msg.getBytes(0, msgBytes);
            body.getBytes(0, bodyBytes);
        } else {
            msgBytes = msg.array();
            bodyBytes = body.array();
        }
        //消息处理
        short messageID = ByteUtils.byteArrayToShort(msgBytes);
        final byte[] bytes = bodyBytes;
        executor.submit( () -> {
            JSONObject response = MessageCoding.messageShunt(messageID, bytes);
            if(response != null){
                //消息返回
                ByteBuf returnByteBuf = Unpooled.buffer();
                byte[] strBytes = JSONObject.toJSONBytes(response);
                byte[] headBytes = ByteUtils.intToByteArray(strBytes.length);
                byte[] msgRBytes = ByteUtils.shortToByteArray((short)(messageID+1000));
                returnByteBuf.writeBytes(headBytes);
                returnByteBuf.writeBytes(msgRBytes);
                returnByteBuf.writeBytes(strBytes);
                context.writeAndFlush(returnByteBuf);
            }
        });

//        if(MessageID.GETGAMEURL_ == messageID){
//            //耗时业务，异步线程池处理
//            final byte[] bytes = bodyBytes;
//            executor.submit( () -> {
//                JSONObject response = MessageCoding.messageShunt(messageID, bytes);
//                if(response != null){
//                    //消息返回
//                    ByteBuf returnByteBuf = Unpooled.buffer();
//                    byte[] strBytes = JSONObject.toJSONBytes(response);
//                    byte[] headBytes = ByteUtils.intToByteArray(strBytes.length);
//                    byte[] msgRBytes = ByteUtils.shortToByteArray((short)(messageID+1000));
//                    returnByteBuf.writeBytes(headBytes);
//                    returnByteBuf.writeBytes(msgRBytes);
//                    returnByteBuf.writeBytes(strBytes);
//                    context.writeAndFlush(returnByteBuf);
//                }
//            });
//        }else {
//
//            JSONObject response = MessageCoding.messageShunt(messageID, bodyBytes);
//            if(response != null){
//                //消息返回
//                ByteBuf returnByteBuf = Unpooled.buffer();
//                byte[] strBytes = JSONObject.toJSONBytes(response);
//                byte[] headBytes = ByteUtils.intToByteArray(strBytes.length);
//                byte[] msgRBytes = ByteUtils.shortToByteArray((short)(messageID+1000));
//                returnByteBuf.writeBytes(headBytes);
//                returnByteBuf.writeBytes(msgRBytes);
//                returnByteBuf.writeBytes(strBytes);
//                context.writeAndFlush(returnByteBuf);
//            }
//        }

    }

}
