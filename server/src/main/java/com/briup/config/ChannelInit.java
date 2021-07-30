package com.briup.config;

import com.briup.dto.RpcRequest;
import com.briup.handler.DecoderHandler;
import com.briup.handler.EncoderHandler;
import com.briup.handler.RpcServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class ChannelInit extends ChannelInitializer<SocketChannel> {

    /**依次加入对应的处理器，netty会按照顺序调用对应的handler
     * 这里为了模拟RPC，所以
     * 1、 首先根据 字节，转化成请求类，即解码
     * 2、 根据请求数据类调用对应的服务方法
     * 3、 对返回体进行对象转二进制码返回
     * */
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //IdleStateHandler netty提供的连接超时类
        socketChannel.pipeline().addLast(new IdleStateHandler(0, 0, ConfigProperties.getInstanse().getConnectTimeout(), TimeUnit.MILLISECONDS))
                //LengthFieldBasedFrameDecoder 给出不想要的头部内容，在转二进制时先写了个 bytes数组的长度，长度时int类型，所以从0往后四个长度跳过
                .addLast(new LengthFieldBasedFrameDecoder(2147483647, 0, 4, 0, 0))
                //自定义的解码器，接受的是上一handler过滤下来的值
                .addLast(new DecoderHandler(RpcRequest.class))
                //将结果编码返回
                .addLast(new EncoderHandler())
                /**
                 * 1、InboundHandler是按照Pipleline的加载顺序，顺序执行。
                 * 2、OutboundHandler是按照Pipeline的加载顺序，逆序执行。
                 * Pipleline是执行完所有有效的InboundHandler，再返回执行在最后一个InboundHandler之前的OutboundHandler。
                 * 注意，有效的InboundHandler是指fire事件触达到的InboundHandler，
                 * 如果某个InboundHandler没有调用fire事件，后面的InboundHandler都是无效的InboundHandler。为了印证这一点，
                 * 我们继续做一个实验，我们把其中一个OutboundHandler放在最后一个有效的InboundHandler之前，
                 * 看看这唯一的一个OutboundHandler是否会执行，其他OutboundHandler是否不会执行。
                 *
                 * 解码继承自outHandler，所以要放在业务处理逻辑之前，否则不会被执行
                 */
                //自定义处理handler
                .addLast(new RpcServerHandler());
    }
}
