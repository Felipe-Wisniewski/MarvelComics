package com.felipewisniewski.marvelcomics.Presenter;

public class Character {

    public String id;
    public String name;
    public String description;
    public String thumbnail;

    public Character() { }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getThumbnail() { return thumbnail; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }

    /*
    id (int, optional): The unique ID of the character resource.,
    name (string, optional): The name of the character.,
    description (string, optional): A short bio or description of the character.,
    modified (Date, optional): The date the resource was most recently modified.,
    resourceURI (string, optional): The canonical URL identifier for this resource.,
    urls (Array[Url], optional): A set of public web site URLs for the resource.,
    thumbnail (Image, optional): The representative image for this character.,
    comics (ComicList, optional): A resource list containing comics which feature this character.,
    stories (StoryList, optional): A resource list of stories in which this character appears.,
    events (EventList, optional): A resource list of events in which this character appears.,
    series (SeriesList, optional): A resource list of series in which this character appears.
    */
}
