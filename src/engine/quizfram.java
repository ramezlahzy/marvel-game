package engine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import model.world.Champion;

public class quizfram extends JFrame{
	JButton button1;
	JButton button2;
	JButton button3;
	 Champion firstChampion;
	 Champion secondChampion;
	 Champion thirdChampion;
	 JLayeredPane lPane;
  public quizfram() {
	  setVisible(true);
	  setSize(1400,800);
	  this.setLayout(null);
	  Game game=new Game(new Player("ramez"), new Player("nashaat"));
	  
	//  this.setLayout(new BorderLayout());
	  System.out.println(game.getAvailableChampions().size());
      int random1=(int)(Math.random()*game.getAvailableChampions().size());	  
      int random2=(int)(Math.random()*game.getAvailableChampions().size());	  
      int random3=(int)(Math.random()*game.getAvailableChampions().size());	  
      firstChampion=game.getAvailableChampions().get(random1);
      secondChampion=game.getAvailableChampions().get(random2);
       thirdChampion=game.getAvailableChampions().get(random3);
      fillbuttons();
	  
	  
	  
	  
  }
	public void fillbuttons() {
//		if(button1!=null) {
//			this.remove(button1);
//			this.remove(button2);
//			this.remove(button3);
//		}
		if(lPane!=null)
			this.remove(lPane);
		lPane = new JLayeredPane();
		lPane.setOpaque(true);
		lPane.setBackground(Color.black);
		this.setLayout(new GridLayout());
		button1=new JButton("name "+firstChampion.getName()+"   "+firstChampion.getCurrentHP()+"");
		button1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				firstChampion.setCurrentHP(firstChampion.getCurrentHP()-500);
				if(firstChampion.getCurrentHP()==0)
					button1.setEnabled(false);
				fillbuttons();
			}
		});
	//	button1.setBackground(Color.black);
		button2=new JButton("name "+secondChampion.getName()+"   "+secondChampion.getCurrentHP()+"");
		button2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				secondChampion.setCurrentHP(secondChampion.getCurrentHP()-500);
				if(secondChampion.getCurrentHP()==0)
					button2.setEnabled(false);
				fillbuttons();
			}
		});
		button3=new JButton("name "+thirdChampion.getName()+"   "+thirdChampion.getCurrentHP()+"");
		button3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				thirdChampion.setCurrentHP(thirdChampion.getCurrentHP()-500);
				if(thirdChampion.getCurrentHP()==0)
					button3.setEnabled(false);
				fillbuttons();
				
			}
		});
		button1.setBounds(0,0,1400,800/3);
		button2.setBounds(0,800/3,1400,800/3);
		button3.setBounds(0,1600/3,1400,800/3);

		
		lPane.add(button1);
		lPane.add(button2);
		lPane.add(button3);
		this.add(lPane);
		revalidate();
		repaint();
		
	}
	
}
