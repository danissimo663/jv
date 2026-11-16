package ru.university.todo14;

import java.util.ArrayList;

public class Photo {
    private String name;
    private ArrayList<Integer> likes;
    private ArrayList<Integer> dislikes;

    public Photo(String name) {
        this.name = name;
        likes = new ArrayList<>();
        dislikes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getLikesCount() {
        return likes.size();
    }

    public int getDislikesCount() {
        return dislikes.size();
    }

    public void rate(int userId, boolean like) {
        likes.remove(Integer.valueOf(userId));
        dislikes.remove(Integer.valueOf(userId));

        if (like) {
            likes.add(userId);
        } else {
            dislikes.add(userId);
        }
    }
}
