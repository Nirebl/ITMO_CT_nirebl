package game;

import java.util.*;
import java.io.IOException;

public class Config {
    private String game;
    private List<String> players;
    private String board;
    private int n, m;
    private int k;

    private Config() {
        players = new ArrayList<>();
    }

    public static Config ReadConfiguration(String configFileName) throws IOException {
        IniFile iniFile = new IniFile();
        iniFile.load(configFileName);

        Config cfg = new Config();
        cfg.game = iniFile.getString("Game", "TwoPlayer");

        int plN = 1;
        String pltype = iniFile.getString("Player" + plN, "");
        while (pltype != "") {
            cfg.players.add(pltype);
            plN++;
            pltype = iniFile.getString("Player" + plN, "");
        }

        cfg.board = iniFile.getString("Board", "Rectangle");
        cfg.n = iniFile.getInt("SizeX", 5);
        cfg.m = iniFile.getInt("SizeY", 7);

        cfg.k = iniFile.getInt("WinnerCount", 4);

        return cfg;
    }

    public String getGame() {
        return this.game;
    }

    public List<String> getPlayers() {
        return this.players;
    }

    public String getBoard() {
        return this.board;
    }

    public int getN() {
        return this.n;
    }

    public int getM() {
        return this.m;
    }

    public int getK() {
        return this.k;
    }

}
