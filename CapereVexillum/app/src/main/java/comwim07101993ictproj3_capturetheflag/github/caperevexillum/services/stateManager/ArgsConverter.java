package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager;

import com.google.gson.Gson;

import java.util.Arrays;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.ArrayHelpers;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flag;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flags;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Team;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.ESocketEmitKey;
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
            Object[] argsArray = (Object[]) args;
            if (ArrayHelpers.IsNullOrEmpty(argsArray)) {
                return new Flags();
            }

            if (argsArray[0].equals("[]")) {
                return new Flags();
            }

            Flags flags = new Flags();
            for (Object a : argsArray) {
                flags.add((Flag) a);
            }

            return flags;

        } else if (stateManagerKey == EStateManagerKey.GAME_STARTED) {
            return true;
        } else if (stateManagerKey == EStateManagerKey.IS_HOST) {
            return true;
        }

        return args;
    }

    static Object ConvertStateManagerStateToSocketArgs(ISocketKey socketKey, Object args) {
        if (socketKey == ESocketEmitKey.CAPTURE_FLAG) {
            return new Gson().toJson(args);
        }
        return args;
    }

}
