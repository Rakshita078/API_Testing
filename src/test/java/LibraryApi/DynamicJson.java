package LibraryApi;


import Utilities.Payload;
import Utilities.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class DynamicJson {



    @Test(dataProvider = "BooksData", dataProviderClass = DataProviders.class)
    public void addBook(String isbn, String aisle){
        RestAssured.baseURI = "http://216.10.245.166";
      String response =   given().log().all().header("Content-Type","application/json").body(Payload.AddBook(isbn,aisle))
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();
      JsonPath js = ReusableMethods.rawToJson(response);
      String id = js.get("ID");
        System.out.println(id);

        //Delete Book
        given().log().all().header("Content-Type","application/json").body(Payload.deleteBook(id)).
                when().post("/Library/DeleteBook.php")
                .then().log().all().assertThat().statusCode(200);
       Assert.assertEquals("book is successfully deleted","book is successfully deleted");


    }
}
