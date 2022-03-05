package tests;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;
public class TestsExamples {
	Random rand = new Random();
	int randomNum = rand.nextInt((10 - 1) + 1) + 1;
	int randomPostid=rand.nextInt((100 - 1) + 1) + 1;
	@Test
	public void TestPrintRandomUserEmail() {


		System.out.println(randomNum);
		baseURI="https://jsonplaceholder.typicode.com";
		Response response= get("/users/"+randomNum);

		System.out.println(response.jsonPath().get("email"));
		int statusCode=response.getStatusCode();
		Assert.assertEquals(statusCode, 200);

	}


	@Test
	public void TestRandomUserPostsIDs() {
		baseURI="https://jsonplaceholder.typicode.com";

		System.out.println("https://jsonplaceholder.typicode.com"+"/users/"+randomNum+"/posts");
		given().get("/users/"+randomNum+"/posts").then().statusCode(200);
		//		.body("id",contains(arrayContaining(greaterThan(0) ,lessThan(101))));
		List<Integer> ids = given().get("/users/"+randomNum+"/posts")
				.then().extract().path("id");
		for(int id: ids) {
			System.out.println(id);
			Assert.assertTrue(101>id&&id>0);
		}
	}

	@Test
	public void TestPost() {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "foo");
		map.put("body", "bar");
		map.put("userId",randomNum );
		JSONObject request = new JSONObject(map);

		/*
		request.put("title", "foo");
		request.put("body", "bar");
		request.put("userId",randomNum );
		 */

		baseURI="https://jsonplaceholder.typicode.com";

		given().body(request.toJSONString()).when().post("/posts").then().statusCode(201)
		.body("id", equalTo(101));
	}

}
