package empSystem.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Splash extends JFrame implements ActionListener {
    Splash() {
        //  Set Background Color
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        //  Set Label Heading For SplashCreen
        JLabel heading = new JLabel("SISTEM MANAJEMEN PEGAWAI");
        heading.setBounds(180, 25, 470, 30);
        heading.setFont(new Font("Poppins", Font.PLAIN, 30));
        add(heading);

        //  Set Button Style
        JButton clickToContinue = new JButton("Masuk");
        clickToContinue.setBounds(320, 360, 120, 40);
        clickToContinue.setBackground(Color.decode("#001524"));
        clickToContinue.setForeground(Color.decode("#FDE5D4"));
        clickToContinue.addActionListener(this);
        add(clickToContinue);

        ImageIcon imgPath = new ImageIcon("src/main/resources/img/ruangMeeting.jpg");
        Image imgScale = imgPath.getImage().getScaledInstance(800, 550, Image.SCALE_DEFAULT);
        ImageIcon imgIcon = new ImageIcon(imgScale);
        JLabel image = new JLabel(imgIcon);
        image.setBounds(0, 40, 800, 650);
        add(image);

        //  Set Screen Size And Screen Location
        setSize(800, 550);
        setLocation(280, 90);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        new LoginForm();
        setVisible(false);
    }

    public static void main(String[] arg) {
        new Splash();
    }
}
