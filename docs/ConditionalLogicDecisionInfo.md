

# ConditionalLogicDecisionInfo

Audit record of a signer-side decision about a CL rule that fired against a completed field.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**ruleId** | **String** | External ID of the conditional rule that fired |  |
|**fieldApiId** | **String** | api_id of the field the decision applies to |  |
|**decisionType** | [**DecisionTypeEnum**](#DecisionTypeEnum) | Whether the signer kept the existing signature or the value was cleared |  |
|**decidedAt** | **OffsetDateTime** | When the decision was recorded |  |
|**description** | **String** | Human-readable summary of the decision |  [optional] |



## Enum: DecisionTypeEnum

| Name | Value |
|---- | -----|
| KEPT_SIGNATURE | &quot;kept_signature&quot; |
| CLEARED_VALUE | &quot;cleared_value&quot; |
| UNKNOWN_DEFAULT_OPEN_API | &quot;unknown_default_open_api&quot; |



