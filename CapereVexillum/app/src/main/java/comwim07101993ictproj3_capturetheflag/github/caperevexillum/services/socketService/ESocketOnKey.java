package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Flag;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.LobbySettings;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Team;
import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.interfaces.ISocketKey;

/**
 * Created by wimva on 25/11/2017.
 */

public enum ESocketOnKey implements ISocketKey {

    HOST {
        private static final String IDENTIFIER = "host";

        @Override
        public String getStringIdentifier() {
            return IDENTIFIER;
        }
    },

    WAS_LOBBY_CREATED{
        @Override
        public String getStringIdentifier() {
            return "wasLobbyCreated";
        }

        @Override
        public Class getValueClass() {
            return LobbySettings.class;
        }
    },

    START {
        private static final String IDENTIFIER = "start";

        @Override
        public String getStringIdentifier() {
            return IDENTIFIER;
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

    GET_LOBBY_ID {
        @Override
        public String getStringIdentifier() {
            return "getLobbyId";
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
