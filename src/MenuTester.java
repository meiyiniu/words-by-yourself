
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collections;

import javax.swing.JFrame;

public class MenuTester extends JFrame implements ActionListener {
    
    private MenuPanel menu;
    
    public MenuTester(){
        menu = new MenuPanel();
        setContentPane(menu);
        setTitle("Words by Yourself");
        setSize(350,640);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
            	BufferedWriter w;
            	try {
	            	w= new BufferedWriter(new FileWriter("high_scores.txt"));
	            	w.write(menu.scores.size()+"\n");
	            	for (int i : menu.scores) w.write(i+"\n");
	            	w.flush();
	            	w.close();
	            	System.out.println("SUCCESSFUL CLOSING");
            	} catch(Exception e){System.out.println("UNSUCCESSFUL CLOSING");System.exit(-1);}
            	System.exit(0);
            }
        });
        if (new File("high_scores.txt").exists()) {
        	BufferedReader r;
        	try {
        		r=new BufferedReader(new FileReader("high_scores.txt"));
        		int size=Integer.parseInt(r.readLine());
        		for (int i=0; i<size; i++) {
        			menu.scores.add(Integer.parseInt(r.readLine()));
        		}
        		r.close();
        		Collections.sort(menu.scores);
        	} catch(Exception e){}
        }
        menu.addScore(2);
        menu.repaint();
    	menu.revalidate();
        //pack();
    }

    public void actionPerformed(ActionEvent e){}
    public static void main(String[] args){
        MenuTester m = new MenuTester();
        m.setVisible(true);
    }

}