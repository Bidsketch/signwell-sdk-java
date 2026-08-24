

# ValidationErrorValue

## oneOf schemas
* [List<String>](List<String>.md)
* [Map<String, ValidationErrorValue>](Map<String, ValidationErrorValue>.md)
* [String](String.md)

## Example
```java
// Import classes:
import com.signwell.sdk.models.ValidationErrorValue;
import com.signwell.sdk.models.List<String>;
import com.signwell.sdk.models.Map<String, ValidationErrorValue>;
import com.signwell.sdk.models.String;

public class Example {
    public static void main(String[] args) {
        ValidationErrorValue exampleValidationErrorValue = new ValidationErrorValue();

        // create a new List<String>
        List<String> exampleList<String> = new List<String>();
        // set ValidationErrorValue to List<String>
        exampleValidationErrorValue.setActualInstance(exampleList<String>);
        // to get back the List<String> set earlier
        List<String> testList<String> = (List<String>) exampleValidationErrorValue.getActualInstance();

        // create a new Map<String, ValidationErrorValue>
        Map<String, ValidationErrorValue> exampleMap<String, ValidationErrorValue> = new Map<String, ValidationErrorValue>();
        // set ValidationErrorValue to Map<String, ValidationErrorValue>
        exampleValidationErrorValue.setActualInstance(exampleMap<String, ValidationErrorValue>);
        // to get back the Map<String, ValidationErrorValue> set earlier
        Map<String, ValidationErrorValue> testMap<String, ValidationErrorValue> = (Map<String, ValidationErrorValue>) exampleValidationErrorValue.getActualInstance();

        // create a new String
        String exampleString = new String();
        // set ValidationErrorValue to String
        exampleValidationErrorValue.setActualInstance(exampleString);
        // to get back the String set earlier
        String testString = (String) exampleValidationErrorValue.getActualInstance();
    }
}
```


