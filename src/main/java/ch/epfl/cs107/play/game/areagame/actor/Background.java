package ch.epfl.cs107.play.game.areagame.actor;

import ch.epfl.cs107.play.game.actor.Entity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;


public class Background extends Entity {

    /// Sprite of the actor
    private final ImageGraphics sprite;

    /**
     * Default Background Constructor
     * by default the Background image is using the area title as file name
     * @param area (Area): ownerArea. Not null
     */
    public Background(Area area) {
        super(DiscreteCoordinates.ORIGIN.toVector());
        sprite = new ImageGraphics(ResourcePath.getBackground(area.getTitle()), area.getWidth(), area.getHeight(), null, Vector.ZERO, 1.0f, -Float.MAX_VALUE);
        sprite.setParent(this);
    }

    /**
     * Default Background Constructor
     * by default the Background image is using the area title as file name
     * @param area (Area): ownerArea. Not null
     */
    public Background(Area area, String title) {
        super(DiscreteCoordinates.ORIGIN.toVector());
        sprite = new ImageGraphics(ResourcePath.getBackground(title), area.getWidth(), area.getHeight(), null, Vector.ZERO, 1.0f, -Float.MAX_VALUE);
        sprite.setParent(this);
    }

    /**
     * Extended Background Constructor
     * by default the Background image is using the area title as file name
     * @param area (Area): ownerArea. Not null
     * @param region (RegionOfInterest): region of interest in the image for the background, may be null
     */
    public Background(Area area, RegionOfInterest region) {
        super(DiscreteCoordinates.ORIGIN.toVector());
        sprite = new ImageGraphics(ResourcePath.getBackground(area.getTitle()), area.getWidth(), area.getHeight(), region, Vector.ZERO, 1.0f, -Float.MAX_VALUE);
        sprite.setParent(this);
    }

    /**
     * Extended Background Constructor
     * @param area (Area): ownerArea. Not null
     * @param region (RegionOfInterest): region of interest in the image for the background, may be null
     * @param name (String): Background file name (i.e only the name, with neither path, nor file extension). Not null
     */
    public Background(Area area, RegionOfInterest region, String name) {
        super(DiscreteCoordinates.ORIGIN.toVector());
        sprite = new ImageGraphics(ResourcePath.getBackground(name), area.getWidth(), area.getHeight(), region, Vector.ZERO, 1.0f, -Float.MAX_VALUE);
        sprite.setParent(this);
    }

    /**
     * Alternative Background Constructor
     * @param name (String): Background file name (i.e only the name, with neither path, nor file extension). Not null
     * @param width (int): of the desired background
     * @param height (int): of the desired background
     * @param region (RegionOfInterest): region of interest in the image for the background. May be null
     */
    public Background(String name, int width, int height, RegionOfInterest region) {
        super(DiscreteCoordinates.ORIGIN.toVector());
        sprite = new ImageGraphics(ResourcePath.getBackground(name), width, height, region, Vector.ZERO, 1.0f, -Float.MAX_VALUE);
        sprite.setParent(this);
    }

    /// Background implements Graphics

    @Override
    public boolean draw(Canvas canvas) {
        sprite.draw(canvas);
        return false;
    }

}
