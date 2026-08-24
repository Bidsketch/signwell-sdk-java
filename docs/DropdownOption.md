

# DropdownOption

A dropdown option - either a simple string or a detailed object with name and optional api_id

## oneOf schemas
* [DetailedOption](DetailedOption.md)
* [String](String.md)

## Example
```java
// Import classes:
import com.signwell.sdk.models.DropdownOption;
import com.signwell.sdk.models.DetailedOption;
import com.signwell.sdk.models.String;

public class Example {
    public static void main(String[] args) {
        DropdownOption exampleDropdownOption = new DropdownOption();

        // create a new DetailedOption
        DetailedOption exampleDetailedOption = new DetailedOption();
        // set DropdownOption to DetailedOption
        exampleDropdownOption.setActualInstance(exampleDetailedOption);
        // to get back the DetailedOption set earlier
        DetailedOption testDetailedOption = (DetailedOption) exampleDropdownOption.getActualInstance();

        // create a new String
        String exampleString = new String();
        // set DropdownOption to String
        exampleDropdownOption.setActualInstance(exampleString);
        // to get back the String set earlier
        String testString = (String) exampleDropdownOption.getActualInstance();
    }
}
```


