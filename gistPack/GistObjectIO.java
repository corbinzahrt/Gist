package gistPack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Encapsulates the serialization of <code>GistShell</code>s and <code>GistObject</code>s.
 * @author CorbinSupertramp
 *
 */
public class GistObjectIO {
	
	/**
	 * Holds all <code>GistObjects</code> loaded from the <code>GistShell</code>s 
	 * in the given repository.
	 */
	private ArrayList<GistObject> gists = new ArrayList<>();
	
	/**
	 * Holds all the <code>GistShells</code> contained in the given repository. 
	 */
	private ArrayList<GistShell> shells = new ArrayList<>();
	
	/**
	 * Loads a repository from the given <code>File</code> object representation, <code>repository</code>.<P>
	 * Places all <code>Shell</code>s in the repository in <code>GistObject</code>s.
	 * @param repository
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public GistObjectIO(File repository) throws IOException, ClassNotFoundException{
		ArrayList<GistShell> gIn = null;
		FileInputStream input = new FileInputStream(repository.getAbsolutePath());
		ObjectInputStream in = new ObjectInputStream(input);
		gIn = (ArrayList<GistShell>) in.readObject();
		shells = gIn;
		in.close();
		input.close();
		for(GistShell s: shells){
			GistObject g = new GistObject(s);
			gists.add(g);
		}
	}
	
	/**
	 * When given an array of <code>GistObjects</code> this constructor extracts their
	 * <code>Shell</code>s and adds them to <code>shells</code>.
	 * @param arr
	 */
	public GistObjectIO(ArrayList<GistObject> arr){
		for(GistObject g: arr){
			shells.add(g.getShell());
		}
	}
	
	/**
	 * Returns all <code>GistObjects</code> contained by this instance of <code>GistObjectIO</code>.
	 * @return
	 */
	public ArrayList<GistObject> getRepository(){
		return gists;
	}
	
	/**
	 * Saves the state of the ArrayList <code>shells</code> to the specified <code>directory</code>.
	 * @param directory
	 * @throws IOException
	 */
	public void save(File directory) throws IOException{
		FileOutputStream fileOut = new FileOutputStream(directory.getAbsolutePath());
		ObjectOutputStream objects = new ObjectOutputStream(fileOut);
		objects.writeObject(shells);
		objects.close();
		fileOut.close();
	}
	

}
