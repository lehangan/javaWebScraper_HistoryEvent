package Entity;

import java.util.List;

public class HistoricalEventWar extends HistoricalEvent{
    private List<String> characters;
    private String result;
    private String location;

    public List<String> getCharacters() {
		return this.characters;
	}

	public void setCharacters(List<String> charaters) {
		this.characters = charaters;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public HistoricalEventWar() {
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HistoricalEvent{")
				.append("\n name=").append(id)
				.append("'\n time='").append(time)
				.append("'\n dynasty related='").append(dynasty_related)
                .append("'\n location='").append(location)
				.append("'\n related to='").append(relatedTo)
                .append("'\n characters related='").append(characters)
                .append("'\n result='").append(result)
				.append("\n}");
		return sb.toString();
	}
}
