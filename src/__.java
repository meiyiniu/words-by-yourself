import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

public class __ {
    // to get a specific letter c do __.letters[c-'A'];
    // to get a specific board tile do __.letters[__.DOUBLE_LETTER]
    public static final BufferedImage[] letters = new BufferedImage[26];
    public static final BufferedImage[] dark_letters = new BufferedImage[26];
    public static final BufferedImage[] board = new BufferedImage[26];
    public static final int EMPTY = 0;
    public static final int START = 1;
    public static final int DOUBLE_LETTER = 2;
    public static final int DOUBLE_WORD = 3;
    public static final int TRIPLE_LETTER = 4;
    public static final int TRIPLE_WORD = 5;
    public static BufferedImage logo, name, playerInfo, highScores, play;

    static void init(){
        Object o=new Object();
        try {
            for (int i=0; i<26; i++) {
                System.out.println("res/"+(char)('A'+i)+".png");
                letters[i]=ImageIO.read(__.class.getResource("res/"+(char)('A'+i)+".png"));
                int w=letters[i].getWidth(),h=letters[i].getHeight();
                dark_letters[i]=new BufferedImage(w,h, BufferedImage.TYPE_INT_ARGB);
                for (int x=0; x<w; x++)
                	for (int y=0; y<h; y++) {
                		int mask=(1<<8)-1;
                		int r=(letters[i].getRGB(x, y)>>16)&mask;
                		int g=(letters[i].getRGB(x, y)>>8)&mask;
                		int b=(letters[i].getRGB(x, y))&mask;
                		double c=0.8;
                		r*=c;
                		g*=c;
                		b*=c;
                		dark_letters[i].setRGB(x, y, (0xFF000000)+(r<<16)+(g<<8)+(b));
                	}
            }
            // this is where you put the file paths to all the images
            //PrintWriter w = new PrintWriter("Blank.txt","UTF-8");
            //w.println(".");
            board[EMPTY]=ImageIO.read(__.class.getResource("res/Blank.png"));
            logo = ImageIO.read(__.class.getResource("res/Logo2.png"));
            name = ImageIO.read(__.class.getResource("res/Name2.png"));
            playerInfo = ImageIO.read(__.class.getResource("res/PlayerInfo.png"));
            highScores = ImageIO.read(__.class.getResource("res/HighScores.png"));
            play = ImageIO.read(__.class.getResource("res/Play.png"));
            board[START]=ImageIO.read(__.class.getResource("res/Star.jpg"));
            board[DOUBLE_LETTER]=ImageIO.read(__.class.getResource("res/DL.jpg"));
            board[DOUBLE_WORD]=ImageIO.read(__.class.getResource("res/DW.jpg"));
            board[TRIPLE_LETTER]=ImageIO.read(__.class.getResource("res/TL.jpg"));
            board[TRIPLE_WORD]=ImageIO.read(__.class.getResource("res/TW.jpg"));
        } catch (Exception e) {
            System.err.println("File not found");
            e.printStackTrace();
            throw new InternalError();
        }
    }
}
