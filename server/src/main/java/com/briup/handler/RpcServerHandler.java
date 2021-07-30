package com.briup.handler;

import com.alibaba.fastjson.JSONObject;
import com.briup.dto.RpcRequest;
import com.briup.dto.RpcResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;
import java.util.HashMap;

@ChannelHandler.Sharable //表示该实例可以在channel里共享,即单例模式
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final HashMap<String,Object> serviceMap = new HashMap<>();
    public static void addService(String version,Class interfaceClass,Object service){
        serviceMap.put(interfaceClass.getName()+version,service);
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext,RpcRequest rpcRequest) throws Exception {
        System.out.println(rpcRequest);
        System.out.println(rpcRequest.getInterfaceName()+rpcRequest.getVersion());
        Object o = serviceMap.get(rpcRequest.getInterfaceName() + rpcRequest.getVersion());
        Object invoke;
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        if ( parameterTypes!= null && parameterTypes.length>0){
            Object[] body = rpcRequest.getBody();
            Object[] param = new Object[parameterTypes.length];
            for (int i = 0;i < param.length;i++){
                param[i] = JSONObject.toJavaObject((JSONObject)body[i],parameterTypes[i]);
            }
            invoke = o.getClass().getMethod(rpcRequest.getMethodName(),parameterTypes).invoke(o,param);
        }else {
            invoke = o.getClass().getMethod(rpcRequest.getMethodName()).invoke(o);
        }
        channelHandlerContext.writeAndFlush(new RpcResponse(1,"请求成功",invoke));
    }

}
