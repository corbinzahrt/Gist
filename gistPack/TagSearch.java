package gistPack;

import java.util.Scanner;

public class TagSearch {
	
	/**
	 * The begin bracket.
	 */
	private static String TAG_0 ="[";
	
	private static String TAG_1 = "]";
	
	/**
	 * Holds the current command. 
	 */
	private String command;
	
	public String tagSearch(String string){
		Scanner scanner = new Scanner(string);
		while(scanner.hasNext()){
			if(scanner.hasNext(TAG_0)){
				command = scanner.next();
				} 
			else{
				command = "null";
				}
			}
		return command;
		}
	
}

