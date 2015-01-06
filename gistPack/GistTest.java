package gistPack;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class GistTest {
	private static String flush ="\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
	private static int number = 1;
	private static ArrayList<GistObject> cage = new ArrayList<>();
	private static ArrayList<GistObject> cageLoad = new ArrayList<>();
	/**
	 * Stores the index of the current <code>GistObject</code>.
	 */
	private static int currentGistObject;
	private static boolean hasPicked = false;
	private static boolean done = false;
	
	
	private static 	GistIO gist;
	
public static void main(String[] args) throws IOException, ClassNotFoundException{
	gist = new GistIO();
	TagSearch Tag = new TagSearch();
	cage = gist.loadRepository();
	set(0);
	Tag.tagSearch(snap().displayIndex(6));
	

	
//	while(!hasPicked){
//		run();
//		runMenu();
//		}
//
//
//	
//
//	
//	displayCage(cageLoad);
//	gist.saveRepository(cageLoad);
	
	
	
	
	//user picks existing dir or makes one.
	//user picks repository or makes one
	
}

private static void run() throws ClassNotFoundException, IOException{
	boolean notDone = true;
	while(notDone){
		pickDir();
		pickRepository();
		try{
			cageLoad = gist.loadRepository();
			notDone = false;
			} catch (EOFException e){
				System.out.println("Not a valid repository. Try again.");
				System.out.println("If file contained a '.txt' extension, a blank save was written to the file you just\nattempted to make. Try 1 more time.");
				try{
					createSave();
				}catch(NullPointerException ex){
				System.out.println("Illegal filetype. Try again.");
				}
	}
	}
}

private static void runMenu(){
	Scanner keyboard = new Scanner(System.in);
	while(!done){
		guiSet();
		addContent();
		System.out.println("Type 'exit.' when finished or 'change.' to pick a new repository, otherise, hit any key.");
		if(keyboard.hasNext("exit.")){
			done = true;
			hasPicked = true;
		}else if(keyboard.hasNext("change.")){
			hasPicked = false;
			done = false;
			return;
		}
		else{
			done = false;
			String toss = keyboard.nextLine();
		}
	}
	
}
private static void pickRepository() throws FileNotFoundException{
	boolean waiting = true;
	Scanner keyboard = new Scanner(System.in);
	while(waiting){
		if(gist.display().length == 0){
			System.out.println("There are no repositories here. Please enter a name to create one.");
			gist.makeRepository(keyboard.nextLine());
		}
		System.out.println("Select a repository");
		display(gist.display());
		boolean sitting = true;
		while(sitting){
			
			if(keyboard.hasNextInt()){
				try{
					gist.setFinePoint(gist.displayIndex(keyboard.nextInt()));
					sitting = false;
					waiting = false;
				}catch (Exception e){
					System.out.println("Makes no sense. Try again.");
				}
				
			}
		}
		
		
	}
	
}

private static void pickDir() throws FileNotFoundException{
	boolean waiting = true;
	System.out.println("Enter the index of the directory you'd like, or type 'create' to make a new one or 'delete.' to destroy one.");
	displayAL(gist.getMaster());
	Scanner keyboard = new Scanner(System.in);
	while(waiting){
		if(keyboard.hasNext("delete.")){
			String toss = keyboard.nextLine();
			System.out.println("Enter index of directory to be permanantly destroyed.");
			gist.delete(keyboard.nextInt());
			pickDir();
			return;
		}
		if(keyboard.hasNext("create.")){
			System.out.println("Enter the name of the directory you'd like to create.");
			String toss = keyboard.nextLine();
			String name = keyboard.nextLine();
			gist.makeDirectory(name);
			displayAL(gist.getMaster());
			}
		if(keyboard.hasNextInt()){
			gist.setPoint(gist.getMaster().get(keyboard.nextInt()));
			System.out.println("Point has been set to " + gist.getPoint().getName());
			waiting = false;
		}
		}
	}



/**
 * Saves writing a whole bunch. Returns the <code>GistObject</code> at index specified by <code>currentGistObject</code>.
 * of <code>cageLoad</code>.
 * @param index
 * @return
 */
private static GistObject load(){
	return cageLoad.get(currentGistObject);
}


/**
 *Same as <code>load</code>, except for returning a <code>GistObject</code> of <code>cage</code>.
 * @param index
 * @return
 */
private static GistObject snap(){
	return cage.get(currentGistObject);
}

/**
 * Sets the index of the <code>currentGistObject</code>.
 * @param index
 */
private static void set(int index){
	currentGistObject = index;
}

/**
 * Emulating a simple user interface.
 */
private static void guiSet(){
	boolean edit = false;
	Scanner keyboard = new Scanner(System.in);
	System.out.println("There are " + cageLoad.size() + " shells you can choose");
	System.out.println("Pick one, or type 'edit' to edit the description.");
	int i = 0;
	for(GistObject e: cageLoad){
		System.out.print(i + " ");
		System.out.println(e.displayIndex(5));
		i++;
	}
	boolean invalid = false;
	if(keyboard.hasNext("edit.")){
		edit = true;
		invalid = true;
		String toss = keyboard.nextLine();
	}

	while(invalid){
		
		try{
			System.out.println("Enter the number you'd like to edit.");
			
			int input = keyboard.nextInt();
			set(input);
			invalid = false;
		} catch (InputMismatchException e){
		System.out.println("Makes no sense. Try again.");
		String toss = keyboard.nextLine();
		}
	}
	

	boolean empty = load().displayIndex(5).isEmpty() || edit;
	if(empty){
		System.out.println("Please enter a description of this shell before you add content.");
	}
	while(empty){
		String userIn = keyboard.nextLine();
		load().replace(5, userIn);
		empty = load().displayIndex(5).isEmpty();
		edit = false;
	}
	
}

private static void neatDisplay(ArrayList<GistObject> arr){
	for(GistObject g: arr){
		String[] contents1 = g.display();
		String[] contents = new String[2];
		contents[0] = contents1[5];
		contents[1] = contents1[6];
		
		for(String s: contents){
			System.out.println(s);
		}
		System.out.println();
	}
	
}

private static void displayCage(ArrayList<GistObject> arr){
	for(GistObject g: arr){
		String[] contents = g.display();
		for(String s: contents){
			System.out.println(s);
		}
		System.out.println();
	}
}

private static void display(String[] arr){
	for(int i = 0; i<arr.length; i++){
		System.out.println(i + " " + arr[i]);
	}
}

/**
 * Prints ArrayLists
 * @param arr
 */
private static void displayAL(ArrayList<String> arr){
	for(String e: arr){
		System.out.print("[" + arr.indexOf(e) +"]" + " ");
		System.out.println(e);
	}
}

private static void addContent(){
	
	boolean working = true;
	System.out.println("Type 'done.' when finished.");
	while(working){
		Scanner keyboard2 = new Scanner(System.in);
		System.out.println(flush);
		System.out.println("[Type 'done.' when finished.]\n");
		System.out.println(load().displayIndex(6));
		String input = keyboard2.nextLine();

		if(input.equals("done.")){
			working = false;
		}else{
			load().addln(6,input);
		}
		
		
	}
}

private static void removeContent(){
	
}

private static void createSave() throws IOException{
	/**
	 * Creating an array of GistObjects to test the serialization class. 
	 */
	for(int i= 0; i<number; i++){
		GistObject gister = new GistObject();
		
		for(int k=0; k<gister.display().length; k++){
			String filler = gister.getLabel(k);
			//String filler = "";
			gister.add(k, filler);
			if(k == 3){
				gister.replace(k, "" + i);
			}
			}
		cage.add(gister);
	}
	
	
	gist.saveRepository(cage);
}
}
