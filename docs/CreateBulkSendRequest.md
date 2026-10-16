

# CreateBulkSendRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**templateIds** | **List&lt;UUID&gt;** | Unique identifiers for a list of templates. A template that carries a pre-applied signature or initials field cannot be used while 21 CFR Part 11 is enabled, which requires every signature to be signed by the recipient: it returns a 422. |  |
|**bulkSendCsv** | **String** | A RFC 4648 base64 string of the template CSV file to be validated. While 21 CFR Part 11 is enabled, every recipient needs a name: the CSV must carry a name column next to each placeholder email column (for example &#x60;signer_name&#x60; alongside &#x60;signer_email&#x60;), and each row must fill that name in wherever it carries the matching email, otherwise it returns a 422. A row that leaves both the email and the name blank sends nothing to that placeholder, so no name is needed there. |  |
|**skipRowErrors** | **Boolean** | Whether to skip errors in the rows. Defaults to &#x60;false&#x60;. |  [optional] |
|**apiApplicationId** | **UUID** | Unique identifier for API Application settings to use. API Applications are optional and mainly used when isolating OAuth apps or for more control over embedded API settings |  [optional] |
|**name** | **String** | The name of the Bulk Send. Will be used as the document name for each of the documents. |  [optional] |
|**subject** | **String** | Email subject for the signature request that recipients will see. Defaults to the default system subject or a template subject. |  [optional] |
|**message** | **String** | Email message for the signature request that recipients will see. Defaults to the default system message or a template message. |  [optional] |
|**applySigningOrder** | **Boolean** | When set to &#x60;true&#x60; recipients will sign one at a time in the order of the &#x60;recipients&#x60; collection of this request. |  [optional] |
|**customRequesterName** | **String** | Sets the custom requester name for the document. When set, this is the name used for all email communications, signing notifications, and in the audit file. |  [optional] |
|**customRequesterEmail** | **String** | Sets the custom requester email for the document. When set, this is the email used for all email communications, signing notifications, and in the audit file. |  [optional] |



