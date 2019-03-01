package com.movieevent.movienight.service;

import com.google.api.client.googleapis.auth.oauth2.*;
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
    private static final String CLIENT_ID = "552100520661-javvlc9vbqenon4daiqam1d409pco69q.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "kgRVi8eUqDOd0wwgKpt19qg2";
    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserService userService;

    private Events events;
    private GoogleIdToken idToken;
    private boolean users1LogedIn = false;

    private DateTime now;
    private DateTime maxTime;
    List<String> usersLogedIn;
    boolean userLogedIn = false;


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

                    CLIENT_ID,

                    CLIENT_SECRET,

                    code,

                    "http://localhost:8080")

                    .execute();

        } catch (IOException e) {

            e.printStackTrace();

        }
        String accessToken = tokenResponse.getAccessToken();

        String refreshToken = tokenResponse.getRefreshToken();

        Long expiresAt = System.currentTimeMillis() + (tokenResponse.getExpiresInSeconds() * 1000);

        try {

            idToken = tokenResponse.parseIdToken();

        } catch (IOException e) {

            e.printStackTrace();
        }

        GoogleIdToken.Payload payload = idToken.getPayload();
        // Use THIS ID as a key to identify a google user-account.


        newauthorizedUser.setAccessToken(accessToken);
        newauthorizedUser.setRefreshToken(refreshToken);
        newauthorizedUser.setEmail(payload.getEmail());
        newauthorizedUser.setExpiresAt(expiresAt);


        userRepository.save(newauthorizedUser);

        return"user information saved successfully";
    }

    private String changeDateToString(DateTime dateTime) {
        String dateTimeString = dateTime.toString();
        String dateString = dateTimeString.substring(0, 10);
        String timeString = dateTimeString.substring(11, 16);
        String stringDateTime = dateString.concat(" " + timeString);
        return stringDateTime;
    }

    public List<User> loadUsers() throws ResourceNotFoundException {

        List<User> usersInDB = new ArrayList<>();
        usersInDB = userRepository.findAll();
        System.out.println(usersInDB.size());
        Events refreshEvents = null;
        if (usersInDB.size() == 0) {
            throw new ResourceNotFoundException("there are no users signed in to book an movie event");
        } else {


            for (User userInDB : usersInDB) {

                GoogleCredential credential;

                if (userInDB.getExpiresAt() < System.currentTimeMillis() ) {
                    System.out.println("New Refresh Tocken");
                    GoogleTokenResponse tokenResponse = null;
                    tokenResponse = getFreshGoogleResponse(userInDB.getRefreshToken());
                    credential = getRefreshedCredentials(tokenResponse);
                    userInDB.setAccessToken(tokenResponse.getAccessToken());
                    userInDB.setExpiresAt(System.currentTimeMillis() / 1000 + 3600);
                } else {
                    credential = new GoogleCredential().setAccessToken(userInDB.getAccessToken());
                }
                //userRepository.delete(userInDB);
                Calendar calendar = getCalendar(credential);

                try {

                    refreshEvents = calendar.events().list("primary")
                            .setMaxResults(50)
                            .setTimeMin(now)
                            .setTimeMax(maxTime)

                            .setOrderBy("startTime")

                            .setSingleEvents(true)

                            .execute();


                } catch (IOException e) {

                    e.printStackTrace();
                }

                List<Event> items = refreshEvents.getItems();

                if (items.isEmpty()) {

                    System.out.println("No upcoming events found.");

                } else {
                    userInDB.getUserEvents().clear();

                    for (Event event : items) {
                        DateTime start = event.getStart().getDateTime();
                        if (start == null) {
                            start = event.getStart().getDate();
                        }
                        DateTime end = event.getEnd().getDateTime();

                        if (end == null) {

                            end = event.getEnd().getDate();

                        }
                        UserEvent refreshUserEvent = new UserEvent();
                        refreshUserEvent.setStartEvent(changeDateToString(start));
                        refreshUserEvent.setName(event.getSummary());
                        refreshUserEvent.setEndEvent(changeDateToString(end));
                        //      System.out.printf("%s (%s) -> (%s)\n", event.getSummary(), start, end);
                        userInDB.addEvent(refreshUserEvent);

                    }

                }

                userService.addFreeTime(userInDB);
                userRepository.save(userInDB);
                System.out.println(userInDB.getEmail());

            }


        }
     List<User>users=new ArrayList<>();
        users=userRepository.findAll();
        for (User user:users ) {
            user.setAccessToken(null);
            user.setRefreshToken(null);
        }
        return users;
    }

    private GoogleTokenResponse getFreshGoogleResponse(String refreshCode) {
        try {
            GoogleTokenResponse response = new GoogleRefreshTokenRequest(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance(),
                    refreshCode,
                    CLIENT_ID,
                    CLIENT_SECRET)
                    .execute();

            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private GoogleCredential getRefreshedCredentials(GoogleTokenResponse response) {

        return new GoogleCredential().setAccessToken(response.getAccessToken());
    }


    private Calendar getCalendar(GoogleCredential credential) {
        return new Calendar.Builder(
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                credential)
                .setApplicationName("Movie Nights")
                .build();
    }


}
