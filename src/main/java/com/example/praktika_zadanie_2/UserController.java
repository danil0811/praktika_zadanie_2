package com.example.praktika_zadanie_2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    List<User> users = new ArrayList<>();
    private int nextUserID = 1;
    @PostMapping
    @ResponseBody
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        if (isValidUser(newUser)) {
            newUser.setId(nextUserID++);
            users.add(newUser); // Сохраните нового пользователя в списке.
            return ResponseEntity.ok(newUser); // Вернется ответ 200 OK с сохраненным пользователем.
        } else {
            throw new UserException("Некорректный запрос на создание пользователя");
        }
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

    @PatchMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<User> updateUser(@PathVariable Integer userId, @RequestBody (required = false) User updatedUser) {
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

        // Добавьте проверку на пустое тело запроса (updatedUser не равен null)
        if (updatedUser == null) {
            return ResponseEntity.ok(userToUpdate);
        }

        // Проверьте, не является ли обновленный пользователь пустым и содержит ли он непустые поля
        if (updatedUser.getName() != null && !updatedUser.getName().isEmpty()) {
            userToUpdate.setName(updatedUser.getName());
        }

        if (updatedUser.getDefaultCurrency() != null) {
            userToUpdate.setDefaultCurrency(updatedUser.getDefaultCurrency());
        }

        return ResponseEntity.ok(userToUpdate);
    }


    @GetMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<User> getUseById(@PathVariable Integer userId) {
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

    @DeleteMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<User> deleteUser(@PathVariable Integer userId) {
        User userToDelete = null;
        for (User user : users) {
            if (user.getId().equals(userId)) {
                userToDelete = user;
                break;
            }
        }

        if (userToDelete != null) {
            users.remove(userToDelete);
            return ResponseEntity.ok(userToDelete); // Верните код состояния 200 для удаленного пользователя.
        } else {
            return ResponseEntity.ok().build(); // Возвращает код состояния 200, если пользователь с указанным идентификатором не найден.
        }
    }

    @GetMapping
    @ResponseBody
    public List<User> getAllUsers() {
        if (users.isEmpty()) {
            return new ArrayList<>(); // Возвращает пустую коллекцию, когда в ней нет пользователей
        }
        return users;
    }

}