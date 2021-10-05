package com.github.jh3nd3rs0n.jargyle.client.propertyspec.impl;

import com.github.jh3nd3rs0n.jargyle.client.Property;
import com.github.jh3nd3rs0n.jargyle.client.PropertySpec;
import com.github.jh3nd3rs0n.jargyle.client.socks5.userpassauth.UsernamePassword;

public final class UsernamePropertySpec extends PropertySpec<String> {

	private static String getValidatedUsername(final String s) {
		UsernamePassword.validateUsername(s);
		return s;
	}
	
	public UsernamePropertySpec(
			final Object permission, 
			final String s, 
			final String defaultVal) {
		super(permission, s, String.class, getValidatedUsername(defaultVal));
	}

	@Override
	public Property<String> newProperty(final String value) {
		return super.newProperty(getValidatedUsername(value));
	}

	@Override
	public Property<String> newPropertyOfParsableValue(final String value) {
		return super.newProperty(getValidatedUsername(value));
	}

}