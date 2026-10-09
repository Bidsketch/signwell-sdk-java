

# PlaceholdersInnerPasscodeDelivery

Passcode delivery (OTP) settings for this placeholder. When enabled, documents created from this template inherit these settings for the corresponding recipient. While 21 CFR Part 11 is enabled for the workspace, a placeholder cannot disable passcode delivery: it returns a 422.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**enabled** | **Boolean** | Enable passcode delivery for this recipient. Cannot be set to &#x60;false&#x60; on a 21 CFR Part 11 document: it returns a 422. |  [optional] |
|**methods** | [**List&lt;MethodsEnum&gt;**](#List&lt;MethodsEnum&gt;) | Delivery methods for the passcode. Currently supports: email. Defaults to [\&quot;email\&quot;] when omitted. |  [optional] |
|**expireAfterAccess** | **Boolean** | Whether the passcode expires after the recipient accesses the document. Inherited by documents created from this template. |  [optional] |



## Enum: List&lt;MethodsEnum&gt;

| Name | Value |
|---- | -----|
| EMAIL | &quot;email&quot; |
| UNKNOWN_DEFAULT_OPEN_API | &quot;unknown_default_open_api&quot; |



