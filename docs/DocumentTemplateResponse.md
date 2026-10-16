

# DocumentTemplateResponse


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **UUID** |  |  |
|**apiApplicationId** | **UUID** |  |  [optional] |
|**requesterEmailAddress** | **String** |  |  [optional] |
|**customRequesterName** | **String** |  |  [optional] |
|**customRequesterEmail** | **String** |  |  [optional] |
|**name** | **String** |  |  [optional] |
|**subject** | **String** |  |  [optional] |
|**message** | **String** |  |  [optional] |
|**metadata** | **Map&lt;String, String&gt;** |  |  [optional] |
|**createdAt** | **OffsetDateTime** |  |  [optional] |
|**updatedAt** | **OffsetDateTime** |  |  [optional] |
|**placeholders** | [**List&lt;DocumentTemplateResponsePlaceholdersInner&gt;**](DocumentTemplateResponsePlaceholdersInner.md) |  |  [optional] |
|**copiedPlaceholders** | [**List&lt;DocumentTemplateResponseCopiedPlaceholdersInner&gt;**](DocumentTemplateResponseCopiedPlaceholdersInner.md) |  |  [optional] |
|**status** | **String** |  |  [optional] |
|**reminders** | **Boolean** |  |  [optional] |
|**archived** | **Boolean** |  |  [optional] |
|**embeddedEditUrl** | **String** |  |  [optional] |
|**templateLink** | **String** |  |  [optional] |
|**templateId** | **UUID** |  |  [optional] |
|**applySigningOrder** | **Boolean** |  |  [optional] |
|**redirectUrl** | **String** |  |  [optional] |
|**declineRedirectUrl** | **String** |  |  [optional] |
|**language** | **String** |  |  [optional] |
|**expiresIn** | **Integer** |  |  [optional] |
|**files** | [**List&lt;FileInfo&gt;**](FileInfo.md) |  |  [optional] |
|**fields** | **List&lt;List&lt;DocumentResponseFieldsInnerInner&gt;&gt;** |  |  [optional] |
|**allowDecline** | **Boolean** |  |  [optional] |
|**allowReassign** | **Boolean** |  |  [optional] |
|**labels** | [**List&lt;LabelInfo&gt;**](LabelInfo.md) |  |  [optional] |
|**checkboxGroups** | [**List&lt;CheckboxGroupInfo&gt;**](CheckboxGroupInfo.md) |  |  [optional] |
|**conditionalRules** | [**List&lt;ConditionalRuleInfo&gt;**](ConditionalRuleInfo.md) |  |  [optional] |



