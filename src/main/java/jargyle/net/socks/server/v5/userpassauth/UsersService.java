package jargyle.net.socks.server.v5.userpassauth;

abstract class UsersService {

	private final String source;
	
	public UsersService(final String src) { 
		this.source = src;
	}
	
	public final String getSource() {
		return this.source;
	}
	
	public abstract Users getUsers();
	
}
