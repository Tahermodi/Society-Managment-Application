package sgp.project.society;

public class DummyClassMyFlatActivity {

    private String id;
    private String name;
    private String mobile_number;

    public DummyClassMyFlatActivity() {
    }

    public DummyClassMyFlatActivity(String id, String name, String mobile_number) {
        this.id = id;
        this.name = name;
        this.mobile_number = mobile_number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }
}
