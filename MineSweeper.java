
/*
  File: TestMineSweeper.java

  Description:

  Student Name: Parth Bhateja

  Student UT EID: pb7993
/*  Course Name: CS 312

  Unique Numbers:

  Date Created:

  Date Last Modified:

*/

class minesweeper 
{
  /** 
    mine and clue values, 9 - mine, 0-8 clue values
  */
  public int[][] mines; 

  /** 
    tile values:
       0 - open      
       1 - closed
       2 - question 
       3 - mine
  */
  public int[][] tiles;

  /** 
    Game status - win, lose, play
  */
  private String status;

  /** 
    Default constructor
      *	board size 9 x 9
      * create mines and tile arrays
      * place mines
      * calculate clues
      * set game status to play   
  */
  public minesweeper() 
  {
    initGame(9, 9);
  }

  /** 
    Non-default constructor
      * use specifies board size
      * create mines and tile arrays
      * place mines
      * calculate clues
      * set game status to play
      * newRows number of rows for grid
      * newCols number of columns for grid
  */
  public minesweeper (int newRows, int newCols) 
  {
    initGame(newRows, newCols);
  }

  /** 
    Returns the game status - "play", "win", or "lose"
  */
  public String getStatus () 
  {
    return status;
  }

  /** 
    Returns number of rows in the board
  */
  public int getRows () 
  {
    return mines.length;
  }

  /** 
    Returns the number of columns in the board
  */
  public int getCols () 
  {
    return mines[0].length;
  }

  /** 
    Returns the value of the mines array at r,c
    * -1 is returned if invalid r,c
    * r row index
    * c column index
  */
  public int getMines (int r, int c) 
  {
    if (validIndex(r, c)) 
    {
      return mines[r][c];
    } 
    else 
    {
      return -1;
    }
  }

  /** 
    Returns the value of the tiles array at r,c
    * -1 is returned if invalid r,c
    * r row index
    * c column index
  */
  public int getTiles (int r, int c) 
  {
    if (validIndex(r, c)) 
    {
      return tiles[r][c];
    } 
    else 
    {
      return -1;
    }
  }

  /** 
    Mark tile - 
    * open tile 
    * flag tile as mine 
    * set tile as question mark 
    * close tile
    * r row index
    * c column index
    * t: 0 - open, 1 - close, 2 - question, 3 - mine
    * invalid r,c values must be ignored
    * a tile that is opened must stay open
    * a tile that is marked as a flag (ie. tile[][] value 3) can not be opened
    * tile values can only change when game status is "play"
    * game status must be updated after a tile is opened
    * implementation must be recursive; you can use a helper method
  */
  public void markTile (int r, int c, int t) 
  {
    if(validIndex(r,c) && getStatus().equals("play")) //making sure we're playing
    {
      if(getTiles(r,c) != 0)
      {
        if(t == 0)
        {
          if(getTiles(r,c) != 3) //make sure it isnt' flagged
          {
            if(getMines(r,c) == 9)
            {
              tiles[r][c] = 0;
              status = "lose";
            }
            else
            {
              markTileHelper(r,c);
              status = "play";
            }
          }
          else
          {
            tiles[r][c] = t;
            status = "play";
          }
        }
      }
      int count = 0;
      for(int i=0; i<getRows(); i++)
      {
        for(int j=0; j<getCols(); j++)
        {
          if(getTiles(i,j) == 0 || getMines(i,j) == 9)
          {
            count++;
          }
        }
      }
      
      if(count == getRows() * getCols() && !getStatus().equals("lose"))
      {
        status = "win";
      }
    }
  }
  
  private void markTileHelper(int r, int c)
  {
    if(validIndex(r,c) == true && getTiles(r,c) != 0 && getTiles(r,c) != 3)
    {
      tiles[r][c] = 0;
      if(getMines(r,c) == 0)
      {
        for(int i = r-1; i<= r+1; i++)
        {
          for(int j = c-1; j<= c+1; j++)
          {
            markTileHelper(i,j);
          }
        }
      }
    }
  }

  /** 
    Returns mines array as a String
  */
  public String toStringMines() 
  {
    String result = "\n";

    for (int r = 0; r < mines.length; r++) 
    {
      for (int c = 0; c < mines[r].length; c++)
	result = result + mines[r][c];

      result += "\n";
    }

    return result;
  }

  /**
    Returns tiles array as a String
  */
  public String toStringTiles() 
  {
    String result = "\n";

    for (int r = 0; r < tiles.length; r++) 
    {
      for (int c = 0; c < tiles[r].length; c++)
	result = result + tiles[r][c];

      result += "\n";
    }

    return result;
  }

  /** 
    Returns game board as String
  */
  public String toStringBoard () 
  {
    String result = "";

    for (int r = 0; r < tiles.length; r++) 
    {
      for (int c = 0; c < tiles[r].length; c++) 
      {
	result += this.getBoard(r, c);
      }
      result += "\n"; //advance to next line
    }

    return result;
  }

  /** 
    getBoard - determines current game board character for r,c position 
    * using the value of the mines[][] and tiles[][]array
    * '1'-'8'  opened tile showing clue value
    * ' '      opened tile blank
    * 'X'      tile closed
    * '?'      tile closed marked with ?
    * 'F'      tile closed marked with flag
    * '*'      mine
    * 
    * '-'      if game lost, mine that was incorrectly flagged
    * '!'      if game lost, mine that ended game
    * 'F'      if game won, all mines returned with F
  */
  public char getBoard (int r, int c) 
  {
    char result = 'X';
    if(getTiles(r,c) == 0 && getMines(r,c) == 0) //getting blank
      result = ' ';
    else if(getTiles(r,c) == 0 && getMines(r,c) >= 1 && getMines(r,c) <=8) //getting clue
      result = Character.toChars(getMines(r,c) + 48)[0];
    else if(getTiles(r,c) == 3) //getting flag
      result = 'F';
    else if(getTiles(r,c) == 2) //getting question
      result = '?';
    else if(getTiles(r,c) == 1) //getting closed
      result = 'X';
    
    if(getStatus().equals("lose"))
    {
      if(getTiles(r,c) == 0 && getMines(r,c) == 0)
        result = '!';
      else if(getTiles(r,c) >= 1 && getTiles(r,c) <=3 && getMines(r,c) == 9)
        result = '*';
      else if(getTiles(r,c) == 3 && !(getMines(r,c) == 9))
        result = '-';
    }
    
    if(gameWon() == true && getMines(r,c) == 9)
    {
      result = 'F';
    }
    
    return result;
    
  }

  /** 
    Create mines & tiles array
    * place mines
    * update clues
    * newRows number of rows for grid
    * newCols number of columns for grid
  */
  private void initGame (int newRows, int newCols) 
  {
    //allocate space for mines and tiles array
    if ((newRows >= 1) && (newCols >= 1)) 
    {
      mines = new int[newRows][newCols];
      tiles = new int[newRows][newCols];

      //init tiles array
      resetTiles();

      //place mines
      placeMines();

      //update clues
      calculateClues();

      //set game status
      status = "play";
    }
  }

  /** 
    Sets all tiles to 1 - closed
  */
  private void resetTiles () 
  {
    for(int i = 0; i<getRows(); i++)
    {
      for(int j = 0; j<getCols(); j++)
      {
        tiles[i][j] = 1;
      }
    }
  }

  /** 
    Places mines randomly on grid
    * integer value 9 represents a mine
    * number of mines = 1 + (number of columns * number rows) / 10
    * minimum number of mines = 1
  */
  private void placeMines () 
  {
    int numMines = 0;
    int targetMines = 1 + (getRows() * getCols())/10;
    while(numMines < targetMines)
    {
      int row = (int)(Math.random() * getRows());
      int col = (int)(Math.random() * getCols());
      if(mines[row][col] != 9)
      {
        mines[row][col] = 9;
        numMines++;
      }
    }
  }

  /** 
    Calculates clue values and updates
    * clue values in mines array
    * integer value 9 represents a mine
    * clue values will be 0 ... 8
  */
  private void calculateClues () 
  {
    for(int i=0; i<getRows(); i++)
    {
      for(int j=0; j< getCols(); j++)
      {
        if(mines[i][j] != 9)
        {
          mines[i][j] = calculateCluesHelper(i,j);
        }
      }
    }
  }
  
  private int calculateCluesHelper(int row, int col)
  {
    int count = 0;
    for(int i = row-1; i<= row+1; i++)
    {
      for(int j = col-1; j<= col+1; j++)
      {
        if(validIndex(i,j) && mines[i][j] == 9)
          count ++;
      }
    }
    return count;
  }

  /** 
    Determines if x,y is valid position
    * x row index
    * y column index
    * true if valid position on board,
    * false if not valid board position
  */
  private boolean validIndex (int x, int y) 
  {
    if( x >= 0 && x < getRows() && y >= 0 && y< getCols())
       return true; 
    return false; 
  }

  /** 
    Returns game status - true if game won or false
                          if game not won
  */
  private boolean gameWon() 
  {
    if( getStatus().equals("win"))
      return true;
   else 
        return false;
  }

}
public class TestMineSweeper 
{
  public static void main(String[] args) 
  {
    // create new minesweeper instance 2 rows by 5 columns
    minesweeper game = new minesweeper (3, 5);

    // display mines
    System.out.println ( game.toStringMines() );
        
    // display tiles
    System.out.println ( game.toStringTiles() );
	    
    // display board
    System.out.println ( game.toStringBoard() );
		
    // mark tile at (0, 0) as Open
    game.markTile (0, 0, 0);
	
    // mark tile at (0, 1) as Question Mark
    game.markTile (0, 1, 2);
			
    // mark tile at (0, 0) as Mine
    game.markTile (0, 2, 3);
		  
    // display tiles 
    System.out.println ( game.toStringTiles() );
	   
    // display board
    System.out.println ( game.toStringBoard() );
		
  }
}