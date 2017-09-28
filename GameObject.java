package SpaceInvaders;


import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
/**
 * Created by Kim Huynh on 7/14/2017.
 */
public abstract class GameObject {
        public boolean alive = true;
        public Node view;
        public Point2D velocity = new Point2D(0,0);
        int bulletID;
        ImagePattern imgP;
        //public Point2D
        public GameObject(Node view){
                this.view = view;
        }

        public void update(){
                view.setTranslateX(view.getTranslateX() + velocity.getX());
                view.setTranslateY(view.getTranslateY() + velocity.getY());
        }

        public void setVelocity(Point2D velocity){
                this.velocity = velocity;
        }

        public Point2D getVelocity() {
                return velocity;
        }
        public Node getView(){
                return view;
        }

        public boolean isAlive(){
                return alive;
        }

        public boolean isDead(){
                return !alive;
        }
        public void setAlive(boolean alive){
                this.alive = alive;
        }

        public void moveRight(){
                if(view.getTranslateX() + 5 >= 525)
                        view.setTranslateX(525);
                else
                        view.setTranslateX(getView().getTranslateX() +5);
        }
        public void moveLeft(){
                if(view.getTranslateX() - 5 <= 55)
                        view.setTranslateX(55);
                else
                        view.setTranslateX(getView().getTranslateX() -5);
        }
        public void moveUp(){
                if(view.getTranslateY() - 5 <= 60)
                        view.setTranslateY(60);
                else
                        view.setTranslateY(getView().getTranslateY() -5);
        }
        public void moveDown(){
                if(view.getTranslateY() + 5 >= 450)
                        view.setTranslateY(450);
                else
                        view.setTranslateY(getView().getTranslateY() +5);
        }
        public int getBulletID(){
                return bulletID;
        }

        public void setImagePattern(Image img){
                this.imgP = new ImagePattern(img);
        }
        public boolean isColliding(GameObject other){
                return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
        }
}
