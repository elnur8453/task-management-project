package az.developia.task_management.services;

import az.developia.task_management.dtos.request.OwnerAddRequest;
import az.developia.task_management.entities.Owner;
import az.developia.task_management.entities.User;
import az.developia.task_management.exceptions.OurRuntimeException;
import az.developia.task_management.repositories.OwnerRepository;
import az.developia.task_management.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private OwnerRepository ownerRepository;

    public void addOwner(OwnerAddRequest ownerAddRequest) {
        String username = ownerAddRequest.getUsername();
        Optional<User> findedUser = userRepository.findById(username);
        if (findedUser.isPresent()) {
            throw new OurRuntimeException("User with this username already exists");
        }

        User user = new User();
        user.setUsername(ownerAddRequest.getUsername());

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode(ownerAddRequest.getPassword());

        user.setPassword("{bcrypt}" + password);
        user.setEnabled(1);
        userRepository.save(user);

        Owner owner = new Owner();
        owner.setUsername(user.getUsername());
        owner.setName(ownerAddRequest.getName());
        owner.setPhone(ownerAddRequest.getPhone());
        ownerRepository.save(owner);
    }


}
