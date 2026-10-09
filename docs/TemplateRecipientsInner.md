

# TemplateRecipientsInner


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | A unique identifier that you will give to each recipient. We recommend numbering sequentially from 1 to X. IDs are required for associating recipients to fields and more. |  |
|**name** | **String** | Name of the recipient. Required on 21 CFR Part 11 documents: a recipient without a name returns a 422. |  [optional] |
|**email** | **String** | Email address for the recipient. Required unless &#x60;delivery_method&#x60; is &#x60;sms&#x60;. |  [optional] |
|**placeholderName** | **String** | The name of the placeholder you want this recipient assigned to. |  [optional] |
|**passcode** | **String** | If set, signers assigned with a passcode will be required to enter the passcode before they’re able to view and complete the document. On a 21 CFR Part 11 document SignWell generates the passcode, so a sender-defined passcode returns a 422. |  [optional] |
|**passcodeDelivery** | [**RecipientsInnerPasscodeDelivery**](RecipientsInnerPasscodeDelivery.md) |  |  [optional] |
|**subject** | **String** | Email subject for the signature request that the recipient will see. Overrides the general subject for the template. |  [optional] |
|**message** | **String** | Email message for the signature request that the recipient will see. Overrides the general message for the template. |  [optional] |
|**sendEmail** | **Boolean** | Applies on when &#x60;embedded_signing&#x60; is &#x60;true&#x60;. By default, recipients are not notified through email to sign when doing embedded signing. Setting this to &#x60;true&#x60;  will send a notification email to the recipient. Default is &#x60;false&#x60;. |  [optional] |
|**sendEmailDelay** | **Integer** | If &#x60;send_email&#x60; is &#x60;true&#x60; recipients will receive a new document notification immediately. In the case of embedded signing, you can delay this notification to only send if the document is not completed within a few minutes. The email notification will not go out if the document is completed before the delay time is over. Valid values are in minutes ranging from &#x60;0&#x60; to &#x60;60&#x60;. Defaults to &#x60;0&#x60;. |  [optional] |
|**deliveryMethod** | [**DeliveryMethodEnum**](#DeliveryMethodEnum) | How the recipient is notified and receives the signing link: &#x60;email&#x60; (default), &#x60;sms&#x60;, or &#x60;email_and_sms&#x60;. SMS delivery is available only to workspaces that are eligible for SMS and have it enabled. &#x60;email&#x60; requires &#x60;email&#x60;, &#x60;sms&#x60; requires &#x60;phone_number&#x60;, and &#x60;email_and_sms&#x60; requires both. SMS content always uses fixed system templates; &#x60;subject&#x60; and &#x60;message&#x60; apply to email notifications only. A 21 CFR Part 11 document records one verified channel per recipient: &#x60;email&#x60; and &#x60;sms&#x60; are both accepted, while &#x60;email_and_sms&#x60; returns a 422. |  [optional] |
|**phoneNumber** | **String** | Recipient phone number in E.164 format (for example &#x60;+14155551234&#x60;). Required when &#x60;delivery_method&#x60; is &#x60;sms&#x60; or &#x60;email_and_sms&#x60;. Only phone numbers from the country codes currently enabled for SMS are accepted. |  [optional] |
|**sendSms** | **Boolean** | Applies only when &#x60;embedded_signing&#x60; is &#x60;true&#x60; and the recipient&#39;s &#x60;delivery_method&#x60; includes SMS. By default, recipients are not notified through SMS when doing embedded signing. Setting this to &#x60;true&#x60; will send the SMS notification to the recipient. Default is &#x60;false&#x60;. |  [optional] |
|**sendSmsDelay** | **Integer** | If &#x60;send_sms&#x60; is &#x60;true&#x60; the recipient will receive the SMS notification immediately. In the case of embedded signing, you can delay this notification to only send if the document is not completed within a few minutes. The SMS will not go out if the document is completed before the delay time is over. Valid values are in minutes ranging from &#x60;0&#x60; to &#x60;60&#x60;. Defaults to &#x60;0&#x60;. |  [optional] |



## Enum: DeliveryMethodEnum

| Name | Value |
|---- | -----|
| EMAIL | &quot;email&quot; |
| SMS | &quot;sms&quot; |
| EMAIL_AND_SMS | &quot;email_and_sms&quot; |
| UNKNOWN_DEFAULT_OPEN_API | &quot;unknown_default_open_api&quot; |



