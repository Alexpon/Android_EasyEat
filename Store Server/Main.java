import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import javax.smartcardio.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main{

	public Main() {

	}
	
	public static TerminalFactory terminalFactory = TerminalFactory.getDefault();
	public static Card card;
	public static CardChannel channel;
	public static CommandAPDU command;
	public static ResponseAPDU response;

	//wallet
	public static byte[] SelectAID = new byte[]{(byte) 0x00, (byte) 0xA4, (byte) 0x04, (byte) 0x00, (byte) 0x07,
		(byte) 0xF0, (byte) 0x39, (byte) 0x41, (byte) 0x48, (byte) 0x14, (byte) 0x81, (byte) 0x00, (byte) 0x00
	};

	/////////////////////////////////////// PERSO ///////////////////////////////////////////////
	
	public static byte[] SelectPersoUserID = new byte[]{
			(byte) 0x80, (byte) 0x01, (byte) 0x00, (byte) 0x00,
			(byte) 0x0a, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
						 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
	};
	public static byte[] SelectPersoUsername = new byte[]{
			(byte) 0x80, (byte) 0x01, (byte) 0x00, (byte) 0x01,
			(byte) 0x0c, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
						 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
	};
	public static byte[] SelectPersoCertiIssuedDate = new byte[]{
			(byte) 0x80, (byte) 0x01, (byte) 0x00, (byte) 0x02,
			(byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
	};
	public static byte[] SelectPersoCertiExpiredDate = new byte[]{
			(byte) 0x80, (byte) 0x01, (byte) 0x00, (byte) 0x03,
			(byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
	};
	public static byte[] SelectPersoCertiSerialNum = new byte[]{
			(byte) 0x80, (byte) 0x01, (byte) 0x00, (byte) 0x04,
			(byte) 0x0c, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
						 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
	};
	public static byte[] SelectPersoPublicKey = new byte[]{
			(byte) 0x80, (byte) 0x01, (byte) 0x00, (byte) 0x05, 
			(byte) 0x40, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
						 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
						 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
						 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
						 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
						 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
						 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
						 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
	};
	public static byte[] SelectPersoPrivateKey = new byte[]{
			(byte) 0x80, (byte) 0x01, (byte) 0x00, (byte) 0x06, 
			(byte) 0x40, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
						 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
						 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
						 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
						 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
						 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
						 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
						 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
	};
	public static byte[] SelectPersoDigitalSign = new byte[]{
			(byte) 0x80, (byte) 0x01, (byte) 0x00, (byte) 0x07, 
			(byte) 0x20, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			 			 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
			 			 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
			 			 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
	};
	public static byte[] SelectPersoPinCode = new byte[]{
			(byte) 0x80, (byte) 0x01, (byte) 0x00, (byte) 0x08,
			(byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
						 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
	};
	public static byte[] SelectPersoMoney = new byte[]{
			(byte) 0x80, (byte) 0x01, (byte) 0x00, (byte) 0x09,
			(byte) 0x0a, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
						 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
	};
	
///////////////////////////////////// MANIPULATE PINCODE //////////////////////////////////////
	
	public static byte[] SelectVerifyPin = new byte[]{
			(byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x00,
			(byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
						 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
};
	
	public static byte[] SelectChangePin = new byte[]{
			(byte) 0x80, (byte) 0x03, (byte) 0x00, (byte) 0x00,
			(byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
						 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
};
	
///////////////////////////////////// Write NORMAL DATA //////////////////////////////////////

	public static byte[] SelectAddMoney = new byte[]{
			(byte) 0x80, (byte) 0x04, (byte) 0x00, (byte) 0x00,
			(byte) 0x0a, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
						 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
};
	public static byte[] SelectPayMoney = new byte[]{
			(byte) 0x80, (byte) 0x04, (byte) 0x00, (byte) 0x01,
			(byte) 0x0a, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
						 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
};
	public static byte[] SelectWriteDataExpiredDate = new byte[]{
			(byte) 0x80, (byte) 0x04, (byte) 0x00, (byte) 0x02,
			(byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
						 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
};
	
////////////////////////////////////// READ NORMAL DATA //////////////////////////////////////
	
	public static byte[] SelectReadUserID = new byte[]{
			(byte) 0x80, (byte) 0x05, (byte) 0x00, (byte) 0x00, (byte) 0x00
};
	public static byte[] SelectReadUsername = new byte[]{
			(byte) 0x80, (byte) 0x05, (byte) 0x00, (byte) 0x01, (byte) 0x00
};
	public static byte[] SelectReadCertificateIssuedDate = new byte[]{
			(byte) 0x80, (byte) 0x05, (byte) 0x00, (byte) 0x02, (byte) 0x00
};
	public static byte[] SelectReadCertificateExpirationDate = new byte[]{
			(byte) 0x80, (byte) 0x05, (byte) 0x00, (byte) 0x03, (byte) 0x00
};
	public static byte[] SelectReadMoney = new byte[]{
			(byte) 0x80, (byte) 0x05, (byte) 0x00, (byte) 0x04, (byte) 0x00
};
	
///////////////////////////////////READ CONFIDANTIAL DATA ///////////////////////////////////

	public static byte[] SelectReadPublicKey = new byte[]{
			(byte) 0x80, (byte) 0x05, (byte) 0x00, (byte) 0x04, (byte) 0x00
};
	public static byte[] SelectReadPrivateKey = new byte[]{
			(byte) 0x80, (byte) 0x05, (byte) 0x00, (byte) 0x04, (byte) 0x00
};
	public static byte[] SelectReadSignature = new byte[]{
			(byte) 0x80, (byte) 0x05, (byte) 0x00, (byte) 0x04, (byte) 0x00
};
	public static byte[] SelectReadSerialNumber = new byte[]{
			(byte) 0x80, (byte) 0x05, (byte) 0x00, (byte) 0x04, (byte) 0x00
};
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////NEW    END//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static int byteArrayToInt(byte[] b) 
	{
	    return   b[3] & 0xFF |
	            (b[2] & 0xFF) << 8 |
	            (b[1] & 0xFF) << 16 |
	            (b[0] & 0xFF) << 24;
	}

	public static byte[] intToByteArray(int a)
	{
	    return new byte[] {
	        (byte) ((a >> 24) & 0xFF),
	        (byte) ((a >> 16) & 0xFF),   
	        (byte) ((a >> 8) & 0xFF),   
	        (byte) (a & 0xFF)
	    };
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		try {
			for (CardTerminal terminal : terminalFactory.terminals().list()) {
				System.out.println(terminal.getName());
				try {
					Card card = terminal.connect("*");
					CardChannel channel = card.getBasicChannel();

					System.out.println("SelectAID ");
					CommandAPDU command = new CommandAPDU(SelectAID);
					ResponseAPDU response = channel.transmit(command);
					byte recv[] = response.getBytes();
					for (int i = 0; i < recv.length; i++) {
						System.out.print(String.format("%02X", recv[i]));
					}
					System.out.println("");
			
/////////////////////////////////////Perso///////////////////////////////////////////////
					
					perso();
					
///////////////////////////////////END Perso//////////////////////////////////////////
					
					//VerifyPin
					System.out.println("verifyPin ");
					command = new CommandAPDU(SelectVerifyPin);
					response = channel.transmit(command);				
					recv = response.getBytes();
					for (int i = 0; i < recv.length; i++) {
					System.out.print(String.format("%02X", recv[i]));
					}
					System.out.println("");
					
					//UserID
					System.out.println("read userID");
					command = new CommandAPDU(SelectReadUserID);
					response = channel.transmit(command);
					recv = response.getBytes();
					for (int i = 0; i < 2; i++) {
						System.out.println(String.format("%02X", recv[i]));
					}
					byte[] realData1 = new byte[recv.length-2];
					System.arraycopy(recv, 2, realData1, 0, recv.length-2);
					String recvComID1 = new String(realData1);
					System.out.println("userID: " + recvComID1);
					
					//Username
					System.out.println("read username");
					command = new CommandAPDU(SelectReadUsername);
					response = channel.transmit(command);				
					recv = response.getBytes();
					realData1 = new byte[recv.length-2];
					System.arraycopy(recv, 2, realData1, 0, recv.length-2);
					String recvLoyaltyID1 = new String(realData1);
					System.out.println("Username: " + recvLoyaltyID1);
							
					//Money
					System.out.println("read money");
					command = new CommandAPDU(SelectReadMoney);
					response = channel.transmit(command);
					recv = response.getBytes();
					for (int i = 0; i < 2; i++) {
						System.out.print(String.format("%02X", recv[i]));
					}
					System.out.println();
					byte[] realData2 = new byte[recv.length-2];
					System.arraycopy(recv, 2, realData2, 0, recv.length-2);
					String recMoney = new String(realData2);
					System.out.println("money: " + recMoney);
						
				} catch (javax.smartcardio.CardNotPresentException e) {
					// e.printStackTrace();
					continue;
				} catch (CardException e) {
					// e.printStackTrace();
					continue;
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (CardException e) {
			e.printStackTrace();
		}

	}

	public static void perso() throws CardException{
		
		byte[] recv;
		
	//UserID
		String userID = "A12000000";
		byte[] byte_userID = userID.getBytes();
		if(byte_userID.length < 10){
			System.arraycopy(byte_userID, 0, SelectPersoUserID, SelectPersoUserID.length-byte_userID.length-2, byte_userID.length);
		}else{
			System.out.println("userID too long");
		}
		System.out.println("PersoUserID ");
		command = new CommandAPDU(SelectPersoUserID);
		response = channel.transmit(command);
		recv = response.getBytes();
		for (int i = 0; i < recv.length; i++) {
			System.out.print(String.format("%02X", recv[i]));
		}
		System.out.println("");
		
	//Username
		String username = "Keven";
		byte[] byte_username = username.getBytes();
		if(byte_username.length < 12){
			System.arraycopy(byte_username, 0, SelectPersoUsername, SelectPersoUsername.length-byte_username.length-2, byte_username.length);
		}else{
			System.out.println("Username too long");
		}
		System.out.println("PersoUsername ");
		command = new CommandAPDU(SelectPersoUsername);
		response = channel.transmit(command);
		
		recv = response.getBytes();
		for (int i = 0; i < recv.length; i++) {
		System.out.print(String.format("%02X", recv[i]));
		}
		System.out.println("");
		
	//IssueDate
		String certificateIssuedDate = "20151101";
		byte[] byte_certificateIssuedDate = certificateIssuedDate.getBytes();
		if(byte_certificateIssuedDate.length < 6){
			System.arraycopy(byte_certificateIssuedDate, 0, SelectPersoCertiIssuedDate, SelectPersoCertiIssuedDate.length-byte_certificateIssuedDate.length-2, byte_certificateIssuedDate.length);
		}else{
			System.out.println("CertificateIssuedDate too long");
		}
		System.out.println("PersoCertiIssuedDate ");
		command = new CommandAPDU(SelectPersoCertiIssuedDate);
		response = channel.transmit(command);
		
		recv = response.getBytes();
		for (int i = 0; i < recv.length; i++) {
		System.out.print(String.format("%02X", recv[i]));
		}
		System.out.println("");
		
	//ExpirationDate
		String certificateExpirationDate = "20161101";
		byte[] byte_certificateExpirationDate = certificateExpirationDate.getBytes();
		if(byte_certificateExpirationDate.length < 6){
			System.arraycopy(byte_certificateExpirationDate, 0, SelectPersoCertiExpiredDate, SelectPersoCertiExpiredDate.length-byte_certificateExpirationDate.length-2, byte_certificateExpirationDate.length);
		}else{
			System.out.println("ExpiredDate too long");
		}
		System.out.println("persoCertiExpiredDate ");
		command = new CommandAPDU(SelectPersoCertiExpiredDate);
		response = channel.transmit(command);
		
		recv = response.getBytes();
		for (int i = 0; i < recv.length; i++) {
		System.out.print(String.format("%02X", recv[i]));
		}
		System.out.println("");
		

	//CertiSerialNum
		System.out.println("persoCertiSerialNum ");
		command = new CommandAPDU(SelectPersoCertiSerialNum);
		response = channel.transmit(command);
		
		recv = response.getBytes();
		for (int i = 0; i < recv.length; i++) {
		System.out.print(String.format("%02X", recv[i]));
		}
		System.out.println("");
		
	//PublicKey
		System.out.println("persoPublicKey ");
		command = new CommandAPDU(SelectPersoPublicKey);
		response = channel.transmit(command);
		
		recv = response.getBytes();
		for (int i = 0; i < recv.length; i++) {
		System.out.print(String.format("%02X", recv[i]));
		}
		System.out.println("");
		
	//PrivateKey
		System.out.println("persoPrivateKey ");
		command = new CommandAPDU(SelectPersoPrivateKey);
		response = channel.transmit(command);
		
		recv = response.getBytes();
		for (int i = 0; i < recv.length; i++) {
		System.out.print(String.format("%02X", recv[i]));
		}
		System.out.println("");
		
	//DigitalSign
		System.out.println("persoDigitalSign ");
		command = new CommandAPDU(SelectPersoDigitalSign);
		response = channel.transmit(command);
		
		recv = response.getBytes();
		for (int i = 0; i < recv.length; i++) {
		System.out.print(String.format("%02X", recv[i]));
		}
		System.out.println("");
		
	//Pincode
		String pincode = "0000";
		byte[] byte_pincode = pincode.getBytes();
		if(byte_pincode.length < 8){
			System.arraycopy(byte_pincode, 0, SelectPersoPinCode, SelectPersoPinCode.length-byte_pincode.length-2, byte_pincode.length);
		}else{
			System.out.println("Pincode too long");
		}
		System.out.println("persoPinCode ");
		command = new CommandAPDU(SelectPersoPinCode);
		response = channel.transmit(command);
		recv = response.getBytes();
		for (int i = 0; i < recv.length; i++) {
		System.out.print(String.format("%02X", recv[i]));
		}
		System.out.println("");
		
	//Money
		int money = 0;
		byte[] byte_money = intToByteArray(money);
		if(byte_money.length < 10){
			System.arraycopy(byte_money, 0, SelectPersoMoney, SelectPersoMoney.length-byte_money.length-2, byte_money.length);
		}else{
			System.out.println("Money too long");
		}
		System.out.println("persoMoney ");
		command = new CommandAPDU(SelectPersoMoney);
		response = channel.transmit(command);
		recv = response.getBytes();
		for (int i = 0; i < recv.length; i++) {
		System.out.print(String.format("%02X", recv[i]));
		}
		System.out.println("");
	}
	
	public void WriteMoney(int money, String type)
	{
		try {
			for (CardTerminal terminal : terminalFactory.terminals().list()) {
				System.out.println(terminal.getName());
				try {
					Card card = terminal.connect("*");
					CardChannel channel = card.getBasicChannel();

					System.out.println("SelectAID ");
					CommandAPDU command = new CommandAPDU(SelectAID);
					ResponseAPDU response = channel.transmit(command);
					byte recv[] = response.getBytes();
					for (int i = 0; i < recv.length; i++) {
						System.out.print(String.format("%02X", recv[i]));
					}
					System.out.println("");
					System.out.println("verifyPin ");
					command = new CommandAPDU(SelectVerifyPin);
					response = channel.transmit(command);				
					System.out.println("writeMoney");

					byte[] writeMoney;
					byte[] b_money = intToByteArray(money);
					
					if(type=="add"){
						writeMoney = SelectAddMoney;
					}else{
						writeMoney = SelectPayMoney;
					}
					
					if(b_money.length > 10)
						writeMoney[4] = (byte)0x00;
					else
						System.arraycopy(b_money, 0, writeMoney, 5, 15);
					
					command = new CommandAPDU(writeMoney);
					response = channel.transmit(command);
					recv = response.getBytes();
					for (int i = 0; i < recv.length; i++) {
						System.out.print(String.format("%02X", recv[i]));
					}
					System.out.println("");
				} catch (javax.smartcardio.CardNotPresentException e1) {
					// e.printStackTrace();
					continue;
				} catch (CardException e2) {
					// e.printStackTrace();
					continue;
				}
			}
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (CardException e2) {
			e2.printStackTrace();
		}
	}
		
	public void ReadUserInfo(String type)
	{
		try {
			for (CardTerminal terminal : terminalFactory.terminals().list()) {
				System.out.println(terminal.getName());
				try {
					Card card = terminal.connect("*");
					CardChannel channel = card.getBasicChannel();

					System.out.println("SelectAID ");
					CommandAPDU command = new CommandAPDU(SelectAID);
					ResponseAPDU response = channel.transmit(command);
					byte[] recv = response.getBytes();
					for (int i = 0; i < recv.length; i++) {
						System.out.print(String.format("%02X", recv[i]));
					}
					System.out.println();
					
					System.out.println("verifyPin ");
					command = new CommandAPDU(SelectVerifyPin);
					response = channel.transmit(command);
					
					System.out.println("");
					System.out.println("readData");
					
					command = new CommandAPDU(SelectReadUserID);
					response = channel.transmit(command);
					String userID = parser(response.getBytes());
					System.out.println("UserID: "+userID);
					
					command = new CommandAPDU(SelectReadUsername);
					response = channel.transmit(command);
					String username = parser(response.getBytes());
					System.out.println("Username: "+username);
					
					command = new CommandAPDU(SelectReadCertificateIssuedDate);
					response = channel.transmit(command);
					String issuedDate = parser(response.getBytes());
					System.out.println("CertificateIssuedDate: "+issuedDate);
					
					command = new CommandAPDU(SelectReadCertificateExpirationDate);
					response = channel.transmit(command);
					String expirationDate = parser(response.getBytes());
					System.out.println("CertificateExpirationDate: "+expirationDate);
					
					command = new CommandAPDU(SelectReadMoney);
					response = channel.transmit(command);
					String money = parser(response.getBytes());
					System.out.println("Money: "+money);
					
				} catch (javax.smartcardio.CardNotPresentException e1) {
					// e.printStackTrace();
					continue;
				} catch (CardException e2) {
					// e.printStackTrace();
					continue;
				}
			}
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (CardException e2) {
			e2.printStackTrace();
		}
	}
	
	public String parser(byte[] receive){
		String data;
		if(receive[0] == (byte)0x90 && receive[1] == (byte)0x00){
			byte[] b_data = Arrays.copyOfRange(receive, 2, receive.length);
			data = new String(b_data);
		}
		else {
			System.out.println("Something went wrong~~~");
			for (int i = 0; i < receive.length; i++) {
				System.out.print(String.format("%02X", receive[i]));
			}
			data="";
		}
		return data;
	}
	
}