package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.enums;

/**
 * Created by Wim Van Laer on 21/10/2017.
 *
 * ViewState is the state in which the view currently is. It tells what the user of the app sees.
 */

public enum ViewState {
    /**
     * The create lobby view, the user was creating a lobby
     */
    CREATE_LOBBY,
    /**
     * The lobby view, the user was selecting a team or waiting for the game to start
     */
    LOBBY,
    /**
     * Home view, the game is busy but there are nog beacons around
     */
    HOME,
    /**
     * The user is asked if he/she wants to participate in a quiz to capture a flag
     */
    QUIZ_QUESTION,
    /**
     * Quiz view, the user is participating in a quiz to capture a flag.
     */
    QUIZ,
    /**
     * END view, the game has ended en the winner is displayed.
     */
    END,
}
