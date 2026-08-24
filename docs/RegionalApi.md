# RegionalApi

All URIs are relative to *https://www.signwell.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getNom151Certificate**](RegionalApi.md#getNom151Certificate) | **GET** /api/v1/documents/{id}/nom151_certificate/ | MX – NOM-151 Certificate |
| [**getNom151CertificateWithHttpInfo**](RegionalApi.md#getNom151CertificateWithHttpInfo) | **GET** /api/v1/documents/{id}/nom151_certificate/ | MX – NOM-151 Certificate |



## getNom151Certificate

> CompletableFuture<Nom151UrlResponse> getNom151Certificate(id, urlOnly, objectOnly)

MX – NOM-151 Certificate

Download NOM-151 certificate for a completed document. Returns a ZIP file, download URL, or raw certificate data based on query parameters.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.RegionalApi;
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

        RegionalApi apiInstance = new RegionalApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        Boolean urlOnly = false; // Boolean | If true, returns JSON with download URL instead of downloading the file
        Boolean objectOnly = false; // Boolean | 
        try {
            CompletableFuture<Nom151UrlResponse> result = apiInstance.getNom151Certificate(id, urlOnly, objectOnly);
            System.out.println(result.get());
        } catch (ApiException e) {
            System.err.println("Exception when calling RegionalApi#getNom151Certificate");
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
| **urlOnly** | **Boolean**| If true, returns JSON with download URL instead of downloading the file | [optional] [default to false] |
| **objectOnly** | **Boolean**|  | [optional] [default to false] |

### Return type

CompletableFuture<[**Nom151UrlResponse**](Nom151UrlResponse.md)>


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
| **422** | unprocessable_entity |  -  |
| **429** | rate limit exceeded |  -  |

## getNom151CertificateWithHttpInfo

> CompletableFuture<ApiResponse<Nom151UrlResponse>> getNom151Certificate getNom151CertificateWithHttpInfo(id, urlOnly, objectOnly)

MX – NOM-151 Certificate

Download NOM-151 certificate for a completed document. Returns a ZIP file, download URL, or raw certificate data based on query parameters.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.RegionalApi;
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

        RegionalApi apiInstance = new RegionalApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        Boolean urlOnly = false; // Boolean | If true, returns JSON with download URL instead of downloading the file
        Boolean objectOnly = false; // Boolean | 
        try {
            CompletableFuture<ApiResponse<Nom151UrlResponse>> response = apiInstance.getNom151CertificateWithHttpInfo(id, urlOnly, objectOnly);
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
            System.out.println("Response body: " + response.get().getData());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling RegionalApi#getNom151Certificate");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling RegionalApi#getNom151Certificate");
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
| **urlOnly** | **Boolean**| If true, returns JSON with download URL instead of downloading the file | [optional] [default to false] |
| **objectOnly** | **Boolean**|  | [optional] [default to false] |

### Return type

CompletableFuture<ApiResponse<[**Nom151UrlResponse**](Nom151UrlResponse.md)>>


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
| **422** | unprocessable_entity |  -  |
| **429** | rate limit exceeded |  -  |

