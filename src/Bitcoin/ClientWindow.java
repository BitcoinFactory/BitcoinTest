package Bitcoin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ClientWindow extends JPanel{

	static final long serialVersionUID = 1;
	private boolean possedeDisque = true;

	public ClientWindow() {
		Color c = new Color(252,229,205);
		setBackground(c);
		setPreferredSize(new Dimension(500, 400));
	} 

	public void setPossedeDisque(boolean possedeDisque) {
		this.possedeDisque = possedeDisque;
	}

	public void dessiner(Graphics g) {

		g.setColor(Color.black);
		g.fillOval(98, 38, 304, 84);

		Color c = new Color(246, 178, 107);
		g.setColor(c);
		g.fillOval(100, 40, 300, 80);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (possedeDisque) dessiner(g);
	}


	public static void main(String[] arg) {	
		JFrame cadre = new javax.swing.JFrame("BitcoinFactory - Hardcore mining");		// On définit un cadre avec son nom : la fenêtre

		cadre.setContentPane(new ClientWindow());										// Mon client window sera la container
		cadre.setLocation(300, 100);													// Son point d'apparition sur l'écran
		cadre.pack();																	// Ouvert ou non
		cadre.setVisible(true);															// Visibilité du cadre
		cadre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);							// Ferme l'application en même temps que la fenêtre
		cadre.setResizable(false);
		
		JLabel taille = new JLabel("Bitcoin MINER", SwingConstants.CENTER) ;
		taille.setPreferredSize(new Dimension (500,140));
		
		Font font = new Font("Arial",Font.BOLD, 20);
		taille.setFont(font);
		
		cadre.getContentPane().add(taille);
	}



}
