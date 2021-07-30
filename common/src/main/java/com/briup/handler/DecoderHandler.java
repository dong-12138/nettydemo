package com.briup.handler;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


public class DecoderHandler extends ByteToMessageDecoder {

    private final Class request;

    public DecoderHandler(Class request) {
        this.request = request;
    }

    protected void decode(ChannelHandlerContext channelHandlerContext,ByteBuf byteBuf,List<Object> list) throws Exception {
        System.out.println("receive byte code to decode"+byteBuf.toString());
        if (byteBuf.readableBytes() < 4) {
            return;
        }
        byteBuf.markReaderIndex();
        int dataLength = byteBuf.readInt();
        if (byteBuf.readableBytes() < dataLength) {
            byteBuf.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        byteBuf.readBytes(data);
        try {
            String json = new String(data);
            list.add(JSONObject.parseObject(json,request));
        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }
}
