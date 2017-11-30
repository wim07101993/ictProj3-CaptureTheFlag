package comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService;

import comwim07101993ictproj3_capturetheflag.github.caperevexillum.services.socketService.interfaces.ISocketKey;

/**
 * Created by wimva on 27/11/2017.
 */

public class SocketValueChangedArgs {
    private ISocketKey key;
    private Object args;

    public SocketValueChangedArgs(ISocketKey key, Object args) {
        this.key = key;
        this.args = args;
    }

    public ISocketKey getKey() {
        return key;
    }

    public Object getArgs() {
        return args;
    }
}
