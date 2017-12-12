package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.interfaces.ISocketKey;

/**
 * Created by wimva on 25/11/2017.
 */

// TODO WIM change base get value class to return String.class
public enum ESocketEmitKey implements ISocketKey {
    START_GAME {
        private static final String IDENTIFIER = "startLobby";

        @Override
        public String getStringIdentifier() {
            return IDENTIFIER;
        }
        public Class getValueClass() {
            return Integer.class;
        }
    },
    ASK_FLAGS {
        private static final String IDENTIFIER = "askFlags";

        @Override
        public String getStringIdentifier() {
            return IDENTIFIER;
        }
    },


    ASK_TEAMS {
        private static final String IDENTIFIER = "askTeams";

        @Override
        public String getStringIdentifier() {
            return IDENTIFIER;
        }
    },
    ASK_TIME {
        private static final String IDENTIFIER = "askTime";

        @Override
        public String getStringIdentifier() {
            return IDENTIFIER;
        }
    },

    CAPTURE_FLAG {
        private static final String IDENTIFIER = "captureFlag";

        @Override
        public String getStringIdentifier() {
            return IDENTIFIER;
        }

        @Override
        public Class getValueClass() {
            return String.class;
        }
    },

    JOIN_LOBBY {
        private static final String IDENTIFIER = "joinLobby";

        @Override
        public String getStringIdentifier() {
            return IDENTIFIER;
        }
        @Override
        public Class getValueClass() {
            return String.class;
        }
    },

    ASK_PLAYERS {
        private static final String IDENTIFIER = "askPlayers";

        @Override
        public String getStringIdentifier() {
            return IDENTIFIER;
        }

        @Override
        public Class getValueClass() {
            return Integer.class;
        }
    },

    CREATE_LOBBY {
        private static final String IDENTIFIER = "createLobbyNew";

        @Override
        public String getStringIdentifier() {
            return IDENTIFIER;
        }

        @Override
        public Class getValueClass() {
            return String.class;
        }
    },

    JOIN_TEAM {
        private static final String IDENTIFIER = "joinTeam";
        @Override
        public String getStringIdentifier() {
            return IDENTIFIER;
        }
        @Override
        public Class getValueClass() {
            return String.class;
        }
    },

    HOST_LEFT {
        private static final String IDENTIFIER = "hostLeft";
        @Override
        public String getStringIdentifier() {
            return IDENTIFIER;
        }
        @Override
        public Class getValueClass() {
            return Integer.class;
        }
    },

    LEAVE_LOBBY {
        private static final String IDENTIFIER = "leaveLobby";
        @Override
        public String getStringIdentifier() {
            return IDENTIFIER;
        }
        @Override
        public Class getValueClass() {
            return Integer.class;
        }
    };




    @Override
    public EMode getMode() {
        return EMode.EMIT;
    }

    @Override
    public Class getValueClass() {
        return null;
    }


}
