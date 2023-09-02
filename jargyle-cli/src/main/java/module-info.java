module com.github.jh3nd3rs0n.jargyle.cli {
	requires com.github.jh3nd3rs0n.jargyle.client;
	requires com.github.jh3nd3rs0n.jargyle.common;
	requires com.github.jh3nd3rs0n.jargyle.internal;
	requires com.github.jh3nd3rs0n.jargyle.server;
	requires com.github.jh3nd3rs0n.jargyle.server.configrepo.impl;
	requires com.github.jh3nd3rs0n.jargyle.transport;
	requires org.slf4j;
	exports com.github.jh3nd3rs0n.argmatey;
	exports com.github.jh3nd3rs0n.jargyle.cli;
}