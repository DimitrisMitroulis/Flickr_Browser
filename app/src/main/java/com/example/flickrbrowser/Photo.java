package com.example.flickrbrowser;

public class Photo {
    //simple class to get the image
    private String mTitle;
    private String mAuthor;
    private String mAuthorId;
    private String mTags;
    private String mImage;
    private String mPublishedDate;
    private String mDescription;

    public Photo(String title, String author, String authorId, String tags, String image, String publishedDate, String description) {
        mTitle = title;
        mAuthor = author;
        mAuthorId = authorId;
        mTags = tags;
        mImage = image;
        mPublishedDate = publishedDate;
        mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getAuthorId() {
        return mAuthorId;
    }

    public String getTags() {
        return mTags;
    }

    public String getImage() {
        return mImage;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }

    public String getDescription() {
        return mDescription;
    }


    @Override
    public String toString() {
        return "Photo{" +
                "mTitle='" + mTitle + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mAuthorId='" + mAuthorId + '\'' +
                ", mTags='" + mTags + '\'' +
                ", mImage='" + mImage + '\'' +
                ", mPublishedDate='" + mPublishedDate + '\'' +
                ", mDescription='" + mDescription + '\'' +
                '}';
    }
}
