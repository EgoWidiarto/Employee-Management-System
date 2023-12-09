package empSystem.admin;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewEmpActivity extends JFrame implements ActionListener {
    JTable table = new JTable();
    JTextField txtEmp;
    JButton search, print, back;

    JComboBox<String> comboDepSearch = new JComboBox<>();
    String essn;
    ViewEmpActivity() {
        JLabel headingView = new JLabel("Lihat Data Kegiatan Pegawai");
        headingView.setBounds(310, 20, 280, 30);
        headingView.setFont(new Font("Poppins", Font.BOLD, 20));
        add(headingView);

        JLabel ssn = new JLabel("Filter SSN:");
        ssn.setBounds(160, 70, 180, 30);
        ssn.setFont(new Font("Poppins", Font.BOLD, 14));
        add(ssn);
        txtEmp = new JTextField();
        txtEmp.setBounds(160, 100, 200, 30);
        add(txtEmp);

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(140, 150, 550, 500);
        add(jsp);

        // Search Employee From Their Department
        JLabel project = new JLabel("Filter Project:");
        project.setBounds(390, 70, 180, 30);
        project.setFont(new Font("Poppins", Font.BOLD, 14));
        add(project);
        project.setFont(new Font("Poppins", Font.PLAIN, 14));
        comboDepSearch.setBounds(390, 100, 180, 30);
        comboDepSearch.setBackground(Color.WHITE);
        try {
            Connection connection = Conn.getConnection();
            String query = "SELECT pname FROM project";
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String name = rs.getString("pname");
                comboDepSearch.addItem(name);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        comboDepSearch.setSelectedIndex(-1);
        add(comboDepSearch);

        search = new JButton("Cari");
        search.setBounds(20, 200, 80, 30);
        search.setBackground(Color.decode("#22668D"));
        search.setForeground(Color.decode("#FFFADD"));
        search.addActionListener(this);
        add(search);

        print = new JButton("Cetak");
        print.setBounds(20, 250, 80, 30);
        print.setBackground(Color.decode("#22668D"));
        print.setForeground(Color.decode("#FFFADD"));
        print.addActionListener(this);
        add(print);

        back = new JButton("Kembali");
        back.setBounds(20, 300, 80, 30);
        back.setBackground(Color.decode("#22668D"));
        back.setForeground(Color.decode("#FFFADD"));
        back.addActionListener(this);
        add(back);

        //  Set Screen Size And Screen Location
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setSize(850, 550);
        setLocation(190, 50);
        setVisible(true);
        viewWorks(null);
    }

    public void viewWorks(String ssn) {
        String selected = "e.name AS \"Nama Lengkap\", e.ssn AS NIP, p.pname AS \"Nama Project\", works AS Kegiatan, work_time AS Waktu";
        String querySQL = "";
        if (ssn != null && !ssn.trim().isEmpty()) {
            // Jika SSN diberikan, cari pegawai dengan SSN tersebut
            querySQL = "SELECT " + selected + " FROM work_log wl JOIN works_on wo ON wl.essn = wo.essn JOIN employee e ON wl.essn = e.ssn JOIN project p ON wo.project_id = p.project_id WHERE e.ssn = ?";
        } else if (comboDepSearch.getSelectedIndex() > -1) {
            // Jika tidak, tampilkan semua pegawai
            querySQL = "SELECT "+ selected +" FROM work_log wl JOIN works_on wo ON wl.essn = wo.essn JOIN employee e ON wl.essn = e.ssn JOIN project p ON wo.project_id = p.project_id WHERE pname = ?";
        } else {
            querySQL = "SELECT " + selected + " FROM work_log wl JOIN works_on wo ON wl.essn = wo.essn JOIN employee e ON wl.essn = e.ssn JOIN project p ON wo.project_id = p.project_id";
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

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == search) {
            viewWorks(txtEmp.getText());
            txtEmp.setText("");
            comboDepSearch.setSelectedIndex(-1);
        } else if (e.getSource() == print) {
            try {
                table.print();
            } catch (Exception ae) {
                ae.printStackTrace();
            }
        } else {
            new Home();
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new ViewEmpActivity();
    }
}
