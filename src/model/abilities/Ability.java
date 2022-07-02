package model.abilities;

import java.util.ArrayList;

import model.world.Damageable;

public abstract class Ability implements Cloneable{
	private String name;
	private int manaCost;
	private int baseCooldown;
	private int currentCooldown;
	private int castRange;
	private AreaOfEffect castArea;
	private int requiredActionPoints;

	public Ability(String name, int cost, int baseCoolDown, int castRange, AreaOfEffect area, int required) {
		this.name = name;
		this.manaCost = cost;
		this.baseCooldown = baseCoolDown;
		this.currentCooldown = 0;
		this.castRange = castRange;
		this.castArea = area;
		this.requiredActionPoints = required;
	}
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
	public int getCurrentCooldown() {
		return currentCooldown;
	}
	public abstract void execute(ArrayList<Damageable> targets) throws CloneNotSupportedException;

	public void setCurrentCooldown(int currentCoolDown) {
		if (currentCoolDown < 0)
			currentCoolDown = 0;
		else if (currentCoolDown > baseCooldown)
			currentCoolDown = baseCooldown;
		this.currentCooldown = currentCoolDown;
	}

	public String getName() {
		return name;
	}

	public int getManaCost() {
		return manaCost;
	}

	public int getBaseCooldown() {
		return baseCooldown;
	}

	public int getCastRange() {
		return castRange;
	}

	public AreaOfEffect getCastArea() {
		return castArea;
	}

	public int getRequiredActionPoints() {
		return requiredActionPoints;
	}
	
	public String toString() {
		String allString="";
		allString+="name: "+name+"\n"; 
		allString+="manaCost: "+manaCost+"\n"; 
		allString+="baseCooldown: "+baseCooldown+"\n"; 
		allString+="castRange: "+castRange+"\n"; 
		allString+="castArea: "+castArea+"\n"; 
		allString+="requiredActionPoints: "+requiredActionPoints+"\n"; 
		return name;
 
	}
	public String toString2() {
		String allString="";
		allString+="name: "+name+"\n"; 
		allString+="manaCost: "+manaCost+"\n"; 
		allString+="baseCooldown: "+baseCooldown+"\n"; 
		allString+="castRange: "+castRange+"\n"; 
		allString+="castArea: "+castArea+"\n"; 
		allString+="requiredActionPoints: "+requiredActionPoints+"\n"; 
		return allString;
 
	}
	public Ability getvirtual() {
		try {
			Ability vAbility=(Ability) this.clone();
			return vAbility;
		} catch (CloneNotSupportedException e) {e.printStackTrace();}
	
	   return null;
	
	
	}

}
