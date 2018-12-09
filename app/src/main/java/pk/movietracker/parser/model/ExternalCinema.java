package pk.movietracker.parser.model;

import com.google.gson.annotations.SerializedName;

public class ExternalCinema {

    @SerializedName("id")
    private String externalId;
    @SerializedName("displayName")
    private String name;
    private String address;

    public ExternalCinema() { }

    public ExternalCinema(String externalId, String name, String address) {
        this.externalId = externalId;
        this.name = name;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "ExternalCinema{" +
                "externalId='" + externalId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
