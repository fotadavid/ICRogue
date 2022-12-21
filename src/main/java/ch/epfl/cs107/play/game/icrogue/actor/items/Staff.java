package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class Staff extends Item{

    private Sprite staff;
    private Sprite[] sprites = new Sprite[8];
    private Animation currentAnimation;

    public Staff(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        for(int i = 0; i < 8; i++)
            sprites[i] = new Sprite("zelda/staff", 1f, 1f, this, new RegionOfInterest(i * 32, 0, 32, 32));
        currentAnimation = new Animation(10, sprites);
        currentAnimation.setSpeedFactor(5);
        currentAnimation.setAnchor(new Vector(0, 0));
        currentAnimation.setHeight(1f);
        currentAnimation.setHeight(1f);
    }

    public String getTitle() {
        return "zelda/staff";
    }

    // update the state of te game
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        currentAnimation.update(deltaTime);
    }

    // draws the staff on the game screen
    // if it hasn't been collected
    @Override
    public boolean draw(Canvas canvas) {
        if(!this.isCollected())
            currentAnimation.draw(canvas);
        return false;
    }

    // returns a list of DiscreteCoordinates representing the cells that the staff occupies
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    // boolean to check whether the object has been collected or not
    @Override
    public boolean takeCellSpace() {
        if(!this.isCollected())
            return true;
        return false;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    // a boolean indicating whether the interaction
    // is a cell interaction or a view interaction
    // calls the appropriate interaction method on the visitor
    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    @Override
    public boolean isCellInteractable() {
        return false;
    }
}