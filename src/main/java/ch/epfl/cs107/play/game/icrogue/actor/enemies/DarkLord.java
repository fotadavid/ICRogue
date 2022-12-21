package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.RandomHelper;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.nio.channels.spi.SelectorProvider;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DarkLord extends ICRogueActor {
    private final Sprite[] sprites = new Sprite[4];
    private final float COOLDOWN = 1.f;
    private final float COOLDOWN_END = 1.f;
    private final float ROTATION_COOLDOWN = 1.f;
    private boolean isAttacking = false;
    private final Sprite[][] spellSprites = new Sprite[4][3];
    private Animation spellAnimation;
    private Sprite[] currentSpriteLine = new Sprite[3];
    private Sprite currentSprite;
    private float dtAttack, dtRotation, dtFinal;
    private boolean attackOnce = true, turnOnce = true;
    private int rotationCounter;
    public DarkLord(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        for( int i = 0; i < 4; i++ )
            sprites[i] = new Sprite("zelda/darkLord", 1.5f, 1.5f, this,
                    new RegionOfInterest(0, i*32, 32, 32));
        for( int i = 0; i < 3; i++ )
            for( int j = 0; j < 4; j++ )
                spellSprites[j][i] = new Sprite("zelda/darkLord.spell", 1.5f, 1.5f, this,
                        new RegionOfInterest(i * 32, j*32, 32, 32));
        currentSprite = sprites[2];
        currentSpriteLine = spellSprites[0];
        spellAnimation = new Animation(10, currentSpriteLine);
        spellAnimation.setSpeedFactor(5);
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
        spellAnimation = new Animation(10, currentSpriteLine);
        spellAnimation.setSpeedFactor(5);
        spellAnimation.setAnchor(new Vector(0, 0));
        spellAnimation.setHeight(1.5f);
        spellAnimation.setHeight(1.5f);
    }
    public void update(float deltaTime){
        super.update(deltaTime);
        dtAttack += deltaTime;
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
                if(dtFinal > COOLDOWN_END) {
                    rotationCounter = 0;
                    moveToRandomPosition();
                }
            }
            dtRotation+=deltaTime;
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
        dtRotation = 0;
        dtAttack = 0;
        dtFinal = 0;
        int x = RandomHelper.roomGenerator.nextInt(1, 9);
        int y = RandomHelper.roomGenerator.nextInt(1, 9);
        DiscreteCoordinates randomPosition = new DiscreteCoordinates(x, y);
        orientate(Orientation.DOWN);
        changePosition(randomPosition);
    }
    public void attack(){
        getOwnerArea().registerActor(new Fire(getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates()));
        isAttacking = true;
        attackOnce = false;
    }
    @Override
    public boolean draw(Canvas canvas) {
        if(isAttacking)
            spellAnimation.draw(canvas);
        else
            currentSprite.draw(canvas);
        return false;
    }
}