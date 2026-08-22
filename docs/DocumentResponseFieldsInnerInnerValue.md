

# DocumentResponseFieldsInnerInnerValue

## oneOf schemas
* [BigDecimal](BigDecimal.md)
* [Boolean](Boolean.md)
* [String](String.md)

NOTE: this class is nullable.

## Example
```java
// Import classes:
import com.signwell.sdk.models.DocumentResponseFieldsInnerInnerValue;
import com.signwell.sdk.models.BigDecimal;
import com.signwell.sdk.models.Boolean;
import com.signwell.sdk.models.String;

public class Example {
    public static void main(String[] args) {
        DocumentResponseFieldsInnerInnerValue exampleDocumentResponseFieldsInnerInnerValue = new DocumentResponseFieldsInnerInnerValue();

        // create a new BigDecimal
        BigDecimal exampleBigDecimal = new BigDecimal();
        // set DocumentResponseFieldsInnerInnerValue to BigDecimal
        exampleDocumentResponseFieldsInnerInnerValue.setActualInstance(exampleBigDecimal);
        // to get back the BigDecimal set earlier
        BigDecimal testBigDecimal = (BigDecimal) exampleDocumentResponseFieldsInnerInnerValue.getActualInstance();

        // create a new Boolean
        Boolean exampleBoolean = new Boolean();
        // set DocumentResponseFieldsInnerInnerValue to Boolean
        exampleDocumentResponseFieldsInnerInnerValue.setActualInstance(exampleBoolean);
        // to get back the Boolean set earlier
        Boolean testBoolean = (Boolean) exampleDocumentResponseFieldsInnerInnerValue.getActualInstance();

        // create a new String
        String exampleString = new String();
        // set DocumentResponseFieldsInnerInnerValue to String
        exampleDocumentResponseFieldsInnerInnerValue.setActualInstance(exampleString);
        // to get back the String set earlier
        String testString = (String) exampleDocumentResponseFieldsInnerInnerValue.getActualInstance();
    }
}
```


