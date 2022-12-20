package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0StaffRoom extends Level0ItemRoom{
    private int keyId;
    private Staff staff;

    // initializes the keyId instance variable
    // creates a new Staff object and add it to the Item List
    public Level0StaffRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
        this.keyId = keyId;
        staff = new Staff(this, Orientation.DOWN, new DiscreteCoordinates(5, 5));
        addItemToList(staff);
    }

    // called to create the area of the room and register Staff as an actor
    public void createArea(){
        super.createArea();
        registerActor(staff);
    }

    // boolean indicating if the Staff has been collected or not
    public boolean logic(){
        if(staff.isCollected()){
            return true;}
        return false;
    }
}
