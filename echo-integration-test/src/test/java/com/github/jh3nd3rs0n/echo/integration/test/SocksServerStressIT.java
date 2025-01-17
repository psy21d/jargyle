package com.github.jh3nd3rs0n.echo.integration.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.InetAddress;

import com.github.jh3nd3rs0n.jargyle.common.number.NonNegativeInteger;
import org.junit.Test;

import com.github.jh3nd3rs0n.echo.DatagramEchoClient;
import com.github.jh3nd3rs0n.echo.DatagramEchoServer;
import com.github.jh3nd3rs0n.echo.EchoClient;
import com.github.jh3nd3rs0n.echo.EchoServer;
import com.github.jh3nd3rs0n.jargyle.client.DtlsPropertySpecConstants;
import com.github.jh3nd3rs0n.jargyle.client.NetObjectFactory;
import com.github.jh3nd3rs0n.jargyle.client.Properties;
import com.github.jh3nd3rs0n.jargyle.client.Scheme;
import com.github.jh3nd3rs0n.jargyle.client.Socks5PropertySpecConstants;
import com.github.jh3nd3rs0n.jargyle.client.SocksClient;
import com.github.jh3nd3rs0n.jargyle.client.SslPropertySpecConstants;
import com.github.jh3nd3rs0n.jargyle.common.net.Port;
import com.github.jh3nd3rs0n.jargyle.common.security.EncryptedPassword;
import com.github.jh3nd3rs0n.jargyle.protocolbase.socks5.Method;
import com.github.jh3nd3rs0n.jargyle.protocolbase.socks5.Methods;
import com.github.jh3nd3rs0n.jargyle.server.Configuration;
import com.github.jh3nd3rs0n.jargyle.server.DtlsSettingSpecConstants;
import com.github.jh3nd3rs0n.jargyle.server.GeneralSettingSpecConstants;
import com.github.jh3nd3rs0n.jargyle.server.Settings;
import com.github.jh3nd3rs0n.jargyle.server.Socks5SettingSpecConstants;
import com.github.jh3nd3rs0n.jargyle.server.SocksServer;
import com.github.jh3nd3rs0n.jargyle.server.SslSettingSpecConstants;
import com.github.jh3nd3rs0n.jargyle.server.socks5.userpassmethod.UserRepositorySpecConstants;
import com.github.jh3nd3rs0n.test.help.TestStringConstants;
import com.github.jh3nd3rs0n.test.help.constants.TestResourceConstants;

/*
 * This integration test is ignored due to its success being dependent on the 
 * machine running this integration test. 
 * 
 * To run this integration test only, add the following configuration XML 
 * element within the Maven Failsafe plugin in the file pom.xml 
 * 
 * <plugin>
 *     <groupId>org.apache.maven.plugins</groupId>
 *     <artifactId>maven-failsafe-plugin</artifactId>
 *     <!-- ... -->
 *     <configuration>
 *         <!-- enable virtual threads in Java 19 -->
 *         <!--
 *         <argLine>--enable-preview</argLine>
 *         -->
 *         <includes>
 */
 //            <include>**/*StressIT.java</include>
/* 
 *         </includes>
 *     </configuration>
 *     <!-- ... -->
 * <plugin>
 * 
 * Then comment out the Ignore annotation for the class SocksServerStressIT below.
 * 
 * You can also adjust the constant value of BACKLOG to the desired number of 
 * backlogged inbound TCP connections.  
 * 
 * You can also adjust the constant values of DATAGRAM_ECHO_CLIENT_THREAD_COUNT 
 * and ECHO_CLIENT_THREAD_COUNT to the desired number of threads for testing.
 * 
 */
@org.junit.Ignore
public class SocksServerStressIT {

	private static final int BACKLOG = 100;
	
	private static final int DATAGRAM_ECHO_CLIENT_THREAD_COUNT = 100;
	private static final int ECHO_CLIENT_THREAD_COUNT = 100;
	
	private static final int SOCKS_SERVER_PORT = 6789;
	private static final int SOCKS_SERVER_PORT_USING_SOCKS5_USERPASSMETHOD = 7890;
	private static final int SOCKS_SERVER_PORT_USING_SSL = 8900;
	private static final int SOCKS_SERVER_PORT_USING_SSL_AND_SOCKS5_USERPASSMETHOD = 9000;
	private static final String SOCKS5_USERS = new StringBuilder()
			.append("Aladdin:opensesame ")
			.append("Jasmine:mission%3Aimpossible ")
			.append("Abu:safeDriversSave40%25")
			.toString();
	
	private static Configuration newConfiguration() {
		return Configuration.newUnmodifiableInstance(Settings.of(
				GeneralSettingSpecConstants.PORT.newSetting(
						Port.valueOf(SOCKS_SERVER_PORT)),
				GeneralSettingSpecConstants.BACKLOG.newSetting(
						NonNegativeInteger.valueOf(BACKLOG))));
	}
	
	private static Configuration newConfigurationUsingSocks5UserpassMethod() {
		return Configuration.newUnmodifiableInstance(Settings.of(
				GeneralSettingSpecConstants.PORT.newSetting(
						Port.valueOf(SOCKS_SERVER_PORT_USING_SOCKS5_USERPASSMETHOD)),
				GeneralSettingSpecConstants.BACKLOG.newSetting(
						NonNegativeInteger.valueOf(BACKLOG)),
				Socks5SettingSpecConstants.SOCKS5_METHODS.newSetting(
						Methods.of(Method.USERNAME_PASSWORD)),
				Socks5SettingSpecConstants.SOCKS5_USERPASSMETHOD_USER_REPOSITORY.newSetting(
						UserRepositorySpecConstants.STRING_SOURCE_USER_REPOSITORY.newUserRepository(
								SOCKS5_USERS))));
	}
	
	private static Configuration newConfigurationUsingSsl() {
		return Configuration.newUnmodifiableInstance(Settings.of(
				GeneralSettingSpecConstants.PORT.newSetting(
						Port.valueOf(SOCKS_SERVER_PORT_USING_SSL)),
				GeneralSettingSpecConstants.BACKLOG.newSetting(
						NonNegativeInteger.valueOf(BACKLOG)),
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
	
	private static Configuration newConfigurationUsingSslAndSocks5UserpassMethod() {
		return Configuration.newUnmodifiableInstance(Settings.of(
				GeneralSettingSpecConstants.PORT.newSetting(
						Port.valueOf(SOCKS_SERVER_PORT_USING_SSL_AND_SOCKS5_USERPASSMETHOD)),
				GeneralSettingSpecConstants.BACKLOG.newSetting(
						NonNegativeInteger.valueOf(BACKLOG)),
				DtlsSettingSpecConstants.DTLS_ENABLED.newSetting(Boolean.TRUE),
				DtlsSettingSpecConstants.DTLS_KEY_STORE_FILE.newSetting(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_FILE.getFile()),
				DtlsSettingSpecConstants.DTLS_KEY_STORE_PASSWORD.newSettingWithParsedValue(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_PASSWORD_FILE.getContentAsString()),
				SslSettingSpecConstants.SSL_ENABLED.newSetting(Boolean.TRUE),
				SslSettingSpecConstants.SSL_KEY_STORE_FILE.newSetting(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_FILE.getFile()),
				SslSettingSpecConstants.SSL_KEY_STORE_PASSWORD.newSettingWithParsedValue(
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_PASSWORD_FILE.getContentAsString()),
				Socks5SettingSpecConstants.SOCKS5_METHODS.newSetting(
						Methods.of(Method.USERNAME_PASSWORD)),
				Socks5SettingSpecConstants.SOCKS5_USERPASSMETHOD_USER_REPOSITORY.newSetting(
						UserRepositorySpecConstants.STRING_SOURCE_USER_REPOSITORY.newUserRepository(
								SOCKS5_USERS))));
	}
	
	private static SocksClient newSocks5Client() {
		return Scheme.SOCKS5.newSocksServerUri(
				InetAddress.getLoopbackAddress().getHostAddress(), 
				Integer.valueOf(SOCKS_SERVER_PORT))
				.newSocksClient(Properties.of());
	}
	
	private static SocksClient newSocks5ClientUsingSocks5UserpassMethod(
			final String username,
			final char[] password) {
		Properties properties = Properties.of(
				Socks5PropertySpecConstants.SOCKS5_METHODS.newProperty(
						Methods.of(Method.USERNAME_PASSWORD)),
				Socks5PropertySpecConstants.SOCKS5_USERPASSMETHOD_USERNAME.newProperty(
						username),
				Socks5PropertySpecConstants.SOCKS5_USERPASSMETHOD_PASSWORD.newProperty(
						EncryptedPassword.newInstance(password)));
		return Scheme.SOCKS5.newSocksServerUri(
				InetAddress.getLoopbackAddress().getHostAddress(), 
				Integer.valueOf(SOCKS_SERVER_PORT_USING_SOCKS5_USERPASSMETHOD))
				.newSocksClient(properties);		
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
				Integer.valueOf(SOCKS_SERVER_PORT_USING_SSL))
				.newSocksClient(properties);
	}
	
	private static SocksClient newSocks5ClientUsingSslAndSocks5UserpassMethod(
			final String username,
			final char[] password) {
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
						TestResourceConstants.ECHO_INTEGRATION_TEST_SOCKS_SERVER_KEY_STORE_PASSWORD_FILE.getContentAsString()),
				Socks5PropertySpecConstants.SOCKS5_METHODS.newProperty(
						Methods.of(Method.USERNAME_PASSWORD)),
				Socks5PropertySpecConstants.SOCKS5_USERPASSMETHOD_USERNAME.newProperty(
						username),
				Socks5PropertySpecConstants.SOCKS5_USERPASSMETHOD_PASSWORD.newProperty(
						EncryptedPassword.newInstance(password)));
		return Scheme.SOCKS5.newSocksServerUri(
				InetAddress.getLoopbackAddress().getHostAddress(), 
				Integer.valueOf(SOCKS_SERVER_PORT_USING_SSL_AND_SOCKS5_USERPASSMETHOD))
				.newSocksClient(properties);		
	}
	
	//@org.junit.Ignore
	@Test
	public void testDatagramEchoClientsBehindSocks5Server() throws IOException {
		System.out.printf("Running %s%n", Thread.currentThread().getStackTrace()[1].getMethodName());
		SocksServer socksServer = new SocksServer(newConfiguration());
		DatagramEchoServer datagramEchoServer = new DatagramEchoServer();
		try {
			socksServer.start();			
			datagramEchoServer.start();
			IoStressor ioStressor = new IoStressor(DATAGRAM_ECHO_CLIENT_THREAD_COUNT);			
			IoStressor.Stats stats = ioStressor.executeForEachThread(() -> {
				DatagramEchoClient datagramEchoClient = new DatagramEchoClient(
						newSocks5Client().newSocksNetObjectFactory()); 
				datagramEchoClient.echo(TestStringConstants.STRING_01);
			});
			stats.print();
			assertEquals(DATAGRAM_ECHO_CLIENT_THREAD_COUNT, stats.getActualSuccessfulThreadCount());
		} finally {
			if (!datagramEchoServer.getState().equals(DatagramEchoServer.State.STOPPED)) {
				datagramEchoServer.stop();
			}
			if (!socksServer.getState().equals(SocksServer.State.STOPPED)) {
				socksServer.stop();
			}
		}
	}	
	
	//@org.junit.Ignore
	@Test
	public void testDatagramEchoClientsBehindSocks5ServerUsingSocks5UserpassMethod() throws IOException {
		System.out.printf("Running %s%n", Thread.currentThread().getStackTrace()[1].getMethodName());
		SocksServer socksServerUsingSocks5UserpassMethod = new SocksServer(
				newConfigurationUsingSocks5UserpassMethod());
		DatagramEchoServer datagramEchoServer = new DatagramEchoServer();
		try {
			socksServerUsingSocks5UserpassMethod.start();			
			datagramEchoServer.start();			
			IoStressor ioStressor = new IoStressor(DATAGRAM_ECHO_CLIENT_THREAD_COUNT);
			IoStressor.Stats stats = ioStressor.executeForEachThread(() -> {
				DatagramEchoClient datagramEchoClient = new DatagramEchoClient(
						newSocks5ClientUsingSocks5UserpassMethod(
								"Aladdin",
								"opensesame".toCharArray()).newSocksNetObjectFactory()); 
				datagramEchoClient.echo(TestStringConstants.STRING_01);
			});
			stats.print();
			assertEquals(DATAGRAM_ECHO_CLIENT_THREAD_COUNT, stats.getActualSuccessfulThreadCount());			
		} finally {
			if (!datagramEchoServer.getState().equals(DatagramEchoServer.State.STOPPED)) {
				datagramEchoServer.stop();
			}
			if (!socksServerUsingSocks5UserpassMethod.getState().equals(SocksServer.State.STOPPED)) {
				socksServerUsingSocks5UserpassMethod.stop();
			}
		}
	}
	
	//@org.junit.Ignore
	@Test
	public void testDatagramEchoClientsBehindSocks5ServerUsingSsl() throws IOException {
		System.out.printf("Running %s%n", Thread.currentThread().getStackTrace()[1].getMethodName());
		SocksServer socksServerUsingSsl = new SocksServer(
				newConfigurationUsingSsl());
		DatagramEchoServer datagramEchoServer = new DatagramEchoServer();
		try {
			socksServerUsingSsl.start();			
			datagramEchoServer.start();
			IoStressor ioStressor = new IoStressor(DATAGRAM_ECHO_CLIENT_THREAD_COUNT);			
			IoStressor.Stats stats = ioStressor.executeForEachThread(() -> {
				DatagramEchoClient datagramEchoClient = new DatagramEchoClient(
						newSocks5ClientUsingSsl().newSocksNetObjectFactory()); 
				datagramEchoClient.echo(TestStringConstants.STRING_01);
			});
			stats.print();
			assertEquals(DATAGRAM_ECHO_CLIENT_THREAD_COUNT, stats.getActualSuccessfulThreadCount());			
		} finally {
			if (!datagramEchoServer.getState().equals(DatagramEchoServer.State.STOPPED)) {
				datagramEchoServer.stop();
			}
			if (!socksServerUsingSsl.getState().equals(SocksServer.State.STOPPED)) {
				socksServerUsingSsl.stop();
			}
		}
	}
	
	//@org.junit.Ignore
	@Test
	public void testDatagramEchoClientsBehindSocks5ServerUsingSslAndSocks5UserpassMethod() throws IOException {
		System.out.printf("Running %s%n", Thread.currentThread().getStackTrace()[1].getMethodName());
		SocksServer socksServerUsingSslAndSocks5UserpassMethod = new SocksServer(
				newConfigurationUsingSslAndSocks5UserpassMethod());
		DatagramEchoServer datagramEchoServer = new DatagramEchoServer();
		try {
			socksServerUsingSslAndSocks5UserpassMethod.start();
			datagramEchoServer.start();
			IoStressor ioStressor = new IoStressor(DATAGRAM_ECHO_CLIENT_THREAD_COUNT);
			IoStressor.Stats stats = ioStressor.executeForEachThread(() -> {
				DatagramEchoClient datagramEchoClient = new DatagramEchoClient(
						newSocks5ClientUsingSslAndSocks5UserpassMethod(
								"Aladdin",
								"opensesame".toCharArray()).newSocksNetObjectFactory()); 
				datagramEchoClient.echo(TestStringConstants.STRING_01);
			});
			stats.print();
			assertEquals(DATAGRAM_ECHO_CLIENT_THREAD_COUNT, stats.getActualSuccessfulThreadCount());			
		} finally {
			if (!datagramEchoServer.getState().equals(DatagramEchoServer.State.STOPPED)) {
				datagramEchoServer.stop();
			}
			if (!socksServerUsingSslAndSocks5UserpassMethod.getState().equals(SocksServer.State.STOPPED)) {
				socksServerUsingSslAndSocks5UserpassMethod.stop();
			}
		}
		
	}

	//@org.junit.Ignore
	@Test
	public void testEchoClientsBehindSocks5Server() throws IOException {
		System.out.printf("Running %s%n", Thread.currentThread().getStackTrace()[1].getMethodName());
		SocksServer socksServer = new SocksServer(newConfiguration());
		EchoServer echoServer = new EchoServer(
				NetObjectFactory.getDefault(), 
				EchoServer.PORT, 
				BACKLOG,
				EchoServer.INET_ADDRESS,
				EchoServer.SOCKET_SETTINGS);
		try {
			socksServer.start();			
			echoServer.start();			
			IoStressor ioStressor = new IoStressor(ECHO_CLIENT_THREAD_COUNT);			
			IoStressor.Stats stats = ioStressor.executeForEachThread(() -> {
				EchoClient echoClient = new EchoClient(
						newSocks5Client().newSocksNetObjectFactory()); 
				echoClient.echo(TestStringConstants.STRING_01);
			});
			stats.print();
			assertEquals(ECHO_CLIENT_THREAD_COUNT, stats.getActualSuccessfulThreadCount());			
		} finally {
			if (!echoServer.getState().equals(EchoServer.State.STOPPED)) {
				echoServer.stop();
			}
			if (!socksServer.getState().equals(SocksServer.State.STOPPED)) {
				socksServer.stop();
			}
		}
	}	
	
	//@org.junit.Ignore
	@Test
	public void testEchoClientsBehindSocks5ServerUsingSocks5UserpassMethod() throws IOException {
		System.out.printf("Running %s%n", Thread.currentThread().getStackTrace()[1].getMethodName());
		SocksServer socksServerUsingSocks5UserpassMethod = new SocksServer(
				newConfigurationUsingSocks5UserpassMethod());
		EchoServer echoServer = new EchoServer(
				NetObjectFactory.getDefault(), 
				EchoServer.PORT, 
				BACKLOG,
				EchoServer.INET_ADDRESS,
				EchoServer.SOCKET_SETTINGS);
		try {
			socksServerUsingSocks5UserpassMethod.start();
			echoServer.start();			
			IoStressor ioStressor = new IoStressor(ECHO_CLIENT_THREAD_COUNT);			
			IoStressor.Stats stats = ioStressor.executeForEachThread(() -> {
				EchoClient echoClient = new EchoClient(
						newSocks5ClientUsingSocks5UserpassMethod(
								"Aladdin",
								"opensesame".toCharArray()).newSocksNetObjectFactory()); 
				echoClient.echo(TestStringConstants.STRING_01);
			});
			stats.print();
			assertEquals(ECHO_CLIENT_THREAD_COUNT, stats.getActualSuccessfulThreadCount());			
		} finally {
			if (!echoServer.getState().equals(EchoServer.State.STOPPED)) {
				echoServer.stop();
			}
			if (!socksServerUsingSocks5UserpassMethod.getState().equals(SocksServer.State.STOPPED)) {
				socksServerUsingSocks5UserpassMethod.stop();
			}
		}
	}
	
	//@org.junit.Ignore
	@Test
	public void testEchoClientsBehindSocks5ServerUsingSsl() throws IOException {
		System.out.printf("Running %s%n", Thread.currentThread().getStackTrace()[1].getMethodName());
		SocksServer socksServerUsingSsl = new SocksServer(
				newConfigurationUsingSsl());
		EchoServer echoServer = new EchoServer(
				NetObjectFactory.getDefault(), 
				EchoServer.PORT, 
				BACKLOG,
				EchoServer.INET_ADDRESS,
				EchoServer.SOCKET_SETTINGS);
		try {
			socksServerUsingSsl.start();
			echoServer.start();			
			IoStressor ioStressor = new IoStressor(ECHO_CLIENT_THREAD_COUNT);			
			IoStressor.Stats stats = ioStressor.executeForEachThread(() -> {
				EchoClient echoClient = new EchoClient(
						newSocks5ClientUsingSsl().newSocksNetObjectFactory()); 
				echoClient.echo(TestStringConstants.STRING_01);
			});
			stats.print();
			assertEquals(ECHO_CLIENT_THREAD_COUNT, stats.getActualSuccessfulThreadCount());			
		} finally {
			if (!echoServer.getState().equals(EchoServer.State.STOPPED)) {
				echoServer.stop();
			}
			if (!socksServerUsingSsl.getState().equals(SocksServer.State.STOPPED)) {
				socksServerUsingSsl.stop();
			}
		}
	}
	
	//@org.junit.Ignore
	@Test
	public void testEchoClientsBehindSocks5ServerUsingSslAndSocks5UserpassMethod() throws IOException {
		System.out.printf("Running %s%n", Thread.currentThread().getStackTrace()[1].getMethodName());
		SocksServer socksServerUsingSslAndSocks5UserpassMethod = new SocksServer(
				newConfigurationUsingSslAndSocks5UserpassMethod());
		EchoServer echoServer = new EchoServer(
				NetObjectFactory.getDefault(), 
				EchoServer.PORT, 
				BACKLOG,
				EchoServer.INET_ADDRESS,
				EchoServer.SOCKET_SETTINGS);
		try {
			socksServerUsingSslAndSocks5UserpassMethod.start();
			echoServer.start();			
			IoStressor ioStressor = new IoStressor(ECHO_CLIENT_THREAD_COUNT);			
			IoStressor.Stats stats = ioStressor.executeForEachThread(() -> {
				EchoClient echoClient = new EchoClient(
						newSocks5ClientUsingSslAndSocks5UserpassMethod(
								"Aladdin",
								"opensesame".toCharArray()).newSocksNetObjectFactory()); 
				echoClient.echo(TestStringConstants.STRING_01);
			});
			stats.print();
			assertEquals(ECHO_CLIENT_THREAD_COUNT, stats.getActualSuccessfulThreadCount());			
		} finally {
			if (!echoServer.getState().equals(EchoServer.State.STOPPED)) {
				echoServer.stop();
			}
			if (!socksServerUsingSslAndSocks5UserpassMethod.getState().equals(SocksServer.State.STOPPED)) {
				socksServerUsingSslAndSocks5UserpassMethod.stop();
			}
		}
	}
	
}
