# TemplateApi

All URIs are relative to *https://www.signwell.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createTemplate**](TemplateApi.md#createTemplate) | **POST** /api/v1/document_templates | Create Template |
| [**createTemplateWithHttpInfo**](TemplateApi.md#createTemplateWithHttpInfo) | **POST** /api/v1/document_templates | Create Template |
| [**deleteTemplate**](TemplateApi.md#deleteTemplate) | **DELETE** /api/v1/document_templates/{id}/ | Delete Template |
| [**deleteTemplateWithHttpInfo**](TemplateApi.md#deleteTemplateWithHttpInfo) | **DELETE** /api/v1/document_templates/{id}/ | Delete Template |
| [**getTemplate**](TemplateApi.md#getTemplate) | **GET** /api/v1/document_templates/{id}/ | Get Template |
| [**getTemplateWithHttpInfo**](TemplateApi.md#getTemplateWithHttpInfo) | **GET** /api/v1/document_templates/{id}/ | Get Template |
| [**listTemplates**](TemplateApi.md#listTemplates) | **GET** /api/v1/document_templates | List Templates |
| [**listTemplatesWithHttpInfo**](TemplateApi.md#listTemplatesWithHttpInfo) | **GET** /api/v1/document_templates | List Templates |
| [**updateTemplate**](TemplateApi.md#updateTemplate) | **PUT** /api/v1/document_templates/{id}/ | Update Template |
| [**updateTemplateWithHttpInfo**](TemplateApi.md#updateTemplateWithHttpInfo) | **PUT** /api/v1/document_templates/{id}/ | Update Template |



## createTemplate

> CompletableFuture<DocumentTemplateResponse> createTemplate(documentTemplateRequest)

Create Template

Creates a new template.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.TemplateApi;
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

        TemplateApi apiInstance = new TemplateApi(defaultClient);
        DocumentTemplateRequest documentTemplateRequest = new DocumentTemplateRequest(); // DocumentTemplateRequest | 
        try {
            CompletableFuture<DocumentTemplateResponse> result = apiInstance.createTemplate(documentTemplateRequest);
            System.out.println(result.get());
        } catch (ApiException e) {
            System.err.println("Exception when calling TemplateApi#createTemplate");
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
| **documentTemplateRequest** | [**DocumentTemplateRequest**](DocumentTemplateRequest.md)|  | |

### Return type

CompletableFuture<[**DocumentTemplateResponse**](DocumentTemplateResponse.md)>


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

## createTemplateWithHttpInfo

> CompletableFuture<ApiResponse<DocumentTemplateResponse>> createTemplateWithHttpInfo(documentTemplateRequest)

Create Template

Creates a new template.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.TemplateApi;
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

        TemplateApi apiInstance = new TemplateApi(defaultClient);
        DocumentTemplateRequest documentTemplateRequest = new DocumentTemplateRequest(); // DocumentTemplateRequest | 
        try {
            CompletableFuture<ApiResponse<DocumentTemplateResponse>> response = apiInstance.createTemplateWithHttpInfo(documentTemplateRequest);
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
            System.out.println("Response body: " + response.get().getData());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling TemplateApi#createTemplate");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling TemplateApi#createTemplate");
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
| **documentTemplateRequest** | [**DocumentTemplateRequest**](DocumentTemplateRequest.md)|  | |

### Return type

CompletableFuture<ApiResponse<[**DocumentTemplateResponse**](DocumentTemplateResponse.md)>>


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


## deleteTemplate

> CompletableFuture<Void> deleteTemplate(id)

Delete Template

Deletes a template. Supply the unique template ID from either a Create Template request or template page URL.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.TemplateApi;
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

        TemplateApi apiInstance = new TemplateApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        try {
            CompletableFuture<Void> result = apiInstance.deleteTemplate(id);
        } catch (ApiException e) {
            System.err.println("Exception when calling TemplateApi#deleteTemplate");
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

## deleteTemplateWithHttpInfo

> CompletableFuture<ApiResponse<Void>> deleteTemplateWithHttpInfo(id)

Delete Template

Deletes a template. Supply the unique template ID from either a Create Template request or template page URL.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.TemplateApi;
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

        TemplateApi apiInstance = new TemplateApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        try {
            CompletableFuture<ApiResponse<Void>> response = apiInstance.deleteTemplateWithHttpInfo(id);
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling TemplateApi#deleteTemplate");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling TemplateApi#deleteTemplate");
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


## getTemplate

> CompletableFuture<DocumentTemplateResponse> getTemplate(id)

Get Template

Returns a template and all associated template data. Supply the unique template ID from either a Create Template request or template page URL.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.TemplateApi;
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

        TemplateApi apiInstance = new TemplateApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        try {
            CompletableFuture<DocumentTemplateResponse> result = apiInstance.getTemplate(id);
            System.out.println(result.get());
        } catch (ApiException e) {
            System.err.println("Exception when calling TemplateApi#getTemplate");
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

CompletableFuture<[**DocumentTemplateResponse**](DocumentTemplateResponse.md)>


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

## getTemplateWithHttpInfo

> CompletableFuture<ApiResponse<DocumentTemplateResponse>> getTemplateWithHttpInfo(id)

Get Template

Returns a template and all associated template data. Supply the unique template ID from either a Create Template request or template page URL.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.TemplateApi;
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

        TemplateApi apiInstance = new TemplateApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        try {
            CompletableFuture<ApiResponse<DocumentTemplateResponse>> response = apiInstance.getTemplateWithHttpInfo(id);
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
            System.out.println("Response body: " + response.get().getData());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling TemplateApi#getTemplate");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling TemplateApi#getTemplate");
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

CompletableFuture<ApiResponse<[**DocumentTemplateResponse**](DocumentTemplateResponse.md)>>


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


## listTemplates

> CompletableFuture<DocumentTemplateListResponse> listTemplates(page, limit, query)

List Templates

Returns a paginated list of templates for the authenticated account.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.TemplateApi;
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

        TemplateApi apiInstance = new TemplateApi(defaultClient);
        Integer page = 1; // Integer | 
        Integer limit = 10; // Integer | 
        String query = "query_example"; // String | Search templates using SignWell key:value syntax.
        try {
            CompletableFuture<DocumentTemplateListResponse> result = apiInstance.listTemplates(page, limit, query);
            System.out.println(result.get());
        } catch (ApiException e) {
            System.err.println("Exception when calling TemplateApi#listTemplates");
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
| **query** | **String**| Search templates using SignWell key:value syntax. | [optional] |

### Return type

CompletableFuture<[**DocumentTemplateListResponse**](DocumentTemplateListResponse.md)>


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

## listTemplatesWithHttpInfo

> CompletableFuture<ApiResponse<DocumentTemplateListResponse>> listTemplatesWithHttpInfo(page, limit, query)

List Templates

Returns a paginated list of templates for the authenticated account.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.TemplateApi;
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

        TemplateApi apiInstance = new TemplateApi(defaultClient);
        Integer page = 1; // Integer | 
        Integer limit = 10; // Integer | 
        String query = "query_example"; // String | Search templates using SignWell key:value syntax.
        try {
            CompletableFuture<ApiResponse<DocumentTemplateListResponse>> response = apiInstance.listTemplatesWithHttpInfo(page, limit, query);
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
            System.out.println("Response body: " + response.get().getData());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling TemplateApi#listTemplates");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling TemplateApi#listTemplates");
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
| **query** | **String**| Search templates using SignWell key:value syntax. | [optional] |

### Return type

CompletableFuture<ApiResponse<[**DocumentTemplateListResponse**](DocumentTemplateListResponse.md)>>


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


## updateTemplate

> CompletableFuture<DocumentTemplateResponse> updateTemplate(id, documentTemplateUpdateRequest)

Update Template

Updates an existing template.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.TemplateApi;
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

        TemplateApi apiInstance = new TemplateApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        DocumentTemplateUpdateRequest documentTemplateUpdateRequest = new DocumentTemplateUpdateRequest(); // DocumentTemplateUpdateRequest | 
        try {
            CompletableFuture<DocumentTemplateResponse> result = apiInstance.updateTemplate(id, documentTemplateUpdateRequest);
            System.out.println(result.get());
        } catch (ApiException e) {
            System.err.println("Exception when calling TemplateApi#updateTemplate");
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
| **documentTemplateUpdateRequest** | [**DocumentTemplateUpdateRequest**](DocumentTemplateUpdateRequest.md)|  | |

### Return type

CompletableFuture<[**DocumentTemplateResponse**](DocumentTemplateResponse.md)>


### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | ok |  -  |
| **400** | bad request |  -  |
| **422** | unprocessable entity |  -  |
| **429** | rate limit exceeded |  -  |

## updateTemplateWithHttpInfo

> CompletableFuture<ApiResponse<DocumentTemplateResponse>> updateTemplateWithHttpInfo(id, documentTemplateUpdateRequest)

Update Template

Updates an existing template.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.TemplateApi;
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

        TemplateApi apiInstance = new TemplateApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        DocumentTemplateUpdateRequest documentTemplateUpdateRequest = new DocumentTemplateUpdateRequest(); // DocumentTemplateUpdateRequest | 
        try {
            CompletableFuture<ApiResponse<DocumentTemplateResponse>> response = apiInstance.updateTemplateWithHttpInfo(id, documentTemplateUpdateRequest);
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
            System.out.println("Response body: " + response.get().getData());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling TemplateApi#updateTemplate");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling TemplateApi#updateTemplate");
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
| **documentTemplateUpdateRequest** | [**DocumentTemplateUpdateRequest**](DocumentTemplateUpdateRequest.md)|  | |

### Return type

CompletableFuture<ApiResponse<[**DocumentTemplateResponse**](DocumentTemplateResponse.md)>>


### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | ok |  -  |
| **400** | bad request |  -  |
| **422** | unprocessable entity |  -  |
| **429** | rate limit exceeded |  -  |

