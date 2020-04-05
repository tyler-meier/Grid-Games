package ooga.engine;

@FunctionalInterface
public interface SelectedCellCounter {
    void changeCount(boolean increment);
}
