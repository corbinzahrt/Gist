package Tests;

import gistPack.GistObject;
import gistPack.GistTest;
import gistPack.State;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import API.GistEnvironment;

public class TextFormatTests extends State {
	
	/**
	 * The format test file.
	 */
	private static File text = new File("format.txt");
	
	
	/**
	 * Holds each line of the text document "format.txt"
	 */
	private static ArrayList<String> lines = new ArrayList<>();
	
	/**
	 * The length of formatting margins and bars.
	 * Also the maximum width allowed for a string in this document. 
	 */
	private static int width = 80;
	
	/**
	 * Width in ratio to width.
	 */
	private static int smallWidth= 75;
	
	

	
	
	public static void main(String[] args) throws IOException, ClassNotFoundException{
		String fileName = "GitKnows.txt";
		PrintWriter output = new PrintWriter(fileName);
		
		widthPrint(output, "=");
		bodyFormat(output, "Title Of Repository");
		widthPrint(output, "=");
		bodyFormat(output, "Subtitle");
		printX(output, "-", smallWidth);
		bodyFormat(output, "I am testing this feature. I think I need to write enough characters to go over the smallWidth limit.");
		lineX(output, " ", 20, smallWidth);
		printX(output, "-", smallWidth);
		widthPrint(output, "=");
		bodyFormat(output, "End.");
		widthPrint(output, "=");
		output.close();
		fillUp(new File(fileName));
		printLengths();
	
	}
	
	private static void lineX(PrintWriter out, String text, int number, int lineLength){
		for(int i= 0; i < number; i++){
			printX(out, text, lineLength);
		}
	}
	
	private static void bodyFormat(PrintWriter out, String text){
		ArrayList<String> buffy = new ArrayList<>();
		if(text.length() > smallWidth){
			
			while(text.length() > smallWidth){
				String buffer = text.substring(0, smallWidth);
				/**
				 * This statement will break the line at the last whitespace
				 * to avoid breaking up words. 
				 */
				if(buffer.contains(" ")){
					int whiteSpace = buffer.lastIndexOf(" ");
					buffer = buffer.substring(0, whiteSpace);
					out.println(buffer);
					text = text.substring(whiteSpace);
				}
				else{
					out.println(buffer);
					text = text.substring(smallWidth);
				}
				
			}
			
		}
		text = text.trim();
		out.println(text);
		
	}
	
	private static void widthPrint(PrintWriter out, String test){
		for(int i = 0; i < width; i++){
			out.print(test);
		}
		out.println();
	}
	
	private static void printX(PrintWriter out, String test, int width){
		for(int i = 0; i < width; i++){
			out.print(test);
		}
		out.println();
	}

	/**
	 * Pass a file. Each line in the document is added to the array lines.
	 * @param file
	 * @throws FileNotFoundException
	 */
	private static void fillUp(File file) throws FileNotFoundException{
		Scanner scanner = new Scanner(file);
		while(scanner.hasNextLine()){
			lines.add(scanner.nextLine());
		}
		
	}
	
	/**
	 * Print the length of each line in the array lines. 
	 */
	private static void printLengths(){
		for(String e: lines){
			System.out.print(e);
			int margin =width - e.length();
			String whiteSpace = " ";
			while(margin > 0){
				whiteSpace += " ";
				margin--;
			}
			System.out.println(whiteSpace + e.length() + " long");
		}
		
	}

	
}


