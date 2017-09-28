package SpaceInvaders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * Created by hoang on 7/14/2017.
 */
public class Aliens extends GameObject {

    public Aliens(Image img){
        super(new Rectangle(30,30, new ImagePattern(img)));
    }
}
