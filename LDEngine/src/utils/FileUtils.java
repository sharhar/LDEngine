package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class FileUtils {
	
	public static String readFile(String path) {
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line;
			while((line = reader.readLine()) != null) {
				result.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("Could not read file!");
			e.printStackTrace();
			System.exit(-1);
		}
		return result.toString();
	}
	
	public static String loadAsString(String file) {
		File test = new File(file);
		
		if(!test.exists()){
			return null;
		}
		
		String result = "";
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String buffer = "";
			while ((buffer = reader.readLine()) != null) {
				result += buffer + "\n";
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void writeSrting(String path, String text) {
		File file = new File(path);
		
		if(!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		try {
			PrintWriter writer = new PrintWriter(path,"UTF-8");
			writer.print(text);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}