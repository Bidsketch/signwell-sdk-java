# BulkSendApi

All URIs are relative to *https://www.signwell.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createBulkSend**](BulkSendApi.md#createBulkSend) | **POST** /api/v1/bulk_sends | Create Bulk Send |
| [**createBulkSendWithHttpInfo**](BulkSendApi.md#createBulkSendWithHttpInfo) | **POST** /api/v1/bulk_sends | Create Bulk Send |
| [**getBulkSend**](BulkSendApi.md#getBulkSend) | **GET** /api/v1/bulk_sends/{id}/ | Get Bulk Send |
| [**getBulkSendWithHttpInfo**](BulkSendApi.md#getBulkSendWithHttpInfo) | **GET** /api/v1/bulk_sends/{id}/ | Get Bulk Send |
| [**getBulkSendCsvTemplate**](BulkSendApi.md#getBulkSendCsvTemplate) | **GET** /api/v1/bulk_sends/csv_template/ | Get Bulk Send CSV Template |
| [**getBulkSendCsvTemplateWithHttpInfo**](BulkSendApi.md#getBulkSendCsvTemplateWithHttpInfo) | **GET** /api/v1/bulk_sends/csv_template/ | Get Bulk Send CSV Template |
| [**getBulkSendDocuments**](BulkSendApi.md#getBulkSendDocuments) | **GET** /api/v1/bulk_sends/{id}/documents/ | Get Bulk Send Documents |
| [**getBulkSendDocumentsWithHttpInfo**](BulkSendApi.md#getBulkSendDocumentsWithHttpInfo) | **GET** /api/v1/bulk_sends/{id}/documents/ | Get Bulk Send Documents |
| [**listBulkSends**](BulkSendApi.md#listBulkSends) | **GET** /api/v1/bulk_sends | List Bulk Sendings |
| [**listBulkSendsWithHttpInfo**](BulkSendApi.md#listBulkSendsWithHttpInfo) | **GET** /api/v1/bulk_sends | List Bulk Sendings |
| [**validateBulkSendCsv**](BulkSendApi.md#validateBulkSendCsv) | **POST** /api/v1/bulk_sends/validate_csv/ | Validate Bulk Send CSV |
| [**validateBulkSendCsvWithHttpInfo**](BulkSendApi.md#validateBulkSendCsvWithHttpInfo) | **POST** /api/v1/bulk_sends/validate_csv/ | Validate Bulk Send CSV |



## createBulkSend

> CompletableFuture<BulkSendCreateResponse> createBulkSend(createBulkSendRequest)

Create Bulk Send

Creates a bulk send, and it validates the CSV file before creating the bulk send.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.BulkSendApi;
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

        BulkSendApi apiInstance = new BulkSendApi(defaultClient);
        CreateBulkSendRequest createBulkSendRequest = new CreateBulkSendRequest(); // CreateBulkSendRequest | 
        try {
            CompletableFuture<BulkSendCreateResponse> result = apiInstance.createBulkSend(createBulkSendRequest);
            System.out.println(result.get());
        } catch (ApiException e) {
            System.err.println("Exception when calling BulkSendApi#createBulkSend");
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
| **createBulkSendRequest** | [**CreateBulkSendRequest**](CreateBulkSendRequest.md)|  | |

### Return type

CompletableFuture<[**BulkSendCreateResponse**](BulkSendCreateResponse.md)>


### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | successful |  -  |
| **401** | unauthorized |  -  |
| **422** | unprocessable entity |  -  |
| **429** | rate limit exceeded |  -  |

## createBulkSendWithHttpInfo

> CompletableFuture<ApiResponse<BulkSendCreateResponse>> createBulkSendWithHttpInfo(createBulkSendRequest)

Create Bulk Send

Creates a bulk send, and it validates the CSV file before creating the bulk send.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.BulkSendApi;
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

        BulkSendApi apiInstance = new BulkSendApi(defaultClient);
        CreateBulkSendRequest createBulkSendRequest = new CreateBulkSendRequest(); // CreateBulkSendRequest | 
        try {
            CompletableFuture<ApiResponse<BulkSendCreateResponse>> response = apiInstance.createBulkSendWithHttpInfo(createBulkSendRequest);
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
            System.out.println("Response body: " + response.get().getData());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling BulkSendApi#createBulkSend");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling BulkSendApi#createBulkSend");
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
| **createBulkSendRequest** | [**CreateBulkSendRequest**](CreateBulkSendRequest.md)|  | |

### Return type

CompletableFuture<ApiResponse<[**BulkSendCreateResponse**](BulkSendCreateResponse.md)>>


### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | successful |  -  |
| **401** | unauthorized |  -  |
| **422** | unprocessable entity |  -  |
| **429** | rate limit exceeded |  -  |


## getBulkSend

> CompletableFuture<BulkSendResponse> getBulkSend(id)

Get Bulk Send

Returns information about the Bulk Send.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.BulkSendApi;
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

        BulkSendApi apiInstance = new BulkSendApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        try {
            CompletableFuture<BulkSendResponse> result = apiInstance.getBulkSend(id);
            System.out.println(result.get());
        } catch (ApiException e) {
            System.err.println("Exception when calling BulkSendApi#getBulkSend");
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

CompletableFuture<[**BulkSendResponse**](BulkSendResponse.md)>


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
| **404** | not found |  -  |
| **429** | rate limit exceeded |  -  |

## getBulkSendWithHttpInfo

> CompletableFuture<ApiResponse<BulkSendResponse>> getBulkSendWithHttpInfo(id)

Get Bulk Send

Returns information about the Bulk Send.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.BulkSendApi;
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

        BulkSendApi apiInstance = new BulkSendApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        try {
            CompletableFuture<ApiResponse<BulkSendResponse>> response = apiInstance.getBulkSendWithHttpInfo(id);
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
            System.out.println("Response body: " + response.get().getData());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling BulkSendApi#getBulkSend");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling BulkSendApi#getBulkSend");
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

CompletableFuture<ApiResponse<[**BulkSendResponse**](BulkSendResponse.md)>>


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
| **404** | not found |  -  |
| **429** | rate limit exceeded |  -  |


## getBulkSendCsvTemplate

> CompletableFuture<File> getBulkSendCsvTemplate(templateIds, base64)

Get Bulk Send CSV Template

Fetches a CSV template that corresponds to the provided document template IDs. CSV templates are blank CSV files that have columns containing required and optional data that can be sent when creating a bulk send. Fields can be referenced by the field label. Example: [placeholder name]_[field label] could be something like customer_address or signer_company_name (if &#39;Customer&#39; and &#39;Signer&#39; were placeholder names for templates set up in SignWell).

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.BulkSendApi;
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

        BulkSendApi apiInstance = new BulkSendApi(defaultClient);
        List<UUID> templateIds = Arrays.asList(); // List<UUID> | 
        Boolean base64 = true; // Boolean | 
        try {
            CompletableFuture<File> result = apiInstance.getBulkSendCsvTemplate(templateIds, base64);
            System.out.println(result.get());
        } catch (ApiException e) {
            System.err.println("Exception when calling BulkSendApi#getBulkSendCsvTemplate");
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
| **templateIds** | [**List&lt;UUID&gt;**](UUID.md)|  | |
| **base64** | **Boolean**|  | [optional] |

### Return type

CompletableFuture<[**File**](File.md)>


### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/octet-stream, application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful |  -  |
| **401** | unauthorized |  -  |
| **404** | not found |  -  |
| **429** | rate limit exceeded |  -  |

## getBulkSendCsvTemplateWithHttpInfo

> CompletableFuture<ApiResponse<File>> getBulkSendCsvTemplateWithHttpInfo(templateIds, base64)

Get Bulk Send CSV Template

Fetches a CSV template that corresponds to the provided document template IDs. CSV templates are blank CSV files that have columns containing required and optional data that can be sent when creating a bulk send. Fields can be referenced by the field label. Example: [placeholder name]_[field label] could be something like customer_address or signer_company_name (if &#39;Customer&#39; and &#39;Signer&#39; were placeholder names for templates set up in SignWell).

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.BulkSendApi;
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

        BulkSendApi apiInstance = new BulkSendApi(defaultClient);
        List<UUID> templateIds = Arrays.asList(); // List<UUID> | 
        Boolean base64 = true; // Boolean | 
        try {
            CompletableFuture<ApiResponse<File>> response = apiInstance.getBulkSendCsvTemplateWithHttpInfo(templateIds, base64);
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
            System.out.println("Response body: " + response.get().getData());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling BulkSendApi#getBulkSendCsvTemplate");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling BulkSendApi#getBulkSendCsvTemplate");
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
| **templateIds** | [**List&lt;UUID&gt;**](UUID.md)|  | |
| **base64** | **Boolean**|  | [optional] |

### Return type

CompletableFuture<ApiResponse<[**File**](File.md)>>


### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/octet-stream, application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful |  -  |
| **401** | unauthorized |  -  |
| **404** | not found |  -  |
| **429** | rate limit exceeded |  -  |


## getBulkSendDocuments

> CompletableFuture<BulkSendDocumentsResponse> getBulkSendDocuments(id, limit, page)

Get Bulk Send Documents

Returns information about the Bulk Send.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.BulkSendApi;
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

        BulkSendApi apiInstance = new BulkSendApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        Integer limit = 10; // Integer | 
        Integer page = 1; // Integer | 
        try {
            CompletableFuture<BulkSendDocumentsResponse> result = apiInstance.getBulkSendDocuments(id, limit, page);
            System.out.println(result.get());
        } catch (ApiException e) {
            System.err.println("Exception when calling BulkSendApi#getBulkSendDocuments");
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
| **limit** | **Integer**|  | [optional] [default to 10] |
| **page** | **Integer**|  | [optional] [default to 1] |

### Return type

CompletableFuture<[**BulkSendDocumentsResponse**](BulkSendDocumentsResponse.md)>


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
| **404** | not found |  -  |
| **429** | rate limit exceeded |  -  |

## getBulkSendDocumentsWithHttpInfo

> CompletableFuture<ApiResponse<BulkSendDocumentsResponse>> getBulkSendDocumentsWithHttpInfo(id, limit, page)

Get Bulk Send Documents

Returns information about the Bulk Send.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.BulkSendApi;
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

        BulkSendApi apiInstance = new BulkSendApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        Integer limit = 10; // Integer | 
        Integer page = 1; // Integer | 
        try {
            CompletableFuture<ApiResponse<BulkSendDocumentsResponse>> response = apiInstance.getBulkSendDocumentsWithHttpInfo(id, limit, page);
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
            System.out.println("Response body: " + response.get().getData());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling BulkSendApi#getBulkSendDocuments");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling BulkSendApi#getBulkSendDocuments");
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
| **limit** | **Integer**|  | [optional] [default to 10] |
| **page** | **Integer**|  | [optional] [default to 1] |

### Return type

CompletableFuture<ApiResponse<[**BulkSendDocumentsResponse**](BulkSendDocumentsResponse.md)>>


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
| **404** | not found |  -  |
| **429** | rate limit exceeded |  -  |


## listBulkSends

> CompletableFuture<BulkSendListResponse> listBulkSends(userEmail, limit, page, apiApplicationId)

List Bulk Sendings

Returns information about the Bulk Send.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.BulkSendApi;
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

        BulkSendApi apiInstance = new BulkSendApi(defaultClient);
        String userEmail = "userEmail_example"; // String | 
        Integer limit = 10; // Integer | 
        Integer page = 1; // Integer | 
        UUID apiApplicationId = UUID.randomUUID(); // UUID | 
        try {
            CompletableFuture<BulkSendListResponse> result = apiInstance.listBulkSends(userEmail, limit, page, apiApplicationId);
            System.out.println(result.get());
        } catch (ApiException e) {
            System.err.println("Exception when calling BulkSendApi#listBulkSends");
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
| **userEmail** | **String**|  | [optional] |
| **limit** | **Integer**|  | [optional] [default to 10] |
| **page** | **Integer**|  | [optional] [default to 1] |
| **apiApplicationId** | **UUID**|  | [optional] |

### Return type

CompletableFuture<[**BulkSendListResponse**](BulkSendListResponse.md)>


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

## listBulkSendsWithHttpInfo

> CompletableFuture<ApiResponse<BulkSendListResponse>> listBulkSendsWithHttpInfo(userEmail, limit, page, apiApplicationId)

List Bulk Sendings

Returns information about the Bulk Send.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.BulkSendApi;
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

        BulkSendApi apiInstance = new BulkSendApi(defaultClient);
        String userEmail = "userEmail_example"; // String | 
        Integer limit = 10; // Integer | 
        Integer page = 1; // Integer | 
        UUID apiApplicationId = UUID.randomUUID(); // UUID | 
        try {
            CompletableFuture<ApiResponse<BulkSendListResponse>> response = apiInstance.listBulkSendsWithHttpInfo(userEmail, limit, page, apiApplicationId);
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
            System.out.println("Response body: " + response.get().getData());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling BulkSendApi#listBulkSends");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling BulkSendApi#listBulkSends");
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
| **userEmail** | **String**|  | [optional] |
| **limit** | **Integer**|  | [optional] [default to 10] |
| **page** | **Integer**|  | [optional] [default to 1] |
| **apiApplicationId** | **UUID**|  | [optional] |

### Return type

CompletableFuture<ApiResponse<[**BulkSendListResponse**](BulkSendListResponse.md)>>


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


## validateBulkSendCsv

> CompletableFuture<BulkSendValidateCsvResponse> validateBulkSendCsv(bulkSendCsvRequest)

Validate Bulk Send CSV

Validates a Bulk Send CSV file before creating the Bulk Send. It will check the structure of the CSV and the data it contains, and return any errors found.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.BulkSendApi;
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

        BulkSendApi apiInstance = new BulkSendApi(defaultClient);
        BulkSendCsvRequest bulkSendCsvRequest = new BulkSendCsvRequest(); // BulkSendCsvRequest | 
        try {
            CompletableFuture<BulkSendValidateCsvResponse> result = apiInstance.validateBulkSendCsv(bulkSendCsvRequest);
            System.out.println(result.get());
        } catch (ApiException e) {
            System.err.println("Exception when calling BulkSendApi#validateBulkSendCsv");
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
| **bulkSendCsvRequest** | [**BulkSendCsvRequest**](BulkSendCsvRequest.md)|  | |

### Return type

CompletableFuture<[**BulkSendValidateCsvResponse**](BulkSendValidateCsvResponse.md)>


### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful |  -  |
| **401** | unauthorized |  -  |
| **422** | unprocessable entity |  -  |
| **429** | rate limit exceeded |  -  |

## validateBulkSendCsvWithHttpInfo

> CompletableFuture<ApiResponse<BulkSendValidateCsvResponse>> validateBulkSendCsvWithHttpInfo(bulkSendCsvRequest)

Validate Bulk Send CSV

Validates a Bulk Send CSV file before creating the Bulk Send. It will check the structure of the CSV and the data it contains, and return any errors found.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.BulkSendApi;
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

        BulkSendApi apiInstance = new BulkSendApi(defaultClient);
        BulkSendCsvRequest bulkSendCsvRequest = new BulkSendCsvRequest(); // BulkSendCsvRequest | 
        try {
            CompletableFuture<ApiResponse<BulkSendValidateCsvResponse>> response = apiInstance.validateBulkSendCsvWithHttpInfo(bulkSendCsvRequest);
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
            System.out.println("Response body: " + response.get().getData());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling BulkSendApi#validateBulkSendCsv");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling BulkSendApi#validateBulkSendCsv");
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
| **bulkSendCsvRequest** | [**BulkSendCsvRequest**](BulkSendCsvRequest.md)|  | |

### Return type

CompletableFuture<ApiResponse<[**BulkSendValidateCsvResponse**](BulkSendValidateCsvResponse.md)>>


### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful |  -  |
| **401** | unauthorized |  -  |
| **422** | unprocessable entity |  -  |
| **429** | rate limit exceeded |  -  |

