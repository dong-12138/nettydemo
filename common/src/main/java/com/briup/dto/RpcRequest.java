package com.briup.dto;

import java.util.Arrays;

public class RpcRequest {
    private String interfaceName;
    private String methodName;
    private String version;
    private Object[] body;
    private Class<?>[] parameterTypes;

    public RpcRequest() {
    }

    public RpcRequest(String interfaceName,String methodName,String version,Object[] body,Class<?>[] parameterTypes) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.version = version;
        this.body = body;
        this.parameterTypes = parameterTypes;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Object[] getBody() {
        return body;
    }

    public void setBody(Object[] body) {
        this.body = body;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    @Override
    public String toString() {
        return "RpcRequest{" +
                "interfaceName='" + interfaceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", version='" + version + '\'' +
                ", body=" + Arrays.toString(body) +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                '}';
    }
}
