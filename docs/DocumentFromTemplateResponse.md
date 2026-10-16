

# DocumentFromTemplateResponse


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**cfrPart11** | **Boolean** | Whether 21 CFR Part 11 controls apply to this document. Read-only: the value is taken from the workspace setting when the document is created and never changes afterwards. |  [optional] |
|**cfrPart11SigningMode** | [**CfrPart11SigningModeEnum**](#CfrPart11SigningModeEnum) | The signing mode of a 21 CFR Part 11 document: streamlined (one final authentication) or per_signature (authentication at every signature and initials field). Null when Part 11 does not apply. |  [optional] |
|**testMode** | **Boolean** |  |  |
|**id** | **UUID** |  |  |
|**templateId** | **UUID** |  |  [optional] |
|**templateIds** | **List&lt;UUID&gt;** |  |  [optional] |
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
|**recipients** | [**List&lt;DocumentFromTemplateResponseRecipientsInner&gt;**](DocumentFromTemplateResponseRecipientsInner.md) |  |  [optional] |
|**status** | **String** | Possible values: Draft, Created, Sending, Sent, Pending, Viewed, Completed, Manually completed, Declined, Canceled, Bounced, Blocked, Error, Expired |  [optional] |
|**reminders** | **Boolean** |  |  [optional] |
|**archived** | **Boolean** |  |  [optional] |
|**embedded** | [**EmbeddedBranding**](EmbeddedBranding.md) |  |  [optional] |
|**embeddedSigning** | **Boolean** |  |  [optional] |
|**embeddedEditUrl** | **String** |  |  [optional] |
|**embeddedPreviewUrl** | **String** |  |  [optional] |
|**applySigningOrder** | **Boolean** |  |  [optional] |
|**redirectUrl** | **String** |  |  [optional] |
|**declineRedirectUrl** | **String** |  |  [optional] |
|**language** | **String** |  |  [optional] |
|**expiresIn** | **Integer** |  |  [optional] |
|**declineMessage** | **String** |  |  [optional] |
|**errorMessage** | **String** |  |  [optional] |
|**embeddedSigningNotifications** | **Boolean** |  |  [optional] |
|**attachmentRequests** | [**List&lt;DocumentResponseAttachmentRequestsInner&gt;**](DocumentResponseAttachmentRequestsInner.md) |  |  [optional] |
|**files** | [**List&lt;FileInfo&gt;**](FileInfo.md) |  |  [optional] |
|**copiedContacts** | [**List&lt;CopiedContactInfo&gt;**](CopiedContactInfo.md) |  |  [optional] |
|**fields** | **List&lt;List&lt;DocumentFromTemplateResponseFieldsInnerInner&gt;&gt;** |  |  [optional] |
|**allowDecline** | **Boolean** |  |  [optional] |
|**allowReassign** | **Boolean** |  |  [optional] |
|**labels** | [**List&lt;LabelInfo&gt;**](LabelInfo.md) |  |  [optional] |
|**checkboxGroups** | [**List&lt;CheckboxGroupInfo&gt;**](CheckboxGroupInfo.md) |  |  [optional] |
|**conditionalRules** | [**List&lt;ConditionalRuleInfo&gt;**](ConditionalRuleInfo.md) |  |  [optional] |
|**conditionalLogicDecisions** | [**List&lt;ConditionalLogicDecisionInfo&gt;**](ConditionalLogicDecisionInfo.md) |  |  [optional] |



## Enum: CfrPart11SigningModeEnum

| Name | Value |
|---- | -----|
| STREAMLINED | &quot;streamlined&quot; |
| PER_SIGNATURE | &quot;per_signature&quot; |
| UNKNOWN_DEFAULT_OPEN_API | &quot;unknown_default_open_api&quot; |



