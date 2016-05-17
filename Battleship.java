import java.io.*;
import java.util.*;

public class Battleship
{
	private char[][] personalMap = new char[10][10];
	private char[][] enemyMap = new char[10][10];

	private int[] nums = new int[4];

	public Battleship()
	{
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
			{
				personalMap[i][j] = '-';
				enemyMap[i][j] = '-';
			}

		for (int i = 0; i < 4; i++)
			nums[i] = i + 1;
		ArrayList<String> ships = readFromFile("shipPlacement");
		for (String line : ships)
			placeShip(line);
	}
	
	public static ArrayList<String> readFromFile(String fileName)
	{
		BufferedReader in = null;
		FileInputStream fis = null;
		ArrayList<String> ret = new ArrayList<String>();

		try
		{
			fis = new FileInputStream(fileName);
			in = new BufferedReader(new InputStreamReader(fis));

			String line = in.readLine();
			while (line != null)
			{
				ret.add(line);
				line = in.readLine();
			}
		}
		catch (IOException ex){ System.out.println(ex.toString()); }

		return ret;
	}

	public String printMaps()
	{
		String ret = "<h3>Your layout</h3>";
		ret += "<table border='1'><tr><th></th><th>A</th><th>B</th><th>C</th><th>D</th><th>E</th>";
		ret += "<th>F</th><th>G</th><th>H</th><th>I</th><th>J</th></tr>";
		for (int i = 0; i < 10; i++)
		{
			ret += "<tr><td>" + i + "</td>";
			for (int j = 0; j < 10; j++)
			{
				ret += "<td>"+personalMap[i][j]+"</td>";
			}
			ret += "</tr>";
		}
		ret += "</table>";
		
		ret += "<h3>Enemy layout</h3>";
		ret += "<table border='1'><tr><th></th><th>A</th><th>B</th><th>C</th><th>D</th><th>E</th>";
		ret += "<th>F</th><th>G</th><th>H</th><th>I</th><th>J</th></tr>";
		for (int i = 0; i < 10; i++)
		{
			ret += "<tr><td>" + i + "</td>";
			for (int j = 0; j < 10; j++)
			{
				ret += "<td>"+enemyMap[i][j]+"</td>";
			}
			ret += "</tr>";
		}
		ret += "</table>";
		return ret;
	}

	/*
		recieves input in format "type,dir,row,col"
		eg "2,h,1,a" will place a ship of length 2
			horizontally at coordinate 1a
	*/
	public void placeShip(String input)
	{
		char dir;
		int type;
		type = input.charAt(0) - '0';
		input = input.substring(input.indexOf(',') + 1);
		dir = input.charAt(0);
		input = input.substring(input.indexOf(',') + 1);
		int[] rc = parseCoordinate(input);

		System.out.println("Row:" + rc[0] + " Col: " + rc[1]);
		if (nums[type - 1] > 0)
			for (int i = 0; i < type; i++)
				if (dir == 'h')
				{
					personalMap[rc[0]][rc[1] + i] = '#';
					//enemyMap[rc[0]][rc[1] + i] = '#';
				}
				else
				{
					personalMap[rc[0] + i][rc[1]] = '#';
					//enemyMap[rc[0] + i][rc[1]] = '#';
				}
	}

	/*
		Incoming missile from enemy at the coordinate
		format "row,col"
	*/
	public boolean incoming(String coordinate)
	{
		return hit(coordinate,personalMap);
	}

	public boolean outgoing(String coordinate, boolean h)
	{
		int[] rc = htmlCoordinate(coordinate);
		if (h)
		{
			enemyMap[rc[0]][rc[1]] = '*';
			return true;
		}
		else
		{
			enemyMap[rc[0]][rc[1]] = 'x';
			return false;
		}
	}

	public boolean hit(String coordinate, char[][] map)
	{
		int[] rc = htmlCoordinate(coordinate);
		boolean ret = false;
		if (map[rc[0]][rc[1]] == '#')
		{
			ret = true;
		}

		map[rc[0]][rc[1]] = 'x';
		return ret;
	}
	
	

	public boolean checkMap(char[][] map)
	{
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				if (map[i][j] == '#')
				{
					return false;
				}
			}
		}

		return true;
	}

	private int[] parseCoordinate(String coordinate)
	{
		int[] rc = new int[2];
		rc[0] = Integer.parseInt(coordinate.substring(0, coordinate.indexOf(','))) - 1;
		coordinate = coordinate.substring(coordinate.indexOf(',') + 1);
		rc[1] = coordinate.charAt(0) - 'a';

		return rc;
	}
	
	private int[] htmlCoordinate(String coordinate)
	{
		int[] rc = new int[2];
		rc[0] = Integer.parseInt(coordinate.substring(0, coordinate.indexOf('%'))) - 1;
		coordinate = coordinate.substring(coordinate.indexOf('%') + 3);
		rc[1] = coordinate.charAt(0) - 'a';

		return rc;
	}
}