package empSystem.admin;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

public class AddEmployeeAppTest {
    @Test
    public void testAddEmployee() throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conn.getConnection();
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
            form.addEmp();

            // Test Data Exist Ot Not
            stmt = conn.prepareStatement("SELECT * FROM employee WHERE ssn = ?");
            stmt.setString(1, "22051214069");
            rs = stmt.executeQuery();

            assertTrue("Expected at least one row in result set", rs.next());
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

    @Test(expected = SQLException.class)
    public void testAddEmployeeWithExistingSSN() throws Exception {
        AddEmployee form = new AddEmployee();

        // Set the text fields
        form.txtName.setText("John Doe");
        form.txtBdate.setText("1980-01-01");
        form.txtSsn.setText("22051214069");  // Assuming this SSN already exists in the database
        form.txtAddress.setText("123 Main St");
        form.txtSex.setText("M");
        form.txtSalary.setText("50000");
        form.comboDep.setSelectedItem("IT");

        // Call the method
        form.addEmp();
    }
}