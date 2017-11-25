package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.interfaces.ISocketKey;

/**
 * Created by wimva on 25/11/2017.
 */

public enum ESocketEmitKey implements ISocketKey {

    ASK_FLAGS {
        private static final String IDENTIFIER = "askFlags";

        @Override
        public String getStringIdentifier() {
            return IDENTIFIER;
        }

        @Override
        public Class getValueClass() {
            return null;
        }
    },

    ASK_TEAMS {
        private static final String IDENTIFIER = "askTeams";

        @Override
        public String getStringIdentifier() {
            return IDENTIFIER;
        }

        @Override
        public Class getValueClass() {
            return null;
        }
    },

    ASK_TIME {
        private static final String IDENTIFIER = "askTime";

        @Override
        public String getStringIdentifier() {
            return IDENTIFIER;
        }

        @Override
        public Class getValueClass() {
            return null;
        }
    };


    @Override
    public EMode getMode() {
        return EMode.EMIT;
    }
}
