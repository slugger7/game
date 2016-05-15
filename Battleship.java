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
	public void printPersonal()
	{
		printMap(personalMap);
	}

	private void printMap(char[][] map)
	{
		System.out.println("|  |A|B|C|D|E|F|G|H|I|J|");
		String tmp;
		for (int i = 0; i < 10; i++)
		{
			tmp = "|" + String.format("%02d",(i + 1)) + "|";
			for (int j = 0; j < 10; j++)
			{
				tmp += map[i][j] + "|";
			}
			System.out.println(tmp);
		}
	}

	/*
		recieves input in format "type,dir,row,col"
		eg "2,h,1,a" will place a ship of length 2
			horizontally at coordinate 1a
	*/
	public void placeShip(String input)
	{
		int r, c, type;
		char dir;
		type = input.charAt(0) - '0';
		input = input.substring(input.indexOf(',') + 1);
		dir = input.charAt(0);
		input = input.substring(input.indexOf(',') + 1);
		r = Integer.parseInt(input.substring(0,input.indexOf(','))) - 1;
		input = input.substring(input.indexOf(',') + 1);
		c = input.charAt(0) - 'a';

		if (nums[type - 1] > 0)
		{
			for (int i = 0; i < type; i++)
			{
				if (dir == 'h')
				{
					personalMap[r][c + i] = '#';
				}
				else
				{
					personalMap[r + i][c] = '#';
				}
			}
		}
	}
}