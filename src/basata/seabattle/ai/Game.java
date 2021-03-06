package basata.seabattle.ai;

import java.awt.Point;

import basata.seabattle.conection.BattleMessage;
import basata.seabattle.conection.MessConst;
import basata.seabattle.conection.SocketBattleClient;
import basata.seabattle.gui.GameForm2;
import basata.seabattle.gui.GamesList;
import basata.seabattle.models.Field;
import basata.seabattle.models.Statistic;

public class Game {

	private SocketBattleClient sbk;
	private Statistic statistic;
	private AI ai;
	private GameForm2 gameForm;
	private boolean isEnemyOnline; 

	public Game(String ip, int port) {
		sbk = new SocketBattleClient(ip, port);
	}

	public BattleMessage login(String log, String pass) {
		BattleMessage tmp = new BattleMessage(log, pass);
		BattleMessage res = null;
		try {
			res = sbk.sendRequest(tmp);
		} catch (Exception e) {
			System.err.println("LOGIN ERROR");
			e.printStackTrace();
		}
		return res;
	}

	public BattleMessage quit() {
		BattleMessage tmp = new BattleMessage(MessConst.QUIT);
		BattleMessage res = null;
		try {
			res = sbk.sendRequest(tmp);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.type == MessConst.LOGIN_OK) {
			statistic = res.statistic;
		}

		return res;
	}

	public GamesList getGamesList() {
		BattleMessage tmp = new BattleMessage(MessConst.GET_GAMES_LIST);
		BattleMessage res = null;

		try {
			res = sbk.sendRequest(tmp);
			return res.gamesList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public BattleMessage createNewGame(Field field, GameForm2 form) {
		gameForm = form;
		try {
			statistic.plusField(field);
			BattleMessage tmp = new BattleMessage(MessConst.CREATE_NEW_GAME,
					field, statistic);
			BattleMessage res = sbk.sendRequest(tmp);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public BattleMessage joinGame(int gameId, GameForm2 form) {
		gameForm = form;
		BattleMessage tmp = new BattleMessage(MessConst.JOIN_GAME, gameId);
		try {
			BattleMessage res = sbk.sendRequest(tmp);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public BattleMessage readyToGame(Field field) {
		try {
			statistic.plusField(field);
			BattleMessage tmp = new BattleMessage(MessConst.READY, field,
					statistic);
			BattleMessage res = sbk.sendRequest(tmp);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int makeShot(int x, int y){
		BattleMessage tmp = new BattleMessage(MessConst.SHOT, new Point(x, y));
		try {
			BattleMessage res = sbk.sendRequest(tmp);
			switch (res.type) {
			case MessConst.SHOT_FAIL:
				return Field.STRUCKED_FREE_CELL;
			case MessConst.SHOT_HIT:
				return Field.STRUCKED_CELL_WITH_SHEEP;
			case MessConst.SHOT_KILL:
				return Field.STRUCKED_CELL_WITH_SHEEP;
			case MessConst.LOSE:
				return Field.STRUCKED_CELL_WITH_SHEEP;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
}
