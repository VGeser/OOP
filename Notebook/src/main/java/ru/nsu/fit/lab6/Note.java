package ru.nsu.fit.lab6;

import com.google.gson.annotations.SerializedName;

public class Note {
    @SerializedName("timestamp")
    private final String timestamp;

    @SerializedName("text")
    private final String text;

    public Note(String timestamp, String text) {
        this.timestamp = timestamp;
        this.text = text;
    }

    /**
     * there are no setters because no editing is required
     */

    public String getTimestamp() {
        return timestamp;
    }

    public String getText() {
        return text;
    }

}
