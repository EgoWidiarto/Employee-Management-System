package empSystem.users;

import empSystem.admin.Conn;

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

public class UserViewCard extends JFrame implements ActionListener {
    JLabel txtName, txtBdate, txtSsn, txtSex, txtSalary, txtAddress, txtDep;
    JButton submit, view, back;
    String empSsn;
    UserViewCard(String ssn) {
        this.empSsn = ssn;

        // Setup Border Style
        Border line = BorderFactory.createLineBorder(Color.decode("#0e0e0e"));
        Border padding = new EmptyBorder(0, 10, 0, 0);
        Border compounBorder = new CompoundBorder(line, padding);

        // Setup Font Style
        Font font = new Font("Poppins", Font.PLAIN, 18);

        JLabel empHeading = new JLabel("Data Diri Pegawai");
        empHeading.setBounds(295, 30, 500, 27);
        empHeading.setFont(new Font("Poppins", Font.BOLD, 25));
        add(empHeading);

        // Text Field
        JLabel empSsn = new JLabel("NIP");
        empSsn.setBounds(60, 120, 100, 30);
        empSsn.setFont(new Font("Poppins", Font.PLAIN, 18));
        add(empSsn);
        txtSsn = new JLabel();
        txtSsn.setBounds(120, 120, 180, 30);
        txtSsn.setBorder(compounBorder);
        add(txtSsn);

        JLabel empName = new JLabel("Nama");
        empName.setBounds(60, 185, 150, 30);
        empName.setFont(font);
        add(empName);
        txtName = new JLabel();
        txtName.setBounds(190, 185, 180, 30);
        txtName.setBorder(compounBorder);
        add(txtName);

        JLabel empSalary = new JLabel("Gaji");
        empSalary.setBounds(450, 185, 150, 30);
        empSalary.setFont(font);
        add(empSalary);
        txtSalary = new JLabel();
        txtSalary.setBounds(565, 185, 180, 30);
        txtSalary.setBorder(compounBorder);
        add(txtSalary);

        JLabel empSex = new JLabel("Jenis Kelamin");
        empSex.setBounds(60, 225, 150, 30);
        empSex.setFont(new Font("Poppins", Font.PLAIN, 18));
        add(empSex);
        txtSex = new JLabel();
        txtSex.setBounds(190, 225, 180, 30);
        txtSex.setBorder(compounBorder);
        add(txtSex);

        JLabel empBdate = new JLabel("Tanggal Lahir");
        empBdate.setBounds(60, 265, 150, 30);
        empBdate.setFont(new Font("Poppins", Font.PLAIN, 18));
        add(empBdate);
        txtBdate = new JLabel();
        txtBdate.setBounds(190, 265, 180, 30);
        txtBdate.setBorder(compounBorder);
        add(txtBdate);

        JLabel empDep = new JLabel("Departmen");
        empDep.setBounds(450, 225, 150, 30);
        empDep.setFont(new Font("Poppins", Font.PLAIN, 18));
        add(empDep);
        txtDep = new JLabel();
        txtDep.setBounds(565, 225, 180, 30);
        txtDep.setBorder(compounBorder);
        add(txtDep);

        JLabel empAdres = new JLabel("Alamat");
        empAdres.setBounds(60, 305, 150, 30);
        empAdres.setFont(font);
        add(empAdres);
        txtAddress = new JLabel();
        txtAddress.setBounds(190, 305, 180, 30);
        txtAddress.setBorder(compounBorder);
        add(txtAddress);

        try {
            Connection conn = Conn.getConnection();
            String query = "SELECT * FROM employee e JOIN department d ON e.dep_id = e.dep_id WHERE ssn = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, ssn);  // asumsikan empId adalah integer
            ResultSet result = pstmt.executeQuery();

            while(result.next()) {
                txtName.setText(result.getString("name"));
                txtSsn.setText(result.getString("ssn"));
                txtSex.setText(result.getString("sex"));
                txtSalary.setText(result.getString("salary"));
                txtBdate.setText(result.getString("bdate"));
                txtAddress.setText(result.getString("address"));
                txtDep.setText(result.getString("dname"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        submit = new JButton("Input Kegiatan");
        submit.setBounds(250, 410, 150, 40);
        submit.setBackground(Color.decode("#22668D"));
        submit.setForeground(Color.decode("#FFFADD"));
        submit.addActionListener(this);
        add(submit);

        back = new JButton("Kembali");
        back.setBounds(510, 410, 100, 40);
        back.setBackground(Color.decode("#22668D"));
        back.setForeground(Color.decode("#FFFADD"));
        back.addActionListener(this);
        add(back);

        //  Set Screen Size And Screen Location
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setSize(900, 500);
        setLocation(190, 50);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            new TambahKegiatan(empSsn);
        } else {
            new LoginUser();
            setVisible(false);
        }
    }
    public static void main(String[] args) {
        new LoginUser();
    }
}
