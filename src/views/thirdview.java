package views;

import javax.management.monitor.GaugeMonitor;
import javax.naming.NameAlreadyBoundException;
import javax.print.attribute.standard.JobPriority;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.text.InternationalFormatter;
import javax.xml.crypto.dsig.XMLSignature.SignatureValue;

import controller.Control;
import controller.computerplayer;
import controller.virtualgame;
import engine.Game;
import engine.Player;
import engine.PriorityQueue;
import exceptions.ChampionDisarmedException;
import exceptions.GameActionException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.abilities.CrowdControlAbility;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.effects.Effect;
import model.effects.EffectType;
import model.world.Champion;
import model.world.Cover;
import model.world.Damageable;
import model.world.Direction;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.IntPredicate;

public class thirdview extends JFrame implements ActionListener, KeyListener, listener {

	private Control control;
	private Game game;
	private Player firstPlayer;
	private Player secondPlayer;
	private JLayeredPane lpane;
	private JPanel board;
	private JPanel selectedchampion;
	private JLayeredPane turnorderJPanel;
	private Ability selectedAbility;
	private JPanel photoofcurrentchampion;
	private boolean ifweneedcast;
	private JButton ifwecastButton;
	private Ability choosedabilityforcast;
	private ArrayList<JButton> firstteamButtons;
	private ArrayList<JButton> secondteamButtons;
	private boolean computerplayernow;
	private int difficulty;
	private JPanel turnPanel;
	private JPanel nameJPanel;
	private JButton useleaderButton;
	private JButton  nameplayer;
	/**
	 * @return the computerplayernow
	 */
	public boolean isComputerplayernow() {
		return computerplayernow;
	}

	/**
	 * @param computerplayernow the computerplayernow to set
	 */
	public void setComputerplayernow(boolean computerplayernow) {
		this.computerplayernow = computerplayernow;
	}

	private boolean computerplayermood;

	public thirdview(Control control, boolean computerplayermood, int dif) throws GameActionException {
		difficulty = dif;
		firstteamButtons = new ArrayList<JButton>();
		secondteamButtons = new ArrayList<JButton>();
		this.setLayout(new BorderLayout());
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.computerplayermood = computerplayermood;

		File file = new File("basicsound.wav");
		try {
			Clip clip = AudioSystem.getClip();
			// clip.open(AudioSystem.getAudioInputStream(file));
			clip.loop(44444);

			// clip.start();
		} catch (Exception e1) {
			// TODO: handle exception
		}
		this.control = control;
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.addKeyListener(this);
		game = control.getGame();
		firstPlayer = game.getFirstPlayer();
		secondPlayer = game.getSecondPlayer();
		game.endTurn();

//		   if(computerplayermood)
//	        	if(firstPlayer.getTeam().contains(game.getCurrentChampion())) {
//	        		computerplayernow=true;
//	        	    System.out.println("computer will play");	
//	        	}

		setVisible(true);
		reload();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		if (firstPlayer.getTeam().contains(game.getCurrentChampion()) && computerplayermood) {
			setComputerplayernow(true);
			new computerplayer(game, this, difficulty);
		}
	}

	private void reload() throws GameActionException {
		ImageIcon backgroundImage = new ImageIcon("backgroundthird9.jpg");
		JLabel back = new JLabel(backgroundImage);
		JPanel panel1 = new JPanel();
		panel1.add(back, BorderLayout.SOUTH);
		panel1.setBackground(Color.black);
		panel1.setBounds(0, 0, 1650, 1200);
		ImageIcon backgroundImage1 = new ImageIcon("board.jpg");
		JLabel back1 = new JLabel(backgroundImage1);
		JPanel panel11 = new JPanel();
		panel11.add(back1, BorderLayout.SOUTH);
		panel11.setBackground(Color.gray);
		panel11.setBounds(450, 80, 800, 500);

		lpane = new JLayeredPane();
		lpane.setOpaque(true);
		lpane.setBackground(Color.black);
		lpane.add(panel1, Integer.valueOf(0));
		lpane.add(panel11, Integer.valueOf(1));
		fillturnorder();
		add(lpane, BorderLayout.CENTER);
		fillboard();
		selectedchampion = new JPanel();
		setfirstplayerteam();
		setsecondplayerteam();
		fillpriorityqueueturn();
	}

	public void fillpriorityqueueturn() {
		if (turnPanel != null)
			lpane.remove(turnPanel);
		turnPanel = new JPanel();
		turnPanel.setLayout(new GridLayout(1, 6));
		PriorityQueue priorityQueue = game.getTurnOrder().getvirsual();
		while (!priorityQueue.isEmpty()) {
			Champion champion = (Champion) priorityQueue.remove();
			JTextArea textArea = new JTextArea(champion.getName());
			textArea.setOpaque(false);
			textArea.setBackground(Color.black);
			textArea.setForeground(Color.white);
			textArea.setFont(new Font("", 22, 20));
			textArea.setEditable(false);
			textArea.setFocusable(false);
			turnPanel.add(textArea);
			System.out.println("texarea");
		}
		turnPanel.setBackground(Color.white);
		turnPanel.setOpaque(false);
		turnPanel.setBounds(450, 650, 800, 50);
		turnPanel.setForeground(Color.white);
		lpane.add(turnPanel, Integer.valueOf(2));
		revalidate();
		repaint();
	}

	public void viewcomputerplayerexeption() {
		JOptionPane.showMessageDialog(this, "please don't interrupt me ,ana lsa bfkr");
	}

	public void fillturnorder() {

		ImageIcon backgroundImage2 = new ImageIcon("board.jpg");
		JLabel back2 = new JLabel(backgroundImage2);
		JPanel panel1 = new JPanel();

		panel1.add(back2, BorderLayout.SOUTH);
		panel1.setBackground(Color.black);
		panel1.setBounds(1350, 100, 200, 600);
		// lpane.add(panel1, JLayeredPane.DEFAULT_LAYER);
		if (turnorderJPanel != null)
			lpane.remove(turnorderJPanel);
		turnorderJPanel = new JLayeredPane();
		turnorderJPanel.setBorder(null);
		turnorderJPanel.setOpaque(false);
		turnorderJPanel.setLayout(new GridLayout(6, 1));
		turnorderJPanel.setBounds(1350, 100, 200, 600);
		lpane.add(turnorderJPanel, Integer.valueOf(2));
		Champion c = game.getCurrentChampion();
		revalidate();
		repaint();
		// ImageIcon backgroundImage = new ImageIcon("Captain America.png");
		ImageIcon backgroundImage = new ImageIcon(c.getName() + ".png");
		JLabel back = new JLabel(backgroundImage);
		if (photoofcurrentchampion != null)
			lpane.remove(photoofcurrentchampion);
		photoofcurrentchampion = new JPanel();
		photoofcurrentchampion.setBorder(null);
		photoofcurrentchampion.setOpaque(false);
		photoofcurrentchampion.add(back);
		lpane.add(photoofcurrentchampion, Integer.valueOf(2));
		photoofcurrentchampion.setBounds(1300, 0, 250, 100);
		photoofcurrentchampion.setBackground(Color.black);
		JPanel effectsJPanel = geteffects();
		effectsJPanel.setBorder(null);
		turnorderJPanel.add(effectsJPanel);
		turnorderJPanel.setBorder(null);
		turnorderJPanel.setOpaque(false);
		getabilities(turnorderJPanel);
		revalidate();
		repaint();

	}

	private String getspecficString(Ability ability) {
		if (ability instanceof HealingAbility)
			return "Healing amount=" + ((HealingAbility) ability).getHealAmount();
		if (ability instanceof DamagingAbility)
			return "damage amount=" + ((DamagingAbility) ability).getDamageAmount();
		if (ability instanceof CrowdControlAbility)
			return "Effect=" + ((CrowdControlAbility) ability).getEffect();
		return null;
	}

	private void getabilities(JLayeredPane turnorderJPanel) {
		// JPanel jPanel=new JPanel();
		// jPanel.setLayout(new GridLayout(6,1));

		for (Ability e : game.getCurrentChampion().getAbilities()) {

			JPanel abilityJPanel = new JPanel();
			abilityJPanel.setLayout(new GridLayout(1, 1));
			String allString = "";
			allString += "name of ability: " + e.getName() + "\n";
			allString += "manaCost: " + e.getManaCost() + "\n";
			allString += "baseCooldown: " + e.getCurrentCooldown() + "\n";
			allString += "castRange: " + e.getCastRange() + "\n";
			allString += "castArea: " + e.getCastArea() + "\n";
			allString += getspecficString(e) + "\n";
			allString += "requiredActionPoints: " + e.getRequiredActionPoints() + "\n" + "\n" + "\n" + "\n";
			JTextArea infoArea = new JTextArea(allString);

			infoArea.setBackground(new Color(0x123456));
			infoArea.setBackground(Color.black);
			infoArea.setFont(new Font("", 22, 9));
			JButton abilitButton = new JButton();
			abilitButton.setFocusable(false);
			abilitButton.setText("SELECT");
			// abilityJPanel.add(button);
			// abilityJPanel.add(infoArea);
			abilityJPanel.setForeground(Color.white);
			infoArea.setEditable(false);
			infoArea.setFocusable(false);
			turnorderJPanel.add(abilityJPanel);
			// abilityJPanel.add(abilitButton);
			infoArea.setForeground(Color.white);
			infoArea.setBorder(null);
			infoArea.setOpaque(false);
			abilityJPanel.add(infoArea);
			abilityJPanel.setLayout(new GridLayout(1, 1));
			abilityJPanel.setForeground(Color.white);
			abilityJPanel.setBorder(null);
			abilityJPanel.setOpaque(false);
			revalidate();
			repaint();
			// abilityJPanel.setBounds(1000, 600, 50, 400);
		}
		ifwecastButton = new JButton();
		ifwecastButton.setFocusable(false);
		ifwecastButton.setText("CAST     from 1 to " + "\n" + game.getCurrentChampion().getAbilities().size()
				+ "                                                                     ");
		ifwecastButton.setBackground(Color.black);
		ifwecastButton.setFont(new Font("", 22, 15));
		ifwecastButton.setForeground(Color.white);
		turnorderJPanel.add(ifwecastButton);
		ifwecastButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (computerplayernow) {
					viewcomputerplayerexeption();
					return;
				}
				ifwecastButton.setBackground(new Color(0x123456));
				ifweneedcast = true;
			}
		});

		// return jPanel;

	}

	private JPanel geteffects() {
		String s = "APPLIED EFFECTS" + '\n';
		for (Effect e : game.getCurrentChampion().getAppliedEffects()) {
			// System.out.print(e);
			s += e;
		}
		// s+="\n"+"Abbilities";
		JTextArea infoArea = new JTextArea(s);
		infoArea.setBorder(null);
		infoArea.setForeground(Color.white);
		infoArea.setOpaque(false);
		infoArea.setBackground(Color.black);
		infoArea.setEditable(false);
		infoArea.setFocusable(false);
		infoArea.setFont(new Font("", 22, 7));
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new GridLayout(1, 1));
		jPanel.add(infoArea);
		jPanel.setForeground(Color.white);
		jPanel.setBorder(null);
		jPanel.setOpaque(false);
		return jPanel;

	}

	public void fillboard() {
		if (board != null)
			lpane.remove(board);
		board = new JPanel();
		// board.setBorder(null);
		// board.setOpaque(false);
		board.setLayout(new GridLayout(5, 5));
		// board.setBackground(Color.black);
		board.setBounds(450, 80, 800, 500);
		// board.setOpaque(false);
		// board.setBackground(Color.black);
		lpane.add(board, Integer.valueOf(2));

		for (Object[] f : game.getBoard())
			for (Object o : f) {
				if (o instanceof Cover) {
					getforcover((Cover) o, board);
				}
				if (o instanceof Champion) {
					// System.out.print("ll");
					JButton button = getforchampion((Champion) o, board);
//					if(firstPlayer.getTeam().contains((Champion)o)) {
//						firstteamButtons.add(button);
//					}
//					else {
//						secondteamButtons.add(button);				
//					}
				}
				if (o == null) {
					board.add(getfornull());

				}
			}
	}

	private Cover getCover(String s) {
		int x = Integer.parseInt("" + s.charAt(0));
		int y = Integer.parseInt("" + s.charAt(1));
		return (Cover) game.getBoard()[x][y];
	}

	public void getforcover(Cover c, JPanel board2) {

		JButton button = new JButton();
		button.setFocusable(false);
		button.setName(c.getLocation().x + "" + c.getLocation().y);
		ImageIcon backgroundImage = new ImageIcon("groot.png");
		button.setIcon(backgroundImage);
		button.addActionListener(this);
		button.setBorder(null);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setOpaque(false);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (computerplayernow) {
					viewcomputerplayerexeption();
					return;
				}
				
				JButton button = (JButton) e.getSource();

				Cover ccCover = getCover(button.getName());
				JTextArea infoArea = new JTextArea("CurrentHP= " + ccCover.getCurrentHP() + "");
				revalidate();
				repaint();
				infoArea.setForeground(Color.white);
				infoArea.setBackground(Color.black);
				// System.out.println(ccCover.getCurrentHP());
				infoArea.setEditable(false);
				infoArea.setFocusable(false);
				infoArea.setOpaque(false);
				lpane.remove(selectedchampion);
				selectedchampion = new JPanel();
				selectedchampion.setLayout(new GridLayout(1, 1));
				selectedchampion.add(infoArea);
				selectedchampion.setOpaque(false);
				selectedchampion.setBounds(0, 0, 200, 100);
				if(ifweneedcast) {
					try {
					game.castAbility(choosedabilityforcast,ccCover.getLocation().x,ccCover.getLocation().y);
					choosedabilityforcast = null;
					ifweneedcast = false;
					ifwecastButton.setBackground(Color.black);
					fillboard();
					fillturnorder();
					fillpriorityqueueturn();
					revalidate();
					repaint();}
					catch (Exception ee) {
                        JOptionPane.showMessageDialog(lpane, ee.getMessage());
						// TODO: handle exception
					}
				}
				lpane.add(selectedchampion, Integer.valueOf(2));
			}
		});
		JPanel twiseJPanel = new JPanel();
		twiseJPanel.setLayout(null);
		JProgressBar parBar = new JProgressBar();
		parBar.setValue(c.getCurrentHP() / 10);
		parBar.setSize(160, 20);
		parBar.setBounds(0, 95, 140, 7);
		button.setBounds(0, 0, 160, 93);
		parBar.setForeground(Color.cyan);
		twiseJPanel.add(button);
		parBar.setName("c.getLocation().x" + "" + "c.getLocation().y");
		twiseJPanel.add(parBar);
		parBar.setBorder(null);
		twiseJPanel.setOpaque(false);
		parBar.setOpaque(false);
		board2.add(twiseJPanel);

	}

	public JButton getforchampion(Champion c, JPanel board2) {

		JButton button = new JButton();
		if (firstPlayer.getTeam().contains(c)) {
			firstteamButtons.add(button);
		} else {
			secondteamButtons.add(button);
		}
		button.addActionListener(this);
		button.setFocusable(false);
		button.setName(c.getName());
//		button.setBorder(null);
//		button.setBorderPainted(false);
//		button.setContentAreaFilled(false);
		// button.setOpaque(false);
		button.setBackground(Color.black);
		button.setForeground(Color.white);
		ImageIcon backgroundImage = new ImageIcon(c.getName() + ".png");
		//
		
//		ImageIcon backgroundImage = new ImageIcon("1.jpg");

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (computerplayernow) {
					viewcomputerplayerexeption();
					return;
				}
				if (selectedchampion != null)
					lpane.remove(selectedchampion);
				JButton button = (JButton) e.getSource();
				Champion champion = getChampion(button.getName());
				JTextArea infoArea = new JTextArea(champion.toString());
				infoArea.setFont(new Font("", Font.BOLD, 10));
				infoArea.setForeground(Color.white);
				// infoArea.setBackground(Color.black);
				infoArea.setEditable(false);
				infoArea.setFocusable(false);
				infoArea.setOpaque(false);
				selectedchampion = new JPanel();
				selectedchampion.add(infoArea);
				selectedchampion.setLayout(new GridLayout(1, 1));
				selectedchampion.setForeground(Color.white);
				selectedchampion.setBounds(0, 0, 200, 300);
				selectedchampion.setOpaque(false);
				if(ifweneedcast) {
					try {
					game.castAbility(choosedabilityforcast,champion.getLocation().x,champion.getLocation().y);
					choosedabilityforcast = null;
					ifweneedcast = false;
					ifwecastButton.setBackground(Color.black);
					fillboard();
					fillturnorder();
					fillpriorityqueueturn();
					revalidate();
					repaint();}
					catch (Exception ee) {
                        JOptionPane.showMessageDialog(lpane, ee.getMessage());
						// TODO: handle exception
					}
				}
				lpane.add(selectedchampion, Integer.valueOf(2));
				revalidate();
				repaint();
			}
		});
		button.setBorder(null);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setOpaque(false);
		button.setIcon(backgroundImage);
		// board2.add(button);
//		board2.setBorder(null);
		board2.setOpaque(false);

		JPanel twiseJPanel = new JPanel();
		twiseJPanel.setLayout(null);
		JProgressBar parBar = new JProgressBar();
		parBar.setValue(c.getCurrentHP() * 100 / c.getMaxHP());
		parBar.setSize(160, 20);
		parBar.setBounds(0, 95, 140, 7);
		button.setBounds(0, 0, 160, 93);
		parBar.setForeground(Color.cyan);
		twiseJPanel.add(button);
		parBar.setName(c.getName());
		twiseJPanel.add(parBar);
		parBar.setBorder(null);
		twiseJPanel.setOpaque(false);
		parBar.setOpaque(false);
		board2.add(twiseJPanel);
		return button;
	}

	public Champion getChampion(String s) {
		// System.out.println(s);
		for (Champion c : firstPlayer.getTeam())
			if (c.getName().equals(s))
				return c;

		for (Champion c : secondPlayer.getTeam())
			if (c.getName().equals(s))
				return c;
		return null;
	}

	public JButton getfornull() {
		ImageIcon backgroundImage = new ImageIcon("empty.jpg");
		JButton button = new JButton();
		// button.setIcon(backgroundImage);
		button.setBackground(Color.black);
		// button.setBackground(Color.white);
		button.setOpaque(true);
		button.setFocusable(false);
		button.addActionListener(this);
		button.setBorder(null);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setOpaque(false);
		return button;
	}

	private Damageable searchforDamageable(String s) {
		// System.out.println(s);
		for (Champion c : game.getFirstPlayer().getTeam())
			if (c.getName().equals(s))
				return c;
		for (Champion c : game.getSecondPlayer().getTeam())
			if (c.getName().equals(s))
				return c;
		return (Damageable) game.getBoard()[Integer.parseInt(s.charAt(0) + "")][Integer.parseInt(s.charAt(1) + "")];
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (computerplayernow) {
			viewcomputerplayerexeption();
			return;
		}
		File file = new File("buttonsound.wav");
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
			clip.start();
		} catch (Exception e1) {
			// TODO: handle exception
		}

		JButton button = (JButton) e.getSource();
		if (button.getText().equals(""))
			return;
		Damageable damageable = searchforDamageable(button.getText());

		if (choosedabilityforcast != null && choosedabilityforcast.getCastArea() == AreaOfEffect.SINGLETARGET) {
			try {
				game.castAbility(choosedabilityforcast, damageable.getLocation().x, damageable.getLocation().y);
				ifweneedcast = false;
				this.ifwecastButton.setBackground(Color.black);
				fillboard();
				fillturnorder();
				revalidate();
				repaint();
			} catch (GameActionException | CloneNotSupportedException e1) {

				final JFrame exempionFrame = new JFrame();
				exempionFrame.setSize(100, 100);
				TextArea textArea = new TextArea();
				JLabel jLabel = new JLabel();
				jLabel.add(textArea);
				exempionFrame.add(jLabel);
				exempionFrame.setVisible(true);
				exempionFrame.setLocation(new Point(600, 300));
				exempionFrame.setSize(400, 200);
				JButton button1 = new JButton();
				button1.setText(e1.getMessage());
				exempionFrame.add(button1);
				button1.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (computerplayernow) {
							viewcomputerplayerexeption();
							return;
						}
						exempionFrame.setVisible(false);
						exempionFrame.setDefaultCloseOperation(exempionFrame.EXIT_ON_CLOSE);

					}
				});
			}
		}

	}

	private void setfirstplayerteam() {
		
		if(nameplayer!=null)
			lpane.remove(nameplayer);
		if(nameJPanel!=null)
			lpane.remove(nameJPanel);
		if(useleaderButton!=null)
			lpane.remove(useleaderButton);
		 useleaderButton = new JButton();
		useleaderButton.setFocusable(false);
		useleaderButton.setText("USE leader ability  ");
		useleaderButton.setBackground(new Color(0x123456));
		useleaderButton.setFont(new Font("", 22, 20));
		useleaderButton.setBorder(null);
		useleaderButton.setBorderPainted(false);
		useleaderButton.setForeground(Color.white);
		nameplayer = new JButton();
		nameplayer.setBorder(null);
		nameplayer.setBorderPainted(false);
		nameplayer.setContentAreaFilled(false);
		nameplayer.setOpaque(false);
		nameplayer.setFocusable(false);
		nameplayer.setText(game.getFirstPlayer().getName());
		nameplayer.setBackground(Color.GRAY);
		nameplayer.setForeground(Color.white);
		nameplayer.setFont(new Font("", 22, 20));

		useleaderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (computerplayernow) {
					viewcomputerplayerexeption();
					return;
				}
				JButton button2 = (JButton) e.getSource();

				if (game.getSecondPlayer().getTeam().contains(game.getCurrentChampion()))
					return;

				try {
					game.useLeaderAbility();
					button2.setBackground(Color.black);
					button2.setOpaque(false);
					button2.setContentAreaFilled(false);
					button2.setBorder(null);
					button2.setBorderPainted(false);
					button2.setContentAreaFilled(false);
					button2.setOpaque(false);
					button2.setFocusable(false);

				} catch (GameActionException e1) {
					// JOptionPane.showMessageDialog(, e1.getMessage());
					final JFrame exempionFrame = new JFrame();
					exempionFrame.setSize(100, 100);
					TextArea textArea = new TextArea();
					JLabel jLabel = new JLabel();
					jLabel.add(textArea);
					exempionFrame.add(jLabel);
					exempionFrame.setVisible(true);
					exempionFrame.setLocation(new Point(600, 300));
					exempionFrame.setSize(400, 200);
					JButton button = new JButton();
					button.setText(e1.getMessage());
					exempionFrame.add(button);
					button.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							if (computerplayernow) {
								viewcomputerplayerexeption();
								return;
							}
							exempionFrame.setVisible(false);
							exempionFrame.setDefaultCloseOperation(exempionFrame.EXIT_ON_CLOSE);

						}
					});
				}
				;

			}
		});
		
		nameJPanel = new JPanel();
		nameJPanel.setLayout(new GridLayout(3, 1));
		nameJPanel.setBounds(0, 200, 200, 400);
		useleaderButton.setBounds(0, 600, 200, 100);
		nameplayer.setBounds(0, 700, 200, 100);
		;
		
		lpane.add(nameplayer, Integer.valueOf(2));
		lpane.add(nameJPanel, Integer.valueOf(2));
		lpane.add(useleaderButton, Integer.valueOf(2));
		for (Champion c : game.getFirstPlayer().getTeam())
			getforchampion(c, nameJPanel);

	}

	private void setsecondplayerteam() {

		JButton useleaderButton = new JButton();
		useleaderButton.setFocusable(false);
		useleaderButton.setText("USE leader ability  ");
		useleaderButton.setBackground(new Color(0x123456));
		useleaderButton.setFont(new Font("", 22, 20));
		useleaderButton.setBorder(null);
		useleaderButton.setBorderPainted(false);
		useleaderButton.setForeground(Color.white);
		JButton nameplayer = new JButton();
		nameplayer.setFocusable(false);
		nameplayer.setText(game.getSecondPlayer().getName());
		nameplayer.setBackground(Color.gray);
		nameplayer.setForeground(Color.white);
		nameplayer.setFont(new Font("", 22, 20));
		nameplayer.setBorder(null);
		nameplayer.setBorderPainted(false);
		nameplayer.setContentAreaFilled(false);
		nameplayer.setOpaque(false);

		useleaderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (computerplayernow) {
					viewcomputerplayerexeption();
					return;
				}
				JButton button2 = (JButton) e.getSource();

				if (game.getFirstPlayer().getTeam().contains(game.getCurrentChampion()))
					return;

				try {
					game.useLeaderAbility();
					button2.setBackground(Color.black);
					button2.setOpaque(true);
					button2.setBorder(null);
					button2.setBorderPainted(false);
					button2.setContentAreaFilled(false);
					button2.setOpaque(false);
					button2.setContentAreaFilled(false);
				} catch (GameActionException e1) {

					final JFrame exempionFrame = new JFrame();
					exempionFrame.setSize(100, 100);
					TextArea textArea = new TextArea();
					JLabel jLabel = new JLabel();
					jLabel.add(textArea);
					exempionFrame.add(jLabel);
					exempionFrame.setVisible(true);
					exempionFrame.setLocation(new Point(600, 300));
					exempionFrame.setSize(400, 200);
					JButton button = new JButton();
					button.setText(e1.getMessage());
					exempionFrame.add(button);
					button.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							if (computerplayernow) {
								viewcomputerplayerexeption();
								return;
							}
							exempionFrame.setVisible(false);
							exempionFrame.setDefaultCloseOperation(exempionFrame.EXIT_ON_CLOSE);

						}
					});
				}
				;

			}
		});
//		firstplayernameArea.setFocusable(false);
//		JLabel nameJLabel = new JLabel();
//		nameJLabel.add(firstplayernameArea);

		JPanel nameJPanel = new JPanel();
		nameJPanel.setLayout(new GridLayout(3, 1));
		// nameJPanel.add(nameButton);
//		nameJPanel.setBounds(440+250, 550+100, 600, 100);
//		useleaderButton.setBounds(220+250, 550+100, 220, 100);
//		nameplayer.setBounds(0+250, 550+100, 220, 100);;

		nameJPanel.setBounds(200, 200, 200, 400);
		useleaderButton.setBounds(200, 600, 200, 100);
		nameplayer.setBounds(200, 700, 200, 100);
		;
		lpane.add(nameplayer, Integer.valueOf(2));
		lpane.add(nameJPanel, Integer.valueOf(2));
		lpane.add(useleaderButton, Integer.valueOf(2));
		for (Champion c : game.getSecondPlayer().getTeam())
			getforchampion(c, nameJPanel);
		// System.out.println("a;ds");
		// add(nameJPanel);

	}

	private void setendturnbutton() {
		JButton button = new JButton();
		button.setFocusable(false);
		button.setText("endturn");
		ImageIcon backgroundImag = new ImageIcon("endturn.jpg");
		button.setIcon(backgroundImag);
		JPanel endturnbuttonJPanel = new JPanel();
		lpane.add(button, Integer.valueOf(2));
		button.setBounds(0, 400, 200, 180);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (computerplayernow) {
					viewcomputerplayerexeption();
					return;
				}
				if (game.checkGameOver() != null) {
					endtheandselectwinner();
					return;
				}
				game.endTurn();
				fillpriorityqueueturn();
				fillturnorder();
				revalidate();
				repaint();
			}
		});
	}

	public void endtheandselectwinner() {
		this.setVisible(false);
		Player winnerPlayer = game.checkGameOver();
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0,0,1370,730);
		
		JFrame frame = new JFrame();
		frame.setTitle("End Game");
		frame.add(layeredPane);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH );
		frame.setLayout(null);
		
		JLabel label1 = new JLabel();
		label1.setBounds(0,0,1375,735);
		ImageIcon icon = new ImageIcon("Thanos1.jpg");
		label1.setIcon(icon);
		layeredPane.add(label1 , Integer.valueOf(0));
		
		JLabel label = new JLabel("<html>Congratulation " + winnerPlayer.getName() +"<br/>You are The Winner</html>");
		label.setForeground(Color.yellow);
		label.setFont(new Font("MV Boli",Font.BOLD,50));
		label.setBounds(50,520,1375,140);
		layeredPane.add(label , Integer.valueOf(10));
		
		//frame.setResizable(false);
		frame.setVisible(true);
		

	}
	public void endtheandselectwinner1() {
		this.setVisible(false);
		Player winnerPlayer = game.checkGameOver();
		final JFrame exempionFrame = new JFrame();
		exempionFrame.setSize(100, 100);
		TextArea textArea = new TextArea();
		JLabel jLabel = new JLabel();
		jLabel.add(textArea);
		exempionFrame.add(jLabel);
		exempionFrame.setVisible(true);
		exempionFrame.setLocation(new Point(600, 300));
		exempionFrame.setSize(400, 200);
		JButton button = new JButton();
		button.setText("congrantulation " + winnerPlayer.getName() + " is the winner ,you deserve it");
		exempionFrame.add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (computerplayernow) {
					viewcomputerplayerexeption();
					return;
				}
				exempionFrame.setVisible(false);
				exempionFrame.setDefaultCloseOperation(exempionFrame.EXIT_ON_CLOSE);

			}
		});

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// System.out.println(1);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		// System.out.println(2);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (computerplayernow) {
			viewcomputerplayerexeption();
			return;
		}
		Direction direction = null;
		if (e.getKeyCode() == 73)
			direction = direction.UP;
		if (e.getKeyCode() == 75)
			direction = direction.DOWN;
		if (e.getKeyCode() == 74)
			direction = direction.LEFT;
		if (e.getKeyCode() == 76)
			direction = direction.RIGHT;
		if (direction != null)
			try {
				game.attack(direction);
				setfirstplayerteam();
				setsecondplayerteam();
				this.fillboard();
				// reload();
				lpane.remove(selectedchampion);

			} catch (GameActionException e1) {

//				final JFrame exempionFrame = new JFrame();
//				exempionFrame.setSize(100, 100);
//				TextArea textArea = new TextArea();
//				JLabel jLabel = new JLabel();
//				jLabel.add(textArea);
//				exempionFrame.add(jLabel);
//				exempionFrame.setVisible(true);
//				exempionFrame.setLocation(new Point(600, 300));
//				exempionFrame.setSize(400, 200);
//				JButton button = new JButton();
//				button.setText(e1.getMessage());
//				exempionFrame.add(button);
//				button.addActionListener(new ActionListener() {
//
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						exempionFrame.setVisible(false);
//						exempionFrame.setDefaultCloseOperation(exempionFrame.EXIT_ON_CLOSE);
//
//					}
//				});
				JOptionPane.showMessageDialog(this, e1.getMessage());
			}

		{
			Direction Directionformove = null;
			if (e.getKeyCode() == 38)
				Directionformove = direction.UP;
			if (e.getKeyCode() == 40)
				Directionformove = direction.DOWN;
			if (e.getKeyCode() == 37)
				Directionformove = direction.LEFT;
			if (e.getKeyCode() == 39)
				Directionformove = direction.RIGHT;
			if (Directionformove != null)
				try {
					game.move(Directionformove);
					// reload();
					fillboard();
				} catch (GameActionException e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage());
//					final JFrame exempionFrame = new JFrame();
//					exempionFrame.setSize(100, 100);
//					TextArea textArea = new TextArea();
//					JLabel jLabel = new JLabel();
//					jLabel.add(textArea);
//					exempionFrame.add(jLabel);
//					exempionFrame.setVisible(true);
//					exempionFrame.setLocation(new Point(600, 300));
//					exempionFrame.setSize(400, 200);
//					JButton button = new JButton();
//					button.setText(e1.getMessage());
//					exempionFrame.add(button);
//					button.addActionListener(new ActionListener() {
//
//						@Override
//						public void actionPerformed(ActionEvent e) {
//							exempionFrame.setVisible(false);
//							exempionFrame.setDefaultCloseOperation(exempionFrame.EXIT_ON_CLOSE);
//
//						}
//					});
				}
		}
		revalidate();
		repaint();

		if (e.getKeyCode() == 10) {
			if (game.checkGameOver() != null) {
				endtheandselectwinner();
				return;
			}
			// System.out.println("in real game
			// before"+game.getCurrentChampion().getName());
			game.endTurn();
			fillpriorityqueueturn();

//			//System.out.println("in real game after"+game.getCurrentChampion().getName());
			//System.out.println(game.getTurnOrder());
//			if(game.getFirstPlayer().getTeam().contains(game.getCurrentChampion()));
			revalidate();
			repaint();
//			if(firstPlayer.getTeam().contains(game.getCurrentChampion())) {
//        		computerplayernow=true;
//        	    System.out.println("computer will play");	
//        	}
//			if(computerplayernow)
//				new computerplayer(game, this);

			if (firstPlayer.getTeam().contains(game.getCurrentChampion()) && computerplayermood) {
				setComputerplayernow(true);
				new computerplayer(game, this, difficulty);
			}

			// this.printtwoteam();
			revalidate();
			repaint();
//			System.out.println(game.getTurnOrder());
			ifweneedcast = false;
			choosedabilityforcast = null;
			fillturnorder();
		}
		revalidate();
		repaint();

		if (ifweneedcast) {
			choosecast(e.getKeyCode());
		}
		// System.out.println("choosedability"+choosedabilityforcast);
		if (choosedabilityforcast != null && choosedabilityforcast.getCastArea() == AreaOfEffect.DIRECTIONAL) {
			Direction directionforability = null;
			if (e.getKeyCode() == 65)
				directionforability = direction.LEFT;
			if (e.getKeyCode() == 87)
				directionforability = direction.UP;
			if (e.getKeyCode() == 83)
				directionforability = direction.DOWN;
			if (e.getKeyCode() == 68)
				directionforability = direction.RIGHT;
			if (directionforability != null) {
				try {
					game.castAbility(choosedabilityforcast, directionforability);
					setfirstplayerteam();
					setsecondplayerteam();
					ifweneedcast = false;
					choosedabilityforcast = null;
					this.ifwecastButton.setBackground(Color.black);
					fillboard();
					fillturnorder();
					revalidate();
					repaint();
				} catch (GameActionException | CloneNotSupportedException e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage());
//					final JFrame exempionFrame = new JFrame();
//					exempionFrame.setSize(100, 100);
//					TextArea textArea = new TextArea();
//					JLabel jLabel = new JLabel();
//					jLabel.add(textArea);
//					exempionFrame.add(jLabel);
//					exempionFrame.setVisible(true);
//					exempionFrame.setLocation(new Point(600, 300));
//					exempionFrame.setSize(400, 200);
//					JButton button = new JButton();
//					button.setText(e1.getMessage());
//					exempionFrame.add(button);
//					button.addActionListener(new ActionListener() {
//
//						@Override
//						public void actionPerformed(ActionEvent e) {
//							exempionFrame.setVisible(false);
//							exempionFrame.setDefaultCloseOperation(exempionFrame.EXIT_ON_CLOSE);
//
//						}
//					});
				}
			}
		}

	}

	/**
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * @param game the game to set
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	/**
	 * @return the computerplayermood
	 */
	public boolean isComputerplayermood() {
		return computerplayermood;
	}

	/**
	 * @param computerplayermood the computerplayermood to set
	 */
	public void setComputerplayermood(boolean computerplayermood) {
		this.computerplayermood = computerplayermood;
	}

	public void printtwoteam() {
		System.out.println(control.getGame().getFirstPlayer().getName());
		for (Champion c : control.getGame().getFirstPlayer().getTeam())
			System.out.println(c.getName() + "   ");
		System.out.println();
		System.out.println(control.getGame().getSecondPlayer().getName());
		for (Champion c : control.getGame().getSecondPlayer().getTeam())
			System.out.println(c.getName() + "   ");
	}

	private void choosecast(int code) {
		code -= 97;
		Champion champion = game.getCurrentChampion();
		if (code < 0 || code >= champion.getAbilities().size())
			return;
		Ability ability = champion.getAbilities().get(code);
		choosedabilityforcast = ability;
		if (choosedabilityforcast.getCastArea() == AreaOfEffect.SELFTARGET
				|| choosedabilityforcast.getCastArea() == AreaOfEffect.SURROUND
				|| choosedabilityforcast.getCastArea() == AreaOfEffect.TEAMTARGET) {
			try {
				game.castAbility(choosedabilityforcast);
				choosedabilityforcast = null;
				ifweneedcast = false;
				ifwecastButton.setBackground(Color.black);
				fillboard();
				fillturnorder();
				fillpriorityqueueturn();
				revalidate();
				repaint();
			} catch (GameActionException | CloneNotSupportedException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage());
//				final JFrame exempionFrame = new JFrame();
//				exempionFrame.setSize(100, 100);
//				TextArea textArea = new TextArea();
//				JLabel jLabel = new JLabel();
//				jLabel.add(textArea);
//				exempionFrame.add(jLabel);
//				exempionFrame.setVisible(true);
//				exempionFrame.setLocation(new Point(600, 300));
//				exempionFrame.setSize(400, 200);
//				JButton button = new JButton();
//				button.setText(e1.getMessage());
//				exempionFrame.add(button);
//				button.addActionListener(new ActionListener() {
//
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						exempionFrame.setVisible(false);
//						exempionFrame.setDefaultCloseOperation(exempionFrame.EXIT_ON_CLOSE);
//
//					}
//				});
			}
		}
	}

	private int getcrosx(int x) {
		return 80 + 50 + x * 100;
	}

	private int getcrosy(int y) {
		return 450 + 80 + 160 * y;
	}

	private Rectangle getpositionofattackingRectangle() {

		Champion champion = game.getCurrentChampion();
		int x = champion.getLocation().x;
		int y = champion.getLocation().y;
		Rectangle rectangle = new Rectangle(280 + y * 220, 50 + x * 105, 50, 50);
		return rectangle;
	}

	private Rectangle getpositiontoRectangle(Object o, Direction d) {
		Champion champion = game.getCurrentChampion();
		int attackrange = champion.getAttackRange();
		int x = champion.getLocation().x;
		int y = champion.getLocation().y;
		if (o == null && d == Direction.UP)
			return new Rectangle(getcrosy(y) - 40, getcrosx(Math.max(x - attackrange, -1)) - 50, 160, 100);
		if (o == null && d == Direction.DOWN)
			return new Rectangle(getcrosy(y) - 40, getcrosx(Math.min(x + attackrange, 5)) - 50, 160, 100);
		if (o == null && d == Direction.RIGHT)
			return new Rectangle(getcrosy(y + attackrange) - 40, getcrosx(x) - 50, 160, 100);
		if (o == null && d == Direction.LEFT)
			return new Rectangle(getcrosy(y - attackrange) - 40, getcrosx(x) - 50, 160, 100);

		// System.out.println(o);
		Damageable damageable = (Damageable) o;
		x = damageable.getLocation().x;
		y = damageable.getLocation().y;
		Rectangle rectangle = new Rectangle(getcrosy(y) - 40, getcrosx(x) - 50, 160, 100);
		return rectangle;
	}

	@Override
	public void attack(Direction direction, Damageable target) {

		File file = new File("att.wav");
		// File file = new File("attackahmed.wav");

		try {
			Clip clip = AudioSystem.getClip();
			// clip.loop(33);
		//	clip.open(AudioSystem.getAudioInputStream(file));
		//	clip.start();
		} catch (Exception e1) {
		}

		final JPanel lPanel = new JPanel();

		lpane.add(lPanel, Integer.valueOf(3));
		// ImageIcon fireIcon = new ImageIcon("fireCaptain America.png");
		// ImageIcon fireIcon = new ImageIcon("fire"+game.getCurrentChampion() +".png");
		ImageIcon fireIcon = new ImageIcon("fire1.png");

		JLabel fireJLabel = new JLabel(fireIcon);
		fireJLabel.setBorder(null);
		fireJLabel.setOpaque(false);
		lPanel.add(fireJLabel);
		lPanel.setBorder(null);
		lPanel.setOpaque(false);
		lPanel.setBackground(Color.black);
		lPanel.setOpaque(false);
		Dimension size = lPanel.getPreferredSize();
		lPanel.setSize(100, 100);

		Point currentPoint = game.getCurrentChampion().getLocation();
		Rectangle from = new Rectangle(getcrosy(currentPoint.y) - 40, getcrosx(currentPoint.x) - 50, 160, 100);
		Rectangle to = getpositiontoRectangle(target, direction);
//         System.out.println(game.getCurrentChampion().getCondition());

		Animate animate = new Animate(lPanel, from, to, lpane);
		animate.delay = 40;
		animate.start();
		revalidate();
		repaint();
		// animation
//        final int[] x = {from.x};
//        final int[] y = {from.y};
//        final int[] z = {to.x};
//        final int[] w = {to.y};
//        final int[] time = {0};
//		final Direction direction2=direction;

		// lPanel.setLocation(x[0],y[0]);
//        lPanel.setLocation(new Point(x[0],y[0]));
//
//        

//        final int[] time1 = {0};
//       final Direction direction22=direction;
//    
//        javax.swing.Timer timer = new javax.swing.Timer(0, new ActionListener() {
//           
//        	@Override
//            public void actionPerformed(ActionEvent e) {
//            //    f.setVisible(false);
//                Timer timer1=(Timer)e.getSource();
//                //timer1.setDelay(0);
//                time1[0]++;
//                if(time1[0]==99)
//                	 time1[0]++;
//                if(x[0]==z[0]&&y[0]==w[0]) {
//                	lpane.remove(lPanel);
//                	revalidate();
//            		repaint();
//                    timer1.stop();
//                       
//                }
//
//                if(direction22==Direction.UP)
//                	y[0]--;
//                if(direction22==Direction.DOWN)
//                	y[0]++;
//                if(direction22==Direction.RIGHT)
//                	x[0]++;
//                if(direction22==Direction.LEFT)
//                	x[0]--;
//                lPanel.setLocation(x[0],y[0]);
//
//            }
//        });
//        timer.setInitialDelay(0);
		// Timer.setLogTimers(true);
		// timer.start();

	}

	private JPanel getPanel(Damageable damageable) {
		JPanel panel = new JPanel();
		ImageIcon imageIcon;
		if (damageable instanceof Champion) {
			imageIcon = new ImageIcon(((Champion) damageable).getName() + ".png");
		} else {
			imageIcon = new ImageIcon("groot.png");
		}
		JLabel label = new JLabel(imageIcon);
		panel.add(label);
		return panel;
	}

	public void death(Damageable damageable, int hight, int width) {
		fillboard();
		// System.out.println(damageable);
		File file = new File("shaft.wav");
		try {
			Clip clip = AudioSystem.getClip();
			// clip.loop(33);
			clip.open(AudioSystem.getAudioInputStream(file));
			clip.start();
		} catch (Exception e1) {
		}
		hight = getcrosx(hight);
		width = getcrosy(width);

		Rectangle from = new Rectangle(width - 90, hight - 60, 160, 100);
		Rectangle to = new Rectangle(width - 90, hight - 60, 0, 0);
		JPanel panel = getPanel(damageable);
		lpane.add(panel, Integer.valueOf(3));
		panel.setBorder(null);
		panel.setOpaque(false);
		Animate1 animate = new Animate1(panel, from, to, lpane);
		animate.delay = 0;
		animate.start();
		revalidate();
		repaint();

	}

	@Override
	public void castteamtarget(Player p1, boolean with) {
		ArrayList<JButton> targetsArrayList;
		if (p1 == firstPlayer)
			targetsArrayList = firstteamButtons;
		else {
			targetsArrayList = secondteamButtons;
		}
		harmteam(targetsArrayList, with);

	}

	private void harmteam(final ArrayList<JButton> targetButtons, boolean flag) {
		// System.out.println(targetButtons.size());
		for (JButton button : targetButtons) {
			requestFocusInWindow();
			button.setBorderPainted(true);
			button.setContentAreaFilled(true);
			button.setOpaque(true);
			requestFocusInWindow();
			this.lpane.remove(board);
			revalidate();
			repaint();
			if (flag)
				button.setBackground(Color.green);
			else {
				button.setBackground(Color.red);
			}
		}
		javax.swing.Timer timer = new javax.swing.Timer(0, new ActionListener() {
			int t = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				Timer timer1 = (Timer) e.getSource();
				t++;

				if (t == 100) {
					for (JButton button : targetButtons) {
						requestFocusInWindow();
						button.setBorderPainted(false);

						button.setContentAreaFilled(false);
						button.setOpaque(false);
						// board.setVisible(false);

					}
					revalidate();
					repaint();
					timer1.stop();

				}

			}
		});
		timer.setInitialDelay(0);
		Timer.setLogTimers(true);
		timer.start();
	}

	@Override
	public void castsingletarget() {
		// TODO Auto-generated method stub

	}

	@Override
	public void castselftarget() {
		// TODO Auto-generated method stub

	}

	@Override
	public void castdirectiontarget() {
		// TODO Auto-generated method stub

	}

	@Override
	public void sroundtarget(Champion c) {
		int width = getcrosy(c.getLocation().y);
		int hight = getcrosx(c.getLocation().x);
		fillboard();
		File file = new File("shaft.wav");
		try {
			Clip clip = AudioSystem.getClip();
			// clip.loop(33);
			clip.open(AudioSystem.getAudioInputStream(file));
			clip.start();
		} catch (Exception e1) {
		}
		final JPanel panel = new JPanel();
		final ImageIcon imageIcon = new ImageIcon("firesurround2.png");
		final JLabel label = new JLabel(imageIcon);
		panel.add(label);
		lpane.add(panel, Integer.valueOf(3));
		panel.setBorder(null);
		panel.setOpaque(false);
		revalidate();
		repaint();
		final int[] arr = new int[] { width - 90, hight - 60, 160, 100 };
		Timer timer = new Timer(20, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (arr[0] == 0 || arr[1] == 0 || arr[2] >= 520) {
					((Timer) (e.getSource())).stop();
					panel.setVisible(false);
					label.setVisible(false);

					lpane.remove(panel);

					revalidate();
					repaint();
//                	revalidate();
//            		repaint();
///                    timer1.stop();

				}
				arr[0] -= 5;
				arr[1] -= 5;
				arr[2] += 10;
				arr[3] += 10;
				panel.setBounds(arr[0], arr[1], (arr[2]), arr[3]);
				Image originalImage = imageIcon.getImage().getScaledInstance(arr[2], arr[3], Image.SCALE_DEFAULT);
				imageIcon.setImage(originalImage);
				label.setSize(arr[2], arr[3]);
				revalidate();

			}
		});
		timer.start();
		requestFocus();
	}
	@Override
	public void leaderabilityforvalain(ArrayList<Champion> t) {
		System.out.println("valin");
		for(Champion c:t) {
			final JPanel lPanel = new JPanel();
			System.out.println("valin2");

			lpane.add(lPanel, Integer.valueOf(3));
			// ImageIcon fireIcon = new ImageIcon("fireCaptain America.png");
			// ImageIcon fireIcon = new ImageIcon("fire"+game.getCurrentChampion() +".png");
			ImageIcon fireIcon = new ImageIcon("fireabove.png");

			JLabel fireJLabel = new JLabel(fireIcon);
			fireJLabel.setBorder(null);
			fireJLabel.setOpaque(false);
			lPanel.add(fireJLabel);
			lPanel.setBorder(null);
			lPanel.setOpaque(false);
			lPanel.setBackground(Color.black);
			lPanel.setOpaque(false);
			Dimension size = lPanel.getPreferredSize();
			lPanel.setSize(100, 100);

			Point currentPoint =c.getLocation();
			Rectangle from = new Rectangle(getcrosy(currentPoint.y) - 40, getcrosx(currentPoint.x) - 300, 160, 100);
			Rectangle to =  new Rectangle(getcrosy(currentPoint.y) - 40, getcrosx(currentPoint.x) - 50, 160, 100);//getpositiontoRectangle(target, direction);
//	         System.out.println(game.getCurrentChampion().getCondition());

			Animate animate = new Animate(lPanel, from, to, lpane);
			animate.delay = 40;
			animate.start();
			revalidate();
			repaint();
			
			
			
			System.out.println("we used valina");
			
			
			
			
			
			
			
			
			
			
		}
		
	}
     public void casthealing(ArrayList<Champion>t) {
    	 for(Champion c:t) {
 			final JPanel lPanel = new JPanel();
 			System.out.println("valin2");

 			lpane.add(lPanel, Integer.valueOf(3));
 			// ImageIcon fireIcon = new ImageIcon("fireCaptain America.png");
 			// ImageIcon fireIcon = new ImageIcon("fire"+game.getCurrentChampion() +".png");
 			ImageIcon fireIcon = new ImageIcon("healing.png");

 			JLabel fireJLabel = new JLabel(fireIcon);
 			fireJLabel.setBorder(null);
 			fireJLabel.setOpaque(false);
 			lPanel.add(fireJLabel);
 			lPanel.setBorder(null);
 			lPanel.setOpaque(false);
 			lPanel.setBackground(Color.black);
 			lPanel.setOpaque(false);
 			Dimension size = lPanel.getPreferredSize();
 			lPanel.setSize(100, 100);

 			Point currentPoint =c.getLocation();
 			Rectangle from = new Rectangle(getcrosy(currentPoint.y) - 40, getcrosx(currentPoint.x) - 300, 160, 100);
 			Rectangle to =  new Rectangle(getcrosy(currentPoint.y) - 40, getcrosx(currentPoint.x) - 50, 160, 100);//getpositiontoRectangle(target, direction);
// 	         System.out.println(game.getCurrentChampion().getCondition());

 			Animate animate = new Animate(lPanel, from, to, lpane);
 			animate.delay = 60;
 			animate.start();
 			revalidate();
 			repaint();
    	 }
     }
     
     public void leaderabilityforvalaind(ArrayList<Damageable> t) {
 		System.out.println("valin");
 		for(Damageable c:t) {
 			final JPanel lPanel = new JPanel();
 			System.out.println("valin2");

 			lpane.add(lPanel, Integer.valueOf(3));
 			// ImageIcon fireIcon = new ImageIcon("fireCaptain America.png");
 			// ImageIcon fireIcon = new ImageIcon("fire"+game.getCurrentChampion() +".png");
 			ImageIcon fireIcon = new ImageIcon("fireabove.png");

 			JLabel fireJLabel = new JLabel(fireIcon);
 			fireJLabel.setBorder(null);
 			fireJLabel.setOpaque(false);
 			lPanel.add(fireJLabel);
 			lPanel.setBorder(null);
 			lPanel.setOpaque(false);
 			lPanel.setBackground(Color.black);
 			lPanel.setOpaque(false);
 			Dimension size = lPanel.getPreferredSize();
 			lPanel.setSize(100, 100);

 			Point currentPoint =c.getLocation();
 			Rectangle from = new Rectangle(getcrosy(currentPoint.y) - 40, getcrosx(currentPoint.x) - 300, 160, 100);
 			Rectangle to =  new Rectangle(getcrosy(currentPoint.y) - 40, getcrosx(currentPoint.x) - 50, 160, 100);//getpositiontoRectangle(target, direction);
// 	         System.out.println(game.getCurrentChampion().getCondition());

 			Animate animate = new Animate(lPanel, from, to, lpane);
 			animate.delay = 40;
 			animate.start();
 			revalidate();
 			repaint();
 			
 			
 			
 			System.out.println("we used valina");
 			
 			
 			
 			
 			
 			
 			
 			
 			
 			
 		}
 		
 	}
      public void casthealingd(ArrayList<Damageable>t) {
     	 for(Damageable c:t) {
  			final JPanel lPanel = new JPanel();
  			System.out.println("valin2");

  			lpane.add(lPanel, Integer.valueOf(3));
  			// ImageIcon fireIcon = new ImageIcon("fireCaptain America.png");
  			// ImageIcon fireIcon = new ImageIcon("fire"+game.getCurrentChampion() +".png");
  			ImageIcon fireIcon = new ImageIcon("healing.png");

  			JLabel fireJLabel = new JLabel(fireIcon);
  			fireJLabel.setBorder(null);
  			fireJLabel.setOpaque(false);
  			lPanel.add(fireJLabel);
  			lPanel.setBorder(null);
  			lPanel.setOpaque(false);
  			lPanel.setBackground(Color.black);
  			lPanel.setOpaque(false);
  			Dimension size = lPanel.getPreferredSize();
  			lPanel.setSize(100, 100);

  			Point currentPoint =c.getLocation();
  			Rectangle from = new Rectangle(getcrosy(currentPoint.y) - 40, getcrosx(currentPoint.x) - 300, 160, 100);
  			Rectangle to =  new Rectangle(getcrosy(currentPoint.y) - 40, getcrosx(currentPoint.x) - 50, 160, 100);//getpositiontoRectangle(target, direction);
//  	         System.out.println(game.getCurrentChampion().getCondition());

  			Animate animate = new Animate(lPanel, from, to, lpane);
  			animate.delay = 60;
  			animate.start();
  			revalidate();
  			repaint();
     	 }
      }
//	
	public static class Animate {

		public static final int RUN_TIME = 200;

		private JPanel panel;
		private Rectangle from;
		private Rectangle to;
		private int delay;
		private long startTime;
		private JLayeredPane lpane;

		public Animate(JPanel panel, Rectangle from, Rectangle to, JLayeredPane lpane) {
			this.panel = panel;
			this.from = from;
			this.to = to;
			this.lpane = lpane;
		}

		public void start() {
			Timer timer = new Timer(delay, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					long duration = System.currentTimeMillis() - startTime;
					double progress = (double) duration / (double) RUN_TIME;
					if (progress > 1f) {
						progress = 1f;
						lpane.remove(panel);
						((Timer) e.getSource()).stop();
						lpane.revalidate();
						lpane.repaint();
					}

					Rectangle target = calculateProgress(from, to, progress);
					panel.setBounds(target);
					// lpane.remove(panel);
				}
			});
			timer.setRepeats(true);
			timer.setCoalesce(true);
			timer.setInitialDelay(0);
			startTime = System.currentTimeMillis();
			timer.start();

		}

	}

	public static Rectangle calculateProgress(Rectangle startBounds, Rectangle targetBounds, double progress) {

		Rectangle bounds = new Rectangle();

		if (startBounds != null && targetBounds != null) {

			bounds.setLocation(calculateProgress(startBounds.getLocation(), targetBounds.getLocation(), progress));
			bounds.setSize(calculateProgress(startBounds.getSize(), targetBounds.getSize(), progress));

		}

		return bounds;

	}

	public static Point calculateProgress(Point startPoint, Point targetPoint, double progress) {

		Point point = new Point();

		if (startPoint != null && targetPoint != null) {

			point.x = calculateProgress(startPoint.x, targetPoint.x, progress);
			point.y = calculateProgress(startPoint.y, targetPoint.y, progress);

		}

		return point;

	}

	public static int calculateProgress(int startValue, int endValue, double fraction) {

		int value = 0;
		int distance = endValue - startValue;
		value = (int) Math.round((double) distance * fraction);
		value += startValue;

		return value;

	}

	public static Dimension calculateProgress(Dimension startSize, Dimension targetSize, double progress) {

		Dimension size = new Dimension();

		if (startSize != null && targetSize != null) {

			size.width = calculateProgress(startSize.width, targetSize.width, progress);
			size.height = calculateProgress(startSize.height, targetSize.height, progress);

		}

		return size;

	}

	public static class Animate1 {

		public static final int RUN_TIME = 2000;

		private JPanel panel;
		private Rectangle from;
		private Rectangle to;
		private int delay;
		private long startTime;
		private JLayeredPane lpane;

		public Animate1(JPanel panel, Rectangle from, Rectangle to, JLayeredPane lpane) {
			this.panel = panel;
			this.from = from;
			this.to = to;
			this.lpane = lpane;
		}

		public void start() {
			Timer timer = new Timer(delay, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					long duration = System.currentTimeMillis() - startTime;
					double progress = (double) duration / (double) RUN_TIME;
					if (progress > 1f) {
						progress = 1f;
						lpane.remove(panel);
						((Timer) e.getSource()).stop();
						lpane.revalidate();
						lpane.repaint();
					}

					Rectangle target = calculateProgress(from, to, progress);
					panel.setBounds(target);
					// lpane.remove(panel);
				}
			});
			timer.setRepeats(true);
			timer.setCoalesce(true);
			timer.setInitialDelay(0);
			startTime = System.currentTimeMillis();
			timer.start();

		}

	}
//done
	
}
