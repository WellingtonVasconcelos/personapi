package vasconcelos.wellington.personapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vasconcelos.wellington.personapi.dto.MessageResponseDTO;
import vasconcelos.wellington.personapi.dto.request.PersonDTO;
import vasconcelos.wellington.personapi.entity.Person;
import vasconcelos.wellington.personapi.dto.mapper.PersonMapper;
import vasconcelos.wellington.personapi.exception.PersonNotFoundException;
import vasconcelos.wellington.personapi.repository.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public MessageResponseDTO createPerson(PersonDTO personDTO) {
        Person personToSave = personMapper.toModel(personDTO);

        Person savePerson = personRepository.save(personToSave);
        return createMessageResponse(savePerson.getId(), "Created person with ID " );

    }

    public List<PersonDTO> listAll() {
        List<Person> allPeople = personRepository.findAll();
        return allPeople.stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Person person = verifyIfExists(id);

        return personMapper.toDTO(person);
    }

    public void delete(Long id) throws PersonNotFoundException {
        verifyIfExists(id);

        personRepository.deleteById(id);
    }



    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfExists(id);

        Person personToSave = personMapper.toModel(personDTO);

        Person updatedPerson = personRepository.save(personToSave);
        return createMessageResponse(updatedPerson.getId(), "Update person with ID ");
    }

    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }
    private static MessageResponseDTO createMessageResponse(Long id, String message) {
        return MessageResponseDTO
                .builder()
                .message(message + id)
                .build();
    }
}
