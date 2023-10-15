package com.empSystem;

import org.junit.Test;

import java.sql.SQLException;
import static org.junit.Assert.*;

public class ConnectionAppTest {
    @Test
    public void testDatabaseConnection() {
        boolean exceptionThrown = false;
        try {
            Conn.getConnection();
        } catch (SQLException e) {
            exceptionThrown = true;
        }
        assertFalse("Expected no SQLException to be thrown", exceptionThrown);
    }
}
