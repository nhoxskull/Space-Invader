package SpaceInvaders;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Created by hoang on 7/14/2017.
 */
public class Gift extends GameObject {



    public Gift(Image img){
        super(new Rectangle(20,20,new ImagePattern(img)));
        super.bulletID = (int)Math.floor(Math.random()*3+1);
    }



}
