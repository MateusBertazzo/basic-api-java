package br.com.mateusbertazzo.basicapi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel){
        var userOrNull = this.userRepository.findByUsername(userModel.getUsername());

        if (userOrNull != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User al ready exists");
        }

        var hashPassword = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        
        userModel.setPassword(hashPassword);
        
        var createdUser = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
