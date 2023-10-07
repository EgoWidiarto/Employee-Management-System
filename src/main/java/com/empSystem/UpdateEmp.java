package com.empSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;


public class UpdateEmp extends JFrame implements ActionListener {
    JTextField txtAddress, txtSex, txtSalary, txtDep;
    JLabel txtName, txtBdate, txtSsn;
    JButton submit, view, back;
    String empId;
    UpdateEmp(String empId) {
        this.empId = empId;
        JFrame frame = new JFrame("Centered Button");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        // Create Laber For Heading
        JLabel empHeading = new JLabel("Perbarui Data Pegawai");
        empHeading.setBounds(290, 30, 500, 27);
        empHeading.setFont(new Font("Poppins", Font.BOLD, 25));
        add(empHeading);

        // Text Field
        JLabel empName = new JLabel("Name");
        empName.setBounds(60, 120, 100, 30);
        empName.setFont(new Font("Poppins", Font.PLAIN, 18));
        add(empName);
        txtName = new JLabel();
        txtName.setBounds(220, 120, 180, 30);
        add(txtName);

        JLabel empBdate = new JLabel("Tanggal Lahir");
        empBdate.setBounds(60, 180, 150, 30);
        empBdate.setFont(new Font("Poppins", Font.PLAIN, 18));
        add(empBdate);
        txtBdate = new JLabel();
        txtBdate.setBounds(220, 180, 180, 30);
        add(txtBdate);

        JLabel empSsn = new JLabel("SSN");
        empSsn.setBounds(60, 240, 150, 30);
        empSsn.setFont(new Font("Poppins", Font.PLAIN, 18));
        add(empSsn);
        txtSsn = new JLabel();
        txtSsn.setBounds(220, 240, 180, 30);
        add(txtSsn);

        try {
            Connection conn = Conn.getConnection();
            String query = "SELECT * FROM employee WHERE ssn = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, empId);  // asumsikan empId adalah integer
            ResultSet result = pstmt.executeQuery();

            while(result.next()) {
                txtName.setText(result.getString("name"));
                txtBdate.setText(result.getString("bdate"));
                txtSsn.setText(result.getString("ssn"));
                txtAddress.setText(result.getString("address"));
                txtSex.setText(result.getString("sex"));
                txtSalary.setText(result.getString("salary"));
                txtDep.setText(result.getString("department"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel empAdres = new JLabel("Alamat");
        empAdres.setBounds(60, 300, 150, 30);
        empAdres.setFont(new Font("Poppins", Font.PLAIN, 18));
        add(empAdres);
        txtAddress = new JTextField();
        txtAddress.setBounds(220, 300, 180, 30);
        add(txtAddress);

        JLabel empSex = new JLabel("Gender");
        empSex.setBounds(450, 120, 150, 30);
        empSex.setFont(new Font("Poppins", Font.PLAIN, 18));
        add(empSex);
        txtSex = new JTextField();
        txtSex.setBounds(600, 120, 180, 30);
        add(txtSex);

        JLabel empSalary = new JLabel("Gaji");
        empSalary.setBounds(450, 180, 150, 30);
        empSalary.setFont(new Font("Poppins", Font.PLAIN, 18));
        add(empSalary);
        txtSalary = new JTextField();
        txtSalary.setBounds(600, 180, 180, 30);
        add(txtSalary);

        JLabel empDep = new JLabel("Department");
        empDep.setBounds(450, 240, 150, 30);
        empDep.setFont(new Font("Poppins", Font.PLAIN, 18));
        add(empDep);
        txtDep = new JTextField();
        txtDep.setBounds(600, 240, 180, 30);
        add(txtDep);

        submit = new JButton("Submit");
        submit.setBounds(280, 410, 100, 40);
        submit.setBackground(Color.decode("#22668D"));
        submit.setForeground(Color.decode("#FFFADD"));
        submit.addActionListener(this);
        add(submit);

        view = new JButton("Lihat Pegawai");
        view.setBounds(400, 410, 140, 40);
        view.setBackground(Color.decode("#22668D"));
        view.setForeground(Color.decode("#FFFADD"));
        view.addActionListener(this);
        add(view);

        back = new JButton("Kembali");
        back.setBounds(560, 410, 100, 40);
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

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            try {
                // Get a connection from your Conn class
                Connection conn = Conn.getConnection();

                // Parse Text To Date
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsed = format.parse(txtBdate.getText());
                java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());

                // Prepare an SQL statement
                String query = "UPDATE employee SET name = ?, bdate = ?, ssn = ?, address = ?, sex = ?, salary = ?, department = ? WHERE ssn = ?";
                PreparedStatement stmt = conn.prepareStatement(query);

// Set the values from the text fields
                stmt.setString(1, txtName.getText());
                stmt.setDate(2, sqlDate);
                stmt.setString(3, txtSsn.getText());
                stmt.setString(4, txtAddress.getText());
                stmt.setString(5, txtSex.getText());
                stmt.setFloat(6, Float.parseFloat(txtSalary.getText()));
                stmt.setString(7, txtDep.getText());
                stmt.setString(8, txtSsn.getText());  // assuming you want to update the record of this ssn

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Data update successfully");
                    txtName.setText("");
                    txtBdate.setText("");
                    txtSsn.setText("");
                    txtAddress.setText("");
                    txtSex.setText("");
                    txtSalary.setText("");
                    txtDep.setText("");
                }

                // Close the connection
                conn.close();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        } else if (e.getSource() == view) {
            setVisible(false);
            new ViewEmployee();
        } else {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] arg){
        new UpdateEmp("");
    }
}
