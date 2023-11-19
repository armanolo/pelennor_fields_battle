package es.mmm.managerartillery.domain.valueobject;

public class Url {
    private final String value;

    public Url(String url){
        if (url == null) {
            throw new IllegalArgumentException("URL data cannot be null.");
        }

        if (!url.matches("http://\\w+(:\\d+)?")){
            throw new IllegalArgumentException("URL data is not correct.");
        }

        this.value = url;
    }

    public String value() {
        return value;
    }
}
