package SpaceInvaders;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * Created by Kim Huynh on 7/14/2017.
 */
public class Players extends GameObject {
    public int score;
    public String name;

    public Players(Image img){
        super(new Rectangle(30, 30, new ImagePattern(img)));
    }



    public void setScore(int score){
        this.score = score;
    }
    public int getScore(){
        return this.score;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    @Override
    public String toString(){
        return "Name: " + getName() + "Score: " + getScore();
    }
}
