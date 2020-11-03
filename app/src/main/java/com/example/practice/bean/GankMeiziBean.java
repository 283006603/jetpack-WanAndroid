package com.example.practice.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created By 大苏打
 * on 2020/11/3
 */
public class GankMeiziBean implements Parcelable{

    /**
     * _id : 5e959250808d6d2fe6b56eda
     * author : 鸢媛
     * category : Girl
     * createdAt : 2020-05-25 08:00:00
     * desc : 与其安慰自己平凡可贵， 不如拼尽全力活得漂亮。 ​ ​​​​
     * images : ["http://gank.io/images/f4f6d68bf30147e1bdd4ddbc6ad7c2a2"]
     * likeCounts : 4
     * publishedAt : 2020-05-25 08:00:00
     * stars : 1
     * title : 第96期
     * type : Girl
     * url : http://gank.io/images/f4f6d68bf30147e1bdd4ddbc6ad7c2a2
     * views : 9165
     */

    private String _id;
    private String author;
    private String category;
    private String createdAt;
    private String desc;
    private int likeCounts;
    private String publishedAt;
    private int stars;
    private String title;
    private String type;
    private String url;
    private int views;
    private List<String> images;

    public GankMeiziBean(){
    }

    protected GankMeiziBean(Parcel in){
        _id = in.readString();
        author = in.readString();
        category = in.readString();
        createdAt = in.readString();
        desc = in.readString();
        likeCounts = in.readInt();
        publishedAt = in.readString();
        stars = in.readInt();
        title = in.readString();
        type = in.readString();
        url = in.readString();
        views = in.readInt();
        images = in.createStringArrayList();
    }

    public static final Creator<GankMeiziBean> CREATOR = new Creator<GankMeiziBean>(){
        @Override
        public GankMeiziBean createFromParcel(Parcel in){
            return new GankMeiziBean(in);
        }

        @Override
        public GankMeiziBean[] newArray(int size){
            return new GankMeiziBean[size];
        }
    };

    public String get_id(){
        return _id;
    }

    public void set_id(String _id){
        this._id = _id;
    }

    public String getAuthor(){
        return author;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }

    public String getDesc(){
        return desc;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public int getLikeCounts(){
        return likeCounts;
    }

    public void setLikeCounts(int likeCounts){
        this.likeCounts = likeCounts;
    }

    public String getPublishedAt(){
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt){
        this.publishedAt = publishedAt;
    }

    public int getStars(){
        return stars;
    }

    public void setStars(int stars){
        this.stars = stars;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public int getViews(){
        return views;
    }

    public void setViews(int views){
        this.views = views;
    }

    public List<String> getImages(){
        return images;
    }

    public void setImages(List<String> images){
        this.images = images;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(_id);
        dest.writeString(author);
        dest.writeString(category);
        dest.writeString(createdAt);
        dest.writeString(desc);
        dest.writeInt(likeCounts);
        dest.writeString(publishedAt);
        dest.writeInt(stars);
        dest.writeString(title);
        dest.writeString(type);
        dest.writeString(url);
        dest.writeInt(views);
        dest.writeStringList(images);
    }
}
