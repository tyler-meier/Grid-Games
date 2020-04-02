package ooga.data;

public class Data implements DataBuilder {

  @Override
  public DataObject getPlayerProfile(String username, String password) {
    return null;
  }

  @Override
  public void saveNewPlayerProfile(String username, String password) {

  }

  @Override
  public DataObject getEngineAttributes() {
    return null;
  }

  @Override
  public void saveConfigurationFile(String username, DataObject engineAttributes) {

  }

  @Override
  public DataObject loadPreviousGame(String username, String gameType) {
    return null;
  }

  @Override
  public DataObject loadConfigurationFile(String gameType) {
    return null;
  }
}
