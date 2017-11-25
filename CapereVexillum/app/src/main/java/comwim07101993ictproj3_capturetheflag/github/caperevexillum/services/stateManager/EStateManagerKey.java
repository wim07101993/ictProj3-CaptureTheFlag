package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager;

import java.util.List;
import java.util.Set;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flags;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.LobbySettings;

/**
 * Created by wimva on 25/11/2017.
 */

public enum EStateManagerKey implements  IStateManagerKey {

    REGISTERED_KEYS {
        @Override
        public Class getValueClass() {
            return Set.class;
        }
    },
    TEAMS {
        @Override
        public Class getValueClass() {
            return List.class;
        }
    },
    FLAGS {
        @Override
        public Class getValueClass() {
            return Flags.class;
        }
    },
    LOBBY_SETTINGS {
        @Override
        public Class getValueClass() {
            return LobbySettings.class;
        }
    },
    USER_ID {
        @Override
        public Class getValueClass() {
            return String.class;
        }
    },
    SCORE {
        @Override
        public Class getValueClass() {
            return long.class;
        }
    },
    LOBBY_ID {
        @Override
        public Class getValueClass() {
            return String.class;
        }
    },
    GAME_TIME {
        @Override
        public Class getValueClass() {
            return float.class;
        }
    },
    GAME_STARTED {
        @Override
        public Class getValueClass() {
            return boolean.class;
        }
    },
    MY_FLAGS {
        @Override
        public Class getValueClass() {
            return Flags.class;
        }
    },
    GAME_ENDED {
        @Override
        public Class getValueClass() {
            return boolean.class;
        }
    },
    PLAYER_NAME {
        @Override
        public Class getValueClass() {
            return String.class;
        }
    };

    public static EStateManagerKey convertFromString(String stringKey){
        for (EStateManagerKey key : EStateManagerKey.values()){
            if (key.toString().equals(stringKey)){
                return key;
            }
        }
        throw new IllegalArgumentException("stringKey is not a key of the EStateManagerKey enum");
    }
}
