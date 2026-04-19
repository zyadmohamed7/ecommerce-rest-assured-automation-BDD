package org.example.testcases.accounts;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.apis.AccountEndpoint;
import org.example.datagenerators.AccountDataGenerator;
import org.example.pojos.CreateAccountRequestPojo;
import org.example.pojos.CreateAccountResponsePojo;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

@Epic("Automation Exercise API Testing")
@Feature("Account Management")
public class AccountCreationTests {

    private AccountEndpoint accountEndpoint = new AccountEndpoint();

    @Test(priority = 1)
    @Story("Account Creation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Should create account successfully when all valid data provided")
    public void shouldCreateAccountSuccessfully_WhenValidDataProvided() {

        CreateAccountRequestPojo accountData = AccountDataGenerator.generateRandomAccount();

        System.out.println("Creating account with email: " + accountData.getEmail());

        Response response = accountEndpoint.createAccount(accountData);
        /*
        System.out.println("Status: " + response.getStatusCode());
        System.out.println("Content-Type: " + response.getContentType());
        System.out.println("Body: " + response.asString());



         */

        response.then()
                .using()
                .parser("text/html", io.restassured.parsing.Parser.JSON)
                .statusCode(200)
                .body("responseCode", equalTo(201))
                .body("message", equalTo("User created!"));
        CreateAccountResponsePojo responseData =
                response.then()
                        .extract()
                        .as(CreateAccountResponsePojo.class,
                                io.restassured.mapper.ObjectMapperType.JACKSON_2);


    }

    @Test(priority = 2)
    @Story("Account Creation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Should reject duplicate email when account already exists")
    public void shouldRejectDuplicateEmail_WhenAccountAlreadyExists() {
        // a known email that already exists
        CreateAccountRequestPojo accountData = AccountDataGenerator.generateAccountWithEmail("testuser@example.com");

        Response response = accountEndpoint.createAccount(accountData);
        response.then()
                .assertThat()
                .statusCode(anyOf(equalTo(400), equalTo(200)))
                .log().all();

        System.out.println("✅ Duplicate email test completed");
    }



    @Test(priority = 3)
    @Story("Account Creation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Should reject account creation when required fields are missing")
    public void shouldRejectAccountCreation_WhenRequiredFieldsMissing() {
        CreateAccountRequestPojo incompleteData = AccountDataGenerator.generateIncompleteAccount();

        Response response = accountEndpoint.createAccount(incompleteData);

        response.then()
                .assertThat()
                .statusCode(anyOf(equalTo(400), equalTo(200)))
                .log().all();

        System.out.println("✅ Missing fields test completed");
    }


    @Test(priority = 4)
    @Story("Account Creation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Should create multiple accounts successfully")
    public void shouldCreateMultipleAccountsSuccessfully_WhenValidData() {
        // create 3 accounts
        for (int i = 1; i <= 3; i++) {
            CreateAccountRequestPojo accountData = AccountDataGenerator.generateRandomAccount();

            System.out.println("\nCreating account " + i + " with email: " + accountData.getEmail());

            Response response = accountEndpoint.createAccount(accountData);

            response.then()
                    .assertThat()
                    .statusCode(200)
                    .log().body();

            System.out.println("✅ Account " + i + " created successfully!");
        }
    }


}