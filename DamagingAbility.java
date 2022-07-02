package model.abilities;

import java.util.ArrayList;

import model.world.Champion;
import model.world.Cover;
import model.world.Damageable;

public class DamagingAbility extends Ability {
	
	private int damageAmount;
	public DamagingAbility(String name, int cost, int baseCoolDown, int castRadius, AreaOfEffect area,int required,int damageAmount) {
		super(name, cost, baseCoolDown, castRadius, area,required);
		this.damageAmount=damageAmount;
	}
	public int getDamageAmount() {
		return damageAmount;
	}
	public void setDamageAmount(int damageAmount) {
		this.damageAmount = damageAmount;
	}
	
	@Override
	public void execute(ArrayList<Damageable> targets) {
		// TODO Auto-generated method stub
		for(int i=0;i<targets.size();i++) {
			Damageable y = targets.get(i);
			
			if(y instanceof Champion) {
				int x = y.getCurrentHP();
				x=y.getCurrentHP()-this.damageAmount;
			}
			else if (y instanceof Cover) {
				int x = y.getCurrentHP();
				x=y.getCurrentHP()-this.damageAmount;
			}
		}
		
	}
	

}
