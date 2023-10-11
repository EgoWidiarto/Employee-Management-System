package com.empSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Home extends JFrame implements ActionListener {

    JButton add, view, update, remove;

    Home() {
        // Set heading Label
        JLabel homeHeading = new JLabel("Sistem Manajemen Pegawai");
        homeHeading.setBounds(270, 30, 500, 30);
        homeHeading.setFont(new Font("Poppins", Font.BOLD, 27));
        homeHeading.setForeground(Color.decode("#EEEDED"));
        add(homeHeading);

        // Setup Font
        Font font = new Font("Poppins", Font.BOLD, 18);

        // Button Home Screen
        // Add Pegawai
        add = new JButton("Tambah Pegawai");
        add.setBounds(310, 170, 330, 50);
        add.setBackground(Color.decode("#213555"));
        add.setForeground(Color.decode("#F0F0F0"));
        add.setFont(font);
        add.addActionListener(this);
        add(add);
        // View Pegawai
        view = new JButton("Lihat Pegawai");
        view.setBounds(310, 230, 330, 50);
        view.setBackground(Color.decode("#213555"));
        view.setForeground(Color.decode("#F0F0F0"));
        view.setFont(font);
        view.addActionListener(this);
        add(view);
        // Update Pegawai
        update = new JButton("Perbarui Pegawai");
        update.setBounds(310, 290, 330, 50);
        update.setBackground(Color.decode("#213555"));
        update.setForeground(Color.decode("#F0F0F0"));
        update.setFont(font);
        update.addActionListener(this);
        add(update);
        // Remove Pegawai
        remove = new JButton("Hapus Pegawai");
        remove.setBounds(310, 350, 330, 50);
        remove.setBackground(Color.decode("#213555"));
        remove.setForeground(Color.decode("#F0F0F0"));
        remove.setFont(font);
        remove.addActionListener(this);
        add(remove);

        // Set Background Image
        ImageIcon imgPath = new ImageIcon("src/main/resources/img/ruangMeja.jpg");
        Image imgScale = imgPath.getImage().getScaledInstance(1000, 500, Image.SCALE_DEFAULT);
        ImageIcon img = new ImageIcon(imgScale);
        JLabel image = new JLabel(img);
        image.setBounds(0, 0, 1000, 500);
        add(image);

        //  Set Screen Size And Screen Location
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setSize(1000, 500);
        setLocation(190, 100);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == add){
            new AddEmployee();
            setVisible(false);
        }else if (ae.getSource() == view) {
            new ViewEmployee();
            setVisible(false);
        } else if (ae.getSource() == update) {
            new UpdateEmp("");
            setVisible(false);
        } else {
            new RemoveEmp();
            setVisible(false);
        }
    }

    public static void main(String[] arg){
        new Splash();
    }
}
