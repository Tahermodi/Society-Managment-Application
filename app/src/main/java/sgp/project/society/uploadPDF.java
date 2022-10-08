package sgp.project.society;

public class uploadPDF {
    private String flat_no;
    private String url;

    public uploadPDF() {
    }

    public uploadPDF(String flat_no, String url) {
        this.flat_no = flat_no;
        this.url = url;
    }

    public String getFlat_no() {
        return flat_no;
    }

    public String getUrl() {
        return url;
    }

}
