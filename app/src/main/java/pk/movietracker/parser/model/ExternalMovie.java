package pk.movietracker.parser.model;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;
import java.util.List;

public class ExternalMovie {

    @SerializedName("id")
    private String externalId;
    private String name;
    private Integer length;
    private String link;
    private String releaseYear;
    private String posterLink;
    private String description;
    @SerializedName("attributeIds")
    private List<String> externalAttributes = new LinkedList<>();
    private List<ExternalEvent> events = new LinkedList<>();

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ExternalEvent> getEvents() {
        return events;
    }

    public void setEvents(List<ExternalEvent> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "ExternalMovie{" +
                "externalId='" + externalId + '\'' +
                ", name='" + name + '\'' +
                ", length=" + length +
                ", link='" + link + '\'' +
                ", releaseYear='" + releaseYear + '\'' +
                ", posterLink='" + posterLink + '\'' +
                ", description='" + description + '\'' +
                ", externalAttributes=" + externalAttributes +
                ", events=" + events +
                '}';
    }
}
