package com.es.phoneshop.mapping;

public class MinicartLink {
    private String nameLink;
    private String link;

    public MinicartLink(String nameLink, String link) {
        this.nameLink = nameLink;
        this.link = link;
    }

    public String getNameLink() {
        return nameLink;
    }

    public void setNameLink(String nameLink) {
        this.nameLink = nameLink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
