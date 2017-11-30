package comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers;

import java.util.List;

/**
 * Created by wimva on 25/11/2017.
 */

public final class ListHelpers {
    public static boolean IsNullOrEmpty(List list) {
        return list == null || list.size() == 0;
    }
}