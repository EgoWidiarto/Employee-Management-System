package empSystem.admin;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;


public class UpdateEmp extends JFrame implements ActionListener {
    JTextField txtAddress, txtSalary;
    JLabel txtName, txtBdate, txtSsn, txtSex;
    JButton submit, view, back;
    JComboBox<String> comboDep;
    String empId;
    UpdateEmp(String ssn) {
        this.empId = ssn;

        // Create Laber For Heading
        JLabel empHeading = new JLabel("Perbarui Data Pegawai");
        empHeading.setBounds(290, 30, 500, 27);
        empHeading.setFont(new Font("Poppins", Font.BOLD, 25));
        add(empHeading);

        // Setup Border Style
        Border line = BorderFactory.createLineBorder(Color.decode("#0e0e0e"));
        Border padding = new EmptyBorder(0, 10, 0, 0);
        Border compounBorder = new CompoundBorder(line, padding);

        // Setup Font Style
        Font font = new Font("Poppins", Font.PLAIN, 18);

        // Text Field
        JLabel empName = new JLabel("Name");
        empName.setBounds(60, 120, 100, 30);
        empName.setFont(new Font("Poppins", Font.PLAIN, 18));
        add(empName);
        txtName = new JLabel();
        txtName.setBounds(220, 120, 180, 30);
        txtName.setBorder(compounBorder);
        add(txtName);

        JLabel empBdate = new JLabel("Tanggal Lahir");
        empBdate.setBounds(60, 180, 150, 30);
        empBdate.setFont(new Font("Poppins", Font.PLAIN, 18));
        add(empBdate);
        txtBdate = new JLabel();
        txtBdate.setBounds(220, 180, 180, 30);
        txtBdate.setBorder(compounBorder);
        add(txtBdate);

        JLabel empSsn = new JLabel("SSN");
        empSsn.setBounds(60, 240, 150, 30);
        empSsn.setFont(font);
        add(empSsn);
        txtSsn = new JLabel();
        txtSsn.setBounds(220, 240, 180, 30);
        txtSsn.setBorder(compounBorder);
        add(txtSsn);

        JLabel empSex = new JLabel("Gender");
        empSex.setBounds(450, 120, 150, 30);
        empSex.setFont(font);
        add(empSex);
        txtSex = new JLabel();
        txtSex.setBounds(600, 120, 180, 30);
        txtSex.setBorder(compounBorder);
        add(txtSex);

        JLabel empAdres = new JLabel("Alamat");
        empAdres.setBounds(60, 300, 150, 30);
        empAdres.setFont(font);
        add(empAdres);
        txtAddress = new JTextField();
        txtAddress.setBounds(220, 300, 180, 30);
        txtAddress.setBorder(compounBorder);
        add(txtAddress);

        JLabel empSalary = new JLabel("Gaji");
        empSalary.setBounds(450, 180, 150, 30);
        empSalary.setFont(font);
        add(empSalary);
        txtSalary = new JTextField();
        txtSalary.setBounds(600, 180, 180, 30);
        txtSalary.setBorder(compounBorder);
        add(txtSalary);

        JLabel empDep = new JLabel("Department");
        empDep.setBounds(450, 240, 150, 30);
        empDep.setFont(font);
        add(empDep);
        comboDep = new JComboBox<>();
        comboDep.setBounds(600, 240, 180, 30);
        comboDep.setBackground(Color.WHITE);
        add(comboDep);

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
                txtSex.setText(result.getString("sex"));
                txtAddress.setText(result.getString("address"));
                txtSalary.setText(result.getString("salary"));

                try {
                    Connection connection = Conn.getConnection();
                    String queryDname = "SELECT dname FROM department";
                    PreparedStatement pst = connection.prepareStatement(queryDname);
                    ResultSet rs = pst.executeQuery();

                    while (rs.next()) {
                        String name = rs.getString("dname");
                        comboDep.addItem(name);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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

                // Prepare The dep_Id
                String selectedDepName = (String)comboDep.getSelectedItem();
                String querySQL = "SELECT dep_id FROM department WHERE dname = ?";
                PreparedStatement pst = conn.prepareStatement(querySQL);
                pst.setString(1, selectedDepName);
                ResultSet rs = pst.executeQuery();
                String depIdTrue;
                if (rs.next()) {
                    depIdTrue = rs.getString("dep_id");
                } else {
                    throw new IllegalArgumentException("Department tidak ditemukan");
                }

                // Prepare an SQL statement
                String query = "UPDATE employee SET name = ?, bdate = ?, ssn = ?, address = ?, sex = ?, salary = ?, dep_id = ? WHERE ssn = ?";
                PreparedStatement stmt = conn.prepareStatement(query);

                // Set the values from the text fields
                stmt.setString(1, txtName.getText());
                stmt.setDate(2, sqlDate);
                stmt.setString(3, txtSsn.getText());
                stmt.setString(4, txtAddress.getText());
                stmt.setString(5, txtSex.getText());
                stmt.setFloat(6, Float.parseFloat(txtSalary.getText()));
                stmt.setString(7, depIdTrue);
                stmt.setString(8, txtSsn.getText()); // SSN employee Updated

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Data update successfully");
                    new ViewEmployee();
                    setVisible(false);
                }

                // Close the connection
                conn.close();

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

    public static void main(String[] arg){
        new Splash();
    }
}