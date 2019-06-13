package com.lambdaschool.zoos.controller;

import com.lambdaschool.zoos.model.Animal;
import com.lambdaschool.zoos.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/animals")
public class AnimalController
{
    @Autowired
    private AnimalService animalService;

    @GetMapping(value = "/animals",
                produces = {"application/json"})
    public ResponseEntity<?> listAllAnimals()
    {
        return new ResponseEntity<>(animalService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{type}",
                produces = {"application/json"})
    public ResponseEntity<?> findAnimalByType(
            @PathVariable
                    String type)
    {
        Animal a = animalService.findAnimalByType(type);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    @GetMapping(value = "/count", produces = {"application/json"})
    public ResponseEntity<?> getNumZoosAnimalsIn()
    {
        return new ResponseEntity<>(animalService.getCountAnimalInZoo(), HttpStatus.OK);
    }
}
