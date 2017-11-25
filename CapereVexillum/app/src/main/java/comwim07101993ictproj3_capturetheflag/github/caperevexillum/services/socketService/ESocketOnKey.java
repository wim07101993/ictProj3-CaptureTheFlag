package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.models.Team;

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

        @Override
        public Class getValueClass() {
            return null;
        }
    },

    START {
        private static final String IDENTIFIER = "start";

        @Override
        public String getStringIdentifier() {
            return IDENTIFIER;
        }

        @Override
        public Class getValueClass() {
            return null;
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
            return float.class;
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
    };

    @Override
    public EMode getMode() {
        return EMode.ON;
    }
}
