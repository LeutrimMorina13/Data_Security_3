import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.crypto.Cipher;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Server {
    public static void main(String[] args) {
        int port = 12345;

        Security.addProvider(new BouncyCastleProvider());

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            KeyPair keyPair = KeyGenerator.generateKeyPair("Server");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                new ServerThread(socket, keyPair).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

class ServerThread extends Thread {
    private Socket socket;
    private KeyPair keyPair;
    private Logger encryptedLogger;
    private Logger decryptedLogger;

    public ServerThread(Socket socket, KeyPair keyPair) {
        this.socket = socket;
        this.keyPair = keyPair;
        initializeLoggers();
    }

    private void initializeLoggers() {
        try {
            // Logger for encrypted messages
            encryptedLogger = Logger.getLogger("EncryptedMessagesLogger");
            FileHandler encryptedFh = new FileHandler("encrypted_messages.log", true);
            encryptedLogger.addHandler(encryptedFh);
            SimpleFormatter encryptedFormatter = new SimpleFormatter();
            encryptedFh.setFormatter(encryptedFormatter);

            // Logger for decrypted messages
            decryptedLogger = Logger.getLogger("DecryptedMessagesLogger");
            FileHandler decryptedFh = new FileHandler("decrypted_messages.log", true);
            decryptedLogger.addHandler(decryptedFh);
            SimpleFormatter decryptedFormatter = new SimpleFormatter();
            decryptedFh.setFormatter(decryptedFormatter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try (
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true)
        ) {
            String text;
            while ((text = reader.readLine()) != null) {
                String[] parts = text.split(":", 2);
                String command = parts[0];
                String message = parts.length > 1 ? parts[1] : "";

                switch (command) {
                    case "ENCRYPT":
                        byte[] encryptedMessage = encrypt(keyPair.getPublic(), message);
                        String encryptedMessageBase64 = Base64.getEncoder().encodeToString(encryptedMessage);
                        writer.println(encryptedMessageBase64);
                        encryptedLogger.info("Encrypted message: " + encryptedMessageBase64);
                        break;
                    case "DECRYPT":
                        byte[] decryptedMessage = decrypt(keyPair.getPrivate(), Base64.getDecoder().decode(message));
                        String decryptedMessageStr = new String(decryptedMessage);
                        writer.println(decryptedMessageStr);
                        decryptedLogger.info("Decrypted message: " + decryptedMessageStr);
                        break;
                    case "SIGN":
                        byte[] signature = sign(keyPair.getPrivate(), message);
                        writer.println(Base64.getEncoder().encodeToString(signature));
                        break;
                    case "VERIFY":
                        String[] verifyParts = message.split(":", 2);
                        String originalMessage = verifyParts[0];
                        byte[] signatureBytes = Base64.getDecoder().decode(verifyParts[1]);
                        boolean isVerified = verify(keyPair.getPublic(), originalMessage, signatureBytes);
                        writer.println(isVerified);
                        break;
                    default:
                        writer.println("Unknown command");
                        break;
                }
            }
        } catch (SocketException ex) {
            System.out.println("Connection reset by client: " + ex.getMessage());
            encryptedLogger.warning("Connection reset by client: " + ex.getMessage());
            decryptedLogger.warning("Connection reset by client: " + ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}