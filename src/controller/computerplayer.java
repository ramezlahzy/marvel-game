package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.DirectoryStream.Filter;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.junit.runner.notification.StoppedByUserException;

import engine.Game;
import exceptions.GameActionException;
import model.world.Champion;
import model.world.Direction;
import views.thirdview;

public class computerplayer {
	virtualgame virsualGame;
	ArrayList<Direction> directions;
	ArrayList<String> OptionsString;
	ArrayList<String> bestActionStrings;
	int bestdifferntinhealth;
	int bestdifferntindistance;
	int healthbeforeationsofenemies;
	int distancefromenemiesbeforeations;
	private int difficulty;
	Game realGame;
	private boolean flag;
	static TreeMap<Integer, ArrayList<String>> map;
    private thirdview thridview;
	public computerplayer(Game game,thirdview thrirdview,int dif) {
		difficulty=dif;
		this.thridview=thrirdview;
		realGame = game;
		map = new TreeMap<Integer, ArrayList<String>>();
		bestdifferntindistance = 10000;
		bestActionStrings = new ArrayList<>();
		OptionsString = new ArrayList<>();
		OptionsString.add("MU");
		OptionsString.add("MD");
		OptionsString.add("MR");
		OptionsString.add("ML");
		OptionsString.add("AU");
		OptionsString.add("AD");
		OptionsString.add("AR");
		OptionsString.add("AL");
		virsualGame = game.turngametoVirtualgame();
		distancefromenemiesbeforeations = virsualGame.getdictancefromcurrenttoenemies();
		healthbeforeationsofenemies = virsualGame.gethealthofenemies();
		virtualmoves(virsualGame, new ArrayList<String>(), 0, 0);
//		System.out.println("a6a");
//		System.out.println(map);
	//	System.out.println(bestActionStrings);
		
	   System.out.println("computer will use "+game.getCurrentChampion().getName());
		excuteactions();

	}

	public void excuteactions() {
	//	System.out.println(bestActionStrings);
		if (bestActionStrings.isEmpty()) {
			
			realGame.endTurn();
			thridview.fillpriorityqueueturn();

			if (realGame.checkGameOver() != null) {
				thridview.endtheandselectwinner();
				return;
			}
			System.out.println(((Champion)(realGame.getTurnOrder().peekMin())).getName()+"   will player after computer");
			this.thridview.fillturnorder();
			thridview.setComputerplayernow(false);
			
			if (realGame.checkGameOver() != null) {
				thridview.endtheandselectwinner();
				return;
			}
			checkpalyeragain();
			return;
			
		}
		try {
			 flag=!flag;
			
			if(flag) {
				
						String string = bestActionStrings.remove(0);

				System.out.println(string);
				if (string.equals("MU"))
					realGame.move(Direction.UP);
				if (string.equals("MD"))
					realGame.move(Direction.DOWN);
				if (string.equals("MR"))
					realGame.move(Direction.RIGHT);
				if (string.equals("ML"))
					realGame.move(Direction.LEFT);

				if (string.equals("AU"))
					realGame.attack(Direction.UP);
				if (string.equals("AD"))
					realGame.attack(Direction.DOWN);
				if (string.equals("AR"))
					realGame.attack(Direction.RIGHT);
				if (string.equals("AL"))
					realGame.attack(Direction.LEFT);
				}
				thridview.fillboard();
				thridview.revalidate();
				thridview.repaint();
			
		if(!flag) {
		        boolean flag1=true;
				try {
					realGame.useLeaderAbility();
					flag1=false;
				} catch (Exception e) {
					// TODO: handle exception
				}	
				if(flag1)
				try {
					realGame.castAbility(realGame.getCurrentChampion().getAbilities().get(2));
					flag1=false;

				} catch (Exception e) {
					// TODO: handle exception
				}
				if(flag1)
				try {
					realGame.castAbility(realGame.getCurrentChampion().getAbilities().get(0));
					flag1=false;

				} catch (Exception e) {
					// TODO: handle exception
				}
				if(flag)
				try {
					realGame.castAbility(realGame.getCurrentChampion().getAbilities().get(1));
					flag=false;

				} catch (Exception e) {
					// TODO: handle exception
				}
				thridview.fillboard();
				thridview.revalidate();
				thridview.repaint();
		
		}
		
		
		
		
		
		} catch (Exception e) {
				// TODO: handle exception
			}

			
			
			
			
		
		final int[] counter = new int[] { 0 };
		Timer timer = new Timer(10, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (counter[0]++ >= 60) {
					excuteactions();
				    ((Timer)e.getSource()).stop();	
				}

			}
		});
		timer.start();
	}
   public void checkpalyeragain() {
	   
			if (thridview.getGame().getFirstPlayer().getTeam().contains(thridview.getGame().getCurrentChampion())&&
					thridview.isComputerplayermood()) {
				thridview.setComputerplayernow(true);

				new computerplayer(realGame, thridview,difficulty);
			}
		
   }
	public ArrayList<Direction> getbestwaytomove() {
		ArrayList<Direction> moves = new ArrayList<Direction>();

		return moves;
	}

	public void virtualmoves(virtualgame temp, ArrayList<String> Actions, int bestlose, int counter) {
		// System.out.println(" :"+bestlose);
		// System.out.println("in virtual"+temp.getCurrentChampion().getName());
		
		
		
		
		
		System.out.println("difff    "+difficulty);
		if (bestlose >= 40)//difficulty*20-10)
			return;
		if (counter >= 8) {
			counter = 0;
			// virtualmoves(temp, Actions,++bestlose, counter);
			// return;
		}
		// System.out.println("phase : "+"counter"+counter+" bestlose: "+bestlose);

		ArrayList<String> newactionStrings2 = (ArrayList<String>) Actions.clone();
		virtualgame newVirtualgame2 = new virtualgame(temp);
		virtualmoves(newVirtualgame2, newactionStrings2, 1 + bestlose, 1 + counter);

		try {
			// System.out.println(" "+OptionsString.get(counter));
			// if (OptionsString.get(counter).equals("MU"))
			if (counter == 0) {
				// System.out.println(bestlose);
				virtualgame newVirtualgame = new virtualgame(temp);
//				System.out.println("beforemove");
//                newVirtualgame.printgrid();
				newVirtualgame.move(Direction.UP);
//				System.out.println("aftermove");
//                newVirtualgame.printgrid();
//    			System.out.println('\n'+'\n'+'\n'+'\n');

				ArrayList<String> newactionStrings = (ArrayList<String>) Actions.clone();
				newactionStrings.add("MU");
				int currenthealthofenemies = temp.gethealthofenemies();
				int currentdistanceofenemies = temp.getdictancefromcurrenttoenemies();
				int differntinhealth = this.healthbeforeationsofenemies - currentdistanceofenemies;
				int differntindistance = this.distancefromenemiesbeforeations - currentdistanceofenemies;
				// map.put(differntindistance,newactionStrings);
				virtualmoves(newVirtualgame, newactionStrings, ++bestlose, ++counter);
				// System.out.println("done");

			}
			// if (OptionsString.get(counter).equals("MD"))
			if (counter == 1) {
				virtualgame newVirtualgame = new virtualgame(temp);
//				System.out.println("beforemove");
//                newVirtualgame.printgrid();
				newVirtualgame.move(Direction.DOWN);
//				System.out.println("aftermove");
				// newVirtualgame.printgrid();
				// System.out.println('\n'+'\n'+'\n'+'\n');
				ArrayList<String> newactionStrings = (ArrayList<String>) Actions.clone();
				newactionStrings.add("MD");
				int currenthealthofenemies = temp.gethealthofenemies();
				int currentdistanceofenemies = temp.getdictancefromcurrenttoenemies();
				int differntinhealth = this.healthbeforeationsofenemies - currentdistanceofenemies;
				int differntindistance = this.distancefromenemiesbeforeations - currentdistanceofenemies;
				// map.put(differntindistance,newactionStrings);
				virtualmoves(newVirtualgame, newactionStrings, ++bestlose, ++counter);
				// System.out.println("done");

			}
			// if (OptionsString.get(counter).equals("MR"))
			if (counter == 2) {
				virtualgame newVirtualgame = new virtualgame(temp);
				newVirtualgame.move(Direction.RIGHT);
				ArrayList<String> newactionStrings = (ArrayList<String>) Actions.clone();
				newactionStrings.add("MR");
				int currenthealthofenemies = temp.gethealthofenemies();
				int currentdistanceofenemies = temp.getdictancefromcurrenttoenemies();
				int differntinhealth = this.healthbeforeationsofenemies - currentdistanceofenemies;
				int differntindistance = this.distancefromenemiesbeforeations - currentdistanceofenemies;
				// map.put(differntindistance,newactionStrings);
				virtualmoves(newVirtualgame, newactionStrings, ++bestlose, ++counter);
				// System.out.println("done");
			}
			// if (OptionsString.get(counter).equals("ML"))
			if (counter == 3) {
				virtualgame newVirtualgame = new virtualgame(temp);
				newVirtualgame.move(Direction.LEFT);
				ArrayList<String> newactionStrings = (ArrayList<String>) Actions.clone();
				newactionStrings.add("ML");
				int currenthealthofenemies = temp.gethealthofenemies();
				int currentdistanceofenemies = temp.getdictancefromcurrenttoenemies();
				int differntinhealth = this.healthbeforeationsofenemies - currentdistanceofenemies;
				int differntindistance = this.distancefromenemiesbeforeations - currentdistanceofenemies;
				// map.put(differntindistance,newactionStrings);
				virtualmoves(newVirtualgame, newactionStrings, ++bestlose, ++counter);
				// System.out.println("done");

			}

			// if (OptionsString.get(counter).equals("AU"))
			if (counter == 4) {
				virtualgame newVirtualgame = new virtualgame(temp);
				newVirtualgame.attackvirtual(Direction.UP);
				int currenthealthofenemies = temp.gethealthofenemies();
				int differntinhealth = this.healthbeforeationsofenemies - currenthealthofenemies;
				ArrayList<String> newactionStrings = (ArrayList<String>) Actions.clone();
				newactionStrings.add("AU");
				// map.put(differntinhealth,newactionStrings);
				virtualmoves(newVirtualgame, newactionStrings, ++bestlose, ++counter);

			}
			// if (OptionsString.get(counter).equals("AD"))
			if (counter == 5) {
				virtualgame newVirtualgame = new virtualgame(temp);
				newVirtualgame.attackvirtual(Direction.DOWN);
				int currenthealthofenemies = temp.gethealthofenemies();
				int differntinhealth = this.healthbeforeationsofenemies - currenthealthofenemies;
				ArrayList<String> newactionStrings = (ArrayList<String>) Actions.clone();
				newactionStrings.add("AD");
				// map.put(differntinhealth,newactionStrings);
				virtualmoves(newVirtualgame, newactionStrings, ++bestlose, ++counter);

			}
			// if (OptionsString.get(counter).equals("AR"))
			if (counter == 6) {
				virtualgame newVirtualgame = new virtualgame(temp);
				newVirtualgame.attackvirtual(Direction.RIGHT);
				ArrayList<String> newactionStrings = (ArrayList<String>) Actions.clone();
				int currenthealthofenemies = temp.gethealthofenemies();
				int differntinhealth = this.healthbeforeationsofenemies - currenthealthofenemies;
				newactionStrings.add("AR");
				// map.put(differntinhealth,newactionStrings);
				virtualmoves(newVirtualgame, newactionStrings, ++bestlose, ++counter);

			}
			// if (OptionsString.get(counter).equals("AL"))
			if (counter == 7) {
				virtualgame newVirtualgame = new virtualgame(temp);
				newVirtualgame.attackvirtual(Direction.LEFT);
				ArrayList<String> newactionStrings = (ArrayList<String>) Actions.clone();
				int currenthealthofenemies = temp.gethealthofenemies();
				int differntinhealth = this.healthbeforeationsofenemies - currenthealthofenemies;
				newactionStrings.add("AL");
				map.put(differntinhealth, newactionStrings);
				virtualmoves(newVirtualgame, newactionStrings, ++bestlose, ++counter);

			}

		} catch (GameActionException e) {
			// System.out.println(Actions);
			// System.out.println(OptionsString.get(counter));
			int currenthealthofenemies = temp.gethealthofenemies();
			int currentdistanceofenemies = temp.getdictancefromcurrenttoenemies();
			int differntinhealth = this.healthbeforeationsofenemies - currenthealthofenemies;
			int differntindistance = this.distancefromenemiesbeforeations - currentdistanceofenemies;
			if (differntinhealth > bestdifferntinhealth) {
				// System.out.println("bestdifferntinhealth :"+bestdifferntinhealth);
				// System.out.println("differntinhealth :"+differntinhealth);
				bestdifferntinhealth = differntinhealth;
				bestActionStrings = Actions;
				map.put(differntindistance, Actions);
			}
			if (differntinhealth == bestdifferntinhealth) {
				// System.out.println("currentdistanceofenemies :"+currentdistanceofenemies);
				// System.out.println("bestdifferntindistance :"+bestdifferntindistance);
				// map.put(Actions, differntindistance);

				if (differntindistance >= bestdifferntindistance) {
					bestdifferntindistance = differntindistance;
					bestActionStrings = Actions;
					map.put(differntindistance, Actions);
				}
			}
		}
	}

//	public virtualgame getvirsualgame(Game game) {
//		return game.turngametoVirtualgame();
//
//	}
}
