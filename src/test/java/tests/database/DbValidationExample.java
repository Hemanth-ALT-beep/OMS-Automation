package tests.database;

import org.testng.Assert;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public final class DbValidationExample {
    private DbValidationExample() {
    }

    /**
     * Example only. Set OMS_DB_URL, OMS_DB_USER and OMS_DB_PASSWORD
     * and adapt table/column names to the approved QA database schema.
     */
    public static void verifyOrder(
            String orderId,
            BigDecimal expectedTotal,
            String expectedCurrency
    ) throws Exception {
        String dbUrl = System.getenv("OMS_DB_URL");
        String dbUser = System.getenv("OMS_DB_USER");
        String dbPassword = System.getenv("OMS_DB_PASSWORD");

        if (dbUrl == null || dbUser == null || dbPassword == null) {
            throw new IllegalStateException("Set OMS_DB_URL, OMS_DB_USER and OMS_DB_PASSWORD first.");
        }

        String sql = "SELECT order_id, total_amount, currency " +
                "FROM orders WHERE order_id = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, orderId);

            try (ResultSet resultSet = statement.executeQuery()) {
                Assert.assertTrue(resultSet.next(), "Order row should exist");
                Assert.assertEquals(resultSet.getString("order_id"), orderId);
                Assert.assertEquals(resultSet.getBigDecimal("total_amount"), expectedTotal);
                Assert.assertEquals(resultSet.getString("currency"), expectedCurrency);
            }
        }
    }
}
