package com.github.jh3nd3rs0n.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.github.jh3nd3rs0n.jargyle.client.NetObjectFactory;
import com.github.jh3nd3rs0n.jargyle.common.net.SocketSettings;

public final class EchoServer {

	private static final class Listener implements Runnable {
		
		private final ServerSocket serverSocket;
	
		public Listener(final ServerSocket serverSock) {
			this.serverSocket = serverSock;
		}
	
		public void run() {
			ExecutorService executor = ExecutorHelper.newExecutor();
			try {
				while (true) {
					try {
						Socket clientSocket = this.serverSocket.accept();
						executor.execute(new Worker(clientSocket));
					} catch (IOException e) {
						if (ThrowableHelper.isOrHasInstanceOf(
								e, SocketException.class)) {
							break;
						}
						e.printStackTrace();
						break;
					}
				}
			} finally {
				executor.shutdownNow();
			}
		}
	}

	public static enum State {
		
		STARTED,
		
		STOPPED;
		
	}
	
	private static final class Worker implements Runnable {
		
		private final Socket clientSocket;
	
		public Worker(final Socket clientSock) {
			this.clientSocket = clientSock;
		}
	
		public void run() {
			try {
				InputStream in = this.clientSocket.getInputStream();
				OutputStream out = this.clientSocket.getOutputStream();
				byte[] bytes = MeasuredIoHelper.readFrom(in);
				MeasuredIoHelper.writeThenFlush(bytes, out);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					this.clientSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static final int BACKLOG = 50;
	public static final InetAddress INET_ADDRESS = InetAddress.getLoopbackAddress();
	public static final int PORT = 1084;
	public static final SocketSettings SOCKET_SETTINGS = SocketSettings.of();
	
	private final int backlog;
	private final InetAddress bindInetAddress;
	private ExecutorService executor;
	private final NetObjectFactory netObjectFactory;
	private int port;
	private ServerSocket serverSocket;
	private final SocketSettings socketSettings;
	private final int specifiedPort;
	private State state;

	public EchoServer() {
		this(
				NetObjectFactory.getDefault(), 
				PORT, 
				BACKLOG, 
				INET_ADDRESS, 
				SOCKET_SETTINGS);
	}
	
	public EchoServer(final NetObjectFactory netObjFactory, final int prt) {
		this(
				netObjFactory, 
				prt, 
				BACKLOG, 
				INET_ADDRESS, 
				SOCKET_SETTINGS);
	}
	
	public EchoServer(
			final NetObjectFactory netObjFactory, 
			final int prt, 
			final int bklog,
			final InetAddress bindInetAddr,
			final SocketSettings socketSttngs) {
		this.backlog = bklog;
		this.bindInetAddress = bindInetAddr;
		this.executor = null;
		this.netObjectFactory = netObjFactory;
		this.port = -1;
		this.serverSocket = null;
		this.socketSettings = socketSttngs;
		this.specifiedPort = prt;
		this.state = State.STOPPED;
	}

	public InetAddress getInetAddress() {
		return this.bindInetAddress;
	}
	
	public int getPort() {
		return this.port;
	}
	
	public State getState() {
		return this.state;
	}
	
	public void start() throws IOException {
		this.serverSocket = this.netObjectFactory.newServerSocket(
				this.specifiedPort, this.backlog, this.bindInetAddress);
		this.socketSettings.applyTo(this.serverSocket);
		this.port = this.serverSocket.getLocalPort();
		this.executor = Executors.newSingleThreadExecutor();
		this.executor.execute(new Listener(this.serverSocket));
		this.state = State.STARTED;
	}
	
	public void stop() throws IOException {
		this.serverSocket.close();
		this.serverSocket = null;
		this.port = -1;
		this.executor.shutdownNow();
		this.executor = null;
		this.state = State.STOPPED;
	}
	
}