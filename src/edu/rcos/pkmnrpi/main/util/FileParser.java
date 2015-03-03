package edu.rcos.pkmnrpi.main.util;

import java.io.BufferedReader;
import java.io.File;
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

	public static void saveFile(ArrayList<String> data, File file) {
		try {
			PrintWriter out = new PrintWriter(file);
			for (String str : data)
				out.println(str);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<ArrayList<String>> parseSeperatedFile(String filename, char c) {
		ArrayList<String> temp = new ArrayList<String>();
		ArrayList<ArrayList<String>> all = new ArrayList<ArrayList<String>>();
		for (String str : parseFile(filename)) {
			if (isUniform(str, c)) {
				all.add(temp);
				temp = new ArrayList<String>();
			} else
				temp.add(str);
		}
		all.add(temp);
		return all;
	}

	public static boolean isUniform(String str, char c) {
		if (str.isEmpty())
			return false;
		for (char h : str.toCharArray())
			if (c != h)
				return false;
		return true;
	}
}
