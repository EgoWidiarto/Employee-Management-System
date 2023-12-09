package empSystem.users;

import empSystem.admin.Conn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TambahKegiatan extends JFrame implements ActionListener {
    JTextField txtWorks;
    JComboBox projectComboBox;
    JButton submit;
    String empId, projectId;
    TambahKegiatan(String ssn) {
        this.empId = ssn;

        JLabel project = new JLabel("Project");
        project.setBounds(150, 20, 120, 30);
        project.setFont(new Font("Poppins", Font.PLAIN, 18));
        add(project);
        //  ComboBox Project
        projectComboBox = new JComboBox<>();
        projectComboBox.setBounds(220, 20, 180, 40);
        projectComboBox.setBackground(Color.WHITE);
        try {
            Connection conn = Conn.getConnection();
            String query = "SELECT p.project_id, pname FROM project p JOIN works_on wo ON p.project_id = wo.project_id WHERE end_date > CURDATE() AND wo.essn = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, empId);
            ResultSet resultSet = statement.executeQuery();

            Vector<String> employeeVector = new Vector<>();

            while (resultSet.next()) {
                String projectId = resultSet.getString("p.project_id");
                String name = resultSet.getString("pname");
                employeeVector.add(projectId + " - " + name);
            }
            projectComboBox.setModel(new DefaultComboBoxModel<>(employeeVector));

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        add(projectComboBox);

        JLabel empAdres = new JLabel("Kegiatan:");
        empAdres.setBounds(100, 70, 150, 30);
        empAdres.setFont(new Font("Poppins", Font.BOLD, 16));
        add(empAdres);
        txtWorks = new JTextField();
        txtWorks.setBounds(100, 100, 400, 160);
        add(txtWorks);

        projectComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // Menyeleksi SSN dari pegawai untuk diinputkan ke database
                String selectedEmployee = (String) projectComboBox.getSelectedItem();
                if(selectedEmployee == null) {
                    throw new IllegalArgumentException("Data Dalam Databse Masih Kosong");
                } else {
                    String[] bagian = selectedEmployee.split(" - ");
                    projectId = bagian[0];
                }
            }
        });

        submit = new JButton("Kirim");
        submit.setBounds(250, 290, 100, 30);
        submit.setBackground(Color.decode("#22668D"));
        submit.setForeground(Color.decode("#FFFADD"));
        submit.addActionListener(this);
        add(submit);

        //  Set Screen Size And Screen Location
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setSize(600, 400);
        setLocation(190, 50);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Connection conn = Conn.getConnection();

            String query = "INSERT INTO works_on (essn, project_id, works, work_time) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, empId);
            pstmt.setString(2, projectId);
            pstmt.setString(3, txtWorks.getText());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Data berhasi dimasukkan");
                txtWorks.setText("");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LoginUser();
    }
}
