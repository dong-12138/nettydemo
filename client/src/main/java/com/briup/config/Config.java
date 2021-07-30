package com.briup.config;


import com.briup.RpcClient;

import java.io.FileInputStream;
import java.util.Properties;

public class Config {
    private static Config config = null;
    private final Properties properties;

    public static Config getInstanse() throws Exception{
        if (config == null){
            synchronized (Config.class){
                String path = RpcClient.class.getResource("").getPath().split("/com")[0]+"/application.properties";
                FileInputStream fis = new FileInputStream(path);
                Properties properties = new Properties();
                properties.load(fis);
                if (config == null){
                    config = new Config(properties);
                }
            }
        }
        return config;
    }
    private Config(Properties properties) {
        this.properties = properties;
    }

    public String getServerIp(){
        return properties.get("server.ip") == null?"127.0.0.1":(String) properties.get("server.ip");
    }

    public Integer getServerPort(){
        return properties.get("server.port") == null?8899:Integer.parseInt((String) properties.get("server.port"));
    }

    public Integer getConnectTimeout(){
        return properties.get("connect.timeout") == null?30000:Integer.parseInt((String) properties.get("connect.timeout"));
    }
}
