

# ConditionalRuleInfo

Conditional rule configuration

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | Conditional rule ID |  |
|**targetId** | **String** | api_id of the field that owns the rule, or the external_id of a checkbox group when the rule is owned by a group (groups have no api_id) |  |
|**actionType** | [**ActionTypeEnum**](#ActionTypeEnum) | Action applied to action_field_ids when the conditions are met |  |
|**actionFieldIds** | **List&lt;String&gt;** | api_ids of fields the rule affects when triggered |  [optional] |
|**conditions** | [**List&lt;ConditionalRuleInfoConditionsInner&gt;**](ConditionalRuleInfoConditionsInner.md) |  |  [optional] |



## Enum: ActionTypeEnum

| Name | Value |
|---- | -----|
| SHOW | &quot;show&quot; |
| HIDE | &quot;hide&quot; |
| REQUIRE | &quot;require&quot; |
| UNKNOWN_DEFAULT_OPEN_API | &quot;unknown_default_open_api&quot; |



