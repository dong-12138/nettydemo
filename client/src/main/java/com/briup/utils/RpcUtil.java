package com.briup.utils;


import java.lang.reflect.Proxy;


public class RpcUtil{

    public static <T> T createService(Class interfaceClass, String version) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new ObjectProxy(interfaceClass, version)
        );
    }

}
