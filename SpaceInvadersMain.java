package SpaceInvaders;/**
 * Created by Kim Huynh on 7/15/2017.
 */

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.io.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpaceInvadersMain extends Application {

    public Pane root;
    boolean goRight = true;//check if the alien currently moving to the right
    static boolean onePlayerIsClick;//check if the game in one player mode
    static boolean twoPlayerIsClick;//check if the game in two player mode
    int P1lives = 3;//lives of player 1
    int P2lives = 3;//live of player 2
    AnimationTimer stage1Timer;//
    AnimationTimer stage2Timer;
    static Rectangle block = new Rectangle();//indicate the conner of aliens array
    static boolean checkGift1 = false;//check player get or not get the gift
    static boolean checkGift2 = false;
    static int bulletkeep1 = 1;//basic bullet type
    static int bulletkeep2 = 1;
    int highScore;
    int tempScore1 = 0;
    int tempScore2 = 0;
    int score1 = 0;
    int score2 = 0;
    Text p1Score;
    Text p2Score;
    Text hScore;
    TextField p1Name = new TextField();
    TextField p2Name = new TextField();
    Button okay = new Button("FINISH");
    Label gameOver;

    List<GameObject> bulletP1s = new ArrayList<>();
    List<GameObject> alien1s = new ArrayList<>();
    List<GameObject> alien2s = new ArrayList<>();
    List<GameObject> alien3s = new ArrayList<>();
    List<GameObject> bulletAs = new ArrayList<>();
    List<GameObject> gifts = new ArrayList<>();
    List<GameObject> bulletP2s = new ArrayList<>();

    public GameObject player1;
    public GameObject player2;
    public GameObject alienBoss;

    //initialize player 1
    Image imgPlayer1 = new Image("SpaceInvaders/Images/Player1.png");
    ImageView ivPlayer1 = new ImageView(imgPlayer1);
    ImageView player1Live1 = new ImageView(imgPlayer1);
    ImageView player1Live2 = new ImageView(imgPlayer1);
    ImageView player1Live3 = new ImageView(imgPlayer1);

    //initialize player2
    Image imgPlayer2 = new Image("SpaceInvaders/Images/Player2.png");
    ImageView ivPlayer2 = new ImageView(imgPlayer2);
    ImageView player2Live1 = new ImageView(imgPlayer2);
    ImageView player2Live2 = new ImageView(imgPlayer2);
    ImageView player2Live3 = new ImageView(imgPlayer2);

    //initialize alien Image
    Image alien1Img = new Image("SpaceInvaders/Images/Alien1.png");
    Image alien2Img = new Image("SpaceInvaders/Images/Alien2.png");
    Image alien3Img = new Image("SpaceInvaders/Images/Alien3.png");
    Image alienBossImg = new Image("SpaceInvaders/Images/AlienBoss.png");
    //initialize alienBoss
    //initialize bullet Image
    Image basicBullet1Img = new Image("SpaceInvaders/Images/basic_bullet.png");
    Image playerBullet1Img = new Image("SpaceInvaders/Images/playerBullet1.png");
    Image playerBullet2Img = new Image("SpaceInvaders/Images/playerBullet2.png");
    Image alienBulletImg = new Image("SpaceInvaders/Images/alienBullet.png");

    //Gift Image
    Image giftImg = new Image("SpaceInvaders/Images/Gift.png");
    //Weapon bullet1 = new Weapon(ivBullet1,player1.getImgView().getTranslateX() + 15,player1.getImgView().getTranslateY(),0, 5);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        root = new Pane();
        //set background Image;
        Image background = new Image("SpaceInvaders/Images/Background.gif");
        BackgroundImage bgimg = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(700, 600, false, false, true, false));
        root.setBackground(new Background(bgimg));

        //set player
        player1 = new Players(imgPlayer1);
        player1.setVelocity(new Point2D(0, 0));
        addGameObject(player1, 180, 450);
        player2 = new Players(imgPlayer2);
        player2.setVelocity(new Point2D(0, 0));
        alienBoss = new Aliens(alienBossImg);


        //player 1 Label score
        Label p1Label = new Label("Player 1");
        p1Label.setId("label");
        p1Label.setTextFill(Color.WHITE);
        p1Label.setTranslateX(30);
        p1Label.setTranslateY(15);
        root.getChildren().add(p1Label);

        p1Score = new Text("" + score1);
        p1Score.setId("label");
        p1Score.setFill(Color.WHITE);
        p1Score.setTranslateX(55);
        p1Score.setTranslateY(45);
        root.getChildren().add(p1Score);

        //HighScore Label score
        Label highScoreL = new Label("High Score");
        highScoreL.setId("label");
        highScoreL.setTextFill(Color.WHITE);
        highScoreL.setTranslateX(260);
        highScoreL.setTranslateY(15);
        root.getChildren().add(highScoreL);

        hScore = new Text("" + highScore);
        hScore.setId("label");
        hScore.setFill(Color.WHITE);
        hScore.setTranslateX(300);
        hScore.setTranslateY(45);
        root.getChildren().add(hScore);

        //Player 2 label score
        Label p2Label = new Label("Player 2");
        p2Label.setId("label");
        p2Label.setTextFill(Color.WHITE);
        p2Label.setTranslateX(500);
        p2Label.setTranslateY(15);
        root.getChildren().add(p2Label);

        p2Score = new Text("" + score2);
        p2Score.setId("label");
        p2Score.setFill(Color.WHITE);
        p2Score.setTranslateX(525);
        p2Score.setTranslateY(45);
        root.getChildren().add(p2Score);

        //Space Invader Label
        Image imgspace = new Image("SpaceInvaders/Images/MainLabel.png");
        ImageView spaceLabel = new ImageView(imgspace);
        spaceLabel.setFitWidth(500);
        spaceLabel.setFitHeight(100);
        spaceLabel.setTranslateX(60);
        spaceLabel.setTranslateY(55);
        root.getChildren().add(spaceLabel);

        //Alien score
        Image alienScore = new Image("SpaceInvaders/Images/AlienScore.png");
        ImageView ivAlienScore = new ImageView(alienScore);
        ivAlienScore.setFitHeight(200);
        ivAlienScore.setFitWidth(200);
        ivAlienScore.setTranslateY(150);
        ivAlienScore.setTranslateX(220);
        root.getChildren().add(ivAlienScore);

        //choosing player button
        Button onePlayer = new Button("One Player");
        onePlayer.setTranslateX(250);
        onePlayer.setTranslateY(350);
        root.getChildren().add(onePlayer);

        Button twoPlayer = new Button("Two Player");
        twoPlayer.setTranslateX(250);
        twoPlayer.setTranslateY(370);
        root.getChildren().add(twoPlayer);

        //show highScore button
        Button hScoreDisplay = new Button("High Scores");
        hScoreDisplay.setTranslateX(250);
        hScoreDisplay.setTranslateY(390);
        root.getChildren().add(hScoreDisplay);

        //show How to play button
        Button howTo = new Button("How To Play");
        howTo.setTranslateX(250);
        howTo.setTranslateY(410);
        root.getChildren().add(howTo);

        //Player2 icon
        ivPlayer2.setFitHeight(30);
        ivPlayer2.setFitWidth(30);
        ivPlayer2.setTranslateY(450);
        ivPlayer2.setTranslateX(400);
        root.getChildren().add(ivPlayer2);

        //Adding line below Players
        Line line = new Line();
        line.setStartX(50);
        line.setStartY(500);
        line.setEndX(550);
        line.setEndY(500);
        line.setStroke(Color.WHITE);
        root.getChildren().add(line);

        //adding lives player 1

        player1Live1.setFitHeight(15);
        player1Live1.setFitWidth(15);
        player1Live1.setTranslateX(50);
        player1Live1.setTranslateY(520);
        root.getChildren().add(player1Live1);


        player1Live2.setFitHeight(15);
        player1Live2.setFitWidth(15);
        player1Live2.setTranslateX(75);
        player1Live2.setTranslateY(520);
        root.getChildren().add(player1Live2);


        player1Live3.setFitHeight(15);
        player1Live3.setFitWidth(15);
        player1Live3.setTranslateX(100);
        player1Live3.setTranslateY(520);
        root.getChildren().add(player1Live3);

        // adding lives player 2

        player2Live1.setFitHeight(15);
        player2Live1.setFitWidth(15);
        player2Live1.setTranslateX(485);//400,475
        player2Live1.setTranslateY(520);
        root.getChildren().add(player2Live1);


        player2Live2.setFitHeight(15);
        player2Live2.setFitWidth(15);
        player2Live2.setTranslateX(510);//400,475
        player2Live2.setTranslateY(520);
        root.getChildren().add(player2Live2);


        player2Live3.setFitHeight(15);
        player2Live3.setFitWidth(15);
        player2Live3.setTranslateX(535);//400,475
        player2Live3.setTranslateY(520);
        root.getChildren().add(player2Live3);

        //Game Tutorial
        Image tutorialIMG = new Image("SpaceInvaders/Images/Tutorial.png");
        ImageView tutorial = new ImageView(tutorialIMG);
        tutorial.setFitWidth(450);
        tutorial.setFitHeight(180);
        tutorial.setTranslateX(80);
        tutorial.setTranslateY(150);

        howTo.setOnMouseClicked(e -> {
            root.getChildren().remove(ivAlienScore);
            root.getChildren().remove(onePlayer);
            root.getChildren().remove(twoPlayer);
            root.getChildren().remove(hScoreDisplay);
            root.getChildren().remove(howTo);
            root.getChildren().add(tutorial);
            Button back = new Button("Back");
            back.setTranslateX(280);
            back.setTranslateY(370);
            root.getChildren().add(back);
            back.setOnMouseClicked(event -> {
                root.getChildren().add(ivAlienScore);
                root.getChildren().add(onePlayer);
                root.getChildren().add(twoPlayer);
                root.getChildren().add(hScoreDisplay);
                root.getChildren().add(howTo);
                root.getChildren().remove(back);
                root.getChildren().remove(tutorial);
            });
        });

        //show High Score File reading

        //game Play for One Player
        onePlayer.setOnMouseClicked(e2 -> {
            root.getChildren().remove(twoPlayer);
            root.getChildren().remove(onePlayer);
            root.getChildren().remove(ivAlienScore);
            root.getChildren().remove(hScoreDisplay);
            root.getChildren().remove((howTo));
            root.getChildren().remove((spaceLabel));
            root.getChildren().remove(ivPlayer2);
            onePlayerIsClick = true;
            checkHighScore();


            drawStage1();

            stage1Timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    stage1Update();
                }
            };
            stage1Timer.start();

            player1.getView().setFocusTraversable(true);
            root.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.RIGHT)
                    player1.moveRight();
                else if (e.getCode() == KeyCode.LEFT)
                    player1.moveLeft();
                else if (e.getCode() == KeyCode.UP)
                    player1.moveUp();
                else if (e.getCode() == KeyCode.DOWN)
                    player1.moveDown();
                else if (e.getCode() == KeyCode.CONTROL) {
                    player1Shoot();
                }
            });
            okay.setOnMouseClicked(e ->{
                Scores p1score = new Scores(p1Name.getText(),score1);
                saveScore(p1score);
                P1lives = 3;
                root.getChildren().addAll(twoPlayer,onePlayer,ivAlienScore,hScoreDisplay,howTo,spaceLabel,ivPlayer2,player1Live1,player1Live2,player1Live3);
                root.getChildren().removeAll(gameOver, p1Name,p2Name,okay);
            });
        });
        //game Play for Two Player
        twoPlayer.setOnMouseClicked(e2 -> {
            root.getChildren().remove(twoPlayer);
            root.getChildren().remove(onePlayer);
            root.getChildren().remove(ivAlienScore);
            root.getChildren().remove(hScoreDisplay);
            root.getChildren().remove((spaceLabel));
            root.getChildren().remove(ivPlayer2);
            root.getChildren().remove((howTo));
            twoPlayerIsClick = true;
            checkHighScore();

            addGameObject(player2, 400, 450);

            drawStage1();
            stage1Timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    stage1Update();
                }
            };
            stage1Timer.start();
            player1.getView().setFocusTraversable(true);
            player2.getView().setFocusTraversable(true);
            root.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.RIGHT)
                    player1.moveRight();
                else if (e.getCode() == KeyCode.LEFT)
                    player1.moveLeft();
                else if (e.getCode() == KeyCode.UP)
                    player1.moveUp();
                else if (e.getCode() == KeyCode.DOWN)
                    player1.moveDown();
                else if (e.getCode() == KeyCode.CONTROL) {
                    if (player1.isAlive())
                        player1Shoot();
                } else if (e.getCode() == KeyCode.D)
                    player2.moveRight();
                else if (e.getCode() == KeyCode.A)
                    player2.moveLeft();
                else if (e.getCode() == KeyCode.W)
                    player2.moveUp();
                else if (e.getCode() == KeyCode.S)
                    player2.moveDown();
                else if (e.getCode() == KeyCode.SPACE) {
                    if (player2.isAlive())
                        player2Shoot();
                }
            });
            okay.setOnMouseClicked(e ->{
                Scores p1score = new Scores(p1Name.getText(),score1);
                Scores p2score = new Scores(p2Name.getText(),score2);
                saveScore(p1score);
                saveScore(p2score);
                P1lives = 3;
                P2lives = 3;
                root.getChildren().addAll(twoPlayer,onePlayer,ivAlienScore,hScoreDisplay,howTo,spaceLabel,ivPlayer2,player1Live1,player1Live2,player1Live3);
                     root.getChildren().removeAll(gameOver, p1Name,p2Name,okay);
            });

        });
        //button
        primaryStage.setTitle("SpaceInvader_HuynhKim");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.getScene().getStylesheets().add(SpaceInvadersMain.class.getResource("SpaceInvader.css").toExternalForm());
        primaryStage.show();
    }

    public void addGameObject(GameObject object, double x, double y) {
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
        root.getChildren().add(object.getView());
    }

    public void addAlien1(GameObject alien1, double x, double y) {
        alien1s.add(alien1);
        addGameObject(alien1, x, y);
    }

    public void addAlien2(GameObject alien2, double x, double y) {
        alien2s.add(alien2);
        addGameObject(alien2, x, y);
    }

    public void addAlien3(GameObject alien3, double x, double y) {
        alien3s.add(alien3);
        addGameObject(alien3, x, y);
    }

    public void addbulletP1(GameObject bulletP1, double x, double y) {
        bulletP1s.add(bulletP1);
        addGameObject(bulletP1, x, y);
    }

    public void addbulletP2(GameObject bulletP2, double x, double y) {
        bulletP2s.add(bulletP2);
        addGameObject(bulletP2, x, y);
    }

    public void addbulletA(GameObject bulletA, double x, double y) {
        bulletAs.add(bulletA);
        addGameObject(bulletA, x, y);
    }

    public void addGift(GameObject gift, double x, double y) {
        gifts.add(gift);
        addGameObject(gift, x, y);
    }

    public void drawStage1() {
        for (double j = 0; j < 2; j++) {
            for (double i = 0.0; i < 7; i += 1) {
                addAlien1(new Aliens(alien1Img), i * 40, j * 40);
            }
        }
        for (int i = 0; i < alien1s.size(); i++) {
            alien1s.get(i).getView().setTranslateX(alien1s.get(i).getView().getTranslateX() + 60);
            alien1s.get(i).getView().setTranslateY(alien1s.get(i).getView().getTranslateY() + 65);
            block.setTranslateX(alien1s.get(i).getView().getTranslateX());
        }
        for (double j = 0; j < 2; j++) {
            for (double i = 0.0; i < 7; i += 1) {
                addAlien2(new Aliens(alien2Img), i * 40, j * 40);
            }
        }
        for (int i = 0; i < alien2s.size(); i++) {
            alien2s.get(i).getView().setTranslateX(alien2s.get(i).getView().getTranslateX() + 60);
            alien2s.get(i).getView().setTranslateY(alien2s.get(i).getView().getTranslateY() + 145);
            block.setTranslateY(alien2s.get(i).getView().getTranslateY());
        }
    }

    public void stage1Movement() {
        if (goRight) {
            if (block.getTranslateX() + 30 >= 550) {
                goRight = false;
                for (GameObject alien : alien1s) {
                    if (alien != null) {
                        alien.getView().setTranslateY(alien.getView().getTranslateY() + 20);
                    }
                }
                for (GameObject alien : alien2s) {
                    if (alien != null) {
                        alien.getView().setTranslateY(alien.getView().getTranslateY() + 20);
                    }
                }
                block.setTranslateY(block.getTranslateY() + 20);
            }
            for (GameObject alien : alien1s) {
                if (alien != null) {
                    alien.getView().setTranslateX(alien.getView().getTranslateX() + 0.5);
                    double rand = Math.floor(Math.random() * 1000 + 1);
                    if (rand == 5) {
                        Bullet aBullet = new Bullet(basicBullet1Img);
                        aBullet.setVelocity(new Point2D(0, 2.5));
                        addbulletA(aBullet, alien.getView().getTranslateX(), alien.getView().getTranslateY());
                    }
                    if (Math.random() < 0.00004) {
                        Gift gift = new Gift(giftImg);
                        gift.setVelocity(new Point2D(0, 1));
                        addGift(gift, alien.getView().getTranslateX(), alien.getView().getTranslateY());
                    }
                }
            }
            for (GameObject alien : alien2s) {
                if (alien != null) {
                    alien.getView().setTranslateX(alien.getView().getTranslateX() + 0.5);
                    double rand = Math.floor(Math.random() * 1000 + 1);
                    if (rand == 5) {
                        Bullet aBullet = new Bullet(basicBullet1Img);
                        aBullet.setVelocity(new Point2D(0, 2.5));
                        addbulletA(aBullet, alien.getView().getTranslateX(), alien.getView().getTranslateY());
                    }
                    if (Math.random() < 0.00004) {
                        Gift gift = new Gift(giftImg);
                        gift.setVelocity(new Point2D(0, 1));
                        addGift(gift, alien.getView().getTranslateX(), alien.getView().getTranslateY());

                    }
                }
            }
            block.setTranslateX(block.getTranslateX() + 0.5);
        } else {
            if (block.getTranslateX() - (30 * (7 + 2)) <= 30) {
                goRight = true;
                for (GameObject alien : alien1s) {
                    if (alien != null) {
                        alien.getView().setTranslateY(alien.getView().getTranslateY() + 20);
                    }
                }
                for (GameObject alien : alien2s) {
                    if (alien != null) {
                        alien.getView().setTranslateY(alien.getView().getTranslateY() + 20);
                    }
                }
                block.setTranslateY(block.getTranslateY() + 20);
            }
            for (GameObject alien : alien1s) {
                if (alien != null) {
                    alien.getView().setTranslateX(alien.getView().getTranslateX() - 0.5);
                    double rand = Math.floor(Math.random() * 1000 + 1);
                    if (rand == 5) {
                        Bullet aBullet = new Bullet(basicBullet1Img);
                        aBullet.setVelocity(new Point2D(0, 2.5));
                        addbulletA(aBullet, alien.getView().getTranslateX(), alien.getView().getTranslateY());
                    }
                    if (Math.random() < 0.00004) {
                        Gift gift = new Gift(giftImg);
                        gift.setVelocity(new Point2D(0, 1));
                        addGift(gift, alien.getView().getTranslateX(), alien.getView().getTranslateY());

                    }
                }
            }
            for (GameObject alien : alien2s) {
                if (alien != null) {
                    alien.getView().setTranslateX(alien.getView().getTranslateX() - 0.5);
                    double rand = Math.floor(Math.random() * 1000 + 1);
                    if (rand == 5) {
                        Bullet aBullet = new Bullet(basicBullet1Img);
                        aBullet.setVelocity(new Point2D(0, 2.5));
                        addbulletA(aBullet, alien.getView().getTranslateX(), alien.getView().getTranslateY());
                    }
                    if (Math.random() < 0.00004) {
                        Gift gift = new Gift(giftImg);
                        gift.setVelocity(new Point2D(0, 1));
                        addGift(gift, alien.getView().getTranslateX(), alien.getView().getTranslateY());

                    }
                }
            }
            block.setTranslateX(block.getTranslateX() - 0.5);
        }
    }

    public void stage1Update() {
        stage1Movement();
        alienCheck();
        checkGameOver();
        giftCheck();

        bulletP1s.removeIf(GameObject::isDead);
        bulletP2s.removeIf(GameObject::isDead);
        alien1s.removeIf(GameObject::isDead);
        alien2s.removeIf(GameObject::isDead);
        bulletAs.removeIf(GameObject::isDead);

        bulletP1s.forEach(GameObject::update);
        bulletP2s.forEach(GameObject::update);
        alien1s.forEach(GameObject::update);
        alien2s.forEach(GameObject::update);
        bulletAs.forEach(GameObject::update);
        gifts.forEach(GameObject::update);
        player1.update();
        player2.update();

        p1Score.setText("" + score1);
        p2Score.setText("" + score2);
        hScore.setText("" + highScore);
        nextStageCheck();
    }

    public void alienCheck() {
        //check player 1 bullet
        if (onePlayerIsClick) {
            for (GameObject bulletp : bulletP1s) {
                for (GameObject alien1 : alien1s) {
                    if (bulletp.isColliding(alien1)) {
                        bulletp.setAlive(false);
                        alien1.setAlive(false);
                        root.getChildren().removeAll(bulletp.getView(), alien1.getView());
                        tempScore1 += 10;
                        score1 += 10;

                    } else if (bulletp.getView().getTranslateY() - 10 < 45) {
                        bulletp.setAlive(false);
                        root.getChildren().remove(bulletp.getView());
                    }
                }
                for (GameObject alien2 : alien2s) {
                    if (bulletp.isColliding(alien2)) {
                        bulletp.setAlive(false);
                        alien2.setAlive(false);
                        root.getChildren().removeAll(bulletp.getView(), alien2.getView());
                        tempScore1 += 15;
                        score1 += 15;

                    } else if (bulletp.getView().getTranslateY() - 10 < 45) {
                        bulletp.setAlive(false);
                        root.getChildren().remove(bulletp.getView());
                    }
                }
            }
        }
        //player 2 bullet check
        if (twoPlayerIsClick) {
            for (GameObject bulletp : bulletP1s) {
                for (GameObject alien1 : alien1s) {
                    if (bulletp.isColliding(alien1)) {
                        bulletp.setAlive(false);
                        alien1.setAlive(false);
                        root.getChildren().removeAll(bulletp.getView(), alien1.getView());
                        tempScore1 += 10;
                        score1 += 10;

                    } else if (bulletp.getView().getTranslateY() - 10 < 45) {
                        bulletp.setAlive(false);
                        root.getChildren().remove(bulletp.getView());
                    }
                }
                for (GameObject alien2 : alien2s) {
                    if (bulletp.isColliding(alien2)) {
                        bulletp.setAlive(false);
                        alien2.setAlive(false);
                        root.getChildren().removeAll(bulletp.getView(), alien2.getView());
                        tempScore1 += 15;
                        score1 += 15;

                    } else if (bulletp.getView().getTranslateY() - 10 < 45) {
                        bulletp.setAlive(false);
                        root.getChildren().remove(bulletp.getView());
                    }
                }
            }
            for (GameObject bulletp2 : bulletP2s) {
                for (GameObject alien1 : alien1s) {
                    if (bulletp2.isColliding(alien1)) {
                        bulletp2.setAlive(false);
                        alien1.setAlive(false);
                        root.getChildren().removeAll(bulletp2.getView(), alien1.getView());
                        tempScore2 += 10;
                        score2 += 10;

                    } else if (bulletp2.getView().getTranslateY() - 10 < 45) {
                        bulletp2.setAlive(false);
                        root.getChildren().remove(bulletp2.getView());
                    }
                }
                for (GameObject alien2 : alien2s) {
                    if (bulletp2.isColliding(alien2)) {
                        bulletp2.setAlive(false);
                        alien2.setAlive(false);
                        root.getChildren().removeAll(bulletp2.getView(), alien2.getView());
                        tempScore2 += 15;
                        score2 += 15;

                    } else if (bulletp2.getView().getTranslateY() - 10 < 45) {
                        bulletp2.setAlive(false);
                        root.getChildren().remove(bulletp2.getView());
                    }
                }
            }
        }
    }

    public void giftCheck() {
        for (GameObject gift : gifts) {
            if (gift.isColliding(player1)) {
                gift.setAlive(false);
                checkGift1 = true;
                root.getChildren().remove(gift.getView());
            } else {
                checkGift1 = false;
            }
            if (gift.isColliding(player2)) {
                gift.setAlive(false);
                checkGift2 = true;
                root.getChildren().remove(gift.getView());
            } else {
                checkGift2 = false;
            }
        }
    }

    public void player1Shoot() {
        if (bulletP1s.size() < 1) {
            if (gifts.size() < 1) {
                Bullet pBullet = new Bullet(basicBullet1Img);
                pBullet.setVelocity(new Point2D(0, -2));
                addbulletP1(pBullet, player1.getView().getTranslateX() + 10, player1.getView().getTranslateY() - 5);
            } else {
                if (checkGift1) {
                    if (gifts.get(gifts.size() - 1).getBulletID() == 2) {
                        Bullet pBullet = new Bullet(playerBullet1Img);
                        bulletkeep1 = 2;
                        pBullet.setVelocity(new Point2D(0, -2.5));
                        addbulletP1(pBullet, player1.getView().getTranslateX() + 10, player1.getView().getTranslateY() - 5);
                    } else if (gifts.get(gifts.size() - 1).getBulletID() == 3) {
                        Bullet pBullet = new Bullet(playerBullet2Img);
                        pBullet.setVelocity(new Point2D(0, -3));
                        bulletkeep1 = 3;
                        addbulletP1(pBullet, player1.getView().getTranslateX() + 10, player1.getView().getTranslateY() - 5);
                    } else {
                        Bullet pBullet = new Bullet(basicBullet1Img);
                        pBullet.setVelocity(new Point2D(0, -2));
                        bulletkeep1 = 1;
                        addbulletP1(pBullet, player1.getView().getTranslateX() + 10, player1.getView().getTranslateY() - 5);
                    }
                } else {
                    if (bulletkeep1 == 2) {
                        Bullet pBullet = new Bullet(playerBullet1Img);
                        pBullet.setVelocity(new Point2D(0, -2.5));
                        addbulletP1(pBullet, player1.getView().getTranslateX() + 10, player1.getView().getTranslateY() - 5);
                    } else if (bulletkeep1 == 3) {
                        Bullet pBullet = new Bullet(playerBullet2Img);
                        pBullet.setVelocity(new Point2D(0, -3));
                        addbulletP1(pBullet, player1.getView().getTranslateX() + 10, player1.getView().getTranslateY() - 5);
                    } else {
                        Bullet pBullet = new Bullet(basicBullet1Img);
                        pBullet.setVelocity(new Point2D(0, -2.0));
                        addbulletP1(pBullet, player1.getView().getTranslateX() + 10, player1.getView().getTranslateY() - 5);
                    }
                }
            }
        }
    }

    public void player2Shoot() {
        if (bulletP2s.size() < 1) {
            if (gifts.size() < 1) {
                Bullet pBullet = new Bullet(basicBullet1Img);
                pBullet.setVelocity(new Point2D(0, -2));
                addbulletP2(pBullet, player2.getView().getTranslateX() + 10, player2.getView().getTranslateY() - 5);

            } else {

                if (checkGift2) {
                    if (gifts.get(gifts.size() - 1).getBulletID() == 2) {
                        Bullet pBullet = new Bullet(playerBullet1Img);
                        bulletkeep2 = 2;
                        pBullet.setVelocity(new Point2D(0, -2.5));
                        addbulletP2(pBullet, player2.getView().getTranslateX() + 10, player2.getView().getTranslateY() - 5);

                    } else if (gifts.get(gifts.size() - 1).getBulletID() == 3) {
                        Bullet pBullet = new Bullet(playerBullet2Img);
                        pBullet.setVelocity(new Point2D(0, -3));
                        bulletkeep2 = 3;
                        addbulletP2(pBullet, player2.getView().getTranslateX() + 10, player2.getView().getTranslateY() - 5);

                    } else {
                        Bullet pBullet = new Bullet(basicBullet1Img);
                        pBullet.setVelocity(new Point2D(0, -2));
                        bulletkeep2 = 1;
                        addbulletP2(pBullet, player2.getView().getTranslateX() + 10, player2.getView().getTranslateY() - 5);

                    }
                } else {
                    if (bulletkeep2 == 2) {
                        Bullet pBullet = new Bullet(playerBullet1Img);
                        pBullet.setVelocity(new Point2D(0, -2.5));
                        addbulletP2(pBullet, player2.getView().getTranslateX() + 10, player2.getView().getTranslateY() - 5);
                    } else if (bulletkeep2 == 3) {
                        Bullet pBullet = new Bullet(playerBullet2Img);
                        pBullet.setVelocity(new Point2D(0, -3));
                        addbulletP2(pBullet, player2.getView().getTranslateX() + 10, player2.getView().getTranslateY() - 5);
                    } else {
                        Bullet pBullet = new Bullet(basicBullet1Img);
                        pBullet.setVelocity(new Point2D(0, -2.0));
                        addbulletP2(pBullet, player2.getView().getTranslateX() + 10, player2.getView().getTranslateY() - 5);
                    }
                }
            }
        }
    }

    public void nextStageCheck() {
        if (onePlayerIsClick) {
            if (tempScore1 == 350) {
                drawStage1();
                tempScore1 = 0;
            }
        } else if (twoPlayerIsClick) {
            if (tempScore1 + tempScore2 == 350) {
                drawStage1();
                tempScore1 = 0;
                tempScore2 = 0;
            }
        }
    }

    public void checkGameOver() {
        if (onePlayerIsClick) {
            for (GameObject bulletA : bulletAs) {
                if (bulletA.isColliding(player1)) {
                    bulletA.setAlive(false);
                    P1lives--;
                    root.getChildren().removeAll(bulletA.getView());
                    if (P1lives == 2) {
                        root.getChildren().remove(player1Live3);
                    } else if (P1lives == 1)
                        root.getChildren().remove(player1Live2);
                    else if (P1lives == 0) {
                        root.getChildren().remove(player1Live1);
                        GameOver();
                    }
                }
            }
            if (block.getTranslateY() + 50 > 500) {
                GameOver();
            }
        } else if (twoPlayerIsClick) {
            for (GameObject bulletA : bulletAs) {
                if (bulletA.isColliding(player1)) {
                    bulletA.setAlive(false);
                    P1lives--;
                    root.getChildren().removeAll(bulletA.getView());
                    if (P1lives == 2) {
                        root.getChildren().remove(player1Live3);
                    } else if (P1lives == 1)
                        root.getChildren().remove(player1Live2);
                    else if (P1lives == 0) {
                        root.getChildren().remove(player1Live1);
                        root.getChildren().removeAll(player1.getView());
                        player1.setAlive(false);
                    }
                }
                if (bulletA.isColliding(player2)) {
                    bulletA.setAlive(false);
                    P2lives--;
                    root.getChildren().removeAll(bulletA.getView());
                    if (P2lives == 2) {
                        root.getChildren().remove(player2Live3);
                    } else if (P2lives == 1)
                        root.getChildren().remove(player2Live2);
                    else if (P2lives == 0) {
                        root.getChildren().remove(player2Live1);
                        root.getChildren().removeAll(player2.getView());
                        player2.setAlive(false);
                    }
                }
            }
            if (!root.getChildren().contains(player1.getView()) && !root.getChildren().contains(player2.getView()))
                GameOver();
            else if (block.getTranslateY() + 50 > 500)
                GameOver();

        }
    }

    public void GameOver() {
        stage1Timer.stop();
        //remove all element
        for (GameObject gift : gifts) {
            root.getChildren().removeAll(gift.getView());
        }
        for (GameObject alien1 : alien1s) {
            root.getChildren().remove(alien1.getView());
        }
        for (GameObject alien2 : alien2s) {
            root.getChildren().remove(alien2.getView());
        }
        for (GameObject bulletA : bulletAs) {
            root.getChildren().remove(bulletA.getView());
        }
        for (GameObject bulletP : bulletP1s) {
            root.getChildren().remove(bulletP.getView());
        }
        for (GameObject bulletP2 : bulletP2s) {
            root.getChildren().removeAll(bulletP2.getView());
        }
        //gameOver label!
        gameOver = new Label("GAME OVER!!");
        gameOver.setId("gameOver");
        gameOver.setTextFill(Color.WHITE);
        gameOver.setTranslateX(230);
        gameOver.setTranslateY(100);
        root.getChildren().add(gameOver);
        //save score
        if(onePlayerIsClick){
            p1Name.setPromptText("Player Name");
            p1Name.setId("label");
            p1Name.setTranslateX(250);
            p1Name.setTranslateY(150);
            root.getChildren().add(p1Name);

            okay.setTranslateX(270);
            okay.setTranslateY(200);
            root.getChildren().add(okay);
        }else if(twoPlayerIsClick){
            p1Name.setPromptText("Player 1");
            p1Name.setId("label");
            p1Name.setTranslateX(250);
            p1Name.setTranslateY(150);
            root.getChildren().add(p1Name);

            p2Name.setPromptText("Player 2");
            p2Name.setId("label");
            p2Name.setTranslateX(250);
            p2Name.setTranslateY(200);
            root.getChildren().add(p2Name);

            okay.setTranslateX(270);
            okay.setTranslateY(250);
            root.getChildren().add(okay);
        }
        //file reading and writing
    }
    //write to from file
    public void saveScore(Scores pScore){
        try{
            File scoreFile = new File("Scores.txt");
            if(!scoreFile.exists()){
                scoreFile.createNewFile();
            }
            FileWriter fw =  new FileWriter(scoreFile.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(pScore.toString());
            bw.newLine();
            bw.close();
            fw.close();
        }catch(IOException e){
            System.out.println(e);
        }
    }
    //read from file
    public void HighScoreDisplay(){
        //read from file
    }
    public void checkHighScore(){
        try{
            File scoreFile = new File("Scores.txt");
            if(!scoreFile.exists()) {
                throw new FileNotFoundException();
            }
            FileReader fr = new FileReader(scoreFile.getAbsoluteFile());
            BufferedReader br = new BufferedReader(fr);
            String s;
            while((s = br.readLine()) != null){
                Pattern p = Pattern.compile("Name= (.*), Score= (.*).");
                Matcher m = p.matcher(s);
                if(m.matches()){
                    int score = Integer.parseInt(m.group(2));
                    if(score > highScore){
                        highScore = score;
                    }
                }
            }
            }catch (IOException e){
            System.out.println(e);
        }
    }

}
