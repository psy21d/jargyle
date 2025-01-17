package com.github.jh3nd3rs0n.echo.integration.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.InetAddress;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.jh3nd3rs0n.echo.DatagramEchoClient;
import com.github.jh3nd3rs0n.echo.DatagramEchoServer;
import com.github.jh3nd3rs0n.echo.EchoClient;
import com.github.jh3nd3rs0n.echo.EchoServer;
import com.github.jh3nd3rs0n.jargyle.client.DtlsPropertySpecConstants;
import com.github.jh3nd3rs0n.jargyle.client.Properties;
import com.github.jh3nd3rs0n.jargyle.client.Scheme;
import com.github.jh3nd3rs0n.jargyle.client.SocksClient;
import com.github.jh3nd3rs0n.jargyle.client.SslPropertySpecConstants;
import com.github.jh3nd3rs0n.jargyle.common.net.Port;
import com.github.jh3nd3rs0n.jargyle.server.Configuration;
import com.github.jh3nd3rs0n.jargyle.server.DtlsSettingSpecConstants;
import com.github.jh3nd3rs0n.jargyle.server.GeneralSettingSpecConstants;
import com.github.jh3nd3rs0n.jargyle.server.Settings;
import com.github.jh3nd3rs0n.jargyle.server.SocksServer;
import com.github.jh3nd3rs0n.jargyle.server.SslSettingSpecConstants;
import com.github.jh3nd3rs0n.test.help.TestStringConstants;
import com.github.jh3nd3rs0n.test.help.ThreadHelper;
import com.github.jh3nd3rs0n.test.help.constants.TestResourceConstants;

public class EchoThroughSocksServerUsingSslIT {
	
	private static final int SOCKS_SERVER_PORT_USING_SSL = 9100;
	private static final int SOCKS_SERVER_PORT_USING_SSL_AND_REQUESTED_CLIENT_AUTH = 9200;
	private static final int SOCKS_SERVER_PORT_USING_SSL_AND_REQUIRED_CLIENT_AUTH = 9300;
	
	private static DatagramEchoServer datagramEchoServer;
	private static EchoServer echoServer;
	
	private static SocksServer socksServerUsingSsl;
	private static SocksServer socksServerUsingSslAndRequestedClientAuth;
	private static SocksServer socksServerUsingSslAndRequiredClientAuth;
	
	private static Configuration newConfigurationUsingSsl() {
		return Configuration.newUnmodifiableInstance(Settings.of(
				GeneralSettingSpecConstants.PORT.newSetting(
						Port.valueOf(SOCKS_SERVER_PORT_USING_SSL)),
				DtlsSettingSpecConstants.DTLS_ENABLED.newSetting(Boolean.TRUE),
				DtlsSettingSpecConstants.DTLS_KEY_STORE_FILE.newSetting(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_FILE.getFile()),
				DtlsSettingSpecConstants.DTLS_KEY_STORE_PASSWORD.newSettingWithParsedValue(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_PASSWORD_FILE.getContentAsString()),
				SslSettingSpecConstants.SSL_ENABLED.newSetting(Boolean.TRUE),
				SslSettingSpecConstants.SSL_KEY_STORE_FILE.newSetting(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_FILE.getFile()),
				SslSettingSpecConstants.SSL_KEY_STORE_PASSWORD.newSettingWithParsedValue(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_PASSWORD_FILE.getContentAsString())));
	}
	
	private static Configuration newConfigurationUsingSslAndRequestedClientAuth() {
		return Configuration.newUnmodifiableInstance(Settings.of(
				GeneralSettingSpecConstants.PORT.newSetting(
						Port.valueOf(
								SOCKS_SERVER_PORT_USING_SSL_AND_REQUESTED_CLIENT_AUTH)),
				DtlsSettingSpecConstants.DTLS_ENABLED.newSetting(Boolean.TRUE),
				DtlsSettingSpecConstants.DTLS_KEY_STORE_FILE.newSetting(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_FILE.getFile()),
				DtlsSettingSpecConstants.DTLS_KEY_STORE_PASSWORD.newSettingWithParsedValue(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_PASSWORD_FILE.getContentAsString()),
				DtlsSettingSpecConstants.DTLS_TRUST_STORE_FILE.newSetting(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_CLIENT_KEY_STORE_FILE.getFile()),
				DtlsSettingSpecConstants.DTLS_TRUST_STORE_PASSWORD.newSettingWithParsedValue(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_CLIENT_KEY_STORE_PASSWORD_FILE.getContentAsString()),
				DtlsSettingSpecConstants.DTLS_WANT_CLIENT_AUTH.newSetting(
						Boolean.TRUE),				
				SslSettingSpecConstants.SSL_ENABLED.newSetting(Boolean.TRUE),
				SslSettingSpecConstants.SSL_KEY_STORE_FILE.newSetting(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_FILE.getFile()),
				SslSettingSpecConstants.SSL_KEY_STORE_PASSWORD.newSettingWithParsedValue(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_PASSWORD_FILE.getContentAsString()),
				SslSettingSpecConstants.SSL_TRUST_STORE_FILE.newSetting(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_CLIENT_KEY_STORE_FILE.getFile()),
				SslSettingSpecConstants.SSL_TRUST_STORE_PASSWORD.newSettingWithParsedValue(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_CLIENT_KEY_STORE_PASSWORD_FILE.getContentAsString()),
				SslSettingSpecConstants.SSL_WANT_CLIENT_AUTH.newSetting(
						Boolean.TRUE)));
	}
	
	private static Configuration newConfigurationUsingSslAndRequiredClientAuth() {
		return Configuration.newUnmodifiableInstance(Settings.of(
				GeneralSettingSpecConstants.PORT.newSetting(
						Port.valueOf(
								SOCKS_SERVER_PORT_USING_SSL_AND_REQUIRED_CLIENT_AUTH)),
				DtlsSettingSpecConstants.DTLS_ENABLED.newSetting(Boolean.TRUE),
				DtlsSettingSpecConstants.DTLS_KEY_STORE_FILE.newSetting(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_FILE.getFile()),
				DtlsSettingSpecConstants.DTLS_KEY_STORE_PASSWORD.newSettingWithParsedValue(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_PASSWORD_FILE.getContentAsString()),
				DtlsSettingSpecConstants.DTLS_NEED_CLIENT_AUTH.newSetting(
						Boolean.TRUE),
				DtlsSettingSpecConstants.DTLS_TRUST_STORE_FILE.newSetting(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_CLIENT_KEY_STORE_FILE.getFile()),
				DtlsSettingSpecConstants.DTLS_TRUST_STORE_PASSWORD.newSettingWithParsedValue(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_CLIENT_KEY_STORE_PASSWORD_FILE.getContentAsString()),
				SslSettingSpecConstants.SSL_ENABLED.newSetting(Boolean.TRUE),
				SslSettingSpecConstants.SSL_KEY_STORE_FILE.newSetting(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_FILE.getFile()),
				SslSettingSpecConstants.SSL_KEY_STORE_PASSWORD.newSettingWithParsedValue(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_PASSWORD_FILE.getContentAsString()),
				SslSettingSpecConstants.SSL_NEED_CLIENT_AUTH.newSetting(
						Boolean.TRUE),
				SslSettingSpecConstants.SSL_TRUST_STORE_FILE.newSetting(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_CLIENT_KEY_STORE_FILE.getFile()),
				SslSettingSpecConstants.SSL_TRUST_STORE_PASSWORD.newSettingWithParsedValue(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_CLIENT_KEY_STORE_PASSWORD_FILE.getContentAsString())));
	}

	private static SocksClient newSocks5ClientUsingSsl() {
		Properties properties = Properties.of(
				DtlsPropertySpecConstants.DTLS_ENABLED.newProperty(
						Boolean.TRUE),
				DtlsPropertySpecConstants.DTLS_TRUST_STORE_FILE.newProperty(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_FILE.getFile()),
				DtlsPropertySpecConstants.DTLS_TRUST_STORE_PASSWORD.newPropertyWithParsedValue(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_PASSWORD_FILE.getContentAsString()),
				SslPropertySpecConstants.SSL_ENABLED.newProperty(
						Boolean.TRUE),
				SslPropertySpecConstants.SSL_TRUST_STORE_FILE.newProperty(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_FILE.getFile()),
				SslPropertySpecConstants.SSL_TRUST_STORE_PASSWORD.newPropertyWithParsedValue(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_PASSWORD_FILE.getContentAsString()));
		return Scheme.SOCKS5.newSocksServerUri(
				InetAddress.getLoopbackAddress().getHostAddress(), 
				SOCKS_SERVER_PORT_USING_SSL)
				.newSocksClient(properties);
	}

	private static SocksClient newSocks5ClientUsingSslAndRequestedClientAuth() {
		Properties properties = Properties.of(
				DtlsPropertySpecConstants.DTLS_ENABLED.newProperty(
						Boolean.TRUE),
				DtlsPropertySpecConstants.DTLS_KEY_STORE_FILE.newProperty(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_CLIENT_KEY_STORE_FILE.getFile()),
				DtlsPropertySpecConstants.DTLS_KEY_STORE_PASSWORD.newPropertyWithParsedValue(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_CLIENT_KEY_STORE_PASSWORD_FILE.getContentAsString()),
				DtlsPropertySpecConstants.DTLS_TRUST_STORE_FILE.newProperty(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_FILE.getFile()),
				DtlsPropertySpecConstants.DTLS_TRUST_STORE_PASSWORD.newPropertyWithParsedValue(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_PASSWORD_FILE.getContentAsString()),
				SslPropertySpecConstants.SSL_ENABLED.newProperty(Boolean.TRUE),
				SslPropertySpecConstants.SSL_KEY_STORE_FILE.newProperty(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_CLIENT_KEY_STORE_FILE.getFile()),
				SslPropertySpecConstants.SSL_KEY_STORE_PASSWORD.newPropertyWithParsedValue(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_CLIENT_KEY_STORE_PASSWORD_FILE.getContentAsString()),
				SslPropertySpecConstants.SSL_TRUST_STORE_FILE.newProperty(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_FILE.getFile()),
				SslPropertySpecConstants.SSL_TRUST_STORE_PASSWORD.newPropertyWithParsedValue(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_PASSWORD_FILE.getContentAsString()));
		return Scheme.SOCKS5.newSocksServerUri(
				InetAddress.getLoopbackAddress().getHostAddress(), 
				SOCKS_SERVER_PORT_USING_SSL_AND_REQUESTED_CLIENT_AUTH)
				.newSocksClient(properties);
	}
	
	private static SocksClient newSocks5ClientUsingSslAndRequiredClientAuth() {
		Properties properties = Properties.of(
				DtlsPropertySpecConstants.DTLS_ENABLED.newProperty(
						Boolean.TRUE),
				DtlsPropertySpecConstants.DTLS_KEY_STORE_FILE.newProperty(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_CLIENT_KEY_STORE_FILE.getFile()),
				DtlsPropertySpecConstants.DTLS_KEY_STORE_PASSWORD.newPropertyWithParsedValue(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_CLIENT_KEY_STORE_PASSWORD_FILE.getContentAsString()),
				DtlsPropertySpecConstants.DTLS_TRUST_STORE_FILE.newProperty(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_FILE.getFile()),
				DtlsPropertySpecConstants.DTLS_TRUST_STORE_PASSWORD.newPropertyWithParsedValue(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_PASSWORD_FILE.getContentAsString()),
				SslPropertySpecConstants.SSL_ENABLED.newProperty(Boolean.TRUE),
				SslPropertySpecConstants.SSL_KEY_STORE_FILE.newProperty(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_CLIENT_KEY_STORE_FILE.getFile()),
				SslPropertySpecConstants.SSL_KEY_STORE_PASSWORD.newPropertyWithParsedValue(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_CLIENT_KEY_STORE_PASSWORD_FILE.getContentAsString()),
				SslPropertySpecConstants.SSL_TRUST_STORE_FILE.newProperty(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_FILE.getFile()),
				SslPropertySpecConstants.SSL_TRUST_STORE_PASSWORD.newPropertyWithParsedValue(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_PASSWORD_FILE.getContentAsString()));
		return Scheme.SOCKS5.newSocksServerUri(
				InetAddress.getLoopbackAddress().getHostAddress(), 
				SOCKS_SERVER_PORT_USING_SSL_AND_REQUIRED_CLIENT_AUTH)
				.newSocksClient(properties);
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		// System.setProperty("javax.net.debug", "ssl,handshake");
		datagramEchoServer = new DatagramEchoServer();
		datagramEchoServer.start();
		echoServer = new EchoServer();
		echoServer.start();
		socksServerUsingSsl = new SocksServer(newConfigurationUsingSsl());
		socksServerUsingSsl.start();
		socksServerUsingSslAndRequestedClientAuth = new SocksServer(
				newConfigurationUsingSslAndRequestedClientAuth());
		socksServerUsingSslAndRequestedClientAuth.start();
		socksServerUsingSslAndRequiredClientAuth = new SocksServer(
				newConfigurationUsingSslAndRequiredClientAuth());
		socksServerUsingSslAndRequiredClientAuth.start();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws IOException {
		// System.clearProperty("javax.net.debug");
		if (!datagramEchoServer.getState().equals(DatagramEchoServer.State.STOPPED)) {
			datagramEchoServer.stop();
		}
		if (!echoServer.getState().equals(EchoServer.State.STOPPED)) {
			echoServer.stop();
		}
		if (!socksServerUsingSsl.getState().equals(SocksServer.State.STOPPED)) {
			socksServerUsingSsl.stop();
		}
		if (!socksServerUsingSslAndRequestedClientAuth.getState().equals(SocksServer.State.STOPPED)) {
			socksServerUsingSslAndRequestedClientAuth.stop();
		}
		if (!socksServerUsingSslAndRequiredClientAuth.getState().equals(SocksServer.State.STOPPED)) {
			socksServerUsingSslAndRequiredClientAuth.stop();
		}
		ThreadHelper.sleepForThreeSeconds();
	}
	
	@Test
	public void testDatagramEchoClientBehindSocks5ServerUsingSsl01() throws IOException {
		DatagramEchoClient datagramEchoClient = new DatagramEchoClient(
				newSocks5ClientUsingSsl().newSocksNetObjectFactory()); 
		String string = TestStringConstants.STRING_01;
		String returningString = datagramEchoClient.echo(string);
		assertEquals(string, returningString);
	}
	
	@Test
	public void testDatagramEchoClientBehindSocks5ServerUsingSsl02() throws IOException {
		DatagramEchoClient datagramEchoClient = new DatagramEchoClient(
				newSocks5ClientUsingSsl().newSocksNetObjectFactory()); 
		String string = TestStringConstants.STRING_02;
		String returningString = datagramEchoClient.echo(string);
		assertEquals(string, returningString);
	}
	
	@Test
	public void testDatagramEchoClientBehindSocks5ServerUsingSsl03() throws IOException {
		DatagramEchoClient datagramEchoClient = new DatagramEchoClient(
				newSocks5ClientUsingSsl().newSocksNetObjectFactory()); 
		String string = TestStringConstants.STRING_03;
		String returningString = datagramEchoClient.echo(string);
		assertEquals(string, returningString);
	}
	
	@Test
	public void testDatagramEchoClientBehindSocks5ServerUsingSslAndRequestedClientAuth01() throws IOException {
		DatagramEchoClient datagramEchoClient = new DatagramEchoClient(
				newSocks5ClientUsingSslAndRequestedClientAuth().newSocksNetObjectFactory()); 
		String string = TestStringConstants.STRING_01;
		String returningString = datagramEchoClient.echo(string);
		assertEquals(string, returningString);
	}
	
	@Test
	public void testDatagramEchoClientBehindSocks5ServerUsingSslAndRequestedClientAuth02() throws IOException {
		DatagramEchoClient datagramEchoClient = new DatagramEchoClient(
				newSocks5ClientUsingSslAndRequestedClientAuth().newSocksNetObjectFactory()); 
		String string = TestStringConstants.STRING_02;
		String returningString = datagramEchoClient.echo(string);
		assertEquals(string, returningString);
	}
	
	@Test
	public void testDatagramEchoClientBehindSocks5ServerUsingSslAndRequestedClientAuth03() throws IOException {
		DatagramEchoClient datagramEchoClient = new DatagramEchoClient(
				newSocks5ClientUsingSslAndRequestedClientAuth().newSocksNetObjectFactory()); 
		String string = TestStringConstants.STRING_03;
		String returningString = datagramEchoClient.echo(string);
		assertEquals(string, returningString);
	}
	
	@Test
	public void testDatagramEchoClientBehindSocks5ServerUsingSslAndRequiredClientAuth01() throws IOException {
		DatagramEchoClient datagramEchoClient = new DatagramEchoClient(
				newSocks5ClientUsingSslAndRequiredClientAuth().newSocksNetObjectFactory()); 
		String string = TestStringConstants.STRING_01;
		String returningString = datagramEchoClient.echo(string);
		assertEquals(string, returningString);
	}
	
	@Test
	public void testDatagramEchoClientBehindSocks5ServerUsingSslAndRequiredClientAuth02() throws IOException {
		DatagramEchoClient datagramEchoClient = new DatagramEchoClient(
				newSocks5ClientUsingSslAndRequiredClientAuth().newSocksNetObjectFactory()); 
		String string = TestStringConstants.STRING_02;
		String returningString = datagramEchoClient.echo(string);
		assertEquals(string, returningString);
	}

	@Test
	public void testDatagramEchoClientBehindSocks5ServerUsingSslAndRequiredClientAuth03() throws IOException {
		DatagramEchoClient datagramEchoClient = new DatagramEchoClient(
				newSocks5ClientUsingSslAndRequiredClientAuth().newSocksNetObjectFactory()); 
		String string = TestStringConstants.STRING_03;
		String returningString = datagramEchoClient.echo(string);
		assertEquals(string, returningString);
	}
	
	@Test
	public void testEchoClientBehindSocks5ServerUsingSsl01() throws IOException {
		EchoClient echoClient = new EchoClient(
				newSocks5ClientUsingSsl().newSocksNetObjectFactory()); 
		String string = TestStringConstants.STRING_01;
		String returningString = echoClient.echo(string);
		assertEquals(string, returningString);
	}
	
	@Test
	public void testEchoClientBehindSocks5ServerUsingSsl02() throws IOException {
		EchoClient echoClient = new EchoClient(
				newSocks5ClientUsingSsl().newSocksNetObjectFactory()); 
		String string = TestStringConstants.STRING_02;
		String returningString = echoClient.echo(string);
		assertEquals(string, returningString);		
	}
	
	@Test
	public void testEchoClientBehindSocks5ServerUsingSsl03() throws IOException {
		EchoClient echoClient = new EchoClient(
				newSocks5ClientUsingSsl().newSocksNetObjectFactory()); 
		String string = TestStringConstants.STRING_03;
		String returningString = echoClient.echo(string);
		assertEquals(string, returningString);		
	}
	
	@Test
	public void testEchoClientBehindSocks5ServerUsingSslAndRequestedClientAuth01() throws IOException {
		EchoClient echoClient = new EchoClient(
				newSocks5ClientUsingSslAndRequestedClientAuth().newSocksNetObjectFactory()); 
		String string = TestStringConstants.STRING_01;
		String returningString = echoClient.echo(string);
		assertEquals(string, returningString);
	}
	
	@Test
	public void testEchoClientBehindSocks5ServerUsingSslAndRequestedClientAuth02() throws IOException {
		EchoClient echoClient = new EchoClient(
				newSocks5ClientUsingSslAndRequestedClientAuth().newSocksNetObjectFactory()); 
		String string = TestStringConstants.STRING_02;
		String returningString = echoClient.echo(string);
		assertEquals(string, returningString);		
	}
	
	@Test
	public void testEchoClientBehindSocks5ServerUsingSslAndRequestedClientAuth03() throws IOException {
		EchoClient echoClient = new EchoClient(
				newSocks5ClientUsingSslAndRequestedClientAuth().newSocksNetObjectFactory()); 
		String string = TestStringConstants.STRING_03;
		String returningString = echoClient.echo(string);
		assertEquals(string, returningString);		
	}
	
	@Test
	public void testEchoClientBehindSocks5ServerUsingSslAndRequiredClientAuth01() throws IOException {
		EchoClient echoClient = new EchoClient(
				newSocks5ClientUsingSslAndRequiredClientAuth().newSocksNetObjectFactory()); 
		String string = TestStringConstants.STRING_01;
		String returningString = echoClient.echo(string);
		assertEquals(string, returningString);
	}
	
	@Test
	public void testEchoClientBehindSocks5ServerUsingSslAndRequiredClientAuth02() throws IOException {
		EchoClient echoClient = new EchoClient(
				newSocks5ClientUsingSslAndRequiredClientAuth().newSocksNetObjectFactory()); 
		String string = TestStringConstants.STRING_02;
		String returningString = echoClient.echo(string);
		assertEquals(string, returningString);		
	}

	@Test
	public void testEchoClientBehindSocks5ServerUsingSslAndRequiredClientAuth03() throws IOException {
		EchoClient echoClient = new EchoClient(
				newSocks5ClientUsingSslAndRequiredClientAuth().newSocksNetObjectFactory()); 
		String string = TestStringConstants.STRING_03;
		String returningString = echoClient.echo(string);
		assertEquals(string, returningString);		
	}

	@Test
	public void testEchoServerBehindSocks5ServerUsingSsl01() throws IOException {
		EchoServer echServer = new EchoServer(
				newSocks5ClientUsingSsl().newSocksNetObjectFactory(), 0); 
		String string = TestStringConstants.STRING_01;
		EchoServerHelper.startThenExecuteThenStop(echServer, () -> {
			String returningString = new EchoClient().echo(
					string, echServer.getInetAddress(), echServer.getPort());
			assertEquals(string, returningString);
		});
	}
	
	@Test
	public void testEchoServerBehindSocks5ServerUsingSsl02() throws IOException {
		EchoServer echServer = new EchoServer(
				newSocks5ClientUsingSsl().newSocksNetObjectFactory(), 0); 
		String string = TestStringConstants.STRING_02;
		EchoServerHelper.startThenExecuteThenStop(echServer, () -> {
			String returningString = new EchoClient().echo(
					string, echServer.getInetAddress(), echServer.getPort());
			assertEquals(string, returningString);
		});
	}
	
	@Test
	public void testEchoServerBehindSocks5ServerUsingSsl03() throws IOException {
		EchoServer echServer = new EchoServer(
				newSocks5ClientUsingSsl().newSocksNetObjectFactory(), 0); 
		String string = TestStringConstants.STRING_03;
		EchoServerHelper.startThenExecuteThenStop(echServer, () -> {
			String returningString = new EchoClient().echo(
					string, echServer.getInetAddress(), echServer.getPort());
			assertEquals(string, returningString);
		});
	}
	
	@Test
	public void testEchoServerBehindSocks5ServerUsingSslAndRequestedClientAuth01() throws IOException {
		EchoServer echServer = new EchoServer(
				newSocks5ClientUsingSslAndRequestedClientAuth().newSocksNetObjectFactory(), 0); 
		String string = TestStringConstants.STRING_01;
		EchoServerHelper.startThenExecuteThenStop(echServer, () -> {
			String returningString = new EchoClient().echo(
					string, echServer.getInetAddress(), echServer.getPort());
			assertEquals(string, returningString);
		});
	}

	@Test
	public void testEchoServerBehindSocks5ServerUsingSslAndRequestedClientAuth02() throws IOException {
		EchoServer echServer = new EchoServer(
				newSocks5ClientUsingSslAndRequestedClientAuth().newSocksNetObjectFactory(), 0); 
		String string = TestStringConstants.STRING_02;
		EchoServerHelper.startThenExecuteThenStop(echServer, () -> {
			String returningString = new EchoClient().echo(
					string, echServer.getInetAddress(), echServer.getPort());
			assertEquals(string, returningString);
		});
	}

	@Test
	public void testEchoServerBehindSocks5ServerUsingSslAndRequestedClientAuth03() throws IOException {
		EchoServer echServer = new EchoServer(
				newSocks5ClientUsingSslAndRequestedClientAuth().newSocksNetObjectFactory(), 0); 
		String string = TestStringConstants.STRING_03;
		EchoServerHelper.startThenExecuteThenStop(echServer, () -> {
			String returningString = new EchoClient().echo(
					string, echServer.getInetAddress(), echServer.getPort());
			assertEquals(string, returningString);
		});
	}

	@Test
	public void testEchoServerBehindSocks5ServerUsingSslAndRequiredClientAuth01() throws IOException {
		EchoServer echServer = new EchoServer(
				newSocks5ClientUsingSslAndRequiredClientAuth().newSocksNetObjectFactory(), 0); 
		String string = TestStringConstants.STRING_01;
		EchoServerHelper.startThenExecuteThenStop(echServer, () -> {
			String returningString = new EchoClient().echo(
					string, echServer.getInetAddress(), echServer.getPort());
			assertEquals(string, returningString);
		});
	}

	@Test
	public void testEchoServerBehindSocks5ServerUsingSslAndRequiredClientAuth02() throws IOException {
		EchoServer echServer = new EchoServer(
				newSocks5ClientUsingSslAndRequiredClientAuth().newSocksNetObjectFactory(), 0); 
		String string = TestStringConstants.STRING_02;
		EchoServerHelper.startThenExecuteThenStop(echServer, () -> {
			String returningString = new EchoClient().echo(
					string, echServer.getInetAddress(), echServer.getPort());
			assertEquals(string, returningString);
		});
	}

	@Test
	public void testEchoServerBehindSocks5ServerUsingSslAndRequiredClientAuth03() throws IOException {
		EchoServer echServer = new EchoServer(
				newSocks5ClientUsingSslAndRequiredClientAuth().newSocksNetObjectFactory(), 0); 
		String string = TestStringConstants.STRING_03;
		EchoServerHelper.startThenExecuteThenStop(echServer, () -> {
			String returningString = new EchoClient().echo(
					string, echServer.getInetAddress(), echServer.getPort());
			assertEquals(string, returningString);
		});
	}
	
}
