package comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers;


import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Wim and Sven on 3/10/2017.
 * <p>
 * Utils is a class with only commonly used static methods.
 */
public class Utils {

    /**
     * Creates a toast in the given context with the given string and shows it.
     *
     * @param context Context to show the toast in
     * @param string Text to show in the toast
     */
    public static void toast(Context context, String string) {
        // create new toast in context
        Toast toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
        // set toast position
        toast.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0, 0);
        // show it
        toast.show();
    }

    /**
     * Rounds a number to a certain number of decimal places.
     *
     * @param d number to round
     * @param numberOfDecimalPlaces number of decimal places.
     * @return rounded number
     */
    public static double round(double d, int numberOfDecimalPlaces) {
        // some algorithm
        double multiplier = Math.pow(10, numberOfDecimalPlaces);
        return Math.round(d * multiplier) / multiplier;
    }
}
