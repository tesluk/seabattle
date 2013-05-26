package basata.seabattle.conection;

import java.awt.Point;
import java.nio.ByteBuffer;
import java.util.Arrays;

import basata.seabattle.gui.GamesList;
import basata.seabattle.models.Field;
import basata.seabattle.models.Statistic;

/**
 * 
 * @author Tab
 * 
 */
public class BattleMessage {

	public int type;
	public int size;
	public byte[] data;

	public String login;
	public String pass;
	public int id;
	public Point point;

	public Statistic statistic;
	public Field field;
	public GamesList gamesList;

	public BattleMessage(String _login, String _pass) {
		type = MessConst.LOGIN;
		login = _login;
		pass = _pass;
	}

	public BattleMessage(int _type, Point _point) {
		type = _type;
		point = _point;
	}

	public BattleMessage(int _type, String str) {
		type = _type;
		login = str;
	}

	public BattleMessage(int _type, GamesList list) {
		type = _type;
		gamesList = list;
	}

	public BattleMessage(int _type, Field _field) {
		type = _type;
		field = _field;
	}

	public BattleMessage(int _type, String _login, Statistic stat) {
		type = _type;
		login = _login;
		statistic = stat;
	}

	public BattleMessage(int _type) {
		type = _type;
	}

	public BattleMessage(int _type, int _id) {
		type = _type;
		id = _id;
	}

	public BattleMessage(int _type, int _id, String str) {
		type = _type;
		id = _id;
		login = str;
	}

	public static BattleMessage parseMessage(byte[] buf, int size)
			throws Exception {
		String tmpStr = new String(buf);
		String[] parts = tmpStr.split(";");
		int tmp = Integer.valueOf(parts[0]);
		System.out.println("TYPE " + tmp);
		switch (tmp) {
		case MessConst.LOGIN: // On server
			System.out.println("login");
			return new BattleMessage(parts[1], parts[2]);

		case MessConst.LOGIN_OK:
			System.out.println("log_ok");
			return new BattleMessage(tmp, parts[1],
					Statistic.parseStatistic(parts[2]));

		case MessConst.WRONG_PASS:
			return new BattleMessage(tmp);

		case MessConst.ALREADY_LOGINED:
			return new BattleMessage(tmp);

		case MessConst.NEW_USER:
			System.out.println("NEW_USER");
			return new BattleMessage(tmp, parts[1]);

		case MessConst.GAMES_LIST:
			System.out.println("Games list");
			GamesList list = GamesList.parseGamesList(new String(buf));
			return new BattleMessage(tmp, list);

		case MessConst.NEW_GAME_CREATED:
			System.out.println("new game created");
			return new BattleMessage(tmp, Integer.parseInt(parts[1]));

		case MessConst.JOINED_TO_GAME:
			System.out.println("joined to game");
			return new BattleMessage(tmp, Integer.parseInt(parts[1]));

		case MessConst.OPPONENT_JOINED:
			System.out.println("opponent joined");
			return new BattleMessage(tmp, Integer.parseInt(parts[1]), parts[2]);

		case MessConst.START_YOUR_MOVE:
			System.out.println("start. your move");
			return new BattleMessage(tmp);

		case MessConst.START_OPPONENT_MOVE:
			System.out.println("start. opponent move");
			return new BattleMessage(tmp);

		case MessConst.OPPONENT_DISCONECTED:
			System.out.println("Opponent disconected");
			Field f = new Field();
			f.setField(Field.FromStringToOBJ(parts[1]));
			return new BattleMessage(tmp, f);

		case MessConst.OK:
			System.out.println("OK");
			return new BattleMessage(tmp);

		case MessConst.SHOT:
		case MessConst.SHOT_FAIL:
		case MessConst.SHOT_HIT:
		case MessConst.SHOT_KILL:
		case MessConst.LOSE:
			System.out.println("shot " + tmp);
			return new BattleMessage(tmp, new Point(Integer.parseInt(parts[1]),
					Integer.parseInt(parts[2])));

		default:
			return null;
		}
	}

	public static int byteArrayToInt(byte[] buf) {
		return ByteBuffer.wrap(buf).getInt();
	}

	public static byte[] intToByteArray(int val) {
		return ByteBuffer.allocate(4).putInt(val).array();
	}

	public static byte[] concatArrays(byte[] a, byte[] b) {
		byte[] res = new byte[a.length + b.length];
		System.arraycopy(a, 0, res, 0, a.length);
		System.arraycopy(b, 0, res, a.length, b.length);
		return res;
	}

	public byte[] getData() throws Exception {
		byte[] res = String.valueOf(type).getBytes();
		res = concatArrays(res, ";".getBytes());
		switch (type) {
		case MessConst.LOGIN:
			res = concatArrays(res, login.getBytes());
			res = concatArrays(res, ";".getBytes());
			res = concatArrays(res, pass.getBytes());
			break;

		case MessConst.QUIT:
			break;

		case MessConst.GET_GAMES_LIST:
			break;

		case MessConst.CREATE_NEW_GAME:
			throw new Exception("realize it!");

		case MessConst.JOIN_GAME:
			res = concatArrays(res, ByteBuffer.allocate(4).putInt(id).array());
			break;

		case MessConst.READY:
			throw new Exception("realize it!");

		case MessConst.SHOT:
		case MessConst.SHOT_FAIL:
		case MessConst.SHOT_HIT:
		case MessConst.SHOT_KILL:
		case MessConst.LOSE:
			res = concatArrays(res,
					(String.valueOf(point.x) + ";" + String.valueOf(point.y))
							.getBytes());
			break;

		default:
			throw new Exception("wrong type: " + type);
		}

		return concatArrays(res, ".".getBytes());
	}

	@Override
	public String toString() {
		return "";
	}

}
