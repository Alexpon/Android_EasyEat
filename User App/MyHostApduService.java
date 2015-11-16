package mbp.alexpon.com.easyeat;


import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;

public class MyHostApduService extends HostApduService {

    private byte[] userID = new byte[10];
    private byte[] username = new byte[12];
    private byte[] certiIssuedDate = new byte[6];
    private byte[] certiExpiredDate = new byte[6];
    private byte[] certiSerialNum = new byte[12];
    private byte[] publicKey = new byte[64];
    private byte[] privateKey = new byte[64];
    private byte[] digitalSignature = new byte[32];
    private byte[] pinCode = new byte[8];
    private static int money = 0;
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

////////////////////////////////////////////////////// SELECT APDU /////////////////////////////////////////////////

        if (Arrays.equals(AID_SELECT_APDU, apdu)) {
            inboundApduDescription = "Application selected";
            Log.i("HCEDEMO", inboundApduDescription);
            byte[] answer = new byte[2];
            answer[0] = (byte) 0x90;
            answer[1] = (byte) 0x00;
            responseApdu = answer;
            return responseApdu;
        }

//////////////////////////////////////////////////////// PERSO /////////////////////////////////////////////////////

        else if (selectPersoApdu(apdu)){
            byte[] returnVal = new byte[2];
            returnVal[0] = (byte) 0x90;
            returnVal[1] = (byte) 0x00;
            int data_length = (int)apdu[4];
            switch (apdu[3]){
                case 0x00:
                    userID = Arrays.copyOfRange(apdu, 5, 5+data_length);
                    Log.i("HCEDEMO", "userID = " + new String(userID));
                    break;
                case 0x01:
                    username = Arrays.copyOfRange(apdu, 5, 5+data_length);
                    Log.i("HCEDEMO", "username = " + new String(username));
                    break;
                case 0x02:
                    certiIssuedDate = Arrays.copyOfRange(apdu, 5, 5+data_length);
                    Log.i("HCEDEMO", "certiIssuedDate = " + new String(certiIssuedDate));
                    break;
                case 0x03:
                    certiExpiredDate = Arrays.copyOfRange(apdu, 5, 5+data_length);
                    Log.i("HCEDEMO", "certiExpiredDate = " + new String(certiExpiredDate));
                    break;
                case 0x04:
                    certiSerialNum = Arrays.copyOfRange(apdu, 5, 5+data_length);
                    break;
                case 0x05:
                    publicKey = Arrays.copyOfRange(apdu, 5, 5+data_length);
                    break;
                case 0x06:
                    privateKey = Arrays.copyOfRange(apdu, 5, 5+data_length);
                    break;
                case 0x07:
                    digitalSignature = Arrays.copyOfRange(apdu, 5, 5+data_length);
                    break;
                case 0x08:
                    pinCode = Arrays.copyOfRange(apdu, 5, 5+data_length);
                    Log.i("HCEDEMO", "pinCode = " + new String(pinCode));
                    break;
                case 0x09:
                    money = byteArrayToInt(Arrays.copyOfRange(apdu, 5, 5+data_length));
                    Log.i("HCEDEMO", "Money = " + money);
                    break;
                default:
                    returnVal[0] = (byte) 0x01;
                    returnVal[1] = (byte) 0x00;
                    break;
            }
            return returnVal;
        }

//////////////////////////////////////////////////////// PINCODE /////////////////////////////////////////////////////

        else if (selectVerifyPinApdu(apdu)) {
            Log.i("HCEDEMO", "VerifyPin selected");
            int length = apdu[4];
            System.out.println("length = " + length);
            byte[] answer = new byte[2];
            if (length == 8) {
                byte[] pinToVerify = Arrays.copyOfRange(apdu, 5, 5+length);
                if (Arrays.equals(pinToVerify, pinCode)) {
                    answer[0] = (byte) 0x90;
                    answer[1] = (byte) 0x00;
                    pinVerified = true;
                } else {
                    answer[0] = (byte) 0x02;
                    answer[1] = (byte) 0x00;
                    pinVerified = false;
                }
            }
            else{
                answer[0] = (byte) 0x02;
                answer[1] = (byte) 0x01;
            }
            return answer;
        }

        else if (selectChangePinApdu(apdu)) {
            Log.i("HCEDEMO", "ChangePin selected");
            int length = apdu[4];
            System.out.println("length = " + length);
            byte[] answer = new byte[2];

            if (pinVerified) {
                if (length < 8) {
                    answer[0] = (byte) 0x03;
                    answer[1] = (byte) 0x01;
                } else if (length > 8) {
                    answer[0] = (byte) 0x03;
                    answer[1] = (byte) 0x02;
                } else {
                    pinCode = Arrays.copyOfRange(apdu, 5, 13);
                    answer[0] = (byte) 0x90;
                    answer[1] = (byte) 0x00;
                }
            } else {
                answer[0] = (byte) 0x03;
                answer[1] = (byte) 0x00;
            }
            pinVerified = false;
            responseApdu = answer;
            return responseApdu;
        }

////////////////////////////////////////////////// WRITE NORMAL DATA ///////////////////////////////////////////////

        else if (selectWriteNormalDataApdu(apdu)) {
            Log.i("HCEDEMO", "writeData selected");
            byte[] answer = new byte[2];
            answer[0] = (byte) 0x90;
            answer[1] = (byte) 0x00;
            if (pinVerified) {
                int data_length = apdu[4];
                if (apdu[3] == 0x00) {              ///// ADD MONEY
                    byte[] add_money = Arrays.copyOfRange(apdu, 5, 5+data_length);
                    money += byteArrayToInt(add_money);
                    Log.i("HCEDEMO", "Update Money = " + money);
                }
                else if (apdu[3] == 0x01) {         ///// PAY
                    byte[] sub_money = Arrays.copyOfRange(apdu, 5, 5+data_length);
                    if (money < byteArrayToInt(sub_money)) {
                        answer = new byte[2];
                        answer[0] = (byte) 0x04;
                        answer[1] = (byte) 0x02;
                        return answer;
                    }
                    money -= byteArrayToInt(sub_money);
                    Log.i("HCEDEMO", "Update Money = " + money);
                }
                else if (apdu[3] == 0x02) {          ///// EXPIRED DATE
                    certiExpiredDate = Arrays.copyOfRange(apdu, 5, 5+data_length);
                    Log.i("HCEDEMO", "Update ExpiredDate = " + new String(certiExpiredDate));
                }
                else {                              ///// Undefined
                    answer = new byte[2];
                    answer[0] = (byte) 0x04;
                    answer[1] = (byte) 0x00;
                    responseApdu = answer;
                    return responseApdu;
                }
                pinVerified = false;
            }
            else {
                answer[0] = (byte) 0x04;
                answer[1] = (byte) 0x01;
            }

            return answer;
        }

////////////////////////////////////////////////// READ NORMAL DATA ///////////////////////////////////////////////

        else if (selectReadNormalDataApdu(apdu)) {
            Log.i("HCEDEMO", "ReadNormalData selected");
            byte[] answer = null;
            byte[] data;

            switch (apdu[3]) {
                case 0x00:
                    data = userID;
                    break;
                case 0x01:
                    data = username;
                    break;
                case 0x02:
                    data = certiIssuedDate;
                    break;
                case 0x03:
                    data = certiExpiredDate;
                    break;
                case 0x04:
                    data = intToByteArray(money);
                    break;
                default:
                    data = null;
                    break;
            }
            if (data == null) {
                answer[0] = (byte) 0x05;
                answer[1] = (byte) 0x00;
            } else {
                answer = new byte[data.length + 2];
                answer[0] = (byte) 0x90;
                answer[1] = (byte) 0x00;
                System.arraycopy(data, 0, answer, 2, data.length);
            }
            responseApdu = answer;
            return responseApdu;
        }
        else {
            byte[] answer = null;
            answer[0] = (byte) 0x99;
            answer[0] = (byte) 0x99;
            return answer;
        }
    }


    private boolean selectPersoApdu(byte[] apdu) {
        return ( apdu.length >= 4 && apdu[0] == (byte) 0x80 && apdu[1] == (byte) 0x01 && apdu[2] == (byte) 0x00 );
    }

    private boolean selectVerifyPinApdu(byte[] apdu) {
        return ( apdu.length >= 4 && apdu[0] == (byte) 0x80 && apdu[1] == (byte) 0x02 && apdu[2] == (byte) 0x00 && apdu[3] == (byte) 0x00 );
    }

    private boolean selectChangePinApdu(byte[] apdu) {
        return ( apdu.length >= 4 && apdu[0] == (byte) 0x80 && apdu[1] == (byte) 0x03 && apdu[2] == (byte) 0x00 && apdu[3] == (byte) 0x00 );
    }

    private boolean selectWriteNormalDataApdu(byte[] apdu) {
        return ( apdu.length >= 4 && apdu[0] == (byte) 0x80 && apdu[1] == (byte) 0x04 && apdu[2] == (byte) 0x00);
    }

    private boolean selectReadNormalDataApdu(byte[] apdu) {
        return ( apdu.length >= 4 && apdu[0] == (byte) 0x80 && apdu[1] == (byte) 0x05 &&  apdu[2] == (byte) 0x00);
    }

    //////////////////////////////////////////////////////////////////////////////////////////NEW  END//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


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

    @Override
    public void onDeactivated(int reason) {
        Log.i("HCEDEMO", "Deactivated: " + reason);
    }

}