package basata.seabattle.conection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 
 * @author Tab
 * 
 */
public class SocketBattleClient {

	public int port;
	public String strAddress;
	public InetAddress address;
	public Socket socket;
	public InputStream is;
	public OutputStream os;
	DataInputStream dis;
	DataOutputStream dos;

	public SocketBattleClient(String adr, int _port) {
		port = _port;
		strAddress = adr;
		try {
			address = InetAddress.getByName(strAddress);
			socket = new Socket(address, port);

			is = socket.getInputStream();
			os = socket.getOutputStream();

			dis = new DataInputStream(is);
			dos = new DataOutputStream(os);

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public BattleMessage sendRequest(BattleMessage mes) throws Exception {
		BattleMessage res;
		try {
			dos.write(mes.getData());
			dos.flush();

			System.out.println("Data sended " + new String(mes.getData()));

			byte[] buf = new byte[1024];
			int size = dis.read(buf);

			System.out.println(size);
			System.out.println("Data recieved " + size + "  "
					+ new String(buf, 0, size));

			String recivedData = new String(buf, 0, size);

			res = BattleMessage.parseMessage(
					recivedData.split("\\.")[0].getBytes(), size);

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return res;
	}

	public static void main(String[] args) throws Exception {

		SocketBattleClient sbc = new SocketBattleClient("192.168.1.39", 7777);
		BattleMessage m1 = new BattleMessage(MessConst.GET_GAMES_LIST);

		BattleMessage m2 = sbc.sendRequest(m1);

		// if (m2 == null) {
		// System.out.println("NULL");
		// } else {
		// System.out.println(m2.login + " 44 " + m2.pass);
		// }

	}

}
