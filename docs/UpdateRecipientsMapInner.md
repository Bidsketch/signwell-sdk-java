

# UpdateRecipientsMapInner


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | The recipient&#39;s unique identifier from the Get Document response. |  |
|**name** | **String** | Updated name for the recipient. |  |
|**email** | **String** | Updated email address for the recipient. |  |
|**subject** | **String** | Updated email subject for the signature request that this recipient will see. |  [optional] |
|**message** | **String** | Updated email message for the signature request that this recipient will see. |  [optional] |
|**passcode** | **String** | Updated passcode for the recipient. If set, the signer will be required to enter the passcode before viewing and completing the document. On a 21 CFR Part 11 document SignWell generates the passcode, so a sender-defined passcode returns a 422. |  [optional] |
|**passcodeDelivery** | [**PasscodeDelivery**](PasscodeDelivery.md) |  |  [optional] |



