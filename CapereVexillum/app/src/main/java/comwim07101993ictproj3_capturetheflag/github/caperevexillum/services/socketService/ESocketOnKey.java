package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flag;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Team;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.interfaces.ISocketKey;

/**
 * Created by wimva on 25/11/2017.
 */

// TODO WIM change base get value class to return String.class
public enum ESocketOnKey implements ISocketKey {

    HOST {
        private static final String IDENTIFIER = "host";

        @Override
        public String getStringIdentifier() {
            return IDENTIFIER;
        }
    },

    WAS_LOBBY_CREATED{
        private static final String IDENTIFIER = "wasLobbyCreated";

        @Override
        public String getStringIdentifier() {
            return IDENTIFIER;
        }

        @Override
        public Class getValueClass() {
            return String.class;
        }
    },

    START_GAME {
        private static final String IDENTIFIER = "startGame";

        @Override
        public String getStringIdentifier() {
            return IDENTIFIER;
        }

        @Override
        public Class getValueClass() {
            return String.class;
        }
    },

    RESYNC_TIME {
        private static final String IDENTIFIER = "reSyncTime";

        @Override
        public String getStringIdentifier() {
            return IDENTIFIER;
        }

        @Override
        public Class getValueClass() {
            return Float.class;
        }
    },

    SYNC_FLAGS {
        private static final String IDENTIFIER = "syncFlags";

        @Override
        public String getStringIdentifier() {
            return IDENTIFIER;
        }

        @Override
        public Class getValueClass() {
            return Flag[].class;
        }
    },

    SYNC_TEAM {
        private static final String IDENTIFIER = "syncTeam";

        @Override
        public String getStringIdentifier() {
            return IDENTIFIER;
        }

        @Override
        public Class getValueClass() {
            return Team[].class;
        }
    },

    PLAYERS {
        private static final String IDENTIFIER = "players";

        @Override
        public String getStringIdentifier() {
            return IDENTIFIER;
        }

        @Override
        public Class getValueClass() {
            return String.class;
        }
    },

    JOINED_LOBBY {
        @Override
        public String getStringIdentifier() {
            return null;
        }
    },

    GET_LOBBY_ID {
        private static final String IDENTIFIER = "getLobbyId";

        @Override
        public String getStringIdentifier() {
            return IDENTIFIER;
        }
    };


    @Override
    public EMode getMode() {
        return EMode.ON;
    }

    @Override
    public Class getValueClass() {
        return null;
    }
}
