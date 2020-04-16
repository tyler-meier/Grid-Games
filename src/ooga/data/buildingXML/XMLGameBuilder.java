package ooga.data.buildingXML;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Element;

/**
 * Able to write an XML file based on a map of data with the
 * tags as the keys and the String text encoded as the values
 */

public class XMLGameBuilder extends XMLBuilder {

  private final String NUM_ROWS_TAG= "numRows";
  private final String NUM_COLUMNS_TAG= "numColumns";
  private final String ROW_DELIMINATOR = " ";
  private final String ROW_TAG = "row";
  private final String UNCOVERED_CELL_TAG = "uncoveredCellRow";

  private Map<String, String> dataToWrite;
  private int [][] grid;
  private boolean [][] uncoveredCells;
  private int numRows;
  private int numCols;

  private int ZERO_INDEX = 0;

  public XMLGameBuilder(String mainTag, String pathName, Map<String, String> dataToWrite, int[][] grid, boolean[][] uncoveredCells) {
    super(mainTag, pathName);
    this.dataToWrite = dataToWrite;
    this.grid = grid;
    this.uncoveredCells = uncoveredCells;
    numRows = grid.length;
    numCols = grid[ZERO_INDEX].length;
    createDocument(mainTag, pathName);
  }

  /**
   * Adds different entry for every entry in the map
   * @param root
   */
  @Override
  public void addElementsToRoot(Element root)
  {
    for(String tag : dataToWrite.keySet())
    {
        Element temp = createElement(tag, dataToWrite.get(tag));
        root.appendChild(temp);
    }
    addGrid(root);
    if(uncoveredCells!=null)
    {
      writeBooleanGrid(root);
    }
  }

  private void addGrid(Element root)
  {
    Element numRowsElement = createElement(NUM_ROWS_TAG, Integer.toString(numRows));
    root.appendChild(numRowsElement);

    Element numColumnsElement = createElement(NUM_COLUMNS_TAG, Integer.toString(numCols));
    root.appendChild(numColumnsElement);

    for(int r = ZERO_INDEX; r < numRows; r++)
    {
      List<String> rowValues = new ArrayList<>();
      for(int c = ZERO_INDEX; c < numCols; c++)
      {
        rowValues.add(Integer.toString(grid[r][c]));
      }
      String row = String.join(ROW_DELIMINATOR, rowValues);
      Element temp = createElement(ROW_TAG, row);
      root.appendChild(temp);
    }
  }

  private void writeBooleanGrid(Element root)
  {
    for(int r = ZERO_INDEX; r < numRows; r++)
    {
      List<String> rowValues = new ArrayList<>();
      for(int c = ZERO_INDEX; c < numCols; c++)
      {
        rowValues.add(Boolean.toString(uncoveredCells[r][c]));
      }
      String row = String.join(ROW_DELIMINATOR, rowValues);
      Element temp = createElement(UNCOVERED_CELL_TAG, row);
      root.appendChild(temp);
    }
  }


}
