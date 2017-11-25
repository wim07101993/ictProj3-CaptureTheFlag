package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager;

import java.util.Arrays;
import java.util.Vector;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flag;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flags;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Team;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.interfaces.ISocketKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.interfaces.IStateManagerKey;

/**
 * Created by wimva on 25/11/2017.
 */

final class ArgsConverter {

    static Object ConvertSocketArgsToStateManagerState(IStateManagerKey stateManagerKey, Object args) {
        if (stateManagerKey == EStateManagerKey.TEAMS) {
            return Arrays.asList((Team[]) args);
        } else if (stateManagerKey == EStateManagerKey.FLAGS) {
            Flag[] flagsArray = (Flag[]) args;

            Vector<Flag> flagsVector = new Vector<>(flagsArray.length);
            flagsVector.addAll(Arrays.asList(flagsArray));

            Flags flags = new Flags();
            flags.setRegisteredFlags(flagsVector);
            return flags;

        } else if (stateManagerKey == EStateManagerKey.GAME_STARTED) {
            return true;
        } else if (stateManagerKey == EStateManagerKey.IS_HOST) {
            return true;
        }

        return args;
    }

    static Object ConvertStateManagerStateToSocketArgs(ISocketKey socketKey, Object args) {
        
        return args;
    }

}
