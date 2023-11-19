package es.mmm.managerartillery.domain.valueobject;

public class CannonAvailable {

    private final PositiveNumber cannonId ;
    private final Url url;

    public CannonAvailable(Integer id, String path){
        this.cannonId = new PositiveNumber(id);
        this.url = new Url(path);
    }

    public Integer getCannonId() {
        return cannonId.value();
    }

    public String getUrl() {
        return url.value();
    }
}
