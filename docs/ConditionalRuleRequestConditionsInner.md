

# ConditionalRuleRequestConditionsInner


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**conditionType** | [**ConditionTypeEnum**](#ConditionTypeEnum) | How the field_api_ids combine within a condition: &#x60;any_of&#x60;, &#x60;all_of&#x60;, &#x60;none_of&#x60;, or &#x60;not_all_of&#x60;. |  |
|**status** | [**StatusEnum**](#StatusEnum) | The field state the condition tests for: &#x60;completed&#x60; or &#x60;not_completed&#x60;. |  |
|**joinOperator** | [**JoinOperatorEnum**](#JoinOperatorEnum) | How this condition joins the next one in the rule: &#x60;and_operator&#x60; or &#x60;or_operator&#x60;. The first condition&#39;s value is ignored. |  [optional] |
|**fieldApiIds** | **List&lt;String&gt;** | api_ids of the trigger fields the condition watches. |  |
|**position** | **Integer** | 1-indexed order of the condition within the rule. |  [optional] |



## Enum: ConditionTypeEnum

| Name | Value |
|---- | -----|
| ANY_OF | &quot;any_of&quot; |
| ALL_OF | &quot;all_of&quot; |
| NONE_OF | &quot;none_of&quot; |
| NOT_ALL_OF | &quot;not_all_of&quot; |
| UNKNOWN_DEFAULT_OPEN_API | &quot;unknown_default_open_api&quot; |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| COMPLETED | &quot;completed&quot; |
| NOT_COMPLETED | &quot;not_completed&quot; |
| UNKNOWN_DEFAULT_OPEN_API | &quot;unknown_default_open_api&quot; |



## Enum: JoinOperatorEnum

| Name | Value |
|---- | -----|
| AND_OPERATOR | &quot;and_operator&quot; |
| OR_OPERATOR | &quot;or_operator&quot; |
| UNKNOWN_DEFAULT_OPEN_API | &quot;unknown_default_open_api&quot; |



