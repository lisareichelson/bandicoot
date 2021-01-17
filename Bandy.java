import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.Rectangle;

public class Bandy {
    //initializes x and y coords and file path
    private int width;
    private int height;
    private int x = 100;
    private int y = 450;
    private int speed = 10;
    public Image bandicoot;
    private String path = "src/imageFiles/Bandy_R.png";


    public Bandy(){
        loadPicture(path);
    }


    //Loads bandicoot image and stores its size(which is probably too big right now)
    private void loadPicture(String path){
        ImageIcon bandy = new ImageIcon(path);
        bandicoot = bandy.getImage();
        width = bandicoot.getWidth(null);
        height = bandicoot.getHeight(null);
    }

    public Image getImage(){
        return bandicoot;
    }

    public int getX(){
        return x ;
    }

    public int getY(){
        return y;
    }


    //can delete up and down if only want L/R options
    public void checkForMotion(KeyEvent key){
        int direction = key.getKeyCode();

        //if LEFT
        if(direction == KeyEvent.VK_LEFT && x > 0){
            x -= speed; //Adjust based on desired movement
            path = "src/imageFiles/Bandy_L.png";
            loadPicture(path);
        }
        //if RIGHT
        if(direction == KeyEvent.VK_RIGHT && x < 800){
            x += speed; //Adjust based on desired movement
            path = "src/imageFiles/Bandy_R.png";
            loadPicture(path);
        }
    }


    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }



}
