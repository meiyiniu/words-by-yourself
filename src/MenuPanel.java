
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPanel extends JPanel implements ActionListener {
    private JLabel logo, name;
    private JPanel highScores, playerInfo;
    private JButton play;
    public ArrayList<Integer> scores = new ArrayList<Integer>();
    class T extends JPanel {
    	@Override protected void paintComponent(Graphics g) {
    		super.paintComponent(g);
    		g.drawImage(__.highScores.getScaledInstance(330, 243,  java.awt.Image.SCALE_SMOOTH),0,0,null);
    		if (scores.size()>0)
    			g.drawString("Top Score: "+scores.get(0), 80, 56);
        	if (scores.size()>1)
        		g.drawString("Second: "+scores.get(1), 80, 125);
        	if (scores.size()>2)
        		g.drawString("Third: "+scores.get(2), 80, 200);
    	}
    }
    
    class U extends JPanel {
    	@Override protected void paintComponent(Graphics g) {
    		super.paintComponent(g);
    		g.drawImage(__.playerInfo.getScaledInstance(330, 81,  java.awt.Image.SCALE_SMOOTH),0,0,null);
    		if (cs>=0) {
    			g.drawString("You: "+cs, 80, 42);
    		}
    	}
    }
    public MenuPanel () {
        setLayout(new GridBagLayout());
        __.init();
        GridBagConstraints a = new GridBagConstraints();
        setSize(350, 640);
        setBackground(new Color (16, 70, 133));

        logo = new JLabel ();
        name = new JLabel ();
        playerInfo = new U ();
        highScores = new T ();
        play = new JButton ();

        //300 wide for all components
        //height: 80, 100, 100, 300, 80
        logo.setBackground(new Color (16, 70, 133));
        logo.setPreferredSize(this.getPreferredSize());
        ImageIcon im = new ImageIcon (__.logo);
        im = new ImageIcon(im.getImage().getScaledInstance(330, 54,  java.awt.Image.SCALE_SMOOTH)); 
        logo.setIcon(im);
        logo.setOpaque(true);

        name.setBackground(new Color (16, 70, 133));
        name.setPreferredSize(this.getPreferredSize());
        im = new ImageIcon (__.name);
        im = new ImageIcon(im.getImage().getScaledInstance(330, 81,  java.awt.Image.SCALE_SMOOTH)); 
        name.setIcon(im);
        name.setOpaque(true);

        playerInfo.setBackground(new Color (16, 70, 133));
        playerInfo.setPreferredSize(this.getPreferredSize());
        im = new ImageIcon (__.playerInfo);
        im = new ImageIcon(im.getImage().getScaledInstance(330, 81,  java.awt.Image.SCALE_SMOOTH)); 
        playerInfo.setOpaque(true);

        highScores.setBackground(new Color (16, 70, 133));
        highScores.setPreferredSize(this.getPreferredSize());
        im = new ImageIcon (__.highScores);
        im = new ImageIcon(im.getImage().getScaledInstance(330, 243,  java.awt.Image.SCALE_SMOOTH)); 
        highScores.setOpaque(true);

        play.setBackground(new Color (16, 70, 133));
        play.setPreferredSize(this.getPreferredSize());
        im = new ImageIcon (__.play);
        im = new ImageIcon(im.getImage().getScaledInstance(335, 41,  java.awt.Image.SCALE_SMOOTH)); 
        play.setIcon(im);
        play.setOpaque(true);
        play.addActionListener(this);

        a.gridx = 0;
        a.gridy = 0;
        a.fill=GridBagConstraints.BOTH;
        a.ipadx=330;
        a.ipady=50;
        add(logo, a);
        a.ipady=80;
        a.gridy = 1;
        add(name, a);
        a.gridy = 2;
        add(playerInfo, a);
        a.gridy = 3;
        a.ipady = 250;
        add(highScores, a);
        a.gridy = 4;
        a.ipadx=335;
        a.ipady = 40;
        add(play, a);

        setVisible(true);
        repaint();
        revalidate();
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void actionPerformed(ActionEvent e){
        Game g = new Game(this);
        g.setVisible(true);
    }
    int cs=-1;
    public void addScore(int score) {
    	scores.add(score);
    	cs=score;
    	Collections.sort(scores, new Comparator<Integer>() {
			public int compare(Integer i, Integer j) {
				return i>j?-1:i==j?0:1;
			}
    	});
        repaint();
        revalidate();
    }

  /*  public static void main (String[] args) throws InterruptedException
    {
        MenuPanel m = new MenuPanel ();
    }*/
}