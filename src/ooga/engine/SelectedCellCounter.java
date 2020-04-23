package ooga.engine;

/**
 * Interface used to increment or decrement a count based on boolean true or false.
 * Example usage in Grid/Cell to enable a Cell to increment/decrement selected cell count in Grid when
 * Cell is selected/deselected.
 */
@FunctionalInterface
public interface SelectedCellCounter {
    /**
     * Use to increment or decrement a count based on boolean.
     * @param increment true if increment, false if decrement
     */
    void changeCount(boolean increment);
}
