package empSystem.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopUpProject extends JFrame implements ActionListener {
    JButton addproject, viewProject, worksOn;
    PopUpProject() {
        addproject = new JButton("Masukkan Pegawai");
        addproject.setBounds(55, 40, 220, 40);
        addproject.setBackground(Color.decode("#22668D"));
        addproject.setForeground(Color.decode("#FFFADD"));
        addproject.addActionListener(this);
        add(addproject);

        viewProject = new JButton("Lihat/Tambah Project");
        viewProject.setBounds(55, 90, 220, 40);
        viewProject.setBackground(Color.decode("#22668D"));
        viewProject.setForeground(Color.decode("#FFFADD"));
        viewProject.addActionListener(this);
        add(viewProject);

        worksOn = new JButton("Lihat Project Berjalan");
        worksOn.setBounds(55, 140, 220, 40);
        worksOn.setBackground(Color.decode("#22668D"));
        worksOn.setForeground(Color.decode("#FFFADD"));
        worksOn.addActionListener(this);
        add(worksOn);

        //  Set Screen Size And Screen Location
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setSize(350, 250);
        setLocation(190, 50);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
       if(e.getSource() == viewProject) {
           new Project();
           setVisible(false);
       } else if(e.getSource() == addproject) {
           new AddEmpProject();
           setVisible(false);
       } else {
           new ViewEmpWorks();
           setVisible(false);
       }
    }

    public static void main(String[] args) {
        new Splash();
    }
}
