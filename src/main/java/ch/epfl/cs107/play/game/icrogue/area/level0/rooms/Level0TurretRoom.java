package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0TurretRoom extends Level0EnemyRoom{

    private Turret turret1;
    private Turret turret2;

    // constructor creates 2 new Turret actors and adds them to the room
    public Level0TurretRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
        turret1 = new Turret(this, Orientation.UP, new DiscreteCoordinates(1, 8));
        turret2 = new Turret(this, Orientation.UP, new DiscreteCoordinates(8, 1));
    }

    // called to create the area of the room and register both Turrets as actors
    @Override
    protected void createArea() {
        super.createArea();
        registerActor(turret1);
        registerActor(turret2);
    }

    // boolean indicating if the Turrets are Alive or not
    public boolean logic(){
        if(!turret1.IsAlive() && !turret2.IsAlive()){
            return true;}
        return false;
    }

    // updates the state of the room and its actors
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
}
