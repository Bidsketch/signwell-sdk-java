# WebhooksApi

All URIs are relative to *https://www.signwell.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createWebhook**](WebhooksApi.md#createWebhook) | **POST** /api/v1/hooks | Create Webhook |
| [**createWebhookWithHttpInfo**](WebhooksApi.md#createWebhookWithHttpInfo) | **POST** /api/v1/hooks | Create Webhook |
| [**deleteWebhook**](WebhooksApi.md#deleteWebhook) | **DELETE** /api/v1/hooks/{id}/ | Delete Webhook |
| [**deleteWebhookWithHttpInfo**](WebhooksApi.md#deleteWebhookWithHttpInfo) | **DELETE** /api/v1/hooks/{id}/ | Delete Webhook |
| [**listWebhooks**](WebhooksApi.md#listWebhooks) | **GET** /api/v1/hooks | List Webhooks |
| [**listWebhooksWithHttpInfo**](WebhooksApi.md#listWebhooksWithHttpInfo) | **GET** /api/v1/hooks | List Webhooks |



## createWebhook

> CompletableFuture<WebhookResponse> createWebhook(createWebhookRequest)

Create Webhook

Register a callback URL that we will post document events to. Note: a &#x60;document_signed&#x60; event is not emitted solely because a pre-signed template signature/initials was copied as document content, and &#x60;event.related_signer&#x60; only identifies a real recipient signing action. Two additional event types cover SMS delivery outcomes: &#x60;document_sms_failed&#x60; fires when an SMS reaches a terminal failure (undelivered or failed), and &#x60;document_sms_opted_out&#x60; fires when a phone number is newly added to the SMS suppression list. Both carry &#x60;event.related_recipient&#x60; (id, name, email when present, &#x60;phone_number_masked&#x60;, and &#x60;delivery_method&#x60;) instead of &#x60;event.related_signer&#x60;, and each recipient in &#x60;data.object.recipients[]&#x60; whose delivery method includes SMS carries a nested &#x60;sms&#x60; object (&#x60;phone_number_masked&#x60;, &#x60;send_sms&#x60;, &#x60;send_sms_delay&#x60;, &#x60;delivery_status&#x60;, &#x60;message_type&#x60;, &#x60;failed&#x60;, &#x60;failed_details&#x60;). Email-only recipients keep their existing payload shape unchanged. Only webhooks registered on API-type hooks receive these two events.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.WebhooksApi;
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

        WebhooksApi apiInstance = new WebhooksApi(defaultClient);
        CreateWebhookRequest createWebhookRequest = new CreateWebhookRequest(); // CreateWebhookRequest | 
        try {
            CompletableFuture<WebhookResponse> result = apiInstance.createWebhook(createWebhookRequest);
            System.out.println(result.get());
        } catch (ApiException e) {
            System.err.println("Exception when calling WebhooksApi#createWebhook");
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
| **createWebhookRequest** | [**CreateWebhookRequest**](CreateWebhookRequest.md)|  | |

### Return type

CompletableFuture<[**WebhookResponse**](WebhookResponse.md)>


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
| **429** | rate limit exceeded |  -  |

## createWebhookWithHttpInfo

> CompletableFuture<ApiResponse<WebhookResponse>> createWebhookWithHttpInfo(createWebhookRequest)

Create Webhook

Register a callback URL that we will post document events to. Note: a &#x60;document_signed&#x60; event is not emitted solely because a pre-signed template signature/initials was copied as document content, and &#x60;event.related_signer&#x60; only identifies a real recipient signing action. Two additional event types cover SMS delivery outcomes: &#x60;document_sms_failed&#x60; fires when an SMS reaches a terminal failure (undelivered or failed), and &#x60;document_sms_opted_out&#x60; fires when a phone number is newly added to the SMS suppression list. Both carry &#x60;event.related_recipient&#x60; (id, name, email when present, &#x60;phone_number_masked&#x60;, and &#x60;delivery_method&#x60;) instead of &#x60;event.related_signer&#x60;, and each recipient in &#x60;data.object.recipients[]&#x60; whose delivery method includes SMS carries a nested &#x60;sms&#x60; object (&#x60;phone_number_masked&#x60;, &#x60;send_sms&#x60;, &#x60;send_sms_delay&#x60;, &#x60;delivery_status&#x60;, &#x60;message_type&#x60;, &#x60;failed&#x60;, &#x60;failed_details&#x60;). Email-only recipients keep their existing payload shape unchanged. Only webhooks registered on API-type hooks receive these two events.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.WebhooksApi;
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

        WebhooksApi apiInstance = new WebhooksApi(defaultClient);
        CreateWebhookRequest createWebhookRequest = new CreateWebhookRequest(); // CreateWebhookRequest | 
        try {
            CompletableFuture<ApiResponse<WebhookResponse>> response = apiInstance.createWebhookWithHttpInfo(createWebhookRequest);
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
            System.out.println("Response body: " + response.get().getData());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling WebhooksApi#createWebhook");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling WebhooksApi#createWebhook");
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
| **createWebhookRequest** | [**CreateWebhookRequest**](CreateWebhookRequest.md)|  | |

### Return type

CompletableFuture<ApiResponse<[**WebhookResponse**](WebhookResponse.md)>>


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
| **429** | rate limit exceeded |  -  |


## deleteWebhook

> CompletableFuture<Void> deleteWebhook(id)

Delete Webhook

Deletes a registered callback URL that we are posting document events to.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.WebhooksApi;
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

        WebhooksApi apiInstance = new WebhooksApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        try {
            CompletableFuture<Void> result = apiInstance.deleteWebhook(id);
        } catch (ApiException e) {
            System.err.println("Exception when calling WebhooksApi#deleteWebhook");
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

## deleteWebhookWithHttpInfo

> CompletableFuture<ApiResponse<Void>> deleteWebhookWithHttpInfo(id)

Delete Webhook

Deletes a registered callback URL that we are posting document events to.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.WebhooksApi;
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

        WebhooksApi apiInstance = new WebhooksApi(defaultClient);
        UUID id = UUID.randomUUID(); // UUID | 
        try {
            CompletableFuture<ApiResponse<Void>> response = apiInstance.deleteWebhookWithHttpInfo(id);
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling WebhooksApi#deleteWebhook");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling WebhooksApi#deleteWebhook");
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


## listWebhooks

> CompletableFuture<List<WebhookResponse>> listWebhooks()

List Webhooks

List all the webhooks in the account.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.WebhooksApi;
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

        WebhooksApi apiInstance = new WebhooksApi(defaultClient);
        try {
            CompletableFuture<List<WebhookResponse>> result = apiInstance.listWebhooks();
            System.out.println(result.get());
        } catch (ApiException e) {
            System.err.println("Exception when calling WebhooksApi#listWebhooks");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters

This endpoint does not need any parameter.

### Return type

CompletableFuture<[**List&lt;WebhookResponse&gt;**](WebhookResponse.md)>


### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | ok |  -  |
| **429** | rate limit exceeded |  -  |

## listWebhooksWithHttpInfo

> CompletableFuture<ApiResponse<List<WebhookResponse>>> listWebhooksWithHttpInfo()

List Webhooks

List all the webhooks in the account.

### Example

```java
// Import classes:
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.Configuration;
import com.signwell.sdk.raw.auth.*;
import com.signwell.sdk.raw.models.*;
import com.signwell.sdk.raw.WebhooksApi;
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

        WebhooksApi apiInstance = new WebhooksApi(defaultClient);
        try {
            CompletableFuture<ApiResponse<List<WebhookResponse>>> response = apiInstance.listWebhooksWithHttpInfo();
            System.out.println("Status code: " + response.get().getStatusCode());
            System.out.println("Response headers: " + response.get().getHeaders());
            System.out.println("Response body: " + response.get().getData());
        } catch (InterruptedException | ExecutionException e) {
            ApiException apiException = (ApiException)e.getCause();
            System.err.println("Exception when calling WebhooksApi#listWebhooks");
            System.err.println("Status code: " + apiException.getCode());
            System.err.println("Response headers: " + apiException.getResponseHeaders());
            System.err.println("Reason: " + apiException.getResponseBody());
            e.printStackTrace();
        } catch (ApiException e) {
            System.err.println("Exception when calling WebhooksApi#listWebhooks");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Response headers: " + e.getResponseHeaders());
            System.err.println("Reason: " + e.getResponseBody());
            e.printStackTrace();
        }
    }
}
```

### Parameters

This endpoint does not need any parameter.

### Return type

CompletableFuture<ApiResponse<[**List&lt;WebhookResponse&gt;**](WebhookResponse.md)>>


### Authorization

[api_key](../README.md#api_key)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | ok |  -  |
| **429** | rate limit exceeded |  -  |

