package empSystem.admin;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Project extends JFrame implements ActionListener {
    JTextField projectId, projectName, projectLocation, projectStart, projectEnd, projectStatus;
    JTable table = new JTable();
    JButton submit, addEmp, back;
    Project() {
        // Create Laber For Heading
        JLabel empHeading = new JLabel("Project Perusahaan");
        empHeading.setBounds(290, 30, 500, 27);
        empHeading.setFont(new Font("Poppins", Font.BOLD, 25));
        add(empHeading);

        // Text Field Dan Label
        JLabel idLabel = new JLabel("ID Project");
        idLabel.setBounds(20, 120, 100, 30);
        idLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
        add(idLabel);
        projectId = new JTextField();
        projectId.setBounds(120, 120, 168, 30);
        add(projectId);

        JLabel nameLabel = new JLabel("Nama Project");
        nameLabel.setBounds(20, 160, 150, 30);
        nameLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
        add(nameLabel);
        projectName = new JTextField();
        projectName.setBounds(120, 160, 168, 30);
        add(projectName);

        JLabel locationLabel = new JLabel("Lokasi");
        locationLabel.setBounds(20, 200, 150, 30);
        locationLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
        add(locationLabel);
        projectLocation = new JTextField();
        projectLocation.setBounds(120, 200, 168, 30);
        add(projectLocation);

        JLabel startLabel = new JLabel("Tanggal Mulai");
        startLabel.setBounds(330, 120, 150, 30);
        startLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
        add(startLabel);
        projectStart = new JTextField();
        projectStart.setBounds(440, 120, 180, 30);
        add(projectStart);

        JLabel endLabel = new JLabel("Tanggal Brakhir");
        endLabel.setBounds(330, 160, 150, 30);
        endLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
        add(endLabel);
        projectEnd = new JTextField();
        projectEnd.setBounds(440, 160, 180, 30);
        add(projectEnd);

        JLabel statusLabel = new JLabel("Status");
        statusLabel.setBounds(330, 200, 150, 30);
        statusLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
        add(statusLabel);
        projectStatus = new JTextField();
        projectStatus.setBounds(440, 200, 180, 30);
        add(projectStatus);

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 260, 860, 500);
        add(jsp);

        // Button Style
        submit = new JButton("Submit");
        submit.setBounds(660, 110, 150, 30);
        submit.setBackground(Color.decode("#22668D"));
        submit.setForeground(Color.decode("#FFFADD"));
        submit.addActionListener(this);
        add(submit);

        back = new JButton("Kembali");
        back.setBounds(660, 150, 150, 30);
        back.setBackground(Color.decode("#22668D"));
        back.setForeground(Color.decode("#FFFADD"));
        back.addActionListener(this);
        add(back);

        addEmp = new JButton("Masukkan Pegawai");
        addEmp.setBounds(660, 190, 150, 30);
        addEmp.setBackground(Color.decode("#22668D"));
        addEmp.setForeground(Color.decode("#FFFADD"));
        addEmp.addActionListener(this);
        add(addEmp);

        //  Set Screen Size And Screen Location
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setSize(860, 650);
        setLocation(190, 50);
        setVisible(true);
        viewProject();
    }

    public void viewProject() {
        try {
            Connection conn = Conn.getConnection();
            if (conn == null) {
                System.out.println("Koneksi ke database gagal");
                return;
            }
            String querySQL = "SELECT project_id AS \"ID Project\", pname AS \"Nama Project\", plocation AS Lokasi, start_date AS \"Tanggal Mulai\", end_date AS \"Tanggal Berakhir\", status AS Status FROM project";
            PreparedStatement stmt = conn.prepareStatement(querySQL);
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

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submit) {
            try {
                Connection conn = Conn.getConnection();

                // Convert String Start Date To Date For Input To Database
                SimpleDateFormat formatStartDate = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsedStart = formatStartDate.parse(projectStart.getText());
                java.sql.Date startDate = new java.sql.Date(parsedStart.getTime());

                // Convert String End Date To Date For Input To Database
                SimpleDateFormat formatEndDate = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsedEnd = formatEndDate.parse(projectStart.getText());
                java.sql.Date endtDate = new java.sql.Date(parsedEnd.getTime());

                String query = "INSERT INTO project (project_id, pname, plocation, start_date, end_date, status) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, projectId.getText());
                pstmt.setString(2, projectName.getText());
                pstmt.setString(3, projectLocation.getText());
                pstmt.setDate(4, startDate);
                pstmt.setDate(5, endtDate);
                pstmt.setString(6, projectStatus.getText());

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Data berhasi dimasukkan");
                    viewProject();
                }
            } catch (SQLException | ParseException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == back) {
            new Home();
            setVisible(false);
        } else {
            new AddEmpProject();
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new Splash();
    }
}
