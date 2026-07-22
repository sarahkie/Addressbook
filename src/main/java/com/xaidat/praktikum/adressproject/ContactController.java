package com.xaidat.praktikum.adressproject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping(path = "/app/v1/person")

public class ContactController {

    private static final Pattern PATTERN = Pattern.compile("r");
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }


    @GetMapping()
    public List<Person> returnContactList(@RequestParam(required = false) String name) {
        List<Person> persons = contactService.getContactList();

        if (name != null) {
            List<Person> filteredPersons = new ArrayList<Person>();
            Pattern compiled = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
            for (Person p : persons) {
                if (compiled.matcher(p.getName()).find()) {
                    filteredPersons.add(p);
                }
            }
            return filteredPersons;
        }
        return persons;
    }

    @GetMapping(path = "{id}")
    //fügt automatisch einen Schrägstrich hinzu, nicht schreiben, sonst wird es als absoluter Pfad behandelt
    public ResponseEntity<Person> returnPerson(@PathVariable(name = "id") int id) { //....liest den Wert aus, den der Client in diesem Teil der
        // URL geschickt hat, und übergibt ihn als Methoden-Parameter
        //Rückgabewert: Das ist ein Objekt, das zusammen einen Status PLUS den Inhalt zurückgibt.
        Person person = contactService.getContactMap().get(id);
        if (person == null) { //das ist, wenn die Person an dieser ID gelöscht wurde oder noch nicht eingefügt wurde, also leer ist.
            return ResponseEntity.notFound().build();
        } else return ResponseEntity.ok(person);
    }


    @PostMapping()
    //könnte man auch hier hinzufügen, muss man aber nicht unbedingt:
    // @PutMapping
    public void createNewContact(@RequestBody Person person) {
        contactService.addPersonToMap(person.getName(), person.getPhoneNumber());
    }

    //DELETE:
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable(name = "id") int id) { //Generic-Typ Void bedeutet, dass hier nie etwas drinstehen wird!
        boolean wasRemoved = contactService.removePersonFromMap(id);
        if (!wasRemoved) {
            return ResponseEntity.notFound().build();
        } else return ResponseEntity.noContent().build();
    }

    //Änderungen/Update
    @PutMapping(path = "{id}")
    public ResponseEntity<Person> updateContact(@PathVariable(name = "id") int id, @RequestBody Person person) {
        Person p = contactService.updatePerson(id, person.getName(), person.getPhoneNumber());
        if (p==null) {
            return ResponseEntity.notFound().build();
        } else return ResponseEntity.ok(p);
    }
}
