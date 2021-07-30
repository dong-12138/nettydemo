package com.briup.handler;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


public class EncoderHandler extends MessageToByteEncoder {
    protected void encode(ChannelHandlerContext channelHandlerContext,Object o,ByteBuf byteBuf) throws Exception {
        System.out.println("encoder to write out : "+o);
        byte[] bytes = (JSONObject.toJSONString(o)+"\n").getBytes();
        byteBuf.writeBytes(bytes);
    }
}
