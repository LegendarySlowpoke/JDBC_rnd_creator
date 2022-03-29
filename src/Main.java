import java.util.List;

public class Main {


    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/msgr";
        String username = "root";
        String password = "****";
        boolean connected;

        try {
            //Creating JDBC and connecting to db
            JDBC jdbc = new JDBC(url, username, password);
            connected = jdbc.connect();
            if (!connected) throw new Exception("Unabled to connect to db " + url);

            UserCreator userCreator = new UserCreator();

            //Generating records in db
            User tempUser;
            Long tempId;

            for(int i = 0; i < 50; i++) {
                tempUser = userCreator.createUser();
                tempId = jdbc.addRecord(tempUser);
                if (tempId != Long.MIN_VALUE) {
                    System.out.print("id = " + tempId + " created");
                } else {
                    System.out.print("\n\n FAILED TO CREATE\n" + tempUser.toString() + "\n\n");
                }
                if (i % 10 == 0 ) System.out.println();

            }
            System.out.println("\n\n==================================\n\n");

            //Closing connection and shutting down application
            connected = jdbc.disConnect();
            if (connected) throw new Exception("Unabled to close connection with db!");
        } catch (Exception e) {
            System.out.println("Error has happen in main class: " + e.getMessage());
        }
    }
}
