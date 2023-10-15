package com.empSystem;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewEmployee extends JFrame implements ActionListener {
    JTable table = new JTable();
    JTextField txtEmp;
    JButton search, print, update, back;

    JComboBox<String> comboDepSearch = new JComboBox<>();
    ViewEmployee() {

        JLabel headingView = new JLabel("Lihat Data Pegawai");
        headingView.setBounds(340, 20, 250, 30);
        headingView.setFont(new Font("Poppins", Font.BOLD, 20));
        add(headingView);

        txtEmp = new JTextField();
        txtEmp.setBounds(600, 20, 270, 20);
        add(txtEmp);

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 100, 720, 500);
        add(jsp);

        // Search Employee From Their Department
        JLabel depart = new JLabel("Filter Department");
        depart.setBounds(740, 100, 180, 30);
        add(depart);
        depart.setFont(new Font("Poppins", Font.PLAIN, 14));
        comboDepSearch.setBounds(740, 140, 180, 30);
        comboDepSearch.setBackground(Color.WHITE);
        try {
            Connection connection = Conn.getConnection();
            String query = "SELECT dname FROM department";
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String name = rs.getString("dname");
                comboDepSearch.addItem(name);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        comboDepSearch.setSelectedIndex(-1);
        add(comboDepSearch);

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
        setSize(950, 650);
        setLocation(190, 50);
        setVisible(true);
        viewEmployee(null);
    }

    public void viewEmployee(String ssn) {
            String section = "name, bdate, ssn, address, sex, salary,";
            String querySQL = "";
            if (ssn != null && !ssn.trim().isEmpty()) {
                // Jika SSN diberikan, cari pegawai dengan SSN tersebut
                querySQL = "SELECT " + section + " d.dname FROM employee e JOIN department d ON e.dep_id = d.dep_id WHERE ssn = ?";
            } else if (comboDepSearch.getSelectedIndex() > -1) {
                // Jika tidak, tampilkan semua pegawai
                querySQL = "SELECT " + section + " d.dname FROM employee e JOIN department d ON e.dep_id = d.dep_id WHERE dname = ?";
            } else {
                querySQL = "SELECT " + section + " d.dname FROM employee e JOIN department d ON e.dep_id = d.dep_id";
            }

            try {
                Connection conn = Conn.getConnection();
                if (conn == null) {
                    System.out.println("Koneksi ke database gagal");
                    return;
                }
                PreparedStatement stmt = conn.prepareStatement(querySQL);
                String dname = (String) comboDepSearch.getSelectedItem();

                if (ssn != null && !ssn.trim().isEmpty()) {
                    stmt.setString(1, ssn); // Atur parameter SSN
                } else if (comboDepSearch.getSelectedIndex() != -1) {
                    stmt.setString(1, dname);
                }
                ResultSet result = stmt.executeQuery();

                if (table == null) {
                    System.out.println("Tabel belum diinisialisasi");
                    return;
                }

                table.setModel(DbUtils.resultSetToTableModel(result));
            } catch (Exception ax) {
                ax.printStackTrace();
            }
        }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            viewEmployee(txtEmp.getText());
        } else if (ae.getSource() == print) {
            try {
                table.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == update) {
            setVisible(false);
            new UpdateEmp(txtEmp.getText());
        } else {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] arg) {
        new Splash();
    }
}