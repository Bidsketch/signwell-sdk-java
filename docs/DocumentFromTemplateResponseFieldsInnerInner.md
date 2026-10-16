

# DocumentFromTemplateResponseFieldsInnerInner


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**x** | **Float** |  |  |
|**y** | **Float** |  |  |
|**page** | **Integer** |  |  |
|**recipient** | [**DocumentFromTemplateResponseFieldsInnerInnerRecipient**](DocumentFromTemplateResponseFieldsInnerInnerRecipient.md) |  |  [optional] |
|**apiId** | **String** |  |  [optional] |
|**name** | **String** |  |  [optional] |
|**dateFormat** | **DateFormat** |  |  [optional] |
|**fixedWidth** | **Boolean** |  |  [optional] |
|**formula** | **String** |  |  [optional] |
|**label** | **String** |  |  [optional] |
|**lockSignDate** | **Boolean** |  |  [optional] |
|**required** | **Boolean** |  |  [optional] |
|**type** | **FieldType** |  |  [optional] |
|**validation** | **TextValidation** |  |  [optional] |
|**validationRegex** | **String** | Text fields with custom validation only: the regex pattern used to validate field values. Required when validation is set to custom. |  [optional] |
|**validationRegexErrorMessage** | **String** | Text fields with custom validation only: the error message shown to the recipient when their input doesn&#39;t match the regex pattern. Required when validation is set to custom. |  [optional] |
|**value** | [**DocumentResponseFieldsInnerInnerValue**](DocumentResponseFieldsInnerInnerValue.md) |  |  [optional] |
|**height** | **String** |  |  [optional] |
|**width** | **String** |  |  [optional] |
|**recipientId** | **String** |  |  [optional] |
|**signingElementsGroupId** | **UUID** |  |  [optional] |
|**placeholderName** | **String** |  |  [optional] |
|**options** | [**List&lt;DocumentResponseFieldsInnerInnerOptionsInner&gt;**](DocumentResponseFieldsInnerInnerOptionsInner.md) | Dropdown options (for dropdown/select fields) |  [optional] |
|**defaultOption** | **String** | Default selected option |  [optional] |
|**allowOther** | **Boolean** | Whether \&quot;Other\&quot; option is allowed |  [optional] |



