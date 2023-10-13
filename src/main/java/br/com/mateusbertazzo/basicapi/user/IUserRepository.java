package br.com.mateusbertazzo.basicapi.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserModel, String> {
    UserModel findByUsername(String username);
}
