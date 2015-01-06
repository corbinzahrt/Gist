package gistPack;

import java.util.Date;
import java.util.UUID;

/**
 * This <code>Class</code> encapsulates all the information a <code>GistObject</code> 
 * manipulates, and is the form that is saved to repositories. <p>
 * It is quite alright to save some energy and refer to each instance of this class as a
 * <code>Shell</code>, rather than a <code>GistShell</code>.<p>
 * Originally created so that functionality could be added to the <code>GistObject</code> class without 
 * making old saves throw exceptions, <code>Shell</code> uses three arrays to store all the information 
 * about each instance: <code>labels[]</code>, <code>gist[]</code>, and <code>allowed[]</code>.<p>
 * <code>labels[]</code> is a <code>String</code> array whose elements are predefined in the source code
 * of the <code>Shell Class</code>. Think of it as a generic template for all <code>gist[]</code>
 * arrays, that contains simple labels at each index instead of unique information.
 * These labels specify what kind of information lies at each index of <code>gist[]</code>.
 * <p>
 * Below is the current definition of each index of <code>labels[]</code>.<p>
 * 0 <code>repositoryID</code>,
 * 1 <code>uniqueID</code>,
 * 2 <code>modification log</code>, 
 * 3 <code>item order</code>,
 * 4 <code>tag</code>,
 * 5 <code>description</code>,
 * 6 <code>content</code><p>
 * <p>The contents  of <code>gist[]</code> are manipulated through the <code>GistObject Class</code>.
 * <p><code>allowed[]</code> is a <code>boolean</code> array that is used to control access to each index of
 * <code>gist[]</code>. In the <code>GistObject</code> class, each of the public mutator methods first checks to
 * see whether manipulation of that index is allowed. If a call is made that attempts to modify a protected
 * index, nothing happens. This can be overidden by any of the <code>protected</code> 
 * <code>super</code> methods. Changes made with these methods are recorded in the modification log. 
 * 
 * @author CorbinSupertramp
 * @version 1.0
 *
 */
@SuppressWarnings("serial")
public class GistShell implements java.io.Serializable{
	


	/**
	 * Records whether the <code>GistObject</code> existed at runtime. 
	 */
	private boolean exist = false;
	
	/**
	 * The main event, so to speak. Holds everything the user interacts with. 
	 */
	protected String[] gist;
	
	
	/**
	 * 0 <code>repositoryID</code><p> 
	 * 1 <code>uniqueID</code><p>  
	 * 2 <code>modification log</code><p>  
	 * 3 <code>item order</code><p>  
	 * 4 <code>tag</code><p> 
	 * 5 <code>description</code><p>  
	 * 6 <code>content</code><p> 
	 */
	protected String[] labels = {
			"repositoryID", "uniqueID", "modification log", "item order", "tags", "description",
			"content"
	};
	
	/**
	 * Keeps track of whether the index of <code>gist</code> is allowed to be altered. 
	 */
	protected boolean[] allowed;
	
	/**
	 * Creates an instance of <code>GistShell</code>.<p>
	 * Instantiates the <code>String</code> array <code>gist</code> and the <code>boolean</code> array 
	 * <code>allowed</code> to match the size of the predefined array <code>labels</code>. <p>
	 * <code>gist</code> is the element that contains almost all of the information this program is used 
	 * to store, categorize, and manipulate. <code>allowed</code> contains <code>boolean</code> values 
	 * at each index that indicate whether the same index of <code>gist</code> is allowed to be manipulated.
	 * 
	 */
	public GistShell(){
		gist = new String[labels.length];
		allowed = new boolean[labels.length];
		for(int i = 0; i < labels.length; i++){
			gist[i] = "";
			allowed[i] = true;
		}
		//prevent direct access to the modification log. 
		allowed[2] = false;
		//prevent direct access to repository editing. 
		allowed[0] = false; 
		unique();
	}
	
	/**
	 * Used to record changes to <code>modification log</code>. <p>
	 * Not to be used outside of <code>GistShell</code> or <code>GistObject</code>.
	 * @param index
	 * @param input
	 */
	protected void log(int index, String input){
		Date date = new Date();
		String update = gist[2];
		String timeStamp ="["+ date.toString()+"]: ";
		if(update.isEmpty()){
			update = timeStamp + labels[index] + " " + input;
		} else{
			update = update + "\n" + timeStamp + labels[index] + " " + input;
		}
		
		gist[2] = update;
	}
	
	/**
	 * If this <code>GistShell</code> does not already have a unique ID, this private method 
	 * assigns it one. 
	 */
	private void unique(){
		if(!exist){
			UUID id = UUID.randomUUID();
			gist[1] = id.toString();
			log(1, "was assigned");
			exist = true;
			allowed[1] = false;
		} 
		
	}
	
	
}
