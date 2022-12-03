package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0Room;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

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
    Level0Room currentRoom;
    private Keyboard keyboard;



    private void initLevel(){
        DiscreteCoordinates currentRoomCoor = new DiscreteCoordinates(0, 0);
        currentRoom = new Level0Room(currentRoomCoor);
        addArea(currentRoom);
        Level0Room area = (Level0Room) setCurrentArea(currentRoom.getTitle(), true);
        DiscreteCoordinates coords = area.getPlayerSpawnPosition();
        player = new ICRoguePlayer(area, Orientation.DOWN, coords,"ghost.1");
        player.enterArea(area, coords);
    }
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            initLevel();
            player.fireBall(keyboard.get(Keyboard.X));
            //currentRoom.registerActor(super.fire);
            return true;
        }
        return false;
    }


    /*public void resetMotion() {
       //keyboard.get(Keyboard.R);
       //initLevel();
    }*/

@Override
    public void update(float deltaTime) {
    super.update(deltaTime);
    //player.fireBall(keyboard.get(Keyboard.X));
        //resetMotion();
    }

    @Override
    public void end() {
    }

    @Override
    public String getTitle() {

        return "ICRogue";
    }

    /*protected void switchArea() {

        player.leaveArea();

        areaIndex = (areaIndex==0) ? 1 : 0;

        Level0Room currentArea = (Level0Room) setCurrentArea(areas[areaIndex], false);
        player.enterArea(currentArea, currentArea.getPlayerSpawnPosition());

        player.strengthen();
    }*/
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(player.getCurrentMainCellCoordinates());
    }

}