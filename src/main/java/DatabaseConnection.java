import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    public static void main(String[] args) {
        // Настройки для подключения к базе данных
        String url = "jdbc:postgresql://localhost:5433/rank_the_scores"; // Имя БД
        String user = "postgres"; // Имя пользователя БД
        String password = "1234"; // Пароль пользователя

        // SQL-запрос для ранжирования
        String sql = "SELECT " +
                "id, " +
                "score, " +
                "DENSE_RANK() OVER (ORDER BY score DESC) AS rnk " +
                "FROM scores " +
                "ORDER BY score DESC";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Заголовки вывода
            System.out.println("+----+-------+-----+");
            System.out.println("| id | score | rnk |");
            System.out.println("+----+-------+-----+");

            // Обработка и вывод результатов
            while (rs.next()) {
                int id = rs.getInt("id");
                double score = rs.getDouble("score");
                int rnk = rs.getInt("rnk");

                System.out.printf("| %-2d | %-5.2f | %-3d |\n", id, score, rnk);
            }
            System.out.println("+----+-------+-----+");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}