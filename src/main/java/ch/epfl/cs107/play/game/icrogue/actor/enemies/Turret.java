package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Arrow;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Turret extends ICRogueActor {
    private Sprite turret;
    private final float COOLDOWN = 2.f;
    private Arrow arrow;
    private boolean launch;
    private float dt;
    public Turret(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        turret = new Sprite("icrogue/static_npc", 1.5f, 1.5f, this, null,
                new Vector(-0.25f, 0));

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        dt++;
        if(dt >= COOLDOWN)
        {
            dt = 0;
            launch = !launch;
        }
        throwArrow();
    }

    private void throwArrow() {
        if(launch)
        {
            arrow = new Arrow(getOwnerArea(), Orientation.DOWN, getCurrentMainCellCoordinates());
            arrow = new Arrow(getOwnerArea(), Orientation.UP, getCurrentMainCellCoordinates());
            arrow = new Arrow(getOwnerArea(), Orientation.LEFT, getCurrentMainCellCoordinates());
            arrow = new Arrow(getOwnerArea(), Orientation.RIGHT, getCurrentMainCellCoordinates());
        }
    }

    public boolean draw(Canvas canvas) {
        turret.draw(canvas);
        return false;
    }

    public boolean dead() {
            return true;
    }

    public void kill() {

    }

}