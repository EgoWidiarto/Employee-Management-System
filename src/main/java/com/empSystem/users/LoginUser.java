package empSystem.users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginUser extends JFrame implements ActionListener{

    JTextField txtName;
    JPasswordField txtSsn;
    JButton login;
    LoginUser() {
        JLabel headingLog = new JLabel("Login Form");
        headingLog.setBounds(230, 5, 150, 30);
        headingLog.setFont(new Font("Poppins", Font.BOLD, 16));
        add(headingLog);

        JLabel username = new JLabel("Nama");
        username.setBounds(60, 60, 60, 20);
        add(username);

        txtName = new JTextField();
        txtName.setBounds(150, 60, 150, 30);
        add(txtName);

        JLabel password = new JLabel("NIP");
        password.setBounds(60, 105, 60, 20);
        add(password);

        txtSsn = new JPasswordField();
        txtSsn.setBounds(150, 105, 150, 30);
        add(txtSsn);

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
            // Get the username and password from the text fields
            String username = txtName.getText();
            String password = txtSsn.getText();

            // Run a SQL query
            String sql = "SELECT * FROM employee WHERE name = ? AND ssn = ?";

            // Use try-with-resources to automatically close the resources after use
            try (Connection connection = Conn.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, username);
                statement.setString(2, password);

                try (ResultSet resultSet = statement.executeQuery()) {
                    // Iterate through the result set
                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(null, "Login Berhasil!!");
                        new UserViewCard(password);
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Username atau Password Salah!!");
                    }
                }
            }
        } catch (Exception ae) {
            ae.printStackTrace();
        }
    }

    public static void main(String[] arg){
        new LoginUser();
    }
}
