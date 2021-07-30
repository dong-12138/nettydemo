package com.briup.config;

import com.briup.startup.RpcServer;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigProperties {
    private static ConfigProperties config = null;
    private final Properties properties;

    public static ConfigProperties getInstanse() throws Exception{
        if (config == null){
            synchronized (ConfigProperties.class){
                String path = RpcServer.class.getResource("").getPath().split("/com")[0]+"/application.properties";
                FileInputStream fis = new FileInputStream(path);
                Properties properties = new Properties();
                properties.load(fis);
                if (config == null){
                    config = new ConfigProperties(properties);
                }
            }
        }
        return config;
    }
    private ConfigProperties(Properties properties) {
        this.properties = properties;
    }

    public Integer getServerPort(){
        return properties.get("server.port") == null?8899:Integer.parseInt((String) properties.get("server.port"));
    }

    public Integer getConnectTimeout(){
        return properties.get("connect.timeout") == null?30000:Integer.parseInt((String) properties.get("connect.timeout"));
    }
}
