package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileParser {

	public static ArrayList<String> parseFile(String filename) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = br.readLine()) != null) {
				data.add(line);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public static void saveFile(ArrayList<String> data, String filename) {
		try {
			PrintWriter out = new PrintWriter(filename);
			for (String str : data)
				out.println(str);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
