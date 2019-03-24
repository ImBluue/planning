package com.example.planning.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class Cursus implements Parcelable {

    public Cursus(String campus, String school, String department, String training, String group) {
        this.campus = campus;
        this.school = school;
        this.department = department;
        this.training = training;
        this.group = group;
    }
    private String campus = "";

    @Override
    public String toString() {
        return "Cursus{" +
                "campus='" + campus + '\'' +
                ", school='" + school + '\'' +
                ", department='" + department + '\'' +
                ", training='" + training + '\'' +
                ", group='" + group + '\'' +
                '}';
    }

    private String school = "";
    private String department = "";
    private String training = "";
    private String group = "";

    public Cursus() {
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTraining() {
        return training;
    }

    public void setTraining(String training) {
        this.training = training;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Cursus(Parcel in){
        // the order needs to be the same as in writeToParcel() method
        this.campus = in.readString();
        this.school = in.readString();
        this.department = in.readString();
        this.training = in.readString();
        this.group = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.campus);
        parcel.writeString(this.school);
        parcel.writeString(this.department);
        parcel.writeString(this.training);
        parcel.writeString(this.group);
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Cursus createFromParcel(Parcel in) {
            return new Cursus(in);
        }

        public Cursus[] newArray(int size) {
            return new Cursus[size];
        }
    };
}
