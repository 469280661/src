package cn.edu.hcnu.client1;

import cn.edu.hcnu.client.LoginThread;

//�����ҿͻ���
public class Client1 {
    //������:������¼�߳�
    public static void main(String[] args) throws Exception {
        Thread login = new LoginThread();
        login.start();
    }
}