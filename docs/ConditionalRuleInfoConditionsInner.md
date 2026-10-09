

# ConditionalRuleInfoConditionsInner


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**conditionType** | [**ConditionTypeEnum**](#ConditionTypeEnum) | How field_api_ids combine within this condition |  [optional] |
|**status** | [**StatusEnum**](#StatusEnum) | Field state that satisfies this condition |  [optional] |
|**joinOperator** | [**JoinOperatorEnum**](#JoinOperatorEnum) | How this condition joins with the next one in the rule |  [optional] |
|**fieldApiIds** | **List&lt;String&gt;** | api_ids of fields evaluated by this condition |  [optional] |
|**position** | **Integer** | Order of this condition within the rule |  [optional] |



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



