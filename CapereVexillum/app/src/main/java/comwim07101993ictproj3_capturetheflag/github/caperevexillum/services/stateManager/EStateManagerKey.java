package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager;

import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Set;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.ISerializable;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.PrimitiveDefaults;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flag;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flags;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Player;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Players;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Team;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.ESocketEmitKey;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.ESocketOnKey;
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

    USER_ID {
        @Override
        public Class getValueClass() {
            return String.class;
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
    },

    MY_TEAM {
        @Override
        public Class getValueClass() {
            return String.class;
        }

        @Override
        public Object getDefaultValue() {
            return Team.NO_TEAM;
        }
    },

    CAPTURED_FLAG {
        @Override
        public Class getValueClass() {
            return Flag.class;
        }

        @Override
        public ESocketEmitKey getSocketEmitPutKey() {
            return ESocketEmitKey.CAPTURE_FLAG;
        }

        @Override
        public boolean needsToBeStored() {
            return false;
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

    SCORE {
        @Override
        public Class getValueClass() {
            return Long.class;
        }
    },

    MY_FLAGS {
        @Override
        public Class getValueClass() {
            return Flags.class;
        }
    },

    LOBBY_ID {
        @Override
        public Class getValueClass() {
            return Integer.class;
        }

        @Override
        public ESocketOnKey getSocketOnKey() {
            return ESocketOnKey.GET_LOBBY_ID;
        }
    },

    LOBBY_NAME {
        @Override
        public Class getValueClass() {
            return String.class;
        }

        @Override
        public boolean needsToBeStored() {
            return false;
        }

        @Override
        public ESocketEmitKey getSocketEmitPutKey() {
            return ESocketEmitKey.JOIN_LOBBY;
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

    START_GAME {
        // TODO WIM: connect to socket
        @Override
        public Class getValueClass() {
            return Boolean.class;
        }
    },

    GAME_STARTED {
        @Override
        public Class getValueClass() {
            return new TypeToken<List<Player>>() {
            }.getClass();
        }

        @Override
        public ESocketOnKey getSocketOnKey() {
            return ESocketOnKey.START_GAME;
        }

        @Override
        public boolean needsToBeStored() {
            return false;
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

        @Override
        public boolean needsToBeStored() {
            return false;
        }
    },

    GAME_ENDED {
        @Override
        public Class getValueClass() {
            return Boolean.class;
        }

        @Override
        public boolean needsToBeStored() {
            return false;
        }
    },

    SOCKET_SERVER_ADDRESS {
        @Override
        public Class getValueClass() {
            return String.class;
        }

        @Override
        public Object getDefaultValue() {
            return "http://192.168.137.1";
        }
    },

    SOCKET_PORT_NUMBER {
        @Override
        public Class getValueClass() {
            return Integer.class;
        }

        @Override
        public Object getDefaultValue() {
            return 4040;
        }
    },

    PLAYERS {
        @Override
        public Class getValueClass() {
            return Players.class;
        }

        @Override
        public ESocketOnKey getSocketOnKey() {
            return ESocketOnKey.PLAYERS;
        }

        @Override
        public ESocketEmitKey getSocketEmitAskKey() {
            return ESocketEmitKey.ASK_PLAYERS;
        }
    },

    GAME_DURATION {
        @Override
        public Class getValueClass() {
            return Integer.class;
        }

        @Override
        public Object getDefaultValue() {
            return 30;
        }
    },

    LEAVE_LOBBY {
        @Override
        public Class getValueClass() {
            return Boolean.class;
        }
    },

    USE_MOCK_SOCKET_SERVICE {
        @Override
        public Class getValueClass() {
            return Boolean.class;
        }

        @Override
        public boolean needsToBeStored() {
            return false;
        }

        @Override
        public Object getDefaultValue() {
            return true;
        }
    },

    USE_MOCK_BEACON_SERVICE {
        @Override
        public Class getValueClass() {
            return Boolean.class;
        }

        @Override
        public boolean needsToBeStored() {
            return false;
        }

        @Override
        public Object getDefaultValue() {
            return true;
        }
    };




    public static EStateManagerKey convertFromString(String stringKey) {
        for (EStateManagerKey key : EStateManagerKey.values()) {
            if (key.toString().equals(stringKey)) {
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

    @Override
    public Object getDefaultValue() {
        return PrimitiveDefaults.getDefaultValue(getValueClass());
    }

    @Override
    public boolean needsToBeStored() {
        return true;
    }


}
