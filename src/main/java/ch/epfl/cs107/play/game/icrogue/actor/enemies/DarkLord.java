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
    private final Sprite[] sprites = new Sprite[4];
    private boolean isAttacking = false;
    private final Sprite[][] spellSprites = new Sprite[4][3];
    private Animation spellAnimation;
    private Sprite[] currentSpriteLine;
    private Sprite currentSprite;
    private float dtAttack, dtRotation, dtFinal;
    private boolean attackOnce = true, turnOnce = true;
    private int rotationCounter;
    private boolean isAlive = true;
    private final TextGraphics message;
    private int hp = 10;
    public DarkLord(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        message = new TextGraphics("HP BO$$ : " + getHp(), 1f, Color.BLUE,null, 1,
                true, false, new Vector(0.7f, 0.2f));
        for( int i = 0; i < 4; i++ )
            sprites[i] = new Sprite("zelda/darkLord", 1.5f, 1.5f, this,
                    new RegionOfInterest(0, i*32, 32, 32));
        for( int i = 0; i < 3; i++ )
            for( int j = 0; j < 4; j++ )
                spellSprites[j][i] = new Sprite("zelda/darkLord.spell", 1.5f, 1.5f, this,
                        new RegionOfInterest(i * 32, j*32, 32, 32));
        currentSprite = sprites[2];
        currentSpriteLine = spellSprites[2];
        spellAnimation = new Animation(2, currentSpriteLine);
        spellAnimation.setSpeedFactor(1);
        spellAnimation.setAnchor(new Vector(0, 0));
        spellAnimation.setHeight(1.5f);
        spellAnimation.setHeight(1.5f);
    }
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }
    public void turn(){
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
        spellAnimation = new Animation(2, currentSpriteLine);
        spellAnimation.setSpeedFactor(1);
        spellAnimation.setAnchor(new Vector(0, 0));
        spellAnimation.setHeight(1.5f);
        spellAnimation.setHeight(1.5f);
        turnOnce = false;
    }
    public void update(float deltaTime){
        super.update(deltaTime);
        message.setText("HP BO$$:" + hp);
        if(hp <= 0)
            die();
        spellAnimation.update(deltaTime);
        dtAttack += deltaTime;
        float COOLDOWN = .25f;
        if(dtAttack >= COOLDOWN){
            if(rotationCounter == 0) {
                orientate(Orientation.DOWN);
                if(turnOnce)
                    turn();
                if(attackOnce)
                    attack();
            }else if(rotationCounter == 1){
                orientate(Orientation.RIGHT);
                if(turnOnce)
                    turn();
                if(attackOnce)
                    attack();
            }else if(rotationCounter == 2){
                orientate(Orientation.UP);
                if(turnOnce)
                    turn();
                if(attackOnce)
                    attack();
            }else if(rotationCounter == 3){
                orientate(Orientation.LEFT);
                if(turnOnce)
                    turn();
                if(attackOnce)
                    attack();
                dtFinal += deltaTime;
                float COOLDOWN_END = .25f;
                if(dtFinal > COOLDOWN_END) {
                    rotationCounter = 0;
                    moveToRandomPosition();
                }
            }
            dtRotation+=deltaTime;
            float ROTATION_COOLDOWN = .5f;
            if(dtRotation >= ROTATION_COOLDOWN){
                turnOnce = true;
                attackOnce = true;
                dtRotation = 0;
                rotationCounter++;
            }
        }
    }

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
    public void attack(){
        getOwnerArea().registerActor(new SkullFire(getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates()));
        isAttacking = true;
        attackOnce = false;
    }
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
    public void die(){
        isAlive = false;
        getOwnerArea().unregisterActor(this);
    }
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
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }
}
