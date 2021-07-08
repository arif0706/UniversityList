package com.example.universitylist.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.universitylist.Room.ArrayTypeConvertor;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

@Entity(tableName = "University",indices = @Index(unique = true,value={"name"}))
public class Model {


    @PrimaryKey(autoGenerate = true)
    public int _id;

    @TypeConverters(ArrayTypeConvertor.class)
    @ColumnInfo(name="domains")
    public String[] domains;

    @TypeConverters(ArrayTypeConvertor.class)
    @ColumnInfo(name="web_pages")
    public String[] web_pages;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "alpha_two_code")
    public String alpha_two_code;

    @ColumnInfo(name = "state_province")
    @SerializedName("state-province")
    public String state_province;

    @ColumnInfo(name = "country")
    public String country;


    public String[] getDomains() {
        return domains;
    }

    public void setDomains(String[] domains) {
        this.domains = domains;
    }

    public String[] getWeb_pages() {
        return web_pages;
    }

    public void setWeb_pages(String[] web_pages) {
        this.web_pages = web_pages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlpha_two_code() {
        return alpha_two_code;
    }

    public void setAlpha_two_code(String alpha_two_code) {
        this.alpha_two_code = alpha_two_code;
    }

    public String getState_province() {
        return state_province;
    }

    public void setState_province(String state_province) {
        this.state_province = state_province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public static Comparator<Model> sortByName=new Comparator<Model>() {
        @Override
        public int compare(Model o1, Model o2) {

            return o1.getName().compareTo(o2.getName());

        }
    };

    @NonNull
    @Override
    public String toString() {
        return "Model{" +
                ", name='" + name + '\'' +
                ", alpha_two_code='" + alpha_two_code + '\'' +
                ", state_province='" + state_province + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
