package com.briup.utils;

import com.alibaba.fastjson.JSONObject;
import com.briup.config.Config;
import com.briup.dto.RpcRequest;
import com.briup.dto.RpcResponse;
import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

public class ObjectProxy implements InvocationHandler {

    private Class interfaceClass;
    private String version;

    public ObjectProxy(Class interfaceClass,String version) {
        this.interfaceClass = interfaceClass;
        this.version = version;
    }

    public Object invoke(Object proxy,Method method,Object[] args) throws Throwable {
        return JSONObject.toJavaObject((JSONObject)sendRequest(interfaceClass.getName(),method.getName(),version,args),method.getReturnType());
    }

    private static Object sendRequest(String interfaceName,String methodName,String version,Object[] args) throws Exception{
        Class[] classes = null;
        if (args != null && args.length > 0 ){
            classes = new Class[args.length];
            for (int i = 0;i < classes.length;i++){
                classes[i] = args[i].getClass();
            }
        }
        RpcRequest request = new RpcRequest(interfaceName,methodName,version,args,classes);
        Socket socket = null;
        try {
            socket = new Socket(Config.getInstanse().getServerIp(),Config.getInstanse().getServerPort());
            socket.setKeepAlive(false);
            OutputStream outputStream = socket.getOutputStream();
            byte[] bytes = JSONObject.toJSONString(request).getBytes();
            byte[] bytes1 = new byte[bytes.length + 4];
            int length = bytes.length;
            System.arraycopy(bytes,0,bytes1,4,bytes.length);
            bytes1[0]= (byte) (length>>24);
            bytes1[1]=(byte) (length>>16);
            bytes1[2]=(byte) (length>>8);
            bytes1[3]=(byte) length;
            outputStream.write(bytes1);
            outputStream.flush();
            System.out.println("request id finished :" + bytes.length);
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String s = reader.readLine();
            RpcResponse response = JSONObject.parseObject(s,RpcResponse.class);
            System.out.println("response is success code:"+response.getCode()+" and msg :"+response.getMsg());
            return response.getData();
        }finally {
            if (socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
