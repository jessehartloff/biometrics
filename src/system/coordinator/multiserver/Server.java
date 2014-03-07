package system.coordinator.multiserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Server {
	public void RSA() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, ClassNotFoundException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		
		String encryptThis = "jacordar calculus";
		byte[] keyS = encryptThis.getBytes();
		MessageDigest sha = MessageDigest.getInstance("SHA-1");
		keyS=sha.digest(keyS);
		keyS= Arrays.copyOf(keyS, 16);
		SecretKeySpec secretKeySpec = new SecretKeySpec(keyS,"RSA");
		System.out.println("Secret Key :" + new String(secretKeySpec.getEncoded(),"UTF-8"));
		
		int port = 0;
		ServerSocket serv_socket = null;
		Socket client = null;
		serv_socket = new ServerSocket(0);
		port = serv_socket.getLocalPort();
		System.out.println(port);
		
		client = serv_socket.accept();
		System.out.println("Connected!  " );
		ObjectInputStream objInput = new ObjectInputStream(client.getInputStream());
		PublicKey pk = (PublicKey) objInput.readObject();
		System.out.println(pk.toString());
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE,pk);
		byte[] encryptedKey = cipher.doFinal(secretKeySpec.getEncoded());
		//System.out.println("Length of Encrypted Key : " + encryptedKey.length);
		System.out.println("Key Encrypted With Public Key : " + new String(encryptedKey, "UTF-8"));
		DataOutputStream dataOut = new DataOutputStream(client.getOutputStream());
		dataOut.write(encryptedKey,0,encryptedKey.length);
		
	}

	public static void main(String[] args) throws NoSuchProviderException, InvalidKeySpecException, ClassNotFoundException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IOException {
		// TODO Auto-generated method stub
		Server server = new Server();
		server.RSA();
	
	}
	
}
