package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0StaffRoom extends Level0ItemRoom{
    private int keyId;
    private Staff staff;

    public Level0StaffRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
        this.keyId = keyId;
        staff = new Staff(this, Orientation.DOWN, new DiscreteCoordinates(5, 5));
        addItemToList(staff);
    }
    public void createArea(){
        super.createArea();
        registerActor(staff);
    }

    public boolean logic(){
        if(staff.isCollected()){
            return true;}
        return false;
    }
}
