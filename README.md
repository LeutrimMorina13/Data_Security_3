# SD_Faza3
Polybius Square Cipher and Myszkowski Transposition Project

Professor [Arbena Musa](https://github.com/ArbenaMusa)

# Language
Project PGP is developed in Java language.

# Description of algorithms.

Pretty Good Privacy (PGP) is a widely-used encryption program that provides cryptographic privacy and authentication for data communication. Developed by Phil Zimmermann in 1991, PGP uses a combination of symmetric-key cryptography and public-key cryptography to secure emails, files, and other forms of data.

 Key Components of PGP:

1. Public and Private Keys:
   - PGP uses a pair of keys for encryption and decryption. The public key is shared with others to encrypt messages, while the private key is kept secret by the owner to decrypt those messages.
   
2. Digital Signatures:
   - PGP can create a digital signature to verify the sender's identity and ensure that the message has not been tampered with. This involves hashing the message and encrypting the hash with the sender's private key.

3. Key Management:
   - PGP uses a decentralized trust model called the "web of trust." Users sign each other's public keys, creating a network of trusted connections. This allows users to verify the authenticity of public keys without relying on a central authority.

How PGP Works:

1. Encryption:
   - The sender generates a session key, a one-time-use symmetric key, to encrypt the plaintext message.
   - The session key is then encrypted with the recipient's public key.
   - Both the encrypted message and the encrypted session key are sent to the recipient.
   - 
2. Decryption:
   - The recipient uses their private key to decrypt the session key.
   - The decrypted session key is then used to decrypt the message.

3. Digital Signing:
   - The sender creates a hash of the message using a hash function.
   - This hash is then encrypted with the sender's private key to create a digital signature.
   - The message and the digital signature are sent to the recipient.

4. Verification:
   - The recipient decrypts the digital signature using the sender's public key to retrieve the hash.
   - The recipient also hashes the received message.
   - If the hashes match, the signature is verified, confirming the sender's identity and the message's integrity.

Applications of PGP:

- Email Security: PGP is often used to encrypt email messages, ensuring that only the intended recipient can read them.
- File Encryption: PGP can encrypt files for secure storage or transmission.
- Authentication: PGP digital signatures can be used to authenticate software distributions and documents.
  

 # How to execute the program.
 
1. Start the Server:
   - Begin by launching the server application. This server will handle all encryption, decryption, signing, and verification tasks based on client requests.

2. Start the Client:
   - Next, run the client application. The client interface will provide the user with various options to interact with the server.

3. Select an Operation:
   - The client will present four primary options:
     1. Encrypt- To secure a message by converting it into ciphertext.
     2. Decrypt - To convert the ciphertext back into its original plaintext form.
     3. Sign - To create a digital signature for a message, ensuring its authenticity and integrity.
     4. Verify - To check the validity of a digital signature, confirming the message's origin and integrity.
     5. Exit - To close the client application.

4. Enter the Message:
   - Based on the selected operation, the user will be prompted to enter the message to be processed.

5. Server Processing:
   - The server receives the client's request and performs the chosen operation (encrypt, decrypt, sign, or verify) on the provided message.

6. Receive the Output:
   - The processed message (encrypted, decrypted, signed, or verified) is sent back from the server to the client, completing the operation.


 # Confidential
This project is developed from the authors below with full rights.

# Authors

[Elda Qollaku](https://github.com/eldaaqollaku)


[Leonita Sinani](https://github.com/leonitaas)


[Linda Hasanaj](https://github.com/Linda-Hasanaj)


[Leutrim Morina](https://github.com/LeutrimMorina13)

 
