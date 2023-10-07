package com.empSystem;

import javax.swing.*;
import java.awt.*;
import java.security.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SignUp extends JFrame implements ActionListener{

    JTextField txtUser;
    JPasswordField txtPassword;
    JButton login, sign;
    SignUp() {
        JLabel headingSign = new JLabel("Sign Up Form");
        headingSign.setBounds(230, 5, 150, 30);
        headingSign.setFont(new Font("Poppins", Font.BOLD, 16));
        add(headingSign);

        JLabel username = new JLabel("username");
        username.setBounds(60, 60, 60, 20);
        add(username);

        txtUser = new JTextField();
        txtUser.setBounds(150, 60, 150, 30);
        add(txtUser);

        JLabel password = new JLabel("Password");
        password.setBounds(60, 105, 60, 20);
        add(password);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 105, 150, 30);
        add(txtPassword);

        ImageIcon imgPath = new ImageIcon(ClassLoader.getSystemResource("img/profile.png"));
        Image imgScale = imgPath.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        ImageIcon imgIcon = new ImageIcon(imgScale);
        JLabel image = new JLabel(imgIcon);
        image.setBounds(320, 15, 200, 200);
        add(image);

        //  Set Button Style
        login = new JButton("Login");
        login.setBounds(150, 190, 110, 25);
        login.setBackground(Color.decode("#22668D"));
        login.setForeground(Color.decode("#FFFADD"));
        login.addActionListener(this);
        add(login);

        sign = new JButton("Sign Up");
        sign.setBounds(300, 190, 110, 25);
        sign.setBackground(Color.decode("#22668D"));
        sign.setForeground(Color.decode("#FFFADD"));
        sign.addActionListener(this);
        add(sign);

        //  Set Screen Size And Screen Location
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setSize(600, 300);
        setLocation(420, 170);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sign) {
            try {
                Connection conn = Conn.getConnection();
                String username = txtUser.getText();
                String password = txtPassword.getText();

                // Encrypt the password
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                messageDigest.update(password.getBytes());
                byte[] hash = messageDigest.digest();
                String hashedPassword = String.format("%064x", new java.math.BigInteger(1, hash));

                // Run a SQL query
                String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, username);
                statement.setString(2, hashedPassword);
                ResultSet resultSet = statement.executeQuery();

                setVisible(false);
                new LoginForm();

                // Close the connection
                conn.close();
            } catch (Exception ae) {
                ae.printStackTrace();
            }
        } else {
            setVisible(false);
            new LoginForm();
        }
    }

    public static void main(String[] arg){
        new SignUp();
    }
}
