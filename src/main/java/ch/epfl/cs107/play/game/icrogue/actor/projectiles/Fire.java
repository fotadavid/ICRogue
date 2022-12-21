package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
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


public class Fire extends Projectile {

    private int damagePoints;
    private int framePoints;
    private Sprite fire;
    private Keyboard keyboard;

    private final static int MOVE_DURATION = 8;
    private Sprite[] sprites = new Sprite[7];
    private Animation currentAnimation;


    public Fire(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        //fire = new Sprite("zelda/fire", 1f, 1f, this, new RegionOfInterest(0, 0, 16, 16), new Vector(0, 0));
        for( int i = 0; i < 7; i++ )
            sprites[i] = new Sprite("zelda/fire", 1f, 1f, this, new RegionOfInterest(i * 16, 0, 16, 16));
        currentAnimation = new Animation(10, sprites);
        currentAnimation.setSpeedFactor(5);
        currentAnimation.setAnchor(new Vector(0, 0));
        currentAnimation.setWidth(1f);
        currentAnimation.setHeight(1f);
        damagePoints = 1;
        framePoints = 5;
    }

    public String getTitle() {
        return "zelda/fire";
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
        //fire.draw(canvas);
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
    private class ICRogueFireInteractionHandler implements ICRogueInteractionHandler
    {
        public void interactWith(ICRogueBehavior.ICRogueCell cell, boolean isCellInteraction)
        {
            switch(cell.getType())
            {
                case WALL, HOLE -> consume();
            }
        }
        @Override
        public void interactWith(Turret turret, boolean isCellInteraction)
        {
            if(isCellInteraction)
                turret.die();
        }
    }

}
