package gistPack;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class encapsulates the file management of <code>Gist</code>.<p>
 * Everything for this class revolves around a plain text <code>MASTER_FILE</code> that is located 
 * at <code>System.getProperty("user.dir")</code>.  
 * <code>MASTER_FILE</code> is essentially a list of directories represented by absolute paths. 
 * <p>When <code>GistIO</code> is executed the contents of <code>MASTER_FILE</code> are dumped 
 * into two <code>ArrayList</code>s.
 * One, <code>directoriesList</code> consists of all the <code>String</code> pathnames in
 * <code>MASTER_FILE</code>. The other, <code>directorySpool</code> is a set of <code>File</code> 
 * objects derived from those pathnames in <code>directoriesList</code>.
 * The two arrays should always contain equivalent items for a given index. <p>
 * If the <code>MASTER_FILE</code> does not exist at runtime in the path specified by 
 * <code>System.getProperty("user.dir")</code>, then <code>GistIO</code> creates it. 
 * 
 * @author corbinzahrt
 * @version 1.0
 *
 */
public class GistIO {
	
	/**
	 * Instance variable that stores the number of items per column when things are displayed.
	 */
	private int pColumnWidth = 2;
	
	/**
	 * Holds the pathnames of all <code>Strings</code> in the <code>MASTER_FILE</code>. 
	 */
	private ArrayList<String> directoriesList = new ArrayList<String>();
	
	/**
	 * Holds all pathnames from <code>directoriesList</code> as <code>File</code> objects. 
	 */
	private ArrayList<File> directorySpool = new ArrayList<File>();
	
	/**
	 * Filename (including ".txt" extension) of the <code>MASTER_FILE</code>.
	 */
	public static final String MASTER_FILE = "previous.txt";
	
	/**
	 * The default constructor. Pulls all paths recorded in <code>MASTER_FILE</code> and adds them to the <code>ArrayList</code>s 
	 * <code>directoriesList</code> and <code>directorySpool</code>. <p> If <code>MASTER_FILE</code> does not exist at runtime it is created. 
	 * @throws FileNotFoundException
	 */
	public GistIO() throws FileNotFoundException{
		File currentDir = new File(System.getProperty("user.dir"));
		File masterDir = null;
		String [] contents = currentDir.list();
		boolean hasMaster = false;
		
		for(int i= 0; i < contents.length; i++){
			String currentItem = contents[i];
			if(currentItem.contentEquals(MASTER_FILE)){
				masterDir = new File(MASTER_FILE);
				hasMaster = true;
			}
		}
		if(hasMaster == false){
			PrintWriter makeFile = new PrintWriter(MASTER_FILE);
			masterDir = new File(MASTER_FILE);
			makeFile.close();
			System.out.println("It looks like there are no pre-existing directories.");
			
		}
		
		Scanner scanner = new Scanner(masterDir);
		
		while(scanner.hasNextLine()){
			String currentItem = scanner.nextLine();
			directoriesList.add(currentItem);
		} scanner.close();
		
		if(directoriesList.size() < 1){
			System.out.println("It looks like there are no pre-existing directories.");
		}
		
		for(int i = 0; i < directoriesList.size(); i++){
			File currentFile = new File(directoriesList.get(i));
			directorySpool.add(currentFile);
		}
	}
	
	/**
	 * Prompts the user to type the name of a directory they wish to create.<p>
	 * If creation is successful, "Directory created." is printed
	 * to the screen. 
	 * @throws FileNotFoundException
	 */
	public void createDirectory() throws FileNotFoundException{
		System.out.println("\nPlease type a name for the directory you wish to create.");
		Scanner keyboard = new Scanner(System.in);
		String userIn = keyboard.nextLine();
		
		
		userIn = System.getProperty("user.dir") + File.separatorChar + userIn;
	
		boolean newDirectory = new File(userIn).mkdir();
		if(newDirectory){
			System.out.println("Directory created.");
			spoolUp(userIn);
		}
		
		
	}
	
	/**
	 * Displays the names of every directory in the <code>MASTER_FILE</code>.<p>
	 * Things are displayed in two columns with numbers before each item corresponding to 
	 * each directory's <code>index</code>.
	 */
	public void displayMaster(){
		for(int i = 0; i < directoriesList.size(); i++){
			System.out.print(i + " " + directorySpool.get(i).getName() + "\t\t");
			int modSpace = i % pColumnWidth;
			
			if(modSpace != 0){
				System.out.println();
			}
		}
	}
	
	/**
	 * Displays the contents of a given directory. <p>This method is unfinished and need refining. 
	 */
	public void displayContents(){
		
		Scanner keyboard = new Scanner(System.in);
		if(directoriesList.size()-1 > 0){
			for(int i = 0; i < directoriesList.size(); i++){
				int modSpace = i % pColumnWidth;
				System.out.println(i + " " + directorySpool.get(i).getName());
				
				if(modSpace != 0){
					System.out.println();
			}
			}
		}
			
			String [] contents = directorySpool.get(keyboard.nextInt()).list();
			
			for(int k = 0; k < contents.length; k++){
				int modSpace2 = k % pColumnWidth;
				System.out.println(contents[k]);
				
				if(modSpace2 != 0){
					System.out.println();
				}
	}
		}
	
	/**
	 * Deletes directories. <p>I don't like this implementation. This method 
	 * needs more work. 
	 * @throws FileNotFoundException
	 */
	public void delete() throws FileNotFoundException{
		Scanner keyboard = new Scanner(System.in);
		
		
		boolean done = false;
		System.out.println("Type 'done' when finished.");
		while(!done){
			if(keyboard.hasNextInt() && directoriesList.size() > 0){
				int item = keyboard.nextInt();
				boolean worked = directorySpool.get(item).delete();
				if(worked){
				directoriesList.remove(item);
				directorySpool.remove(item);
				refresh();
				}
				displayMaster();
				}else if (keyboard.next().contains("done")){
					done = true;
					}else if (directoriesList.size() == 0){
						done = true;
						} else{
							System.out.println("Invalid.");
							}
			}
		}
	
	/**
	 * A helper method that takes a pathname as a String and adds it to the arrays directorySpool and directoriesList. If the directory is 
	 * successfully created, then a new version of <code>MASTER_FILE</code> is created with the new item. 
	 * @param input
	 * @throws FileNotFoundException 
	 */
	private void spoolUp (String input) throws FileNotFoundException{
		directoriesList.add(input);
		File addIn = new File(input);
		directorySpool.add(addIn);
		refresh();
	}
	
	/**
	 * Helper method that recreates <code>MASTER_FILE</code> with the contents of <code>directoriesList</code>.
	 * @throws FileNotFoundException
	 */
	private void refresh() throws FileNotFoundException{
		PrintWriter refresh = new PrintWriter(MASTER_FILE);
		
		for(int i = 0; i < directoriesList.size(); i++){
			refresh.println(directoriesList.get(i));
		} refresh.close();
		
	}
}
