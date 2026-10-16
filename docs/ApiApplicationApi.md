# ApiApplicationApi

All URIs are relative to *https://www.signwell.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**deleteApiApplication**](ApiApplicationApi.md#deleteApiApplication) | **DELETE** /api/v1/api_applications/{id}/ | Delete API Application |
| [**deleteApiApplicationWithHttpInfo**](ApiApplicationApi.md#deleteApiApplicationWithHttpInfo) | **DELETE** /api/v1/api_applications/{id}/ | Delete API Application |
| [**getApiApplication**](ApiApplicationApi.md#getApiApplication) | **GET** /api/v1/api_applications/{id}/ | Get API Application |
| [**getApiApplicationWithHttpInfo**](ApiApplicationApi.md#getApiApplicationWithHttpInfo) | **GET** /api/v1/api_applications/{id}/ | Get API Application |



## deleteApiApplication

> CompletableFuture<Void> deleteApiApplication(id)

Delete API Application

Deletes an API Application from an account. Supply the unique Application ID from either the Create API Application response or the API Application edit page

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.ApiApplicationApi;
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

        ApiApplicationApi apiInstance = new ApiApplicationApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        try {
            CompletableFuture<Void> result = apiInstance.deleteApiApplication(id);
        } catch (ApiException e) {
            System.err.println("Exception when calling ApiApplicationApi#deleteApiApplication");
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

## deleteApiApplicationWithHttpInfo

> CompletableFuture<ApiResponse<Void>> deleteApiApplicationWithHttpInfo(id)

Delete API Application

Deletes an API Application from an account. Supply the unique Application ID from either the Create API Application response or the API Application edit page

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.ApiApplicationApi;
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

        ApiApplicationApi apiInstance = new ApiApplicationApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        try {
            CompletableFuture<ApiResponse<Void>> response = apiInstance.deleteApiApplicationWithHttpInfo(id);
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling ApiApplicationApi#deleteApiApplication");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling ApiApplicationApi#deleteApiApplication");
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


## getApiApplication

> CompletableFuture<ApiApplicationResponse> getApiApplication(id)

Get API Application

Gets the details of a specific API Application within an account. Supply the unique Application ID from either the Create API Application response or the API Application edit page.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.ApiApplicationApi;
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

        ApiApplicationApi apiInstance = new ApiApplicationApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        try {
            CompletableFuture<ApiApplicationResponse> result = apiInstance.getApiApplication(id);
            System.out.println(result.get());
        } catch (ApiException e) {
            System.err.println("Exception when calling ApiApplicationApi#getApiApplication");
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

CompletableFuture<[**ApiApplicationResponse**](ApiApplicationResponse.md)>


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

## getApiApplicationWithHttpInfo

> CompletableFuture<ApiResponse<ApiApplicationResponse>> getApiApplicationWithHttpInfo(id)

Get API Application

Gets the details of a specific API Application within an account. Supply the unique Application ID from either the Create API Application response or the API Application edit page.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.ApiApplicationApi;
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

        ApiApplicationApi apiInstance = new ApiApplicationApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        try {
            CompletableFuture<ApiResponse<ApiApplicationResponse>> response = apiInstance.getApiApplicationWithHttpInfo(id);
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
            System.out.println("Response body: " + response.get().getData());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling ApiApplicationApi#getApiApplication");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling ApiApplicationApi#getApiApplication");
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

CompletableFuture<ApiResponse<[**ApiApplicationResponse**](ApiApplicationResponse.md)>>


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

