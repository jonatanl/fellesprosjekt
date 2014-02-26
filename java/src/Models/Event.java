package Models;

import java.util.Date;

public class Event {
    private String title;
    private Date date;
    private String startTime;
    private String endTime;
    private String description;
    private String location;
    private Room room;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Room getRom() {
        return room;
    }

    public void setRom(Room rom) {
        room = rom;
    }

    @Override
    public String toString() {
        return "Event{" +
                "title='" + title + '\'' +
                ", date=" + date +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", room=" + room +
                '}';
    }
}
