package ch.epfl.cs107.play.game.icrogue.actor.connector;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.List;

public class Connector extends AreaEntity implements Interactable {

    private Sprite invisible, closed, locked, currentState;
    private int NO_KEY_ID = 67, keyId;
    private DiscreteCoordinates coordinates;
    private String destination;

    // represents the current state of the connector
    public enum ConnectorType{
        OPEN,
        CLOSED,
        LOCKED,
        INVISIBLE;
    }
    public ConnectorType getType(){return type;}
    private ConnectorType type = ConnectorType.INVISIBLE;
    public Connector(Area area, Orientation orientation, DiscreteCoordinates coordinates, String destination) {
        super(area, orientation, coordinates);
        this.destination = destination;
        this.coordinates = coordinates;
        invisible = new Sprite("icrogue/invisibleDoor_"+orientation.ordinal(),
                (orientation.ordinal()+1)%2+1, orientation.ordinal()%2+1, this);
        closed = new Sprite("icrogue/door_"+orientation.ordinal(),
                (orientation.ordinal()+1)%2+1, orientation.ordinal()%2+1, this);
        locked = new Sprite("icrogue/lockedDoor_"+orientation.ordinal(),
                (orientation.ordinal()+1)%2+1, orientation.ordinal()%2+1, this);
        currentState = invisible;
    }
    public Connector(Area area, Orientation orientation, DiscreteCoordinates coordinates, String destination, int keyId) {
        super(area, orientation, coordinates);
        this.keyId = keyId;
        this.destination = destination;
        this.coordinates = coordinates;
        invisible = new Sprite("icrogue/invisibleDoor_"+orientation.ordinal(),
                (orientation.ordinal()+1)%2+1, orientation.ordinal()%2+1, this);
        closed = new Sprite("icrogue/door_"+orientation.ordinal(),
                (orientation.ordinal()+1)%2+1, orientation.ordinal()%2+1, this);
        locked = new Sprite("icrogue/lockedDoor_"+orientation.ordinal(),
                (orientation.ordinal()+1)%2+1, orientation.ordinal()%2+1, this);
        currentState = locked;
    }

    // returns the coordinates of the connector
    public DiscreteCoordinates getCoordinates(){return coordinates;}

    // allows to set the destination of the connector
    public void setDestination(String destination) {this.destination = destination;}

    // returns the destination of the connector
    public String getDestination() {
        return destination;
    }

    // returns the Key ID that is required to unlock the connector
    // or a default value if the connector is not locked
    public int getKeyId(){
        if(this.getType().equals(ConnectorType.LOCKED))
            return keyId;
        else return NO_KEY_ID;
    }

    // returns a list of the coordinates of rge cells that the connector occupies
 public List<DiscreteCoordinates> getCurrentCells() {
        DiscreteCoordinates coord = getCurrentMainCellCoordinates();
        return List.of(coord, coord.jump(new Vector((getOrientation().ordinal()+1)%2, getOrientation().ordinal()%2)));
    }

    // returns a boolean value indicating weather the connector occupies space in the grid
    @Override
    public boolean takeCellSpace() {
        if(!this.getType().equals(ConnectorType.OPEN))
            return true;
        else  return false;
    }
    public void setKeyId(int keyId) {this.keyId = keyId;}

    // returns a boolean value indicating whether the connector can be interacted with by other entities
    @Override
    public boolean isCellInteractable() {
        return true;
    }

    // returns a boolean value indicating whether the connector can be interacted with by the player
    @Override
    public boolean isViewInteractable() {
        if(this.getType() == ConnectorType.LOCKED)
            return true;
        return false;
    }

    // allows the connector to interact with the player or other entities
    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
            ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    // draws the connectors on the game screen
    @Override
    public boolean draw(Canvas canvas) {
            if (type.equals(ConnectorType.INVISIBLE))
                invisible.draw(canvas);
            else if (type.equals(ConnectorType.LOCKED))
                locked.draw(canvas);
            else if (type.equals(ConnectorType.CLOSED) && !getOwnerArea().logic())
                closed.draw(canvas);
        return false;
    }

    public void setCurrentState(ConnectorType type){
        this.type = type;
    }
}

