package empSystem.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;


public class AddEmployee extends JFrame implements ActionListener {
    JTextField txtName, txtBdate, txtSsn, txtAddress, txtSalary;
    JComboBox  comboDep, comboSex;
    JButton submit, view, back;
    AddEmployee() {
        // Create Laber For Heading
        JLabel empHeading = new JLabel("Tambahkan Pegawai Baru");
        empHeading.setBounds(290, 30, 500, 27);
        empHeading.setFont(new Font("Poppins", Font.BOLD, 25));
        add(empHeading);

        // Text Field Dan Label
        JLabel empName = new JLabel("Nama");
        empName.setBounds(60, 120, 100, 30);
        empName.setFont(new Font("Poppins", Font.PLAIN, 18));
        add(empName);
        txtName = new JTextField();
        txtName.setBounds(220, 120, 180, 30);
        add(txtName);

        JLabel empBdate = new JLabel("Tanggal Lahir");
        empBdate.setBounds(60, 180, 150, 30);
        empBdate.setFont(new Font("Poppins", Font.PLAIN, 18));
        add(empBdate);
        txtBdate = new JTextField();
        txtBdate.setBounds(220, 180, 180, 30);
        add(txtBdate);

        JLabel empSsn = new JLabel("NIP");
        empSsn.setBounds(60, 240, 150, 30);
        empSsn.setFont(new Font("Poppins", Font.PLAIN, 18));
        add(empSsn);
        txtSsn = new JTextField();
        txtSsn.setBounds(220, 240, 180, 30);
        add(txtSsn);

        JLabel empAdres = new JLabel("Alamat");
        empAdres.setBounds(60, 300, 150, 30);
        empAdres.setFont(new Font("Poppins", Font.PLAIN, 18));
        add(empAdres);
        txtAddress = new JTextField();
        txtAddress.setBounds(220, 300, 180, 30);
        add(txtAddress);

        JLabel empSex = new JLabel("Jenis Kelamin");
        empSex.setBounds(450, 120, 150, 30);
        empSex.setFont(new Font("Poppins", Font.PLAIN, 18));
        add(empSex);
        String[] empSexChoice = {"L", "P"};
        comboSex = new JComboBox<>(empSexChoice);
        comboSex.setSelectedIndex(-1);
        comboSex.setBounds(600, 120, 180, 30);
        comboSex.setBackground(Color.WHITE);
        add(comboSex);

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
        comboDep = new JComboBox();
        try {
            Connection connection = Conn.getConnection();
            String query = "SELECT dname FROM department";
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String name = rs.getString("dname");
                comboDep.addItem(name);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        comboDep.setBounds(600, 240, 180, 30);
        comboDep.setBackground(Color.WHITE);
        comboDep.setSelectedIndex(-1);
        add(comboDep);

        // Button Style
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
                addEmp();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        } else if (e.getSource() == view) {
            new ViewEmployee();
            setVisible(false);
        } else {
            new Home();
            setVisible(false);
        }
    }

    public void addEmp() {
        try (Connection conn = Conn.getConnection()) {
            // Convert String To Date
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsed = format.parse(txtBdate.getText());
            java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());

            // Select dep_id From ComboBox
            String selectedDepName = (String)comboDep.getSelectedItem();
            String query = "SELECT dep_id FROM department WHERE dname = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, selectedDepName);
            ResultSet rs = pst.executeQuery();
            String depId = null;
            if (rs.next()) {
                depId = rs.getString("dep_id");
            }

            // Convert Salary To Big Decimal
            BigDecimal salaryBig = new BigDecimal(txtSalary.getText());

            // Prepare an SQL statement
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO employee (name, bdate, ssn, address, sex, salary, dep_id) VALUES (?, ?, ?, ?, ?, ?, ?)");

            String nama = txtName.getText();
            if(nama == null || nama.trim().isEmpty()) {
                throw new IllegalArgumentException("Nama tidak boleh kosong");
            }
            // Set the values from the text fields
            stmt.setString(1, txtName.getText());
            stmt.setDate(2, sqlDate);
            stmt.setString(3, txtSsn.getText());
            stmt.setString(4, txtAddress.getText());
            stmt.setString(5, (String)comboSex.getSelectedItem());
            stmt.setBigDecimal(6, salaryBig);
            if(depId != null || !depId.trim().isEmpty()) {
                stmt.setString(7, depId);
                // Setelah memasukkan data ke database, atur item yang dipilih menjadi null
                comboDep.setSelectedItem(null);
            } else {
                throw new IllegalArgumentException("Department tidak boleh kosong");
            }

            // Execute the statement
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Data inserted successfully");
                txtName.setText("");
                txtBdate.setText("");
                txtSsn.setText("");
                txtAddress.setText("");
                comboSex.setSelectedIndex(-1);
                txtSalary.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  
    }

    public static void main(String[] arg){
        new Splash();
    }
}
