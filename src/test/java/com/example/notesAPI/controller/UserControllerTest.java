package com.example.notesAPI.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.restassured.http.Header;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

class UserControllerTest {

    //test naming convention "method_scenario_expected"

    private static String authToken;

    @BeforeAll
    static void setup() {
        //specify base uri
        baseURI = "http://localhost:8080/user";

        //get jwt token to use in all requests
        authToken =
            given() //prerequisites
            .when()// action to be performed
                .contentType("application/json")
                .body("""
                        {
                            "email":"sampleemail@gmail.com",
                            "userPassword":"qwertyisfun"
                        }""")
                .post("/login")
            .then() //what needs to be asserted/validated/tested for correctness
                .extract().body().asString();
    }

    /////////////////////////
    /// CREATE USER TESTS ///
    /////////////////////////

    @Test
    void createUser_UserDoesntExists_UserIsCreated(){
        given()
                .when()
                .contentType("application/json")
                .body("""
                        {
                            "username":"klopez2",
                            "email":"sampleemail2@gmail.com",
                            "userPassword":"qwertyisfun2"
                        }
                        """)
                .post("/createUser")
                .then()
                .assertThat()
                .statusCode(200);

    }

    @Test
    void createUser_UserDoesExists_ConflictResponse(){
        given()
                .when()
                .contentType("application/json")
                .body("""
                        {
                            "username":"klopez",
                            "email":"sampleemail@gmail.com",
                            "userPassword":"qwertyisfun"
                        }
                        """)
                .post("/createUser")
                .then()
                .assertThat()
                .statusCode(409);//conflict
    }

    @Test
    void createUser_EmptyReqBody_BadReqResponse(){
        given()
                .when()
                .contentType("application/json")
                .body("")
                .post("/createUser")
                .then()
                .assertThat()
                .statusCode(400);//bad request
    }

    @Test
    void createUser_UsernameTooLong_BadReqResponse(){
        given()
                .when()
                .contentType("application/json")
                .body("""
                        {
                            "username":"klopezklopezklopezklopezkl(51 chars including this)",
                            "email":"sampleemail@gmail.com",
                            "userPassword":"qwertyisfun"
                        }
                        """)
                .post("/createUser")
                .then()
                .assertThat()
                .statusCode(400);//bad request
    }

    @Test
    void createUser_EmailTooLong_BadReqResponse(){
        given()
                .when()
                .contentType("application/json")
                .body("""
                        {
                            "username":"klopez",
                            "email":"sampleemail@gmail.comsampleemail@gmail.comsampleemail@gmail.comsampleemail@gmail.com
                            sampleemail@gmail.comsampleemail@gmail.comsampleemail@gmail.comsampleemail@gmai
                            (this is 255 chars including this)",
                            "userPassword":"qwertyisfun"
                        }
                        """)
                .post("/createUser")
                .then()
                .assertThat()
                .statusCode(400);//bad request
    }





    @Test
    void getUser_ProperBody_ReturnsSuccessMsg(){
        Header authHeader = new Header("Authorization", "Bearer "+authToken);

        given()
        .when()
            .contentType("application/json")
            .header(authHeader)
                .body("""
                        {
                            "email":"sampleemail@gmail.com"
                        }
                        """)
                .get("/getUser")
        .then()
                .assertThat()
                .statusCode(200)
                .body("response", equalTo(true),
                        "message", equalTo("User Found"),
                        "data.username", equalTo("klopez"),
                        "data.email", equalTo("sampleemail@gmail.com"),
                        "data.password",equalTo(null)
                );
    }


    @Test
    void getUser() {
    }

    @Test
    void updateEmail() {
    }

    @Test
    void updateUsername() {
    }

    @Test
    void updatePassword() {
    }
}