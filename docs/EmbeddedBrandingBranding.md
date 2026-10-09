

# EmbeddedBrandingBranding

Only the properties actually set on the document. Every one is optional: an unset property is absent rather than null, because null is how a request clears one.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**companyLogo** | [**CompanyLogoEnum**](#CompanyLogoEnum) | Write-only. Reports only that a logo was set, never its URL or Base64 payload. |  [optional] |
|**accentColor** | **String** | Hex, as #RRGGBB. |  [optional] |
|**buttonLabelColor** | **String** | Hex, as #RRGGBB. |  [optional] |
|**linkColor** | **String** | Hex, as #RRGGBB. |  [optional] |
|**buttonBorderRadius** | **Integer** | Pixels, 0 to 25. |  [optional] |



## Enum: CompanyLogoEnum

| Name | Value |
|---- | -----|
| PROVIDED | &quot;provided&quot; |
| UNKNOWN_DEFAULT_OPEN_API | &quot;unknown_default_open_api&quot; |



