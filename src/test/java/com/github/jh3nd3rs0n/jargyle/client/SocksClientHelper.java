package com.github.jh3nd3rs0n.jargyle.client;

import java.net.InetAddress;

import com.github.jh3nd3rs0n.jargyle.common.security.EncryptedPassword;
import com.github.jh3nd3rs0n.jargyle.transport.socks5.Method;
import com.github.jh3nd3rs0n.jargyle.transport.socks5.Methods;

public final class SocksClientHelper {

	public static SocksClient newSocks5Client(final int port) {
		return Scheme.SOCKS5.newSocksServerUri(
				InetAddress.getLoopbackAddress().getHostAddress(), 
				Integer.valueOf(port))
				.newSocksClient(Properties.newInstance());
	}
	
	public static SocksClient newSocks5ClientUsingSocks5Userpassauth(
			final int port, 
			final String username,
			final char[] password) {
		Properties properties = Properties.newInstance(
				Socks5PropertySpecConstants.SOCKS5_METHODS.newProperty(
						Methods.newInstance(Method.USERNAME_PASSWORD)),
				Socks5PropertySpecConstants.SOCKS5_USERPASSAUTH_USERNAME.newProperty(
						username),
				Socks5PropertySpecConstants.SOCKS5_USERPASSAUTH_PASSWORD.newProperty(
						EncryptedPassword.newInstance(password)));
		return Scheme.SOCKS5.newSocksServerUri(
				InetAddress.getLoopbackAddress().getHostAddress(), 
				Integer.valueOf(port))
				.newSocksClient(properties);		
	}
	
	private SocksClientHelper() { }
	
}
