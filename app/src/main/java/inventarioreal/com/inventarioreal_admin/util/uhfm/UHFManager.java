package inventarioreal.com.inventarioreal_admin.util.uhfm;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.pda.serialport.SerialPort;
import cn.pda.serialport.Tools;

public class UHFManager {
	public final String PROTOCOL_ISO_18000_6B = "0003";
	public final String PROTOCOL_ISO_18000_6C = "0005";
	private String Protocol = PROTOCOL_ISO_18000_6C;
	private int Antenne = 1;
	private SerialPort mSerialPort;
	private InputStream inputStream;
	private OutputStream outputStream;
	public static int PORT = 13;
	public static int Baudrate = 115200;
	/***** pang *********************************************************/
	private final byte HEAD = (byte) 0xFF ;  //cmd header
	private final byte CMD_READ_TAG = (byte) 0x22 ;//read tag
	private final byte CMD_READ_SINGLE_TAG_DATA = (byte) 0x28 ;//read tag data by filter data
	private final byte CMD_WRITE_EPC = 0x23 ; //write EPC
	private final byte CMD_LOCK = (byte) 0x25 ;//LOCK TAG
	private final byte CMD_KILL = (byte) 0x26 ;//KILL TAG

	/***lock membank mask****/
	public static final int MASK_EPC = 0x0030 ;
	public static final int MASK_TID = 0x000C ;
	public static final int MASK_USER = 0x0003 ;
	public static final int MASK_ACCESS = 0x00C0 ;
	public static final int MASK_KILL = 0x0300 ;
	/***lock membank action****/
	public static final int ACTION_ALL_OPEN = 0x0000 ;
	public static final int ACTION_USER_FOREVER_OPEN = 0x0001 ;
	public static final int ACTION_USER_LOCK = 0x0002 ;
	public static final int ACTION_USER_FOREVER_LOCK = 0x0003 ;
	public static final int ACTION_TID_FOREVER_OPEN = 0x0004 ;
	public static final int ACTION_TID_LOCK = 0x0008 ;
	public static final int ACTION_TID_FOREVER_LOCK = 0x000C ;
	public static final int ACTION_EPC_FOREVER_OPEN = 0x0010 ;
	public static final int ACTION_EPC_LOCK = 0x0020 ;
	public static final int ACTION_EPC_FOREVER_LOCK = 0x0030 ;
	public static final int ACTION_ACCESS_FOREVER_OPEN = 0x0040 ;
	public static final int ACTION_ACCESS_LOCK = 0x0080 ;
	public static final int ACTION_ACCESS_FOREVER_LOCK = 0x00C0 ;
	public static final int ACTION_KILL_FOREVER_OPEN = 0x0100 ;
	public static final int ACTION_KILL_LOCK = 0x0200 ;
	public static final int ACTION_KILL_FOREVER_LOCK = 0x0300 ;

	/***** pang *********************************************************/



	/***  *****************************************************/
	private String cmdCurrentProgram = "FF000C1D03";
	private String cmdBootFirmware = "FF00041D0B";
	private String cmdSetFreBand = "FF0197064BBB";
	private String cmdGetAllFre = "FF00711D7E";
	private String cmdGetNowFre = "FF00671D68";
	private String cmdSetReadPower = "FF02920BB84AE1";
	private String cmdGetReadPower = "FF016200BEBD";
	private String cmdSetWritePower = "FF02940BB84AE1";
	private String cmdGetWritePower = "FF016400B8BD";
	private String cmdSetAgreement = "FF02930005517D";// NANO只ISO18000-6C
	// private String cmdSetAntenna = "FF009100001758";
	private String cmdClearBuffer = "FF002A1D25";
	private String cmdSearchTag = "FF052200001301F42B19";
	private String cmdGetTagData = "FF032901FF001B03";

	private String cmdOpenFilter = "FF039A010C01A35C";
	private String cmdCloseFilter = "FF039A010C00A35D";
	private String cmdContinuousRead = "FF102F00000122000005072210001B03E801FFDD2B";//6c
	private String cmdContinuousRead_6b = "FF102F00000122000003072210001B03E801FF5DE0";//6b
	private String cmdStopContinuousRead = "FF032F0000025E86";

	private String cmdPramtarSave = "FF039D01010137CB";
	private String cmdRestoreDefaults = "FF039D040101676E";
	private String cmdGetNowFrePoint = "FF00651D6A";
	private String cmdSetFrePoint = "FF0C95000DC370000DF638000E2612C18F";
	private String cmdWriteData = "FF212403E80300000000000000000000000020101234112233443E71";
	private String cmdReadTheTag = "FF0F2204001301F40000000000000020084010B8";

	private String cmdSelectTagContnRead = "FF1B2F00000122000005122214001B03E801FF000000000000002010E20061BD";
	private String cmdSetAnten2 = "FF039102010142C7";

	private String cmdAntennaDetection = "FF016105BDB8";
	private String cmdReadOtherTagArea = "FF112200001701F401092801F4000200000000000D76";// eg:TID
	private String cmdContReadOtherTagArea = "FF1C2F00000122000005132210001F03E801FF01092803E8000200000000001B2C";// eg:TID
	private String cmdGetVersion = "FF00031D0C";
	private String cmdWriteEPC = "FF102303E80000E200303510030108172065CCBB27";
	/** Reserved bank (kill and access passwords) */
	public static int RESERVED = 0;
	/** EPC memory bank */
	public static int EPC = 1;
	/** TID memory bank */
	public static int TID = 2;
	/** User memory bank */
	public static int USER = 3;

	private UHFManager() {
		mSerialPort = new SerialPort();
		try {
			mSerialPort = new SerialPort(PORT, Baudrate, 0);
			mSerialPort.power_5Von();
			mSerialPort.rfid_poweron();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		inputStream = mSerialPort.getInputStream();
		outputStream = mSerialPort.getOutputStream();
		try {
			inputStream.read(new byte[4960]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Test();
	}
	public static UHFManager getInstance() {
		return new UHFManager();
	}
//	public void Restart() {
//		mSerialPort.power_5Voff();
//		mSerialPort.rfid_poweroff();
//		Timer timer = new Timer();
//		timer.schedule(new TimerTask() {
//			@Override
//			public void run() {
//				mSerialPort.power_5Von();
////				mSerialPort.rfid_poweron();
//			}
//		}, 300);
//	}

	public void close() {
		if(mSerialPort!=null){
			mSerialPort.power_5Voff();
			mSerialPort.rfid_poweroff();
			mSerialPort.close(PORT);
			mSerialPort.setGPIOlow(92);
		}
	}

	/**
	 * Init Program
	 */
	public boolean initRfid() {
		String recv = sendcmd(cmdCurrentProgram);
		if (recv != null && recv.length() > 12) {
			String r = recv.substring(10, 12);
			if ((Tools.HexString2Bytes(r)[0] & (byte) 0x01) == 1) {
				sendcmd(cmdBootFirmware);
			}
		}
		if (checkReturn(cmdCurrentProgram, recv)) {
			return true;
		}
		return false;
	}

	/**
	 * get current frequecy band
	 * @return
	 */
	public String getNowFreBand() {
		String rev = sendcmd(cmdGetNowFre);
		if (rev != null && rev.length() > 14) {
			String fres = rev.substring(10, rev.length() - 4);
			byte[] frebs = Tools.HexString2Bytes(fres);
			if (!rev.substring(6, 10).equals("0000")) {
				return null;
			}
			switch (frebs[0]) {
			case (byte) 0x00:
				return "UnspecifiedRegion";

			case (byte) 0x01:
				return "NorthAmerica";

			case (byte) 0x02:
				return "EuropeanUnion";

			case (byte) 0x03:
				return "Korea";

			case (byte) 0x04:
				return "India";

			case (byte) 0x05:
				return "Japan";

			case (byte) 0x06:
				return "China";

			case (byte) 0x07:
				return "EuropeanUnion2";

			case (byte) 0x08:
				return "EuropeanUnion3";

			case (byte) 0x09:
				return "Korea2";

			case (byte) 0x0A:
				return "China(840MHZ)";

			case (byte) 0x0B:
				return "Australia";

			case (byte) 0x0C:
				return "NewZealand";

			case (byte) 0x0D:
				return "FCC";

			case (byte) 0x0E:
				return "5MhzFCC";

			case (byte) 0xFF:
				return "OPEN";
			}
		} else {
		}
		return null;
	}

	public String[] getALLFreBand() {
		String rev = sendcmd(cmdGetAllFre);
		if (rev != null && rev.length() > 14) {
			String fres = rev.substring(10, rev.length() - 4);
			byte[] frebs = Tools.HexString2Bytes(fres);
			String[] allfre = new String[frebs.length];
			for (int i = 0; i < frebs.length; i++) {
				switch (frebs[i]) {
				case (byte) 0x00:
					allfre[i] = "Unspecifiedregion";
					break;
				case (byte) 0x01:
					allfre[i] = "NorthAmerica";
					break;
				case (byte) 0x02:
					allfre[i] = "EuropeanUnion";
					break;
				case (byte) 0x03:
					allfre[i] = "Korea";
					break;
				case (byte) 0x04:
					allfre[i] = "India";
					break;
				case (byte) 0x05:
					allfre[i] = "Japan";
					break;
				case (byte) 0x06:
					allfre[i] = "China";
					break;
				case (byte) 0x07:
					allfre[i] = "EuropeanUnion2";
					break;
				case (byte) 0x08:
					allfre[i] = "EuropeanUnion3";
					break;
				case (byte) 0x09:
					allfre[i] = "Korea2";
					break;
				case (byte) 0x0A:
					allfre[i] = "China(840MHZ)";
					break;
				case (byte) 0x0B:
					allfre[i] = "Australia";
					break;
				case (byte) 0x0C:
					allfre[i] = "NewZealand";
					break;
				case (byte) 0x0D:
					allfre[i] = "FCC";
					break;
				case (byte) 0x0E:
					allfre[i] = "5MhzFCC";
					break;
				case (byte) 0xFF:
					allfre[i] = "OPEN";
					break;
				}
			}
			FreRegion.ALL_REGIONs = fres;
			return allfre;
		} else {
			return null;
		}
	}
	private boolean setAntenne(int i){
		Antenne = i;
		if (i==2){
		cmdSetAnten2 = cmdSetAnten2.substring(0, 8)+"0202";}
		else {
			cmdSetAnten2 = cmdSetAnten2.substring(0, 12);
		}
		byte[] crc = getCRC(Tools.HexString2Bytes(cmdSetAnten2), 1, 5);
		cmdSetAnten2 = cmdSetAnten2 + Tools.Bytes2HexString(crc, crc.length);
		String rev = sendcmd(cmdSetAnten2);
		if (checkReturn(cmdSetAnten2, rev)) {
			return true;
		} else {
			return false;
		}
	}
	private boolean setAntenneWrite(int i){
		String cmdSetAnten = "FF029101014009";
		if (i==2){
			cmdSetAnten = cmdSetAnten.substring(0, 6)+"0202";}
		else {
			cmdSetAnten = cmdSetAnten.substring(0, 10);
		}
		byte[] crc = getCRC(Tools.HexString2Bytes(cmdSetAnten), 1, 4);
		cmdSetAnten = cmdSetAnten + Tools.Bytes2HexString(crc, crc.length);
		String rev = sendcmd(cmdSetAnten);
		if (checkReturn(cmdSetAnten, rev)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * set region frequency
	 * @param region
	 * @return
	 */
	public boolean setFreBand(String region) {
		cmdSetFreBand = cmdSetFreBand.substring(0, 6) + region;
		byte[] crc = getCRC(Tools.HexString2Bytes(cmdSetFreBand), 1, 3);
		cmdSetFreBand = cmdSetFreBand + Tools.Bytes2HexString(crc, crc.length);
		String rev = sendcmd(cmdSetFreBand);
		if (checkReturn(cmdSetFreBand, rev)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get Version Info
	 * 
	 * @return status
	 */
	public VersionInfo getVersion() {
		String rev = sendcmd(cmdGetVersion);
		VersionInfo versionInfo = new VersionInfo();
		if (rev != null && !rev.equals("") && rev.length() > 50) {
			versionInfo.Bootloader = rev.substring(10, 18);
			versionInfo.HardwareVersion = rev.substring(18, 26);
			versionInfo.SoftwareDATE = rev.substring(26, 34);
			versionInfo.SoftwareVersion = rev.substring(34, 42);
			versionInfo.Agreements = rev.substring(42, 50);
			return versionInfo;
		}
		return null;
	}

	/**
	 * Get module read power at present
	 * 
	 * @return power
	 */
	public int getReadPower() {
		String rev = sendcmd(cmdGetReadPower);
		if (rev != null && rev.length() > 10) {
			if (checkReturn(cmdGetReadPower, rev)) {
				return Integer.valueOf(rev.substring(12, 16), 16) / 100;
			}
		}
		return (Integer) 0;
	}

	/**
	 * set read power
	 * 
	 * @param power
	 *            value
	 * @return
	 */
	public boolean setReadPower(int power) {
		setAntenne(Antenne);
//			setAntenne(2);
		if (power >= -5 && power <= 30) {
			String powers = Integer.toHexString(power * 100);

			switch (powers.length()) {
			case 0:
				powers = "0000";
				break;
			case 1:
				powers = "000" + powers;
				break;
			case 2:
				powers = "00" + powers;
				break;
			case 3:
				powers = "0" + powers;
				break;
			case 8:
				powers = powers.substring(4, 8);
				break;
			default:
				break;
			}

			String forcrc = cmdSetReadPower.substring(0, 6) + powers;
			byte[] crc = getCRC(Tools.HexString2Bytes(forcrc), 1, 4);
			String setpowercmd = forcrc
					+ Tools.Bytes2HexString(crc, crc.length);
			String rev = sendcmd(setpowercmd);
			if (rev != null && rev.length() > 10) {
				String status = rev.substring(6, 10);
				if (status.equals("0000")) {
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	/**
	 * Get module read power at present
	 *
	 * @return power
	 */
	public int getWritePower() {
		String rev = sendcmd(cmdGetWritePower);
		if (rev != null && rev.length() > 10) {
			String status = rev.substring(6, 10);
			if (status.equals("0000")) {
				return Integer.valueOf(rev.substring(12, 16), 16) / 100;
			}
		}
		return (Integer) null;
	}

	/**
	 * set write power
	 *
	 * @param power
	 *            value
	 * @return
	 */
	public boolean setWritePower(int power) {
//		SetProtocol(Protocol);
		setAntenneWrite(Antenne);
		if (power >= -5 && power <= 30) {
			String powers = Integer.toHexString(power * 100);
			switch (powers.length()) {
			case 0:
				powers = "0000";
				break;
			case 1:
				powers = "000" + powers;
				break;
			case 2:
				powers = "00" + powers;
				break;
			case 3:
				powers = "0" + powers;
				break;
			default:
				break;
			}
			String forcrc = cmdSetWritePower.substring(0, 6) + powers;
			byte[] crc = getCRC(Tools.HexString2Bytes(forcrc), 1, 4);
			String setpowercmd = forcrc
					+ Tools.Bytes2HexString(crc, crc.length);
			String rev = sendcmd(setpowercmd);
			if (rev != null && rev.length() > 10) {
				String status = rev.substring(6, 10);
				if (status.equals("0000")) {
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	/**
	 * Set The protocol
	 */
	public boolean setProtocol(String protocol) {

		if (protocol.equals(PROTOCOL_ISO_18000_6B)){
			cmdContinuousRead = cmdContinuousRead_6b;
//			String rev = sendcmd("FF02930005517D");
//			if (checkReturn("FF02930005517D",rev)){
//				return true;
//			}else {
//				return false;
//			}
			return true;
		}else {
			String rev = sendcmd("FF02930005517D");
			if (checkReturn("FF02930005517D",rev)){
				return true;
			}else {
				return false;
			}
		}

	}

	/**
	 * Clear the Buffer
	 *
	 * @return
	 */
	public boolean clearBuffer() {
		String rev = sendcmd(cmdClearBuffer);
		if (rev != null && rev.equals("FF002A000001E8")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * SearchTag Tag
	 * @return Tag Count
	 */
	private int searchTag(boolean isFilter, int timeout) {
//		SetFilter(isFilter);
		clearBuffer();
		String time = Integer.toHexString(timeout);
		switch (time.length()) {
		case 1:
			time = "000" + time;
			break;
		case 2:
			time = "00" + time;
			break;
		case 3:
			time = "0" + time;
			break;

		default:
			time = "0000";
			break;
		}
		cmdSearchTag = cmdSearchTag.substring(0, 12) + time;
		byte[] crc = getCRC(Tools.HexString2Bytes(cmdSearchTag), 1, 7);
		String rev = sendcmd(
				cmdSearchTag + Tools.Bytes2HexString(crc, crc.length), timeout);
		setFilter(false);
		if (rev != null && rev.length() >= 24) {
			String countst = rev.substring(
					16, 24);
			byte[] countbs = Tools.HexString2Bytes(countst);
			return 256*countbs[2] +countbs[3];
		}
		return -1;
	}

	/***
	 * interval get EPC
	 * @param time delayed time
	 * @return
	 */
	public List<EPCDataModel> getEpcData(int time) {
		List<EPCDataModel> list = new ArrayList<EPCDataModel>();
		int count = searchTag(false,time);
		if (count>0) {
			list =  GetEpcData1(list,count);
			return  list;
		}else {
			return  null;
		}
	}
	private List<EPCDataModel> GetEpcData1(List<EPCDataModel> lists, int count) {
		String rev = sendcmd(cmdGetTagData);
		if (checkReturn(cmdGetTagData, rev)/*&&checkCRC(Tools.HexString2Bytes(rev))*/) {
			int thiscount = Integer.parseInt(rev.substring(16, 18), 8);
			String datas = rev.substring(18, rev.length() - 4);
			for (int i = 0; i < thiscount; i++) {
				int dataLength = Integer.parseInt(datas.substring(26, 30), 16) / 8;
				int epcLength = (Integer.parseInt(datas.substring(
						32 + dataLength * 2, 36 + dataLength * 2), 16)) / 8 - 4;
				if (dataLength >= 0 && epcLength > 0) {
					EPCDataModel model = new EPCDataModel();
					String rssiString = datas.substring(2, 4);
					String data = datas.substring(30, 30 + 2 * dataLength);
					String epc = datas.substring(40 + 2 * dataLength, 40 + 2
							* dataLength + 2 * epcLength);
					// //Log.e("data:epc", data + ":" + epc + ":" + rssiString);
					model.RSSI = Tools.HexString2Bytes(rssiString)[0];
					model.EPC = Tools.HexString2Bytes(epc);
					lists.add(model);
					datas = datas
							.substring(44 + dataLength * 2 + epcLength * 2,
									datas.length());
				}
			}
			int remainder = count - thiscount;
			if (remainder > 0) {
				GetEpcData1(lists, remainder);
			}
		}
		return lists;
		// return true;
	}

	/***
	 * read tag data from buffer
	 * @param lists
	 * @param count
	 * @return
	 */
	public List<TagDataModel> getTAGData(List<TagDataModel> lists, int count) {
		// cmdGetTagData = cmdGetTagData.substring(0, 6)+"000000";
		// byte[] crc = getCRC(Tools.HexString2Bytes(cmdGetTagData), 1, 5);
		String rev = sendcmd(cmdGetTagData);
		if (checkReturn(cmdGetTagData, rev)) {
			Log.e("get tag data ", rev) ;
			int thiscount = Integer.parseInt(rev.substring(16, 18), 8);
			String datas = rev.substring(18, rev.length() - 4);

			for (int i = 0; i < thiscount; i++) {
				int dataLength = Integer.parseInt(datas.substring(26, 30), 16) / 8;
				int epcLength = (Integer.parseInt(datas.substring(
						32 + dataLength * 2, 36 + dataLength * 2), 16)) / 8 - 4;
				if (dataLength >= 0 && epcLength > 0) {
					TagDataModel model = new TagDataModel();
					String rssiString = datas.substring(2, 4);
					String data = datas.substring(30, 30 + 2 * dataLength);
					String epc = datas.substring(40 + 2 * dataLength, 40 + 2
							* dataLength + 2 * epcLength);
					// //Log.e("data:epc", data + ":" + epc + ":" + rssiString);
					model.RSSI = Tools.HexString2Bytes(rssiString)[0];
					model.Epc = epc;
					model.DATA = data;
					lists.add(model);
					datas = datas
							.substring(44 + dataLength * 2 + epcLength * 2,
									datas.length());
				}
			}
			int remainder = count - thiscount;
			if (remainder > 0) {
				getTAGData(lists, remainder);
			}
		}
		return lists;
		// return true;
	}

	/**
	 * open filter is true,close filter is
	 * @return
	 */
	public boolean setFilter(boolean isFilter) {
		String rev;
		if (isFilter) {
			rev = sendcmd(cmdOpenFilter);
		} else {
			rev = sendcmd(cmdCloseFilter);
		}
		if (rev != null && rev.length() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * SAVE PRAMTAR
	 *
	 * @return
	 */
	public boolean savePramtar() {
		String rev = sendcmd(cmdPramtarSave);
		if (rev != null && rev.equals("FF029D00000101D811")) {
			return true;
		}
		return false;
	}

	/**
	 *
	 * @return
	 */
	private boolean restoreDefaults() {
		String rev = sendcmd(cmdRestoreDefaults);
		if (rev != null && rev.equals("FF029D00000401DD11")) {
			return true;
		}
		return false;
	}

	/**
	 * get current frequency points
	 * @return
	 */
	public int[] getNowFrePoints() {

		String rev = sendcmd(cmdGetNowFrePoint);
		if (rev != null && rev.length() >= 14) {
			String info = rev.substring(6, 10);
			if (info.equals("0000")) {
				int pointCount = (rev.length() - 14) / 8;
				int[] points = new int[pointCount];
				for (int i = 0; i < points.length; i++) {
					points[i] = /* Tools.bytesToInt(Tools.HexString2Bytes( */HexToInt(rev
							.substring(10 + i * 8, 10 + (i + 1) * 8))/* )) */;
				}
				return points;
			}
		}
		return null;
	}

	/**
	 * set frequency points
	 * @param frePoints
	 * @return
	 */
	public boolean setFrePoints(int[] frePoints) {
		String FreString = "";
		for (int i = 0; i < frePoints.length; i++) {
			String string = IntToHex(frePoints[i]);
			string = "000" + string.substring(0, string.length());
			FreString = FreString + string;
			// //Log.e("points:", "" + FreString);
		}
		return SetFrePoint(FreString);
	}

	private boolean SetFrePoint(String FrePoint) {
		int lengt = (FrePoint.length() / 2);
		byte[] lenbs = new byte[1];
		lenbs[0] = (byte) lengt;
		String forcrcString = "FF" + Tools.Bytes2HexString(lenbs, lenbs.length)
				+ "95" + FrePoint;
		byte[] crc = getCRC(Tools.HexString2Bytes(forcrcString), 1,
				(int) lengt + 2);
		String cmdString = forcrcString
				+ Tools.Bytes2HexString(crc, crc.length);
		String rev = sendcmd(cmdString);
		if (rev != null && rev.length() >= 10) {
			String info = rev.substring(6, 10);
			if (info.equals("0000")) {
				return true;
			}
			if (info.equals("0104")) {
			}
		}
		return false;
	}

////////////////////////////pang//////////////////////////////////////////////////////
	/**
	 * read membank data, select TAG by EPC
	 * @param epcBytes
	 * @param password
	 * @param membank
	 * @param startAddr
	 * @param readLen
     * @return
     */
	public byte[] readDataByEPC(byte[] epcBytes, byte[] password, int membank ,int startAddr, int readLen) {
		byte[] data = null ;
		int filterEPC = 4 ;//filter by epc
		int filterAddr = 32 ;
		data = readSingleTagData(filterEPC,filterAddr, epcBytes,password, membank, startAddr, readLen);
		return data ;
	}
	/**
	 * read data by filter data
	 * @param filterBank filter membank : 0-none select password , 1-All epc data , 2- TID , 3-USER, 4-EPC
	 * @param filterAddr filter membank address
	 * @param filterData filter data
	 * @param password  access password
	 * @param membank read membank
	 * @param startAddr start address
     * @param readLen read length (word)
     */
	private byte[] readSingleTagData(int filterBank, int filterAddr,  byte[] filterData, byte[] password, int membank , int startAddr, int readLen) {
		byte[] data = null ;
		byte[] cmd = new byte[23 + filterData.length] ;
		cmd[0] = HEAD ;//0xff
		cmd[1] = (byte) (18 + filterData.length) ;
		cmd[2] = CMD_READ_SINGLE_TAG_DATA ;
		cmd[3] = 0x03 ;
		cmd[4] = (byte) 0xE8 ;//0x03E8 timeout
		cmd[5] = (byte) filterBank ; //filterBank
		cmd[6] = (byte) membank ; //read membank
		cmd[7] = 0x00 ;
		cmd[8] = 0x00 ;
		cmd[9] = (byte) (startAddr/256);
		cmd[10] = (byte) (startAddr%256) ;//cmd[7~9] start address
		cmd[11] = (byte) readLen ;//readLen
		System.arraycopy(password, 0, cmd, 12, password.length);//password
		cmd[16] = 0x00 ;
		cmd[17] = 0x00 ;
		cmd[18] = (byte) (filterAddr/256) ;
		cmd[19] = (byte) (filterAddr%256) ; //filter address
		cmd[20] = (byte) (8*filterData.length) ; //filter data length (bit)
		System.arraycopy(filterData, 0, cmd, 21, filterData.length);//filter data
		byte[] crc = getCRC(cmd, 1,cmd.length - 3);//check crc 2 bytes
		cmd[21+filterData.length] = crc[0] ;
		cmd[22+filterData.length] = crc[1]  ;
		Log.e("cmd readSingleTagData", Tools.Bytes2HexString(cmd, cmd.length)) ;
		String recv = sendcmd(Tools.Bytes2HexString(cmd, cmd.length)) ;
		if (recv != null)
			Log.e("cmd select recv", recv) ;
		if (checkReturn(Tools.Bytes2HexString(cmd, cmd.length), recv)) {
			byte[] recvBytes = Tools.HexString2Bytes(recv) ;
			int len = recvBytes[1]&0xff ;
			if (len > 0){
				data = new byte[len - 1] ;
				System.arraycopy(recvBytes, 6, data, 0, len - 1);
			}
		}

		return data ;
	}

	/**
	 * lock tag by EPC data
	 * @param password access password
	 * @param epc EPC bytes
	 * @param membankMask  membankMask
	 * @param lockAction  lock action , open/forever open/lock/forever lock
     * @return
     */
	public boolean lockMembank(byte[] password, byte[] epc, int membankMask, int lockAction) {
		int epcLen = epc.length ;
		byte[] cmd = new byte[17 + epcLen] ;
		cmd[0] = HEAD ;
		cmd[1] = (byte) (12 + epcLen) ;
		cmd[2] = CMD_LOCK ;
		cmd[3] = 0x03 ;
		cmd[4] = (byte) 0xE8 ;//time out
		cmd[5] = 0x01 ;//select epc all
		System.arraycopy(password, 0, cmd, 6, password.length);
		cmd[10] = (byte)(membankMask/256) ;
		cmd[11] = (byte)(membankMask%256) ;//membank mask
		cmd[12] = (byte)(lockAction/256) ;
		cmd[13] = (byte)(lockAction%256) ;//lockAction
		cmd[14] = (byte) (8*epcLen) ;
		System.arraycopy(epc, 0 , cmd, 15, epc.length);
		byte[] crc = getCRC(cmd, 1,cmd.length - 3);//check crc 2 bytes
		cmd[15 + epcLen] = crc[0] ;
		cmd[16 + epcLen] = crc[1]  ;
		Log.e("cmd lockMembank", Tools.Bytes2HexString(cmd, cmd.length)) ;
		String recv = sendcmd(Tools.Bytes2HexString(cmd, cmd.length)) ;
		if (recv != null){
			Log.e("cmd lockMembank recv", recv) ;
			if (checkReturn(Tools.Bytes2HexString(cmd, cmd.length), recv)) {
				byte[] recvBytes = Tools.HexString2Bytes(recv) ;
				if(recvBytes.length > 6){//FF00250000F007  lock
					if(recvBytes[3] == 0 && recvBytes[4] == 0 ){
						return true ;
					}
				}
			}
		}

		return false  ;
	}


	/**
	 * kill tag
	 * @param killPwd  kill password
	 * @param epcBytes EPC bytes
     * @return
     */
	public boolean killTag(byte[] killPwd, byte[] epcBytes){
		//test cmd : FF 13 26 03 E8 01 11 11 22 22 05 11 22 33 44 55 66 77 88 99 AA DD D8
		int epcLen = epcBytes.length ;
		byte[] cmd = new byte[14 + epcLen] ;
		cmd[0] = HEAD ;
		cmd[1] = (byte)(9 + epcLen) ;
		cmd[2] = CMD_KILL ;
		cmd[3] = 0x03 ;
		cmd[4] = (byte) 0xE8 ;//time out
		cmd[5] = 0x01 ;//select epc all
		System.arraycopy(killPwd, 0, cmd, 6, killPwd.length);
		cmd[10] = 0x00 ;//
		cmd[11] = (byte) (8*epcLen) ;
		System.arraycopy(epcBytes, 0 , cmd, 12, epcLen);
		byte[] crc = getCRC(cmd, 1,cmd.length - 3);//check crc 2 bytes
		cmd[12 + epcLen] = crc[0] ;
		cmd[13 + epcLen] = crc[1]  ;
		Log.e("cmd killTag", Tools.Bytes2HexString(cmd, cmd.length)) ;
		String recv = sendcmd(Tools.Bytes2HexString(cmd, cmd.length)) ;
		if (recv != null){
			Log.e("cmd killTag recv", recv) ;
			if (checkReturn(Tools.Bytes2HexString(cmd, cmd.length), recv)) {
				byte[] recvBytes = Tools.HexString2Bytes(recv) ;
				if(recvBytes.length > 6){//FF00250000F007  lock
					if(recvBytes[3] == 0 && recvBytes[4] == 0 ){
						return true ;
					}
				}
			}
		}
		return false ;
	}
	
	/**
	 * repair new EPC by TID
	 * @param password
	 * @param tid
	 * @param newEPC
	 * @return
	 */
	public boolean writeEpcByTID(byte[] password, byte[] tid, byte[] newEPC){
		//test cmd:FF132303E802000000000000000028E2801130205555B512

		int epcLen = newEPC.length ;
		int tidLen = tid.length ;
		byte[] cmd = new byte[17 + epcLen + tidLen] ;
		cmd[0] = HEAD ;
		cmd[1] = (byte) (12 + epcLen + tidLen) ;
		cmd[2] = CMD_WRITE_EPC ;
		cmd[3] = 0x03 ;
		cmd[4] = (byte) 0xE8 ;//time out
		cmd[5] = 0x02 ;//select tid
		System.arraycopy(password, 0, cmd, 6, password.length);
		cmd[10] = 0 ;
		cmd[11] = 0 ;
		cmd[12] = 0 ;
		cmd[13] = 0 ;//start address
		cmd[14] = (byte) (tidLen*8) ;//tidlen
		System.arraycopy(tid, 0, cmd, 15, tidLen) ;
		System.arraycopy(newEPC, 0, cmd, 15 + tidLen, epcLen) ;
		byte[] crc = getCRC(cmd, 1,cmd.length - 3);//check crc 2 bytes
		cmd[15 + epcLen + tidLen] = crc[0] ;
		cmd[16 + epcLen + tidLen] = crc[1]  ;
		Log.e("cmd writeEpcByTID", Tools.Bytes2HexString(cmd, cmd.length)) ;
		String recv = sendcmd(Tools.Bytes2HexString(cmd, cmd.length)) ;
		if (recv != null){
			Log.e("cmd writeEpcByTID recv", recv) ;
			if (checkReturn(Tools.Bytes2HexString(cmd, cmd.length), recv)) {
				byte[] recvBytes = Tools.HexString2Bytes(recv) ;
				if(recvBytes.length > 6){//FF 00 23 00 00 90 C1
					if(recvBytes[3] == 0 && recvBytes[4] == 0 ){
						return true ;
					}
				}
			}
		}
		return false ;
	}
	
	public boolean writeDataByTID(int membank, byte[] tid, byte[] data,
			int timeout_ms, int start_addr, byte[] password) {
		//test cmd
		return false ;
		
	}
	
///////////////////////////////////////////////////////////////////////////////
	public List<TagDataModel> ReadData(int membank, int startaddr, int datalength,
                                       byte[] password) {
		clearBuffer();
		List<TagDataModel> list = new ArrayList<TagDataModel>();
		// byte[] data = new byte[datalength];
		String t = IntToHex(100);
		switch (t.length()) {
		case 1:
			t = "000" + t;
			break;
		case 2:
			t = "00" + t;
			break;
		case 3:
			t = "0" + t;
			break;
		}
		String startaddrs = IntToHex(startaddr);
		switch (startaddrs.length()) {
		case 1:
			startaddrs = "0000000" + startaddrs;
			break;
		case 2:
			startaddrs = "000000" + startaddrs;
			break;
		case 3:
			startaddrs = "00000" + startaddrs;
			break;
		case 4:
			startaddrs = "0000" + startaddrs;
			break;
		case 5:
			startaddrs = "000" + startaddrs;
			break;
		case 6:
			startaddrs = "00" + startaddrs;
			break;
		case 7:
			startaddrs = "0" + startaddrs;
			break;
		}
		String ls = IntToHex(datalength);
		switch (ls.length()) {
		case 1:
			ls = "0" + ls;
			break;
		}
		String forcrc = "FF" + "11" + "22" + "00" + "0017" + t + "010928" + t
				+ "000" + membank + startaddrs + ls;

		byte[] crc = getCRC(Tools.HexString2Bytes(forcrc), 1,
				forcrc.length() / 2 - 1);
		String cmd = forcrc + Tools.Bytes2HexString(crc, crc.length);
		String rev = sendcmd(cmd);
		if (checkReturn(cmd, rev)) {
			Log.e("read data", rev) ;
			String counts = rev.substring(16, 24);
			int count = Tools.bytesToInt(Tools.HexString2Bytes(counts));
			getTAGData(list, count);
		}
		if (list.size() > 0) {
			return  list;
		} else {
			return null;
		}
	}



	/**
	 * repair EPC
	 * @param newepc
	 * @param timeout
	 * @return
	 */
	private boolean WriteEpc(byte[] newepc, int timeout) {
//		SetProtocol(Protocol_ISO_18000_6C);
//		SetWritePower(15);
		String t = IntToHex(timeout);
		switch (t.length()) {
		case 1:
			t = "000" + t;
			break;
		case 2:
			t = "00" + t;
			break;
		case 3:
			t = "0" + t;
			break;
		}
		String ls = IntToHex(newepc.length + 4);
		String newepcString = Tools.Bytes2HexString(newepc, newepc.length);
		switch (ls.length()) {
		case 1:
			ls = "0" + ls;
			break;
		}
		String forcrc = "FF" + ls + "23" + t + "0000" + newepcString;
		byte[] crc = getCRC(Tools.HexString2Bytes(forcrc), 1,
				forcrc.length() / 2 - 1);
		String cmd = forcrc + Tools.Bytes2HexString(crc, crc.length);
		String rev = sendcmd(cmd);
		if (checkReturn(cmd, rev)) {
			return true;
		}

		return false;
	}

	/**
	 * write data to tag by EPC, for single tag
	 * @param membank  the membank of need to write data
	 * @param epc  the select tag EPC
	 * @param data  need to write data
	 * @param timeout_ms  timeout
	 * @param start_addr  start address
	 * @param password access password
     * @return
     */
	public boolean writeDataByEpc(int membank, byte[] epc, byte[] data,
			int timeout_ms, int start_addr, byte[] password) {
		String t = IntToHex(timeout_ms);
		switch (t.length()) {
		case 1:
			t = "000" + t;
			break;
		case 2:
			t = "00" + t;
			break;
		case 3:
			t = "0" + t;
			break;
		}
		String startaddrs = IntToHex(start_addr);
		switch (startaddrs.length()) {
		case 1:
			startaddrs = "0000000" + startaddrs;
			break;
		case 2:
			startaddrs = "000000" + startaddrs;
			break;
		case 3:
			startaddrs = "00000" + startaddrs;
			break;
		case 4:
			startaddrs = "0000" + startaddrs;
			break;
		case 5:
			startaddrs = "000" + startaddrs;
			break;
		case 6:
			startaddrs = "00" + startaddrs;
			break;
		case 7:
			startaddrs = "0" + startaddrs;
			break;
		}

		String epcls = IntToHex(epc.length * 8);
		switch (epcls.length()) {
		case 1:
			epcls = "0" + epcls;
			break;
		}
		String ls = IntToHex(epc.length + data.length + 13);
		switch (ls.length()) {
		case 1:
			ls = "0" + ls;
			break;
		}
		// FF 13 24 0064 01 00000020 01 00000000 60 AABBCCDDABCD26FA

		String forcrc = "FF" + ls + "24" + t + "01" + startaddrs + "0"
				+ membank + Tools.Bytes2HexString(password, password.length)
				+ epcls + Tools.Bytes2HexString(epc, epc.length)
				+ Tools.Bytes2HexString(data, data.length);
		byte[] crc = getCRC(Tools.HexString2Bytes(forcrc), 1,
				forcrc.length() / 2 - 1);
		String cmd = forcrc + Tools.Bytes2HexString(crc, crc.length);
		String rev = sendcmd(cmd);
		if (checkReturn(cmd, rev)) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * start to inventory tag EPC
	 * @param isFilter
	 * @return
     */
	public boolean startInventory(boolean isFilter) {

		sendcmd("FF01980044BD");
		sendcmd(cmdCloseFilter);//
		if (isFilter) {
			// sendcmd(cmdOpenFilter);//
			sendcmd("FF039B050001DCE9");
		} else {

			sendcmd("FF039B050000DCE8");
		}
		// sendcmd(cmd_start2);
		String rev = sendcmd(cmdContinuousRead);
		if (checkReturn(cmdContinuousRead, rev)) {
			return true;
		}
		return false;
	}

	public boolean stopInventory() {
		if (outputStream!=null) {
			sendstopcmd(cmdStopContinuousRead);
			//20170406 luo//
//			sendcmd("FF039B050000DCE8");
			return true;
		}else  {
				return  false;
		}

	}


	/**
	 * get EPC data from buffer
	 * @return
     */
	public byte[] getEPCByteBuff() {
		try {
			int count = inputStream.available();
			if (count > 0) {
				byte[] head1 = new byte[1];
				inputStream.read(head1);
				if (head1[0] == (byte) 0xff) {
					inputStream.read(head1);
					int dataLen = head1[0]; //
					if (dataLen < 0) {
						dataLen = 256 + dataLen;
					}
//					Thread.sleep((int) dataLen / 70);
					byte[] bytes = new byte[dataLen + 5];
					inputStream.read(bytes);
					final byte[] bytess = new byte[dataLen + 7];
					System.arraycopy(bytes, 0, bytess, 2, dataLen + 5);
					bytess[0] = (byte) 0xff;
					bytess[1] = head1[0];
					return bytess;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//

		return null;
	}

	public boolean StopFlag = true;


	public EPCDataModel getEPC(byte[] bs) {
		if (bs != null && bs.length >= 6) {
//			String stopflag = Tools.Bytes2HexString(bs, bs.length).substring(2,
//					6);
			if (bs[1] == 0x01&&bs[2]==0x2F/*stopflag.equals("012F")*/) {
				StopFlag = true;
			}
		}
		if (bs != null && bs.length > 12 && checkCRC(bs)) {
			if (bs[1] > 28) {
				byte[] msgdatalenth = new byte[2];
				System.arraycopy(bs, 24, msgdatalenth, 0, 2);
				int datalent = Integer.parseInt(Tools.Bytes2HexString(
						msgdatalenth, msgdatalenth.length), 16);
				byte[] epclen = new byte[2];
				byte[] rssibs = new byte[1];
				System.arraycopy(bs, 12, rssibs, 0, 1);
				int epclent = 12;
				if (bs.length < (19 + datalent)) {
					return null;
				}
				if (bs.length >= 27 + datalent + 2) {
					System.arraycopy(bs, 27 + datalent, epclen, 0, 2);
					byte[] epclength = new byte[4];
					epclength[1] = epclen[0];
					epclength[0] = epclen[1];
					epclent = Tools.bytesToInt(epclength) / 8 - 4;
					if (epclent > 0 ) {
						// //Log.e("epclength",
						// Tools.Bytes2HexString(epclen, epclen.length) + ":"
						// + epclent);
						if (bs.length >= 31 + datalent + epclent) {
							byte[] epc = new byte[epclent];
							System.arraycopy(bs, 31 + datalent, epc, 0, epclent);
							EPCDataModel epcm = new EPCDataModel();
							epcm.EPC = epc;
							epcm.RSSI = rssibs[0];
							return epcm;
						} else {
							Log.e("getEpc","bytes length error");
							return null;
						}
					} else {
						return null;
					}
				} else {
					return null;
				}

			} else {
				return null;
			}
		} else {
			return null;
		}

	}

	public boolean checkAntenna() {
		String rev = sendcmd(cmdAntennaDetection);
		if (checkReturn(cmdAntennaDetection, rev)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * repair access password by EPC
	 * @param epcbytes
	 * @param oldPassword
	 * @param newPassword
	 * @param timeout_ms
	 * @return
	 */
	public boolean writeAccessPassword(byte[] epcbytes,byte[] oldPassword,byte[] newPassword,int timeout_ms){
		if (writeDataByEpc(RESERVED,epcbytes,newPassword,timeout_ms,2,oldPassword))
				return  true;
		else return false;
	}

	/**
	 * repair kill password by EPC
	 * @param epcbytes
	 * @param oldPassword
	 * @param newPassword
	 * @param timeout_ms
	 * @return
	 */
	public boolean writeKillPassword(byte[] epcbytes,byte[] oldPassword,byte[] newPassword,int timeout_ms){
		if (writeDataByEpc(RESERVED,epcbytes,newPassword,timeout_ms,0,oldPassword))
			return  true;
		else return false;
	}

	private boolean LockTagByEpc(byte[] epc, String lockType, byte[] accesspassword, int timeout_ms){
		if (accesspassword.length!=4) return false;
		String t = IntToHex(timeout_ms);
		switch (t.length()) {
			case 1:
				t = "000" + t;
				break;
			case 2:
				t = "00" + t;
				break;
			case 3:
				t = "0" + t;
				break;
		}
		String epcls = IntToHex(epc.length * 8);
		switch (epcls.length()) {
			case 1:
				epcls = "0" + epcls;
				break;
		}
		String ls = IntToHex(epc.length + 12);
		switch (ls.length()) {
			case 1:
				ls = "0" + ls;
				break;
		}

		String forcrc = "FF" + ls + "25" + t + "01"
				+ Tools.Bytes2HexString(accesspassword,accesspassword.length)
				+ lockType+lockType
				+ epcls + Tools.Bytes2HexString(epc, epc.length);

		byte[] crc = getCRC(Tools.HexString2Bytes(forcrc), 1,
				forcrc.length() / 2 - 1);
		String cmd = forcrc + Tools.Bytes2HexString(crc, crc.length);
		String rev = sendcmd(cmd);
		if (checkReturn(cmd, rev)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean KillTagByEpc(byte[] epc, byte[] killpassword,int timeout_ms){
		if (killpassword.length!=4) return false;
		String t = IntToHex(timeout_ms);
		switch (t.length()) {
			case 1:
				t = "000" + t;
				break;
			case 2:
				t = "00" + t;
				break;
			case 3:
				t = "0" + t;
				break;
		}
		String epcls = IntToHex(epc.length * 8);
		switch (epcls.length()) {
			case 1:
				epcls = "0" + epcls;
				break;
		}
		String ls = IntToHex(epc.length + 12);
		switch (ls.length()) {
			case 1:
				ls = "0" + ls;
				break;
		}
		String forcrc = "FF" + ls + "26" + t + "01"
				+ Tools.Bytes2HexString(killpassword,killpassword.length)
				+"00"
				+ epcls + Tools.Bytes2HexString(epc, epc.length);

		byte[] crc = getCRC(Tools.HexString2Bytes(forcrc), 1,
				forcrc.length() / 2 - 1);
		String cmd = forcrc + Tools.Bytes2HexString(crc, crc.length);
		String rev = sendcmd(cmd);
		if (checkReturn(cmd, rev)) {
			return true;
		} else {
			return false;
		}
	}
	private boolean LockTagByTID(byte[] tid, String lockType, byte[] accesspassword, int timeout_ms){
		if (accesspassword.length!=4) return false;
		String t = IntToHex(timeout_ms);
		switch (t.length()) {
			case 1:
				t = "000" + t;
				break;
			case 2:
				t = "00" + t;
				break;
			case 3:
				t = "0" + t;
				break;
		}
		String epcls = IntToHex(tid.length * 8);
		switch (epcls.length()) {
			case 1:
				epcls = "0" + epcls;
				break;
		}
		String ls = IntToHex(tid.length + 12);
		switch (ls.length()) {
			case 1:
				ls = "0" + ls;
				break;
		}

		String forcrc = "FF" + ls + "25" + t + "02"
				+ Tools.Bytes2HexString(accesspassword,accesspassword.length)
				+ lockType+lockType+"00000000"
				+ epcls + Tools.Bytes2HexString(tid, tid.length);

		byte[] crc = getCRC(Tools.HexString2Bytes(forcrc), 1,
				forcrc.length() / 2 - 1);
		String cmd = forcrc + Tools.Bytes2HexString(crc, crc.length);
		String rev = sendcmd(cmd);
		if (checkReturn(cmd, rev)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean KillTagByTID(byte[] tid, byte[] killpassword,int timeout_ms){
		if (killpassword.length!=4) return false;
		String t = IntToHex(timeout_ms);
		switch (t.length()) {
			case 1:
				t = "000" + t;
				break;
			case 2:
				t = "00" + t;
				break;
			case 3:
				t = "0" + t;
				break;
		}
		String epcls = IntToHex(tid.length * 8);
		switch (epcls.length()) {
			case 1:
				epcls = "0" + epcls;
				break;
		}
		String ls = IntToHex(tid.length + 12);
		switch (ls.length()) {
			case 1:
				ls = "0" + ls;
				break;
		}
		String forcrc = "FF" + ls + "26" + t + "02"
				+ Tools.Bytes2HexString(killpassword,killpassword.length)
				+"00"+"00000000"
				+ epcls + Tools.Bytes2HexString(tid, tid.length);

		byte[] crc = getCRC(Tools.HexString2Bytes(forcrc), 1,
				forcrc.length() / 2 - 1);
		String cmd = forcrc + Tools.Bytes2HexString(crc, crc.length);
		String rev = sendcmd(cmd);
		if (checkReturn(cmd, rev)) {
			return true;
		} else {
			return false;
		}
	}



	private boolean checkCRC(byte[] bs) {
		byte[] crc = getCRC(bs, 1, bs[1] + 4);
		byte[] datacrc = new byte[2];
		if (bs.length >= bs[1] + 6 && (bs[1] + 6) > 0) {
			datacrc[0] = bs[bs[1] + 5];
			datacrc[1] = bs[bs[1] + 6];
			if (crc[0] == datacrc[0] && crc[1] == datacrc[1]) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	// public static int bytesToInt(byte[] src, int offset) {
	// int value;
	// value = (int) ((src[offset] & 0xFF) | ((src[offset + 1] & 0xFF) << 8));
	// return value;
	// }

	private byte[] getCRC(byte[] bs, int offset, int length) {
		short n = calcCrc(bs, offset, length);
		byte[] b = new byte[2];
		b[0] = (byte) ((n & 0xFF00) >> 8);
		b[1] = (byte) (n & 0xFF);
		return b;
	}

	private static int crcTable[] = { 0x0000, 0x1021, 0x2042, 0x3063, 0x4084,
			0x50a5, 0x60c6, 0x70e7, 0x8108, 0x9129, 0xa14a, 0xb16b, 0xc18c,
			0xd1ad, 0xe1ce, 0xf1ef, };

	// calculates ThingMagic's CRC-16
	private static short calcCrc(byte[] message, int offset, int length) {
		int crc = 0xffff;

		for (int i = offset; i < offset + length; i++) {
			crc = ((crc << 4) | ((message[i] >> 4) & 0xf))
					^ crcTable[crc >> 12];
			crc &= 0xffff;
			crc = ((crc << 4) | ((message[i] >> 0) & 0xf))
					^ crcTable[crc >> 12];
			crc &= 0xffff;
		}
		return (short) crc;
	}

	private String sendcmd(String cmd) {
		if (outputStream != null) {
			try {
				inputStream.read(new byte[5000]);
				outputStream.write(Tools.HexString2Bytes(cmd));
				Log.e("send", "" + cmd);
				String rev = recvCMD(1000);
				Log.e("rev:", "" + rev);
				return rev;
				// return receiveData(false);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}



	private String sendstopcmd(String cmd) {
		if (outputStream != null) {
			try {
				// inputStream.read(new byte[5000]);
				outputStream.write(Tools.HexString2Bytes(cmd));
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	private String sendcmd(String cmd, int timeout) {
		if (outputStream != null) {
			try {
				inputStream.read(new byte[4960]);
				outputStream.write(Tools.HexString2Bytes(cmd));
//				 //Log.e("鍙戦�佺瓑寰�"+timeout, "" + cmd);
				// Thread.sleep(5);
				// reciveDdata = "";
				Thread.sleep(timeout);
				String rev = recvCMD(1000);
//				 //Log.e("绛夊緟杩斿洖:", "" + rev);
				return rev;
				// return receiveData(false);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	private boolean checkReturn(String cmd, String rev) {
		if (rev != null && rev.length() > 10) {
			String c = cmd.substring(4, 6);
			String r = rev.substring(4, 6);
			String s = rev.substring(6, 10);
			// ////Log.e("c r s", c+":"+r+":"+s);
			if (c.equals(r) && s.equals("0000")) {
				return true;
			} else if (s.equals("0401")) {
				// //Log.e("Return Error:", "A protocol must be set.");
			}
			{
				return false;
			}
		} else {
			return false;
		}
	}


	// lbx 2017-05-16
	private String recvCMD(long timeout) {
		byte[] recv = new byte[4096];
		int headlen = 7;
		byte[] rbuf = new byte[7];
		int lenTmp = 0;
		int count = 0;
		try {
			long TickCount = System.currentTimeMillis();
			while (System.currentTimeMillis() - TickCount < timeout) {
				if (inputStream.available() >= 7) {
					lenTmp = inputStream.read(rbuf);
					System.arraycopy(rbuf, 0, recv, count, lenTmp);
					count += lenTmp;
					if (count >= headlen)
						break;
					Thread.sleep(5);
				} else {
					Thread.sleep(5);
				}
			}
			int lenLeft = rbuf[1];
			if (lenLeft < 0) {
				lenLeft = 256 + lenLeft;
			}
			rbuf = new byte[lenLeft];
			while ((lenLeft > 0)
					&& (System.currentTimeMillis() - TickCount < timeout)) {
				if (inputStream.available() > 0) {
					lenTmp = inputStream.read(rbuf);
					System.arraycopy(rbuf, 0, recv, count, lenTmp);
					lenLeft -= lenTmp;
					count += lenTmp;
					Thread.sleep(5);
				}
			}

			return Tools.Bytes2HexString(recv, count);
		} catch (NullPointerException npe) {
			recv = null;
			//Log.e("commandReader", "NullPointerException");
		} catch (Exception e) {
			e.printStackTrace();
			//Log.e("commandReader", "Exception");
			recv = null;
		}
		rbuf = null;
		return null;
	}


	private static String IntToHex(int n) {
		char[] ch = new char[20];
		int nIndex = 0;
		while (true) {
			int m = n / 16;
			int k = n % 16;
			if (k == 15)
				ch[nIndex] = 'F';
			else if (k == 14)
				ch[nIndex] = 'E';
			else if (k == 13)
				ch[nIndex] = 'D';
			else if (k == 12)
				ch[nIndex] = 'C';
			else if (k == 11)
				ch[nIndex] = 'B';
			else if (k == 10)
				ch[nIndex] = 'A';
			else
				ch[nIndex] = (char) ('0' + k);
			nIndex++;
			if (m == 0)
				break;
			n = m;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(ch, 0, nIndex);
		sb.reverse();
		String strHex = new String("");
		strHex += sb.toString();
		return strHex;
	}

	public static int HexToInt(String strHex) {
		int nResult = 0;
		if (!IsHex(strHex))
			return nResult;
		String str = strHex.toUpperCase();
		if (str.length() > 2) {
			if (str.charAt(0) == '0' && str.charAt(1) == 'X') {
				str = str.substring(2);
			}
		}
		int nLen = str.length();
		for (int i = 0; i < nLen; ++i) {
			char ch = str.charAt(nLen - i - 1);
			try {
				nResult += (GetHex(ch) * GetPower(16, i));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return nResult;
	}

	public static int GetHex(char ch) throws Exception {
		if (ch >= '0' && ch <= '9')
			return (int) (ch - '0');
		if (ch >= 'a' && ch <= 'f')
			return (int) (ch - 'a' + 10);
		if (ch >= 'A' && ch <= 'F')
			return (int) (ch - 'A' + 10);
		throw new Exception("error param");
	}

	public static int GetPower(int nValue, int nCount) throws Exception {
		if (nCount < 0)
			throw new Exception("nCount can't small than 1!");
		if (nCount == 0)
			return 1;
		int nSum = 1;
		for (int i = 0; i < nCount; ++i) {
			nSum = nSum * nValue;
		}
		return nSum;
	}

	public static boolean IsHex(String strHex) {
		int i = 0;
		if (strHex.length() > 2) {
			if (strHex.charAt(0) == '0'
					&& (strHex.charAt(1) == 'X' || strHex.charAt(1) == 'x')) {
				i = 2;
			}
		}
		for (; i < strHex.length(); ++i) {
			char ch = strHex.charAt(i);
			if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'F')
					|| (ch >= 'a' && ch <= 'f'))
				continue;
			return false;
		}
		return true;
	}
}
