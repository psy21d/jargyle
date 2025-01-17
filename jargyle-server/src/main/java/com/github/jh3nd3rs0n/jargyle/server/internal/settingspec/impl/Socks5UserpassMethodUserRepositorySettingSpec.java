package com.github.jh3nd3rs0n.jargyle.server.internal.settingspec.impl;

import com.github.jh3nd3rs0n.jargyle.server.Setting;
import com.github.jh3nd3rs0n.jargyle.server.SettingSpec;
import com.github.jh3nd3rs0n.jargyle.server.socks5.userpassmethod.UserRepository;

public final class Socks5UserpassMethodUserRepositorySettingSpec 
	extends SettingSpec<UserRepository> {

	public Socks5UserpassMethodUserRepositorySettingSpec(
			final String n, final UserRepository defaultVal) {
		super(n, UserRepository.class, defaultVal);
	}

	@Override
	public Setting<UserRepository> newSettingWithParsedValue(
			final String value) {
		return super.newSetting(UserRepository.newInstanceFrom(value));
	}

}
