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

        // Button Home Screen
        // Add Pegawai
        add = new JButton("Tambah Pegawai");
        add.setBounds(280, 170, 150, 35);
        add.setBackground(Color.decode("#213555"));
        add.setForeground(Color.decode("#F0F0F0"));
        add.addActionListener(this);
        add(add);
        // View Pegawai
        view = new JButton("Lihat Pegawai");
        view.setBounds(280, 240, 150, 35);
        view.setBackground(Color.decode("#213555"));
        view.setForeground(Color.decode("#F0F0F0"));
        view.addActionListener(this);
        add(view);
        // Update Pegawai
        update = new JButton("Perbarui Pegawai");
        update.setBounds(480, 170, 150, 35);
        update.setBackground(Color.decode("#213555"));
        update.setForeground(Color.decode("#F0F0F0"));
        update.addActionListener(this);
        add(update);
        // Remove Pegawai
        remove = new JButton("Hapus Pegawai");
        remove.setBounds(480, 240, 150, 35);
        remove.setBackground(Color.decode("#213555"));
        remove.setForeground(Color.decode("#F0F0F0"));
        remove.addActionListener(this);
        add(remove);

        // Set Background Image
        ImageIcon imgPath = new ImageIcon(ClassLoader.getSystemResource("img/ruangMeja.jpg"));
        Image imgScale = imgPath.getImage().getScaledInstance(1000, 500, Image.SCALE_DEFAULT);
        ImageIcon imgIcon = new ImageIcon(imgScale);
        JLabel image = new JLabel(imgIcon);
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
            setVisible(false);
            new AddEmployee();
        }else if (ae.getSource() == view) {
            setVisible(false);
            new ViewEmployee();
        } else if (ae.getSource() == update) {
            setVisible(false);
            new UpdateEmp("");
        } else {
            setVisible(false);
            new RemoveEmp();
        }
    }

    public static void main(String[] arg){
        new Home();
    }
}
