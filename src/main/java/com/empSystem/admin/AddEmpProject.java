package empSystem.admin;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
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


public class AddEmpProject extends JFrame implements ActionListener {
    private JComboBox<String> employeeComboBox, projectComboBox;
    private JLabel txtDuration;
    private JButton submit, view, back;
    String projectId, ssn, durasi;
    AddEmpProject() {
        // Create Laber For Heading
        JLabel empHeading = new JLabel("Penempatan Project Pegawai");
        empHeading.setBounds(150, 30, 360, 27);
        empHeading.setFont(new Font("Poppins", Font.BOLD, 23));
        add(empHeading);

        // Setup Border Style
        Border line = BorderFactory.createLineBorder(Color.decode("#0e0e0e"));
        Border padding = new EmptyBorder(0, 5, 0, 0);
        Border compounBorder = new CompoundBorder(line, padding);

        // Text Field Dan Label
        JLabel empName = new JLabel("Pegawai");
        empName.setBounds(96, 120, 100, 30);
        empName.setFont(new Font("Poppins", Font.PLAIN, 18));
        add(empName);
        //  ComboBox Employee
        employeeComboBox = new JComboBox<>();
        try {
            Connection conn = Conn.getConnection();
            String query = "SELECT ssn, name FROM employee";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            Vector<String> employeeVector = new Vector<>();

            while (resultSet.next()) {
                String ssn = resultSet.getString("ssn");
                String name = resultSet.getString("name");
                employeeVector.add(ssn + " - " + name);
            }
            employeeComboBox.setModel(new DefaultComboBoxModel<>(employeeVector));

            // Menyeleksi SSN dari pegawai untuk diinputkan ke database
            String selectedEmployee = (String) employeeComboBox.getSelectedItem();
            if(selectedEmployee == null) {
                throw new IllegalArgumentException("Data Dalam Databse Masih Kosong");
            } else {
                String[] bagian = selectedEmployee.split(" - ");
                ssn = bagian[0];
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        employeeComboBox.setBounds(256, 120, 250, 30);
        employeeComboBox.setBackground(Color.WHITE);
        add(employeeComboBox);

        JLabel project = new JLabel("Project");
        project.setBounds(96, 180, 150, 30);
        project.setFont(new Font("Poppins", Font.PLAIN, 18));
        add(project);
        //  ComboBox Project
        projectComboBox = new JComboBox<>();
        try {
            Connection conn = Conn.getConnection();
            String query = "SELECT project_id, pname FROM project WHERE end_date > CURDATE()";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            Vector<String> employeeVector = new Vector<>();

            while (resultSet.next()) {
                String ssn = resultSet.getString("project_id");
                String name = resultSet.getString("pname");
                employeeVector.add(ssn + " - " + name);
            }
            projectComboBox.setModel(new DefaultComboBoxModel<>(employeeVector));

            // Get Project Id From Selected Item
            String selectedProject = (String) projectComboBox.getSelectedItem();
            if(selectedProject == null) {
                throw new IllegalArgumentException("Data Dalam Databse Masih Kosong");
            } else {
                String[] bagian = selectedProject.split(" - ");
                projectId = bagian[0];
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        projectComboBox.setBounds(256, 180, 250, 30);
        projectComboBox.setBackground(Color.WHITE);
        add(projectComboBox);

        JLabel projectDuration = new JLabel("Durasi Project");
        projectDuration.setBounds(96, 240, 150, 30);
        projectDuration.setFont(new Font("Poppins", Font.PLAIN, 18));
        add(projectDuration);
        txtDuration = new JLabel();
        txtDuration.setBounds(256, 240, 250, 30);
        txtDuration.setFont(new Font("Poppins", Font.BOLD, 12));
        txtDuration.setBorder(compounBorder);
        projectComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                try{
                    Connection conn = Conn.getConnection();

                    String query = "SELECT DATEDIFF(end_date, CURDATE()) AS durasi FROM project WHERE project_id = ?";
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, projectId);
                    ResultSet result = pstmt.executeQuery();
                    while(result.next()){
                        durasi = result.getString("durasi");
                        txtDuration.setText(durasi + " Hari");
                    }
                }catch (SQLException ae) {
                    ae.printStackTrace();
                }
            }
        });
        add(txtDuration);

        // Button Style
        submit = new JButton("Submit");
        submit.setBounds(140, 310, 100, 40);
        submit.setBackground(Color.decode("#22668D"));
        submit.setForeground(Color.decode("#FFFADD"));
        submit.addActionListener(this);
        add(submit);

        view = new JButton("Lihat Pegawai");
        view.setBounds(260, 310, 140, 40);
        view.setBackground(Color.decode("#22668D"));
        view.setForeground(Color.decode("#FFFADD"));
        view.addActionListener(this);
        add(view);

        back = new JButton("Kembali");
        back.setBounds(420, 310, 100, 40);
        back.setBackground(Color.decode("#22668D"));
        back.setForeground(Color.decode("#FFFADD"));
        back.addActionListener(this);
        add(back);

        //  Set Screen Size And Screen Location
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setSize(700, 450);
        setLocation(190, 50);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Splash();
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submit){
            try {
                Connection conn = Conn.getConnection();

                String query = "INSERT INTO works_on (project_id, essn, work_dyas) VALUES (?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, projectId);
                pstmt.setString(2, ssn);
                pstmt.setInt(3, Integer.parseInt(durasi));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == view) {
            new ViewEmpWorks();
            setVisible(false);
        } else {
            setVisible(false);
        }
    }
}
