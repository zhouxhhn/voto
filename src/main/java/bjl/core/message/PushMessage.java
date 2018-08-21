package bjl.core.message;

import bjl.application.chat.representation.ApiPushChatRepresentation;
import bjl.core.api.MessageID;
import bjl.core.util.ByteUtils;
import bjl.core.util.CoreDateUtils;
import bjl.tcp.GlobalVariable;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.util.Date;

/**
 * TCP消息推送
 * Created by zhangjin on 2017/11/10.
 */
public class PushMessage {


    /**
     * 推送聊天信息
     * @param chatRepresentation
     */
    public static void pushChat(ApiPushChatRepresentation chatRepresentation){
        Channel channel = GlobalVariable.getChannelHandler().get("client");
        if(channel != null){
            //消息返回
            ByteBuf returnByteBuf = Unpooled.buffer();
            byte[] strBytes = JSONObject.toJSONBytes(chatRepresentation);
            byte[] headBytes = ByteUtils.intToByteArray(strBytes.length);
            byte[] msgRBytes = ByteUtils.shortToByteArray(MessageID.RADIOINFO_);
            returnByteBuf.writeBytes(headBytes);
            returnByteBuf.writeBytes(msgRBytes);
            returnByteBuf.writeBytes(strBytes);
            channel.writeAndFlush(returnByteBuf);
            System.out.println(CoreDateUtils.formatDateTime(new Date())+" 推送聊天信息成功:"+JSONObject.toJSONString(chatRepresentation));
        }else {
            System.out.println(CoreDateUtils.formatDateTime(new Date())+" 连接为空或已断开，无法广播信息:"+JSONObject.toJSONString(chatRepresentation));
        }
    }

    /**
     * 通用推送
     * @param jsonObject
     */
    public static void ordinaryPush(JSONObject jsonObject,short messageID){
        Channel channel = GlobalVariable.getChannelHandler().get("client");
        if(channel != null){
            //消息返回
            ByteBuf returnByteBuf = Unpooled.buffer();
            byte[] strBytes = JSONObject.toJSONBytes(jsonObject);
            byte[] headBytes = ByteUtils.intToByteArray(strBytes.length);
            byte[] msgRBytes = ByteUtils.shortToByteArray(messageID);
            returnByteBuf.writeBytes(headBytes);
            returnByteBuf.writeBytes(msgRBytes);
            returnByteBuf.writeBytes(strBytes);
            channel.writeAndFlush(returnByteBuf);
            System.out.println(CoreDateUtils.formatDateTime(new Date())+" 推送消息成功:"+jsonObject.toJSONString());
        }else {
            System.out.println(CoreDateUtils.formatDateTime(new Date())+" 连接为空或已断开，无法推送消息:"+messageID+",数据:"+jsonObject.toJSONString());
        }
    }
}
