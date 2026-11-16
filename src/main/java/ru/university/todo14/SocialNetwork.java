package ru.university.todo14;

import java.util.ArrayList;

public class SocialNetwork {
    private ArrayList<User> users;
    private int nextId;

    public SocialNetwork() {
        users = new ArrayList<>();
        nextId = 1;
    }

    public User createUser(String name, String gender,
                           int age, String zodiacSign) {
        User user = new User(nextId, name, gender, age, zodiacSign);
        users.add(user);
        nextId++;
        return user;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public User findUser(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public boolean isBlocked(User first, User second) {
        return first.getBlockedUsers().contains(second.getId())
                || second.getBlockedUsers().contains(first.getId());
    }

    public boolean areFriends(User first, User second) {
        return first.getSubscriptions().contains(second.getId())
                && second.getSubscriptions().contains(first.getId());
    }

    public void showProfile(User viewer, User target) {
        System.out.println("\n--- Профиль пользователя ---");
        System.out.println("Имя: " + target.getName());

        if (viewer.getId() == target.getId()) {
            System.out.println("Пол: " + target.getGender());
            System.out.println("Возраст: " + target.getAge());
            System.out.println("Знак зодиака: " + target.getZodiacSign());
            showPhotos(target);
            return;
        }

        if (isBlocked(viewer, target)) {
            System.out.println("Остальная информация недоступна.");
            return;
        }

        System.out.println("Пол: " + target.getGender());
        System.out.println("Возраст: " + target.getAge());
        System.out.println("Знак зодиака: " + target.getZodiacSign());

        if (viewer.getSubscriptions().contains(target.getId())) {
            showPhotos(target);
        } else {
            System.out.println("Фотографии доступны только подписчикам.");
        }
    }

    private void showPhotos(User user) {
        if (user.getPhotos().isEmpty()) {
            System.out.println("Фотографий пока нет.");
            return;
        }

        System.out.println("Фотографии:");
        for (int i = 0; i < user.getPhotos().size(); i++) {
            Photo photo = user.getPhotos().get(i);
            System.out.println(
                    (i + 1) + ". " + photo.getName()
                            + " | Лайки: " + photo.getLikesCount()
                            + " | Дизлайки: " + photo.getDislikesCount()
            );
        }
    }

    public void subscribe(User viewer, User target) {
        if (viewer.getId() == target.getId()) {
            System.out.println("Нельзя подписаться на самого себя.");
            return;
        }

        if (isBlocked(viewer, target)) {
            System.out.println("Подписка невозможна: есть блокировка.");
            return;
        }

        if (viewer.getSubscriptions().contains(target.getId())) {
            System.out.println("Вы уже подписаны на этого пользователя.");
            return;
        }

        viewer.getSubscriptions().add(target.getId());
        System.out.println("Вы успешно подписались на пользователя.");

        if (areFriends(viewer, target)) {
            System.out.println("Теперь вы друзья!");
        }
    }

    public void block(User viewer, User target) {
        if (viewer.getId() == target.getId()) {
            System.out.println("Нельзя заблокировать самого себя.");
            return;
        }

        if (viewer.getBlockedUsers().contains(target.getId())) {
            System.out.println("Этот пользователь уже заблокирован.");
            return;
        }

        viewer.getBlockedUsers().add(target.getId());
        System.out.println("Пользователь заблокирован.");
    }

    public void unblock(User viewer, User target) {
        if (viewer.getBlockedUsers().remove(Integer.valueOf(target.getId()))) {
            System.out.println("Блокировка снята.");
        } else {
            System.out.println("Этот пользователь не заблокирован вами.");
        }
    }

    public void ratePhoto(User viewer, User target,
                          int photoNumber, boolean like) {
        if (viewer.getId() == target.getId()) {
            System.out.println("Нельзя оценивать собственные фотографии.");
            return;
        }

        if (isBlocked(viewer, target)) {
            System.out.println("Оценка невозможна: есть блокировка.");
            return;
        }

        if (!areFriends(viewer, target)) {
            System.out.println("Оценивать фотографии могут только друзья.");
            return;
        }

        if (photoNumber < 1 || photoNumber > target.getPhotos().size()) {
            System.out.println("Фотографии с таким номером нет.");
            return;
        }

        Photo photo = target.getPhotos().get(photoNumber - 1);
        photo.rate(viewer.getId(), like);

        if (like) {
            System.out.println("Вы поставили лайк.");
        } else {
            System.out.println("Вы поставили дизлайк.");
        }
    }
}
