package ooga.data;

import ooga.data.buildingXML.XMLGameBuilder;

public class XMLGameBuilderTest {
  private final int[][] knownLevelOne = { {1, 5, 6, 6, 1}, {1, 5, 4, 3, 6}, {2, 4, 3, 4, 1}, {1, 5, 2, 4, 1}, {2, 4, 3, 2, 2}, {2, 3, 4, 3, 3}};
  private final boolean[][] uncoveredCells = {{false, false, false, true}, {false, false, false, true}, {false, false, false, true}, {false, false, false, true}, {false, false, false, true}, {false, false, false, true}};
  private final String MAIN_TAG = "game";
  private final String CREATED_GAME_PATH = "data/defaultGames/jujuTest.xml";

  //XMLGameBuilder gameBuilder = new XMLGameBuilder(MAIN_TAG, CREATED_GAME_PATH, knownLevelOne, uncoveredCells);

}
