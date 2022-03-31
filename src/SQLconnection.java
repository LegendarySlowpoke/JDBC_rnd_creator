public class SQLconnection {
    private String username;
    private String password;
    private String serverUrl;
    private String dbName;
    private String tableName;

    public SQLconnection(String username, String password, String serverUrl, String dbName, String tableName) {
        this.username = username;
        this.password = password;
        this.serverUrl = serverUrl;
        this.dbName = dbName;
        this.tableName = tableName;
    }

    public String getFullUrlToDB() {
        return serverUrl + dbName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
