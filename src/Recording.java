import java.util.Collection;
import java.util.Set;

public class Recording {
	private final int year;
	private final String artist;
	private final String title;
	private final String type;
	private final Set<String> genre;

	public Recording(String title, String artist, int year, String type, Set<String> genre) {
		this.title = title;
		this.year = year;
		this.artist = artist;
		this.type = type;
		this.genre = genre;
	}

	public String getArtist() {
		return artist;
	}

	public Collection<String> getGenre() {
		return genre;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	public int getYear() {
		return year;
	}
	    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recording)) {
            return false;
        }
        Recording other = (Recording) o;
        if (other.title.equals(this.title) && other.artist.equals(this.artist) && other.year == this.year)
          return true;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, artist, year);
    }

	@Override
	public String toString() {
		return String.format("{ %s | %s | %s | %d | %s }", artist, title, genre, year, type);
	}
}
