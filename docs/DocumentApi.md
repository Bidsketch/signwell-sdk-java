# DocumentApi

All URIs are relative to *https://www.signwell.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createDocument**](DocumentApi.md#createDocument) | **POST** /api/v1/documents | Create Document |
| [**createDocumentWithHttpInfo**](DocumentApi.md#createDocumentWithHttpInfo) | **POST** /api/v1/documents | Create Document |
| [**createDocumentFromTemplate**](DocumentApi.md#createDocumentFromTemplate) | **POST** /api/v1/document_templates/documents/ | Create Document from Template |
| [**createDocumentFromTemplateWithHttpInfo**](DocumentApi.md#createDocumentFromTemplateWithHttpInfo) | **POST** /api/v1/document_templates/documents/ | Create Document from Template |
| [**deleteDocument**](DocumentApi.md#deleteDocument) | **DELETE** /api/v1/documents/{id}/ | Delete Document |
| [**deleteDocumentWithHttpInfo**](DocumentApi.md#deleteDocumentWithHttpInfo) | **DELETE** /api/v1/documents/{id}/ | Delete Document |
| [**getCompletedPdf**](DocumentApi.md#getCompletedPdf) | **GET** /api/v1/documents/{id}/completed_pdf/ | Completed PDF |
| [**getCompletedPdfWithHttpInfo**](DocumentApi.md#getCompletedPdfWithHttpInfo) | **GET** /api/v1/documents/{id}/completed_pdf/ | Completed PDF |
| [**getDocument**](DocumentApi.md#getDocument) | **GET** /api/v1/documents/{id}/ | Get Document |
| [**getDocumentWithHttpInfo**](DocumentApi.md#getDocumentWithHttpInfo) | **GET** /api/v1/documents/{id}/ | Get Document |
| [**listDocuments**](DocumentApi.md#listDocuments) | **GET** /api/v1/documents | List Documents |
| [**listDocumentsWithHttpInfo**](DocumentApi.md#listDocumentsWithHttpInfo) | **GET** /api/v1/documents | List Documents |
| [**sendDocument**](DocumentApi.md#sendDocument) | **POST** /api/v1/documents/{id}/send/ | Update and Send Document |
| [**sendDocumentWithHttpInfo**](DocumentApi.md#sendDocumentWithHttpInfo) | **POST** /api/v1/documents/{id}/send/ | Update and Send Document |
| [**sendReminder**](DocumentApi.md#sendReminder) | **POST** /api/v1/documents/{id}/remind/ | Send Reminder |
| [**sendReminderWithHttpInfo**](DocumentApi.md#sendReminderWithHttpInfo) | **POST** /api/v1/documents/{id}/remind/ | Send Reminder |
| [**updateAuthentication**](DocumentApi.md#updateAuthentication) | **PATCH** /api/v1/documents/{id}/authentication/ | Update Authentication |
| [**updateAuthenticationWithHttpInfo**](DocumentApi.md#updateAuthenticationWithHttpInfo) | **PATCH** /api/v1/documents/{id}/authentication/ | Update Authentication |
| [**updateRecipients**](DocumentApi.md#updateRecipients) | **PATCH** /api/v1/documents/{id}/recipients/ | Update Recipients |
| [**updateRecipientsWithHttpInfo**](DocumentApi.md#updateRecipientsWithHttpInfo) | **PATCH** /api/v1/documents/{id}/recipients/ | Update Recipients |



## createDocument

> CompletableFuture<DocumentResponse> createDocument(documentRequest)

Create Document

Creates and optionally sends a new document for signing. If &#x60;draft&#x60; is set to true the document will not be sent.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.DocumentApi;
import java.util.concurrent.CompletableFuture;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://www.signwell.com");
        
        // Configure API key authorization: api_key
        ApiKeyAuth api_key = (ApiKeyAuth) defaultClient.getAuthentication("api_key");
        api_key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //api_key.setApiKeyPrefix("Token");

        DocumentApi apiInstance = new DocumentApi(defaultClient);
        DocumentRequest documentRequest = new DocumentRequest(); // DocumentRequest | 
        try {
            CompletableFuture<DocumentResponse> result = apiInstance.createDocument(documentRequest);
            System.out.println(result.get());
        } catch (ApiException e) {
            System.err.println("Exception when calling DocumentApi#createDocument");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **documentRequest** | [**DocumentRequest**](DocumentRequest.md)|  | |

### Return type

CompletableFuture<[**DocumentResponse**](DocumentResponse.md)>


### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | created |  -  |
| **400** | bad request |  -  |
| **422** | unprocessable entity |  -  |
| **429** | rate limit exceeded |  -  |

## createDocumentWithHttpInfo

> CompletableFuture<ApiResponse<DocumentResponse>> createDocument createDocumentWithHttpInfo(documentRequest)

Create Document

Creates and optionally sends a new document for signing. If &#x60;draft&#x60; is set to true the document will not be sent.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.DocumentApi;
import java.util.concurrent.CompletableFuture;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://www.signwell.com");
        
        // Configure API key authorization: api_key
        ApiKeyAuth api_key = (ApiKeyAuth) defaultClient.getAuthentication("api_key");
        api_key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //api_key.setApiKeyPrefix("Token");

        DocumentApi apiInstance = new DocumentApi(defaultClient);
        DocumentRequest documentRequest = new DocumentRequest(); // DocumentRequest | 
        try {
            CompletableFuture<ApiResponse<DocumentResponse>> response = apiInstance.createDocumentWithHttpInfo(documentRequest);
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
            System.out.println("Response body: " + response.get().getData());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling DocumentApi#createDocument");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling DocumentApi#createDocument");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Response headers: " + e.getResponseHeaders());
            System.err.println("Reason: " + e.getResponseBody());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **documentRequest** | [**DocumentRequest**](DocumentRequest.md)|  | |

### Return type

CompletableFuture<ApiResponse<[**DocumentResponse**](DocumentResponse.md)>>


### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | created |  -  |
| **400** | bad request |  -  |
| **422** | unprocessable entity |  -  |
| **429** | rate limit exceeded |  -  |


## createDocumentFromTemplate

> CompletableFuture<DocumentFromTemplateResponse> createDocumentFromTemplate(documentFromTemplateRequest)

Create Document from Template

Creates and optionally sends a new document for signing. If &#x60;draft&#x60; is set to true the document will not be sent.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.DocumentApi;
import java.util.concurrent.CompletableFuture;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://www.signwell.com");
        
        // Configure API key authorization: api_key
        ApiKeyAuth api_key = (ApiKeyAuth) defaultClient.getAuthentication("api_key");
        api_key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //api_key.setApiKeyPrefix("Token");

        DocumentApi apiInstance = new DocumentApi(defaultClient);
        DocumentFromTemplateRequest documentFromTemplateRequest = new DocumentFromTemplateRequest(); // DocumentFromTemplateRequest | 
        try {
            CompletableFuture<DocumentFromTemplateResponse> result = apiInstance.createDocumentFromTemplate(documentFromTemplateRequest);
            System.out.println(result.get());
        } catch (ApiException e) {
            System.err.println("Exception when calling DocumentApi#createDocumentFromTemplate");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **documentFromTemplateRequest** | [**DocumentFromTemplateRequest**](DocumentFromTemplateRequest.md)|  | |

### Return type

CompletableFuture<[**DocumentFromTemplateResponse**](DocumentFromTemplateResponse.md)>


### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | created |  -  |
| **400** | bad request |  -  |
| **422** | unprocessable entity |  -  |
| **429** | rate limit exceeded |  -  |

## createDocumentFromTemplateWithHttpInfo

> CompletableFuture<ApiResponse<DocumentFromTemplateResponse>> createDocumentFromTemplate createDocumentFromTemplateWithHttpInfo(documentFromTemplateRequest)

Create Document from Template

Creates and optionally sends a new document for signing. If &#x60;draft&#x60; is set to true the document will not be sent.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.DocumentApi;
import java.util.concurrent.CompletableFuture;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://www.signwell.com");
        
        // Configure API key authorization: api_key
        ApiKeyAuth api_key = (ApiKeyAuth) defaultClient.getAuthentication("api_key");
        api_key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //api_key.setApiKeyPrefix("Token");

        DocumentApi apiInstance = new DocumentApi(defaultClient);
        DocumentFromTemplateRequest documentFromTemplateRequest = new DocumentFromTemplateRequest(); // DocumentFromTemplateRequest | 
        try {
            CompletableFuture<ApiResponse<DocumentFromTemplateResponse>> response = apiInstance.createDocumentFromTemplateWithHttpInfo(documentFromTemplateRequest);
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
            System.out.println("Response body: " + response.get().getData());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling DocumentApi#createDocumentFromTemplate");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling DocumentApi#createDocumentFromTemplate");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Response headers: " + e.getResponseHeaders());
            System.err.println("Reason: " + e.getResponseBody());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **documentFromTemplateRequest** | [**DocumentFromTemplateRequest**](DocumentFromTemplateRequest.md)|  | |

### Return type

CompletableFuture<ApiResponse<[**DocumentFromTemplateResponse**](DocumentFromTemplateResponse.md)>>


### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | created |  -  |
| **400** | bad request |  -  |
| **422** | unprocessable entity |  -  |
| **429** | rate limit exceeded |  -  |


## deleteDocument

> CompletableFuture<Void> deleteDocument(id)

Delete Document

Deletes a document. Deleting a document will also cancel document signing (if in progress).  Supply the unique document ID from either a Create Document request or document page URL.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.DocumentApi;
import java.util.concurrent.CompletableFuture;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://www.signwell.com");
        
        // Configure API key authorization: api_key
        ApiKeyAuth api_key = (ApiKeyAuth) defaultClient.getAuthentication("api_key");
        api_key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //api_key.setApiKeyPrefix("Token");

        DocumentApi apiInstance = new DocumentApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        try {
            CompletableFuture<Void> result = apiInstance.deleteDocument(id);
        } catch (ApiException e) {
            System.err.println("Exception when calling DocumentApi#deleteDocument");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **UUID**|  | |

### Return type


CompletableFuture<void> (empty response body)

### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | no content |  -  |
| **404** | not found |  -  |
| **429** | rate limit exceeded |  -  |

## deleteDocumentWithHttpInfo

> CompletableFuture<ApiResponse<Void>> deleteDocument deleteDocumentWithHttpInfo(id)

Delete Document

Deletes a document. Deleting a document will also cancel document signing (if in progress).  Supply the unique document ID from either a Create Document request or document page URL.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.DocumentApi;
import java.util.concurrent.CompletableFuture;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://www.signwell.com");
        
        // Configure API key authorization: api_key
        ApiKeyAuth api_key = (ApiKeyAuth) defaultClient.getAuthentication("api_key");
        api_key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //api_key.setApiKeyPrefix("Token");

        DocumentApi apiInstance = new DocumentApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        try {
            CompletableFuture<ApiResponse<Void>> response = apiInstance.deleteDocumentWithHttpInfo(id);
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling DocumentApi#deleteDocument");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling DocumentApi#deleteDocument");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Response headers: " + e.getResponseHeaders());
            System.err.println("Reason: " + e.getResponseBody());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **UUID**|  | |

### Return type


CompletableFuture<ApiResponse<Void>>

### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | no content |  -  |
| **404** | not found |  -  |
| **429** | rate limit exceeded |  -  |


## getCompletedPdf

> CompletableFuture<CompletedPdfResponse> getCompletedPdf(id, urlOnly, auditPage, fileFormat)

Completed PDF

Gets a completed document PDF or ZIP file. Supply the unique document ID from either a document creation request or document page URL.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.DocumentApi;
import java.util.concurrent.CompletableFuture;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://www.signwell.com");
        
        // Configure API key authorization: api_key
        ApiKeyAuth api_key = (ApiKeyAuth) defaultClient.getAuthentication("api_key");
        api_key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //api_key.setApiKeyPrefix("Token");

        DocumentApi apiInstance = new DocumentApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        Boolean urlOnly = false; // Boolean | 
        Boolean auditPage = true; // Boolean | 
        FileFormat fileFormat = FileFormat.fromValue("pdf"); // FileFormat | 
        try {
            CompletableFuture<CompletedPdfResponse> result = apiInstance.getCompletedPdf(id, urlOnly, auditPage, fileFormat);
            System.out.println(result.get());
        } catch (ApiException e) {
            System.err.println("Exception when calling DocumentApi#getCompletedPdf");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **UUID**|  | |
| **urlOnly** | **Boolean**|  | [optional] [default to false] |
| **auditPage** | **Boolean**|  | [optional] [default to true] |
| **fileFormat** | [**FileFormat**](.md)|  | [optional] [enum: pdf, zip] |

### Return type

CompletableFuture<[**CompletedPdfResponse**](CompletedPdfResponse.md)>


### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful, returns the final completed PDF, or if url_only is set to true, a JSON object is returned. When url_only&#x3D;false (default), the response is the raw PDF or ZIP binary data. |  -  |
| **404** | not_found |  -  |
| **429** | rate limit exceeded |  -  |

## getCompletedPdfWithHttpInfo

> CompletableFuture<ApiResponse<CompletedPdfResponse>> getCompletedPdf getCompletedPdfWithHttpInfo(id, urlOnly, auditPage, fileFormat)

Completed PDF

Gets a completed document PDF or ZIP file. Supply the unique document ID from either a document creation request or document page URL.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.DocumentApi;
import java.util.concurrent.CompletableFuture;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://www.signwell.com");
        
        // Configure API key authorization: api_key
        ApiKeyAuth api_key = (ApiKeyAuth) defaultClient.getAuthentication("api_key");
        api_key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //api_key.setApiKeyPrefix("Token");

        DocumentApi apiInstance = new DocumentApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        Boolean urlOnly = false; // Boolean | 
        Boolean auditPage = true; // Boolean | 
        FileFormat fileFormat = FileFormat.fromValue("pdf"); // FileFormat | 
        try {
            CompletableFuture<ApiResponse<CompletedPdfResponse>> response = apiInstance.getCompletedPdfWithHttpInfo(id, urlOnly, auditPage, fileFormat);
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
            System.out.println("Response body: " + response.get().getData());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling DocumentApi#getCompletedPdf");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling DocumentApi#getCompletedPdf");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Response headers: " + e.getResponseHeaders());
            System.err.println("Reason: " + e.getResponseBody());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **UUID**|  | |
| **urlOnly** | **Boolean**|  | [optional] [default to false] |
| **auditPage** | **Boolean**|  | [optional] [default to true] |
| **fileFormat** | [**FileFormat**](.md)|  | [optional] [enum: pdf, zip] |

### Return type

CompletableFuture<ApiResponse<[**CompletedPdfResponse**](CompletedPdfResponse.md)>>


### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful, returns the final completed PDF, or if url_only is set to true, a JSON object is returned. When url_only&#x3D;false (default), the response is the raw PDF or ZIP binary data. |  -  |
| **404** | not_found |  -  |
| **429** | rate limit exceeded |  -  |


## getDocument

> CompletableFuture<DocumentResponse> getDocument(id)

Get Document

Returns a document and all associated document data. Supply the unique document ID from either a document creation request or Document page URL.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.DocumentApi;
import java.util.concurrent.CompletableFuture;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://www.signwell.com");
        
        // Configure API key authorization: api_key
        ApiKeyAuth api_key = (ApiKeyAuth) defaultClient.getAuthentication("api_key");
        api_key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //api_key.setApiKeyPrefix("Token");

        DocumentApi apiInstance = new DocumentApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        try {
            CompletableFuture<DocumentResponse> result = apiInstance.getDocument(id);
            System.out.println(result.get());
        } catch (ApiException e) {
            System.err.println("Exception when calling DocumentApi#getDocument");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **UUID**|  | |

### Return type

CompletableFuture<[**DocumentResponse**](DocumentResponse.md)>


### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful |  -  |
| **404** | not_found |  -  |
| **429** | rate limit exceeded |  -  |

## getDocumentWithHttpInfo

> CompletableFuture<ApiResponse<DocumentResponse>> getDocument getDocumentWithHttpInfo(id)

Get Document

Returns a document and all associated document data. Supply the unique document ID from either a document creation request or Document page URL.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.DocumentApi;
import java.util.concurrent.CompletableFuture;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://www.signwell.com");
        
        // Configure API key authorization: api_key
        ApiKeyAuth api_key = (ApiKeyAuth) defaultClient.getAuthentication("api_key");
        api_key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //api_key.setApiKeyPrefix("Token");

        DocumentApi apiInstance = new DocumentApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        try {
            CompletableFuture<ApiResponse<DocumentResponse>> response = apiInstance.getDocumentWithHttpInfo(id);
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
            System.out.println("Response body: " + response.get().getData());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling DocumentApi#getDocument");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling DocumentApi#getDocument");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Response headers: " + e.getResponseHeaders());
            System.err.println("Reason: " + e.getResponseBody());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **UUID**|  | |

### Return type

CompletableFuture<ApiResponse<[**DocumentResponse**](DocumentResponse.md)>>


### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful |  -  |
| **404** | not_found |  -  |
| **429** | rate limit exceeded |  -  |


## listDocuments

> CompletableFuture<DocumentListResponse> listDocuments(page, limit, query)

List Documents

Returns a paginated list of documents for the authenticated account.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.DocumentApi;
import java.util.concurrent.CompletableFuture;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://www.signwell.com");
        
        // Configure API key authorization: api_key
        ApiKeyAuth api_key = (ApiKeyAuth) defaultClient.getAuthentication("api_key");
        api_key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //api_key.setApiKeyPrefix("Token");

        DocumentApi apiInstance = new DocumentApi(defaultClient);
        Integer page = 1; // Integer | 
        Integer limit = 10; // Integer | 
        String query = "query_example"; // String | Search documents using SignWell key:value syntax.
        try {
            CompletableFuture<DocumentListResponse> result = apiInstance.listDocuments(page, limit, query);
            System.out.println(result.get());
        } catch (ApiException e) {
            System.err.println("Exception when calling DocumentApi#listDocuments");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **page** | **Integer**|  | [optional] [default to 1] |
| **limit** | **Integer**|  | [optional] [default to 10] |
| **query** | **String**| Search documents using SignWell key:value syntax. | [optional] |

### Return type

CompletableFuture<[**DocumentListResponse**](DocumentListResponse.md)>


### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful |  -  |
| **401** | unauthorized |  -  |
| **429** | rate limit exceeded |  -  |

## listDocumentsWithHttpInfo

> CompletableFuture<ApiResponse<DocumentListResponse>> listDocuments listDocumentsWithHttpInfo(page, limit, query)

List Documents

Returns a paginated list of documents for the authenticated account.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.DocumentApi;
import java.util.concurrent.CompletableFuture;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://www.signwell.com");
        
        // Configure API key authorization: api_key
        ApiKeyAuth api_key = (ApiKeyAuth) defaultClient.getAuthentication("api_key");
        api_key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //api_key.setApiKeyPrefix("Token");

        DocumentApi apiInstance = new DocumentApi(defaultClient);
        Integer page = 1; // Integer | 
        Integer limit = 10; // Integer | 
        String query = "query_example"; // String | Search documents using SignWell key:value syntax.
        try {
            CompletableFuture<ApiResponse<DocumentListResponse>> response = apiInstance.listDocumentsWithHttpInfo(page, limit, query);
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
            System.out.println("Response body: " + response.get().getData());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling DocumentApi#listDocuments");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling DocumentApi#listDocuments");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Response headers: " + e.getResponseHeaders());
            System.err.println("Reason: " + e.getResponseBody());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **page** | **Integer**|  | [optional] [default to 1] |
| **limit** | **Integer**|  | [optional] [default to 10] |
| **query** | **String**| Search documents using SignWell key:value syntax. | [optional] |

### Return type

CompletableFuture<ApiResponse<[**DocumentListResponse**](DocumentListResponse.md)>>


### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful |  -  |
| **401** | unauthorized |  -  |
| **429** | rate limit exceeded |  -  |


## sendDocument

> CompletableFuture<DocumentResponse> sendDocument(id, updateDocumentAndSendRequest)

Update and Send Document

Updates a draft document and sends it to be signed by recipients.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.DocumentApi;
import java.util.concurrent.CompletableFuture;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://www.signwell.com");
        
        // Configure API key authorization: api_key
        ApiKeyAuth api_key = (ApiKeyAuth) defaultClient.getAuthentication("api_key");
        api_key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //api_key.setApiKeyPrefix("Token");

        DocumentApi apiInstance = new DocumentApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        UpdateDocumentAndSendRequest updateDocumentAndSendRequest = new UpdateDocumentAndSendRequest(); // UpdateDocumentAndSendRequest | 
        try {
            CompletableFuture<DocumentResponse> result = apiInstance.sendDocument(id, updateDocumentAndSendRequest);
            System.out.println(result.get());
        } catch (ApiException e) {
            System.err.println("Exception when calling DocumentApi#sendDocument");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **UUID**|  | |
| **updateDocumentAndSendRequest** | [**UpdateDocumentAndSendRequest**](UpdateDocumentAndSendRequest.md)|  | |

### Return type

CompletableFuture<[**DocumentResponse**](DocumentResponse.md)>


### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | created |  -  |
| **422** | unprocessable entity |  -  |
| **429** | rate limit exceeded |  -  |

## sendDocumentWithHttpInfo

> CompletableFuture<ApiResponse<DocumentResponse>> sendDocument sendDocumentWithHttpInfo(id, updateDocumentAndSendRequest)

Update and Send Document

Updates a draft document and sends it to be signed by recipients.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.DocumentApi;
import java.util.concurrent.CompletableFuture;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://www.signwell.com");
        
        // Configure API key authorization: api_key
        ApiKeyAuth api_key = (ApiKeyAuth) defaultClient.getAuthentication("api_key");
        api_key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //api_key.setApiKeyPrefix("Token");

        DocumentApi apiInstance = new DocumentApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        UpdateDocumentAndSendRequest updateDocumentAndSendRequest = new UpdateDocumentAndSendRequest(); // UpdateDocumentAndSendRequest | 
        try {
            CompletableFuture<ApiResponse<DocumentResponse>> response = apiInstance.sendDocumentWithHttpInfo(id, updateDocumentAndSendRequest);
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
            System.out.println("Response body: " + response.get().getData());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling DocumentApi#sendDocument");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling DocumentApi#sendDocument");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Response headers: " + e.getResponseHeaders());
            System.err.println("Reason: " + e.getResponseBody());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **UUID**|  | |
| **updateDocumentAndSendRequest** | [**UpdateDocumentAndSendRequest**](UpdateDocumentAndSendRequest.md)|  | |

### Return type

CompletableFuture<ApiResponse<[**DocumentResponse**](DocumentResponse.md)>>


### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | created |  -  |
| **422** | unprocessable entity |  -  |
| **429** | rate limit exceeded |  -  |


## sendReminder

> CompletableFuture<Void> sendReminder(id, sendReminderRequest)

Send Reminder

Sends a reminder email to recipients that have not signed yet.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.DocumentApi;
import java.util.concurrent.CompletableFuture;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://www.signwell.com");
        
        // Configure API key authorization: api_key
        ApiKeyAuth api_key = (ApiKeyAuth) defaultClient.getAuthentication("api_key");
        api_key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //api_key.setApiKeyPrefix("Token");

        DocumentApi apiInstance = new DocumentApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        SendReminderRequest sendReminderRequest = new SendReminderRequest(); // SendReminderRequest | 
        try {
            CompletableFuture<Void> result = apiInstance.sendReminder(id, sendReminderRequest);
        } catch (ApiException e) {
            System.err.println("Exception when calling DocumentApi#sendReminder");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **UUID**|  | |
| **sendReminderRequest** | [**SendReminderRequest**](SendReminderRequest.md)|  | |

### Return type


CompletableFuture<void> (empty response body)

### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | created |  -  |
| **422** | unprocessable entity |  -  |
| **404** | not found |  -  |
| **429** | rate limit exceeded |  -  |

## sendReminderWithHttpInfo

> CompletableFuture<ApiResponse<Void>> sendReminder sendReminderWithHttpInfo(id, sendReminderRequest)

Send Reminder

Sends a reminder email to recipients that have not signed yet.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.DocumentApi;
import java.util.concurrent.CompletableFuture;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://www.signwell.com");
        
        // Configure API key authorization: api_key
        ApiKeyAuth api_key = (ApiKeyAuth) defaultClient.getAuthentication("api_key");
        api_key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //api_key.setApiKeyPrefix("Token");

        DocumentApi apiInstance = new DocumentApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        SendReminderRequest sendReminderRequest = new SendReminderRequest(); // SendReminderRequest | 
        try {
            CompletableFuture<ApiResponse<Void>> response = apiInstance.sendReminderWithHttpInfo(id, sendReminderRequest);
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling DocumentApi#sendReminder");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling DocumentApi#sendReminder");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Response headers: " + e.getResponseHeaders());
            System.err.println("Reason: " + e.getResponseBody());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **UUID**|  | |
| **sendReminderRequest** | [**SendReminderRequest**](SendReminderRequest.md)|  | |

### Return type


CompletableFuture<ApiResponse<Void>>

### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | created |  -  |
| **422** | unprocessable entity |  -  |
| **404** | not found |  -  |
| **429** | rate limit exceeded |  -  |


## updateAuthentication

> CompletableFuture<DocumentResponse> updateAuthentication(id, updateAuthenticationRequest)

Update Authentication

Update passcode delivery settings for recipients on a sent document. Only recipients who have not started signing can be updated.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.DocumentApi;
import java.util.concurrent.CompletableFuture;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://www.signwell.com");
        
        // Configure API key authorization: api_key
        ApiKeyAuth api_key = (ApiKeyAuth) defaultClient.getAuthentication("api_key");
        api_key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //api_key.setApiKeyPrefix("Token");

        DocumentApi apiInstance = new DocumentApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        UpdateAuthenticationRequest updateAuthenticationRequest = new UpdateAuthenticationRequest(); // UpdateAuthenticationRequest | 
        try {
            CompletableFuture<DocumentResponse> result = apiInstance.updateAuthentication(id, updateAuthenticationRequest);
            System.out.println(result.get());
        } catch (ApiException e) {
            System.err.println("Exception when calling DocumentApi#updateAuthentication");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **UUID**|  | |
| **updateAuthenticationRequest** | [**UpdateAuthenticationRequest**](UpdateAuthenticationRequest.md)|  | |

### Return type

CompletableFuture<[**DocumentResponse**](DocumentResponse.md)>


### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful |  -  |
| **404** | not found |  -  |
| **422** | unprocessable entity |  -  |
| **429** | rate limit exceeded |  -  |

## updateAuthenticationWithHttpInfo

> CompletableFuture<ApiResponse<DocumentResponse>> updateAuthentication updateAuthenticationWithHttpInfo(id, updateAuthenticationRequest)

Update Authentication

Update passcode delivery settings for recipients on a sent document. Only recipients who have not started signing can be updated.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.DocumentApi;
import java.util.concurrent.CompletableFuture;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://www.signwell.com");
        
        // Configure API key authorization: api_key
        ApiKeyAuth api_key = (ApiKeyAuth) defaultClient.getAuthentication("api_key");
        api_key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //api_key.setApiKeyPrefix("Token");

        DocumentApi apiInstance = new DocumentApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        UpdateAuthenticationRequest updateAuthenticationRequest = new UpdateAuthenticationRequest(); // UpdateAuthenticationRequest | 
        try {
            CompletableFuture<ApiResponse<DocumentResponse>> response = apiInstance.updateAuthenticationWithHttpInfo(id, updateAuthenticationRequest);
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
            System.out.println("Response body: " + response.get().getData());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling DocumentApi#updateAuthentication");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling DocumentApi#updateAuthentication");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Response headers: " + e.getResponseHeaders());
            System.err.println("Reason: " + e.getResponseBody());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **UUID**|  | |
| **updateAuthenticationRequest** | [**UpdateAuthenticationRequest**](UpdateAuthenticationRequest.md)|  | |

### Return type

CompletableFuture<ApiResponse<[**DocumentResponse**](DocumentResponse.md)>>


### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful |  -  |
| **404** | not found |  -  |
| **422** | unprocessable entity |  -  |
| **429** | rate limit exceeded |  -  |


## updateRecipients

> CompletableFuture<DocumentResponse> updateRecipients(id, updateRecipientsRequest)

Update Recipients

Updates one or more recipients on a document that has already been sent. Only recipients who have not started signing may be updated. Recipient IDs must be retrieved from the Get Document response. Allowed document statuses: sent, viewed, pending, bounced. For non-embedded documents, updated recipients will receive a new notification email. For embedded signing documents, email behavior follows each recipient&#39;s send_email setting.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.DocumentApi;
import java.util.concurrent.CompletableFuture;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://www.signwell.com");
        
        // Configure API key authorization: api_key
        ApiKeyAuth api_key = (ApiKeyAuth) defaultClient.getAuthentication("api_key");
        api_key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //api_key.setApiKeyPrefix("Token");

        DocumentApi apiInstance = new DocumentApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        UpdateRecipientsRequest updateRecipientsRequest = new UpdateRecipientsRequest(); // UpdateRecipientsRequest | 
        try {
            CompletableFuture<DocumentResponse> result = apiInstance.updateRecipients(id, updateRecipientsRequest);
            System.out.println(result.get());
        } catch (ApiException e) {
            System.err.println("Exception when calling DocumentApi#updateRecipients");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **UUID**|  | |
| **updateRecipientsRequest** | [**UpdateRecipientsRequest**](UpdateRecipientsRequest.md)|  | |

### Return type

CompletableFuture<[**DocumentResponse**](DocumentResponse.md)>


### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful |  -  |
| **409** | conflict - document not in eligible state |  -  |
| **400** | bad request - invalid structure |  -  |
| **422** | unprocessable entity - business rule violation |  -  |
| **404** | not found |  -  |
| **429** | rate limit exceeded |  -  |

## updateRecipientsWithHttpInfo

> CompletableFuture<ApiResponse<DocumentResponse>> updateRecipients updateRecipientsWithHttpInfo(id, updateRecipientsRequest)

Update Recipients

Updates one or more recipients on a document that has already been sent. Only recipients who have not started signing may be updated. Recipient IDs must be retrieved from the Get Document response. Allowed document statuses: sent, viewed, pending, bounced. For non-embedded documents, updated recipients will receive a new notification email. For embedded signing documents, email behavior follows each recipient&#39;s send_email setting.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.DocumentApi;
import java.util.concurrent.CompletableFuture;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://www.signwell.com");
        
        // Configure API key authorization: api_key
        ApiKeyAuth api_key = (ApiKeyAuth) defaultClient.getAuthentication("api_key");
        api_key.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //api_key.setApiKeyPrefix("Token");

        DocumentApi apiInstance = new DocumentApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        UpdateRecipientsRequest updateRecipientsRequest = new UpdateRecipientsRequest(); // UpdateRecipientsRequest | 
        try {
            CompletableFuture<ApiResponse<DocumentResponse>> response = apiInstance.updateRecipientsWithHttpInfo(id, updateRecipientsRequest);
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
            System.out.println("Response body: " + response.get().getData());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling DocumentApi#updateRecipients");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling DocumentApi#updateRecipients");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Response headers: " + e.getResponseHeaders());
            System.err.println("Reason: " + e.getResponseBody());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **UUID**|  | |
| **updateRecipientsRequest** | [**UpdateRecipientsRequest**](UpdateRecipientsRequest.md)|  | |

### Return type

CompletableFuture<ApiResponse<[**DocumentResponse**](DocumentResponse.md)>>


### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful |  -  |
| **409** | conflict - document not in eligible state |  -  |
| **400** | bad request - invalid structure |  -  |
| **422** | unprocessable entity - business rule violation |  -  |
| **404** | not found |  -  |
| **429** | rate limit exceeded |  -  |

