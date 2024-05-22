import java.io.*;
import java.net.Socket;
import java.security.*;
import java.util.Base64;
import java.util.Scanner;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Client {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 12345;

        Security.addProvider(new BouncyCastleProvider());

        try (Socket socket = new Socket(hostname, port)) {
            KeyPair keyPair = KeyGenerator.generateKeyPair("Client");

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Choose action: ENCRYPT, DECRYPT, SIGN, VERIFY, EXIT");
                String action = scanner.nextLine();

                if (action.equalsIgnoreCase("EXIT")) {
                    break;
                }

                System.out.println("Enter message:");
                String message = scanner.nextLine();

                switch (action.toUpperCase()) {
                    case "ENCRYPT":
                        writer.println("ENCRYPT:" + message);
                        break;
                    case "DECRYPT":
                        writer.println("DECRYPT:" + message);
                        break;
                    case "SIGN":
                        writer.println("SIGN:" + message);
                        break;
                    case "VERIFY":
                        System.out.println("Enter signature to verify:");
                        String signature = scanner.nextLine();
                        writer.println("VERIFY:" + message + ":" + signature);
                        break;
                    default:
                        System.out.println("Unknown action.");
                        continue;
                }

                // Read server response
                String response = reader.readLine();
                System.out.println("Server response: " + response);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
