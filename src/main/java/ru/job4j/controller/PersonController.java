package ru.job4j.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.domain.Person;
import ru.job4j.service.PersonService;

import java.util.List;


@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;
    private BCryptPasswordEncoder encoder;
    public PersonController(PersonService personService,
                            BCryptPasswordEncoder encoder)  {
        this.personService = personService;
        this.encoder = encoder;
    }

    @GetMapping("/")
    public List<Person> findAll() {
        return this.personService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        var person = this.personService.findById(id);
        return new ResponseEntity<Person>(
                person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        return new ResponseEntity<Person>(
                this.personService.create(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        this.personService.create(person);
        if (this.personService.findById(person.getId()).equals(person)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        this.personService.delete(id);
        if (this.personService.findById(id) != null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }


    @PostMapping("/sign-up")
    public ResponseEntity<Person> signUp(@RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        Person newPerson = personService.create(person);
        if (newPerson == null) {
            return new ResponseEntity<Person>(new Person(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Person>(
                newPerson,
                HttpStatus.CREATED
        );
    }

}