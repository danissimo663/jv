package ru.university.todo14;

import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String gender;
    private int age;
    private String zodiacSign;

    private ArrayList<Photo> photos;
    private ArrayList<Integer> subscriptions;
    private ArrayList<Integer> blockedUsers;

    public User(int id, String name, String gender,
                int age, String zodiacSign) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.zodiacSign = zodiacSign;

        photos = new ArrayList<>();
        subscriptions = new ArrayList<>();
        blockedUsers = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getZodiacSign() {
        return zodiacSign;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public ArrayList<Integer> getSubscriptions() {
        return subscriptions;
    }

    public ArrayList<Integer> getBlockedUsers() {
        return blockedUsers;
    }

    public void addPhoto(String photoName) {
        photos.add(new Photo(photoName));
    }
}
