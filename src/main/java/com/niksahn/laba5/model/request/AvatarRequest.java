package com.niksahn.laba5.model.request;

public class AvatarRequest {
    public String file;

    public AvatarRequest(String file) {
        this.file = file;
    }

    public AvatarRequest() {
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFile() {
        return file;
    }
}
