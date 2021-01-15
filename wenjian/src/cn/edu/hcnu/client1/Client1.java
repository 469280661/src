package cn.edu.hcnu.client1;

import cn.edu.hcnu.client.LoginThread;

//聊天室客户端
public class Client1 {
    //主方法:启动登录线程
    public static void main(String[] args) throws Exception {
        Thread login = new LoginThread();
        login.start();
    }
}