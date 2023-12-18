package com.khagani.to_do_app.exceptions;

import lombok.Builder;

import java.util.Date;
@Builder
public class ErrorDetails {
    private final Date timeStamp;
    private final String message;
    private final String details;

    public ErrorDetails(Date timeStamp, String message, String details) {
        super();
        this.timeStamp = timeStamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
