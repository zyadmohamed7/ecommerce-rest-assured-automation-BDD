package org.example.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerId {

   // @JsonProperty("id") // Must match the API response field
    private String id;

    public CustomerId() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
/*
    @Override
    public String toString() {
        return "OrderIdPojo{id='" + id + "'}";
    }

 */
}
