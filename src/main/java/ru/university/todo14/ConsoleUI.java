package ru.university.todo14;

import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner;
    private final SocialNetwork network;
    private User currentUser;

    private static final String[] ZODIAC_SIGNS = {
            "Овен", "Телец", "Близнецы", "Рак",
            "Лев", "Дева", "Весы", "Скорпион",
            "Стрелец", "Козерог", "Водолей", "Рыбы"
    };

    public ConsoleUI() {
        scanner = new Scanner(System.in);
        network = new SocialNetwork();
        currentUser = null;
    }

    public void start() {
        boolean running = true;

        System.out.println("================================");
        System.out.println("       СОЦИАЛЬНАЯ СЕТЬ");
        System.out.println("          Вариант 13");
        System.out.println("================================");

        while (running) {
            showMenu();
            int choice = readInt("Выберите действие: ");

            switch (choice) {
                case 1:
                    createProfile();
                    break;
                case 2:
                    showUsers();
                    break;
                case 3:
                    selectUser();
                    break;
                case 4:
                    viewProfile();
                    break;
                case 5:
                    addPhoto();
                    break;
                case 6:
                    subscribe();
                    break;
                case 7:
                    ratePhoto();
                    break;
                case 8:
                    blockUser();
                    break;
                case 9:
                    unblockUser();
                    break;
                case 10:
                    showRelationships();
                    break;
                case 0:
                    running = false;
                    System.out.println("Работа программы завершена.");
                    break;
                default:
                    System.out.println("Такого пункта меню нет.");
            }
        }

        scanner.close();
    }

    private void showMenu() {
        System.out.println("\n========== МЕНЮ ==========");

        if (currentUser == null) {
            System.out.println("Текущий пользователь: не выбран");
        } else {
            System.out.println("Текущий пользователь: " + currentUser.getName());
        }

        System.out.println("1. Создать профиль");
        System.out.println("2. Показать всех пользователей");
        System.out.println("3. Выбрать пользователя");
        System.out.println("4. Посмотреть профиль");
        System.out.println("5. Добавить фотографию");
        System.out.println("6. Подписаться на пользователя");
        System.out.println("7. Оценить фотографию");
        System.out.println("8. Заблокировать пользователя");
        System.out.println("9. Разблокировать пользователя");
        System.out.println("10. Посмотреть подписки и блокировки");
        System.out.println("0. Выход");
    }

    private void createProfile() {
        System.out.println("\n--- Создание профиля ---");
        String name = readText("Введите имя: ");

        System.out.println("\nВыберите пол:");
        System.out.println("1. Муж");
        System.out.println("2. Жен");

        int genderChoice;
        while (true) {
            genderChoice = readInt("Ваш выбор: ");
            if (genderChoice == 1 || genderChoice == 2) {
                break;
            }
            System.out.println("Введите 1 или 2.");
        }

        String gender = genderChoice == 1 ? "Муж" : "Жен";
        int age = readInt("Введите возраст: ");

        while (age < 1 || age > 120) {
            System.out.println("Возраст должен быть от 1 до 120.");
            age = readInt("Введите возраст ещё раз: ");
        }

        String zodiacSign = readZodiacSign();
        User user = network.createUser(name, gender, age, zodiacSign);

        System.out.println("\nПрофиль успешно создан.");
        System.out.println("Ваш ID: " + user.getId());

        if (currentUser == null) {
            currentUser = user;
            System.out.println("Вы вошли в созданный профиль.");
        }
    }

    private String readZodiacSign() {
        System.out.println("\nВыберите знак зодиака:");
        for (int i = 0; i < ZODIAC_SIGNS.length; i++) {
            System.out.println((i + 1) + ". " + ZODIAC_SIGNS[i]);
        }

        while (true) {
            int choice = readInt("Ваш выбор: ");
            if (choice >= 1 && choice <= ZODIAC_SIGNS.length) {
                return ZODIAC_SIGNS[choice - 1];
            }
            System.out.println("Введите число от 1 до 12.");
        }
    }

    private void showUsers() {
        System.out.println("\n--- Список пользователей ---");
        if (network.getUsers().isEmpty()) {
            System.out.println("Пользователей пока нет.");
            return;
        }

        for (User user : network.getUsers()) {
            System.out.println("ID: " + user.getId() + " | Имя: " + user.getName());
        }
    }

    private void selectUser() {
        if (network.getUsers().isEmpty()) {
            System.out.println("Сначала создайте профиль.");
            return;
        }

        showUsers();
        int id = readInt("Введите ID пользователя: ");
        User user = network.findUser(id);

        if (user == null) {
            System.out.println("Пользователь не найден.");
            return;
        }

        currentUser = user;
        System.out.println("Вы вошли в профиль: " + user.getName());
    }

    private void viewProfile() {
        if (!checkCurrentUser()) {
            return;
        }

        showUsers();
        int id = readInt("Введите ID профиля для просмотра: ");
        User target = network.findUser(id);

        if (target == null) {
            System.out.println("Пользователь не найден.");
            return;
        }

        network.showProfile(currentUser, target);
    }

    private void addPhoto() {
        if (!checkCurrentUser()) {
            return;
        }

        String photoName = readText("Введите название фотографии: ");
        currentUser.addPhoto(photoName);
        System.out.println("Фотография добавлена.");
    }

    private void subscribe() {
        if (!checkCurrentUser()) {
            return;
        }

        showUsers();
        int id = readInt("Введите ID пользователя: ");
        User target = network.findUser(id);

        if (target == null) {
            System.out.println("Пользователь не найден.");
            return;
        }

        network.subscribe(currentUser, target);
    }

    private void ratePhoto() {
        if (!checkCurrentUser()) {
            return;
        }

        showUsers();
        int id = readInt("Введите ID владельца фотографии: ");
        User target = network.findUser(id);

        if (target == null) {
            System.out.println("Пользователь не найден.");
            return;
        }

        if (currentUser.getId() == target.getId()) {
            System.out.println("Нельзя оценивать собственные фотографии.");
            return;
        }

        if (network.isBlocked(currentUser, target)) {
            System.out.println("Оценка невозможна: есть блокировка.");
            return;
        }

        if (!network.areFriends(currentUser, target)) {
            System.out.println("Оценивать фотографии могут только друзья.");
            return;
        }

        if (target.getPhotos().isEmpty()) {
            System.out.println("У пользователя пока нет фотографий.");
            return;
        }

        System.out.println("\nФотографии пользователя:");
        for (int i = 0; i < target.getPhotos().size(); i++) {
            System.out.println((i + 1) + ". " + target.getPhotos().get(i).getName());
        }

        int photoNumber = readInt("Введите номер фотографии: ");
        System.out.println("1. Лайк");
        System.out.println("2. Дизлайк");
        int rating = readInt("Выберите оценку: ");

        if (rating != 1 && rating != 2) {
            System.out.println("Неверный вариант оценки.");
            return;
        }

        network.ratePhoto(currentUser, target, photoNumber, rating == 1);
    }

    private void blockUser() {
        if (!checkCurrentUser()) {
            return;
        }

        showUsers();
        int id = readInt("Введите ID пользователя для блокировки: ");
        User target = network.findUser(id);

        if (target == null) {
            System.out.println("Пользователь не найден.");
            return;
        }

        network.block(currentUser, target);
    }

    private void unblockUser() {
        if (!checkCurrentUser()) {
            return;
        }

        showUsers();
        int id = readInt("Введите ID пользователя для разблокировки: ");
        User target = network.findUser(id);

        if (target == null) {
            System.out.println("Пользователь не найден.");
            return;
        }

        network.unblock(currentUser, target);
    }

    private void showRelationships() {
        if (!checkCurrentUser()) {
            return;
        }

        System.out.println("\n--- Подписки ---");
        if (currentUser.getSubscriptions().isEmpty()) {
            System.out.println("Подписок нет.");
        } else {
            for (int id : currentUser.getSubscriptions()) {
                User user = network.findUser(id);
                if (user != null) {
                    System.out.println(user.getName());
                }
            }
        }

        System.out.println("\n--- Заблокированные пользователи ---");
        if (currentUser.getBlockedUsers().isEmpty()) {
            System.out.println("Заблокированных пользователей нет.");
        } else {
            for (int id : currentUser.getBlockedUsers()) {
                User user = network.findUser(id);
                if (user != null) {
                    System.out.println(user.getName());
                }
            }
        }
    }

    private boolean checkCurrentUser() {
        if (currentUser == null) {
            System.out.println("Сначала создайте профиль или выберите пользователя.");
            return false;
        }
        return true;
    }

    private String readText(String message) {
        while (true) {
            System.out.print(message);
            String text = scanner.nextLine().trim();
            if (!text.isEmpty()) {
                return text;
            }
            System.out.println("Поле не должно быть пустым.");
        }
    }

    private int readInt(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число.");
            }
        }
    }
}
