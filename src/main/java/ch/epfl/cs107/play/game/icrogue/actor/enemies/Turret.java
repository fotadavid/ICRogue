package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Arrow;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class Turret extends ICRogueActor {
    private Sprite turret;
    private boolean isAlive = true;
    private final float COOLDOWN = 2.f;
    private Arrow arrow1, arrow2, arrow3, arrow4;
    private float dt;
    private Orientation[] orientations;
    public Turret(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        turret = new Sprite("icrogue/static_npc", 1.5f, 1.5f, this, null,
                new Vector(-0.25f, 0));

    }
    public Turret(Area area, Orientation orientation, DiscreteCoordinates position, Orientation[] orientations) {
        super(area, orientation, position);
        this.orientations = orientations;
        turret = new Sprite("icrogue/static_npc", 1.5f, 1.5f, this, null,
                new Vector(-0.25f, 0));

    }
    // returns a boolean value indicating whether the connector can be interacted with by other entities
    public boolean isCellInteractable() {
        return true;
    }

    // returns a List containing the DiscreteCoordinates of the Turret's cell
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }
    // updates the state of the Turret by throwing Arrow objects and removing the Turret from the Area if it is no longer alive
    // it throws the Arrow objects using the throwArrow method and resets dt to 0
    // removes the Turret from the Area using the unregisterActor method, if it is not alive
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        dt+=deltaTime;
        if(dt >= COOLDOWN)
        {
            dt = 0;
            throwArrow();
        }
        if(!this.isAlive)
            getOwnerArea().unregisterActor(this);
    }

    // throws Arrow objects in the cardinal directions from each Turret's "orientations" list
    private void throwArrow() {
            for( Orientation orientation : orientations)
                getOwnerArea().registerActor(new Arrow(getOwnerArea(), orientation, getCurrentMainCellCoordinates()));
    }

    // if Turret is alive, it draws its Turret Sprite on the game screen
    public boolean draw(Canvas canvas) {
        if(this.isAlive)
            turret.draw(canvas);
        return false;
    }

    // returns a boolean value indicating whether the Turret occupies any cells in the Area
    @Override
    public boolean takeCellSpace() {return false;}

    // allows the Turret to interact
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    public boolean IsAlive() {
            return isAlive;
    }

    public void die() {
        isAlive = false;
    }

}