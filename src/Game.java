
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Game extends JFrame implements ActionListener {

    public Board board;
    public Hand hand;							// bad V
    String[] start = new String[]{"WHOLE","MRJAY", "MEIYI", "BOXES", "TABLE", "FLOOR", "HELPS", "GOOSE", "SCARF", "GREEN", "WHITE", "SHINE", "PRICE", "STARS"};
    Dictionary dict;
    Random gen = new Random();
    boolean global=true;
    public int score;
    public JLabel sc;
    public int[] cc = new int[] {1, 10, 40, 90, 150, 210, 300, 400, 500, 700, 1000, 5000};
    int cf=0; Timer t; long pd=0; // previous delay
    MenuPanel xx; // this is to end the game and record high scores
    class TT extends TimerTask {
		public void run() {
			hand.addCard();
			t=new Timer();
			t.schedule(new TT(), (long) ((pd-1000)*0.9)+1000);
		}
    }
    TT tsk=new TT();
    public Game(MenuPanel tmp){
    	this.xx=tmp;
        __.init();
        dict=new Dictionary("words.txt");
        board = new Board(5, this);
        hand = new Hand(7, this);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        add(board);
        sc = new JLabel("Score: 0");
        add(sc);
        add(hand);
        setTitle("Words by Yourself");
        setMinimumSize(new Dimension(800, 900));
        setMaximumSize(new Dimension(800, 900));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
            	endGame();
            }
        });
        pack();
        hand.setLetters(start[gen.nextInt(start.length)].toCharArray());
        t=new Timer();
        t.schedule(tsk, 6000); pd=6000;
    }
    
    void updateScore(int x) {
    	score+=x;
    	sc.setText("Score: "+score);
    	if (score>=cc[cf]) {
    		cf++;
    		board.increaseS();
    	}
    }
    boolean ended=false;
    void endGame() {
    	if (ended) return;
    	ended=true;
    	xx.addScore(score);
    	setVisible(false);
    	super.dispose();
    }
    
    public void actionPerformed(ActionEvent e){}

    /*public static void main(String[] args){
        Game g = new Game(null);
        g.setVisible(true);
    }*/

}