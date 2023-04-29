package com.example.sq_lite;

import java.io.Serializable;

public class Noteq implements Serializable {
    private long id;
    private String name;
    private String note;

    public Noteq(long id, String nameh, String noteg) {
        this.id = id;
        this.name = nameh;
        this.note = noteg;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNote() {
        return note;
    }

}