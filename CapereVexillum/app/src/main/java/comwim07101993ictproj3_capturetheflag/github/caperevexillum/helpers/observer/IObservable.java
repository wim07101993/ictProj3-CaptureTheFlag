package comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.observer;

import java.util.Observer;

/**
 * Created by wimva on 27/11/2017.
 */

public interface IObservable {

    void addObserver(Observer observer);

    void deleteObserver(Observer observer);

    void notifyObservers(Object args);

    void notifyObservers();

}
