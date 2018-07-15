package com.felipewisniewski.marvelcomics.Business;

public class Comics {

    private String id;
    private String total;
    private String title;
    private String description;

    public Comics() { }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTotal() { return total; }
    public void setTotal(String total) { this.total = total; }
}
