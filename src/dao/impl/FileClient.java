package dao.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/*
TCP/IP协议：有2个卷
 */
public class FileClient {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("192.168.1.91", 9999);
            FileOutputStream fos = new FileOutputStream("E://1.txt");
            InputStream is = socket.getInputStream();
            byte buff[] = new byte[1024];
            int a = 0;
            while ((a = is.read(buff)) != -1) {
                fos.write(buff, 0, a);
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
