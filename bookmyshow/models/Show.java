package bookmyshow.models;
import java.util.UUID;

public class Show {
    private String id;
    private String movieName;
    private long startTime;
    private long endTime;
    private Cinema cinema;
    private Screen screen;

    public Show(String movieName, long startTime, long endTime, Cinema cinema, Screen screen) {
        this.id = UUID.randomUUID().toString();
        this.movieName = movieName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cinema = cinema;
        this.screen = screen;
    }

    public String getId(){
        return id;
    }
    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    @Override
    public String toString() {
        return "Show [movieName=" + movieName + ", startTime=" + startTime + ", endTime=" + endTime + ", cinema="
                + cinema + ", screen=" + screen + "]";
    }
    

}
