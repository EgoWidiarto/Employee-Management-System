package com.empSystem;

import org.junit.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static org.junit.Assert.*;

public class AddEmployeeAppTest {

    @Test
    public void AddEmployeeTest() throws Exception {
        AddEmployee form = new AddEmployee();

        // Set the text fields
        form.txtName.setText("John Doe");
        form.txtBdate.setText("1980-01-01");
        form.txtSsn.setText("22051214069");
        form.txtAddress.setText("123 Main St");
        form.txtSex.setText("M");
        form.txtSalary.setText("50000");
        form.comboDep.setSelectedItem("IT");

        // Call the method
        form.addEmployee();

        // Test Data Exist Ot Not
        Connection conn = Conn.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM employee WHERE ssn = ?");
        stmt.setString(1, "22051214069");
        ResultSet rs = stmt.executeQuery();

        assertTrue(rs.next());

        rs.close();
        stmt.close();
        conn.close();
    }
}
