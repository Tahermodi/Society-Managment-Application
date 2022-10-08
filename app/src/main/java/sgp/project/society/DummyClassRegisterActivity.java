package sgp.project.society;

public class DummyClassRegisterActivity {

    private String et_full_name;
    private String et_flat_no;
    private String et_mobile_number;
    private String et_no_of_twowheel;
    private String et_no_of_fourwheel;
    private String et_password;
    private Integer number_of_member;

    public DummyClassRegisterActivity() {

    }

    public DummyClassRegisterActivity(String et_full_name, String et_flat_no, String et_mobile_number, String et_no_of_twowheel, String et_no_of_fourwheel, String et_password, Integer number_of_member) {
        this.et_full_name = et_full_name;
        this.et_flat_no = et_flat_no;
        this.et_mobile_number = et_mobile_number;
        this.et_no_of_twowheel = et_no_of_twowheel;
        this.et_no_of_fourwheel = et_no_of_fourwheel;
        this.et_password = et_password;
        this.number_of_member = number_of_member;
    }

    public String getEt_full_name() {
        return et_full_name;
    }

    public void setEt_full_name(String et_full_name) {
        this.et_full_name = et_full_name;
    }

    public String getEt_flat_no() {
        return et_flat_no;
    }

    public void setEt_flat_no(String et_flat_no) {
        this.et_flat_no = et_flat_no;
    }

    public String getEt_mobile_number() {
        return et_mobile_number;
    }

    public void setEt_mobile_number(String et_mobile_number) {
        this.et_mobile_number = et_mobile_number;
    }

    public String getEt_no_of_twowheel() {
        return et_no_of_twowheel;
    }

    public void setEt_no_of_twowheel(String et_no_of_twowheel) {
        this.et_no_of_twowheel = et_no_of_twowheel;
    }

    public String getEt_no_of_fourwheel() {
        return et_no_of_fourwheel;
    }

    public void setEt_no_of_fourwheel(String et_no_of_fourwheel) {
        this.et_no_of_fourwheel = et_no_of_fourwheel;
    }

    public String getEt_password() {
        return et_password;
    }

    public void setEt_password(String et_password) {
        this.et_password = et_password;
    }

    public Integer getNumber_of_member() {
        return number_of_member;
    }

    public void setNumber_of_member(Integer number_of_member) {
        this.number_of_member = number_of_member;
    }
}
