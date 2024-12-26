package az.developia.task_management.configures;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfigure {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
