package com.movie.googlecalendar.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class SaveGoogleAuthCode {


    @RequestMapping(value = "/storeauthcode", method = RequestMethod.POST)

    public String storeauthcode(@RequestBody String code, @RequestHeader("X-Requested-With") String encoding) {

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

                    "https://localhost:9000")

                    .execute();

        } catch (IOException e) {

            e.printStackTrace();

        }



        // Store these 3in your DB

        String accessToken = tokenResponse.getAccessToken();

        String refreshToken = tokenResponse.getRefreshToken();

        Long expiresAt = System.currentTimeMillis() + (tokenResponse.getExpiresInSeconds() * 1000);



        // Debug purpose only

        System.out.println("accessToken: " + accessToken);

        System.out.println("refreshToken: " + refreshToken);

        System.out.println("expiresAt: " + expiresAt);



        return "OK";

    }




}
