package model.world;

import java.awt.Point;
import java.util.ArrayList;


import model.abilities.Ability;
import model.effects.Effect;

@SuppressWarnings("rawtypes")
public abstract class Champion implements Damageable,Comparable,Cloneable {
	private String name;
	private int maxHP;
	private int currentHP;
	private int mana;
	private int maxActionPointsPerTurn;
	private int currentActionPoints;
	private int attackRange;
	private int attackDamage;
	private int speed;
	private ArrayList<Ability> abilities;


	public void setAppliedEffects(ArrayList<Effect> appliedEffects) {
		this.appliedEffects = appliedEffects;
	}
	public void setAbilities(ArrayList<Ability> abilities) {
		this.abilities = abilities;
	}

	private ArrayList<Effect> appliedEffects;
	private Condition condition;
	private Point location;
	

	public Champion(String name, int maxHP, int mana, int actions, int speed, int attackRange, int attackDamage) {
		this.name = name;
		this.maxHP = maxHP;
		this.mana = mana;
		this.currentHP = this.maxHP;
		this.maxActionPointsPerTurn = actions;
		this.speed = speed;
		this.attackRange = attackRange;
		this.attackDamage = attackDamage;
		this.condition = Condition.ACTIVE;
		this.abilities = new ArrayList<Ability>();
		this.appliedEffects = new ArrayList<Effect>();
		this.currentActionPoints=maxActionPointsPerTurn;
	}
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
	

	public int getMaxHP() {
		return maxHP;
	}

	public String getName() {
		return name;
	}

	public void setCurrentHP(int hp) {

		if (hp <= 0) {
			currentHP = 0;
			condition=Condition.KNOCKEDOUT;
			
		} 
		else if (hp > maxHP)
			currentHP = maxHP;
		else
			currentHP = hp;

	}

	
	public int getCurrentHP() {

		return currentHP;
	}

	public ArrayList<Effect> getAppliedEffects() {
		return appliedEffects;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getAttackDamage() {
		return attackDamage;
	}

	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int currentSpeed) {
		if (currentSpeed < 0)
			this.speed = 0;
		else
			this.speed = currentSpeed;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point currentLocation) {
		this.location = currentLocation;
	}

	public int getAttackRange() {
		return attackRange;
	}

	public ArrayList<Ability> getAbilities() {
		return abilities;
	}

	public int getCurrentActionPoints() {
		return currentActionPoints;
	}

	public void setCurrentActionPoints(int currentActionPoints) {
		if(currentActionPoints>maxActionPointsPerTurn)
			currentActionPoints=maxActionPointsPerTurn;
		else 
			if(currentActionPoints<0)
			currentActionPoints=0;
		this.currentActionPoints = currentActionPoints;
	}

	public int getMaxActionPointsPerTurn() {
		return maxActionPointsPerTurn;
	}

	public void setMaxActionPointsPerTurn(int maxActionPointsPerTurn) {
		this.maxActionPointsPerTurn = maxActionPointsPerTurn;
	}

	public int compareTo(Object o)
	{
		Champion c = (Champion)o;
		if(speed==c.speed)
			return name.compareTo(c.name);
		return -1 * (speed-c.speed);
	}
	
public abstract void useLeaderAbility(ArrayList<Champion> targets);


public String toString() {
	String allString="";
	allString+="name: "+name+"\n"; 
	allString+="maxHP: "+maxHP+"\n"; 
	allString+="currentHP: "+currentHP+"\n"; 
	allString+="mana: "+mana+"\n"; 
	allString+="maxActionPointsPerTurn: "+maxActionPointsPerTurn+"\n"; 
	
	allString+="currentActionPoints: "+currentActionPoints+"\n"; 
	
	allString+="attackRange: "+attackRange+"\n"; 
	allString+="attackDamage: "+attackDamage+"\n"; 
	allString+="speed: "+speed+"\n"; 
	allString+="condition: "+condition+"\n"; 
	//allString+="condition: "+condition+"\n"; 
	allString+="abilities: "+abilities()+"\n"; 
	
	
	
	//allString+="appliedEffects: "+appliedEffects+"\n"; 
	//allString+="currentActionPoints: "+currentActionPoints+"\n"; 
   return allString;

}
private String abilities() {
	String string="";
	for(Ability a:abilities)
		string+=a+"\n";
	return string;
}

public Champion getvirtualChampion() {
	Champion vChampion=null;
	try {
		vChampion = (Champion) this.clone();
		ArrayList< Ability> vAbilities=new ArrayList<Ability>();
		for(Ability a:abilities)
			vAbilities.add(a);
		ArrayList<Effect>vEffects=new ArrayList<Effect>();
		for(Effect e:appliedEffects)
			vEffects.add(e);
		vChampion.setAbilities(vAbilities);
		vChampion.setAppliedEffects(vEffects);
		vChampion.setCondition(condition);
	} catch (CloneNotSupportedException e) {
		e.printStackTrace();
	}
	return vChampion;
}


}
