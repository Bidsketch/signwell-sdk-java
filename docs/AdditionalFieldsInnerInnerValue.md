

# AdditionalFieldsInnerInnerValue

Varies according to the field type. Text fields accept strings or numbers. Date fields accept Iso8601 date strings. CheckBoxes accept booleans. Signature and Initials fields can't be signed through API requests. Autofill text fields accept strings or numbers.

## oneOf schemas
* [BigDecimal](BigDecimal.md)
* [Boolean](Boolean.md)
* [String](String.md)

## Example
```java
// Import classes:
import com.signwell.sdk.models.AdditionalFieldsInnerInnerValue;
import com.signwell.sdk.models.BigDecimal;
import com.signwell.sdk.models.Boolean;
import com.signwell.sdk.models.String;

public class Example {
    public static void main(String[] args) {
        AdditionalFieldsInnerInnerValue exampleAdditionalFieldsInnerInnerValue = new AdditionalFieldsInnerInnerValue();

        // create a new BigDecimal
        BigDecimal exampleBigDecimal = new BigDecimal();
        // set AdditionalFieldsInnerInnerValue to BigDecimal
        exampleAdditionalFieldsInnerInnerValue.setActualInstance(exampleBigDecimal);
        // to get back the BigDecimal set earlier
        BigDecimal testBigDecimal = (BigDecimal) exampleAdditionalFieldsInnerInnerValue.getActualInstance();

        // create a new Boolean
        Boolean exampleBoolean = new Boolean();
        // set AdditionalFieldsInnerInnerValue to Boolean
        exampleAdditionalFieldsInnerInnerValue.setActualInstance(exampleBoolean);
        // to get back the Boolean set earlier
        Boolean testBoolean = (Boolean) exampleAdditionalFieldsInnerInnerValue.getActualInstance();

        // create a new String
        String exampleString = new String();
        // set AdditionalFieldsInnerInnerValue to String
        exampleAdditionalFieldsInnerInnerValue.setActualInstance(exampleString);
        // to get back the String set earlier
        String testString = (String) exampleAdditionalFieldsInnerInnerValue.getActualInstance();
    }
}
```


