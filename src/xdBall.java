import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class xdBall extends Canvas implements MouseMotionListener{


    JFrame frame;
    int width = 1500;
    int height = 1000;
    private int columns = 7;
    private int rows =  6;
    Block[] blocks = new Block[rows * columns];
    boolean running = false;
    public Padda padda = new Padda();
    Image dbImage;
    Graphics g;
    //rita ut spelplanen
    public xdBall() {
        this.frame = new JFrame("grafik");
        this.frame.setSize(new Dimension(width, height));
        this.frame.add(this);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
        this.addMouseMotionListener(this);
        this.setBackground(Color.BLACK);
        //Level design
        for (int y = 0 ; y < rows; y++) {
            for(int x = 0; x < columns; x++){
                blocks[y * columns + x] = new Block(x,y, Color.GREEN);
            }
        }
        // Gör en genomskinlig cursorbild
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Gör en blank cursor
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

        // equip cursorn
        frame.getContentPane().setCursor(blankCursor);
    }

    public void draw() {
        //dubbelbuffer som ritar ut nästa frame innan den syns så att det blir jämnt
        if (dbImage == null) { //Create the buffer
            dbImage = createImage(width, height);
            if (dbImage == null) {
                System.out.println("dbImage is still null!");
                return;
            } else {
                g = dbImage.getGraphics();
            }
        }
        //målar svart mellan varje frame så att den gamla försvinner
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1500, 1000);
        //ritar ut blocken
        g.setColor(Color.GREEN);
        for (int i = 0 ; i < blocks.length; i++) {
            g.setColor(blocks[i].c);
            g.fillRect(100 + blocks[i].x * 200, 50 + blocks[i].y * 100,100,50);
        }
        padda.draw(g);

        this.getGraphics().drawImage(dbImage,0,0,this);
    }


    public static void main(String[] args) {
        xdBall game;
        game = new xdBall();
        game.running = true;
        //Gameloop
        while (game.running)
            game.draw();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }
    //gör att man styr paddan med musen + hindrar paddan från att åka utanför canvasen
    @Override
    public void mouseMoved (MouseEvent e) {
        System.out.println(e.getX() + " " + e.getY());
        padda.x = e.getX();
        if (padda.x > 1216) {
            padda.x = 1216;
        }
        if (padda.x < 15) {
            padda.x = 15;
        }
    }
}
