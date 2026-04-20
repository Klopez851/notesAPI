package com.example.notesAPI.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.http.Header;

import static io.restassured.RestAssured.*;
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
    void createUser_IncorrectReqBody_BadReqResponse(){
        given()
                .when()
                .contentType("application/json")
                .body("""
                        {
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

    ////////////////////////
    /// USER LOGIN TESTS ///
    ////////////////////////

    @Test
    void login_ExistingUser_JWTReturned(){
        given()
                .when()
                .contentType("application/json")
                .body("""
                        {
                            "email":"sampleemail@gmail.com",
                            "userPassword":"qwertyisfun"
                        }""")
                .post("/login")
                .then() //what needs to be asserted/validated/tested for correctness
                .assertThat()
                .statusCode(200) //ok
                .body(not(emptyOrNullString()));
    }

    @Test
    void login_NonExistingUser_NotFoundResponse(){
        given()
                .when()
                .contentType("application/json")
                .body("""
                        {
                            "email":"sampleemail3@gmail.com",
                            "userPassword":"qwertyisfun3"
                        }""")
                .post("/login")
                .then()
                .assertThat().statusCode(404);//not found
    }

    @Test
    void login_EmptyReqBody_BadReqResponse(){
        given()
                .when()
                .contentType("application/json")
                .body("")
                .post("/login")
                .then()
                .assertThat().statusCode(400);
    }

    //////////////////////
    /// GET USER TESTS ///
    //////////////////////

    @Test
    void getUser_ExistingUser_SuccessResponse(){
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
    void getUser_NonExistingUser_NotFoundResponse(){
        Header authHeader = new Header("Authorization", "Bearer "+authToken);

        given()
                .when()
                .contentType("application/json")
                .header(authHeader)
                .body("""
                        {
                            "email":"sampleemail3@gmail.com"
                        }
                        """)
                .get("/getUser")
                .then()
                .assertThat()
                .statusCode(404); //not found
    }

    @Test
    void getUser_EmptyReqBody_BadReqResponse(){
        Header authHeader = new Header("Authorization", "Bearer "+authToken);

        given()
                .when()
                .contentType("application/json")
                .header(authHeader)
                .body("")
                .get("/getUser")
                .then()
                .assertThat()
                .statusCode(400);
    }

    //////////////////////////////
    /// UPDATE USER INFO TESTS ///
    //////////////////////////////

    @Test
    void updateEmail_ExisitingUser_SuccessResponse() {
        Header authHeader = new Header("Authorization", "Bearer "+authToken);

        given()
                .when()
                .contentType("application/json")
                .header(authHeader)
                .body("""
                        {
                            "oldEmail":"sampleemail@gmail.com",
                            "newEmail":"sampleemailtest@gmail.com"
                        }
                        """)
                .patch("/updateEmail")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    void updateEmail_EmptyReqBody_BadResponse() {
        Header authHeader = new Header("Authorization", "Bearer "+authToken);

        given()
                .when()
                .contentType("application/json")
                .header(authHeader)
                .body("")
                .patch("/updateEmail")
                .then()
                .assertThat()
                .statusCode(400);
    }
//
//    @Test
//    void updateUsername() {
//    }
//
//    @Test
//    void updatePassword() {
//    }


    //test reset
    @AfterAll
    static void restDB() {
        //RESET EMAIL FOR SAMPLE USER
        Header authHeaderEmailReset = new Header("Authorization", "Bearer "+authToken);

        given()
                .when()
                .contentType("application/json")
                .header(authHeaderEmailReset)
                .body("""
                        {
                            "oldEmail":"sampleemailtest@gmail.com",
                            "newEmail":"sampleemail@gmail.com"
                        }
                        """)
                .patch("/updateEmail");

        //DELETE NEWLY CREATED USER

        //generate a new token with the credentials of the user to be deleted
        String delToken =
                given() //prerequisites
                        .when()// action to be performed
                        .contentType("application/json")
                        .body("""
                        {
                            "email":"sampleemail2@gmail.com",
                            "userPassword":"qwertyisfun2"
                        }""")
                        .post("/login")
                        .then() //what needs to be asserted/validated/tested for correctness
                        .extract().body().asString();

        Header authHeaderDelUser = new Header("Authorization", "Bearer "+delToken);

        given()
                .when()
                .contentType("application/json")
                .header(authHeaderDelUser)
                .body("""
                        {
                            "email":"sampleemail2@gmail.com"
                        }
                        """)
                .delete("/deleteUser")
                .then()
                .assertThat()
                .statusCode(200);
    }
}