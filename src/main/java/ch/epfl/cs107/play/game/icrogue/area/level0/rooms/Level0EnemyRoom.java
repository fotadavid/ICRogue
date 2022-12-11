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

    @Override
    protected void createArea() {
        for( ICRogueActor a : enemies)
            if(a.isAlive())
                registerActor(a);
        else if(a.isDead())
            registerActor(a);
    }
}
