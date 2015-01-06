package gistPack;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import API.IDisplay;

/**
 * This class encapsulates the file management of <code>Gist</code>.<p>
 * As with every member of <code>Gist</code> the goal is to make each <code>class</code> as simple as
 * humanly possible. Repeat the mantra: reduce, reduce, reduce.<p>
 * <code>GistIO</code> is built around the idea that the user is always, at any given time,
 * located at a <code>point</code> in the directory structure of the overall program. <p>
 * This <code>point</code> is expressed by a pathname which always specifies a directory.
 * Another <code>fine point</code> might be added that specifies the repository within <code>point</code>
 *  the user is currently in.<p>
 * Everything for this class revolves around a plain text <code>MASTER_FILE</code> that is located 
 * at <code>System.getProperty("user.dir")</code>. 
 * <code>MASTER_FILE</code> is essentially a list of directories represented by absolute paths. 
 * <p>When <code>GistIO</code> is executed the contents of <code>MASTER_FILE</code> are dumped 
 * into two <code>ArrayList</code>s.
 * One, <code>list</code> consists of all the <code>String</code> pathnames in
 * <code>MASTER_FILE</code>. The other, <code>spool</code> is a set of <code>File</code> 
 * objects derived from those pathnames in <code>list</code>.
 * The two arrays should always contain equivalent items for a given index, and several private
 * methods within <code>GistIO</code> work to ensure that. <p>
 * If the <code>MASTER_FILE</code> does not exist at runtime in the path specified by 
 * <code>System.getProperty("user.dir")</code>, then <code>GistIO</code> creates it. <p>
 * 
 * Apropos to the <code>Gist</code> mantra, this class is meant to be responsible only
 * for the file management of the program. Therefore it should not perform any of the display 
 * functions. This is the domain of another class. <p>
 * 
 * This <code>class</code> and all other members of <code>Gist</code> can be found on github at the 
 * url <url>https://github.com/corbinzahrt/Gist.git</url>.
 * 
 * @author corbinzahrt
 * @version 1.0
 *
 */
public class GistIO implements IDisplay{
	
	/**
	 * The user's location as a directory in the program. <p>
	 * Is <code>DEFAULT</code> by default. 
	 */
	private File point;
	
	/**
	 * The user's location within <code>point</code>. Always a repository; <code>null</code> otherwise. 
	 */
	private File finePoint;
	
	/**
	 * The name of the default directory. 
	 */
	private static String DEFAULT = "home";
	
	/**
	 * The name of the default repository.
	 */
	private static String HOME = "main.txt";
	
	
	/**
	 * Holds the pathnames of all <code>Strings</code> in the <code>MASTER_FILE</code>. 
	 */
	private ArrayList<String> list = new ArrayList<String>();
	
	/**
	 * Holds all pathnames from <code>directoriesList</code> as <code>File</code> objects. 
	 */
	private ArrayList<File> spool = new ArrayList<File>();
	
	
	/**
	 * Filename (including ".txt" extension) of the <code>MASTER_FILE</code>.<P>
	 * Holds the path names of every directory containing repositories.
	 */
	public static final String MASTER_FILE = "previous.txt";
	
	/**
	 * The default constructor. Pulls all paths recorded in <code>MASTER_FILE</code> and adds them to the <code>ArrayList</code>s 
	 * <code>list</code> and <code>spool</code>. <p> If <code>MASTER_FILE</code> does not exist at runtime it is created. 
	 * @throws FileNotFoundException
	 */
	public GistIO() throws FileNotFoundException {
		
		setPoint("");
		setFinePoint("");
		
		
		//Initializing a null scanner. Apparently I can't access things in my try/catch 
		//statements. 
		Scanner scanner = null;
		
		try{
			File masterDir = new File(MASTER_FILE);
			scanner = new Scanner(masterDir);
			}
		catch (FileNotFoundException e){
				PrintWriter create = new PrintWriter(MASTER_FILE);
				create.close();
				File masterDir = new File(MASTER_FILE);
				scanner =  new Scanner(masterDir);
			} 
		

		/*
		 * Adds each path listed in MASTER_FILE to the String array
		 */
		while(scanner.hasNextLine()){
			String currentItem = scanner.nextLine();
			list.add(currentItem);
		} scanner.close();
		
		/*
		 * Ensures the DEFAULT directory is in MASTER_FILE.
		 * Updates spool to match list. 
		 * Checks the MASTER_FILE for duplicates and non-existent files. 
		 */
		if(!list.contains(point.getAbsolutePath())){
			add(point.getAbsolutePath());
		}	
		updateSpool();
		checkMaster();
		
	}
	
	/**
	 * Saves the given array of <code>GistObject</code>s to the <code>finePoint</code>.
	 * @param arr
	 * @throws IOException
	 */
	public void saveRepository(ArrayList<GistObject> arr) throws IOException{
		GistObjectIO save = new GistObjectIO(arr);
		save.save(finePoint);
	}
	
	/**
	 * Returns an ArrayList of <code>GistObjects</code> from <code>finePoint</code>.
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public ArrayList<GistObject> loadRepository() throws ClassNotFoundException, IOException{
		
		GistObjectIO load = new GistObjectIO(finePoint);
		return setRepositoryID(load.getRepository());
	}
	
	/**
	 * Returns the <code>point</code> itself. Experimental.<p>
	 * <code>point</code> is the location of the user in the program. 
	 * @return
	 */
	public File getPoint(){return point;}
	
	/**
	 * Returns the user's <code>finePoint</code>.
	 * @return
	 */
	public File getFinePoint(){return finePoint;}
	
	/**
	 * Makes a new repository named <code>name</code> in <code>point</code>.
	 * @param name
	 * @throws FileNotFoundException
	 */
	public void makeRepository(String name) throws FileNotFoundException{
		String path = point.getAbsolutePath() + File.separatorChar + name;
		PrintWriter newOne = new PrintWriter(path);
		newOne.close();
	}
	
	/**
	 * Controls the fine location of the user within a directory.<p>
	 * Must be passed an absolute path. 
	 * @param path
	 * @throws FileNotFoundException 
	 */
	public void setFinePoint(String name) throws FileNotFoundException{
		if(name.equals("")){
				finePoint = new File(point.getAbsolutePath() + File.separatorChar+ HOME);
				if(!finePoint.exists()){
					System.out.println("not a real boy");
					makeRepository(HOME);
					finePoint = new File(point.getAbsolutePath() + File.separatorChar+ HOME);
				}
		}else{
			finePoint = new File(point.getAbsolutePath() + File.separatorChar+ name);
		}
		if(finePoint.isHidden()){
			finePoint = null;
		}
	}
	
	/**
	 * Controls the location of the user in the program. <p>
	 * If passed an empty string, <code>DEFAULT</code> is set to the default directory. 
	 * @param path
	 * @throws FileNotFoundException 
	 */
	public void setPoint(String path) throws FileNotFoundException{
		
		if(path.equals("")){
			if(!(new File(DEFAULT).isDirectory())){
				@SuppressWarnings("unused")
				boolean made = new File(DEFAULT).mkdir();
				point = new File(DEFAULT);
			} else{
				point = new File(DEFAULT);
			}
		}else{
			point = new File(path);
			finePoint = null;
		}
	}
	
	/**
	 * Prompts the user to type the name of a directory they wish to create.<p>
	 * Returns a boolean value of <code>true</code> if the directory was created. 
	 * @throws FileNotFoundException
	 */
	public boolean makeDirectory(String name) throws FileNotFoundException{
		String userIn = name;
		
		
		userIn = System.getProperty("user.dir") + File.separatorChar + userIn;
	
		boolean newDirectory = new File(userIn).mkdir();
		if(newDirectory){
			add(userIn);
		}
		return newDirectory;
	}

	/**
	 * Returns the paths of all directories listed <code>MASTER_FILE</code>.
	 * @return
	 */
	public ArrayList<String> getMaster(){
		return list;
		}
	
	/**
	 * Returns the contents of the <code>point</code> as an array of <code>File</code> objects.
	 * @return
	 */
	public String[] display(){return point.list();}
	
	/**
	 * Returns the item at the given index. 
	 */
	public String displayIndex(int index){
		return point.list()[index];
	}
	
	
	/**
	 * Delete a directory at a given index of <code>spool</code>. <p>Returns <code>true</code> if delete
	 * was successful. 
	 */
	public boolean delete(int index) throws FileNotFoundException{
		File currentFile = spool.get(index);
		File[] files = currentFile.listFiles();
		if(files.length > 0){
			for(File e: files){
				e.delete();
			}
		}
		boolean deleted = currentFile.delete();
		if(deleted){
			list.remove(index);
		}
		refresh();
		return deleted;
	}
	
	/**
	 * Adds file to <code>list</code> and updates all relevant instance variables & <code>MASTER_FILE</code>.
	 * @param input
	 * @throws FileNotFoundException
	 */
	private void add (String input) throws FileNotFoundException{
		list.add(input);
		refresh();
	}
	
	/**
	 * Helper method that recreates <code>MASTER_FILE</code> with the contents of <code>list</code>.<p>
	 * Updates <code>spool</code> to match <code>list</code>.
	 * @throws FileNotFoundException
	 */
	private void refresh() throws FileNotFoundException{
		PrintWriter refresh = new PrintWriter(MASTER_FILE);
		
		for(int i = 0; i < list.size(); i++){
			refresh.println(list.get(i));
		} refresh.close();
		
		updateSpool();
		
		
	}
	
	/**
	 * Invoking this method updates the contents of <code>spool</code> to match <code>list</code>.<p>
	 * Use of this helper is discouraged, <code>refresh()</code> automatically invokes it. 
	 */
	private void updateSpool(){
		spool = new ArrayList<File>();
		
		for(int i = 0; i < list.size(); i++){
			File currentFile = new File(list.get(i));
			spool.add(currentFile);
		}
	}
	
	/**
	 * Checks whether all the directories listed in <code>MASTER_FILE</code> exist.<p>
	 * If any do not they are removed. Also checks for any duplicates. 
	 * @throws FileNotFoundException 
	 */
	private void checkMaster() throws FileNotFoundException{
		/*
		 * Removes any directory that does not exist. 
		 */
		for(File e: spool){
			if(!e.exists()){
				list.remove(spool.indexOf(e));
				refresh();
			}
		}
		
		/*
		 * Removes all duplicates in MASTER_FILE. 
		 */
		String check = "";
		for(int i = 0; i<spool.size(); i++){
			if(check.contains(spool.get(i).getAbsolutePath())){
				list.set(i, "");
			} else{
				check = list.get(i) + check + " ";
			}
		}
		
		refresh();
	}
	
	/**
	 * Tags all <code>GistObject</code>s in the given array with the proper Repository ID.
	 * @param arr
	 * @return
	 */
	private ArrayList<GistObject> setRepositoryID(ArrayList<GistObject> arr){
		for(GistObject g: arr){
			if(!g.displayIndex(0).equals(finePoint.getAbsolutePath())){
				g.superSet(0, finePoint.getAbsolutePath());
			}
		}
		return arr;
	}

	
}
