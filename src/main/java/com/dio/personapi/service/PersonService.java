package com.dio.personapi.service;

import com.dio.personapi.dto.MessageResponseDTO;
import com.dio.personapi.entity.Person;
import com.dio.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public MessageResponseDTO createPerson(Person person) {
        Person createdPerson = personRepository.save(person);
        return MessageResponseDTO
                .builder()
                .message("Created Person with ID " + createdPerson.getId())
                .build();
    }
}
