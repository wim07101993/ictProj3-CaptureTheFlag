package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.stateManager;

import java.lang.reflect.Type;

class State implements IState {
    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    private Type type;

    private boolean canGet = true;
    private boolean canSet = true;


    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    public State(  Type type) {
        this.type = type;
    }


    public State( Type type, boolean canGet, boolean canSet) {
        this.type = type;
        this.canSet = canSet;
        this.canGet = canGet;
    }

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    /* ------------------------- GETTERS ------------------------- */

  @Override
    public Type getType() {
        return type;
    }

    @Override
    public boolean canSet() {return canSet;}

    @Override
    public boolean canGet() {return  canGet;}

    /* ------------------------- SETTERS ------------------------- */

}
