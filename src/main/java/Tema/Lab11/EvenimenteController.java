package Tema.Lab11;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/jpa/evenimente")
public class EvenimenteController {

    @Autowired
    private EvenimentRepository repository;

    @GetMapping
    public List<Eveniment> getAllEvenimente() {
        return repository.findAll();
    }

    @GetMapping("/locatie/{locatie}")
    public List<Eveniment> getEvenimenteByLocatie(@PathVariable String locatie) {
        return repository.findByLocatie(locatie);
    }

    @GetMapping("/data/{data}")
    public List<Eveniment> getEvenimenteByData(@PathVariable String data) {
        return repository.findByData(LocalDate.parse(data));
    }

    @PostMapping
    public Eveniment createEveniment(@RequestBody Eveniment eveniment) {
        return repository.save(eveniment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Eveniment> updateEveniment(@PathVariable Long id, @RequestBody Eveniment updatedEveniment) {
        return repository.findById(id)
                .map(eveniment -> {
                    eveniment.setDenumire(updatedEveniment.getDenumire());
                    eveniment.setLocatie(updatedEveniment.getLocatie());
                    eveniment.setData(updatedEveniment.getData());
                    eveniment.setTimp(updatedEveniment.getTimp());
                    eveniment.setPretBilet(updatedEveniment.getPretBilet());
                    return ResponseEntity.ok(repository.save(eveniment));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEveniment(@PathVariable Long id) {
        return repository.findById(id)
                .map(eveniment -> {
                    repository.delete(eveniment);
                    return ResponseEntity.ok().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
