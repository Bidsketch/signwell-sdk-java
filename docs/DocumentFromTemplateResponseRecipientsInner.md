

# DocumentFromTemplateResponseRecipientsInner


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** |  |  [optional] |
|**name** | **String** |  |  [optional] |
|**email** | **String** |  |  |
|**role** | **String** |  |  [optional] |
|**message** | **String** |  |  [optional] |
|**subject** | **String** |  |  [optional] |
|**sendEmail** | **Boolean** |  |  [optional] |
|**sendEmailDelay** | **Integer** |  |  [optional] |
|**deliveryMethod** | [**DeliveryMethodEnum**](#DeliveryMethodEnum) | How the recipient is notified: email (default), sms, or email_and_sms. |  [optional] |
|**sms** | [**DocumentResponseRecipientsInnerSms**](DocumentResponseRecipientsInnerSms.md) |  |  [optional] |
|**signingOrder** | **Integer** |  |  [optional] |
|**signingUrl** | **String** |  |  [optional] |
|**embeddedSigningUrl** | **String** |  |  [optional] |
|**bounced** | **Boolean** |  |  [optional] |
|**bouncedDetails** | **String** |  |  [optional] |
|**attachmentRequests** | [**List&lt;AttachmentRequestInfo&gt;**](AttachmentRequestInfo.md) |  |  [optional] |
|**passcode** | **String** |  |  [optional] |
|**passcodeDelivery** | [**DocumentResponseRecipientsInnerPasscodeDelivery**](DocumentResponseRecipientsInnerPasscodeDelivery.md) |  |  [optional] |
|**status** | **String** |  |  [optional] |
|**placeholderName** | **String** |  |  [optional] |



## Enum: DeliveryMethodEnum

| Name | Value |
|---- | -----|
| EMAIL | &quot;email&quot; |
| SMS | &quot;sms&quot; |
| EMAIL_AND_SMS | &quot;email_and_sms&quot; |
| UNKNOWN_DEFAULT_OPEN_API | &quot;unknown_default_open_api&quot; |



