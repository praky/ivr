package org.motechproject.web.viewmodel;

public class KooKooRequest {
    private String sid;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    @Override
    public String toString() {
        return "KooKooRequest{" +
                "sid='" + sid + '\'' +
                '}';
    }
}
