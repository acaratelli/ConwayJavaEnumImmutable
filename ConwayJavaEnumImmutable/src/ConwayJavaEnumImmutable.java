
public class ConwayJavaEnumImmutable {

	//Main method
	//Creates game matrix variable
	//Calls generateStart to have starting board populated
	//prints starting board
	//loops through game rounds playing the game and printing the board at each round
	public static void main(String[] args) {
		
		cellState[][] current = generateStart(5,5);
		
		System.out.println("Start Matrix:");
		printCurrent(current);
		
		for(int gameRound = 1; gameRound <= 10; gameRound++)
		{
			current = nextRound(current);
			System.out.println("Round " + gameRound + ":");
			printCurrent(current);
		}

	}
	
	//enum for each individual cellState
	//Cell has two states, alive (LIFE), dead (DEAD)
	public enum cellState
	{
		LIFE, DEAD
	}
	
	//Generates starting board
	//Parameters: number of rows and columns in the board
	//Returns: starting board of size [numRows][numCols] randomly populated with LIFE/DEAD cellStates
	public static cellState[][] generateStart(int numRows, int numCols)
	{
		cellState[][] startMatrix = new cellState[numRows][numCols];
		for(int row = 0; row < numRows; row++)
		{
			for(int col = 0; col < numCols; col++)
			{
				double r = Math.random();
				if(r > 0.5)
					startMatrix[row][col] = cellState.LIFE;
				else
					startMatrix[row][col] = cellState.DEAD;
			}
		}
		return startMatrix;
	}
	
	//Prints the matrix you send as a parameter
	public static void printCurrent(cellState[][] current)
	{
		for(int row = 0; row < current.length; row++)
		{
			for(int col = 0; col < current[row].length; col++)
			{
				System.out.print(current[row][col] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	//Receives current states of the board
	//Iterates through each spot in the board and uses generateNewCellState to determine new cellState for this round
	//Once all spots have been evaluated, method returns the new board generated from this round
	public static cellState[][] nextRound(cellState[][] current)
	{
		cellState[][] newBoard = new cellState[current.length][current[0].length];
		for(int row = 0; row < current.length; row++)
		{
			for(int col = 0; col < current[0].length; col++)
			{
				newBoard[row][col] = generateNewCellState(current, row, col);
			}
		}
		return newBoard;
	}
	
	//Receives current game board, and the row (curRow) and col (curCol) of the current spot we're evaluating
	//Uses getNeighborCount to determine the number of surrounding LIFE cells
	//Uses neighborCount to determine the new state of the current spot
	//Returns the new state
	public static cellState generateNewCellState(cellState[][] current, int curRow, int curCol)
	{
		int neighborCount = getNeighborCount(current, curRow, curCol);
		if(neighborCount < 2 || neighborCount > 3)
			return cellState.DEAD;
		else if(neighborCount == 2)
			return current[curRow][curCol];
		else
			return cellState.LIFE;
	}
	
	//Receives current game board, and the row (curRow) and col (curCol) of the current spot we're evaluating
	//This method looks at surrounding cells to count how many are LIFE
	//Returns the count
	public static int getNeighborCount(cellState[][] current, int curRow, int curCol)
	{
		int neighborCount = 0;
		for(int neighborRow = curRow-1; neighborRow <= curRow+1; neighborRow++)
		{
			for(int neighborCol = curCol-1; neighborCol <= curCol+1; neighborCol++)
			{
				if(neighborRow != curRow || neighborCol != curCol)
				{
					try{
						if(current[neighborRow][neighborCol] == cellState.LIFE)
							neighborCount += 1;
					}
					catch(ArrayIndexOutOfBoundsException e)
					{
						continue;
					}
				}
			}
		}
		return neighborCount;
	}

}
