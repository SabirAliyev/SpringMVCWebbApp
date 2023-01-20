package com.sabirali.springcource.util;

import com.sabirali.springcource.dao.PersonDAO;
import com.sabirali.springcource.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator; // add this import manually before implement Validator methods.

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    // method supports() does not allow to use it for another classes.
    @Override
    public boolean supports(Class<?> clazz) {
        // return TRUE if validation class is Person.
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        // Check whether this object has such E-mail.
        // Using .isPresent() method because we use Optional wrapper on person.getEmail() method.
        if (personDAO.show(person.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "This E-mail is already taken");
        }

        // old version of logic with using !=null (not recommended)
        // if (personDAO.show(person.getEmail()) != null) {
        //      errors.rejectValue("email", "", "This E-mail is already taken");
        // }
    }
}
