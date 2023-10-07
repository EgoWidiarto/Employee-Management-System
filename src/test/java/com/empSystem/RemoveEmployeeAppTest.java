package com.empSystem;

import org.junit.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static org.junit.Assert.*;

public class RemoveEmployeeAppTest {
    @Test
    public void RemoveEmployeeTest() throws Exception {
        RemoveEmp form = new RemoveEmp();

        // Hapus pegawai
        boolean result = form.removeEmployee("22051214069");

        // Verifikasi jika pegawai berhasil dihapus
        assertTrue(result);

        // Sekarang periksa database untuk memastikan bahwa pegawai telah dihapus
        Connection conn = Conn.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM employee WHERE ssn = ?");
        stmt.setString(1, "22051214069");
        ResultSet rs = stmt.executeQuery();

        assertFalse(rs.next());  // Ini akan gagal jika ada hasil yang dikembalikan

        // Jangan lupa menutup resources Anda
        rs.close();
        stmt.close();
        conn.close();
    }
}
