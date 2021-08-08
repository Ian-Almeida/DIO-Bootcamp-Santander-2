package com.dio.personapi.service;

import com.dio.personapi.dto.response.MessageResponseDTO;
import com.dio.personapi.dto.request.PersonDTO;
import com.dio.personapi.entity.Person;
import com.dio.personapi.exception.PersonNotFoundException;
import com.dio.personapi.mapper.PersonMapper;
import com.dio.personapi.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private final PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    public MessageResponseDTO createPerson(PersonDTO personDTO) {

        Person personToSave = personMapper.toModel(personDTO);

        Person createdPerson = personRepository.save(personToSave);
        return createMessageResponse(createdPerson.getId(), "Created Person with ID ");
    }

    public List<PersonDTO> listAll() {
        List<Person> allPeople = personRepository.findAll();

        return allPeople.stream().map(personMapper::toDTO).collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Person person = verifyIdExists(id);
        return personMapper.toDTO(person);
    }

    public void delete(Long id) throws PersonNotFoundException {
        verifyIdExists(id);
        personRepository.deleteById(id);
    }


    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {

        verifyIdExists(id);

        Person personToUpdate = personMapper.toModel(personDTO);

        Person updatedPerson = personRepository.save(personToUpdate);
        return createMessageResponse(updatedPerson.getId(), "Updated Person with ID ");
    }

    private Person verifyIdExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id).orElseThrow(()-> new PersonNotFoundException(id));
    }

    private MessageResponseDTO createMessageResponse(Long id, String s) {
        return MessageResponseDTO
                .builder()
                .message(s + id)
                .build();
    }


}
