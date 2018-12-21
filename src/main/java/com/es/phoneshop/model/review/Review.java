package com.es.phoneshop.model.review;

public class Review {
    private String comment;
    private String name;
    private int rating;

    public Review(String comment, String name, int rating) {
        this.comment = comment;
        this.name = name;
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
