package comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.observer;

import android.databinding.ObservableList;

/**
 * Created by wimva on 28/11/2017.
 */

public class ObservableListArgs {

    /* ------------------------- FIELDS ------------------------- */

    private ObservableList list;
    private int start;
    private int count;
    private int from;
    private int to;
    private Mode mode;


    /* ------------------------- CONSTRUCTORS ------------------------- */

    public ObservableListArgs(ObservableList list) {
        this.list = list;
        mode = Mode.CHANGED;
    }

    public ObservableListArgs(ObservableList list, Mode mode, int start, int count) {
        this.list = list;
        this.mode = mode;
        this.start = start;
        this.count = count;
    }

    public ObservableListArgs(ObservableList list, int from, int to, int count) {
        this.list = list;
        this.from = from;
        this.to = to;
        this.count = count;
        mode = Mode.RANGE_MOVED;
    }


    /* ------------------------- METHODS ------------------------- */

    public ObservableList getList() {
        return list;
    }

    public int getStart() {
        return start;
    }

    public int getCount() {
        return count;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public Mode getMode() {
        return mode;
    }


    /* ------------------------- NESTED ------------------------- */

    public enum Mode {
        RANGE_CHANGED,
        RANGE_INSERTED,
        RANGE_MOVED,
        RANGE_REMOVED,
        CHANGED
    }
}
