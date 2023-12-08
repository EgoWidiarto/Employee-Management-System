package empSystem.admin;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;

public class RemoveEmployeeAppTest {
    @Test
    public void testRemoveEmployee() throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conn.getConnection();
            String ssn = "22051214069";

            // Pertama, kita perlu menghapus pegawai ini
            RemoveEmp form = new RemoveEmp();
            form.choiceSSN.select(ssn);
            form.delete.doClick();

            // Verifikasi jika pegawai berhasil dihapus
            stmt = conn.prepareStatement("SELECT * FROM employee WHERE ssn = ?");
            stmt.setString(1, ssn);
            rs = stmt.executeQuery();
            assertFalse("Expected no rows in result set", rs.next());  // Ini akan gagal jika ada hasil yang dikembalikan
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
    public void testRemoveNonExistentEmployee() throws Exception {
        RemoveEmp form = new RemoveEmp();
        form.choiceSSN.select("nonexistent_ssn");  // Assuming this SSN does not exist in the database
        form.delete.doClick();
    }
}