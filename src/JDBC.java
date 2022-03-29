import java.sql.*;

public class JDBC {
    private String url;
    private String username;
    private String password;
    private Connection connection;

    public JDBC(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }


    public boolean connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("    JDBC - connect - Error has happen: " + e.getMessage());
            return false;
        }
        System.out.println("    JDBC - connect - Connecting database...");
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("    JDBC - connect - Error has happen: " + e.getMessage());
            return false;
        }
        System.out.println("    JDBC - connect - Connected to the database successfully");
        System.out.println();
        return true;
    }

    public boolean disConnect() {
        try {
            connection.close();
            return true;
        } catch (Exception e) {
            System.out.println("    JDBC - disConnect - Error has happen: " + e.getMessage());
            return false;
        }
    }

    public Long addRecord(User user) throws Exception {
        String SQL = "INSERT INTO user(email, name, pass_hash, phone_number, surname, tag) "
                + "VALUES(?,?,?,?,?,?)";

        Long id = Long.MIN_VALUE;
            /*
        id           | bigint       | NO   | PRI | NULL    | auto_increment |
| email        | varchar(255) | YES  |     | NULL    |                |
| name         | varchar(255) | NO   |     | NULL    |                |
| pass_hash    | varchar(255) | YES  |     | NULL    |                |
| phone_number | varchar(255) | YES  |     | NULL    |                |
| surname      | varchar(255) | YES  |     | NULL    |                |
| tag          | varchar(255) | NO   |     | NULL    |                |
| sender_id    | bigint       | YES  | MUL | NULL    |
                */
        try (PreparedStatement pstmt = connection.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getPhoneNumber());
            pstmt.setString(5, user.getSurname());
            pstmt.setString(6, user.getTag());

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

}

/*

        System.out.println("    JDBC - connect - Creating statement...");
        Statement statement = connection.createStatement();
        String query = "select * from user";
        System.out.println("    JDBC - connect - Creating ResultSet...");
        ResultSet rs = statement.executeQuery(query);
        while(rs.next()) {
            System.out.println(rs.getString(2));
        }
        statement.close();
        connection.close();
    }
 */