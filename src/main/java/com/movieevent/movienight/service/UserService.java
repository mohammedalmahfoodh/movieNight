package com.movieevent.movienight.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.movieevent.movienight.Repository.UserRepository;
import com.movieevent.movienight.entity.User;
import com.movieevent.movienight.entity.UserEvent;
import com.movieevent.movienight.utilities.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    SaveGoogleAuthService saveGoogleAuthService;

    private UserEvent userMovieEvent;
    private String startMovieEvent = null;

    public UserService() {
        userMovieEvent = new UserEvent();
    }

    public String saveUser(User user) throws ResourceNotFoundException {

        if (user.getEmail() == null) {
            throw new ResourceNotFoundException("No Valid user to save");
        } else {
            User searchedUser = userRepository.findByEmail(user.getEmail());
            if (searchedUser == null) {
                userRepository.save(user);
            }
        }
        return "user saved successfully";
    }

    public User getUserByEmail(String email) throws ResourceNotFoundException {
        User searchedUser = userRepository.findByEmail(email);
        if (searchedUser == null)
            throw new ResourceNotFoundException("No such user in database");

        return searchedUser;
    }

    public boolean validateBookingEventTime(String timeToCheack) {


        return false;
    }

    public Map<String, String> cheackStartBookingValidity(String timeDate) {
        String eventTime = timeDate.replaceAll("t", " ");
        HashMap<String, String> eventValidity = new HashMap<>();
        for (User userLoggedIn : allUsersLoggedIn()) {
            for (UserEvent userEvent : userLoggedIn.getUserEvents()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime startTimeEvent = LocalDateTime.parse(userEvent.getStartEvent(), formatter);
                LocalDateTime endTimeEvent = LocalDateTime.parse(userEvent.getEndEvent(), formatter);
                LocalDateTime newTimeEvent = LocalDateTime.parse(eventTime, formatter);

                if (newTimeEvent.isAfter(startTimeEvent) && newTimeEvent.isBefore(endTimeEvent)) {

                    eventValidity.put("timevalidity", " not valid time, it will be during an existing event " + userEvent.getName());
                    return eventValidity;
                }
            }
        }

        userMovieEvent.setStartEvent(eventTime);
        eventValidity.put("timevalidity", "valid");

        return eventValidity;

    }

    public Map<String, String> cheackEndtBookingValidity(String timeDate) {

        String eventTime = timeDate.replaceAll("t", " ");
        HashMap<String, String> eventValidity = new HashMap<>();
        for (User userLoggedIn : allUsersLoggedIn()) {
            for (UserEvent userEvent : userLoggedIn.getUserEvents()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime startTimeEvent = LocalDateTime.parse(userEvent.getStartEvent(), formatter);
                LocalDateTime endTimeEvent = LocalDateTime.parse(userEvent.getEndEvent(), formatter);
                LocalDateTime movieStatrtEvent = LocalDateTime.parse(userMovieEvent.getStartEvent(), formatter);
                LocalDateTime newTimeEvent = LocalDateTime.parse(eventTime, formatter);


                if (newTimeEvent.isBefore(movieStatrtEvent)) {
                    eventValidity.put("timevalidity", " not valid time end event Time must be after start event time");
                    return eventValidity;
                }
                if (newTimeEvent.isBefore(movieStatrtEvent.plusHours(2))) {
                    eventValidity.put("timevalidity", " not valid time duration minimum duration of event is 2 hours");
                    return eventValidity;
                }

                if (newTimeEvent.isAfter(startTimeEvent) && newTimeEvent.isBefore(endTimeEvent)) {
                    // System.out.println(newTimeEvent);
                    eventValidity.put("timevalidity", " not valid time, it will be during an existing event  " + userEvent.getName());
                    return eventValidity;
                }
            }
        }

        userMovieEvent.setEndEvent(eventTime);
        userMovieEvent.setName("movie event");

        eventValidity.put("timevalidity", "valid");
        creatGoogleEvent(userMovieEvent);
        return eventValidity;
    }

    public Map<String, String> createMovieEvent() {
        HashMap<String, String> eventCreated = new HashMap<>();
        for (User userLoggedIn : allUsersLoggedIn()) {

            User userInMongoDB = getUserByEmail(userLoggedIn.getEmail());
            //   userInMongoDB.addEvent(userMovieEvent);
            //    userRepository.delete(userLoggedIn);
            //   userRepository.save(userInMongoDB);
         /*   System.out.println("####################################");
            System.out.println(userMovieEvent.getStartEvent());
            System.out.println(userMovieEvent.getEndEvent());
            System.out.println(userMovieEvent.getName());*/
        }
        eventCreated.put("createdState", "ok");
        return eventCreated;
    }


    private List<User> allUsersLoggedIn() {
        List<User> usersLoggedIn = new ArrayList<>();
        for (String email : saveGoogleAuthService.getUsersLogedIn()) {
            usersLoggedIn.add(userRepository.findByEmail(email));
        }

        return usersLoggedIn;
    }

    public void addFreeTime(User user) {

        List<UserEvent> events = user.getUserEvents();
        int nextEvent = 1;
        int userEventsIndex = user.getUserEvents().size();
        if (events.isEmpty()) {
            String allTimeFree = "The user has no events , he is free all the time ";
            user.addFreetime(allTimeFree);
        }
        for (int i = 0; i < userEventsIndex; i++) {
            String freeDateEvent = events.get(i).getEndEvent();
            if (i == 0) {
                LocalDateTime nowDate = LocalDateTime.now();
                String nowDateString = nowDate.toString();
                nowDateString = nowDateString.replaceAll("T", " ");
                nowDateString = nowDateString.substring(0, 16);
                String startNextEvent = events.get(i).getStartEvent();
                String newfreeDateEvent = nowDateString.concat("   to   " + startNextEvent);
                user.addFreetime(newfreeDateEvent);
            }
            if (nextEvent == userEventsIndex) {
                freeDateEvent = freeDateEvent.concat(" free date no upcoming event ");
                user.addFreetime(freeDateEvent);
            } else {

                String freeEndDateEvent = events.get(nextEvent).getStartEvent();
                System.out.println(freeEndDateEvent);
                String newfreeDateEvent = freeDateEvent.concat("   to   " + freeEndDateEvent);
                user.addFreetime(newfreeDateEvent);
            }
            nextEvent += 1;
        }
    }

    public void deleteByEmail(String email) {
        userRepository.delete(userRepository.findByEmail(email));
    }

    //########## Create Google Event #####################3
    public void creatGoogleEvent(UserEvent userEvent) {

        String startEvent2 = userEvent.getStartEvent();
        String startEvent = startEvent2.replaceAll("\\s", "T");
        String endEvent2 = userEvent.getEndEvent();
        String endEvent = endEvent2.replaceAll("\\s", "T");

        Event event = new Event()
                .setSummary("Movie Night")
                .setLocation("Malmö")
                .setDescription("movie night event");

        DateTime startDateTime = new DateTime(startEvent + ":00+01:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Europe/Stockholm");

        event.setStart(start);

        DateTime endDateTime = new DateTime(endEvent + ":00+01:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Europe/Stockholm");

        event.setEnd(end);

        String[] recurrence = new String[]{"RRULE:FREQ=DAILY;COUNT=1"};
        event.setRecurrence(Arrays.asList(recurrence));

        EventAttendee[] attendees = new EventAttendee[]{
                new EventAttendee().setEmail("mohammedalmahfoodh@gmail.com")

        };
        event.setAttendees(Arrays.asList(attendees));

        EventReminder[] reminderOverrides = new EventReminder[]{
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        String calendarId = "primary";

        //#### insert the event for all users logged in

        for (String email : saveGoogleAuthService.getUsersLogedIn()) {

            User userToInsertNewEvent = userRepository.findByEmail(email);

            GoogleCredential credential = new GoogleCredential().setAccessToken(userToInsertNewEvent.getAccessToken());

            com.google.api.services.calendar.Calendar calendar =

                    new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)

                            .setApplicationName("Movie Nights")

                            .build();

            try {
                event = calendar.events().insert(calendarId, event).execute();

                userEvent.setEventId(event.getId());
                userToInsertNewEvent.addEvent(userEvent);
                userRepository.save(userToInsertNewEvent);

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.printf("Event created: %s\n", event.getHtmlLink());

        }


    }

    public Map<String, String> deleteGoogleEvent() {
        HashMap<String, String> deleteEvent = new HashMap<>();
        for (String email : saveGoogleAuthService.getUsersLogedIn()) {
            User user;

            user = userRepository.findByEmail(email);
            User userToDeleteEvent = userRepository.findByEmail(email);
            userRepository.delete(user);
            GoogleCredential credential = new GoogleCredential().setAccessToken(userToDeleteEvent.getAccessToken());

            com.google.api.services.calendar.Calendar calendar =

                    new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)

                            .setApplicationName("Movie Nights")

                            .build();

            try {
                calendar.events().delete("primary", userMovieEvent.getEventId()).execute();

            } catch (IOException e) {
                e.printStackTrace();
            }

            userToDeleteEvent.getUserEvents().removeIf(userEvent -> userEvent.getStartEvent().contains(userMovieEvent.getStartEvent()));
            userRepository.save(userToDeleteEvent);

        }

        deleteEvent.put("message", "The event has been cancelled successfully");

        return deleteEvent;
    }

}

