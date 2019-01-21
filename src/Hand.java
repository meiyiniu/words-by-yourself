import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;


public class Hand extends Board {
	
	public ArrayList<Character> letters;
	public int selected;
	
	public Hand(int s, Game game) {
		super(1, s, game);
		letters = new ArrayList<Character>();
		selected=-1;
	}
	
	private Random gen = new Random();
	
	public void addd(char c) {
		if (c==0) return;
		if (c>='a') c=(char) (c-'a'+'A');
		try{
		letters.add(c);
		push_left();
		}catch(Exception e){}
		if (letters.size()>super.size) {
			System.out.println("Player loses");
			super.game.endGame();
		}
	}
	
	public void addCard() {
		if (letters.size()>=super.size) {
			System.out.println("Player loses");
			super.game.endGame();
		}
		if (letters.size()<super.size) {
			char c;
			int p=0;
			do {
				c=(char)('A'+gen.nextInt(26));
				p++;
			} while (p<gen.nextInt(Board.q(c)));
			letters.add(c);
			push_left();
		}
	}
	
	public void remove(int idx) {
		try {
			letters.remove(idx);
			selected=-1;
			push_left();
		} catch(Exception e) {System.out.println(idx+" uh oh");}
        repaint();
        revalidate();
	}
	
	public void setLetters(char ... start) {
		if (start.length > size) return;
		letters = new ArrayList<Character>();
		for (char c : start) letters.add(c);
		push_left();
	}
	
	public void push_left() {
		super.removeAll();
		JButton[][] tmp = new JButton[1][size];
		for (int i=0; i<letters.size(); i++) {
			tmp[0][i]=new JButton();
			tmp[0][i].setIcon(new ImageIcon(__.letters[letters.get(i)-'A'].getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
			tmp[0][i].setActionCommand(0+":"+i);
			tmp[0][i].addActionListener(this);
			add(tmp[0][i]);
		}
		for (int i=letters.size(); i<size; i++) {
			tmp[0][i]=new JButton();
			tmp[0][i].setIcon(new ImageIcon(__.board[__.EMPTY].getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
			tmp[0][i].setActionCommand(0+":"+i);
			add(tmp[0][i]);
			tmp[0][i].addActionListener(this);
		}
		super.b=tmp;
        repaint();
        revalidate();
	}
	
	@Override public void actionPerformed(ActionEvent e) {
		JButton b = ((JButton) e.getSource());
		String ac=b.getActionCommand().split(":")[1];
		int idx = Integer.parseInt(ac);
	//	System.out.println(idx+" "+selected);
		try  {
			letters.get(idx);
			if (selected>=0)
				super.b[0][selected].setIcon(new ImageIcon(__.letters[letters.get(selected)-'A'].getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
			selected=idx;
			super.b[0][idx].setIcon(new ImageIcon(__.dark_letters[letters.get(idx)-'A'].getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
		} catch (Exception ee) {selected = -1;}
	//	System.out.println(idx+" "+selected);
	}
	
	public char sel() {return selected<letters.size()&&selected>=0?letters.get(selected):0;}
	
	
}
