package com.finalproject;

import com.finalproject.exceptions.ImpossibileToCreateEventException;
import com.finalproject.exceptions.ImpossibleToJoinEventException;
import com.finalproject.services.EventService;
import com.finalproject.services.UserService;
import com.finalproject.views.EventView;
import com.finalproject.views.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
public class EndPoints {

    @Autowired UserService userService;
    @Autowired EventService eventService;

    @PostMapping("/user")
    public ResponseEntity<UserView> signUpUser(@RequestBody UserView userView, HttpServletResponse response){
        Cookie cookie = new Cookie("username", userView.getUsername());
        UserView userViewSign = userService.signin(userView, cookie);
        if(userViewSign != null){
            response.addCookie(cookie);
            return new ResponseEntity<>(userViewSign, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/login")
    public ResponseEntity<UserView> logIn(@RequestParam(name="username") String username, @RequestParam(name="password") String password, HttpServletResponse response){
        Cookie cookie = new Cookie("username", username);
        UserView userView = userService.login(username, password, cookie);
        if(userView != null){
            response.addCookie(cookie);
            return new ResponseEntity<>(userView, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

    }

    @GetMapping("/events")
    public ResponseEntity<List<EventView>> getActiveEvents(@CookieValue("username") String username){
        return new ResponseEntity<>(eventService.getActiveEvents(username), HttpStatus.OK);
    }

    @PostMapping("/join/{eventid}")
    public ResponseEntity<EventView> joinEvent(@PathVariable("eventid") UUID eventId, @CookieValue("username") String username){
        try {
            return new ResponseEntity<>(eventService.joinEvent(eventId, username), HttpStatus.CREATED);
        }catch (ImpossibleToJoinEventException e){
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/unjoin/{eventid}")
    public ResponseEntity<EventView> unjoinEvent(@PathVariable("eventid") UUID eventId, @CookieValue("username") String username){
        EventView eventView = eventService.unjoinEvent(eventId, username);
        if(eventView!=null){
            return new ResponseEntity<>(eventView, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @PostMapping("/event")
    public ResponseEntity<EventView> createEvent(@RequestBody EventView eventView, @CookieValue("username") String username) {
        try {
            return new ResponseEntity<>(eventService.createEvent(eventView, username), HttpStatus.CREATED);
        } catch (ImpossibileToCreateEventException | ImpossibleToJoinEventException e) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/event/{eventid}")
    public ResponseEntity<EventView> getEventDetails(@PathVariable("eventid") UUID eventId, @CookieValue("username") String username){
        EventView eventView = eventService.getEventDetails(eventId, username);
        if(eventView!=null){
            return new ResponseEntity<>(eventView, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/event/{eventid}")
    public ResponseEntity<EventView> cancelEvent(@PathVariable("eventid") UUID eventId, @CookieValue("username") String username){
        System.out.println(eventId);
        EventView eventView = eventService.cancelEvent(eventId, username);
        if(eventView!=null){
            return new ResponseEntity<>(eventView, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/user/events")
    public ResponseEntity<List<EventView>> getUserEvents(@CookieValue("username") String username){
        return new ResponseEntity<>(eventService.getUserEvents(username), HttpStatus.OK);
    }
}
