package comwim07101993ictproj3_capturetheflag.github.caperevexillum;

import org.junit.Test;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.ISerializable;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Player;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class StateTester {
    @Test
    public void useAppContext() throws Exception {

        assertEquals(true, Player.class.isAssignableFrom(ISerializable.class));
    }
}
