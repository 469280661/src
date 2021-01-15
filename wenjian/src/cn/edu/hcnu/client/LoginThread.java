package cn.edu.hcnu.client;

import cn.edu.hcnu.utill.MD5;
import com.sun.deploy.panel.RuleSetViewerDialog;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * 登录线程
 */
public class LoginThread extends Thread {
    private JFrame loginf;

    private JTextField t;

    public void run() {
        /*
         * 设置登录界面
         */
        loginf = new JFrame();
        loginf.setResizable(false);
        loginf.setLocation(300, 200);
        loginf.setSize(400, 150);
        loginf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginf.setTitle("聊天室" + " - 登录");

        t = new JTextField("Version " + "1.1.0" + "        By liwei");
        t.setHorizontalAlignment(JTextField.CENTER);
        t.setEditable(false);
        loginf.getContentPane().add(t, BorderLayout.SOUTH);

        JPanel loginp = new JPanel(new GridLayout(3, 2));
        loginf.getContentPane().add(loginp);

        JTextField t1 = new JTextField("登录名:");
        t1.setHorizontalAlignment(JTextField.CENTER);
        t1.setEditable(false);
        loginp.add(t1);

        final JTextField loginname = new JTextField("liu");
        loginname.setHorizontalAlignment(JTextField.CENTER);
        loginp.add(loginname);

        JTextField t2 = new JTextField("密码:");
        t2.setHorizontalAlignment(JTextField.CENTER);
        t2.setEditable(false);
        loginp.add(t2);

        final JTextField loginPassword = new JTextField("liu123");
        loginPassword.setHorizontalAlignment(JTextField.CENTER);
        loginp.add(loginPassword);
        /*
         * 监听退出按钮(匿名内部类)
         */
        JButton b1 = new JButton("退  出");
        loginp.add(b1);
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sql = "SELECT password FROM users WHERE username=?";
                String url = "jdbc:oracle:thin:@localhost:1521:orcl";
                String username = "oooo";
                String password = "ooo1234";
                Connection conn = null;//通过getConnection()方法获得和数据库链接
                try {
                    conn = DriverManager.getConnection(url, username, password);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                PreparedStatement/*编制报表*/ pstmt = null;//SQL 语句被预编译并且存储在 PreparedStatement 对象中
                try {
                    pstmt = conn.prepareStatement(sql);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                try {
                    pstmt.setString(1,username );
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                try {
                    ResultSet rs = pstmt.executeQuery();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        final JButton b2 = new JButton("登  录");
        loginp.add(b2);

        loginf.setVisible(true);

        /**
         * 监听器,监听"登录"Button的点击和TextField的回车
         */
        class ButtonListener implements ActionListener {
            private Socket s;

            public void actionPerformed(ActionEvent e) {
                String username = loginname.getText();
                String password = loginPassword.getText();
                try {
                    String url = "jdbc:oracle:thin:@localhost:1521:orcl";
                    String username_db = "oooo";
                    String password_db = "ooo1234";
                    Connection conn = DriverManager.getConnection(url, username_db, password_db);
                    String sql = "SELECT password FROM users WHERE username=?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1,username);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        String encodePassword = rs.getString("PASSWORD");
                        if (MD5.checkpassword(password,encodePassword)) {
                            InetAddress addr = InetAddress.getLocalHost();//获取IP
                            System.out.println("本机IP地址:"+addr.getHostAddress()) ;//跟上面一行一起的
                            sql="update users set ip=?,port=8888 where username=?";//相当与执行查询IP
                            pstmt=conn.prepareStatement(sql);//声明sql
                            pstmt.setString(1,addr.getHostAddress());//声明IP
                            pstmt.setString(2,username);//声明用户名
                            loginf.setVisible(false);
                           ChatThreadWindow chatThreadWindow=new ChatThreadWindow();
                        } else{
                            System.out.println("登录失败");
                        }
                    }
                } catch (SQLException ee) {
                    ee.printStackTrace();
                } catch (NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                } catch (UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                } catch (UnknownHostException ex) {
                    ex.printStackTrace();
                }
				/*
				1、根据用户去数据库把加密后的密码拿到
				SELECT password FROM users WHERE username='liwei';
				2、把登录界面输入的密码和数据库里加密后的进行比对（调用MD5类的checkpassword方法）
				 */
            }
        }
        ButtonListener bl = new ButtonListener();
        b2.addActionListener(bl);
        loginname.addActionListener(bl);
        loginPassword.addActionListener(bl);
    }
}