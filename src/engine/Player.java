package engine;

import java.util.ArrayList;

import model.world.Champion;

public class Player {
	private String name;
	private ArrayList<Champion> team;
	private Champion leader;

	public Player(String name) {
		this.name = name;
		team = new ArrayList<Champion>();

	}

	public Champion getLeader() {
		return leader;
	}

	public void setLeader(Champion leader) {
		this.leader = leader;
	}

	public String getName() {
		return name;
	}

	public void setTeam(ArrayList<Champion> t) {
		team = t;
	}

	public ArrayList<Champion> getTeam() {
		return team;
	}

	public Player virtualPlayer() {
		Player vPlayer = new Player(name);
		vPlayer.leader = leader.getvirtualChampion();
		ArrayList<Champion> vteam = new ArrayList<Champion>();
		for (Champion c : team)
			vteam.add(c.getvirtualChampion());
		vPlayer.setTeam(vteam);
		return vPlayer;

	}

}
