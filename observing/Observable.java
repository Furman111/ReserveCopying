package observing;

/**
 * Created by Furman on 14.02.2017.
 */
public interface Observable {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}
