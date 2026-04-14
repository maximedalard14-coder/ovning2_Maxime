

import java.util.*;
import java.util.spi.ResourceBundleControlProvider;

public class Searcher implements SearchOperations {
    private final HashSet<String> artists = new HashSet<>();
    private final HashSet<String> genres = new HashSet<>();
    private final HashSet<Recording> recordings = new HashSet<>();
    private final HashMap<String, Recording> titelRecordingHashMap = new HashMap<>();
    private final TreeMap<Integer, Set<Recording>> recordingsYearTreeMap = new TreeMap<>();
    private final HashMap<String, SortedSet<Recording>> artistRecordingSortedSetHashMap = new HashMap<>();
    private final HashMap<String, Set<Recording>> genreRecordingHashMap = new HashMap<>();

    public Searcher(Collection<Recording> data) {
        for (Recording r : data) {
            artists.add(r.getArtist());
            recordings.add(r);
            genres.addAll(r.getGenre());
            titelRecordingHashMap.put(r.getTitle(), r);
            recordingsYearTreeMap.computeIfAbsent(r.getYear(), year -> new HashSet<>()).add(r); //en lambda
            artistRecordingSortedSetHashMap.computeIfAbsent(r.getArtist(), artist -> new TreeSet<>((a, b) -> a.getYear() - b.getYear())).add(r);
            for (String genre : r.getGenre()) {
                genreRecordingHashMap.computeIfAbsent(genre, genreNy -> new HashSet<>()).add(r);
            }
        }

    }

    @Override
    public long numberOfArtists() {
        return artists.size();
    }

    @Override
    public long numberOfGenres() {
        return genres.size();
    }

    @Override
    public long numberOfTitles() {
        return titelRecordingHashMap.size();
    }

    @Override
    public boolean doesArtistExist(String name) {
        if (name == null) {
            return false;
        }
        return artists.contains(name);
    }

    @Override
    public Collection<String> getGenres() {
        return Collections.unmodifiableCollection(genres);
    }

    @Override
    public Recording getRecordingByName(String title) {
        return titelRecordingHashMap.get(title);
    }

    @Override
    public Collection<Recording> getRecordingsAfter(int year) {
        HashSet<Recording> result = new HashSet<>();
        for (Set<Recording> set : recordingsYearTreeMap.tailMap(year).values()) {
            result.addAll(set);
        }
        return Collections.unmodifiableCollection(result);
    }

    @Override
    public SortedSet<Recording> getRecordingsByArtistOrderedByYearAsc(String artist) {

        if (artistRecordingSortedSetHashMap.containsKey(artist)) {
            return Collections.unmodifiableSortedSet(artistRecordingSortedSetHashMap.get(artist));
        }
        return Collections.emptySortedSet();
    }

    @Override
    public Collection<Recording> getRecordingsByGenre(String genre) {
        if (genreRecordingHashMap.containsKey(genre)) {
            return Collections.unmodifiableCollection(genreRecordingHashMap.get(genre));
        }
        return Collections.emptySet();
    }

    @Override
    public Collection<Recording> getRecordingsByGenreAndYear(String genre, int yearFrom, int yearTo) {
        HashSet<Recording> result = new HashSet<>();
        if(genreRecordingHashMap.containsKey(genre)) {
            for (Recording r : genreRecordingHashMap.get(genre)) {
                if (r.getYear() >= yearFrom && r.getYear() <= yearTo) {
                    result.add(r);

                }
            }
            return Collections.unmodifiableCollection(result);
        }
        return Collections.emptySet();
    }

    @Override
    public Collection<Recording> offerHasNewRecordings(Collection<Recording> offered) {
        HashSet<Recording> result= new HashSet<>();
        for(Recording r : offered){
            if(!(recordings.contains(r))){
                result.add(r);
            }
        }
        return Collections.unmodifiableCollection(result);

    }

}
