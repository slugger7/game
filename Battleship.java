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
	}

	public void printMaps()
	{
		System.out.println("Your layout \t\t\tEnemy");
		System.out.println("|  |A|B|C|D|E|F|G|H|I|J|\t|  |A|B|C|D|E|F|G|H|I|J|");
		String tmpe, tmpp;
		for (int i = 0; i < 10; i++)
		{
			tmpp = "|" + String.format("%02d",(i + 1)) + "|";
			tmpe = tmpp;
			for (int j = 0; j < 10; j++)
			{
				tmpp += personalMap[i][j] + "|";
				tmpe += enemyMap[i][j] + "|";
			}
			System.out.println(tmpp + '\t' + tmpe);
		}
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
					personalMap[rc[0]][rc[1] + i] = '#';
				else
					personalMap[rc[0] + i][rc[1]] = '#';
	}

	/*
		Incoming missile from enemy at the coordinate
		format "row,col"
	*/
	public boolean incoming(String coordinate)
	{
		return hit(coordinate,personalMap);
	}

	public boolean outgoing(String coordinate)
	{
		return hit(coordinate,enemyMap);
	}

	public boolean hit(String coordinate, char[][] map)
	{
		int[] rc = parseCoordinate(coordinate);
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
}