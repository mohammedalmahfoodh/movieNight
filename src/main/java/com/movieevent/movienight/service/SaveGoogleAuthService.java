package com.movieevent.movienight.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.movieevent.movienight.Repository.UserRepository;
import com.movieevent.movienight.entity.User;
import com.movieevent.movienight.entity.UserEvent;
import com.movieevent.movienight.utilities.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaveGoogleAuthService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserService userService;

    private Events events;
    private GoogleIdToken idToken;

    private DateTime now;
    private DateTime maxTime;
    List<String> usersLogedIn;

    public List<String> getUsersLogedIn() {
        return usersLogedIn;
    }

    public void setUsersLogedIn(List<String> usersLogedIn) {
        this.usersLogedIn = usersLogedIn;
    }

    public SaveGoogleAuthService() {
        usersLogedIn = new ArrayList<>();

        now = new DateTime(System.currentTimeMillis());
        maxTime = new DateTime("2019-06-30T16:30:00.000+05:30");
        events = null;
        idToken = null;
    }

    public String storeauthcode(String code, String encoding) {

        User newauthorizedUser = new User();

        if (encoding == null || encoding.isEmpty()) {

            // Without the `X-Requested-With` header, this request could be forged. Aborts.

            return "Error, wrong headers";

        }
        GoogleTokenResponse tokenResponse = null;

        try {

            tokenResponse = new GoogleAuthorizationCodeTokenRequest(

                    new NetHttpTransport(),

                    JacksonFactory.getDefaultInstance(),

                    "https://www.googleapis.com/oauth2/v4/token",

                    "552100520661-javvlc9vbqenon4daiqam1d409pco69q.apps.googleusercontent.com",

                    "kgRVi8eUqDOd0wwgKpt19qg2",

                    code,

                    "http://localhost:8080")

                    .execute();

        } catch (IOException e) {

            e.printStackTrace();

        }
        String accessToken = tokenResponse.getAccessToken();

        String refreshToken = tokenResponse.getRefreshToken();

        Long expiresAt = System.currentTimeMillis() + (tokenResponse.getExpiresInSeconds() * 1000);


        // Debug purpose only

        //   System.out.println("accessToken: " + accessToken);

        ///  System.out.println("refreshToken: " + refreshToken);

        //  System.out.println("expiresAt: " + expiresAt);


        GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);


        Calendar calendar =

                new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)

                        .setApplicationName("Movie Nights")

                        .build();

        try {

            events = calendar.events().list("primary")
                    .setMaxResults(50)
                    .setTimeMin(now)
                    .setTimeMax(maxTime)

                    .setOrderBy("startTime")

                    .setSingleEvents(true)

                    .execute();

        } catch (IOException e) {

            e.printStackTrace();

        }

        List<Event> items = events.getItems();

        if (items.isEmpty()) {

            System.out.println("No upcoming events found.");

        } else {

            try {

                idToken = tokenResponse.parseIdToken();

            } catch (IOException e) {

                e.printStackTrace();
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            // Use THIS ID as a key to identify a google user-account.
            String userId = payload.getSubject();

            newauthorizedUser.setAccessToken(accessToken);
            newauthorizedUser.setRefreshToken(refreshToken);
            newauthorizedUser.setEmail(payload.getEmail());


            System.out.println("Upcoming events");

            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();

                if (start == null) { // If it's an all-day-event - store the date instead

                    start = event.getStart().getDate();
                }
                DateTime end = event.getEnd().getDateTime();

                if (end == null) { // If it's an all-day-event - store the date instead

                    end = event.getEnd().getDate();

                }
                UserEvent userEvent = new UserEvent();
                userEvent.setStartEvent(changeDateToString(start));
                userEvent.setName(event.getSummary());
                userEvent.setEndEvent(changeDateToString(end));
                System.out.printf("%s (%s) -> (%s)\n", event.getSummary(), start, end);
                newauthorizedUser.addEvent(userEvent);
            }

        }
// Get profile info from ID token (Obtained at the last step of OAuth2)

        GoogleIdToken idToken = null;

        try {

            idToken = tokenResponse.parseIdToken();

        } catch (IOException e) {

            e.printStackTrace();

        }

        GoogleIdToken.Payload payload = idToken.getPayload();

        // Use THIS ID as a key to identify a google user-account.

        String userId = payload.getSubject();

        newauthorizedUser.setEmail(payload.getEmail());
        userService.addFreeTime(newauthorizedUser);
        User user = new User();
        user = userRepository.findByEmail(payload.getEmail());
        if (user != null) {
            userRepository.delete(user);

        }
        userService.saveUser(newauthorizedUser);
        if (usersLogedIn.size() == 0) {
            usersLogedIn.add(payload.getEmail());
        } else {                           //check if the user has logged in already
            if (!usersLogedIn.contains(payload.getEmail()))
                usersLogedIn.add(payload.getEmail());

        }


        return "user information saved successfully";
    }


    private String changeDateToString(DateTime dateTime) {
        String dateTimeString = dateTime.toString();
        String dateString = dateTimeString.substring(0, 10);
        String timeString = dateTimeString.substring(11, 16);

        String stringDateTime = dateString.concat(" " + timeString);

        return stringDateTime;

    }

    public List<User> loadUsers() throws ResourceNotFoundException {
        List<User> usersToReturn = new ArrayList<>();
        if (usersLogedIn.isEmpty()) {
            throw new ResourceNotFoundException("there are no users signed in to book an movie event");
        } else {

            for (String email : usersLogedIn) {
                User userInDB = userService.getUserByEmail(email);
                usersToReturn.add(userInDB);
            }

        }


        return usersToReturn;

    }


}
