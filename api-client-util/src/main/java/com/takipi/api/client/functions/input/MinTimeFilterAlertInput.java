package com.takipi.api.client.functions.input;

import com.takipi.integrations.functions.annotations.Function;
import com.takipi.integrations.functions.annotations.Function.FunctionType;

@Function(name="minTimeFilterAlert", type=FunctionType.Variable,
	description = "",example="", image="", isInternal=true)
public class MinTimeFilterAlertInput extends ViewInput {
	public String text;
	public String minRange;
}
