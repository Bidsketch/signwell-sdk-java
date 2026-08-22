

# AdditionalFilesInner

Additional files to be appended to the document. Will not replace existing files from the template. Document files can be uploaded by specifying a file URL or base64 string. Either `file_url` or `file_base64` must be present (not both). Valid file types are: .pdf, .docx, .jpg, .png, .ppt, .xls, .pages, and .txt.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | Name of the file that will be uploaded. |  |
|**fileUrl** | **String** | Publicly available URL of the file to be uploaded. |  [optional] |
|**fileBase64** | **byte[]** | A RFC 4648 base64 string of the file to be uploaded. |  [optional] |



