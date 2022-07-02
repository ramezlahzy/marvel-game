package views;

import java.awt.AWTKeyStroke;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.ProcessHandle.Info;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicScrollPaneUI;

import org.junit.validator.PublicClassValidator;
import org.w3c.dom.Text;
import org.w3c.dom.css.Counter;

import controller.Control;
import engine.Game;
import engine.Player;
import exceptions.GameActionException;
import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.world.Champion;

public class secondview extends JFrame implements ActionListener {
	private Control control;
	private Counter colorCounter;
	private JLayeredPane lpane;
	private JPanel championsboard;
	private JPanel selecter;
	private ArrayList<String> labelselecter;
	private JLayeredPane selectedpanel;
	private int counter;
	private JLabel choosewho;
	private JPanel selected;
	private Champion currentChampion;
	private JPanel infoJPanel;
	private JButton ok;
    private boolean computerplayermood;
	public secondview(Control control,boolean computerplayermood) throws IOException {
		this.computerplayermood=computerplayermood;
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		setResizable(false);
		// this.setLayeredPane(null);
		labelselecter = new ArrayList<String>();
		ImageIcon backgroundImage = new ImageIcon("backgroundthird.jpg");
		JLabel back = new JLabel(backgroundImage);
		this.setLayout(new BorderLayout());
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		JPanel panel1 = new JPanel();
		panel1.add(back, BorderLayout.SOUTH);
		panel1.setBackground(Color.black);
		// panel1.setOpaque(true);
		String label1 = control.getGame().getFirstPlayer().getName() + ",    \n" + "please choose leader";
		String label2 = control.getGame().getFirstPlayer().getName() + ",   \n" + "please choose first champion";
		String label3 = control.getGame().getFirstPlayer().getName() + ",   \n" + "please choose second champion";
		String label4 = control.getGame().getSecondPlayer().getName() + ",   \n" + "please choose leader";
		String label5 = control.getGame().getSecondPlayer().getName() + ",   \n" + "please choose first champion";
		String label6 = control.getGame().getSecondPlayer().getName() + ",   \n" + "please choose second chmapion";
		labelselecter.add(label1);
		labelselecter.add(label2);
		labelselecter.add(label3);
		labelselecter.add(label4);
		labelselecter.add(label5);
		labelselecter.add(label6);
		this.control = control;
		lpane = new JLayeredPane();
		panel1.setOpaque(true);
		lpane.setBackground(Color.black);
		lpane.setOpaque(true);
		selecter = new JPanel();
		selecter.setBounds(950, 0, 300, 50);
		choosewho = new JLabel(labelselecter.get(counter));
		choosewho.setFont(new Font(labelselecter.get(counter), Font.BOLD, 17));
		choosewho.setForeground(Color.white);
		choosewho.setBorder(null);
		choosewho.setOpaque(false);
		selecter.add(choosewho);
		selecter.setBorder(null);
		selecter.setOpaque(false);
		lpane.add(selecter, JLayeredPane.DRAG_LAYER);
		setLocation(new Point(125, 45));
		setSize(1300, 770);
		add(lpane, BorderLayout.CENTER);
		selected = new JPanel(new GridLayout(3, 1));
		selected.setBackground(Color.black);
		selected.setBounds(950, 70, 200, 350);
		revalidate();
		repaint();
		// selected.add(new JButton("OFFF"));

		lpane.add(selected, JLayeredPane.DRAG_LAYER);
		selected.setOpaque(true);
		// this.add(selected);
		filchampion();
		selected.setOpaque(false);
		panel1.setBounds(0, 100, 1300, 770 + 45);

		lpane.add(panel1, JLayeredPane.DEFAULT_LAYER);

		revalidate();
		repaint();
	}

	private void putokbutton() {
		if (ok != null) {
			lpane.remove(ok);
			// this.remove(ok);
		}
		ok = new JButton();
		ok.setFocusable(false);
		ok.setText("SELECT");
		ok.setBounds(1100, 500, 100, 100);
		ok.setBackground(Color.green);
		ok.setOpaque(true);
		lpane.add(ok, JLayeredPane.DRAG_LAYER);
		// this.add(ok);
		;
		ok.addActionListener(this);

	}

	public void filchampion() {
		if (championsboard != null) {
			lpane.remove(championsboard);
			// this.remove(championsboard);
		}
		championsboard = new JPanel(new GridLayout(4, 4));
		lpane.add(championsboard, JLayeredPane.DRAG_LAYER);
		// add(championsboard);
		championsboard.setVisible(true);
		championsboard.setBounds(150, 10, 800, 700);
		championsboard.setOpaque(true);
		for (Champion c : control.getGame().getAvailableChampions()) {
			JButton button = new JButton();
			button.setFocusable(false);
			button.setText(c.getName());
			ImageIcon backgroundImage = new ImageIcon(c.getName() + ".png");
			button.setBackground(Color.white);
			button.setForeground(Color.white);
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if(counter>=3&&computerplayermood)
					{
						viewcomputerplayerexeptionselecter();
						return;
					}
					putokbutton();
					// ok.setBackground(Color.black);
					JButton button = (JButton) e.getSource();
					currentChampion = getChampion(button.getText());
					JTextArea infoArea = new JTextArea(getChampion(button.getText()).toString());
					for(Ability a:getChampion(button.getText()).getAbilities())
						infoArea.setText(infoArea.getText()+"\n"+a.toString2());
					// infoArea.setBackground(Color.black);
					if (infoJPanel != null) {
						lpane.remove(infoJPanel);
						// remove(infoJPanel);
					}
					infoJPanel = new JPanel();
					infoJPanel.setFont(new Font("", Font.BOLD, 12));
					infoJPanel.setBounds(5, 50, 190, 800);
					infoJPanel.add(infoArea);
					infoJPanel.setForeground(Color.white);
					infoJPanel.setBackground(Color.black);
					infoJPanel.setBorder(null);
					infoJPanel.setOpaque(false);
					infoArea.setBorder(null);
					infoArea.setOpaque(false);
					lpane.add(infoJPanel, JLayeredPane.DRAG_LAYER);
					infoArea.setBackground(Color.black);
                    infoArea.setForeground(Color.white);
					infoArea.setFocusable(false);
					infoArea.setEditable(false);
				}
			});
			button.setIcon(backgroundImage);
			button.setBackground(Color.black);
			button.setBorder(null);
			button.setBorderPainted(false);
			button.setContentAreaFilled(false);
			button.setOpaque(false);
			championsboard.add(button);
			// add(new JPanel());

		}

		championsboard.setBackground(Color.black);

		championsboard.setBorder(null);
		championsboard.setOpaque(false);

		control.getGame().placeChampions();
		revalidate();
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(counter>=3&&computerplayermood)
		{
			viewcomputerplayerexeptionselecter();
			return;
		}
		ok.setBackground(Color.black);
		if (currentChampion == null)
			return;
		Champion selectedChampion = currentChampion;
		currentChampion = null;
		if (selecter != null) {
			lpane.remove(selecter);
			// remove(selecter);
		}
		selecter = new JPanel();
		if (++counter < 6) {
			choosewho = new JLabel(labelselecter.get(counter));
			choosewho.setFont(new Font(labelselecter.get(counter), Font.BOLD, 17));
			TextArea textArea = new TextArea(labelselecter.get(counter));
			textArea.setForeground(Color.WHITE);
			choosewho.add(textArea);
			choosewho.setForeground(Color.white);
			// choosewho.setBackground(Color.black);
			choosewho.setBorder(null);
			choosewho.setOpaque(false);
			// textArea.setBackground(Color.white);

		}
	
		System.out.println(counter);
		if (4 == counter) {
			lpane.remove(selected);
			this.remove(selected);
			
			selected = new JPanel(new GridLayout(3, 1));
			selected.setBackground(Color.black);
			selected.setBounds(950, 100, 200, 350);
			lpane.add(selected, JLayeredPane.DRAG_LAYER);
			
			// this.add(selected);
		}
		selecter.add(choosewho);
		selecter.setBorder(null);
		selecter.setOpaque(false);
		lpane.add(selecter, JLayeredPane.DRAG_LAYER);
		// this.add(selecter);
		// selecter.setBackground(Color.black);
		selecter.setBounds(950, 0, 300, 50);

		control.getGame().getAvailableChampions().remove(selectedChampion);
		if (counter <= 3) {
			control.getGame().getFirstPlayer().getTeam().add(selectedChampion);
			if (counter == 1) {
				control.getGame().getFirstPlayer().setLeader(selectedChampion);
			}
		} else {
			control.getGame().getSecondPlayer().getTeam().add(selectedChampion);
			if (counter == 4) {
				control.getGame().getSecondPlayer().setLeader(selectedChampion);
			}
		}

		filchampion();
		fillselected(selectedChampion);
		revalidate();
		repaint();
		if (counter == 6) {
			try {
				control.tothird(computerplayermood);
			} catch (GameActionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(counter==3)
			if(computerplayermood) {
				System.out.println("computer player");
				computerselectedmood();
			
		//	return;
			}
	}
	public void viewcomputerplayerexeptionselecter() {
		JOptionPane.showMessageDialog(this, "please don't interrupt me ,ana lsa ba5tar");
	}
	public void viewcomputerplayerexeptionmouse() {
		JOptionPane.showMessageDialog(this, "you already a5tart yabny");
	}
	public Champion getChampion(String s) {
		// System.out.println(s);
		for (Champion c : control.getGame().getAvailableChampions())
			if (c.getName().equals(s))
				return c;
		return null;
	}

	public void fillselected(Champion c) {
		// lpane.remove(championsboard);

		ImageIcon backgroundImage = new ImageIcon(c.getName() + ".png");
		JLabel back = new JLabel(backgroundImage);
		TextArea infoArea = new TextArea(c.toString());
		selected.add(back);
		revalidate();
		repaint();
	}

	
	public void computerselectedmood() {
		int random=(int)(control.getGame().getAvailableChampions().size()*Math.random());
		ok.setBackground(Color.black);
		Champion selectedChampion = control.getGame().getAvailableChampions().remove(random);
		System.out.println(selectedChampion);
		currentChampion = null;
		if (selecter != null) {
			lpane.remove(selecter);
			// remove(selecter);
		}
		selecter = new JPanel();
		if (++counter < 6) {
			choosewho = new JLabel(labelselecter.get(counter));
			choosewho.setFont(new Font(labelselecter.get(counter), Font.BOLD, 17));
			TextArea textArea = new TextArea(labelselecter.get(counter));
			textArea.setForeground(Color.WHITE);
			choosewho.add(textArea);
			choosewho.setForeground(Color.white);
			// choosewho.setBackground(Color.black);
			choosewho.setBorder(null);
			choosewho.setOpaque(false);
			// textArea.setBackground(Color.white);

		}
		if (4 == counter) {
			lpane.remove(selected);
			this.remove(selected);
			selected = new JPanel(new GridLayout(3, 1));
			selected.setBackground(Color.black);
			selected.setBounds(950, 100, 200, 350);
			lpane.add(selected, JLayeredPane.DRAG_LAYER);
			// this.add(selected);
		}
		selecter.add(choosewho);
		selecter.setBorder(null);
		selecter.setOpaque(false);
		lpane.add(selecter, JLayeredPane.DRAG_LAYER);
		// this.add(selecter);
		// selecter.setBackground(Color.black);
		selecter.setBounds(950, 0, 300, 50);

		control.getGame().getAvailableChampions().remove(selectedChampion);
		if (counter <= 3) {
			control.getGame().getFirstPlayer().getTeam().add(selectedChampion);
			if (counter == 1) {
				control.getGame().getFirstPlayer().setLeader(selectedChampion);
			}
		} else {
			control.getGame().getSecondPlayer().getTeam().add(selectedChampion);
			if (counter == 4) {
				control.getGame().getSecondPlayer().setLeader(selectedChampion);
			}
		}

		filchampion();
		fillselected(selectedChampion);
		revalidate();
		repaint();
		if (counter == 6) {
			try {
				control.tothird(computerplayermood);
				printtwoteam();
				return;
			} catch (GameActionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		final int[] counter = new int[] { 0 };
		Timer timer = new Timer(20, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (counter[0]++ >= 50) {
					computerselectedmood();
				    ((Timer)e.getSource()).stop();	
				}

			}
		});
		timer.start();
	}
	public void printtwoteam() {
		System.out.println(control.getGame().getFirstPlayer().getName());
		for(Champion c:control.getGame().getFirstPlayer().getTeam())
			System.out.println(c.getName()+"   ");
		System.out.println();
		System.out.println(control.getGame().getSecondPlayer().getName());
		for(Champion c:control.getGame().getSecondPlayer().getTeam())
			System.out.println(c.getName()+"   ");
	}
	public Control getControl() {
		return control;
	}

	public void setControl(Control control) {
		this.control = control;
	}

}
