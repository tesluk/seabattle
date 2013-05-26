package basata.seabattle.gui;

import java.util.ArrayList;

public class GamesList {

	public class GameRow {
		public String nick;
		public int gameId;

		public GameRow(String _nick, int _id) {
			nick = _nick;
			gameId = _id;
		}
	}

	ArrayList<GameRow> games = new ArrayList<GamesList.GameRow>();

	public void addGame(GameRow game) {
		games.add(game);
	}

	public void addGame(String _nick, int id) {
		addGame(new GameRow(_nick, id));
	}

	public ArrayList<GameRow> getGames() {
		return games;
	}

	public static GamesList parseGamesList(String str) throws Exception {
		String[] parts = str.split(";");
		if (parts.length % 2 == 0) {
			throw new Exception("Wrong number of games " + parts.length);
		}

		GamesList res = new GamesList();
		for (int i = 1; i < parts.length; i += 2) {
			res.addGame(parts[i + 1], Integer.parseInt(parts[i]));
		}
		
		return res;
	}

}
