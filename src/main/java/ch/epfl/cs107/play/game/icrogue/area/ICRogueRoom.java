package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.connector.Connector;
import ch.epfl.cs107.play.game.tutosSolution.Tuto2;
import ch.epfl.cs107.play.game.tutosSolution.Tuto2Behavior;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;
import java.util.List;

public abstract class ICRogueRoom extends Area {

    private ICRogueBehavior behavior; // field for the behavior of the room
    private String behaviorName; // field for the name of the behavior
    private List<Connector> connectors = new ArrayList<Connector>(); //list to store the connectors of the room
    private DiscreteCoordinates roomCoordinates; // field to store the coordinates of the room
    private List<DiscreteCoordinates> connectorsCoordinates; // list to store the coordinates of the connectors
    private List<Orientation> orientations; // list to store the orientations of the connectors
    private List <String> connectorDestinationRooms; // list to store the names of the destination rooms for the connectors
    public ICRogueRoom(List<DiscreteCoordinates> connectorsCoordinates, List<Orientation> orientations,
                       List <String> connectorDestinationRooms, String behaviorName, DiscreteCoordinates roomCoordinates)
    {
        this.behaviorName = behaviorName;
        this.roomCoordinates = roomCoordinates;
        this.connectorsCoordinates = connectorsCoordinates;
        this.orientations = orientations;
        this.connectorDestinationRooms = connectorDestinationRooms;
        // creates a Connector object for each set of coordinates, orientation, and destination room name
        // and adds it to the list of connectors
        for( int i = 0; i < connectorsCoordinates.size(); i++ ) {
            connectors.add(new Connector(this, orientations.get(i), connectorsCoordinates.get(i), connectorDestinationRooms.get(i)));
        }
    }

    // gets the list of connectors
    public List<Connector> getConnectors(){
        return connectors;
    }

    public void setConnectorDestinationRoom(int index, String destinationRoom) {
        connectorDestinationRooms.set(index, destinationRoom);
    }

    public void setConnectorsKeys(int key){

    }

    /**
     * Create the area by adding it all actors
     * called by begin method
     * Note it set the Behavior as needed !
     */
    protected void createArea(){
        for(Connector connector : connectors)
            registerActor(connector);
    };
    @Override
    public final float getCameraScaleFactor() {
        return 11;
    }

    // gets the spawn position for the player in the area
    public abstract DiscreteCoordinates getPlayerSpawnPosition();

    // updates the state of the connectors based what buttons are pressed
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Keyboard keyboard = this.getKeyboard(); // gets the keyboard input
        // if "O" is pressed, all connectors are set to the OPEN state
        if(keyboard.get(Keyboard.O).isPressed())
            for(Connector connector : connectors)
                connector.setCurrentState(Connector.ConnectorType.OPEN);
        // if "T" is pressed, all connectors are set to the CLOSED state
        else if(keyboard.get(Keyboard.T).isPressed())
             for(Connector connector : connectors)
                 connector.setCurrentState(Connector.ConnectorType.CLOSED);
             // if "L" is pressed, all connectors with coordinates (0,4) are set to the LOCKED state
        else if(keyboard.get(Keyboard.L).isPressed())
             for(Connector connector : connectors)
                 if( connector.getCoordinates().equals(new DiscreteCoordinates(0, 4)))
                     connector.setCurrentState(Connector.ConnectorType.LOCKED);
             // If the logic method returns true,
            // all connectors that are CLOSED are sets to the OPEN state
             if(logic() == true)
                 for(Connector connector : connectors) {
                     if (connector.getType() == Connector.ConnectorType.CLOSED)
                         connector.setCurrentState(Connector.ConnectorType.OPEN);
                 }

    }

    // boolean indicating whether the task of the room has been completed or not
    public boolean logic(){
        getPlayerSpawnPosition();
        if(enterAreaCells()){
            return true;}
        return false;
    }

    @Override
    public boolean begin(Window window, FileSystem behaviorName) {
        if (super.begin(window, behaviorName)) {
            // Set the behavior map
            behavior = new ICRogueBehavior(window, this.behaviorName);
            setBehavior(behavior);
            createArea();
            return true;
        }
        return false;
    }
}