

# DocumentFromTemplateResponse


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
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



