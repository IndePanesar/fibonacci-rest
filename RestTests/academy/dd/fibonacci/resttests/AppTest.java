/*
* I.S.Panesar
* March 2018
* Junit tests to test the Fibonacci APIs
* Maven goal to run tests
*       mvn surefire:test
 */
package academy.dd.fibonacci.resttests;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;

import org.junit.*;

public class AppTest {
    private List<String> _fibonacciSeq;
    // Get the RequestSpecification of the request to send
    // to the server.
    private RequestSpecification _httpRequest;
    private ObjectMapper _objectMapper;

    private final int _httpOK = 200;
    private final int _port = 7003;

    @Before
    public void Initialize() {
        // Set the default Fibonacci sequence of 10 
        _fibonacciSeq = Arrays.asList("0", "1", "1", "2", "3", "5", "8", "13", "21", "34");
        _objectMapper = new ObjectMapper();

        // Set the base URL for RESTful web service
        RestAssured.baseURI = String.format("http://localhost:%d", _port);
        // Get the RequestSpecification of the request to send
        // to the server.
        _httpRequest = RestAssured.given();
    }

    @Test
    public void CorrectlyFetchTheFirstTenEntriesOfTheFibonacciSequenceTest() {
        try {
            // Get the response from the server specifying the method Type and the method URL.
            Response response = _httpRequest.request().get("/fib");

            // Check the response status code is 200.
            int statusCode = response.getStatusCode();

            // Assert that correct status code is returned.
            assertThat(statusCode).isEqualTo(_httpOK);

            // Verify the response is the expected sequence
            String responseBody = response.getBody().asString();

            // Map the response json to a List<String>
            List<String> fibList = _objectMapper.readValue(responseBody, new TypeReference<List<String>>() {
            });
            assertThat(fibList).isEqualTo(_fibonacciSeq);
        } catch (JsonParseException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (JsonMappingException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void CorrectlyGetTheFibonacciNumberAtIndex10Test()
    {
        try
        {
            int index = 10;
            long fibonacciExpected = 55L;
            long fibonacciActual = GetFibonacciAtIndex(index);
            assertThat(fibonacciActual).isEqualTo(fibonacciExpected);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void TheFibonacciNumberReturnedAtIndex29IsIncorrectTest()
    {
        try
        {
            int index = 29;
            long fibonacciExpected = 514229L;
            long fibonacciActual = GetFibonacciAtIndex(index);
            assertThat(fibonacciActual).as("Expected Fibonacci number at index 29 to be 514229 but was %d", fibonacciActual).isEqualTo(fibonacciExpected);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            fail(e.getMessage());
        }
        catch(AssertionError e)
        {
            assertThat(e).hasMessageContaining("Expected Fibonacci number at index 29 to be");
        }
    }

    @Test
    public void GetTheCorrectFibonacciSequenceInRange0To10Test()
    {
        int startIndex = 0, finishIndex = 10;
        try
        {
            // Get the response from the server specifying the method Type and the method URL.
            Response response = GetFibonacciForRange(startIndex, finishIndex);

            // Check the response status code is 200.
            int statusCode = response.getStatusCode();

            // Assert that correct status code is returned.
            assertThat(statusCode).isEqualTo(_httpOK);

            // Verify the response is the expected sequence within range
            VerifySequence(response.getBody().asString(), startIndex, finishIndex);
        }
        catch(AssertionError e)
        {
            String message = String.format("Expected Fibonacci number in range (%d, %d) at index", startIndex, finishIndex);
            assertThat(e).hasMessageContaining(message);
            fail(e.getMessage());
        }
    }

    @Test
    public void GetTheCorrectFibonacciSequenceInRange10To20Test()
    {
        int startIndex = 10, finishIndex = 20;
        try
        {
            // Get the response from the server specifying the method Type and the method URL.
            Response response = GetFibonacciForRange(startIndex, finishIndex);

            // Check the response status code is 200.
            int statusCode = response.getStatusCode();

            // Assert that correct status code is returned.
            assertThat(statusCode).isEqualTo(_httpOK);

            // Verify the response is the expected sequence within range
            VerifySequence(response.getBody().asString(), startIndex, finishIndex);
        }
        catch(AssertionError e)
        {
            String message = String.format("Expected Fibonacci number in range (%d, %d) at index", startIndex, finishIndex);
            assertThat(e).hasMessageContaining(message);
            fail(e.getMessage());
        }
    }

    @Test
    public void GetTheCorrectFibonacciSequenceInRange20To30Test()
    {
        int startIndex = 20, finishIndex = 30;
        try
        {
            // Get the response from the server specifying the method Type and the method URL.
            Response response = GetFibonacciForRange(startIndex, finishIndex);

            // Check the response status code is 200.
            int statusCode = response.getStatusCode();

            // Assert that correct status code is returned.
            assertThat(statusCode).isEqualTo(_httpOK);

            // Verify the response is the expected sequence within range
            VerifySequence(response.getBody().asString(), startIndex, finishIndex);
        }
        catch(AssertionError e)
        {
            String message = String.format("Expected Fibonacci number in range (%d, %d) at index", startIndex, finishIndex);
            assertThat(e).hasMessageContaining(message);
            fail(e.getMessage());
        }
    }

    // Function to get response from Fibonacci Range api
    private Response GetFibonacciForRange(int p_StartIndex, int p_FinishIndex)
    {
        String rangePath = "/fib/range/?startIndex=" + p_StartIndex + "&finishIndex=" + p_FinishIndex;
        Response response = _httpRequest.request().get(rangePath);
        return response;
    }


    // Function to get response from the Fibonacci Index api
    private Long GetFibonacciAtIndex(int p_Index) throws IOException
    {
        String indexPath = "/fib/" + p_Index;
        // Get the response from the server specifying the method Type and the method URL.
        Response response = _httpRequest.request().get(indexPath);

        // Check the response status code is 200.
        int statusCode = response.getStatusCode();

        // Assert that correct status code is returned.
        assertThat(statusCode).isEqualTo(_httpOK);

        // Verify the response is the expected sequence
        String responseBody = response.getBody().asString();

        // Map the response json to a List<String>
        return _objectMapper.readValue(responseBody, new TypeReference<Long>() {});
    }

    // Function to verify the Fibonacci sequence is correct
    private void VerifySequence(String p_Body, int p_StartIndex, int p_FinishIndex) throws AssertionError
    {
        try
        {
            // Map the response json to a List<String>
            List<Long> fibList = _objectMapper.readValue(p_Body, new TypeReference<List<Long>>() {});

            long nextExpected = 0l;

            for (int i = 2; i < fibList.size(); i++)
            {
                nextExpected = fibList.get(i - 2) + fibList.get(i - 1);
                assertThat(fibList.get(i)).as("Expected Fibonacci number in range (%d, %d) at index %d to be %d but was %d", p_StartIndex, p_FinishIndex, i + p_StartIndex, nextExpected, fibList.get(i))
                                          .isEqualTo(nextExpected);
            }
        }
        catch (JsonParseException e)
        {
            e.printStackTrace();
            fail(e.getMessage());
        }
        catch (JsonMappingException e)
        {
            e.printStackTrace();
            fail(e.getMessage());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}

