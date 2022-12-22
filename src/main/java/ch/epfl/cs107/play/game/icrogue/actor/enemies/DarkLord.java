package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.RandomHelper;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.SkullFire;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public class DarkLord extends ICRogueActor {
    // array of sprites for the dark lord character
    private final Sprite[] sprites = new Sprite[4];

    // boolean to track if the dark lord is attacking
    private boolean isAttacking = false;

    // array of 2 dimensions of sprites for the dark lord's spell
    private final Sprite[][] spellSprites = new Sprite[4][3];

    // animation for the dark lord's spells
    private Animation spellAnimation;

    // current sprite for the dark lord and current line of spell sprites
    private Sprite[] currentSpriteLine;
    private Sprite currentSprite;

    // timers for the different actions
    private float dtAttack, dtRotation, dtFinal;

    // boolean to track whether the Dark Lord attacked only once or turned
    private boolean attackOnce = true, turnOnce = true;

    // counter for the dark lord's rotation
    private int rotationCounter;

    // boolean to track if the dark lord is alive or not
    private boolean isAlive = true;

    // Text graphics to display the dark lord's health on the screen
    private final TextGraphics message;

    // Dark lord's initial health points
    private int hp = 10;
    public DarkLord(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        // initializes the message to display the dark lord's health
        message = new TextGraphics("HP BO$$ : " + getHp(), 1f, Color.BLUE,null, 1,
                true, false, new Vector(0.7f, 0.2f));
        // loads the sprites for the dark lord character
        for( int i = 0; i < 4; i++ )
            sprites[i] = new Sprite("zelda/darkLord", 1.5f, 1.5f, this,
                    new RegionOfInterest(0, i*32, 32, 32));
        // loads the sprites for the dark lord's spells
        for( int i = 0; i < 3; i++ )
            for( int j = 0; j < 4; j++ )
                spellSprites[j][i] = new Sprite("zelda/darkLord.spell", 1.5f, 1.5f, this,
                        new RegionOfInterest(i * 32, j*32, 32, 32));
        // sets the initial sprite for the dark lord and the initial line of spell sprites
        currentSprite = sprites[2];
        currentSpriteLine = spellSprites[2];
        spellAnimation = new Animation(2, currentSpriteLine);
        spellAnimation.setSpeedFactor(1);
        spellAnimation.setAnchor(new Vector(0, 0));
        spellAnimation.setHeight(1.5f);
        spellAnimation.setHeight(1.5f);
    }
    // returns a list containing the dark lord's current cell
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }
    // updates the dark lord's sprite based on its orientation
    private void turn(){
        if(getOrientation().equals(Orientation.UP)) {
            currentSprite = sprites[0];
            currentSpriteLine = spellSprites[0];
        }
        else if(getOrientation().equals(Orientation.DOWN)) {
            currentSprite = sprites[2];
            currentSpriteLine = spellSprites[2];
        }
        else if(getOrientation().equals(Orientation.RIGHT)){
            currentSprite = sprites[3];
            currentSpriteLine = spellSprites[3];
        }
        else if(getOrientation().equals(Orientation.LEFT)){
            currentSprite = sprites[1];
            currentSpriteLine = spellSprites[1];
        }
        // updates the spell animation with the new sprite
        spellAnimation = new Animation(2, currentSpriteLine);
        spellAnimation.setSpeedFactor(1);
        spellAnimation.setAnchor(new Vector(0, 0));
        spellAnimation.setHeight(1.5f);
        spellAnimation.setHeight(1.5f);
        // resets the turnOnce
        turnOnce = false;
    }

    // updates the state of the game
    public void update(float deltaTime){
        super.update(deltaTime);
        // updates the message to display the current health points, when the boss looses HP
        // if the dark lord's health is 0 or less,he dies
        message.setText("HP BO$$:" + hp);
        if(hp <= 0)
            die();
        // updates the spell animation
        spellAnimation.update(deltaTime);
        // updates the attack timer
        dtAttack += deltaTime;
        float COOLDOWN = .25f;
        if(dtAttack >= COOLDOWN){
            // if the rotation counter is 0, sets the orientation to down ONCE and attacks ONCE
            if(rotationCounter == 0) {
                orientate(Orientation.DOWN);
                if(turnOnce)
                    turn();
                if(attackOnce)
                    attack();
            }else if(rotationCounter == 1){
                // if the rotation counter is 1, sets the orientation to right ONCE and attacks ONCE
                orientate(Orientation.RIGHT);
                if(turnOnce)
                    turn();
                if(attackOnce)
                    attack();
            }else if(rotationCounter == 2){
                // if the rotation counter is 2, sets the orientation to up ONCE and attacks ONCE
                orientate(Orientation.UP);
                if(turnOnce)
                    turn();
                if(attackOnce)
                    attack();
            }else if(rotationCounter == 3){
                // if the rotation counter is 3, sets the orientation to left ONCE and attacks ONCE
                orientate(Orientation.LEFT);
                // only turn once then attack once
                if(turnOnce)
                    turn();
                if(attackOnce)
                    attack();
                dtFinal += deltaTime;
                float COOLDOWN_END = .25f;
                if(dtFinal > COOLDOWN_END) {
                    rotationCounter = 0;
                    // moves itself in randomposition
                    moveToRandomPosition();
                }
            }
            // updates the rotation timer
            dtRotation+=deltaTime;
            float ROTATION_COOLDOWN = .5f;
            if(dtRotation >= ROTATION_COOLDOWN){
                turnOnce = true;
                attackOnce = true;
                // resets the rotation timer and the attackOnce flag
                dtRotation = 0;
                rotationCounter++;
            }
        }
    }

    // generates the random x and y values
    // orientate and changePosition methods change the orientation and position of the DarkLord
    // turn method allows to turn the DarkLord on the screen
    private void moveToRandomPosition()
    {
        isAttacking = false;
        attackOnce = true;
        turnOnce = true;
        dtRotation = 0;
        dtAttack = 0;
        dtFinal = 0;
        int x = RandomHelper.roomGenerator.nextInt(1, 9);
        int y = RandomHelper.roomGenerator.nextInt(1, 9);
        DiscreteCoordinates randomPosition = new DiscreteCoordinates(x, y);
        orientate(Orientation.DOWN);
        changePosition(randomPosition);
        turn();
    }

    // handles the dark lord attacking
    private void attack(){
        getOwnerArea().registerActor(new SkullFire(getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates()));
        // sets the isAttacking boolean to true
        isAttacking = true;
        attackOnce = false;
    }
    // if Dark Lord is alive, it draws its Sprite on the game screen
    // if he is attacking and alive, draws its spell animation on the screen
    // it draws his health points the game screen
    @Override
    public boolean draw(Canvas canvas) {
        super.draw(canvas);
        if(isAttacking && isAlive)
            spellAnimation.draw(canvas);
        else if(isAlive)
            currentSprite.draw(canvas);
        message.draw(canvas);
        return false;
    }
    // handles the dark lord dying
    // sets the isAlive boolean to false
    // if the Dark Lord died, he is removed from the game screen
    public void die(){
        isAlive = false;
        getOwnerArea().unregisterActor(this);
    }

    // setter and getter for the Dark Lord's health points
    public void setHp( int hp )
    {
        this.hp = hp;
    }
    public int getHp()
    {
        return hp;
    }
    public boolean isCellInteractable() {
        return true;
    }
    public boolean takeCellSpace() {return true;}
    @Override
    public boolean isAlive(){return isAlive;}

    // allows the Dark Lord to interact
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }
}
