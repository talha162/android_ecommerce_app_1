package com.example.project.models;

public class ReviewModel {
    String username1,comment1;
    String imageView1;
    float ratingBar1;

    public ReviewModel(String username1, String comment1, String imageView1, float ratingBar1) {
        this.username1 = username1;
        this.comment1 = comment1;
        this.imageView1 = imageView1;
        this.ratingBar1 = ratingBar1;
    }

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public String getComment1() {
        return comment1;
    }

    public void setComment1(String comment1) {
        this.comment1 = comment1;
    }

    public String getProfilepic() {
        return imageView1;
    }

    public void setProfile(String imageView1) {
        this.imageView1 = imageView1;
    }

    public float getRatingBar1() {
        return ratingBar1;
    }

    public void setRatingBar1(float ratingBar1) {
        this.ratingBar1 = ratingBar1;
    }
}
