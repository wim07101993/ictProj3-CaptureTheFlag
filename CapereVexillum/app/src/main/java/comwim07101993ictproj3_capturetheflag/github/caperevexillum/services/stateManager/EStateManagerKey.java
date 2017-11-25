package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager;

import java.util.List;
import java.util.Set;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flags;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.LobbySettings;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.ESocketEmitKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.ESocketOnKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.interfaces.ISocketKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager.interfaces.IStateManagerKey;

/**
 * Created by wimva on 25/11/2017.
 */

public enum EStateManagerKey implements IStateManagerKey {

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

        @Override
        public ESocketEmitKey getSocketEmitAskKey() {
            return ESocketEmitKey.ASK_TEAMS;
        }

        @Override
        public ESocketOnKey getSocketOnKey() {
            return ESocketOnKey.SYNC_TEAM;
        }
    },

    FLAGS {
        @Override
        public Class getValueClass() {
            return Flags.class;
        }

        @Override
        public ESocketEmitKey getSocketEmitAskKey() {
            return ESocketEmitKey.ASK_FLAGS;
        }

        @Override
        public ESocketOnKey getSocketOnKey() {
            return ESocketOnKey.SYNC_FLAGS;
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
            return Long.class;
        }
    },

    LOBBY_ID {
        @Override
        public Class getValueClass() {
            return Integer.class;
        }
    },

    GAME_TIME {
        @Override
        public Class getValueClass() {
            return Float.class;
        }

        @Override
        public ESocketEmitKey getSocketEmitAskKey() {
            return ESocketEmitKey.ASK_TIME;
        }

        @Override
        public ESocketOnKey getSocketOnKey() {
            return ESocketOnKey.RESYNC_TIME;
        }
    },

    GAME_STARTED {
        @Override
        public Class getValueClass() {
            return Boolean.class;
        }

        @Override
        public ESocketOnKey getSocketOnKey() {
            return ESocketOnKey.START;
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
            return Boolean.class;
        }
    },

    PLAYER_NAME {
        @Override
        public Class getValueClass() {
            return String.class;
        }
    },

    IS_HOST {
        @Override
        public Class getValueClass() {
            return Boolean.class;
        }

        @Override
        public ESocketOnKey getSocketOnKey() {
            return ESocketOnKey.HOST;
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

    @Override
    public ESocketOnKey getSocketOnKey() {
        return null;
    }

    @Override
    public ESocketEmitKey getSocketEmitAskKey() {
        return null;
    }

    @Override
    public ESocketEmitKey getSocketEmitPutKey() {
        return null;
    }
}
