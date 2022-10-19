package vasconcelos.wellington.personapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vasconcelos.wellington.personapi.entity.Person;

public interface PersonRepository  extends JpaRepository<Person, Long> {
}
