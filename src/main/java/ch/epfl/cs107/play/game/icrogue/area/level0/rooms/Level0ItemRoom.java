package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.icrogue.actor.items.Item;
import ch.epfl.cs107.play.math.DiscreteCoordinates;


import java.util.ArrayList;
import java.util.List;

abstract class Level0ItemRoom extends Level0Room{

    private boolean isCollected;

    List<Item> items = new ArrayList<>();

    public Level0ItemRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
    }

    // add Item to the Item list
    public void addItemToList(Item item){items.add(item);}
    public boolean isCollected() {
        return isCollected;
    }


}
