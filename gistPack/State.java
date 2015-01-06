package gistPack;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class encapsulates the State of the program.
 * @author corbin
 *
 */
public class State {
	
	private ArrayList<GistObject> cage = new ArrayList<>();
	
	/**
	 * Keeps track of your index within the <code>cage</code>.
	 */
	private int index;
	
	private static GistIO io;
	
	public State(){
		try{
			io = new GistIO();
		}
		catch(FileNotFoundException e){
			System.out.println("Error. Serious. Very. I actually can't explain how this would ever happen.");
		}
		
	}
	
	public void save(ArrayList<GistObject> arr) throws IOException{
		io.saveRepository(arr);
	}
	
	public ArrayList<GistObject> load() throws ClassNotFoundException, IOException{
		return io.loadRepository();
	}
	
	public GistIO getIO(){
		return io;
	}
	
	public ArrayList<GistObject> getCage(){
		return cage;
	}
}
