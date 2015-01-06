package API;

import gistPack.GistObject;
import gistPack.State;

import java.io.IOException;
import java.util.ArrayList;

public abstract class GistEnvironment {
	
	/**
	 * Instance of <code>State</code>
	 */
	private State State = new State();
	
	/**
	 * Stores all <code>GistObjects</code> for easy manipulation. 
	 */
	private ArrayList<GistObject> cage = new ArrayList<GistObject>();
	
	/**
	 * Keeps track of your index within the <code>cage</code>.
	 */
	private int index;
	
	
	/**
	 * Create <code>number</code> <code>GistObject</code> saves. 
	 * @param number
	 * @throws IOException
	 */
	public void createSave(int number) throws IOException {
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
			State.save(cage);
			}
	
	/**
	 * Set's the index of the current <code>GistObject</code>. 
	 * @param index
	 */
	public void set(int index){
		this.index = index;
	}
	
	/**
	 * Returns the current <code>GistObject</code>.
	 * @return
	 */
	public GistObject get(){
		return cage.get(index);
	}
	
	

}
