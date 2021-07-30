package com.briup;



import com.briup.dto.User;
import com.briup.interfaces.UserService;
import com.briup.utils.RpcUtil;

public class RpcClient {
    public static void main(String[] args) {
        UserService service = RpcUtil.createService(UserService.class,"1.0");
        User login = service.login(new User("admin","admin",20,"1.0"));
        System.out.println(login);
    }
}
