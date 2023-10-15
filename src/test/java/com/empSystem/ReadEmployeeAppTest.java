package com.empSystem;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.*;

public class ReadEmployeeAppTest {
    @Test
    public void testReadEmployee() throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conn.getConnection();
            String ssn = "22051214069";

            // Query data
            String section = "name, bdate, ssn, address, sex, salary,";
            stmt = conn.prepareStatement("SELECT "+ section +" d.dname as department_name FROM employee e JOIN department d ON e.dep_id = d.dep_id WHERE e.ssn = ?");
            stmt.setString(1, ssn);
            rs = stmt.executeQuery();

            assertTrue("Expected at least one row in result set", rs.next());
            assertEquals("Expected name to be John Doe", "John Doe", rs.getString("name"));
            assertEquals("Expected birth date to be 1980-01-01", "1980-01-01", rs.getString("bdate"));
            assertEquals("Expected SSN to match input", ssn, rs.getString("ssn"));
            assertEquals("Expected sex to be M", "M", rs.getString("sex"));
            assertEquals("Expected address to be 123 Main St", "123 Main St", rs.getString("address"));
            assertEquals("Expected salary to be 50000", "50000", rs.getString("salary"));
            assertEquals("Expected department name to be IT", "IT", rs.getString("department_name"));
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