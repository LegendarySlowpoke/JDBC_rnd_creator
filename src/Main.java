public class Main {
    public static String serverURL;

    //args dbURL[0], username[1], password[2]
    public static void main(String[] args) {
        serverURL = args[0];
        String username = args[1];
        String password = args[2];

        String dbName1 = "msgr";
        String tableName1 = "user";
        SQLconnection sqlConnection1 = new SQLconnection(username, password, serverURL ,dbName1, tableName1);
        boolean connected1;

        String dbName2 = "msgrClient";
        String tableName2 = "clientData";
        SQLconnection sqlConnection2 = new SQLconnection(username, password, serverURL, dbName2, tableName2);
        boolean connected2;


        try {
            //Creating JDBC and connecting to db
            JDBC jdbc1 = new JDBC(sqlConnection1);
            JDBC jdbc2 = new JDBC(sqlConnection2);
            connected1 = jdbc1.connect();
            connected2 = jdbc2.connect();

            if (!connected1) {
                throw new Exception("Unabled to connect to db " + dbName1);
            }
            if (!connected2) {
                jdbc1.disConnect();
                throw new Exception("Unabled to connect to db " + dbName2);
            }

            //Creating a userCreator entity
            UserCreator userCreator = new UserCreator();

            //Generating records in db
            User tempUser;
            Long tempId1;
            Long tempId2;

            for(int i = 0; i < 15; i++) {
                tempUser = userCreator.createUser();
                tempId1 = jdbc1.addRecord(tempUser);
                tempId2 = jdbc2.addRecord(tempUser, tempId1);
                if (tempId1 != Long.MIN_VALUE && tempId2 != Long.MIN_VALUE) {
                    System.out.print("id:" + tempId2 + " created; ");
                } else {
                    System.out.print("\nFAILED TO CREATE  id1=" + tempId1 + ", id2=" + tempId2 + "\n"
                            + tempUser.getInfo() + "\n");
                }
            if (i % 10 == 0 ) System.out.println();

            }
            System.out.println("\n\n==================================\n\n");

            //Closing connection and shutting down application
            connected1 = jdbc1.disConnect();
            connected2 = jdbc2.disConnect();
            if (connected1) throw new Exception("Unable to close connection with db1!");
            if (connected2) throw new Exception("Unable to close connection with db2!");
        } catch (Exception e) {
            System.out.println("Error has happen in main class: " + e.getMessage());
        }
    }
}
