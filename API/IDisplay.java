package API;

public interface IDisplay {
	
	/**
	 * Classes implementing <code>IDisplay</code> must return a String array. 
	 * @return
	 */
	public String[] display();
	
	/**
	 * Returns the <code>String</code> at the given index. 
	 * @param index
	 * @return
	 */
	public String displayIndex(int index);

}
