package az.developia.task_management.utilities;

import org.springframework.security.core.context.SecurityContextHolder;

public class Methods {

    public static String findActiveUsername() {

        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
