

# PasscodeDelivery

Passcode delivery settings.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**enabled** | **Boolean** | Enable or disable passcode delivery. |  [optional] |
|**methods** | [**List&lt;MethodsEnum&gt;**](#List&lt;MethodsEnum&gt;) | Delivery methods. Defaults to [\&quot;email\&quot;]. |  [optional] |
|**expireAfterAccess** | **Boolean** | Clear passcode after successful authentication. |  [optional] |



## Enum: List&lt;MethodsEnum&gt;

| Name | Value |
|---- | -----|
| EMAIL | &quot;email&quot; |
| UNKNOWN_DEFAULT_OPEN_API | &quot;unknown_default_open_api&quot; |



