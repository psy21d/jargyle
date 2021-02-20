package jargyle.net.socks.client;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import jargyle.NetConstants;
import jargyle.TestStringConstants;
import jargyle.net.DatagramSocketIT;
import jargyle.net.ServerSocketIT;
import jargyle.net.SocketIT;
import jargyle.net.socks.server.ConfigurationHelper;

public class SslIT {

/*	
	@org.junit.BeforeClass
	public static void setUp() {
		System.setProperty("javax.net.debug", "ssl,handshake");
	}
	
	@org.junit.AfterClass
	public static void tearDown() {
		System.clearProperty("javax.net.debug");
	}
*/
	
	@Test
	public void testThroughSocks5DatagramSocketUsingSsl01() throws IOException {
		String string = TestStringConstants.STRING_01;
		String returningString = DatagramSocketIT.echoThroughDatagramSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSsl(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSsl());
		assertEquals(string, returningString);
	}

	@Test
	public void testThroughSocks5DatagramSocketUsingSsl02() throws IOException {
		String string = TestStringConstants.STRING_02;
		String returningString = DatagramSocketIT.echoThroughDatagramSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSsl(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSsl());
		assertEquals(string, returningString);
	}

	@Test
	public void testThroughSocks5DatagramSocketUsingSsl03() throws IOException {
		String string = TestStringConstants.STRING_03;
		String returningString = DatagramSocketIT.echoThroughDatagramSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSsl(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSsl());
		assertEquals(string, returningString);
	}
	
	@Test
	public void testThroughSocks5DatagramSocketUsingSslAndClientAuth01() throws IOException {
		String string = TestStringConstants.STRING_01;
		String returningString = DatagramSocketIT.echoThroughDatagramSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSslAndClientAuth(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSslAndRequiredClientAuth());
		assertEquals(string, returningString);
	}
	
	@Test
	public void testThroughSocks5DatagramSocketUsingSslAndRequestedClientAuth01() throws IOException {
		String string = TestStringConstants.STRING_01;
		String returningString = DatagramSocketIT.echoThroughDatagramSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSsl(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSslAndRequestedClientAuth());
		assertEquals(string, returningString);
	}
	
	@Test
	public void testThroughSocks5DatagramSocketUsingSslAndRequestedClientAuth02() throws IOException {
		String string = TestStringConstants.STRING_02;
		String returningString = DatagramSocketIT.echoThroughDatagramSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSsl(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSslAndRequestedClientAuth());
		assertEquals(string, returningString);
	}
	
	@Test
	public void testThroughSocks5DatagramSocketUsingSslAndRequestedClientAuth03() throws IOException {
		String string = TestStringConstants.STRING_03;
		String returningString = DatagramSocketIT.echoThroughDatagramSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSsl(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSslAndRequestedClientAuth());
		assertEquals(string, returningString);
	}
	
	@Test
	public void testThroughSocks5DatagramSocketUsingSslAndRequiredClientAuth02() throws IOException {
		String string = TestStringConstants.STRING_02;
		String returningString = DatagramSocketIT.echoThroughDatagramSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSslAndClientAuth(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSslAndRequiredClientAuth());
		assertEquals(string, returningString);
	}
	
	@Test
	public void testThroughSocks5DatagramSocketUsingSslAndRequiredClientAuth03() throws IOException {
		String string = TestStringConstants.STRING_03;
		String returningString = DatagramSocketIT.echoThroughDatagramSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSslAndClientAuth(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSslAndRequiredClientAuth());
		assertEquals(string, returningString);
	}
	
	@Test
	public void testThroughSocks5ServerSocketUsingSsl01() throws IOException {
		String string = TestStringConstants.STRING_01;
		String returningString = ServerSocketIT.echoThroughServerSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSsl(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSsl());
		assertEquals(string, returningString);
	}
	
	@Test
	public void testThroughSocks5ServerSocketUsingSsl02() throws IOException {
		String string = TestStringConstants.STRING_02;
		String returningString = ServerSocketIT.echoThroughServerSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSsl(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSsl());
		assertEquals(string, returningString);
	}
	
	@Test
	public void testThroughSocks5ServerSocketUsingSsl03() throws IOException {
		String string = TestStringConstants.STRING_03;
		String returningString = ServerSocketIT.echoThroughServerSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSsl(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSsl());
		assertEquals(string, returningString);
	}
	
	@Test
	public void testThroughSocks5ServerSocketUsingSslAndRequestedClientAuth01() throws IOException {
		String string = TestStringConstants.STRING_01;
		String returningString = ServerSocketIT.echoThroughServerSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSsl(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSslAndRequestedClientAuth());
		assertEquals(string, returningString);
	}

	@Test
	public void testThroughSocks5ServerSocketUsingSslAndRequestedClientAuth02() throws IOException {
		String string = TestStringConstants.STRING_02;
		String returningString = ServerSocketIT.echoThroughServerSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSsl(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSslAndRequestedClientAuth());
		assertEquals(string, returningString);
	}

	@Test
	public void testThroughSocks5ServerSocketUsingSslAndRequestedClientAuth03() throws IOException {
		String string = TestStringConstants.STRING_03;
		String returningString = ServerSocketIT.echoThroughServerSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSsl(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSslAndRequestedClientAuth());
		assertEquals(string, returningString);
	}
	
	@Test
	public void testThroughSocks5ServerSocketUsingSslAndRequiredClientAuth01() throws IOException {
		String string = TestStringConstants.STRING_01;
		String returningString = ServerSocketIT.echoThroughServerSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSslAndClientAuth(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSslAndRequiredClientAuth());
		assertEquals(string, returningString);
	}
	
	@Test
	public void testThroughSocks5ServerSocketUsingSslAndRequiredClientAuth02() throws IOException {
		String string = TestStringConstants.STRING_02;
		String returningString = ServerSocketIT.echoThroughServerSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSslAndClientAuth(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSslAndRequiredClientAuth());
		assertEquals(string, returningString);
	}
	
	@Test
	public void testThroughSocks5ServerSocketUsingSslAndRequiredClientAuth03() throws IOException {
		String string = TestStringConstants.STRING_03;
		String returningString = ServerSocketIT.echoThroughServerSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSslAndClientAuth(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSslAndRequiredClientAuth());
		assertEquals(string, returningString);
	}
	
	@Test
	public void testThroughSocks5SocketUsingSsl01() throws IOException {
		String string = TestStringConstants.STRING_01;
		String returningString = SocketIT.echoThroughSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSsl(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSsl());
		assertEquals(string, returningString);
	}
	
	@Test
	public void testThroughSocks5SocketUsingSsl02() throws IOException {
		String string = TestStringConstants.STRING_02;
		String returningString = SocketIT.echoThroughSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSsl(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSsl());
		assertEquals(string, returningString);		
	}
	
	@Test
	public void testThroughSocks5SocketUsingSsl03() throws IOException {
		String string = TestStringConstants.STRING_03;
		String returningString = SocketIT.echoThroughSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSsl(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSsl());
		assertEquals(string, returningString);		
	}
	
	@Test
	public void testThroughSocks5SocketUsingSslAndRequestedClientAuth01() throws IOException {
		String string = TestStringConstants.STRING_01;
		String returningString = SocketIT.echoThroughSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSsl(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSslAndRequestedClientAuth());
		assertEquals(string, returningString);
	}

	@Test
	public void testThroughSocks5SocketUsingSslAndRequestedClientAuth02() throws IOException {
		String string = TestStringConstants.STRING_02;
		String returningString = SocketIT.echoThroughSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSsl(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSslAndRequestedClientAuth());
		assertEquals(string, returningString);		
	}

	@Test
	public void testThroughSocks5SocketUsingSslAndRequestedClientAuth03() throws IOException {
		String string = TestStringConstants.STRING_03;
		String returningString = SocketIT.echoThroughSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSsl(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSslAndRequestedClientAuth());
		assertEquals(string, returningString);		
	}
	
	@Test
	public void testThroughSocks5SocketUsingSslAndRequiredClientAuth01() throws IOException {
		String string = TestStringConstants.STRING_01;
		String returningString = SocketIT.echoThroughSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSslAndClientAuth(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSslAndRequiredClientAuth());
		assertEquals(string, returningString);
	}
	
	@Test
	public void testThroughSocks5SocketUsingSslAndRequiredClientAuth02() throws IOException {
		String string = TestStringConstants.STRING_02;
		String returningString = SocketIT.echoThroughSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSslAndClientAuth(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSslAndRequiredClientAuth());
		assertEquals(string, returningString);		
	}
	
	@Test
	public void testThroughSocks5SocketUsingSslAndRequiredClientAuth03() throws IOException {
		String string = TestStringConstants.STRING_03;
		String returningString = SocketIT.echoThroughSocket(
				string,
				SocksClientHelper.newSocks5ClientUsingSslAndClientAuth(
						NetConstants.LOOPBACK_ADDRESS.getHostAddress(), 
						null),
				ConfigurationHelper.newConfigurationUsingSslAndRequiredClientAuth());
		assertEquals(string, returningString);		
	}
	
}