package com.github.jh3nd3rs0n.jargyle.server;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import com.github.jh3nd3rs0n.jargyle.client.NetObjectFactory;
import com.github.jh3nd3rs0n.jargyle.common.net.SocketSettings;
import com.github.jh3nd3rs0n.jargyle.common.net.ssl.DtlsDatagramSocketFactory;
import com.github.jh3nd3rs0n.jargyle.internal.net.ssl.SslSocketFactory;

final class WorkerContextFactory {

	private DtlsDatagramSocketFactory clientFacingDtlsDatagramSocketFactory;
	private SslSocketFactory clientFacingSslSocketFactory;
	private final Configuration configuration;
	private Configuration lastConfiguration;	
	private NetObjectFactory netObjectFactory;
	
	public WorkerContextFactory(final Configuration config) {
		this.clientFacingDtlsDatagramSocketFactory = null;
		this.clientFacingSslSocketFactory = null;
		this.configuration = config;
		this.lastConfiguration = null;		
		this.netObjectFactory = null;
	}

	public Rule anyClientRuleApplicableTo(
			final String sourceAddress, final String destinationAddress) {
		Configuration config = this.newConfiguration();
		Settings settings = config.getSettings();
		Rules clientRules = settings.getLastValue(
				GeneralSettingSpecConstants.CLIENT_RULES);
		return clientRules.anyAppliesTo(sourceAddress, destinationAddress);
	}
	
	private void configureClientFacingSocket(
			final Socket clientFacingSock,
			final Configuration config) throws SocketException {
		Settings settings = config.getSettings();
		SocketSettings socketSettings = settings.getLastValue(
				GeneralSettingSpecConstants.CLIENT_FACING_SOCKET_SETTINGS);
		socketSettings.applyTo(clientFacingSock);
	}
	
	private Configuration newConfiguration() {
		Configuration config = ImmutableConfiguration.newInstance(
				this.configuration);
		synchronized (this) {
			if (!ConfigurationsHelper.equals(this.lastConfiguration, config)) {
				this.clientFacingDtlsDatagramSocketFactory =
						DtlsDatagramSocketFactoryImpl.isDtlsEnabled(config) ?
								new DtlsDatagramSocketFactoryImpl(config) : null;
				this.clientFacingSslSocketFactory =
						SslSocketFactoryImpl.isSslEnabled(config) ?
								new SslSocketFactoryImpl(config) : null;
				this.netObjectFactory = new NetObjectFactoryImpl(config);
				this.lastConfiguration = config;
			}
		}
		return config;
	}
	
	public WorkerContext newWorkerContext(
			final Socket clientFacingSocket) throws IOException {
		Configuration config = this.newConfiguration();
		Socket clientFacingSock = clientFacingSocket;
		this.configureClientFacingSocket(clientFacingSock, config);
		clientFacingSock = this.wrapClientFacingSocket(clientFacingSock);
		return new WorkerContext(
				clientFacingSock, 
				config, 
				this.netObjectFactory, 
				this.clientFacingDtlsDatagramSocketFactory);
	}
	
	private Socket wrapClientFacingSocket(
			final Socket clientFacingSock) throws IOException {
		Socket clientFacingSck = clientFacingSock;
		if (this.clientFacingSslSocketFactory != null) {
			clientFacingSck = this.clientFacingSslSocketFactory.newSocket(
					clientFacingSck, null, true);
		}		
		return clientFacingSck;
	}
	
}
