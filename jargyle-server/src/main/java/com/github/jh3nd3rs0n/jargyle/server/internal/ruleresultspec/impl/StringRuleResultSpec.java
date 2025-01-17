package com.github.jh3nd3rs0n.jargyle.server.internal.ruleresultspec.impl;

import com.github.jh3nd3rs0n.jargyle.server.RuleResult;
import com.github.jh3nd3rs0n.jargyle.server.RuleResultSpec;

public final class StringRuleResultSpec extends RuleResultSpec<String> {

	public StringRuleResultSpec(final String n) {
		super(n, String.class);
	}

	@Override
	public RuleResult<String> newRuleResultWithParsedValue(final String value) {
		return super.newRuleResult(value);
	}

}
