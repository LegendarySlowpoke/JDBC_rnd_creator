import java.sql.*;

public class JDBC {
    private final SQLconnection sqLconnection;
    private final String url;
    private final String username;
    private final String password;
    private final String tableName;
    private Connection connection;

    public JDBC(SQLconnection sqlConnection) {
        this.sqLconnection = sqlConnection;
        this.url = sqlConnection.getFullUrlToDB();
        this.username = sqlConnection.getUsername();
        this.password = sqlConnection.getPassword();
        this.tableName = sqlConnection.getTableName();
    }


    public boolean connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("\tJDBC - connect - " + url + " - Error has happen: " + e.getMessage());
            return false;
        }
        System.out.println("\n====================================================\n" +
                "\tJDBC - connect - " + url + " - Connecting database...");
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("\tJDBC - connect - " + url + " - Error has happen: " + e.getMessage());
            return false;
        }
        System.out.println("\tJDBC - connect - Connected to the " + url + " successfully" +
                "\n====================================================");

        if (sqLconnection.getDbName().equals("msgrClient")) {
            if (!checkTable()) return false;
        }
        return true;
    }

    public boolean disConnect() {
        try {
            connection.close();
            return true;
        } catch (Exception e) {
            System.out.println("    JDBC - disConnect - " + url + " - Error has happen: " + e.getMessage());
            return false;
        }
    }

    //User for msgr db
    public Long addRecord(User user) throws Exception {
        if (tableName.equals("user")) {
            String SQL = "INSERT INTO " + tableName + "(email, name, pass_hash, phone_number, surname, tag) "
                    + "VALUES(?,?,?,?,?,?)";

            Long id = Long.MIN_VALUE;
            try (PreparedStatement pstmt = connection.prepareStatement(SQL,
                    Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, user.getEmail());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getPasswordHash());
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
        } else {
            return Long.MIN_VALUE;
        }
    }

    //User for msgrClient db
    public Long addRecord(User user, Long idNumber) throws Exception {
        if (tableName.equals("clientData")) {
            String SQL = "INSERT INTO " + tableName + "(id, email, name, pass, pass_hash, phone_number, surname, tag) "
                    + "VALUES(?,?,?,?,?,?,?,?)";
            try (PreparedStatement pstmt = connection.prepareStatement(SQL,
                    Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setLong(1, idNumber);
                pstmt.setString(2, user.getEmail());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getPassword());
                pstmt.setString(5, user.getPasswordHash());
                pstmt.setString(6, user.getPhoneNumber());
                pstmt.setString(7, user.getSurname());
                pstmt.setString(8, user.getTag());

                int affectedRows = pstmt.executeUpdate();
                // check the affected rows
                if (affectedRows > 0) {
                    return idNumber;
                } else {
                    throw new Exception("smth went wrong during updating db " + url + ": (!affected rows < 0)");
                }
            } catch (Exception e) {
                throw new Exception("smth went wrong during updating db " + url + ": " + e.getMessage());
            }
        } else {
            return Long.MIN_VALUE;
        }
    }


    private boolean checkTable() {
        System.out.println("\tJDBC - connect - database is " + sqLconnection.getDbName() + ", checking if " +
                "table '" + sqLconnection.getTableName() + "' exists");
        try {
            if (!checkIfTableExists()){
                System.out.println("\tJDBC - connect - table '" + sqLconnection.getTableName() + "' in database '"
                        + sqLconnection.getDbName() + "' doesn't exist.\n\t\tCreating new one...");
                //todo finish here: should create new table
                Statement statement = connection.createStatement();
                statement.execute("create table " + sqLconnection.getTableName() +
                        " ( id           LONG default null,\n" +
                        "    email        varchar(255) null,\n" +
                        "    name         varchar(255) null,\n" +
                        "    pass         varchar(255) null,\n" +
                        "    pass_hash    varchar(255) null,\n" +
                        "    phone_number varchar(255) null,\n" +
                        "    surname      varchar(255) null,\n" +
                        "    tag          varchar(255) null\n" +
                        ")");
                if (checkIfTableExists()) {
                    System.out.println("\tJDBC - connect - a new  table '" + sqLconnection.getTableName() +
                            "' in database '" + sqLconnection.getDbName() + "' created!");
                } else {
                    System.out.println("\tJDBC - connect - unabled to create a new table '"
                            + sqLconnection.getTableName() + "' in database '" + sqLconnection.getDbName() + "' =(");
                    throw new SQLException("Unable to create new table " + sqLconnection.getTableName() + " in db " +
                            sqLconnection.getDbName());
                }
            } else {
                System.out.println("\tJDBC - connect - table '" + sqLconnection.getTableName() + "' in database '"
                        + sqLconnection.getDbName() + "' already exists");
            }
        } catch (SQLException e) {
            System.out.println("\tJDBC - connect - smth went wrong while trying to check if table " +
                    sqLconnection.getTableName() + " exists in " + sqLconnection.getDbName() + ": " + e.getMessage());
            return false;
        }
        System.out.println("====================================================");
        return true;
    }


    private boolean checkIfTableExists() throws SQLException {
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet resultSet = databaseMetaData.getTables(null, null, sqLconnection.getTableName(),
                new String[]{"TABLE"});
        return resultSet.next();
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