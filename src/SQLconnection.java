public class SQLconnection {
    private String username;
    private String password;
    private String url;
    private String tableName;

    public SQLconnection(String username, String password, String url, String tableName) {
        this.username = username;
        this.password = password;
        this.url = url;
        this.tableName = tableName;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
