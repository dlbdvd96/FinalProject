package com.finalproject;

import com.finalproject.entities.EventEntity;
import com.finalproject.entities.UserEntity;
import com.finalproject.repo.EventRepo;
import com.finalproject.repo.UserRepo;
import com.finalproject.services.EventService;
import com.finalproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EndPoints {

    @Autowired UserService userService;
    @Autowired EventService eventService;
    @Autowired UserRepo userRepo;
    @Autowired EventRepo eventRepo;

    @PostMapping("/user")
    public String signUpUser(@RequestBody UserEntity userEntity){
        return "Registra un utente alla piattaforma";
    }

    @GetMapping("/login")
    public String logIn(@RequestParam(name="username") String username, @RequestParam(name="password") String password){
        return "Verifica la corrispondenza di email e password e restituisce l'utente corrispondente";
    }

    @GetMapping("/events")
    public List<EventService> getActiveEvents(@CookieValue("username") String usernname){
        //Restituisce gli eventi disponibili a cui l'utente può registrarsi
        return null;
    }

    @PostMapping("/join/{eventid}")
    public String joinEvent(@PathVariable("eventid") String eventId, @CookieValue("username") String usernname){
        return "Registra l'utente ad un evento";
    }

    @PostMapping("/unjoin/{eventid}")
    public String unjoinEvent(@PathVariable("eventid") String eventId, @CookieValue("username") String usernname){
        return "Annulla la registrazione dell'utente ad un evento";
    }

    @PostMapping("/event")
    public String createEvent(@RequestBody EventEntity eventEntity, @CookieValue("username") String usernname){
        return "Crea un evento sulla piattaforma";
    }

    @GetMapping("/event/{eventid}")
    public String getEventDetails(@PathVariable("eventid") String eventId, @CookieValue("username") String usernname){
        return "Restituisce le informazioni dettagliate di un evento.";
    }

    @DeleteMapping("/event/{eventid}")
    public String cancelEvent(@PathVariable("eventid") String eventId, @CookieValue("username") String usernname){
        return "Permette solo al creatore di un evento di annullarlo.";
    }

    @GetMapping("/user/events")
    public List<EventService> getUserEvents(@RequestBody EventEntity eventEntity, @CookieValue("username") String usernname){
        //Restituisce una lista con gli eventi creati dall'utente e quelli a cui ha partecipato. Solo gli eventi futuri, non quelli passati
        return null;
    }
}
