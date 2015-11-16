import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import javax.smartcardio.*;


public class Demo{

	public Demo() {

	}
	
	public static TerminalFactory terminalFactory = TerminalFactory.getDefault();
	public static Card card;
	public static CardChannel channel;
	public static CommandAPDU command;
	public static ResponseAPDU response;

	public static byte[] SelectAID = new byte[]{(byte) 0x00, (byte) 0xA4, (byte) 0x04, (byte) 0x00, (byte) 0x07,
		(byte) 0xF0, (byte) 0x39, (byte) 0x41, (byte) 0x48, (byte) 0x14, (byte) 0x81, (byte) 0x00, (byte) 0x00
	};

	/////////////////////////////////////// PERSO ///////////////////////////////////////////////
	
	public static byte[] SelectPersoUserID = new byte[]{
			(byte) 0x80, (byte) 0x01, (byte) 0x00, (byte) 0x00
	};
	public static byte[] SelectPersoUsername = new byte[]{
			(byte) 0x80, (byte) 0x01, (byte) 0x00, (byte) 0x01
	};
	public static byte[] SelectPersoCertiIssuedDate = new byte[]{
			(byte) 0x80, (byte) 0x01, (byte) 0x00, (byte) 0x02
	};
	public static byte[] SelectPersoCertiExpiredDate = new byte[]{
			(byte) 0x80, (byte) 0x01, (byte) 0x00, (byte) 0x03
	};
	public static byte[] SelectPersoCertiSerialNum = new byte[]{
			(byte) 0x80, (byte) 0x01, (byte) 0x00, (byte) 0x04
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
			(byte) 0x80, (byte) 0x01, (byte) 0x00, (byte) 0x08
	};
	public static byte[] SelectPersoMoney = new byte[]{
			(byte) 0x80, (byte) 0x01, (byte) 0x00, (byte) 0x09
	};
	
///////////////////////////////////// MANIPULATE PINCODE //////////////////////////////////////
	
	public static byte[] SelectVerifyPin = new byte[]{
			(byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x00
};
	
	public static byte[] SelectChangePin = new byte[]{
			(byte) 0x80, (byte) 0x03, (byte) 0x00, (byte) 0x00,
			(byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, 
						 (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
};
	
///////////////////////////////////// Write NORMAL DATA //////////////////////////////////////

	public static byte[] SelectAddMoney = new byte[]{
			(byte) 0x80, (byte) 0x04, (byte) 0x00, (byte) 0x00
};
	public static byte[] SelectPayMoney = new byte[]{
			(byte) 0x80, (byte) 0x04, (byte) 0x00, (byte) 0x01,
};
	public static byte[] SelectWriteDataExpiredDate = new byte[]{
			(byte) 0x80, (byte) 0x04, (byte) 0x00, (byte) 0x02
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
	
	public static void main(String[] args) {
			
					perso();				
					readUserInfo();
					writeData(intToByteArray(50), 1);
					writeData(intToByteArray(20), 2);
					writeData("20161231".getBytes(), 3);
	}

	public static void perso(){
		byte[] recv;
		try {
			for (CardTerminal terminal : terminalFactory.terminals().list()) {
				System.out.println(terminal.getName());
				try {
					Card card = terminal.connect("*");
					CardChannel channel = card.getBasicChannel();

					System.out.println("SelectAID ");
					CommandAPDU command = new CommandAPDU(SelectAID);
					ResponseAPDU response = channel.transmit(command);
					recv = response.getBytes();
					for (int i = 0; i < recv.length; i++) {
						System.out.print(String.format("%02X", recv[i]));
					}
					System.out.println("");
			//UserID
				String userID = "A120000000";
				byte[] byte_userID = userID.getBytes();
				byte[] send_userID = new byte[5+byte_userID.length];
				if(byte_userID.length <= 10){
					send_userID[4] = (byte) byte_userID.length;
					System.arraycopy(SelectPersoUserID, 0, send_userID, 0, SelectPersoUserID.length);
					System.arraycopy(byte_userID, 0, send_userID, 5, byte_userID.length);
					System.out.println("PersoUserID ");
					command = new CommandAPDU(send_userID);
					response = channel.transmit(command);
					recv = response.getBytes();
					for (int i = 0; i < recv.length; i++) {
						System.out.print(String.format("%02X", recv[i]));
					}
					System.out.println("");
				}else{
					System.out.println("userID too long");
				}
				
			//Username
				String username = "Keven";
				byte[] byte_username = username.getBytes();
				byte[] send_username = new byte[5+byte_username.length];
				if(byte_username.length <= 12){
					send_username[4] = (byte) byte_username.length;
					System.arraycopy(SelectPersoUsername, 0, send_username, 0, SelectPersoUsername.length);
					System.arraycopy(byte_username, 0, send_username, 5, byte_username.length);
					System.out.println("PersoUsername ");
					command = new CommandAPDU(send_username);
					response = channel.transmit(command);
					recv = response.getBytes();
					for (int i = 0; i < recv.length; i++) {
						System.out.print(String.format("%02X", recv[i]));
					}
					System.out.println("");
				}else{
					System.out.println("Username too long");
				}
				
				
			//IssueDate
				String certificateIssuedDate = "20151101";
				byte[] byte_certificateIssuedDate = certificateIssuedDate.getBytes();
				byte[] send_certificateIssuedDate = new byte[5+byte_certificateIssuedDate.length];
				if(byte_certificateIssuedDate.length <= 8){
					send_certificateIssuedDate[4] = (byte) byte_certificateIssuedDate.length;
					System.arraycopy(SelectPersoCertiIssuedDate, 0, send_certificateIssuedDate, 0, SelectPersoCertiIssuedDate.length);
					System.arraycopy(byte_certificateIssuedDate, 0, send_certificateIssuedDate, 5, byte_certificateIssuedDate.length);
					System.out.println("PersoCertiIssuedDate ");
					command = new CommandAPDU(send_certificateIssuedDate);
					response = channel.transmit(command);
					recv = response.getBytes();
					for (int i = 0; i < recv.length; i++) {
					System.out.print(String.format("%02X", recv[i]));
					}
					System.out.println("");
				}else{
					System.out.println("CertificateIssuedDate too long");
				}
				
				
			//ExpirationDate
				String certificateExpirationDate = "20161101";
				byte[] byte_certificateExpirationDate = certificateExpirationDate.getBytes();
				byte[] send_certificateExpirationDate = new byte[5+byte_certificateExpirationDate.length];
				if(byte_certificateExpirationDate.length <= 8){
					send_certificateExpirationDate[4] = (byte) byte_certificateExpirationDate.length;
					System.arraycopy(SelectPersoCertiExpiredDate, 0, send_certificateExpirationDate, 0, SelectPersoCertiExpiredDate.length);
					System.arraycopy(byte_certificateExpirationDate, 0, send_certificateExpirationDate, 5, byte_certificateExpirationDate.length);
					System.out.println("persoCertiExpiredDate ");
					command = new CommandAPDU(send_certificateExpirationDate);
					response = channel.transmit(command);
					recv = response.getBytes();
					for (int i = 0; i < recv.length; i++) {
					System.out.print(String.format("%02X", recv[i]));
					}
					System.out.println("");
				}else{
					System.out.println("ExpiredDate too long");
				}
				
				
	
			//CertiSerialNum
				String certiSerialNum = "A7fjg&fow#d";
				byte[] byte_certiSerialNum = certiSerialNum.getBytes();
				byte[] send_certiSerialNum = new byte[5+byte_certiSerialNum.length];
				send_certiSerialNum[4] = (byte) byte_certiSerialNum.length;
				System.arraycopy(SelectPersoCertiSerialNum, 0, send_certiSerialNum, 0, SelectPersoCertiSerialNum.length);
				System.arraycopy(byte_certiSerialNum, 0, send_certiSerialNum, 5, byte_certiSerialNum.length);
				
				System.out.println("persoCertiSerialNum ");
				command = new CommandAPDU(send_certiSerialNum);
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
				String pincode = "00000000";
				byte[] byte_pincode = pincode.getBytes();
				byte[] send_pincode = new byte[5+byte_pincode.length];
				if(byte_pincode.length <= 8){
					send_pincode[4] = (byte) byte_pincode.length;
					System.arraycopy(SelectPersoPinCode, 0, send_pincode, 0, SelectPersoPinCode.length);
					System.arraycopy(byte_pincode, 0, send_pincode, 5, byte_pincode.length);
					System.out.println("persoPinCode ");
					command = new CommandAPDU(send_pincode);
					response = channel.transmit(command);
					recv = response.getBytes();
					for (int i = 0; i < recv.length; i++) {
					System.out.print(String.format("%02X", recv[i]));
					}
					System.out.println("");
				}else{
					System.out.println("Pincode too long");
				}
				
				
			//Money
				int money = 100;
				byte[] byte_money = intToByteArray(money);
				byte[] send_money = new byte[5+byte_money.length];
				if(byte_money.length <= 10){
					send_money[4] = (byte) byte_money.length;
					System.arraycopy(SelectPersoMoney, 0, send_money, 0, SelectPersoMoney.length);
					System.arraycopy(byte_money, 0, send_money, 5, byte_money.length);
					System.out.println("persoMoney ");
					command = new CommandAPDU(send_money);
					response = channel.transmit(command);
					recv = response.getBytes();
					for (int i = 0; i < recv.length; i++) {
					System.out.print(String.format("%02X", recv[i]));
					}
					System.out.println("");
				
				}else{
					System.out.println("Money too long");
				}
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
	
	public static void writeData(byte[] data, int type)  //1.add money 2.pay money 3.update expiredDate
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
					String pin = "00000000";
					byte[] byte_pin = pin.getBytes();
					byte[] verifyPin = new byte[5+byte_pin.length];
					if(byte_pin.length == 8){
						verifyPin[4] = (byte) 8;
						System.arraycopy(SelectVerifyPin, 0, verifyPin, 0, SelectVerifyPin.length);
						System.arraycopy(byte_pin, 0, verifyPin, 5, byte_pin.length);
						command = new CommandAPDU(verifyPin);
						response = channel.transmit(command);
						recv = response.getBytes();
						for (int i = 0; i < recv.length; i++) {
							System.out.print(String.format("%02X", recv[i]));
						}
						System.out.println("");
					}
					
					
					System.out.println("writeData");

					byte[] writeData = new byte[5+data.length];
					writeData[4] = (byte) data.length;
					
					switch(type) {
						case 1:
							System.arraycopy(SelectAddMoney, 0, writeData, 0, SelectAddMoney.length);
							System.arraycopy(data, 0, writeData, 5, data.length);
							break;
						case 2:
							System.arraycopy(SelectPayMoney, 0, writeData, 0, SelectPayMoney.length);
							System.arraycopy(data, 0, writeData, 5, data.length);
							break;
						case 3:
							System.arraycopy(SelectWriteDataExpiredDate, 0, writeData, 0, SelectWriteDataExpiredDate.length);
							System.arraycopy(data, 0, writeData, 5, data.length);
							break;
					}
					
					command = new CommandAPDU(writeData);
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
		
	public static void readUserInfo()
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
					String pin = "00000000";
					byte[] byte_pin = pin.getBytes();
					byte[] verifyPin = new byte[5+byte_pin.length];
					if(byte_pin.length == 8){
						verifyPin[4] = (byte) 8;
						System.arraycopy(SelectVerifyPin, 0, verifyPin, 0, SelectVerifyPin.length);
						System.arraycopy(byte_pin, 0, verifyPin, 5, byte_pin.length);
						command = new CommandAPDU(verifyPin);
						response = channel.transmit(command);
						recv = response.getBytes();
						for (int i = 0; i < recv.length; i++) {
							System.out.print(String.format("%02X", recv[i]));
						}
						System.out.println("");
					}
					
					
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
					int money = parseMoney(response.getBytes());
					System.out.println("Money: "+ money);
					
					
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
	
	public static String parser(byte[] receive){
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
	
	public static int parseMoney(byte[] receive){
		int data;
		if(receive[0] == (byte)0x90 && receive[1] == (byte)0x00){
			byte[] b_data = Arrays.copyOfRange(receive, 2, receive.length);
			data = byteArrayToInt(b_data);
		}
		else {
			System.out.println("Something went wrong~~~");
			for (int i = 0; i < receive.length; i++) {
				System.out.print(String.format("%02X", receive[i]));
			}
			data=0;
		}
		return data;
	}
	
}