package com.khagani.to_do_app.entity;


public enum UserType {
    ADMIN(1), // UserType ADMIN = new UserType(1);
    USER(2),
    MODERATOR(3);

    public final long id;

    UserType(long id) {
        this.id = id;
    }
}
