package Database;

import java.util.List;

public interface IDatabase <E> {

	/* Store an object into database */
	public void store(List<E> listObject);
	/* Load a list of objects with given index range[startIndex, endIndex)
		- startIndex is inclusive
		- endIndex is exclusive
	 */
	public List<E> load(int startIndex, int endIndex);

	/* Load and return all objects in the database */
	public List<E> load();

	/* return the number of objects in the database */
	public int size();

	/* close the database: cleaning environment if neccessary */
	public void close();
}	// close IDatabase
