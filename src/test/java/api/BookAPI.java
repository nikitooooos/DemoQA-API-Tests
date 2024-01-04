package api;

import models.AddBookListModel;
import models.DeleteBooksModel;
import models.IsbnModel;
import models.LoginResponseModel;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static specs.BookSpecs.*;

public class BookAPI {

    public void deleteAllBooks(LoginResponseModel loginResponse) {
        given(bookRequestSpec)
                .contentType(JSON)
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .queryParam("UserId", loginResponse.getUserId())
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(bookResponseSpec);
    }

    public void addBook(LoginResponseModel loginResponse, AddBookListModel booksList) {
        IsbnModel isbn = new IsbnModel("9781449325862");
        List<IsbnModel> isbnList = new ArrayList<>();
        isbnList.add(isbn);

        booksList.setUserId(loginResponse.getUserId());
        booksList.setCollectionOfIsbns(isbnList);

        given(bookRequestSpec)
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .body(booksList)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(addBookResponseSpec);
    }

    public void deleteOneBook(LoginResponseModel loginResponse, String isbn) {

        DeleteBooksModel deleteBook = new DeleteBooksModel();
        deleteBook.setUserId(loginResponse.getUserId());
        deleteBook.setIsbn(isbn);

        given(bookRequestSpec)
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .body(deleteBook)
                .when()
                .delete("/BookStore/v1/Book")
                .then()
                .spec(bookResponseSpec);
    }
}