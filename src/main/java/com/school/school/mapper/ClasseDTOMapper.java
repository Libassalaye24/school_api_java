package com.school.school.mapper;

import com.school.school.dto.ClasseDTO;
import com.school.school.model.Classe;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ClasseDTOMapper implements Function<Classe , ClasseDTO> {
    @Override
    public ClasseDTO apply(Classe classe) {
       return null;
    }
}
