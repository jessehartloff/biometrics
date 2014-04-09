package system.coordinator.multiserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import settings.coordinatorsettings.multiservercoordinatorsettings.ServerOneSettings;
import system.allcommonclasses.commonstructures.Users;
import system.coordinator.Coordinator;
import system.hasher.Hasher;

public abstract class Server extends Coordinator {
	
	public Server(Hasher hasher, Users users) {
		super(hasher, users);
	}

	// base server class extends coordinator

	protected InterServerObjectWrapper receive(ServerSocket serverSocket){
		try{
			Socket client = serverSocket.accept();
			ObjectInputStream objIn = new ObjectInputStream (client.getInputStream());
			InterServerObjectWrapper receivedObject = (InterServerObjectWrapper) objIn.readObject();
			client.close();
			return receivedObject;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected void send(String ip, Long port, InterServerObjectWrapper message){
		try {
			Socket socket = new Socket(InetAddress.getByName(ip), port.intValue());
			ObjectOutputStream objOutput = new ObjectOutputStream(socket.getOutputStream());
			objOutput.writeObject(message);
			socket.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * public void RSA() throws NoSuchAlgorithmException, IOException,
	 * InvalidKeySpecException, ClassNotFoundException, NoSuchPaddingException,
	 * InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
	 * 
	 * String encryptThis = "jacordar calculus"; byte[] keyS =
	 * encryptThis.getBytes(); MessageDigest sha =
	 * MessageDigest.getInstance("SHA-1"); keyS=sha.digest(keyS); keyS=
	 * Arrays.copyOf(keyS, 16); SecretKeySpec secretKeySpec = new
	 * SecretKeySpec(keyS,"DH"); System.out.println("Secret Key :" + new
	 * String(secretKeySpec.getEncoded(),"UTF-8"));
	 * 
	 * int port = 0; ServerSocket serv_socket = null; Socket client = null;
	 * serv_socket = new ServerSocket(0); port = serv_socket.getLocalPort();
	 * System.out.println(port);
	 * 
	 * client = serv_socket.accept(); System.out.println("Connected!  " );
	 * ObjectInputStream objInput = new
	 * ObjectInputStream(client.getInputStream()); PublicKey pk = (PublicKey)
	 * objInput.readObject(); System.out.println(pk.toString()); long
	 * startEncryption = System.currentTimeMillis(); Cipher cipher =
	 * Cipher.getInstance("DH"); cipher.init(Cipher.ENCRYPT_MODE,pk); byte[]
	 * encryptedKey = cipher.doFinal(secretKeySpec.getEncoded()); long
	 * finishEncryption = System.currentTimeMillis();
	 * System.out.println("Time to encrypt is " + (finishEncryption -
	 * startEncryption)); System.out.println("Key Encrypted With Public Key : "
	 * + new String(encryptedKey, "UTF-8")); DataOutputStream dataOut = new
	 * DataOutputStream(client.getOutputStream());
	 * dataOut.write(encryptedKey,0,encryptedKey.length);
	 * 
	 * }
	 * 
	 * public BigInteger encrypt(PublicKey publicKey, BigInteger minutia){
	 * byte[] encryptedKey = null; try{ Cipher cipher =
	 * Cipher.getInstance("DH"); cipher.init(Cipher.ENCRYPT_MODE,publicKey);
	 * encryptedKey = cipher.doFinal(minutia.toByteArray());
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return new BigInteger(encryptedKey); }
	 * 
	 * public static void main(String[] args) throws Exception{ Server server =
	 * new Server(); server.RSA();
	 * 
	 * }
	 */

}
