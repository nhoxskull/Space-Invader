package SpaceInvaders;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
/**
 * Created by Kim Huynh on 7/14/2017.
 */
public class Bullet extends GameObject{

    public Bullet(Image img){
        super(new Rectangle(10,10, new ImagePattern(img)));
    }

}
