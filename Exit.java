import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Exit {
    private int width;
    private int height;
    private int x = 50;
    private int y = 60;
    public Image exitbutton;
    private String path = "src/imageFiles/Exitkey.png";

    public Exit(){
//        loadPicture(path);
//        checkForClick();


    }

    private void loadPicture(String path){
        ImageIcon Exit = new ImageIcon(path);
        exitbutton = Exit.getImage();
        width = exitbutton.getWidth(null);
        height = exitbutton.getHeight(null);
    }

    public void checkForClick(KeyEvent key){
        int pressed = key.getKeyCode();

        if(pressed == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }


    }

    public Image getImage(){
        return exitbutton;
    }

    public int getX(){
        return x ;
    }

    public int getY(){
        return y;
    }


}