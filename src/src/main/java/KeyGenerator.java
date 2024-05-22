
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.IOException;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class KeyGenerator {

    private static Logger keyLogger;

    static {
        Security.addProvider(new BouncyCastleProvider());
        initializeLogger();
    }

    private static void initializeLogger() {
        try {
            keyLogger = Logger.getLogger("KeyLogger");
            FileHandler keyFh = new FileHandler("keys.log", true);
            keyLogger.addHandler(keyFh);
            SimpleFormatter formatter = new SimpleFormatter();
            keyFh.setFormatter(formatter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static KeyPair generateKeyPair(String owner) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC", "BC");
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
        keyGen.initialize(ecSpec, new SecureRandom());
        KeyPair keyPair = keyGen.generateKeyPair();

        logKeyPair(keyPair, owner);

        return keyPair;
    }

    private static void logKeyPair(KeyPair keyPair, String owner) {
        String publicKeyBase64 = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        String privateKeyBase64 = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        keyLogger.info(owner + " Public Key: " + publicKeyBase64);
        keyLogger.info(owner + " Private Key: " + privateKeyBase64);
    }
}

