package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Arrow;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class Turret extends ICRogueActor {
    private Sprite turret;
    private final float COOLDOWN = 2.f;
    private Arrow arrow1, arrow2, arrow3, arrow4;
    private boolean launch;
    private float dt;
    public Turret(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        turret = new Sprite("icrogue/static_npc", 1.5f, 1.5f, this, null,
                new Vector(-0.25f, 0));

    }
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        dt+=deltaTime;
        if(dt >= COOLDOWN)
        {
            dt = 0;
            throwArrow();
        }
    }

    private void throwArrow() {
            arrow1 = new Arrow(getOwnerArea(), Orientation.DOWN, getCurrentMainCellCoordinates());
            getOwnerArea().registerActor(arrow1);
            arrow2 = new Arrow(getOwnerArea(), Orientation.UP, getCurrentMainCellCoordinates());
            getOwnerArea().registerActor(arrow2);
            arrow3 = new Arrow(getOwnerArea(), Orientation.LEFT, getCurrentMainCellCoordinates());
            getOwnerArea().registerActor(arrow3);
            arrow4 = new Arrow(getOwnerArea(), Orientation.RIGHT, getCurrentMainCellCoordinates());
            getOwnerArea().registerActor(arrow4);
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