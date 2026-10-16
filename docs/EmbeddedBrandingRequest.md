

# EmbeddedBrandingRequest

Branding for this document's embedded requesting and signing experiences. Every property is optional and independent. A property you supply overrides the selected API Application; one you omit inherits from it, then from the SignWell default. `company_logo` has one extra step between those two: the account's Settings > Branding logo, which applies on paid plans only. The colors and the border radius have no account step — omitting one falls straight through to the SignWell default. On Update and Send Document the object is a patch: only the properties you send change, and a property sent as `null` clears your override so the value inherits again. Sending `branding` itself as `null` clears every property.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**companyLogo** | [**EmbeddedBrandingRequestCompanyLogo**](EmbeddedBrandingRequestCompanyLogo.md) |  |  [optional] |
|**accentColor** | **String** | Accent color for this document&#39;s embedded pages, as a 6-digit HEX value. Returned normalized to &#x60;#RRGGBB&#x60;. |  [optional] |
|**buttonLabelColor** | **String** | Button label color for this document&#39;s embedded pages, as a 6-digit HEX value. Returned normalized to &#x60;#RRGGBB&#x60;. |  [optional] |
|**linkColor** | **String** | Link color for this document&#39;s embedded pages, as a 6-digit HEX value. Returned normalized to &#x60;#RRGGBB&#x60;. |  [optional] |
|**buttonBorderRadius** | **Integer** | Corner radius of buttons on this document&#39;s embedded pages, in pixels. Must be between 0 and 25. |  [optional] |



