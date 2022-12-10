package ch.epfl.cs107.play.game.icrogue.actor.items;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;


import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class Cherry extends Item{

    private Sprite cherry;


    public Cherry(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        cherry = new Sprite("icrogue/cherry", 0.6f, 0.6f, this);
    }

    public String getTitle() {
        return "icrogue/cherry";
    }

    @Override
    public void draw(Canvas canvas) {cherry.draw(canvas);}

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
        }

    @Override
    public boolean takeCellSpace() {
        return false;
    }


    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }
}


