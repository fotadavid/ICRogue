package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;
import java.util.List;

public class Level0EnemyRoom extends Level0Room{
    public Level0EnemyRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
    }
    List<ICRogueActor> enemies = new ArrayList<>();
    public void addEnemyToList( ICRogueActor enemy ) {enemies.add(enemy);}
    @Override

    // created and sets up the room and its contents as well as the enemies
    // enemies that are removed from the game can't interact with the player and other objects
    protected void createArea() {
        super.createArea();
        for( ICRogueActor a : enemies)
            if(a.isAlive())
                registerActor(a);
        else unregisterActor(a);
    }
}
