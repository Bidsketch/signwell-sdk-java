

# CompletedPdfResponse

## oneOf schemas
* [CompletedPdfUrlResponse](CompletedPdfUrlResponse.md)
* [File](File.md)

## Example
```java
// Import classes:
import com.signwell.sdk.models.CompletedPdfResponse;
import com.signwell.sdk.models.CompletedPdfUrlResponse;
import com.signwell.sdk.models.File;

public class Example {
    public static void main(String[] args) {
        CompletedPdfResponse exampleCompletedPdfResponse = new CompletedPdfResponse();

        // create a new CompletedPdfUrlResponse
        CompletedPdfUrlResponse exampleCompletedPdfUrlResponse = new CompletedPdfUrlResponse();
        // set CompletedPdfResponse to CompletedPdfUrlResponse
        exampleCompletedPdfResponse.setActualInstance(exampleCompletedPdfUrlResponse);
        // to get back the CompletedPdfUrlResponse set earlier
        CompletedPdfUrlResponse testCompletedPdfUrlResponse = (CompletedPdfUrlResponse) exampleCompletedPdfResponse.getActualInstance();

        // create a new File
        File exampleFile = new File();
        // set CompletedPdfResponse to File
        exampleCompletedPdfResponse.setActualInstance(exampleFile);
        // to get back the File set earlier
        File testFile = (File) exampleCompletedPdfResponse.getActualInstance();
    }
}
```


