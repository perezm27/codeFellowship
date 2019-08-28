package com.perezm27.CodeFellowship.models;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class UserPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String body;
    Date createdAt;

    @ManyToOne
    ApplicationUser user;

    public UserPost() {}

    public UserPost(String body, Date createdAt, ApplicationUser user) {
        this.body = body;
        this.createdAt = createdAt;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String toString(){
        return String.format("%s. created by: %s at %s", this.body, this.user.username, this.createdAt);
    }
}