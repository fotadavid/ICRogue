package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.items.Item;
import ch.epfl.cs107.play.game.icrogue.area.Level0;
import ch.epfl.cs107.play.game.icrogue.area.Level;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0Room;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import java.util.Collections;
import java.util.List;

public class ICRogue extends AreaGame {
    public final static float CAMERA_SCALE_FACTOR = 13.f;
    public final static String ROOM = "icrogue/Level0Room";
    private ICRoguePlayer player; // creer iCROGUe player
    private final String[] areas = {ROOM};

    private int areaIndex;
    /**
     * Add all the areas
     */
    ICRogueRoom currentRoom;
    Level currentLevel;

    private void initLevel(){
        currentLevel = new Level0();
        currentLevel.generateFixedMap();
        currentLevel.addAreas(this);
        Level0Room area = (Level0Room) setCurrentArea(currentLevel.getTitle(), true);
        player = new ICRoguePlayer(area, Orientation.DOWN, new DiscreteCoordinates(2, 2),"zelda/player");
        player.enterArea(area, new DiscreteCoordinates(2, 2));
    }
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            initLevel();
            return true;
        }
        return false;
    }

@Override
    public void update(float deltaTime) {
    super.update(deltaTime);
    if(player.isTransitioning())
        switchRoom();
    resetMotion();
    }

    public void resetMotion(){
        Keyboard keyboard = getWindow().getKeyboard();
        Button b = keyboard.get(Keyboard.R);
        if( b.isDown() ) {
            player.leaveArea();
            initLevel();
        }
    }
    @Override
    public void end() {
    }

    @Override
    public String getTitle() {

        return "ICRogue";
    }

    protected void switchRoom()
    {
        player.leaveArea();
        player.setTransitionStateToFalse();
        Level0Room area = (Level0Room) setCurrentArea(player.getDestination(), true);
        player.enterArea(area, area.getPlayerSpawnPosition());
    }
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(player.getCurrentMainCellCoordinates());
    }

}