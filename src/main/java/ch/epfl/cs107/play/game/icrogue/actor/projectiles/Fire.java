package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.DarkLord;
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

    private int damagePoints; // representing the amount of damage that the fire causes
    private int framePoints; // representing value related to the fire's movement
    private Sprite fire;
    private Keyboard keyboard;

    private final static int MOVE_DURATION = 8;
    private Sprite[] sprites = new Sprite[7];
    private Animation currentAnimation;


    public Fire(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        //fire = new Sprite("zelda/fire", 1f, 1f, this, new RegionOfInterest(0, 0, 16, 16), new Vector(0, 0));
        for( int i = 0; i < 7; i++ )
            // initializes each element of the array with a new Sprite object
            sprites[i] = new Sprite("zelda/fire", 1f, 1f, this, new RegionOfInterest(i * 16, 0, 16, 16));
        currentAnimation = new Animation(10, sprites);
        // sets the speed factor and anchor point for the animation
        currentAnimation.setSpeedFactor(5);
        currentAnimation.setAnchor(new Vector(0, 0));
        // sets the size of the animation
        currentAnimation.setWidth(1f);
        currentAnimation.setHeight(1f);
        // initializes the damagePoints and framePoints fields
        damagePoints = 2;
        framePoints = 5;
    }

    public String getTitle() {
        return "zelda/fire";
    }

    // updates the fire's animation and movement
    // if the fire is consumed, it unregisters the fire and take it of game screen
    @Override
    public void update(float deltaTime) {
        currentAnimation.update(deltaTime);
        move(5);
        super.update(deltaTime);
        if(isConsumed())
            getOwnerArea().unregisterActor(this);
    }

    // draw the fire on the screen
    @Override
    public boolean draw(Canvas canvas) {
        currentAnimation.draw(canvas);
        return false;
    }

    // returns a list with the fire's current main cell coordinates
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

    // if the cell is a wall or a hole, the fire is consumed it disappears from the screen game
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

        // if the Turret interact with the Fire, the Turret dies
        @Override
        public void interactWith(Turret turret, boolean isCellInteraction)
        {
            if(isCellInteraction)
                turret.die();
        }
        public void interactWith(DarkLord boss, boolean isCellInteraction)
        {
                consume();
                boss.setHp(boss.getHp() - damagePoints);
        }
    }

}
