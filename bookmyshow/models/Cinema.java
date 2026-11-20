package bookmyshow.models;

import java.util.List;

public class Cinema {
    private String hallName;
    private List<Screen> screens;

    public Cinema(String hallName, List<Screen> screens)
    {
        this.hallName=hallName;
        this.screens=screens;
    }

    public String getHallName()
    {
        return hallName;
    }

    public List<Screen> getScreens()
    {
        return List.copyOf(screens);
    }
}
