# Server Configuration

## Page Contents

-   [Enabling SSL/TLS for TCP Traffic Between the Server and Its Clients](#enabling-ssl-tls-for-tcp-traffic-between-the-server-and-its-clients)
-   [Enabling DTLS for UDP Traffic Between the Server and Its Clients](#enabling-dtls-for-udp-traffic-between-the-server-and-its-clients)
-   [Using SOCKS5 Authentication](#using-socks5-authentication)
    -   [Using No Authentication](#using-no-authentication)
    -   [Using Username Password Authentication](#using-username-password-authentication)
    -   [Using GSS-API Authentication](#using-gss-api-authentication)
-   [Chaining to Another SOCKS Server](#chaining-to-another-socks-server)
    -   [Enabling SSL/TLS for TCP Traffic Between the Server and the Other SOCKS Server](#enabling-ssl-tls-for-tcp-traffic-between-the-server-and-the-other-socks-server)
    -   [Enabling DTLS for UDP Traffic Between the Server and the Other SOCKS Server](#enabling-dtls-for-udp-traffic-between-the-server-and-the-other-socks-server)
    -   [Chaining to the Other SOCKS Server Using SOCKS5 Authentication](#chaining-to-the-other-socks-server-using-socks5-authentication)
        -   [Chaining to the Other SOCKS Server Using No Authentication](#chaining-to-the-other-socks-server-using-no-authentication)
        -   [Chaining to the Other SOCKS Server Using Username Password Authentication](#chaining-to-the-other-socks-server-using-username-password-authentication)
        -   [Chaining to the Other SOCKS Server Using GSS-API Authentication](#chaining-to-the-other-socks-server-using-gss-api-authentication)
    -   [Resolving Host Names From the Other SOCKS5 Server](#resolving-host-names-from-the-other-socks5-server)
-   [Chaining to a Specified Chain of Other SOCKS Servers](#chaining-to-a-specified-chain-of-other-socks-servers)
-   [Chaining to Multiple Specified Chains of Other SOCKS Servers](#chaining-to-multiple-specified-chains-of-other-socks-servers)
-   [Using Rules to Manage Traffic](#using-rules-to-manage-traffic)
    -   [Rule Conditions](#rule-conditions)
    -   [Rule Results](#rule-results)
    -   [Allowing or Denying Traffic](#allowing-or-denying-traffic)
    -   [Allowing a Limited Number of Simultaneous Instances of Traffic](#allowing-a-limited-number-of-simultaneous-instances-of-traffic)
    -   [Routing Traffic](#routing-traffic)
    -   [Redirecting the Desired Destination](#redirecting-the-desired-destination)
    -   [Configuring Sockets](#configuring-sockets)
    -   [Configuring Relay Settings](#configuring-relay-settings)
    -   [Limiting Relay Bandwidth](#limiting-relay-bandwidth)

## Enabling SSL/TLS for TCP Traffic Between the Server and Its Clients

You can enable SSL/TLS for TCP traffic between the server and its clients. By 
default SSL/TLS for TCP traffic between the server and its clients is disabled. 
To enable SSL/TLS for TCP traffic between the server and its clients, you will 
need to have the setting `ssl.enabled` set to `true`. In addition, you will 
need to have the setting `ssl.keyStoreFile` to specify the server's key store 
file (this file would need to be created by Java's keytool utility). Also, you 
will need to have the setting `ssl.keyStorePassword` to specify the password 
for the server's key store file.

Command line example:

```
./bin/jargyle start-server \
    --setting=ssl.enabled=true \
    --setting=ssl.keyStoreFile=server.jks \
    --setting=ssl.keyStorePassword=password
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>ssl.enabled</name>
            <value>true</value>
        </setting>
        <setting>
            <name>ssl.keyStoreFile</name>
            <value>server.jks</value>
        </setting>
        <setting>
            <name>ssl.keyStorePassword</name>
            <value>password</value>
            <doc>If this configuration file was created by new-server-config-file, the password would be encrypted</doc>
        </setting>
    </settings>
</configuration>
```

If you do not want to have the password appear in any script or in any part of 
the command line history for security reasons, you can use the command line 
option `--enter-ssl-key-store-pass` instead. It will provide an interactive 
prompt for you to enter the password.

Command line example:

```    
./bin/jargyle start-server \
    --setting=ssl.enabled=true \
    --setting=ssl.keyStoreFile=server.jks \
    --enter-ssl-key-store-pass
```

If you want to have the client authenticate using SSL/TLS, you will need to have 
the setting `ssl.needClientAuth` set to `true`. In addition, you will need 
to have the setting `ssl.trustStoreFile` to specify the client's key store 
file to be used as a trust store (this file would need to be created by Java's 
keytool utility). Also, you will need to have the setting 
`ssl.trustStorePassword` to specify the password for the client's trust 
store file.

Command line example:

```
./bin/jargyle start-server \
    --setting=ssl.enabled=true \
    --setting=ssl.keyStoreFile=server.jks \
    --setting=ssl.keyStorePassword=password \
    --setting=ssl.needClientAuth=true \
    --setting=ssl.trustStoreFile=client.jks \
    --setting=ssl.trustStorePassword=drowssap
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>ssl.enabled</name>
            <value>true</value>
        </setting>
        <setting>
            <name>ssl.keyStoreFile</name>
            <value>server.jks</value>
        </setting>
        <setting>
            <name>ssl.keyStorePassword</name>
            <value>password</value>
            <doc>If this configuration file was created by new-server-config-file, the password would be encrypted</doc>
        </setting>
        <setting>
            <name>ssl.needClientAuth</name>
            <value>true</value>
        </setting>
        <setting>
            <name>ssl.trustStoreFile</name>
            <value>client.jks</value>
        </setting>
        <setting>
            <name>ssl.trustStorePassword</name>
            <value>drowssap</value>
            <doc>If this configuration file was created by new-server-config-file, the password would be encrypted</doc>
        </setting>
    </settings>
</configuration>
```

If you do not want to have the password appear in any script or in any part of 
the command line history for security reasons, you can use the command line 
option `--enter-ssl-trust-store-pass` instead. It will provide an 
interactive prompt for you to enter the password.

Command line example:

```
./bin/jargyle start-server \
    --setting=ssl.enabled=true \
    --setting=ssl.keyStoreFile=server.jks \
    --enter-ssl-key-store-pass \
    --setting=ssl.needClientAuth=true \
    --setting=ssl.trustStoreFile=client.jks \
    --enter-ssl-trust-store-pass
```

## Enabling DTLS for UDP Traffic Between the Server and Its Clients

You can enable DTLS for UDP traffic between the server and its clients. By 
default DTLS for UDP traffic between the server and its clients is disabled. To 
enable DTLS for UDP traffic between the server and its clients, you will need to 
have the setting `dtls.enabled` set to `true`. In addition, you will need to 
have the setting `dtls.keyStoreFile` to specify the server's key store file 
(this file would need to be created by Java's keytool utility). Also, you will 
need to have the setting `dtls.keyStorePassword` to specify the password for 
the server's key store file.

Command line example:

```
./bin/jargyle start-server \
    --setting=dtls.enabled=true \
    --setting=dtls.keyStoreFile=server.jks \
    --setting=dtls.keyStorePassword=password
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>dtls.enabled</name>
            <value>true</value>
        </setting>
        <setting>
            <name>dtls.keyStoreFile</name>
            <value>server.jks</value>
        </setting>
        <setting>
            <name>dtls.keyStorePassword</name>
            <value>password</value>
            <doc>If this configuration file was created by new-server-config-file, the password would be encrypted</doc>
        </setting>
    </settings>
</configuration>
```

If you do not want to have the password appear in any script or in any part of 
the command line history for security reasons, you can use the command line 
option `--enter-dtls-key-store-pass` instead. It will provide an 
interactive prompt for you to enter the password.

Command line example:

```    
./bin/jargyle start-server \
    --setting=dtls.enabled=true \
    --setting=dtls.keyStoreFile=server.jks \
    --enter-dtls-key-store-pass
```

If you want to have the client authenticate using DTLS, you will need to have 
the setting `dtls.needClientAuth` set to `true`. In addition, you will need 
to have the setting `dtls.trustStoreFile` to specify the client's key store 
file to be used as a trust store (this file would need to be created by Java's 
keytool utility). Also, you will need to have the setting 
`dtls.trustStorePassword` to specify the password for the client's trust 
store file.

Command line example:

```
./bin/jargyle start-server \
    --setting=dtls.enabled=true \
    --setting=dtls.keyStoreFile=server.jks \
    --setting=dtls.keyStorePassword=password \
    --setting=dtls.needClientAuth=true \
    --setting=dtls.trustStoreFile=client.jks \
    --setting=dtls.trustStorePassword=drowssap
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>dtls.enabled</name>
            <value>true</value>
        </setting>
        <setting>
            <name>dtls.keyStoreFile</name>
            <value>server.jks</value>
        </setting>
        <setting>
            <name>dtls.keyStorePassword</name>
            <value>password</value>
            <doc>If this configuration file was created by new-server-config-file, the password would be encrypted</doc>
        </setting>
        <setting>
            <name>dtls.needClientAuth</name>
            <value>true</value>
        </setting>
        <setting>
            <name>dtls.trustStoreFile</name>
            <value>client.jks</value>
        </setting>
        <setting>
            <name>dtls.trustStorePassword</name>
            <value>drowssap</value>
            <doc>If this configuration file was created by new-server-config-file, the password would be encrypted</doc>
        </setting>
    </settings>
</configuration>
```

If you do not want to have the password appear in any script or in any part of 
the command line history for security reasons, you can use the command line 
option `--enter-dtls-trust-store-pass` instead. It will provide an 
interactive prompt for you to enter the password.

Command line example:

```
./bin/jargyle start-server \
    --setting=dtls.enabled=true \
    --setting=dtls.keyStoreFile=server.jks \
    --enter-dtls-key-store-pass \
    --setting=dtls.needClientAuth=true \
    --setting=dtls.trustStoreFile=client.jks \
    --enter-dtls-trust-store-pass
```

## Using SOCKS5 Authentication

The server has the following SOCKS5 authentication methods to choose from:

-   `NO_AUTHENTICATION_REQUIRED`: No authentication required
-   `GSSAPI`: GSS-API authentication
-   `USERNAME_PASSWORD`: Username password authentication

On the command line, you can have one or more of the aforementioned 
authentication methods set in the setting `socks5.methods` as a space 
separated list. 

Command line example:

```
./bin/jargyle start-server "--setting=socks5.methods=NO_AUTHENTICATION_REQUIRED GSSAPI"
```

In the server configuration file, you can have one or more of the aforementioned 
authentication methods set in the setting `socks5.methods` as a 
`<socks5.methods/>` XML element with one or more `<socks5.method/>` XML 
elements each specifying an authentication method.

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>socks5.methods</name>
            <socks5.methods>
                <socks5.method>GSSAPI</socks5.method>
                <socks5.method>USERNAME_PASSWORD</socks5.method>
            </socks5.methods>
        </setting>
    </settings>
</configuration>
```

If not set, the default value for the setting `socks5.methods` is set to `NO_AUTHENTICATION_REQUIRED`

### Using No Authentication

Because the default value for the setting `socks5.methods` is set to 
`NO_AUTHENTICATION_REQUIRED`, it is not required for
`NO_AUTHENTICATION_REQUIRED` to be included in the setting 
`socks5.methods`.

However, if other authentication methods are to be used in addition to 
`NO_AUTHENTICATION_REQUIRED`, `NO_AUTHENTICATION_REQUIRED` must be 
included in the setting `socks5.methods`

Command line example:

```
./bin/jargyle start-server "--setting=socks5.methods=NO_AUTHENTICATION_REQUIRED GSSAPI USERNAME_PASSWORD"
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>socks5.methods</name>
            <socks5.methods>
                <socks5.method>NO_AUTHENTICATION_REQUIRED</socks5.method>
                <socks5.method>GSSAPI</socks5.method>
                <socks5.method>USERNAME_PASSWORD</socks5.method>
            </socks5.methods>
        </setting>
    </settings>
</configuration>
```

### Using Username Password Authentication

To use username password authentication, you will need to have the setting 
`socks5.methods` to have `USERNAME_PASSWORD` included.

Command line example:

```
./bin/jargyle start-server --setting=socks5.methods=USERNAME_PASSWORD
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>socks5.methods</name>
            <socks5.methods>
                <socks5.method>USERNAME_PASSWORD</socks5.method>
            </socks5.methods>
        </setting>
    </settings>
</configuration>
```

Also, you will need to have the setting `socks5.userpassauth.userRepository` to specify the name of the class that extends `com.github.jh3nd3rs0n.jargyle.server.socks5.userpassauth.UserRepository` along with an initialization string value.

The following are two provided classes you can use:

-   `com.github.jh3nd3rs0n.jargyle.server.socks5.userpassauth.userrepo.impl.StringSourceUserRepository`
-   `com.github.jh3nd3rs0n.jargyle.server.socks5.userpassauth.userrepo.impl.CsvFileSourceUserRepository`

`com.github.jh3nd3rs0n.jargyle.server.socks5.userpassauth.userrepo.impl.StringSourceUserRepository`:
This class handles the storage of the SOCKS5 users from the initialization 
string value of a space separated list of username password values.

Each username password value in the space separated list must be of the 
following format:

```
USERNAME:PASSWORD
```

Where `USERNAME` is the username and `PASSWORD` is the password. Both the 
username and password can be URL encoded.

If the username or the password contains a colon character (`:`), then each 
colon character must be replaced with the URL encoding character `%3A`.

If the username or the password contains a space character, then each space 
character must be replaced with the URL encoding character `+` or `%20`.

If the username or the password contains a plus sign character (`+`) not used 
for URL encoding, then each plus sign character not used for URL encoding must 
be replaced with the URL encoding character `%2B`.

If the username or the password contains a percent sign character (`%`) not 
used for URL encoding, then each percent sign character not used for URL 
encoding must be replaced with the URL encoding character `%25`.

Command line example:

```
./bin/jargyle start-server \
    --setting=socks5.methods=USERNAME_PASSWORD \
    "--setting=socks5.userpassauth.userRepository=com.github.jh3nd3rs0n.jargyle.server.socks5.userpassauth.userrepo.impl.StringSourceUserRepository:Aladdin:opensesame Jasmine:mission%3Aimpossible"
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>socks5.methods</name>
            <socks5.methods>
                <socks5.method>USERNAME_PASSWORD</socks5.method>
            </socks5.methods>
        </setting>
        <setting>
            <name>socks5.userpassauth.userRepository</name>
            <socks5.userpassauth.userRepository>
                <className>com.github.jh3nd3rs0n.jargyle.server.socks5.userpassauth.userrepo.impl.StringSourceUserRepository</className>
                <initializationString>Aladdin:opensesame Jasmine:mission%3Aimpossible</initializationString>
            </socks5.userpassauth.userRepository>
        </setting>
    </settings>
</configuration>
```

`com.github.jh3nd3rs0n.jargyle.server.socks5.userpassauth.userrepo.impl.CsvFileSourceUserRepository`: 
This class handles the storage of the SOCKS5 users from a CSV file whose name 
is provided as an initialization string value. The SOCKS5 users from the CSV 
file are loaded onto memory. Because of this, you will need at least as much 
memory as the size of the CSV file. If the CSV file does not exist, it will be 
created and used. If the CSV file does exist, the existing CSV file will be 
used. To manage SOCKS5 users under a user repository, see 
[Managing SOCKS5 Users](cli.html#managing-socks5-users).

Command line example:

```
./bin/jargyle start-server \
    --setting=socks5.methods=USERNAME_PASSWORD \
    --setting=socks5.userpassauth.userRepository=com.github.jh3nd3rs0n.jargyle.server.socks5.userpassauth.userrepo.impl.CsvFileSourceUserRepository:users.csv
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>socks5.methods</name>
            <socks5.methods>
                <socks5.method>USERNAME_PASSWORD</socks5.method>
            </socks5.methods>
        </setting>
        <setting>
            <name>socks5.userpassauth.userRepository</name>
            <socks5.userpassauth.userRepository>
                <className>com.github.jh3nd3rs0n.jargyle.server.socks5.userpassauth.userrepo.impl.CsvFileSourceUserRepository</className>
                <initializationString>users.csv</initializationString>
            </socks5.userpassauth.userRepository>
        </setting>
    </settings>
</configuration>
```

### Using GSS-API Authentication

To use GSS-API authentication, you will need to have the setting 
`socks5.methods` to have `GSSAPI` included.

Command line example:

```    
./bin/jargyle start-server --setting=socks5.methods=GSSAPI
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>socks5.methods</name>
            <socks5.methods>
                <socks5.method>GSSAPI</socks5.method>
            </socks5.methods>
        </setting>
    </settings>
</configuration>
```

Also, you will need to specify Java system properties to use a security 
mechanism that implements the GSS-API (for example, Kerberos is a security 
mechanism that implements the GSS-API).

The following are sufficient examples of using the Kerberos security mechanism:

Command line example:

```
export JARGYLE_OPTS="-Djavax.security.auth.useSubjectCredsOnly=false -Djava.security.auth.login.config=login.conf -Djava.security.krb5.conf=krb5.conf"
./bin/jargyle start-server --setting=socks5.methods=GSSAPI 
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<!--
Before running this file, the following must be executed:
export JARGYLE_OPTS="-Djavax.security.auth.useSubjectCredsOnly=false -Djava.security.auth.login.config=login.conf -Djava.security.krb5.conf=krb5.conf"
-->
<configuration>
    <settings>
        <setting>
            <name>socks5.methods</name>
            <socks5.methods>
                <socks5.method>GSSAPI</socks5.method>
            </socks5.methods>
        </setting>
    </settings>
</configuration>
```

The Java system property `javax.security.auth.useSubjectCredsOnly` with 
the value `false` disables JAAS-based authentication to obtain the credentials 
directly and lets the underlying security mechanism obtain them instead.

The Java system property `java.security.auth.login.config` with the value 
`login.conf` provides a JAAS configuration file for the underlying security 
mechanism.

`login.conf`:

```
com.sun.security.jgss.accept {
  com.sun.security.auth.module.Krb5LoginModule required
  principal="rcmd/127.0.0.1"
  useKeyTab=true
  keyTab="rcmd.keytab"
  storeKey=true;
};
```

In `login.conf`, `rcmd/127.0.0.1` is a service principal that is created by 
a Kerberos administrator specifically for a SOCKS5 server with the service name 
`rcmd` residing at the address `127.0.0.1`. (In a production environment, the 
address `127.0.0.1` should be replaced by the name of the machine of where the 
SOCKS5 server resides.) 

Also in `login.conf`, `rcmd.keytab` is a keytab file also created by a 
Kerberos administrator that contains the aforementioned service principal and 
its respective encrypted key.  

The Java system property `java.security.krb5.conf` with the value 
`krb5.conf` provides the Kerberos configuration file that points to the 
Kerberos Key Distribution Center (KDC) for authentication.   

`krb5.conf`:

```
[libdefaults]
    kdc_realm = EXAMPLE.COM
    default_realm = EXAMPLE.COM
    udp_preference_limit = 4096
    kdc_tcp_port = 12345
    kdc_udp_port = 12345

[realms]
    EXAMPLE.COM = {
        kdc = 127.0.0.1:12345
    }
```

In `krb5.conf`, a KDC is defined as running at the address `127.0.0.1` on 
port `12345` with its realm as `EXAMPLE.COM`. (In a production environment, 
the address `127.0.0.1` should be replaced by the actual address or name of 
the machine of where the KDC resides. Also, in a production environment, the 
realm `EXAMPLE.COM` should be replaced by an actual realm provided by a 
Kerberos administrator.)  

## Chaining to Another SOCKS Server

You can have the server chained to another SOCKS server, meaning that its 
traffic can be routed through another SOCKS server. To have the server chained 
to another SOCKS server, you will need to specify the other SOCKS server as a 
URI in the setting `chaining.socksServerUri`

Command line example:

```
./bin/jargyle start-server --setting=chaining.socksServerUri=socks5://127.0.0.1:23456
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:23456</value>
        </setting>
    </settings>
</configuration>
```

Please note that the scheme in the URI specifies the SOCKS protocol to be used 
when accessing the other SOCKS server (`socks5`), the address or name of the 
machine of where the other SOCKS server resides (`127.0.0.1`), and the port 
number of the other SOCKS server (`23456`). In the aforementioned examples, the 
SOCKS protocol version 5 is used. At this time, the only supported scheme for 
the URI format is `socks5`

### Enabling SSL/TLS for TCP Traffic Between the Server and the Other SOCKS Server

You can enable SSL/TLS for TCP traffic between the server and the other SOCKS 
server under the following condition: 

-   The other SOCKS server accepts SSL/TLS connections.

By default SSL/TLS for TCP traffic between the server and the other SOCKS server 
is disabled. To enable SSL/TLS for TCP traffic between the server and the other 
SOCKS server, you will need to have the setting `chaining.ssl.enabled` set 
to `true`. In addition, you will need to have the setting 
`chaining.ssl.trustStoreFile` to specify the other SOCKS server's key store 
file used as a trust store (this file would need to be created by Java's keytool 
utility). Also, you will need to have the setting 
`chaining.ssl.trustStorePassword` to specify the password for the other 
SOCKS server's trust store file.

Command line example:

```
./bin/jargyle start-server \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:23456 \
    --setting=chaining.ssl.enabled=true \
    --setting=chaining.ssl.trustStoreFile=server.jks \
    --setting=chaining.ssl.trustStorePassword=password
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:23456</value>
        </setting>
        <setting>
            <name>chaining.ssl.enabled</name>
            <value>true</value>
        </setting>
        <setting>
            <name>chaining.ssl.trustStoreFile</name>
            <value>server.jks</value>
        </setting>
        <setting>
            <name>chaining.ssl.trustStorePassword</name>
            <value>password</value>
            <doc>If this configuration file was created by new-server-config-file, the password would be encrypted</doc>
        </setting>
    </settings>
</configuration>
```

If you do not want to have the password appear in any script or in any part of 
the command line history for security reasons, you can use the command line 
option `--enter-chaining-ssl-trust-store-pass` instead. It will provide 
an interactive prompt for you to enter the password.

Command line example:

```
./bin/jargyle start-server \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:23456 \
    --setting=chaining.ssl.enabled=true \
    --setting=chaining.ssl.trustStoreFile=server.jks \
    --enter-chaining-ssl-trust-store-pass
```

If the other SOCKS server wants the client (the server) to authenticate using 
SSL/TLS, you will need to have the setting `chaining.ssl.keyStoreFile` to 
specify the client's key store file (this file would need to be created by 
Java's keytool utility). Also, you will need to have the setting 
`chaining.ssl.keyStorePassword` to specify the password for the client's 
key store file.

Command line example:

```
./bin/jargyle start-server \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:23456 \
    --setting=chaining.ssl.enabled=true \
    --setting=chaining.ssl.keyStoreFile=client.jks \
    --setting=chaining.ssl.keyStorePassword=drowssap \
    --setting=chaining.ssl.trustStoreFile=server.jks \
    --setting=chaining.ssl.trustStorePassword=password    
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:23456</value>
        </setting>
        <setting>
            <name>chaining.ssl.enabled</name>
            <value>true</value>
        </setting>
        <setting>
            <name>chaining.ssl.keyStoreFile</name>
            <value>client.jks</value>
        </setting>
        <setting>
            <name>chaining.ssl.keyStorePassword</name>
            <value>drowssap</value>
            <doc>If this configuration file was created by new-server-config-file, the password would be encrypted</doc>
        </setting>
        <setting>
            <name>chaining.ssl.trustStoreFile</name>
            <value>server.jks</value>
        </setting>
        <setting>
            <name>chaining.ssl.trustStorePassword</name>
            <value>password</value>
            <doc>If this configuration file was created by new-server-config-file, the password would be encrypted</doc>
        </setting>
    </settings>
</configuration>
```

If you do not want to have the password appear in any script or in any part of 
the command line history for security reasons, you can use the command line 
option `--enter-chaining-ssl-key-store-pass` instead. It will provide an 
interactive prompt for you to enter the password.

Command line example:

```
./bin/jargyle start-server \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:23456 \
    --setting=chaining.ssl.enabled=true \
    --setting=chaining.ssl.keyStoreFile=client.jks \
    --enter-chaining-ssl-key-store-pass \
    --setting=chaining.ssl.trustStoreFile=server.jks \
    --enter-chaining-ssl-trust-store-pass
```

### Enabling DTLS for UDP Traffic Between the Server and the Other SOCKS Server

You can enable DTLS for UDP traffic between the server and the other SOCKS 
server under the following condition: 

-   The other SOCKS server accepts DTLS connections.

By default DTLS for UDP traffic between the server and the other SOCKS server is 
disabled. To enable DTLS for UDP traffic between the server and the other SOCKS 
server, you will need to have the setting `chaining.dtls.enabled` set to 
`true`. In addition, you will need to have the setting 
`chaining.dtls.trustStoreFile` to specify the other SOCKS server's key 
store file used as a trust store (this file would need to be created by Java's 
keytool utility). Also, you will need to have the setting `chaining.dtls.trustStorePassword` to specify the password for the other 
SOCKS server's trust store file.

Command line example:

```
./bin/jargyle start-server \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:23456 \
    --setting=chaining.dtls.enabled=true \
    --setting=chaining.dtls.trustStoreFile=server.jks \
    --setting=chaining.dtls.trustStorePassword=password
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:23456</value>
        </setting>
        <setting>
            <name>chaining.dtls.enabled</name>
            <value>true</value>
        </setting>
        <setting>
            <name>chaining.dtls.trustStoreFile</name>
            <value>server.jks</value>
        </setting>
        <setting>
            <name>chaining.dtls.trustStorePassword</name>
            <value>password</value>
            <doc>If this configuration file was created by new-server-config-file, the password would be encrypted</doc>
        </setting>
    </settings>
</configuration>
```
If you do not want to have the password appear in any script or in any part of 
the command line history for security reasons, you can use the command line 
option `--enter-chaining-dtls-trust-store-pass` instead. It will provide 
an interactive prompt for you to enter the password.

Command line example:

```
./bin/jargyle start-server \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:23456 \
    --setting=chaining.dtls.enabled=true \
    --setting=chaining.dtls.trustStoreFile=server.jks \
    --enter-chaining-dtls-trust-store-pass
```

If the other SOCKS server wants the client (the server) to authenticate using 
DTLS, you will need to have the setting `chaining.dtls.keyStoreFile` to 
specify the client's key store file (this file would need to be created by 
Java's keytool utility). Also, you will need to have the setting 
`chaining.dtls.keyStorePassword` to specify the password for the client's 
key store file.

Command line example:

```
./bin/jargyle start-server \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:23456 \
    --setting=chaining.dtls.enabled=true \
    --setting=chaining.dtls.keyStoreFile=client.jks \
    --setting=chaining.dtls.keyStorePassword=drowssap \
    --setting=chaining.dtls.trustStoreFile=server.jks \
    --setting=chaining.dtls.trustStorePassword=password    
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:23456</value>
        </setting>
        <setting>
            <name>chaining.dtls.enabled</name>
            <value>true</value>
        </setting>
        <setting>
            <name>chaining.dtls.keyStoreFile</name>
            <value>client.jks</value>
        </setting>
        <setting>
            <name>chaining.dtls.keyStorePassword</name>
            <value>drowssap</value>
            <doc>If this configuration file was created by new-server-config-file, the password would be encrypted</doc>
        </setting>
        <setting>
            <name>chaining.dtls.trustStoreFile</name>
            <value>server.jks</value>
        </setting>
        <setting>
            <name>chaining.dtls.trustStorePassword</name>
            <value>password</value>
            <doc>If this configuration file was created by new-server-config-file, the password would be encrypted</doc>
        </setting>
    </settings>
</configuration>
```

If you do not want to have the password appear in any script or in any part of 
the command line history for security reasons, you can use the command line 
option `--enter-chaining-dtls-key-store-pass` instead. It will provide an 
interactive prompt for you to enter the password.

Command line example:

```
./bin/jargyle start-server \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:23456 \
    --setting=chaining.dtls.enabled=true \
    --setting=chaining.dtls.keyStoreFile=client.jks \
    --enter-chaining-dtls-key-store-pass \
    --setting=chaining.dtls.trustStoreFile=server.jks \
    --enter-chaining-dtls-trust-store-pass
```

### Chaining to the Other SOCKS Server Using SOCKS5 Authentication

The server has the following SOCKS5 authentication methods to choose from when 
chaining to the other SOCKS5 server:

-   `NO_AUTHENTICATION_REQUIRED`: No authentication required
-   `GSSAPI`: GSS-API authentication
-   `USERNAME_PASSWORD`: Username password authentication

On the command line, you can have one or more of the aforementioned 
authentication methods set in the setting `chaining.socks5.methods` as a 
space separated list. 

Command line example:

```
./bin/jargyle start-server \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:23456 \
    "--setting=chaining.socks5.methods=NO_AUTHENTICATION_REQUIRED GSSAPI"
```

In the configuration file, you can have one or more of the aforementioned 
authentication methods set in the setting `chaining.socks5.methods` as a 
`<socks5.methods/>` XML element with one or more `<socks5.method/>` XML 
elements each specifying an authentication method.

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:23456</value>
        </setting>
        <setting>
            <name>chaining.socks5.methods</name>
            <socks5.methods>
                <socks5.method>GSSAPI</socks5.method>
                <socks5.method>USERNAME_PASSWORD</socks5.method>
            </socks5.methods>
        </setting>
    </settings>
</configuration>
```

If not set, the default value for the setting `chaining.socks5.methods` is 
set to `NO_AUTHENTICATION_REQUIRED`

#### Chaining to the Other SOCKS Server Using No Authentication

Because the default value for the setting `chaining.socks5.methods` is set 
to `NO_AUTHENTICATION_REQUIRED`, it is not required for 
`NO_AUTHENTICATION_REQUIRED` to be included in the setting 
`chaining.socks5.methods`.

However, if other authentication methods are to be used in addition to 
`NO_AUTHENTICATION_REQUIRED`, `NO_AUTHENTICATION_REQUIRED` must be 
included in the setting `chaining.socks5.methods`

Command line example:

```
./bin/jargyle start-server \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:23456 \
    "--setting=chaining.socks5.methods=NO_AUTHENTICATION_REQUIRED GSSAPI USERNAME_PASSWORD"
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:23456</value>
        </setting>
        <setting>
            <name>chaining.socks5.methods</name>
            <socks5.methods>
                <socks5.method>NO_AUTHENTICATION_REQUIRED</socks5.method>
                <socks5.method>GSSAPI</socks5.method>
                <socks5.method>USERNAME_PASSWORD</socks5.method>
            </socks5.methods>
        </setting>
    </settings>
</configuration>
```

#### Chaining to the Other SOCKS Server Using Username Password Authentication

To chain to the other SOCKS server using username password authentication, you 
will need to have the setting `chaining.socks5.methods` to have 
`USERNAME_PASSWORD` included. 

Command line example:

```
./bin/jargyle start-server \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:23456 \
    --setting=chaining.socks5.methods=USERNAME_PASSWORD
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:23456</value>
        </setting>
        <setting>
            <name>chaining.socks5.methods</name>
            <socks5.methods>
                <socks5.method>USERNAME_PASSWORD</socks5.method>
            </socks5.methods>
        </setting>
    </settings>
</configuration>
```

Also, you will need to have the settings 
`chaining.socks5.userpassauth.username` and 
`chaining.socks5.userpassauth.password` respectively specify the username 
and password for the other SOCKS5 server.

Command line example:

```
./bin/jargyle start-server \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:23456 \
    --setting=chaining.socks5.methods=USERNAME_PASSWORD \
    --setting=chaining.socks5.userpassauth.username=Aladdin \
    --setting=chaining.socks5.userpassauth.password=opensesame
    
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:23456</value>
        </setting>
        <setting>
            <name>chaining.socks5.methods</name>
            <socks5.methods>
                <socks5.method>USERNAME_PASSWORD</socks5.method>
            </socks5.methods>
        </setting>
        <setting>
            <name>chaining.socks5.userpassauth.username</name>
            <value>Aladdin</value>
        </setting>
        <setting>
            <name>chaining.socks5.userpassauth.password</name>
            <value>opensesame</value>
            <doc>If this configuration file was created by new-server-config-file, the password would be encrypted</doc>
        </setting>
    </settings>
</configuration>
```

If you do not want to have the password appear in any script or in any part of 
the command line history for security reasons, you can use the command line 
option `--enter-chaining-socks5-userpassauth-pass` instead. It will 
provide an interactive prompt for you to enter the password.

Command line example:

```
./bin/jargyle start-server \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:23456 \
    --setting=chaining.socks5.methods=USERNAME_PASSWORD \
    --setting=chaining.socks5.userpassauth.username=Aladdin \
    --enter-chaining-socks5-userpassauth-pass
```

#### Chaining to the Other SOCKS Server Using GSS-API Authentication

To chain to the other SOCKS server using GSS-API authentication, you will need 
to have the setting `chaining.socks5.methods` to have `GSSAPI` included.

Command line example:

```
./bin/jargyle start-server \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:23456 \
    --setting=chaining.socks5.methods=GSSAPI
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:23456</value>
        </setting>
        <setting>
            <name>chaining.socks5.methods</name>
            <socks5.methods>
                <socks5.method>GSSAPI</socks5.method>
            </socks5.methods>
        </setting>
    </settings>
</configuration>
```

Also, you will need to specify Java system properties to use a security 
mechanism that implements the GSS-API (for example, Kerberos is a security 
mechanism that implements the GSS-API), and you will also need to specify the 
GSS-API service name for the other SOCKS5 server.

The following are sufficient examples of using the Kerberos security mechanism:

Command line example:

```
export JARGYLE_OPTS="-Djavax.security.auth.useSubjectCredsOnly=false -Djava.security.auth.login.config=login.conf -Djava.security.krb5.conf=krb5.conf"
./bin/jargyle start-server \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:23456 \
    --setting=chaining.socks5.methods=GSSAPI \
    --setting=chaining.socks5.gssapiauth.serviceName=rcmd/127.0.0.1 
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
<!--
Before running this file, the following must be executed:
export JARGYLE_OPTS="-Djavax.security.auth.useSubjectCredsOnly=false -Djava.security.auth.login.config=login.conf -Djava.security.krb5.conf=krb5.conf"
-->
    <settings>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:23456</value>
        </setting>
        <setting>
            <name>chaining.socks5.methods</name>
            <socks5.methods>
                <socks5.method>GSSAPI</socks5.method>
            </socks5.methods>
        </setting>
        <setting>
            <name>chaining.socks5.gssapiauth.serviceName</name>
            <value>rcmd/127.0.0.1</value>
        </setting>
    </settings>
</configuration>
```

The Java system property `javax.security.auth.useSubjectCredsOnly` with 
the value `false` disables JAAS-based authentication to obtain the credentials 
directly and lets the underlying security mechanism obtain them instead.

The Java system property `java.security.auth.login.config` with the value 
`login.conf` provides a JAAS configuration file for the underlying security 
mechanism.

`login.conf`:

```
com.sun.security.jgss.initiate {
  com.sun.security.auth.module.Krb5LoginModule required
  principal="alice"
  useKeyTab=true
  keyTab="alice.keytab"
  storeKey=true;
};
```

In `login.conf`, `alice` is a principal that is created by a Kerberos 
administrator. 

Also in `login.conf`, `alice.keytab` is a keytab file also created by a 
Kerberos administrator that contains the aforementioned principal and its 
respective encrypted key.  

The Java system property `java.security.krb5.conf` with the value 
`krb5.conf` provides the Kerberos configuration file that points to the 
Kerberos Key Distribution Center (KDC) for authentication.   

`krb5.conf`:

```
[libdefaults]
    kdc_realm = EXAMPLE.COM
    default_realm = EXAMPLE.COM
    udp_preference_limit = 4096
    kdc_tcp_port = 12345
    kdc_udp_port = 12345

[realms]
    EXAMPLE.COM = {
        kdc = 127.0.0.1:12345
    }
```

In `krb5.conf`, a KDC is defined as running at the address `127.0.0.1` on 
port `12345` with its realm as `EXAMPLE.COM`. (In a production environment, 
the address `127.0.0.1` should be replaced by the actual address or name of 
the machine of where the KDC resides. Also, in a production environment, the 
realm `EXAMPLE.COM` should be replaced by an actual realm provided by a 
Kerberos administrator.)

The setting `chaining.socks5.gssapiauth.serviceName` with the value 
`rcmd/127.0.0.1` is the GSS-API service name (or the Kerberos service 
principal) for the other SOCKS server residing at the address `127.0.0.1`. (In 
a production environment, the address `127.0.0.1` should be replaced by the 
name of the machine of where the other SOCKS server resides.)

### Resolving Host Names From the Other SOCKS5 Server

Before discussing host name resolution from the other SOCKS5 server, a brief 
explanation of the server's internals:

The server uses sockets to interact with the external world.

-   Under the CONNECT command, it uses a socket that connects to the desired 
target server. In this documentation, this socket is called the server-facing 
socket.
-   Under the BIND command, it uses a socket that listens for an inbound socket. 
In this documentation, this socket is called the listen socket.
-   Under the UDP ASSOCIATE command, it uses a UDP socket that sends and 
receives datagram packets to and from peer UDP sockets. In this documentation, 
this UDP socket is called the peer-facing UDP socket.

The server also uses a host resolver to resolve host names for the 
aforementioned sockets and for 
[the RESOLVE command](reference.html#the-socks5-resolve-command).

When the server is chained to another SOCKS5 server, the aforementioned sockets 
that the server uses become SOCKS5-enabled, meaning that their traffic is routed 
through the other SOCKS5 server.

It is similar for the host resolver. When the server is chained to another 
SOCKS5 server, the host resolver that the server uses becomes SOCKS5-enabled, 
meaning that it can use the other SOCKS5 server to resolve host names provided 
that the other SOCKS5 server supports the SOCKS5 RESOLVE command. However, this 
functionality for the host resolver is disabled by default making the host 
resolver resolve host names through the local system.

Therefore, default host name resolution from the other SOCKS5 server is 
performed but has the following limitations:

Default host name resolution from the other SOCKS5 server OCCURS ONLY...

-   ...under the CONNECT command when the server-facing socket makes an 
extemporaneous outbound connection. Preparation is omitted for the server-facing 
socket. Such preparation includes applying the specified socket settings for the 
server-facing socket, resolving the target host name before connecting, and 
setting the specified timeout in milliseconds on waiting for the server-facing 
socket to connect. The host resolver is not used in resolving the target host 
name. When the server-facing socket is SOCKS5-enabled, the target host name is 
resolved by the other SOCKS5 server and not through the local system.

Default host name resolution from the other SOCKS5 server DOES NOT OCCUR...

-   ...under the CONNECT command when the server-facing socket makes a prepared 
outbound connection. Preparation for the server-facing socket includes resolving 
the target host name before connecting. The host resolver is used in resolving 
the target host name. Because of its default functionality, the host resolver 
resolves the target host name through the local system.
-   ...under the BIND command when resolving the binding host name for the 
listen socket. The host resolver is used in resolving the binding host name for 
the listen socket. Because of its default functionality, the host resolver 
resolves the binding host name for the listen socket through the local system.
-   ...under the UDP ASSOCIATE command when resolving the host name for an 
outbound datagram packet. The host resolver is used in resolving the host name 
for an outbound datagram packet. Because of its default functionality, the host 
resolver resolves the host name for an outbound datagram packet through the 
local system.
-   ...under the RESOLVE command when resolving the provided host name. The host 
resolver is used in resolving the provided host name. Because of its default 
functionality, the host resolver resolves the provided host name through the 
local system.

If you prefer to have host name resolution from the other SOCKS5 server without 
the aforementioned limitations, you would need to set the setting 
`chaining.socks5.useResolveCommand` to `true`. This setting enables the 
host resolver to use the SOCKS5 RESOLVE command on the other SOCKS5 server to 
resolve host names. This setting can only be used if the other SOCKS5 server 
supports the SOCKS5 RESOLVE command.

Command line example:

```
./bin/jargyle start-server \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:23456 \
    --setting=chaining.socks5.useResolveCommand=true
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:23456</value>
        </setting>
        <setting>
            <name>chaining.socks5.useResolveCommand</name>
            <value>true</value>
        </setting>
    </settings>
</configuration>
```

## Chaining to a Specified Chain of Other SOCKS Servers

You can have the server chained to a specified chain of other SOCKS servers, 
meaning that its traffic can be routed through the specified chain of the other 
SOCKS servers. To have the server chained to a specified chain of other SOCKS 
servers, you will need to have the setting `chaining.socksServerUri` 
specified multiple times with each setting specifying a SOCKS server as a URI.

Command line example:

```
./bin/jargyle start-server \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:23456 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:65432 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:34567
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:23456</value>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:65432</value>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:34567</value>
        </setting>
    </settings>
</configuration>
```

To specify the settings regarding a SOCKS server in the chain, the settings 
regarding a SOCKS server will need to be placed after that specified SOCKS 
server but before the next specified SOCKS server if any.

Command line example:

```
./bin/jargyle start-server \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:23456 \
    --setting=chaining.socks5.methods=GSSAPI \
    --setting=chaining.socks5.gssapiauth.serviceName=rcmd/127.0.0.1 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:65432 \
    --setting=chaining.socks5.methods=USERNAME_PASSWORD \
    --setting=chaining.socks5.userpassauth.username=Aladdin \
    --setting=chaining.socks5.userpassauth.password=opensesame \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:34567 \
    --setting=chaining.socks5.useResolveCommand=true
    
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:23456</value>
        </setting>
        <setting>
            <name>chaining.socks5.methods</name>
            <socks5.methods>
                <socks5.method>GSSAPI</socks5.method>
            </socks5.methods>
        </setting>
        <setting>
            <name>chaining.socks5.gssapiauth.serviceName</name>
            <value>rcmd/127.0.0.1</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:65432</value>
        </setting>
        <setting>
            <name>chaining.socks5.methods</name>
            <socks5.methods>
                <socks5.method>USERNAME_PASSWORD</socks5.method>
            </socks5.methods>
        </setting>
        <setting>
            <name>chaining.socks5.userpassauth.username</name>
            <value>Aladdin</value>
        </setting>
        <setting>
            <name>chaining.socks5.userpassauth.password</name>
            <value>opensesame</value>
            <doc>If this configuration file was created by new-server-config-file, the password would be encrypted</doc>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:34567</value>
        </setting>        
        <setting>
            <name>chaining.socks5.useResolveCommand</name>
            <value>true</value>
        </setting>    
    </settings>
</configuration>
```

The known limitations of chaining to a specified chain of other SOCKS servers 
include the following:

-   Only TCP traffic can be routed through the chain. The server will attempt to 
route any UDP traffic through the last SOCKS server of the chain.

## Chaining to Multiple Specified Chains of Other SOCKS Servers

You can have the server chained to multiple specified chains of other SOCKS 
servers, meaning that its traffic can be routed through one of the specified 
chains of other SOCKS servers. To have the server chained to multiple specified 
chains of other SOCKS servers, you will need to have a route ID assigned at the 
end of each [chain](#chaining-to-a-specified-chain-of-other-socks-servers) by 
using the setting `chaining.routeId`

Command line example:

```
./bin/jargyle start-server \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:11111 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:11112 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:11113 \
    --setting=chaining.routeId=alpha \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:22221 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:22222 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:22223 \
    --setting=chaining.routeId=beta \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:33331 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:33332 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:33333 \
    --setting=chaining.routeId=delta
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:11111</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:11112</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:11113</value>
        </setting>
        <setting>
            <name>chaining.routeId</name>
            <value>alpha</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:22221</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:22222</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:22223</value>
        </setting>
        <setting>
            <name>chaining.routeId</name>
            <value>beta</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:33331</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:33332</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:33333</value>
        </setting>
        <setting>
            <name>chaining.routeId</name>
            <value>delta</value>
        </setting>
    </settings>
</configuration>
```

From the aforementioned examples: 

-   The chain consisting of `socks5://127.0.0.1:11111`, 
`socks5://127.0.0.1:11112`, and `socks5://127.0.0.1:11113` is assigned 
the route ID of `alpha`
-   The chain consisting of `socks5://127.0.0.1:22221`, 
`socks5://127.0.0.1:22222`, and `socks5://127.0.0.1:22223` is assigned 
the route ID of `beta`
-   The chain consisting of `socks5://127.0.0.1:33331`, 
`socks5://127.0.0.1:33332`, and `socks5://127.0.0.1:33333` is assigned 
the route ID of `delta`

There is another route that is assigned a route ID. That route is the direct 
route. The direct route uses no chain to route the traffic through. It is 
assigned by default a route ID of `lastRoute`.

To omit the direct route from being included, have the last chain not assigned a 
route ID from the setting `chaining.routeId`.

Command line example:

```
./bin/jargyle start-server \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:11111 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:11112 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:11113 \
    --setting=chaining.routeId=alpha \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:22221 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:22222 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:22223 \
    --setting=chaining.routeId=beta \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:33331 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:33332 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:33333
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:11111</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:11112</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:11113</value>
        </setting>
        <setting>
            <name>chaining.routeId</name>
            <value>alpha</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:22221</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:22222</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:22223</value>
        </setting>
        <setting>
            <name>chaining.routeId</name>
            <value>beta</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:33331</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:33332</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:33333</value>
        </setting>
    </settings>
</configuration>
```

From the aforementioned examples: 

-   The chain consisting of `socks5://127.0.0.1:11111`, 
`socks5://127.0.0.1:11112`, and `socks5://127.0.0.1:11113` is assigned 
the route ID of `alpha`
-   The chain consisting of `socks5://127.0.0.1:22221`, 
`socks5://127.0.0.1:22222`, and `socks5://127.0.0.1:22223` is assigned 
the route ID of `beta`
-   The chain consisting of `socks5://127.0.0.1:33331`, 
`socks5://127.0.0.1:33332`, and `socks5://127.0.0.1:33333` is assigned 
by default the route ID of `lastRoute`

To change the route ID assigned to the last route, you can set the setting 
`lastRouteId` to the route ID you want assigned to the last route.

Command line example:

```
./bin/jargyle start-server \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:11111 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:11112 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:11113 \
    --setting=chaining.routeId=alpha \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:22221 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:22222 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:22223 \
    --setting=chaining.routeId=beta \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:33331 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:33332 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:33333 \
    --setting=lastRouteId=omega
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:11111</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:11112</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:11113</value>
        </setting>
        <setting>
            <name>chaining.routeId</name>
            <value>alpha</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:22221</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:22222</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:22223</value>
        </setting>
        <setting>
            <name>chaining.routeId</name>
            <value>beta</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:33331</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:33332</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:33333</value>
        </setting>
        <setting>
            <name>lastRouteId</name>
            <value>omega</value>
        </setting>
    </settings>
</configuration>
```

You can also set the setting `routeSelectionStrategy` to specify the 
selection strategy for the next route. The default is `CYCLICAL`.

Command line example:

```
./bin/jargyle start-server \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:11111 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:11112 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:11113 \
    --setting=chaining.routeId=alpha \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:22221 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:22222 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:22223 \
    --setting=chaining.routeId=beta \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:33331 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:33332 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:33333 \
    --setting=lastRouteId=omega \
    --setting=routeSelectionStrategy=RANDOM
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:11111</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:11112</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:11113</value>
        </setting>
        <setting>
            <name>chaining.routeId</name>
            <value>alpha</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:22221</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:22222</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:22223</value>
        </setting>
        <setting>
            <name>chaining.routeId</name>
            <value>beta</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:33331</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:33332</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:33333</value>
        </setting>
        <setting>
            <name>lastRouteId</name>
            <value>omega</value>
        </setting>
        <setting>
            <name>routeSelectionStrategy</name>
            <value>RANDOM</value>
        </setting>
    </settings>
</configuration>
```

You can also set the setting `routeSelectionLogAction` to specify the 
logging action to take if a route is selected.

Command line example:

```
./bin/jargyle start-server \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:11111 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:11112 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:11113 \
    --setting=chaining.routeId=alpha \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:22221 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:22222 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:22223 \
    --setting=chaining.routeId=beta \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:33331 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:33332 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:33333 \
    --setting=lastRouteId=omega \
    --setting=routeSelectionStrategy=RANDOM \
    --setting=routeSelectionLogAction=LOG_AS_INFO
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:11111</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:11112</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:11113</value>
        </setting>
        <setting>
            <name>chaining.routeId</name>
            <value>alpha</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:22221</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:22222</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:22223</value>
        </setting>
        <setting>
            <name>chaining.routeId</name>
            <value>beta</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:33331</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:33332</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:33333</value>
        </setting>
        <setting>
            <name>lastRouteId</name>
            <value>omega</value>
        </setting>
        <setting>
            <name>routeSelectionStrategy</name>
            <value>RANDOM</value>
        </setting>
        <setting>
            <name>routeSelectionLogAction</name>
            <value>LOG_AS_INFO</value>
        </setting>
    </settings>
</configuration>
```

## Using Rules to Manage Traffic

A rule consists of the following:

-   Rule conditions: fields that altogether evaluate as true if they match a 
specific instance of traffic
-   Rule results: fields that are applied if the aforementioned rule conditions 
evaluate as true for matching a specific instance of traffic

On the command line, a rule consists of a space separated list of both rule 
conditions and rule results. 

In the server configuration file, a rule is expressed as a `<rule/>` XML 
element with a `<ruleConditions/>` XML element and a `<ruleResults/>` XML 
element. 

See [Rule Conditions](#rule-conditions) and [Rule Results](#rule-results) for 
more information.

To specify a rule, you would need to have the setting `rule` specify the rule. 

Command line example:

```
./bin/jargyle start-server --setting=rule=firewallAction=ALLOW
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>rule</name>
            <rule>
                <ruleConditions/>
                <ruleResults>
                    <ruleResult>
                        <name>firewallAction</name>
                        <value>ALLOW</value>
                    </ruleResult>
                </ruleResults>
            </rule>
            <doc>Allows all forms of traffic</doc>
        </setting>
    </settings>
</configuration>
```

To specify multiple rules, you would need to have the setting `rule` specified 
multiple times with each setting specifying another rule.

Command line example:

```
./bin/jargyle start-server \
    "--setting=rule=socks5.command=CONNECT socks5.desiredDestinationPort=80 socks5.desiredDestinationPort=443 firewallAction=ALLOW" \
    "--setting=rule=socks5.command=CONNECT firewallAction=DENY" \
    --setting=rule=firewallAction=ALLOW
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>rule</name>
            <rule>
                <ruleConditions>
                    <ruleCondition>
                        <name>socks5.command</name>
                        <value>CONNECT</value>
                    </ruleCondition>
                    <ruleCondition>
                        <name>socks5.desiredDestinationPort</name>
                        <value>80</value>
                    </ruleCondition>
                    <ruleCondition>
                        <name>socks5.desiredDestinationPort</name>
                        <value>443</value>
                    </ruleCondition>
                </ruleConditions>
                <ruleResults>
                    <ruleResult>
                        <name>firewallAction</name>
                        <value>ALLOW</value>
                    </ruleResult>
                </ruleResults>
            </rule>
            <doc>Allows the CONNECT command to any server on port 80 or 443</doc>
        </setting>
        <setting>
            <name>rule</name>
            <rule>
                <ruleConditions>
                    <ruleCondition>
                        <name>socks5.command</name>
                        <value>CONNECT</value>
                    </ruleCondition>
                </ruleConditions>
                <ruleResults>
                    <ruleResult>
                        <name>firewallAction</name>
                        <value>DENY</value>
                    </ruleResult>
                </ruleResults>
            </rule>
            <doc>Denies the CONNECT command to any server on any other port</doc>
        </setting>
        <setting>
            <name>rule</name>
            <rule>
                <ruleConditions/>
                <ruleResults>
                    <ruleResult>
                        <name>firewallAction</name>
                        <value>ALLOW</value>
                    </ruleResult>
                </ruleResults>
            </rule>
            <doc>Allow anything else</doc>
        </setting>    
    </settings>
</configuration>
```

When a specific instance of traffic is matched by the first rule, that rule is 
applied and the rest of the rules are ignored. Therefore it is best to have more 
specific rules specified first and have less specific rules specified last.

### Rule Conditions

On the command line, rule conditions consist of a space separated list of rule 
conditions. Each rule condition consists of the syntax of `NAME=VALUE` where 
`NAME` is expressed as the name of the rule condition and `VALUE` is expressed 
as the value assigned to the rule condition. 

Partial command line example:

```
clientAddress=127.0.0.1 clientAddress=0:0:0:0:0:0:0:1
```

In the server configuration file, rule conditions are expressed in a 
`<ruleConditions/>` XML element with zero to many `<ruleCondition/>` XML 
elements. Each `<ruleCondition/>` XML element contains a `<name/>` XML 
element for the name of the rule condition and the `<value/>` XML element of 
the value assigned to the rule condition.

Partial server configuration file example:

```
<ruleConditions>
    <ruleCondition>
        <name>clientAddress</name>
        <value>127.0.0.1</value>
    </ruleCondition>
    <ruleCondition>
        <name>clientAddress</name>
        <value>0:0:0:0:0:0:0:1</value>
    </ruleCondition>
</ruleConditions>
```

Rule conditions within a rule are evaluated as true if each group of one or more 
rule conditions with the same name is evaluated as true. A group of one or more 
rule conditions with the same name is evaluated as true if at least one of the 
rule conditions within that group is evaluated as true. Zero rule conditions 
within a rule are evaluated as true.

Partial server configuration file examples:

```
<ruleConditions>
    <ruleCondition>
        <name>clientAddress</name>
        <value>127.0.0.1</value>
    </ruleCondition>
    <ruleCondition>
        <name>clientAddress</name>
        <value>0:0:0:0:0:0:0:1</value>
    </ruleCondition>
</ruleConditions>
<!-- evaluates as true if the client address is the IPv4 loopback address or the IPv6 loopback address -->
```

```
<ruleConditions>
    <ruleCondition>
        <name>socks5.method</name>
        <value>USERNAME_PASSWORD</value>
    </ruleCondition>
    <ruleCondition>
        <name>socks5.user</name>
        <value>guest</value>
    </ruleCondition>
    <ruleCondition>
        <name>socks5.user</name>
        <value>specialuser</value>
    </ruleCondition>
</ruleConditions>
<!-- evaluates as true if username password authentication was used and the user is either 'guest' or 'specialuser' -->
```

```
<ruleConditions/>
<!-- evaluates as true since there are no rule conditions given -->
```

A complete listing of rule conditions can be found in the 
[settings help](cli.html#settings-help) under `SETTING VALUE SYNTAXES` > 
`GENERAL_RULE_CONDITIONS` and `SETTING VALUE SYNTAXES` > 
`SOCKS5_RULE_CONDITIONS`.

### Rule Results

On the command line, rule results consist of a space separated list of rule 
results. Each rule result consists of the syntax of `NAME=VALUE` where `NAME` 
is expressed as the name of the rule result and `VALUE` is expressed as the 
value assigned to the rule result. 

Partial command line example:

```
firewallAction=ALLOW firewallActionLogAction=LOG_AS_INFO
```

In the server configuration file, rule result are expressed in a `<ruleResults/>` 
XML element with zero to many `<ruleResult/>` XML elements. Each 
`<ruleResult/>` XML element contains a `<name/>` XML element for the name of 
the rule result and the `<value/>` XML element of the value assigned to the 
rule result.

Partial configuration file example:

```
<ruleResults>
    <ruleResult>
        <name>firewallAction</name>
        <value>ALLOW</value>
    </ruleResult>
    <ruleResult>
        <name>firewallActionLogAction</name>
        <value>LOG_AS_INFO</value>
    </ruleResult>
</ruleResults>
```

Unless otherwise stated, if a rule result of the same name appears more than 
once in the space separated list or in the `<ruleResults/>` XML element, then 
only the last rule result of the same name is recognized.

A complete listing of rule results can be found in the 
[settings help](cli.html#settings-help) under `SETTING VALUE SYNTAXES` > 
`GENERAL_RULE_RESULTS` and `SETTING VALUE SYNTAXES` > 
`SOCKS5_RULE_RESULTS`.

### Allowing or Denying Traffic

To allow or deny a specific instance of traffic, you will need the following 
rule result:

-   `firewallAction`

The value given to the rule result must be either of the following values:

-   `ALLOW`
-   `DENY`

This rule result can be used with the following rule conditions:

-   `clientAddress`
-   `socks5.command`
-   `socks5.desiredDestinationAddress`
-   `socks5.desiredDestinationPort`
-   `socks5.method`
-   `socks5.secondServerBoundAddress`
-   `socks5.secondServerBoundPort`
-   `socks5.serverBoundAddress`
-   `socks5.serverBoundPort`
-   `socks5.udp.inbound.desiredDestinationAddress`
-   `socks5.udp.inbound.desiredDestinationPort`
-   `socks5.udp.inbound.sourceAddress`
-   `socks5.udp.inbound.sourcePort`
-   `socks5.udp.outbound.desiredDestinationAddress`
-   `socks5.udp.outbound.desiredDestinationPort`
-   `socks5.udp.outbound.sourceAddress`
-   `socks5.udp.outbound.sourcePort`
-   `socks5.user`
-   `socksServerAddress`

**Note**: for any other rule results to be applied, the rule result 
`firewallAction` with the value of `ALLOW` must be present.

You can also specify the logging action to take if the rule result 
`firewallAction` is applied by adding the following rule result:

-   `firewallActionLogAction`

The rule result `firewallActionLogAction` is optional.

Command line example:

```
./bin/jargyle start-server \
    "--setting=rule=socks5.command=BIND socks5.command=UDP_ASSOCIATE firewallAction=DENY firewallActionLogAction=LOG_AS_WARNING" \
    --setting=rule=firewallAction=ALLOW
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>rule</name>
            <rule>
                <ruleConditions>
                    <ruleCondition>
                        <name>socks5.command</name>
                        <value>BIND</value>
                    </ruleCondition>
                    <ruleCondition>
                        <name>socks5.command</name>
                        <value>UDP_ASSOCIATE</value>
                    </ruleCondition>
                </ruleConditions>
                <ruleResults>
                    <ruleResult>
                        <name>firewallAction</name>
                        <value>DENY</value>
                    </ruleResult>
                    <ruleResult>
                        <name>firewallActionLogAction</name>
                        <value>LOG_AS_WARNING</value>
                    </ruleResult>                
                </ruleResults>
            </rule>
            <doc>Deny any BIND or UDP ASSOCIATE commands and log as a warning message if they are denied</doc>
        </setting>
        <setting>
            <name>rule</name>
            <rule>
                <ruleConditions/>
                <ruleResults>
                    <ruleResult>
                        <name>firewallAction</name>
                        <value>ALLOW</value>
                    </ruleResult>
                </ruleResults>
            </rule>
            <doc>Allow anything else</doc>
        </setting>    
    </settings>
</configuration>
```

### Allowing a Limited Number of Simultaneous Instances of Traffic

To allow a limited number of simultaneous specific instances of traffic, you 
will need the following rule result:

-   `firewallActionAllowLimit`

The value given to the rule result must be an integer between 0 (inclusive) and 
2147483647 (inclusive)

This rule result can be used with the following rule conditions:

-   `clientAddress`
-   `socks5.command`
-   `socks5.desiredDestinationAddress`
-   `socks5.desiredDestinationPort`
-   `socks5.method`
-   `socks5.secondServerBoundAddress`
-   `socks5.secondServerBoundPort`
-   `socks5.serverBoundAddress`
-   `socks5.serverBoundPort`
-   `socks5.user`
-   `socksServerAddress`

You can also specify the logging action to take if the limit on the number of 
simultaneous specific instances of traffic has been reached by adding the 
following rule result:

-   `firewallActionAllowLimitReachedLogAction`

The rule result `firewallActionAllowLimitReachedLogAction` is optional.

Command line example:

```
./bin/jargyle start-server \
    "--setting=rule=socks5.method=USERNAME_PASSWORD socks5.user=guest firewallAction=ALLOW firewallActionAllowLimit=50 firewallActionAllowLimitReachedLogAction=LOG_AS_INFO" \
    --setting=rule=firewallAction=ALLOW
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>rule</name>
            <rule>
                <ruleConditions>
                    <ruleCondition>
                        <name>socks5.method</name>
                        <value>USERNAME_PASSWORD</value>
                    </ruleCondition>
                    <ruleCondition>
                        <name>socks5.user</name>
                        <value>guest</value>
                    </ruleCondition>
                </ruleConditions>
                <ruleResults>
                    <ruleResult>
                        <name>firewallAction</name>
                        <value>ALLOW</value>
                    </ruleResult>
                    <ruleResult>
                        <name>firewallActionAllowLimit</name>
                        <value>50</value>
                    </ruleResult>                
                    <ruleResult>
                        <name>firewallActionAllowLimitReachedLogAction</name>
                        <value>LOG_AS_INFO</value>
                    </ruleResult>
                </ruleResults>
            </rule>
            <doc>Allow the user 'guest' from username password authentication 50 simultaneous connections and log as an informational message that the limit has been reached</doc>
        </setting>
        <setting>
            <name>rule</name>
            <rule>
                <ruleConditions/>
                <ruleResults>
                    <ruleResult>
                        <name>firewallAction</name>
                        <value>ALLOW</value>
                    </ruleResult>
                </ruleResults>
            </rule>
            <doc>Allow anything else</doc>
        </setting>
    </settings>
</configuration>
```

### Routing Traffic

To route traffic, you can use the following rule results:

-   `routeSelectionStrategy`: Specifies the selection strategy for the next 
route (This rule result is optional. If this rule result is not specified, the 
setting `routeSelectionStrategy` is used.)
-   `selectableRouteId`: Specifies the ID for a selectable 
[route](#chaining-to-multiple-specified-chains-of-other-socks-servers) (This 
rule result is optional. This rule result can be specified multiple times with 
each rule result specifying another ID for a selectable route. If this rule 
result is not specified, all of the routes defined by the settings 
`chaining.routeId` and `lastRouteId` are selectable.)

These rule results can be used with the following rule conditions:

-   `clientAddress`
-   `socks5.command`
-   `socks5.desiredDestinationAddress`
-   `socks5.desiredDestinationPort`
-   `socks5.method`
-   `socks5.user`
-   `socksServerAddress`

You can also specify the logging action to take if a route is selected by adding 
the following rule result:

-   `routeSelectionLogAction`

The rule result `routeSelectionLogAction` is optional. If the rule result is 
not specified, the setting `routeSelectionLogAction` is used.

Command line example:

```
./bin/jargyle start-server \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:11111 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:11112 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:11113 \
    --setting=chaining.routeId=alpha \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:22221 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:22222 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:22223 \
    --setting=chaining.routeId=beta \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:33331 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:33332 \
    --setting=chaining.socksServerUri=socks5://127.0.0.1:33333 \
    --setting=lastRouteId=omega \
    "--setting=rule=socks5.command=CONNECT firewallAction=ALLOW routeSelectionStrategy=RANDOM selectableRouteId=alpha selectableRouteId=beta routeSelectionLogAction=LOG_AS_INFO" \
    "--setting=rule=firewallAction=ALLOW routeSelectionStrategy=CYCLICAL selectableRouteId=omega"
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:11111</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:11112</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:11113</value>
        </setting>
        <setting>
            <name>chaining.routeId</name>
            <value>alpha</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:22221</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:22222</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:22223</value>
        </setting>
        <setting>
            <name>chaining.routeId</name>
            <value>beta</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:33331</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:33332</value>
        </setting>
        <setting>
            <name>chaining.socksServerUri</name>
            <value>socks5://127.0.0.1:33333</value>
        </setting>
        <setting>
            <name>lastRouteId</name>
            <value>omega</value>
        </setting>
        <setting>
            <name>rule</name>
            <rule>
                <ruleConditions>
                    <ruleCondition>
                        <name>socks5.command</name>
                        <value>CONNECT</value>
                    </ruleCondition>
                </ruleConditions>
                <ruleResults>
                    <ruleResult>
                        <name>firewallAction</name>
                        <value>ALLOW</value>
                    </ruleResult>
                    <ruleResult>
                        <name>routeSelectionStrategy</name>
                        <value>RANDOM</value>
                    </ruleResult>
                    <ruleResult>
                        <name>selectableRouteId</name>
                        <value>alpha</value>
                    </ruleResult>
                    <ruleResult>
                        <name>selectableRouteId</name>
                        <value>beta</value>
                    </ruleResult>
                    <ruleResult>
                        <name>routeSelectionLogAction</name>
                        <value>LOG_AS_INFO</value>
                    </ruleResult>
                </ruleResults>
            </rule>
            <doc>Randomly select either route 'alpha' or 'beta' when allowing the CONNECT command and log as an informational message the route that has been selected</doc>
        </setting>
        <setting>
            <name>rule</name>
            <rule>
                <ruleConditions/>
                <ruleResults>
                    <ruleResult>
                        <name>firewallAction</name>
                        <value>ALLOW</value>
                    </ruleResult>
                    <ruleResult>
                        <name>routeSelectionStrategy</name>
                        <value>CYCLICAL</value>
                    </ruleResult>
                    <ruleResult>
                        <name>selectableRouteId</name>
                        <value>omega</value>
                    </ruleResult>
                </ruleResults>
            </rule>
            <doc>Allow anything else to go through route 'omega'</doc>
        </setting>    
    </settings>
</configuration>
```

### Redirecting the Desired Destination

To redirect the desired destination, you will need either or both of the 
following rule results:

-   `socks5.desiredDestinationAddressRedirect`: Specifies the desired 
destination address redirect
-   `socks5.desiredDestinationPortRedirect`: Specifies the desired 
destination port redirect

These rule results can be used with the following rule conditions:

-   `clientAddress`
-   `socks5.command`
-   `socks5.desiredDestinationAddress`
-   `socks5.desiredDestinationPort`
-   `socks5.method`
-   `socks5.user`
-   `socksServerAddress`

You can also specify the logging action to take if the desired destination is 
redirected by adding the following rule result:

-   `socks5.desiredDestinationRedirectLogAction`

The rule result `socks5.desiredDestinationRedirectLogAction` is optional.

Command line example:

```
./bin/jargyle start-server \
    "--setting=rule=socks5.desiredDestinationAddress=discontinuedserver.com firewallAction=ALLOW socks5.desiredDestinationAddressRedirect=newserver.com socks5.desiredDestinationRedirectLogAction=LOG_AS_INFO" \
    --setting=rule=firewallAction=ALLOW
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>rule</name>
            <rule>
                <ruleConditions>
                    <ruleCondition>
                        <name>socks5.desiredDestinationAddress</name>
                        <value>discontinuedserver.com</value>
                    </ruleCondition>
                </ruleConditions>
                <ruleResults>
                    <ruleResult>
                        <name>firewallAction</name>
                        <value>ALLOW</value>
                    </ruleResult>
                    <ruleResult>
                        <name>socks5.desiredDestinationAddressRedirect</name>
                        <value>newserver.com</value>
                    </ruleResult>
                    <ruleResult>
                        <name>socks5.desiredDestinationRedirectLogAction</name>
                        <value>LOG_AS_INFO</value>
                    </ruleResult>
                </ruleResults>
            </rule>
            <doc>Redirect desired destination 'discontinuedserver.com' to 'newserver.com' and log as an informational message the redirection</doc>
        </setting>
        <setting>
            <name>rule</name>
            <rule>
                <ruleConditions/>
                <ruleResults>
                    <ruleResult>
                        <name>firewallAction</name>
                        <value>ALLOW</value>
                    </ruleResult>
                </ruleResults>
            </rule>
            <doc>Allow anything else</doc>
        </setting>    
    </settings>
</configuration>
```

### Configuring Sockets

To configure the sockets, you will need any of the following rule results:

-   `bindHost`: Specifies the binding host name or address for all sockets 
-   `bindTcpPortRange`: Specifies a binding 
[port range](reference.html#port-range) for all TCP sockets (This rule result 
can be specified multiple times with each rule result specifying another port 
range)
-   `bindUdpPortRange`: Specifies a binding 
[port range](reference.html#port-range) for all UDP sockets (This rule result 
can be specified multiple times with each rule result specifying another port 
range)
-   `clientSocketSetting`: Specifies a socket setting for the client socket 
(This rule result can be specified multiple times with each rule result 
specifying another socket setting)
-   `externalFacingBindHost`: Specifies the binding host name or address for 
all external-facing sockets 
-   `externalFacingBindTcpPortRange`: Specifies a binding 
[port range](reference.html#port-range) for all external-facing TCP sockets 
(This rule result can be specified multiple times with each rule result 
specifying another port range)
-   `externalFacingBindUdpPortRange`: Specifies a binding 
[port range](reference.html#port-range) for all external-facing UDP sockets 
(This rule result can be specified multiple times with each rule result 
specifying another port range)
-   `externalFacingSocketSetting`: Specifies a socket setting for all 
external-facing sockets (This rule result can be specified multiple times with 
each rule result specifying another socket setting)
-   `internalFacingBindHost`: Specifies the binding host name or address for 
all internal-facing sockets
-   `internalFacingBindTcpPortRange`: Specifies a binding 
[port range](reference.html#port-range) for all internal-facing TCP sockets 
(This rule result can be specified multiple times with each rule result 
specifying another port range)
-   `internalFacingBindUdpPortRange`: Specifies a binding 
[port range](reference.html#port-range) for all internal-facing UDP sockets 
(This rule result can be specified multiple times with each rule result 
specifying another port range)
-   `internalFacingSocketSetting`: Specifies a socket setting for all 
internal-facing sockets (This rule result can be specified multiple times with 
each rule result specifying another socket setting)
-   `socketSetting`: Specifies a socket setting for all sockets (This rule 
result can be specified multiple times with each rule result specifying another 
socket setting)
-   `socks5.onBind.inboundSocketSetting`: Specifies a socket setting for 
the inbound socket (This rule result can be specified multiple times with each 
rule result specifying another socket setting)
-   `socks5.onBind.listenBindHost`: Specifies the binding host name or 
address for the listen socket if the provided host address is all zeros
-   `socks5.onBind.listenBindPortRange`: Specifies a binding 
[port range](reference.html#port-range) for the listen socket if the provided 
port is zero (This rule result can be specified multiple times with each rule 
result specifying another port range)
-   `socks5.onBind.listenSocketSetting`: Specifies a socket setting for 
the listen socket (This rule result can be specified multiple times with each 
rule result specifying another socket setting)
-   `socks5.onCommand.bindHost`: Specifies the binding host name or address 
for all sockets 
-   `socks5.onCommand.bindTcpPortRange`: Specifies a binding 
[port range](reference.html#port-range) for all TCP sockets (This rule result 
can be specified multiple times with each rule result specifying another port 
range)
-   `socks5.onCommand.bindUdpPortRange`: Specifies a binding 
[port range](reference.html#port-range) for all UDP sockets (This rule result 
can be specified multiple times with each rule result specifying another port 
range)
-   `socks5.onCommand.externalFacingBindHost`: Specifies the binding host 
name or address for all external-facing sockets 
-   `socks5.onCommand.externalFacingBindTcpPortRange`: Specifies a 
binding [port range](reference.html#port-range) for all external-facing TCP 
sockets (This rule result can be specified multiple times with each rule result 
specifying another port range)
-   `socks5.onCommand.externalFacingBindUdpPortRange`: Specifies a 
binding [port range](reference.html#port-range) for all external-facing UDP 
sockets (This rule result can be specified multiple times with each rule result 
specifying another port range)
-   `socks5.onCommand.externalFacingSocketSetting`: Specifies a socket 
setting for all external-facing sockets (This rule result can be specified 
multiple times with each rule result specifying another socket setting)
-   `socks5.onCommand.internalFacingBindHost`: Specifies the binding 
host name or address for all internal-facing sockets
-   `socks5.onCommand.internalFacingBindTcpPortRange`: Specifies a 
binding [port range](reference.html#port-range) for all internal-facing TCP 
sockets (This rule result can be specified multiple times with each rule result 
specifying another port range)
-   `socks5.onCommand.internalFacingBindUdpPortRange`: Specifies a 
binding [port range](reference.html#port-range) for all internal-facing UDP 
sockets (This rule result can be specified multiple times with each rule result 
specifying another port range)
-   `socks5.onCommand.internalFacingSocketSetting`: Specifies a socket 
setting for all internal-facing sockets (This rule result can be specified 
multiple times with each rule result specifying another socket setting)
-   `socks5.onCommand.socketSetting`: Specifies a socket setting for all 
sockets (This rule result can be specified multiple times with each rule result 
specifying another socket setting)
-   `socks5.onConnect.prepareServerFacingSocket`: Specifies the boolean 
value to indicate if the server-facing socket is to be prepared before 
connecting (involves applying the specified socket settings, resolving the 
target host name, and setting the specified timeout on waiting to connect)
-   `socks5.onConnect.serverFacingBindHost`: Specifies the binding host 
name or address for the server-facing socket
-   `socks5.onConnect.serverFacingBindPortRange`: Specifies a binding 
[port range](reference.html#port-range) for the server-facing socket (This rule 
result can be specified multiple times with each rule result specifying another 
port range)
-   `socks5.onConnect.serverFacingConnectTimeout`: Specifies the timeout 
in milliseconds on waiting for the server-facing socket to connect (Value must 
be an integer between 1 (inclusive) and 2147483647 (inclusive))
-   `socks5.onConnect.serverFacingSocketSetting`: Specifies a socket 
setting for the server-facing socket (This rule result can be specified multiple 
times with each rule result specifying another socket setting)
-   `socks5.onUdpAssociate.clientFacingBindHost`: Specifies the binding 
host name or address for the client-facing UDP socket
-   `socks5.onUdpAssociate.clientFacingBindPortRange`: Specifies a 
binding [port range](reference.html#port-range) for the client-facing UDP socket 
(This rule result can be specified multiple times with each rule result 
specifying another port range)
-   `socks5.onUdpAssociate.clientFacingSocketSetting`: Specifies a 
socket setting for the client-facing UDP socket (This rule result can be 
specified multiple times with each rule result specifying another socket 
setting)
-   `socks5.onUdpAssociate.peerFacingBindHost`: Specifies the binding 
host name or address for the peer-facing UDP socket
-   `socks5.onUdpAssociate.peerFacingBindPortRange`: Specifies a 
binding [port range](reference.html#port-range) for the peer-facing UDP socket 
(This rule result can be specified multiple times with each rule result 
specifying another port range)
-   `socks5.onUdpAssociate.peerFacingSocketSetting`: Specifies a socket 
setting for the peer-facing UDP socket (This rule result can be specified 
multiple times with each rule result specifying another socket setting)

These rule results can be used with the following rule conditions:

-   `clientAddress`
-   `socks5.command`
-   `socks5.desiredDestinationAddress`
-   `socks5.desiredDestinationPort`
-   `socks5.method`
-   `socks5.user`
-   `socksServerAddress`

The rule result `socks5.onBind.inboundSocketSetting` can also be used with 
the following rule conditions:

-   `socks5.serverBoundAddress`
-   `socks5.serverBoundPort`

Command line example:

```
./bin/jargyle start-server \
    "--setting=rule=socks5.command=CONNECT socks5.desiredDestinationAddress=specialserver.com firewallAction=ALLOW socks5.onConnect.prepareServerFacingSocket=true socks5.onConnect.serverFacingSocketSetting=SO_RCVBUF=256 socks5.onConnect.serverFacingSocketSetting=SO_SNDBUF=256" \
    --setting=rule=firewallAction=ALLOW
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>rule</name>
            <rule>
                <ruleConditions>
                    <ruleCondition>
                        <name>socks5.command</name>
                        <value>CONNECT</value>
                    </ruleCondition>            
                    <ruleCondition>
                        <name>socks5.desiredDestinationAddress</name>
                        <value>specialserver.com</value>
                    </ruleCondition>
                </ruleConditions>
                <ruleResults>
                    <ruleResult>
                        <name>firewallAction</name>
                        <value>ALLOW</value>
                    </ruleResult>
                    <ruleResult>
                        <name>socks5.onConnect.prepareServerFacingSocket</name>
                        <value>true</value>
                    </ruleResult>
                    <ruleResult>
                        <name>socks5.onConnect.serverFacingSocketSetting</name>
                        <socketSetting>
                            <name>SO_RCVBUF</name>
                            <value>256</value>
                        </socketSetting>
                    </ruleResult>
                    <ruleResult>
                        <name>socks5.onConnect.serverFacingSocketSetting</name>
                        <socketSetting>
                            <name>SO_SNDBUF</name>
                            <value>256</value>
                        </socketSetting>
                    </ruleResult>
                </ruleResults>
            </rule>
            <doc>Allow the CONNECT command to connect to 'specialserver.com' and configure the server-facing socket for the CONNECT command</doc>
        </setting>
        <setting>
            <name>rule</name>
            <rule>
                <ruleConditions/>
                <ruleResults>
                    <ruleResult>
                        <name>firewallAction</name>
                        <value>ALLOW</value>
                    </ruleResult>
                </ruleResults>
            </rule>
            <doc>Allow anything else</doc>
        </setting>
    </settings>
</configuration>
```

### Configuring Relay Settings

To configure the relay settings, you will need any of the following rule results:

-   `socks5.onBind.relayBufferSize`: Specifies the buffer size in bytes 
for relaying the data (Value must be an integer between 1 (inclusive) and 
2147483647 (inclusive))
-   `socks5.onBind.relayIdleTimeout`: Specifies the timeout in 
milliseconds on relaying no data (Value must be an integer between 1 (inclusive) 
and 2147483647 (inclusive))
-   `socks5.onCommand.relayBufferSize`: Specifies the buffer size in bytes 
for relaying the data (Value must be an integer between 1 (inclusive) and 
2147483647 (inclusive))
-   `socks5.onCommand.relayIdleTimeout`: Specifies the timeout in 
milliseconds on relaying no data (Value must be an integer between 1 (inclusive) 
and 2147483647 (inclusive))
-   `socks5.onConnect.relayBufferSize`: Specifies the buffer size in bytes 
for relaying the data (Value must be an integer between 1 (inclusive) and 
2147483647 (inclusive))
-   `socks5.onConnect.relayIdleTimeout`: Specifies the timeout in 
milliseconds on relaying no data (Value must be an integer between 1 (inclusive) 
and 2147483647 (inclusive))
-   `socks5.onUdpAssociate.relayBufferSize`: Specifies the buffer size in 
bytes for relaying the data (Value must be an integer between 1 (inclusive) and 
2147483647 (inclusive))
-   `socks5.onUdpAssociate.relayIdleTimeout`: Specifies the timeout in 
milliseconds on relaying no data (Value must be an integer between 1 (inclusive) 
and 2147483647 (inclusive))

These rule results can be used with the following rule conditions:

-   `clientAddress`
-   `socks5.command`
-   `socks5.desiredDestinationAddress`
-   `socks5.desiredDestinationPort`
-   `socks5.method`
-   `socks5.secondServerBoundAddress`
-   `socks5.secondServerBoundPort`
-   `socks5.serverBoundAddress`
-   `socks5.serverBoundPort`
-   `socks5.user`
-   `socksServerAddress`

Command line example:

```
./bin/jargyle start-server \
    "--setting=rule=socks5.command=CONNECT socks5.desiredDestinationAddress=intermittent-idling-server.com firewallAction=ALLOW socks5.onConnect.relayIdleTimeout=1024000" \
    --setting=rule=firewallAction=ALLOW
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>rule</name>
            <rule>
                <ruleConditions>
                    <ruleCondition>
                        <name>socks5.command</name>
                        <value>CONNECT</value>
                    </ruleCondition>            
                    <ruleCondition>
                        <name>socks5.desiredDestinationAddress</name>
                        <value>intermittent-idling-server.com</value>
                    </ruleCondition>
                </ruleConditions>
                <ruleResults>
                    <ruleResult>
                        <name>firewallAction</name>
                        <value>ALLOW</value>
                    </ruleResult>
                    <ruleResult>
                        <name>socks5.onConnect.relayIdleTimeout</name>
                        <value>1024000</value>
                    </ruleResult>
                </ruleResults>
            </rule>
            <doc>Allow the CONNECT command to connect to 'intermittent-idling-server.com' with a relay idle timeout of 1024000 milliseconds (1024 seconds)</doc>
        </setting>
        <setting>
            <name>rule</name>
            <rule>
                <ruleConditions/>
                <ruleResults>
                    <ruleResult>
                        <name>firewallAction</name>
                        <value>ALLOW</value>
                    </ruleResult>
                </ruleResults>
            </rule>
            <doc>Allow anything else</doc>
        </setting>    
    </settings>
</configuration>
```

### Limiting Relay Bandwidth

To limit the relay bandwidth, you will need any of the following rule results:

-   `socks5.onBind.relayInboundBandwidthLimit`: Specifies the upper 
limit on bandwidth in bytes per second of receiving inbound data to be relayed
-   `socks5.onBind.relayOutboundBandwidthLimit`: Specifies the upper 
limit on bandwidth in bytes per second of receiving outbound data to be relayed
-   `socks5.onCommand.relayInboundBandwidthLimit`: Specifies the upper 
limit on bandwidth in bytes per second of receiving inbound data to be relayed
-   `socks5.onCommand.relayOutboundBandwidthLimit`: Specifies the upper 
limit on bandwidth in bytes per second of receiving outbound data to be relayed
-   `socks5.onConnect.relayInboundBandwidthLimit`: Specifies the upper 
limit on bandwidth in bytes per second of receiving inbound data to be relayed
-   `socks5.onConnect.relayOutboundBandwidthLimit`: Specifies the upper 
limit on bandwidth in bytes per second of receiving outbound data to be relayed
-   `socks5.onUdpAssociate.relayInboundBandwidthLimit`: Specifies the 
upper limit on bandwidth in bytes per second of receiving inbound data to be 
relayed
-   `socks5.onUdpAssociate.relayOutboundBandwidthLimit`: Specifies the 
upper limit on bandwidth in bytes per second of receiving outbound data to be 
relayed

The value given to any of the rule results must be an integer between 1 
(inclusive) and 2147483647 (inclusive)

These rule results can be used with the following rule conditions:

-   `clientAddress`
-   `socks5.command`
-   `socks5.desiredDestinationAddress`
-   `socks5.desiredDestinationPort`
-   `socks5.method`
-   `socks5.secondServerBoundAddress`
-   `socks5.secondServerBoundPort`
-   `socks5.serverBoundAddress`
-   `socks5.serverBoundPort`
-   `socks5.user`
-   `socksServerAddress`

Command line example:

```
./bin/jargyle start-server \
    "--setting=rule=socks5.command=CONNECT socks5.desiredDestinationAddress=streamingwebsite.com firewallAction=ALLOW socks5.onConnect.relayInboundBandwidthLimit=1024000 socks5.onConnect.relayOutboundBandwidthLimit=1024000" \
    --setting=rule=firewallAction=ALLOW
```

Server configuration file example:

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <settings>
        <setting>
            <name>rule</name>
            <rule>
                <ruleConditions>
                    <ruleCondition>
                        <name>socks5.command</name>
                        <value>CONNECT</value>
                    </ruleCondition>            
                    <ruleCondition>
                        <name>socks5.desiredDestinationAddress</name>
                        <value>streamingwebsite.com</value>
                    </ruleCondition>
                </ruleConditions>
                <ruleResults>
                    <ruleResult>
                        <name>firewallAction</name>
                        <value>ALLOW</value>
                    </ruleResult>
                    <ruleResult>
                         <name>socks5.onConnect.relayInboundBandwidthLimit</name>
                        <value>1024000</value>
                    </ruleResult>
                    <ruleResult>
                         <name>socks5.onConnect.relayOutboundBandwidthLimit</name>
                        <value>1024000</value>
                    </ruleResult>                
                </ruleResults>
            </rule>
            <doc>Allow the CONNECT command to connect to 'streamingwebsite.com' with an upper limit on the relay inbound and outbound bandwidth of 1024000 bytes per second</doc>
        </setting>
        <setting>
            <name>rule</name>
            <rule>
                <ruleConditions/>
                <ruleResults>
                    <ruleResult>
                        <name>firewallAction</name>
                        <value>ALLOW</value>
                    </ruleResult>
                </ruleResults>
            </rule>
            <doc>Allow anything else</doc>
        </setting>    
    </settings>
</configuration>
```