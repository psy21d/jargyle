package com.github.jh3nd3rs0n.jargyle.server.socks5.userpassmethod;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.jh3nd3rs0n.jargyle.internal.annotation.HelpText;
import com.github.jh3nd3rs0n.jargyle.server.socks5.userpassmethod.internal.userrepo.impl.FileSourceUserRepository;

public final class ExternalSourceUserRepositorySpecConstants {

	private static final UserRepositorySpecs USER_REPOSITORY_SPECS =
			new UserRepositorySpecs();

	@HelpText(
			doc = "User repository that handles the storage of the users from "
					+ "a provided file of a list of URL encoded username and "
					+ "hashed password pairs (If the file does not exist, it "
					+ "will be created and used.)", 
			usage = "FileSourceUserRepository:FILE"
	)
	public static final UserRepositorySpec FILE_SOURCE_USER_REPOSITORY = USER_REPOSITORY_SPECS.addThenGet(new UserRepositorySpec(
			"FileSourceUserRepository") {

				@Override
				public UserRepository newUserRepository(
						final String initializationString) {
					return FileSourceUserRepository.newInstance(
							this, initializationString);
				}
		
	});
	
	private static final List<UserRepositorySpec> VALUES =
			USER_REPOSITORY_SPECS.toList();
	
	private static final Map<String, UserRepositorySpec> VALUES_MAP =
			USER_REPOSITORY_SPECS.toMap();
	
	public static UserRepositorySpec valueOfTypeName(final String typeName) {
		if (VALUES_MAP.containsKey(typeName)) {
			return VALUES_MAP.get(typeName);
		}
		String str = VALUES.stream()
				.map(UserRepositorySpec::getTypeName)
				.collect(Collectors.joining(", "));
		throw new IllegalArgumentException(String.format(
				"expected user repository type name must be one of the "
				+ "following values: %s. actual value is %s",
				str,
				typeName));
	}
	
	public static List<UserRepositorySpec> values() {
		return VALUES;
	}
	
	public static Map<String, UserRepositorySpec> valuesMap() {
		return VALUES_MAP;
	}
	
	private ExternalSourceUserRepositorySpecConstants() { }

}