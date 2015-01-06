package gistPack;

import API.DataObject;
import API.IDisplay;

/**
 * Encapsulates the code for an individual <code>GistObject</code>.<p>
 * <code>GistObject</code> is meant to be used in conjunction with a <code>Shell</code>.
 * A <code>Shell</code> stores all the information about the object, while <code>GistObject</code>
 * contains all the functions for manipulating that data. 
 * @author CorbinSupertramp
 *
 */
public class GistObject implements IDisplay, DataObject{
	
	/**
	 * Instance of <code>GistShell</code> that this <code>GistObject</code> will use to store its data.<p>
	 * If an instance is a brand new save, then a new instance of <code>shell</code> will be used. 
	 * Otherwise,a  <code>Shell</code> can be passed via the alternate constructor.
	 */
	private GistShell shell = new GistShell();
	
	
	
	/**
	 * The default constructor.
	 */
	public GistObject(){}
	
	/**
	 * Use this constructor to pass a pre-existing <code>Shell</code>. 
	 * @param shell
	 */
	public GistObject (GistShell shell){this.shell = shell;}

	@Override
	public boolean hasItem(int index) {
		if(shell.gist[index].isEmpty()){
			return false;
		}else{
			return true;
		}
	}
	

	
	/**
	 * Replaces all instances of <code>thisSubstring</code> with <code>withThisOne</code>.
	 * @param index
	 * @param thisSubstring
	 * @param withThisOne
	 */
	public void replaceThis(int index, String thisSubstring, String withThisOne){
		if(shell.allowed[index]){
			shell.gist[index] = shell.gist[index].replaceAll(thisSubstring, withThisOne);
			shell.log(index, "had a String " + thisSubstring.length() + " long " + "replaced with one " + withThisOne.length() + " long.");
		}
	}
	
	/**
	 * Appends <code>input</code> to the beginning of <code>gist[index]</code>.
	 */
	@Override
	public void append(int index, String input) {
		if(shell.allowed[index]){
			shell.gist[index] = input + " " + shell.gist[index];
			shell.log(index, "appended");
		}
	}

	/**
	 * Replaces the contents of <code>gist[index]</code> completely. 
	 */
	@Override
	public void replace(int index, String input) {
		if(shell.allowed[index]){
			shell.gist[index] = input;
			shell.log(index, "replaced");
		}
		
	}

	/**
	 * Adds the given <code>input</code> to the given <code>index</code>.<p>
	 * If <code>gist[index]</code> is not empty, adds a whitespace before <code>input</code>.
	 * Will do nothing if <code>allowed[index]= false</code>.
	 */
	@Override
	public void add(int index, String input) {
		if(shell.allowed[index]){
			if(shell.gist[index].isEmpty()){
				shell.gist[index] = shell.gist[index] + input;
			}else{
				shell.gist[index] = shell.gist[index] + " " + input;
			}
		
			shell.log(index, "added");
		}
	}
	
	public void addln(int index, String input) {
		if(shell.allowed[index]){
			if(shell.gist[index].isEmpty()){
				shell.gist[index] = shell.gist[index] + input;
			}else{
				shell.gist[index] = shell.gist[index] + "\n" + input;
			}
		
			shell.log(index, "added");
		}
	}
	
	/**
	 * 0 <code>repositoryID</code><p> 
	 * 1 <code>uniqueID</code><p>  
	 * 2 <code>modification log</code><p>  
	 * 3 <code>item order</code><p>  
	 * 4 <code>tag</code><p> 
	 * 5 <code>description</code><p>  
	 * 6 <code>content</code><p> 
	 */
	public String displayIndex(int index){return shell.gist[index];}
	/**
	 * Returns <code>gist[]</code>.
	 */
	public String[] display(){return shell.gist;}
	/**
	 * Returns <code>labels[]</code>.
	 * @return
	 */
	public String[] getLabels() {return shell.labels;}
	/**
	 * 0 <code>repositoryID</code><p> 
	 * 1 <code>uniqueID</code><p>  
	 * 2 <code>modification log</code><p>  
	 * 3 <code>item order</code><p>  
	 * 4 <code>tag</code><p> 
	 * 5 <code>description</code><p>  
	 * 6 <code>content</code><p> 
	 */
	public String getLabel(int index) {return shell.labels[index];}
	
	/**
	 * Protected method which ignores whether changes are allowed to a given index. 
	 * @param index
	 * @param input
	 */
	protected void superSet(int index, String input){
		shell.gist[index] = input;
		shell.log(index, "was superSet");
	}
	
	/**
	 * Removes anything matching <code>input</code> from <code>gist[index]</code>.
	 * Ignores any protections. 
	 * @param index
	 * @param input
	 */
	protected void superRemove(int index, String input){
		shell.gist[index] = shell.gist[index].replaceAll(input, "**REMOVED**");
		shell.log(index, "had an item superRemoved");
	}
	
	/**
	 * Returns the instance of <code>Shell</code> this <code>GistObject</code> houses. 
	 * @return
	 */
	protected GistShell getShell(){return shell;}
	

}
