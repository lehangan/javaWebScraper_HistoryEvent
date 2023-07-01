package Entity;
import java.util.Map;

import org.json.simple.JSONArray;

import Exception.NoIdException;
public class Entity {

	private Map<String, String> properties;		// map string with string
	private String id;							// id of the character

	// Constructor
	public Entity(Map<String,String> map) throws NoIdException {
		this.properties = map;
		if (!map.containsKey("id")) 
			throw new NoIdException();
		this.id = map.get("id");
	}	// close constructor


	// Get a value of given attribute(key) in map
	public String getProperty(String key) {
		return this.properties.get(key);
	}	// close getProperty


	@Override
	public String toString() {
		String info = "";
		for (Map.Entry<String, String> entry: this.properties.entrySet()) {
			info += entry.getKey() + " : ";
			info += entry.getValue() + "\n";
		}	// close for
		return info;
	}	// close info
}	// close Entity