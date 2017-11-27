package comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.observer;

import android.support.annotation.NonNull;

import java.util.Observable;

/**
 * Created by wimva on 27/11/2017.
 */

public abstract class AObservable extends Observable{
    @NonNull
    public abstract String getTAG();
}
