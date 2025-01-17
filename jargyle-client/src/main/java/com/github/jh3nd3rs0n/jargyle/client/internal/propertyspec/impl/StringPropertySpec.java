package com.github.jh3nd3rs0n.jargyle.client.internal.propertyspec.impl;

import com.github.jh3nd3rs0n.jargyle.client.Property;
import com.github.jh3nd3rs0n.jargyle.client.PropertySpec;

public final class StringPropertySpec extends PropertySpec<String> {

	public StringPropertySpec(final String n, final String defaultVal) {
		super(n, String.class, defaultVal);
	}

	@Override
	public Property<String> newPropertyWithParsedValue(final String value) {
		return super.newProperty(value);
	}

}
