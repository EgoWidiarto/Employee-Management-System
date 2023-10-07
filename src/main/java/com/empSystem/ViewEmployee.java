package com.empSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class ViewEmployee extends JFrame implements ActionListener {
    JTable table;
    Choice choiceEmpId;
    JButton search, print, update, back;
    ViewEmployee() {

        JLabel headingView = new JLabel("Lihat Data Pegawai");
        headingView.setBounds(340, 20, 250, 30);
        headingView.setFont(new Font("Poppins", Font.BOLD, 20));
        add(headingView);

        choiceEmpId = new Choice();
        choiceEmpId.setBounds(600, 20, 270, 20);
        add(choiceEmpId);

        try {
            Connection conn = Conn.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM Employee");

            while(result.next()){
                choiceEmpId.add(result.getString("ssn"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        table = new JTable();

        try {
            Connection conn = Conn.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM Employee");
            table.setModel(DbUtils.resultSetToTableModel(result));

            while(result.next()){
                choiceEmpId.add(result.getString("ssn"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 100, 900, 550);
        add(jsp);

        search = new JButton("Cari");
        search.setBounds(20, 70, 80, 20);
        search.setBackground(Color.decode("#22668D"));
        search.setForeground(Color.decode("#FFFADD"));
        search.addActionListener(this);
        add(search);

        print = new JButton("Cetak");
        print.setBounds(120, 70, 80, 20);
        print.setBackground(Color.decode("#22668D"));
        print.setForeground(Color.decode("#FFFADD"));
        print.addActionListener(this);
        add(print);

        update = new JButton("Perbarui");
        update.setBounds(220, 70, 120, 20);
        update.setBackground(Color.decode("#22668D"));
        update.setForeground(Color.decode("#FFFADD"));
        update.addActionListener(this);
        add(update);

        back = new JButton("Kembali");
        back.setBounds(360, 70, 80, 20);
        back.setBackground(Color.decode("#22668D"));
        back.setForeground(Color.decode("#FFFADD"));
        back.addActionListener(this);
        add(back);

        //  Set Screen Size And Screen Location
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setSize(900, 650);
        setLocation(190, 50);
        setVisible(true);
    }

    public void viewEmployee() {
        String querySQL = "SELECT * FROM employee WHERE ssn = " + choiceEmpId.getSelectedItem();
        try {
            Connection conn = Conn.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(querySQL);
            table.setModel(DbUtils.resultSetToTableModel(result));
        } catch (Exception ax) {
            ax.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            viewEmployee();
        } else if (ae.getSource() == print) {
            try {
                table.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == update) {
            setVisible(false);
            new UpdateEmp(choiceEmpId.getSelectedItem());
        } else {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] arg) {
        new ViewEmployee();
    }
}