package Entity;

import java.util.List;

public class HistoricalEvent extends Entity {
	protected String time;
	protected String dynasty_related;
	protected List<String> relatedTo;

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDynasty() {
		return this.dynasty_related;
	}

	public void setDynasty(String dynasty) {
		this.dynasty_related = dynasty;
	}

	public List<String> getRelatedTo() {
		return this.relatedTo;
	}

	public void setRelatedTo(List<String> relatedTo) {
		this.relatedTo = relatedTo;
	}

	public HistoricalEvent() {
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HistoricalEvent{")
				.append("\n name=").append(id)
				.append("'\n time='").append(time)
				.append("\n dynasty related='").append(dynasty_related)
				.append("'\n related to='").append(relatedTo)
				.append("\n}");
		return sb.toString();
	}
}
