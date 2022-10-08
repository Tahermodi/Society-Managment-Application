package sgp.project.society;

public class DummyClassRulesActivity {

    private String id;
    private String rule;

    public DummyClassRulesActivity() {
    }

    public DummyClassRulesActivity(String id, String rule) {
        this.id = id;
        this.rule = rule;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
