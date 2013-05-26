package basata.seabattle.ai;

import basata.seabattle.conection.BattleMessage;
import basata.seabattle.conection.MessConst;
import basata.seabattle.conection.SocketBattleClient;
import basata.seabattle.gui.GamesList;
import basata.seabattle.models.Statistic;

public class Game {

	private SocketBattleClient sbk;
	private Statistic statistic;
	private AI ai;

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
		
		if(res.type == MessConst.LOGIN_OK){
			statistic = res.statistic;
		}
		
		return res;
	}

	public GamesList getGamesList(){
		BattleMessage tmp = new BattleMessage(MessConst.GET_GAMES_LIST);
		
		return null;
	}
	
}
