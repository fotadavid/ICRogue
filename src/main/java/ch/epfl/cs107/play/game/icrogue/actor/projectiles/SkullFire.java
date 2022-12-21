package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Canvas;


import java.util.Collections;
import java.util.List;


public class SkullFire extends Projectile {

    private int damagePoints; // representing the amount of damage that the fire causes
    private int framePoints; // representing value related to the fire's movement
    private boolean turnOnce = true;
    private Sprite fire;
    private Keyboard keyboard;

    private final static int MOVE_DURATION = 10;
    private Sprite[][] sprites = new Sprite[4][3];
    private Animation currentAnimation;


    public SkullFire(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        for( int i = 0; i < 3; i++ )
            for( int j = 0; j < 4; j++ )
                sprites[j][i] = new Sprite("zelda/flameskull", 1.5f, 1.5f, this,
                        new RegionOfInterest(i * 32, j*32, 32, 32));
        if(getOrientation().equals(Orientation.UP))
            currentAnimation = new Animation(6, sprites[0]);
        else if(getOrientation().equals(Orientation.DOWN)) {
            currentAnimation = new Animation(6, sprites[2]);
        }
        else if(getOrientation().equals(Orientation.RIGHT)){
            currentAnimation = new Animation(6, sprites[3]);
        }
        else if(getOrientation().equals(Orientation.LEFT)){
            currentAnimation = new Animation(6, sprites[1]);
        }
        damagePoints = 2;
        currentAnimation.setSpeedFactor(3);
        currentAnimation.setAnchor(new Vector(0, 0));
        currentAnimation.setHeight(1.5f);
        currentAnimation.setHeight(1.5f);
    }
    public int getDamage()
    {
        return damagePoints;
    }

    public String getTitle() {
        return "zelda/flameskull";
    }

    @Override
    public void update(float deltaTime) {
        currentAnimation.update(deltaTime);
        move(5);
        super.update(deltaTime);
        if(isConsumed())
            getOwnerArea().unregisterActor(this);
    }

    @Override
    public boolean draw(Canvas canvas) {
        currentAnimation.draw(canvas);
        return false;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    public boolean takeCellSpace() {
        return false;
    }
    public void interactWith(Interactable other, boolean isCellInteraction )
    {
        other.acceptInteraction(handler, isCellInteraction);
    }
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    ICRogueFireInteractionHandler handler = new ICRogueFireInteractionHandler();
    private class ICRogueFireInteractionHandler implements ICRogueInteractionHandler {
        public void interactWith(ICRogueBehavior.ICRogueCell cell, boolean isCellInteraction) {
            switch (cell.getType()) {
                case WALL, HOLE -> consume();
            }
        }
        public void interactWith(ICRoguePlayer other, boolean isCellInteraction)
        {
            consume();
            other.setHp(other.getHp() - getDamage());
        }
    }
}
