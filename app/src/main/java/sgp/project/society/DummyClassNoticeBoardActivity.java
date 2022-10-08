package sgp.project.society;

public class DummyClassNoticeBoardActivity {

    private String id;
    private String notice;

    public DummyClassNoticeBoardActivity() {
    }

    public DummyClassNoticeBoardActivity(String id, String notice) {
        this.id = id;
        this.notice = notice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
}
