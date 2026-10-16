

# PlaceholdersInner


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | A unique identifier that you will give to each placeholder. We recommend numbering sequentially from 1 to X. IDs are required for associating recipients to fields and more. |  |
|**name** | **String** | Name of the placeholder. |  |
|**preassignedRecipientName** | **String** | In some cases, it may be necessary to pre-fill the name and email for a placeholder because it will always be the same person for all documents created from this template. This sets the name. Every recipient on a 21 CFR Part 11 document needs a name, so leaving it unset means the name has to be supplied when the document is created. |  [optional] |
|**preassignedRecipientEmail** | **String** | In some cases, it may be necessary to pre-fill the name and email for a placeholder because it will always be the same person for all documents created from this template. This sets the email. |  [optional] |
|**passcodeDelivery** | [**PlaceholdersInnerPasscodeDelivery**](PlaceholdersInnerPasscodeDelivery.md) |  |  [optional] |



