package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import controller.Control;
import controller.computerplayer;
import engine.Game;
import engine.Player;

public class firstview extends JFrame implements ActionListener {
	private JTextField firstPlayername;
	private JTextField secondPlayername;
	private Control control;
	private boolean computerplayermood;
	private JLayeredPane lpane;
	private JPanel panel3;
	private JPanel panel5;
	private int diffeculty;

	public firstview() throws IOException {
		// this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// back.setSize(1300, 770);
		setLocation(new Point(125, 45));
		setSize(1300, 770);
		ImageIcon backgroundImage = new ImageIcon("FirstView.jpg");
//		Image newImage = (backgroundImage.getImage()).getScaledInstance(50, 50, Image.SCALE_DEFAULT);
//        backgroundImage.setImage(newImage);
		JLabel back = new JLabel(backgroundImage);
		this.setLayout(new BorderLayout());
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		JPanel panel1 = new JPanel();
		panel1.add(back);
		panel1.setOpaque(true);

		JPanel panel2 = new JPanel();
		JButton ok = new JButton("START");

		ok.addActionListener(this);
		panel2.add(ok);
		panel2.setOpaque(false);

		lpane = new JLayeredPane();
		this.add(lpane, BorderLayout.CENTER);
		panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		lpane.add(panel1, JLayeredPane.DEFAULT_LAYER);
		lpane.add(panel2, JLayeredPane.DRAG_LAYER);
		lpane.add(panel3, JLayeredPane.DRAG_LAYER);
		lpane.add(panel4, JLayeredPane.DRAG_LAYER);
		// setPreferredSize(new Dimension(600, 400));
		// setLayout(new BorderLayout());
		add(lpane, BorderLayout.CENTER);
		// lpane.setBounds(125, 45, 1300 + 125, 770 + 45);
		panel1.setBackground(Color.black);
		panel1.setBounds(0, 0, 1300, 770 + 45);
		panel1.setOpaque(false);
		panel2.setBackground(new Color(0x123456));
		panel2.setBounds(625, 500, 50, 30);
		panel2.setOpaque(false);

		TextArea textArea = new TextArea("ramez");
		// textArea.setCaretColor(Color.black);
		textArea.setVisible(true);
		firstPlayername = new JTextField(20);
		firstPlayername.setPreferredSize(new Dimension(200, 40));

		// panel3.setLayout(new );
		panel3.setVisible(rootPaneCheckingEnabled);
		panel3.add(firstPlayername);
		firstPlayername.setForeground(Color.cyan);
		firstPlayername.setBackground(new Color(0x123456));
		firstPlayername.setCaretColor(new Color(0x123456));
		firstPlayername.setPreferredSize(new Dimension(200, 40));
		// firstPlayername.setFont(new Font("MV Moli",Font.BOLD,20));
		firstPlayername.setText("First Player");
		textArea.setFont(new Font("", 22, 20));
		panel3.add(textArea);
		textArea.setBackground(Color.black);
		panel3.setForeground(Color.white);
		panel3.setName("ENTER FISRT PALYER'S NAME");
		panel3.setBounds(820, 550, 200, 40);
		panel3.add(new TextArea("fisrtPlayer")).setVisible(true);
		;
		// panel3.setOpaque(true);

		this.setResizable(false);

		secondPlayername = new JTextField(20);
		// secondPlayername.setFont(new Font("", 20, 15));
		secondPlayername.setPreferredSize(new Dimension(200, 40));
		secondPlayername.setForeground(Color.cyan);
		secondPlayername.setBackground(new Color(0x123456));
		secondPlayername.setCaretColor(new Color(0x123456));
		secondPlayername.setText("Second Player");
		// secondPlayername.setPreferredSize(new Dimension(200, 40));
		// secondPlayername.setFont(new Font("MV Moli",Font.BOLD,20));
		panel4.add(secondPlayername);
		panel4.setBounds(250, 550, 200, 40);
		panel4.setOpaque(true);

		panel5 = new JPanel();
		JButton computerplayer = new JButton("compuer player");
		computerplayer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				computerplayermood = true;
				lpane.add(panel5, JLayeredPane.DRAG_LAYER);
				firstPlayername.setText("abo elnashaat AI");
				firstPlayername.setEditable(false);

				ButtonGroup buttonGroup = new ButtonGroup();

				JRadioButton easyButton = new JRadioButton("easy");
				JRadioButton difficultButton = new JRadioButton("difficult");
				JRadioButton mediumButton = new JRadioButton("medium");
				buttonGroup.add(easyButton);
				buttonGroup.add(mediumButton);
				buttonGroup.add(difficultButton);
				easyButton.setBackground(Color.black);
				mediumButton.setBackground(Color.black);
				difficultButton.setBackground(Color.black);
				easyButton.setName("1");
				mediumButton.setName("2");
				difficultButton.setName("3");

				JPanel difficultyJPanel = new JPanel(new GridLayout(1, 3));
				// easyButton.setOpaque(false);

				ActionListener actionListener = new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						diffeculty = Integer.parseInt(((JRadioButton) e.getSource()).getName());
						// System.out.println("difff " + diffeculty);

					}
				};

				easyButton.addActionListener(actionListener);
				mediumButton.addActionListener(actionListener);
				difficultButton.addActionListener(actionListener);

				difficultyJPanel.add(easyButton);
				difficultyJPanel.add(mediumButton);
				difficultyJPanel.add(difficultButton);
				difficultyJPanel.setBounds(570, 600, 230, 40);
				difficultyJPanel.setOpaque(false);
				difficultyJPanel.setBackground(Color.black);
				difficultyJPanel.setForeground(Color.white);
				lpane.add(difficultyJPanel, JLayeredPane.DRAG_LAYER);
				revalidate();
				repaint();
			}
		});
		panel5.add(computerplayer);
		panel5.setOpaque(true);
		panel5.setBounds(600, 550, 130, 40);
		lpane.add(panel5, JLayeredPane.DRAG_LAYER);
		panel1.setOpaque(false);
		panel2.setOpaque(false);
		panel3.setOpaque(false);
		panel4.setOpaque(false);
		panel5.setOpaque(false);

		// pack();
		// setVisible(true);
		setSize(1300, 770);
//		 revalidate();
//       repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (firstPlayername.getText().equals("") || firstPlayername.getText().equals(""))
			return;
		if (computerplayermood)
			control.setGame(new Game(new Player("abo alnashaat AI"), new Player(secondPlayername.getText())));
		else {
			control.setGame(new Game(new Player(firstPlayername.getText()), new Player(secondPlayername.getText())));
		}
		setVisible(false);
		try {
			System.out.println("difff    " + diffeculty);

			control.tosecondview(computerplayermood, diffeculty);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
		}

	}

	/**
	 * @return the firstPlayername
	 */
	public JTextField getFirstPlayername() {
		return firstPlayername;
	}

	/**
	 * @param firstPlayername the firstPlayername to set
	 */
	public void setFirstPlayername(JTextField firstPlayername) {
		this.firstPlayername = firstPlayername;
	}

	/**
	 * @return the secondPlayername
	 */
	public JTextField getSecondPlayername() {
		return secondPlayername;
	}

	/**
	 * @param secondPlayername the secondPlayername to set
	 */
	public void setSecondPlayername(JTextField secondPlayername) {
		this.secondPlayername = secondPlayername;
	}

	/**
	 * @return the control
	 */
	public Control getControl() {
		return control;
	}

	/**
	 * @param control the control to set
	 */
	public void setControl(Control control) {
		this.control = control;
	}
}
