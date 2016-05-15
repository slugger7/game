import java.io.*;

public class Battleship
{
	private char[][] personalMap = new char[10][10];
	private char[][] enemyMap = new char[10][10];

	private int four = 1, three = 2, two = 3, one = 4;

	public Battleship()
	{
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
			{
				personalMap[i][j] = '-';
				enemyMap[i][j] = '-';
			}
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

	public void placeShip(String input)
	{
		int r, c;
		if (input.length() == 4)
		{
			r = input.charAt(2) - '0' - 1;
			c = input.charAt(3) - 'a';
			System.out.println(r + " " + c);
			switch (input.charAt(0))
			{
				case '1':
					if (one > 0)
					{

					}
					break;
				case '2':
					break;
				case '3':
					break;
				case '4':
					break;
			}
		}
	}
}