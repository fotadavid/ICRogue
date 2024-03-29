package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.window.Canvas;


/**
 * Represents a drawable element.
 */
public interface Graphics {

    /**
     * Renders itself on specified canvas.
     *
     * @param canvas target, not null
     * @return
     */
    boolean draw(Canvas canvas);
}
