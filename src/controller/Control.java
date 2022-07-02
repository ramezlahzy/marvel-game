package controller;

import java.io.IOException;

import engine.Game;
import exceptions.GameActionException;
import views.firstview;
import views.secondview;
import views.thirdview;

public class Control {
  private firstview first;
  private secondview second;
  private Game game;
  private int difficutly;
  public Control() throws IOException {
	  first=new firstview();
	  first.setControl(this);
	  first.setVisible(true);
	  
	 
  }
  public static void main(String[] args) throws IOException {
	  Control control=new Control();
  }
  public void tothird(boolean computerplayermood) throws GameActionException {
	  second.setVisible(false);
	  
	  thirdview thrid=new thirdview(this,computerplayermood,difficutly);
	  game.setListener(thrid); 
  }
  public void tosecondview(boolean computerplayermood,int dif) throws IOException {
	  difficutly=dif;
	  first.setVisible(false);
	  second=new secondview(this,computerplayermood);
	 // second=new secondview();
	  second.setVisible(true);
  }
  
public Game getGame() {
	return game;
}
public void setGame(Game game) {
	this.game = game;
}
}
