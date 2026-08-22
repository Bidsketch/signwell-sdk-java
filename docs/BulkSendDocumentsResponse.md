

# BulkSendDocumentsResponse

Paginated list of documents in a bulk send

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **UUID** |  |  |
|**name** | **String** |  |  [optional] |
|**apiApplicationId** | **UUID** |  |  [optional] |
|**createdAt** | **OffsetDateTime** |  |  [optional] |
|**userId** | **UUID** |  |  [optional] |
|**status** | **String** |  |  [optional] |
|**documents** | [**List&lt;DocumentResponse&gt;**](DocumentResponse.md) |  |  |
|**currentPage** | **Integer** |  |  |
|**nextPage** | **Integer** |  |  [optional] |
|**previousPage** | **Integer** |  |  [optional] |
|**totalCount** | **Integer** |  |  |
|**totalPages** | **Integer** |  |  |



