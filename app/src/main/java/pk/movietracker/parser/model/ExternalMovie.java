package pk.movietracker.parser.model;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;
import java.util.List;

public class ExternalMovie {

    @SerializedName("id")
    private String externalId;
    private String name;
    private Integer length;
    private String releaseYear;
    private String posterLink;
    @SerializedName("attributeIds")
    private List<String> externalAttributes = new LinkedList<>();

    public ExternalMovie() { }

    public ExternalMovie(String externalId, String name, Integer length, String releaseYear, String posterLink, List<String> externalAttributes) {
        this.externalId = externalId;
        this.name = name;
        this.length = length;
        this.releaseYear = releaseYear;
        this.posterLink = posterLink;
        this.externalAttributes = externalAttributes;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getPosterLink() {
        return posterLink;
    }

    public void setPosterLink(String posterLink) {
        this.posterLink = posterLink;
    }

    public List<String> getExternalAttributes() {
        return externalAttributes;
    }

    public void setExternalAttributes(List<String> externalAttributes) {
        this.externalAttributes = externalAttributes;
    }

    @Override
    public String toString() {
        return "ExternalMovie{" +
                "externalId='" + externalId + '\'' +
                ", name='" + name + '\'' +
                ", length=" + length +
                ", releaseYear='" + releaseYear + '\'' +
                ", posterLink='" + posterLink + '\'' +
                ", externalAttributes.size()=" + externalAttributes.size() +
                '}';
    }
}
