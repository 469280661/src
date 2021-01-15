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
 * ��¼�߳�
 */
public class LoginThread extends Thread {
    private JFrame loginf;

    private JTextField t;

    public void run() {
        /*
         * ���õ�¼����
         */
        loginf = new JFrame();
        loginf.setResizable(false);
        loginf.setLocation(300, 200);
        loginf.setSize(400, 150);
        loginf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginf.setTitle("������" + " - ��¼");

        t = new JTextField("Version " + "1.1.0" + "        By liwei");
        t.setHorizontalAlignment(JTextField.CENTER);
        t.setEditable(false);
        loginf.getContentPane().add(t, BorderLayout.SOUTH);

        JPanel loginp = new JPanel(new GridLayout(3, 2));
        loginf.getContentPane().add(loginp);

        JTextField t1 = new JTextField("��¼��:");
        t1.setHorizontalAlignment(JTextField.CENTER);
        t1.setEditable(false);
        loginp.add(t1);

        final JTextField loginname = new JTextField("liu");
        loginname.setHorizontalAlignment(JTextField.CENTER);
        loginp.add(loginname);

        JTextField t2 = new JTextField("����:");
        t2.setHorizontalAlignment(JTextField.CENTER);
        t2.setEditable(false);
        loginp.add(t2);

        final JTextField loginPassword = new JTextField("liu123");
        loginPassword.setHorizontalAlignment(JTextField.CENTER);
        loginp.add(loginPassword);
        /*
         * �����˳���ť(�����ڲ���)
         */
        JButton b1 = new JButton("��  ��");
        loginp.add(b1);
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sql = "SELECT password FROM users WHERE username=?";
                String url = "jdbc:oracle:thin:@localhost:1521:orcl";
                String username = "oooo";
                String password = "ooo1234";
                Connection conn = null;//ͨ��getConnection()������ú����ݿ�����
                try {
                    conn = DriverManager.getConnection(url, username, password);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                PreparedStatement/*���Ʊ���*/ pstmt = null;//SQL ��䱻Ԥ���벢�Ҵ洢�� PreparedStatement ������
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

        final JButton b2 = new JButton("��  ¼");
        loginp.add(b2);

        loginf.setVisible(true);

        /**
         * ������,����"��¼"Button�ĵ����TextField�Ļس�
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
                            InetAddress addr = InetAddress.getLocalHost();//��ȡIP
                            System.out.println("����IP��ַ:"+addr.getHostAddress()) ;//������һ��һ���
                            sql="update users set ip=?,port=8888 where username=?";//�൱��ִ�в�ѯIP
                            pstmt=conn.prepareStatement(sql);//����sql
                            pstmt.setString(1,addr.getHostAddress());//����IP
                            pstmt.setString(2,username);//�����û���
                            loginf.setVisible(false);
                           ChatThreadWindow chatThreadWindow=new ChatThreadWindow();
                        } else{
                            System.out.println("��¼ʧ��");
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
				1�������û�ȥ���ݿ�Ѽ��ܺ�������õ�
				SELECT password FROM users WHERE username='liwei';
				2���ѵ�¼�����������������ݿ�����ܺ�Ľ��бȶԣ�����MD5���checkpassword������
				 */
            }
        }
        ButtonListener bl = new ButtonListener();
        b2.addActionListener(bl);
        loginname.addActionListener(bl);
        loginPassword.addActionListener(bl);
    }
}