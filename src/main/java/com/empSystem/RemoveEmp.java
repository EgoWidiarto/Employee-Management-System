package com.empSystem;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;

public class RemoveEmp extends JFrame implements ActionListener {
    Choice choiceSSN;
    JButton delete, back;
    RemoveEmp(){
        JLabel empId = new JLabel("SSN Pegawai");
        empId.setBounds(50, 50, 100, 30);
        add(empId);

        // Setup Border Style
        Border line = BorderFactory.createLineBorder(Color.decode("#0e0e0e"));
        Border padding = new EmptyBorder(0, 10, 0, 0);
        Border compounBorder = new CompoundBorder(line, padding);

        choiceSSN = new Choice();
        choiceSSN.setBounds(200, 50, 100, 30);
        add(choiceSSN);

        try {
            Connection conn = Conn.getConnection();
            String query = "SELECT * FROM employee";
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while(result.next()) {
                choiceSSN.add(result.getString("ssn"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel labName = new JLabel("Nama");
        labName.setBounds(50, 100, 150, 30);
        add(labName);
        JLabel lbNametxt = new JLabel();
        lbNametxt.setBounds(200, 100, 150, 30);
        lbNametxt.setBorder(compounBorder);
        add(lbNametxt);

        JLabel labbdate = new JLabel("Tanggal Lahir");
        labbdate.setBounds(50, 170, 150, 30);
        add(labbdate);
        JLabel lbBdatetxt = new JLabel();
        lbBdatetxt.setBounds(200, 170, 150, 30);
        lbBdatetxt.setBorder(compounBorder);
        add(lbBdatetxt);

        JLabel labAddress = new JLabel("Alamat");
        labAddress.setBounds(50, 240, 100, 30);
        add(labAddress);
        JLabel lbAddresstxt = new JLabel();
        lbAddresstxt.setBounds(200, 240, 150, 30);
        lbAddresstxt.setBorder(compounBorder);
        add(lbAddresstxt);

        JLabel labsex = new JLabel("Gender");
        labsex.setBounds(500, 100, 100, 30);
        add(labsex);
        JLabel lbSextxt = new JLabel();
        lbSextxt.setBounds(650, 100, 100, 30);
        lbSextxt.setBorder(compounBorder);
        add(lbSextxt);

        JLabel labsalary = new JLabel("Gaji");
        labsalary.setBounds(500, 170, 100, 30);
        add(labsalary);
        JLabel lbsalarytxt = new JLabel();
        lbsalarytxt.setBounds(650, 170, 100, 30);
        lbsalarytxt.setBorder(compounBorder);
        add(lbsalarytxt);

        JLabel labdep = new JLabel("Tanggal Lahir");
        labdep.setBounds(500, 240, 100, 30);
        add(labdep);
        JLabel lbdeptxt = new JLabel();
        lbdeptxt.setBounds(650, 240, 100, 30);
        lbdeptxt.setBorder(compounBorder);
        add(lbdeptxt);

        try {
            String section = "name, bdate, ssn, address, sex, salary,";
            Connection conn = Conn.getConnection();
            String query = "SELECT " + section + "FROM employee e JOIN department d ON e.dep_id = d.dep_id WHERE ssn = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, choiceSSN.getSelectedItem());  // asumsikan empId adalah integer
            ResultSet result = pstmt.executeQuery();

            while(result.next()) {
                lbNametxt.setText(result.getString("name"));
                lbBdatetxt.setText(result.getString("bdate"));
                lbAddresstxt.setText(result.getString("address"));
                lbSextxt.setText(result.getString("sex"));
                lbsalarytxt.setText(result.getString("salary"));
                lbdeptxt.setText(result.getString("dname"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        choiceSSN.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                try {
                    Connection conn = Conn.getConnection();
                    String query = "SELECT * FROM employee WHERE ssn = ?";
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, choiceSSN.getSelectedItem());  // asumsikan empId adalah integer
                    ResultSet result = pstmt.executeQuery();

                    while(result.next()) {
                        lbNametxt.setText(result.getString("name"));
                        lbBdatetxt.setText(result.getString("bdate"));
                        lbAddresstxt.setText(result.getString("address"));
                        lbSextxt.setText(result.getString("sex"));
                        lbsalarytxt.setText(result.getString("salary"));
                        lbdeptxt.setText(result.getString("department"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        delete = new JButton("Hapus Data");
        delete.setBounds(300, 370, 100, 30);
        delete.setBackground(Color.decode("#BB2525"));
        delete.setForeground(Color.decode("#FFF5E0"));
        delete.addActionListener(this);
        add(delete);

        back = new JButton("Kembali");
        back.setBounds(450, 370, 100, 30);
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

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == delete) {
            try {
                removeEmployee((String) choiceSSN.getSelectedItem());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            new Home();
            setVisible(false);
        }
    }

    public boolean removeEmployee(String ssn) throws Exception {
        Connection conn = Conn.getConnection();
        String query = "DELETE FROM employee WHERE ssn = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, ssn);
        int rowsDeleted = pstmt.executeUpdate();
        conn.close();

        if (rowsDeleted > 0) {
            JOptionPane.showMessageDialog(null, "Employee Data Deleted Successfully");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No Employee Data Found to Delete");
            return false;
        }
    }

    public static void main(String[] arg){
        new Splash();
    }
}
