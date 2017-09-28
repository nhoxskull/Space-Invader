package SpaceInvaders;

/**
 * Created by Kim Huynh on 8/11/2017.
 */
public class Scores {
    public int score;
    public String name;
    public Scores(String name, int score){
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString(){
        return "Name= " + getName()+  ", Score= " + getScore()+".";
    }
}
