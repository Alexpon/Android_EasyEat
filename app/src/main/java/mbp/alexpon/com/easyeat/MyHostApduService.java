package mbp.alexpon.com.easyeat;


import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;

public class MyHostApduService extends HostApduService {
    static byte walletBalance = 0;

    byte[] userID = new byte[10];
    byte[] username = new byte[12];
    byte[] certiIssuedDate = new byte[6];
    byte[] certiExpiredDate = new byte[6];
    byte[] certiSerialNum = new byte[12];
    byte[] publicKey = new byte[64];
    byte[] privateKey = new byte[64];
    byte[] digitalSign = new byte[32];
    byte[] pinCode = new byte[8];
    byte[] money = new byte[10];
    boolean pinVerified = false;

    private static final byte[] AID_SELECT_APDU = {
            (byte) 0x00, // CLA (class of command)
            (byte) 0xA4, // INS (instruction); A4 = select
            (byte) 0x04, // P1  (parameter 1)  (0x04: select by name)
            (byte) 0x00, // P2  (parameter 2)
            (byte) 0x07, // LC  (length of data)
            (byte) 0xF0, (byte) 0x39, (byte) 0x41, (byte) 0x48, (byte) 0x14, (byte) 0x81, (byte) 0x00,
            (byte) 0x00 // LE   (max length of expected result, 0 implies 256)
    };

    @Override
    public byte[] processCommandApdu(byte[] apdu, Bundle extras) {
        String inboundApduDescription;
        byte[] responseApdu;

        if (Arrays.equals(AID_SELECT_APDU, apdu)) {
            inboundApduDescription = "Application selected";
            Log.i("HCEDEMO", inboundApduDescription);
            byte[] answer = new byte[2];
            answer[0] = (byte) 0x90;
            answer[1] = (byte) 0x00;
            responseApdu = answer;
            return responseApdu;
        }
/////////////////////////////////////////////////////////////// 11.12 NEW //////////////////////////////////////////////////////////
        else if (selectPersoApdu(apdu)){
            byte[] returnVal = new byte[2];
            returnVal[0] = (byte) 0x90;
            returnVal[1] = (byte) 0x00;
            switch (apdu[2]){
                case 0x00:
                    userID = Arrays.copyOfRange(apdu, 5, 15);
                    break;
                case 0x01:
                    username = Arrays.copyOfRange(apdu, 5, 17);
                    break;
                case 0x02:
                    certiIssuedDate = Arrays.copyOfRange(apdu, 5, 11);
                    break;
                case 0x03:
                    certiExpiredDate = Arrays.copyOfRange(apdu, 5, 11);
                    break;
                case 0x04:
                    certiSerialNum = Arrays.copyOfRange(apdu, 5, 11);
                    break;
                case 0x05:
                    publicKey = Arrays.copyOfRange(apdu, 5, 69);
                    break;
                case 0x06:
                    privateKey = Arrays.copyOfRange(apdu, 5, 69);
                    break;
                case 0x07:
                    digitalSign = Arrays.copyOfRange(apdu, 5, 37);
                    break;
                case 0x08:
                    pinCode = Arrays.copyOfRange(apdu, 5, 13);
                    break;
                case 0x09:
                    money = Arrays.copyOfRange(apdu, 5, 15);
                    break;
                default:
                    returnVal[0] = (byte) 0x01;
                    returnVal[1] = (byte) 0x00;
                    break;
            }
            return returnVal;
        }

        else if (selectVerifyPinApdu(apdu)) {
            Log.i("HCEDEMO", "VerifyPin selected");
            int length = apdu[4];
            System.out.println("length = " + length);
            byte[] answer = new byte[2];
            if(length == 8)
            {
                byte[] pinToVerify = Arrays.copyOfRange(apdu, 5, 13);
                if (Arrays.equals(pinToVerify, pinCode)) {
                    answer[0] = (byte) 0x09;
                    answer[1] = (byte) 0x00;
                    pinVerified = true;
                } else {
                    answer[0] = (byte) 0x09;
                    answer[1] = (byte) 0x02;
                    pinVerified = false;
                }
            }
            responseApdu = answer;
            return responseApdu;
        }

        else if (selectChangePinApdu(apdu)) {
            Log.i("HCEDEMO", "ChangePin selected");
            int length = apdu[4];
            System.out.println("length = " + length);
            byte[] answer = new byte[2];

            if(pinVerified){
                if(length < 8){
                    answer[0] = (byte) 0x0a;
                    answer[1] = (byte) 0x02;
                }
                else if(length > 8){
                    answer[0] = (byte) 0x0a;
                    answer[1] = (byte) 0x03;
                }
                else{
                    pinCode = Arrays.copyOfRange(apdu, 5, 13);
                    answer[0] = (byte) 0x90;
                    answer[1] = (byte) 0x00;
                }
            }
            else{
                answer[0] = (byte) 0x0a;
                answer[1] = (byte) 0x04;
            }
            pinVerified = false;
            responseApdu = answer;
            return responseApdu;
        }

///////////////////////////////////////////////////////////////////// 11.12 NEW ///////////////////////////////////////////////////

        else if (selectAddMoneyApdu(apdu)) {
            Log.i("HCEDEMO", "ADD selected");
            int length = apdu[4];
            System.out.println("length = " + length);
            byte[] answer = new byte[3];

            walletBalance = (byte)(walletBalance + apdu[5]);
            answer[0] = (byte) 0x90;
            answer[1] = (byte) 0x00;
            answer[2] = walletBalance;
            responseApdu = answer;
            return responseApdu;
        }

        else if (selectDebitApdu(apdu)) {
            Log.i("HCEDEMO", "Debit selected");
            int length = apdu[4];
            System.out.println("length = " + length);
            byte[] answer = new byte[3];

            // balance can not be negative
            if ( (byte)( (byte) walletBalance - apdu[5]) < (byte) 0 ) {
                answer[0] = (byte) 0x01;
                answer[1] = (byte) 0x02;
                responseApdu = answer;
                return responseApdu;
            }

            walletBalance = (byte)(walletBalance - apdu[5]);
            answer[0] = (byte) 0x90;
            answer[1] = (byte) 0x00;
            answer[2] = walletBalance;
            responseApdu = answer;
            return responseApdu;
        }

        else if (selectCheckBalanceApdu(apdu)) {
            Log.i("HCEDEMO", "check balance selected");
            byte[] answer = new byte[3];
            answer[0] = (byte) 0x90;
            answer[1] = (byte) 0x00;
            answer[2] = walletBalance;
            responseApdu = answer;
            return responseApdu;
        }

        else {
            Log.i("HCEDEMO", "Unknown command");
            byte[] answer = new byte[2];
            answer[0] = (byte) 0x6F;
            answer[1] = (byte) 0x00;
            responseApdu = answer;
            return responseApdu;
        }
    }

    private boolean selectPersoApdu(byte[] apdu) {
        return apdu.length >= 2 && apdu[0] == (byte) 0x80 && apdu[1] == (byte) 0x01
                && apdu[2] == (byte) 0x00;
    }

    private boolean selectVerifyPinApdu(byte[] apdu) {
        return ( apdu.length >= 4 && apdu[0] == (byte) 0x80 && apdu[1] == (byte) 0x02 && apdu[2] == (byte) 0x00 && apdu[3] == (byte) 0x00 );
    }

    private boolean selectChangePinApdu(byte[] apdu) {
        return ( apdu.length >= 4 && apdu[0] == (byte) 0x80 && apdu[1] == (byte) 0x03 && apdu[2] == (byte) 0x00 && apdu[3] == (byte) 0x00 );
    }

    private boolean selectAddMoneyApdu(byte[] apdu) {
//		(byte) 0x80,  // CLA
//		(byte) 0x01,  // INS
//		(byte) 0x00,  // P1
//		(byte) 0x00,  // P2
        return apdu.length >= 2 && apdu[0] == (byte) 0x80 && apdu[1] == (byte) 0x01
                && apdu[2] == (byte) 0x00 && apdu[3] == (byte) 0x00;
    }

    private boolean selectDebitApdu(byte[] apdu) {
//		(byte) 0x80,  // CLA
//		(byte) 0x02,  // INS
//		(byte) 0x00,  // P1
//		(byte) 0x00,  // P2
        return apdu.length >= 2 && apdu[0] == (byte) 0x80 && apdu[1] == (byte) 0x02
                && apdu[2] == (byte) 0x00 && apdu[3] == (byte) 0x00;
    }

    private boolean selectCheckBalanceApdu(byte[] apdu) {
//		(byte) 0x80,  // CLA
//		(byte) 0x03,  // INS
//		(byte) 0x00,  // P1
//		(byte) 0x00,  // P2
        return apdu.length >= 2 && apdu[0] == (byte) 0x80 && apdu[1] == (byte) 0x03
                && apdu[2] == (byte) 0x00 && apdu[3] == (byte) 0x00;
    }

    @Override
    public void onDeactivated(int reason) {
        Log.i("HCEDEMO", "Deactivated: " + reason);
    }
}

