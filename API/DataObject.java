package API;

public interface DataObject {
	
	
	
	/**
	 * Returns a boolean value for whether the <code>DataObject</code> has data at the given 
	 * <code>index</code>.
	 */
	public boolean hasItem(int index);
	
	/**
	 * Adds the given <code>input</code> at the beginning of the existing content
	 * of the item at the given <code>index</code>. 
	 * @param index
	 * @param input
	 */
	public void append(int index, String input);
	
	/**
	 * Replaces all content of the <code>DataObject</code> at <code>index</code> with the given
	 * <code>input</code>.
	 * @param index
	 * @param input
	 */
	public void replace(int index, String input);
	
	/**
	 * Adds the given <code>input</code> at the end of the existing content for the item at 
	 * <code>index</code>.
	 * @param index
	 * @param input
	 */
	public void add(int index, String input);

}
