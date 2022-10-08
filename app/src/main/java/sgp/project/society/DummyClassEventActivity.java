package sgp.project.society;

public class DummyClassEventActivity {

    private String id;
    private String event_name;
    private String event_date;
    private String event_day;
    private String event_desc;

    public DummyClassEventActivity() {
    }

    public DummyClassEventActivity(String id, String event_name, String event_date, String event_day, String event_desc) {
        this.id = id;
        this.event_name = event_name;
        this.event_date = event_date;
        this.event_day = event_day;
        this.event_desc = event_desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_day() {
        return event_day;
    }

    public void setEvent_day(String event_day) {
        this.event_day = event_day;
    }

    public String getEvent_desc() {
        return event_desc;
    }

    public void setEvent_desc(String event_desc) {
        this.event_desc = event_desc;
    }
}
