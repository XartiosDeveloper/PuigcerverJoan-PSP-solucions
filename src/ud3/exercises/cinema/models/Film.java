package ud3.exercises.cinema.models;

import java.io.Serial;
import java.io.Serializable;

public class Film implements Serializable {
    private String name;
    private int releaseYear;
    private int duration;

    public Film(String name, int releaseYear, int duration) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Film{" +
                "name='" + name + '\'' +
                ", releaseYear=" + releaseYear +
                ", duration=" + duration +
                '}';
    }
}
