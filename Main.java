import java.io.*;
import java.util.*;

public class Main
{
	public static void main(String[] args)
	{
		ArrayList<String> ships = readFromFile("shipPlacement");
		Battleship game = new Battleship();
		for (String line : ships)
			game.placeShip(line);
		game.printPersonal();
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
				System.out.println(line);
				line = in.readLine();
			}
		}
		catch (IOException ex){ System.out.println(ex.toString()); }

		return ret;
	}
}