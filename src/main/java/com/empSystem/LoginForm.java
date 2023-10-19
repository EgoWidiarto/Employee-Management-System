package com.empSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginForm extends JFrame implements ActionListener{

    JTextField txtUser;
    JPasswordField txtPassword;
    JButton login;
    LoginForm() {
        JLabel headingLog = new JLabel("Login Form");
        headingLog.setBounds(230, 5, 150, 30);
        headingLog.setFont(new Font("Poppins", Font.BOLD, 16));
        add(headingLog);

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
        
        ImageIcon imgPath = new ImageIcon("src/main/resources/img/profile.png");
        Image imgScale = imgPath.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        ImageIcon img = new ImageIcon(imgScale);
        JLabel image = new JLabel(img);
        image.setBounds(320, 15, 200, 200);
        add(image);

        //  Set Button Style
        //  Set Button Style
        login = new JButton("Login");
        login.setBounds(240, 180, 80,35);
        login.setBackground(Color.decode("#22668D"));
        login.setForeground(Color.decode("#FFFADD"));
        login.addActionListener(this);
        add(login);

        //  Set Screen Size And Screen Location
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setSize(600, 300);
        setLocation(420, 170);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            Connection connection = Conn.getConnection();
            String username = txtUser.getText();
            String password = txtPassword.getText();

            // Encrypt the password
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());
            byte[] hash = messageDigest.digest();
            String hashedPassword = String.format("%064x", new java.math.BigInteger(1, hash));

            // Run a SQL query
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, hashedPassword);
            ResultSet resultSet = statement.executeQuery();

            // Iterate through the result set
            if (resultSet.next()) {
                new Home();
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Username atau Password Salah!!");
            }

            // Close the connection
            connection.close();
            //  Ceck Data From DataBase

        } catch (Exception ae) {
            ae.printStackTrace();
        }
    }

    public static void main(String[] arg){
        new LoginForm();
    }
}
