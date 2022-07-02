package views;

import java.util.ArrayList;

import engine.Player;
import model.world.Champion;
import model.world.Damageable;
import model.world.Direction;

public interface listener {

	
	public void attack(Direction direction,Damageable target)  ;
	public void death(Damageable damageable,int x,int y);
	public void castteamtarget(Player p1,boolean with);
	public void sroundtarget(Champion c);
	public void leaderabilityforvalain(ArrayList<Champion>t);
	public void castsingletarget();
	public void castselftarget();
	public void leaderabilityforvalaind(ArrayList<Damageable>t);
	public void casthealingd(ArrayList<Damageable>c);

	public void casthealing(ArrayList<Champion>c);
	public void castdirectiontarget();
	
	
}
