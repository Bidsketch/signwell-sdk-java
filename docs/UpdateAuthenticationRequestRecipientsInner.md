

# UpdateAuthenticationRequestRecipientsInner


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | A unique identifier that you will give to each recipient. We recommend numbering sequentially from 1 to X. IDs are required for associating recipients to fields and more. |  |
|**passcode** | **String** | Updated passcode for the recipient. If set, the signer will be required to enter the passcode before viewing and completing the document. On a 21 CFR Part 11 document SignWell generates the passcode, so a sender-defined passcode returns a 422. |  [optional] |
|**passcodeDelivery** | [**PasscodeDelivery**](PasscodeDelivery.md) |  |  [optional] |



