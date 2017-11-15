package comwim07101993ictproj3_capturetheflag.github.caperevexillum.helpers.propertyChanged;

/**
 * Created by wimva on 13/11/2017.
 */

public interface INotifyPropertyChanged {
    void addPropertyChangedListener(IPropertyChangedListener listener);
    void removePropertyChangedListener(IPropertyChangedListener listener);
}
