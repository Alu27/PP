package system.model;

public class Keywords {
    private Integer id;
    private String keywords;

    public Keywords(int id, String keywords) {
        this.keywords = keywords;
        this.id = id;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
