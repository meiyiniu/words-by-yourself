import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Board extends JPanel implements ActionListener {
    private static final long serialVersionUID = -4903649272713429680L;
    protected final Game game;
    protected int size;
    protected JButton b[][]; // array list of JButtons that represents the background of our board
    protected char ar[][];
    protected int mult[][]; // this is double word, triple word, etc
    public Board(int size, Game game) {
    	this.game=game;
        setLayout(new GridLayout(size,size));
        b = new JButton [size][size];
        ar= new char[size][size];
        mult = new int[size][size];
        for(int i = 0; i < size; i++){
            for (int j=0; j<size; j++) {
            	mult[i][j]=1;
                b[i][j] = new JButton();
                b[i][j].setActionCommand(i+":"+j);
                ImageIcon im = new ImageIcon(__.board[__.EMPTY]);
                im = new ImageIcon(im.getImage().getScaledInstance(800/(size+1),800/(size+1),  java.awt.Image.SCALE_SMOOTH)); 
                b[i][j].setIcon(im);
                add(b[i][j]);
                b[i][j].addActionListener(this);
            }
        }
        this.size=size;

        ImageIcon im = new ImageIcon(__.board[__.EMPTY]);
        Image image = im.getImage(); 
        Image newimg = image.getScaledInstance(800/(size+1),800/(size+1),  java.awt.Image.SCALE_SMOOTH);   
        im = new ImageIcon(newimg);  
        b[size/2][size/2].setIcon(im);
        b[size/2][size/2].setIcon(new ImageIcon(__.board[__.START].getScaledInstance(800/(size+1),800/(size+1),  java.awt.Image.SCALE_SMOOTH)));
    }
    
    public Board(int w, int h, Game game) {
    	this.game=game;
        setLayout(new GridLayout(w,h));
        size=Math.max(w, h);
        b = new JButton[w][h];
        for (int i=0; i<w; i++) {
        	for (int j=0; j<h; j++) {
        		b[i][j] = new JButton();
        		b[i][j].setActionCommand(i+":"+j);
        		b[i][j].setIcon(new ImageIcon(__.board[__.EMPTY].getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        		add(b[i][j]);
        		b[i][j].addActionListener(this);
        	}
        }
    }
    
    class Tile implements Comparable<Tile> {
    	int x, y; char v;
    	Tile(int x, int y, char v) {
    		this.x=x;
    		this.y=y;
    		this.v=v;
    	}
    	public int compareTo(Tile other) {
    		if (this.x==other.x) {
    			if (this.y==other.y) return 0;
    			return this.y<other.y?-1:1;
    		}
    		return this.x<other.x?-1:1;
    	}
    	
    }
    
    public boolean checkWord() {
    	ArrayList<Tile> l=new ArrayList<Tile>();
    	for (int i=0; i<ar.length; i++) {
    		for (int j=0; j<ar[0].length; j++) {
    			if (ar[i][j]<='Z'&&ar[i][j]>='A') l.add(new Tile(i, j, ar[i][j]));
    		}
    	}
    	if(l.isEmpty()) return false;
    	Collections.sort(l);
    	String s="";
    	final int inf=0x3f3f3f3f;
    	int xx=-1, yy=-1;
    	for (Tile t : l) {
    		if (xx==-1)xx=t.x;
    		if (yy==-1)yy=t.y;
    		if (t.x!=xx)xx=inf;
    		if (t.y!=yy)yy=inf;
    		if (xx==inf&&yy==inf) return false;
    		s+=t.v;
    	}
    	if (xx!=inf) {
    		int prv=-1;
    		for (Tile t : l) {
    			if (prv!=-1 && t.y!=prv+1) return false;
    			if (prv<0) prv=t.y-1;
    			String st = ""+t.v;
    			for (int y=t.y-1; y>=0; y--) { // check for the word with existing tiles
        			if (ar[t.x][y]>0) st=ar[t.x][y]+st;
        			else break;
        		}
        		for (int y=t.y+1; y<size; y++) { // same as above
        			if (ar[t.x][y]>0) st=st+ar[t.x][y];
        			else break;
        		}
        		if (st.length()>1&&!game.dict.exists(st)) return false;
    			prv++;
    		}
    		boolean b=game.global; // the word has to be connected to something
    		for (int y=l.get(0).y-1; y>=0; y--) {
    			if (ar[xx][y]>0) {s=ar[xx][y]+s; b=true;}
    			else break;
    		}
    		for (int y=l.get(l.size()-1).y+1; y<size; y++) {
    			if (ar[xx][y]>0) {s=s+ar[xx][y]; b=true;}
    			else break;
    		}
    		if(s.length()>1) {
    			if(b&&game.dict.exists(s)) game.global=false;
    			return b&&game.dict.exists(s);
    		}
    	}
    	if (yy!=inf) {
    		int prv=-1;
    		for (Tile t : l) {
    			if (prv!=-1 && t.x!=prv+1) return false;
    			if (prv<0) prv=t.x-1;
    			String st = ""+t.v;
    			for (int x=t.x-1; x>=0; x--) { // check for the word with existing tiles
        			if (ar[x][t.y]>0) st=ar[x][t.y]+st;
        			else break;
        		}
        		for (int x=t.x+1; x<size; x++) { // same as above
        			if (ar[x][t.y]>0) st=st+ar[x][t.y];
        			else break;
        		}
        		if (st.length()>1&&!game.dict.exists(st)) return false;
    			prv++;
    		}
    		boolean b=game.global;
    		for (int x=l.get(0).x-1; x>=0; x--) {
    			if (ar[x][yy]>0) {s=ar[x][yy]+s; b=true;}
    			else break;
    		}
    		for (int x=l.get(l.size()-1).x+1; x<size; x++) {
    			if (ar[x][yy]>0) {s=s+ar[x][yy]; b=true;}
    			else break;
    		}
    		if (b&&game.dict.exists(s)) game.global=false;;
    		return b&&game.dict.exists(s);
    	}
		return game.dict.exists(s);
    }
    
    public static int q(char c){
    	if (c<='Z') c=(char) (c-'A'+'a');
    	switch(c) {
		case 'a':
		case 'e':
		case 'i':
		case 'l':
		case 'n':
		case 'o':
		case 'r':
		case 's':
		case 't':
		case 'u':
    		return 1;
		case 'd':
		case 'g':
			return 2;
		case 'b':
		case 'c':
		case 'm':
		case 'p':
			return 3;
		case 'f':
		case 'h':
		case 'v':
		case 'w':
		case 'y':
			return 4;
		case 'k':
			return 5;
		case 'j':
		case 'x':
			return 8;
		case 'q':
		case 'z':
			return 10;
    	}
    	return 0;
    }
    
    public int lock() {
    	int s=0, m=1;
    	for (int i=0; i<ar.length; i++) {
    		for (int j=0; j<ar[0].length; j++) {
    			if ('A'<=ar[i][j]&&ar[i][j]<='Z') {
    				int qq=q(ar[i][j]);
    				if (mult[i][j]<10) qq*=mult[i][j];
    				if (mult[i][j]>10) m*=mult[i][j];
    				s+=qq;
    				change(i, j, ar[i][j]-'A'+'a');
    			}
    		}
    	}
    	return s*m;
    }
    
    public void actionPerformed(ActionEvent e) {
    	JButton s = (JButton) e.getSource();
    	String ac[] = s.getActionCommand().split(":");
    	int x=Integer.parseInt(ac[0]), y=Integer.parseInt(ac[1]);
    	
    	if (game.hand.sel()!=0 && ar[x][y]==0) {
    		char ss=game.hand.sel();
    		game.hand.remove(game.hand.selected);
    		change(x, y, ss);
    	} else if (ar[x][y]<='Z') {
    		char c=ar[x][y];
    		change(x, y, 0);
    		game.hand.addd(c);
    	}
       /* System.out.println(((JButton)e.getSource()).getActionCommand());
        System.out.println((getS()/2)+":"+(getS()/2));
        if (((JButton) e.getSource()).getActionCommand().equals((getS()/2)+":"+(getS()/2))) {
            System.out.println((getS()/2)+":"+(getS()/2));
            increaseS();
        }*/
    	boolean b=checkWord();
    	if (b) {
    		int sss=lock();
			game.updateScore(sss);
    	}
    }
    
    public void change(int x, int y, int v) {
    	if (('A'<=v&&v<='Z')||('a'<=v&&v<='z')) {
    		ar[x][y]=(char)v;
    		System.out.println("|"+v+"|");
    		if (v<='Z') b[x][y].setIcon(new ImageIcon(__.letters[v-'A'].getScaledInstance(800/(size+1), 800/(size+1), Image.SCALE_SMOOTH)));
    		else b[x][y].setIcon(new ImageIcon(__.dark_letters[v-'a'].getScaledInstance(800/(size+1), 800/(size+1), Image.SCALE_SMOOTH)));
    	}
    	else {
    		ar[x][y]=0;
    		b[x][y].setIcon(new ImageIcon(__.board[__.EMPTY].getScaledInstance(800/(size+1), 800/(size+1), Image.SCALE_SMOOTH)));
    	}
        repaint();
        revalidate();
    }
    
    public int getS() {return size;}
    Random x = new Random();
    public void increaseS() {
        removeAll();
        JButton[][] next = new JButton[size+2][size+2];
        char[][] nxx=new char[size+2][size+2];
        int nm[][] = new int[size+2][size+2];
        size=size+2;
        setLayout(new GridLayout(size, size));
        for(int i = 0; i < size; i++){
            for (int j=0; j < size; j++) {
                if (i==0||j==0||i==size-1||j==size-1) { // if this tile is on the border, it is new
                    next[i][j] = new JButton();
                    next[i][j].setActionCommand(i+":"+j);
                    //next[i][j].setPreferredSize(new Dimension(50,50));
                    next[i][j].setIcon(new ImageIcon(__.board[__.EMPTY].getScaledInstance(800/(size+1), 800/(size+1), Image.SCALE_SMOOTH)));
                    //next[i][j].setBorderPainted(false); 
                  /////  next[i][j].setContentAreaFilled(false); 
                  ////  next[i][j].setFocusPainted(false); 
                  //  next[i][j].setOpaque(false);
                    add(next[i][j]);
                    next[i][j].addActionListener(this);
                    int t=x.nextInt(100);
                    nm[i][j]=1;
                    if (t<4) {
                    	nm[i][j]=2; // double letter
                    	next[i][j].setIcon(new ImageIcon(__.board[__.DOUBLE_LETTER].getScaledInstance(800/(size+1), 800/(size+1), Image.SCALE_SMOOTH)));
                    } else if (t<8) {
                    	nm[i][j]=20; // double word
                    	next[i][j].setIcon(new ImageIcon(__.board[__.DOUBLE_WORD].getScaledInstance(800/(size+1), 800/(size+1), Image.SCALE_SMOOTH)));
                    } else if (t<10) { 
                    	nm[i][j]=3; // triple letter
                    	next[i][j].setIcon(new ImageIcon(__.board[__.TRIPLE_LETTER].getScaledInstance(800/(size+1), 800/(size+1), Image.SCALE_SMOOTH)));
                    } else if (t<12) {
                    	nm[i][j]=30; // triple word
                    	next[i][j].setIcon(new ImageIcon(__.board[__.TRIPLE_WORD].getScaledInstance(800/(size+1), 800/(size+1), Image.SCALE_SMOOTH)));
                    }
                    
                } else {
                    next[i][j] = b[i-1][j-1];
                    nxx[i][j] = ar[i-1][j-1];
                    nm[i][j]=mult[i-1][j-1];
                    ImageIcon ic = (ImageIcon) next[i][j].getIcon();
                    Image im = ic.getImage();
                    next[i][j].setIcon(new ImageIcon(im.getScaledInstance(800/(size+1),800/(size+1), java.awt.Image.SCALE_SMOOTH)));
                    next[i][j].setActionCommand(i+":"+j);
                    add(next[i][j]);
                    //	next[i][j].addActionListener(this);
                }
            }
        }
        b=next;
        ar=nxx;
        mult=nm;
        repaint();
        revalidate();
    }
}
