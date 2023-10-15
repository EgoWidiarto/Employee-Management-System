package com.empSystem;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.*;

public class UpdateEmployeeAppTest {
    @Test
    public void UpdateEmployeeTest() throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conn.getConnection();
            UpdateEmp form = new UpdateEmp("22051214069");

            // Set the text fields
            form.txtAddress.setText("123 Main St");
            form.txtSalary.setText("60000");
            form.comboDep.setSelectedItem("Human Resource");

            // Call the method
            form.submit.doClick();

            // Test Data Exist Ot Not
            stmt = conn.prepareStatement("SELECT name, bdate, ssn, address, sex, salary, d.dname FROM employee e JOIN department d ON e.dep_id = d.dep_id WHERE ssn = ?");
            stmt.setString(1, "22051214069");
            rs = stmt.executeQuery();

            assertTrue(rs.next());
            assertEquals("60000", rs.getString("salary"));
            assertEquals("Human Resource", rs.getString("dname"));
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            // Pastikan untuk selalu menutup resources Anda
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}