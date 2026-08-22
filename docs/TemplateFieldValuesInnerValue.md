

# TemplateFieldValuesInnerValue

TextField value must be a string or a number.

## oneOf schemas
* [BigDecimal](BigDecimal.md)
* [Boolean](Boolean.md)
* [String](String.md)

## Example
```java
// Import classes:
import com.signwell.sdk.models.TemplateFieldValuesInnerValue;
import com.signwell.sdk.models.BigDecimal;
import com.signwell.sdk.models.Boolean;
import com.signwell.sdk.models.String;

public class Example {
    public static void main(String[] args) {
        TemplateFieldValuesInnerValue exampleTemplateFieldValuesInnerValue = new TemplateFieldValuesInnerValue();

        // create a new BigDecimal
        BigDecimal exampleBigDecimal = new BigDecimal();
        // set TemplateFieldValuesInnerValue to BigDecimal
        exampleTemplateFieldValuesInnerValue.setActualInstance(exampleBigDecimal);
        // to get back the BigDecimal set earlier
        BigDecimal testBigDecimal = (BigDecimal) exampleTemplateFieldValuesInnerValue.getActualInstance();

        // create a new Boolean
        Boolean exampleBoolean = new Boolean();
        // set TemplateFieldValuesInnerValue to Boolean
        exampleTemplateFieldValuesInnerValue.setActualInstance(exampleBoolean);
        // to get back the Boolean set earlier
        Boolean testBoolean = (Boolean) exampleTemplateFieldValuesInnerValue.getActualInstance();

        // create a new String
        String exampleString = new String();
        // set TemplateFieldValuesInnerValue to String
        exampleTemplateFieldValuesInnerValue.setActualInstance(exampleString);
        // to get back the String set earlier
        String testString = (String) exampleTemplateFieldValuesInnerValue.getActualInstance();
    }
}
```


