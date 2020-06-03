package jargyle.server;

import java.util.ArrayList;
import java.util.List;

import jargyle.client.socks5.UsernamePassword;
import jargyle.server.socks5.Socks5RequestCriteria;
import jargyle.server.socks5.Socks5RequestCriterion;
import jargyle.server.socks5.UsernamePasswordAuthenticator;

final class ModifiableConfiguration implements Configuration {
	
	private final List<Criterion> allowedClientAddressCriteria;
	private final List<Criterion> allowedIncomingTcpAddressCriteria;
	private final List<Criterion> allowedIncomingUdpAddressCriteria;
	private final List<Socks5RequestCriterion> allowedSocks5RequestCriteria;
	private final List<Criterion> blockedClientAddressCriteria;
	private final List<Criterion> blockedIncomingTcpAddressCriteria;
	private final List<Criterion> blockedIncomingUdpAddressCriteria;		
	private final List<Socks5RequestCriterion> blockedSocks5RequestCriteria;
	private UsernamePassword externalClientSocks5UsernamePassword;
	private final List<Setting> settings;
	private UsernamePasswordAuthenticator socks5UsernamePasswordAuthenticator;
	
	public ModifiableConfiguration() {
		this.allowedClientAddressCriteria = new ArrayList<Criterion>();
		this.allowedIncomingTcpAddressCriteria = new ArrayList<Criterion>();
		this.allowedIncomingUdpAddressCriteria = new ArrayList<Criterion>();
		this.allowedSocks5RequestCriteria = 
				new ArrayList<Socks5RequestCriterion>();
		this.blockedClientAddressCriteria = new ArrayList<Criterion>();
		this.blockedIncomingTcpAddressCriteria = new ArrayList<Criterion>();
		this.blockedIncomingUdpAddressCriteria = new ArrayList<Criterion>();
		this.blockedSocks5RequestCriteria = 
				new ArrayList<Socks5RequestCriterion>();
		this.externalClientSocks5UsernamePassword = null;
		this.settings = new ArrayList<Setting>();
		this.socks5UsernamePasswordAuthenticator = null;
	}
	
	public void add(final Configuration configuration) {
		this.addAllowedClientAddressCriteria(
				configuration.getAllowedClientAddressCriteria());
		this.addAllowedIncomingTcpAddressCriteria(
				configuration.getAllowedIncomingTcpAddressCriteria());
		this.addAllowedIncomingUdpAddressCriteria(
				configuration.getAllowedIncomingUdpAddressCriteria());
		this.addAllowedSocks5RequestCriteria(
				configuration.getAllowedSocks5RequestCriteria());
		this.addBlockedClientAddressCriteria(
				configuration.getBlockedClientAddressCriteria());
		this.addBlockedIncomingTcpAddressCriteria(
				configuration.getBlockedIncomingTcpAddressCriteria());
		this.addBlockedIncomingUdpAddressCriteria(
				configuration.getBlockedIncomingUdpAddressCriteria());			
		this.addBlockedSocks5RequestCriteria(
				configuration.getBlockedSocks5RequestCriteria());
		this.setExternalClientSocks5UsernamePassword(
				configuration.getExternalClientSocks5UsernamePassword());
		this.addSettings(configuration.getSettings());
		this.setSocks5UsernamePasswordAuthenticator(
				configuration.getSocks5UsernamePasswordAuthenticator());
	}

	public void addAllowedClientAddressCriteria(
			final Criteria allowedClientAddrCriteria) {
		if (allowedClientAddrCriteria.toList().isEmpty()) {
			return;
		}
		this.allowedClientAddressCriteria.addAll(
				allowedClientAddrCriteria.toList());
	}
	
	public void addAllowedIncomingTcpAddressCriteria(
			final Criteria allowedIncomingTcpAddrCriteria) {
		if (allowedIncomingTcpAddrCriteria.toList().isEmpty()) {
			return;
		}
		this.allowedIncomingTcpAddressCriteria.addAll(
				allowedIncomingTcpAddrCriteria.toList());
	}
	
	public void addAllowedIncomingUdpAddressCriteria(
			final Criteria allowedIncomingUdpAddrCriteria) {
		if (allowedIncomingUdpAddrCriteria.toList().isEmpty()) {
			return;
		}
		this.allowedIncomingUdpAddressCriteria.addAll(
				allowedIncomingUdpAddrCriteria.toList());
	}
	
	public void addAllowedSocks5RequestCriteria(
			final Socks5RequestCriteria allowedSocks5ReqCriteria) {
		if (allowedSocks5ReqCriteria.toList().isEmpty()) {
			return;
		}
		this.allowedSocks5RequestCriteria.addAll(
				allowedSocks5ReqCriteria.toList());
	}
	
	public void addBlockedClientAddressCriteria(
			final Criteria blockedClientAddrCriteria) {
		if (blockedClientAddrCriteria.toList().isEmpty()) {
			return;
		}
		this.blockedClientAddressCriteria.addAll(
				blockedClientAddrCriteria.toList());
	}
	
	public void addBlockedIncomingTcpAddressCriteria(
			final Criteria blockedIncomingTcpAddrCriteria) {
		if (blockedIncomingTcpAddrCriteria.toList().isEmpty()) {
			return;
		}
		this.blockedIncomingTcpAddressCriteria.addAll(
				blockedIncomingTcpAddrCriteria.toList());
	}
	
	public void addBlockedIncomingUdpAddressCriteria(
			final Criteria blockedIncomingUdpAddrCriteria) {
		if (blockedIncomingUdpAddrCriteria.toList().isEmpty()) {
			return;
		}
		this.blockedIncomingUdpAddressCriteria.addAll(
				blockedIncomingUdpAddrCriteria.toList());
	}
	
	public void addBlockedSocks5RequestCriteria(
			final Socks5RequestCriteria blockedSocks5ReqCriteria) {
		if (blockedSocks5ReqCriteria.toList().isEmpty()) {
			return;
		}
		this.blockedSocks5RequestCriteria.addAll(
				blockedSocks5ReqCriteria.toList());
	}
	
	public void addSettings(final Settings sttngs) {
		if (sttngs.toList().isEmpty()) {
			return;
		}
		this.settings.addAll(sttngs.toList());
	}

	@Override
	public Criteria getAllowedClientAddressCriteria() {
		return Criteria.newInstance(this.allowedClientAddressCriteria);
	}
	
	@Override
	public Criteria getAllowedIncomingTcpAddressCriteria() {
		return Criteria.newInstance(this.allowedIncomingTcpAddressCriteria);
	}
	
	@Override
	public Criteria getAllowedIncomingUdpAddressCriteria() {
		return Criteria.newInstance(this.allowedIncomingUdpAddressCriteria);
	}
	
	@Override
	public Socks5RequestCriteria getAllowedSocks5RequestCriteria() {
		return new Socks5RequestCriteria(this.allowedSocks5RequestCriteria);
	}

	@Override
	public Criteria getBlockedClientAddressCriteria() {
		return Criteria.newInstance(this.blockedClientAddressCriteria);
	}
	
	@Override
	public Criteria getBlockedIncomingTcpAddressCriteria() {
		return Criteria.newInstance(this.blockedIncomingTcpAddressCriteria);
	}
	
	@Override
	public Criteria getBlockedIncomingUdpAddressCriteria() {
		return Criteria.newInstance(this.blockedIncomingUdpAddressCriteria);
	}
	
	@Override
	public Socks5RequestCriteria getBlockedSocks5RequestCriteria() {
		return new Socks5RequestCriteria(this.blockedSocks5RequestCriteria);
	}
	
	@Override
	public UsernamePassword getExternalClientSocks5UsernamePassword() {
		return this.externalClientSocks5UsernamePassword;
	}
	
	@Override
	public Settings getSettings() {
		return Settings.newInstance(this.settings);
	}

	@Override
	public UsernamePasswordAuthenticator getSocks5UsernamePasswordAuthenticator() {
		return this.socks5UsernamePasswordAuthenticator;
	}
	
	public void setExternalClientSocks5UsernamePassword(
			final UsernamePassword externalClientSocks5UsrnmPsswrd) {
		if (externalClientSocks5UsrnmPsswrd == null) {
			return;
		}
		this.externalClientSocks5UsernamePassword = 
				externalClientSocks5UsrnmPsswrd;
	}

	public void setSocks5UsernamePasswordAuthenticator(
			final UsernamePasswordAuthenticator socks5UsrnmPsswrdAuthenticator) {
		if (socks5UsrnmPsswrdAuthenticator == null) {
			return;
		}
		this.socks5UsernamePasswordAuthenticator = socks5UsrnmPsswrdAuthenticator;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName())
			.append(" [allowedClientAddressCriteria=")
			.append(this.allowedClientAddressCriteria)
			.append(", allowedIncomingTcpAddressCriteria=")
			.append(this.allowedIncomingTcpAddressCriteria)
			.append(", allowedIncomingUdpAddressCriteria=")
			.append(this.allowedIncomingUdpAddressCriteria)
			.append(", allowedSocks5RequestCriteria=")
			.append(this.allowedSocks5RequestCriteria)			
			.append(", blockedClientAddressCriteria=")
			.append(this.blockedClientAddressCriteria)
			.append(", blockedIncomingTcpAddressCriteria=")
			.append(this.blockedIncomingTcpAddressCriteria)
			.append(", blockedIncomingUdpAddressCriteria=")
			.append(this.blockedIncomingUdpAddressCriteria)			
			.append(", blockedSocks5RequestCriteria=")
			.append(this.blockedSocks5RequestCriteria)
			.append(", externalClientSocks5UsernamePassword=")
			.append(this.externalClientSocks5UsernamePassword)
			.append(", settings=")
			.append(this.settings)
			.append(", socks5UsernamePasswordAuthenticator=")
			.append(this.socks5UsernamePasswordAuthenticator)
			.append("]");
		return builder.toString();
	}
	
}