package com.example.praktika_zadanie_2;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserService {
    private List<User> users = new ArrayList<>();
    private int nextUserID = 1;

    public ResponseEntity<User> createUser(User newUser) {
        if (isValidUser(newUser)) {
            newUser.setId(nextUserID++);
            users.add(newUser);
            return ResponseEntity.ok(newUser);
        } else {
            throw new UserException("Некорректный запрос на создание пользователя");
        }
    }

    public ResponseEntity<User> updateUser(Integer userId, User updatedUser) {
        User userToUpdate = null;

        // Нахождение пользователя по идентификатору
        for (User user : users) {
            if (user.getId().equals(userId)) {
                userToUpdate = user;
                break;
            }
        }

        if (userToUpdate == null) {
            return ResponseEntity.notFound().build();
        }

        // Добавление проверки на пустое тело запроса (updatedUser не равен null)
        if (updatedUser == null) {
            return ResponseEntity.ok(userToUpdate);
        }

        // Проверка, не является ли обновленный пользователь пустым и содержит ли он непустые поля
        if (updatedUser.getName() != null && !updatedUser.getName().isEmpty()) {
            userToUpdate.setName(updatedUser.getName());
        }

        if (updatedUser.getDefaultCurrency() != null) {
            userToUpdate.setDefaultCurrency(updatedUser.getDefaultCurrency());
        }

        return ResponseEntity.ok(userToUpdate);
    }

    public ResponseEntity<User> getUserById(Integer userId) {
        User userById = null;
        for (User user : users) {
            if (user.getId().equals(userId)) {
                userById = user;
                break;
            }
        }
        if (userById != null) {
            return ResponseEntity.ok(userById); // Вернет пользователю код состояния 200, если он существует.
        }
        return ResponseEntity.notFound().build(); // Возвращает код состояния 404, если пользователь не существует.
    }

    public ResponseEntity<User> deleteUser(Integer userId) {
        User userToDelete = null;
        for (User user : users) {
            if (user.getId().equals(userId)) {
                userToDelete = user;
                break;
            }
        }

        if (userToDelete != null) {
            users.remove(userToDelete);
            return ResponseEntity.ok(userToDelete); // Возвращает код состояния 200 для удаленного пользователя.
        } else {
            return ResponseEntity.ok().build(); // Возвращает код состояния 200, если пользователь с указанным идентификатором не найден.
        }
    }

    public List<User> getAllUsers() {
        if (users.isEmpty()) {
            return new ArrayList<>(); // Возвращает пустую коллекцию, когда в ней нет пользователей
        }
        return users;
    }

    private boolean isValidUser(User user) {
        if (user == null) {
            return false; // Пользователь не может быть null
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            return false; // name должен быть непустой строкой.
        }
        return true;
    }
}
