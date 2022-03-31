import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;


public class UserCreator {

    List<String> names;
    List<String> surnames;
    Random random = new Random(LocalTime.now().getNano());

    public UserCreator() throws Exception {
        try {
            //Creating Lists from files for generating random names;

            names = new FileScanner("names.txt").getLoadedValues();
            surnames = new FileScanner("surnames.txt").getLoadedValues();

        } catch (Exception e) {
            throw new Exception("Smth went wrong in userCreator constructor: " + e.getMessage());
        }
    }

    public User createUser() {
        String name = names.get(getRndWithLimit(names.size()));
        String surname = surnames.get(getRndWithLimit(surnames.size()));
        String tag = name + surname;
        String phoneNumber = String.valueOf(phoneNumberGenerator());
        String email = emailGenerator(name,surname);
        String password = passwordGenerator();
        String passwordHash = encryptThisString(password);

        return new User(name, surname, tag, phoneNumber, email, password, passwordHash);
    }

    private int phoneNumberGenerator() {
        return 7000000 + random.nextInt( 100000,999999);
    }

    private String emailGenerator(String name, String surname) {
        return name + "." + surname + "@mail.mordor";
    }

    private int getRndWithLimit(int limit) {
        return random.nextInt(limit);
    }

    private String passwordGenerator() {
        String password = "";
        int passLength = random.nextInt(6,12);
        for (int i = 0; i < passLength; i++) {
            char ch = (char) random.nextInt(48, 119);
            password = password + ch;
        }
        return password;
    }

    private String encryptThisString(String input) {
        try {
            // getInstance() method is called with algorithm SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            // return the HashText
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}