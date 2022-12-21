package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;
import java.util.List;

public class Level0EnemyRoom extends Level0Room{
    public Level0EnemyRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
    }

    // list of ICRogueActor objects representing enemies in the room
    List<ICRogueActor> enemies = new ArrayList<>();
    public void addEnemyToList( ICRogueActor enemy ) {enemies.add(enemy);}
    @Override
    protected void createArea() {
        super.createArea();
        for( ICRogueActor a : enemies)
            if(a.isAlive())
                registerActor(a); // if the enemy is alive, registers it in the game
        else unregisterActor(a); // if the enemy is not alive, unregisters it from the game
    }
}
