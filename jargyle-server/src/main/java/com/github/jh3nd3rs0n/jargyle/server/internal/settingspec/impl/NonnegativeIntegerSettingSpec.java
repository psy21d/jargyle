package com.github.jh3nd3rs0n.jargyle.server.internal.settingspec.impl;

import com.github.jh3nd3rs0n.jargyle.common.lang.NonnegativeInteger;
import com.github.jh3nd3rs0n.jargyle.server.Setting;
import com.github.jh3nd3rs0n.jargyle.server.SettingSpec;

public final class NonnegativeIntegerSettingSpec 
	extends SettingSpec<NonnegativeInteger> {

	public NonnegativeIntegerSettingSpec(
			final String n, final NonnegativeInteger defaultVal) {
		super(n, NonnegativeInteger.class, defaultVal);
	}

	@Override
	public Setting<NonnegativeInteger> newSettingOfParsableValue(
			final String value) {
		return super.newSetting(NonnegativeInteger.newInstance(value));
	}

}