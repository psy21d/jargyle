# Client Properties

## Page Contents

-   [SOCKS Server URI Properties](#socks-server-uri-properties)
    -   [socksServerUri.port](#socksserveruri-port)
    -   [socksServerUri.host](#socksserveruri-host)
    -   [socksServerUri.scheme](#socksserveruri-scheme)
-   [General Properties](#general-properties)
    -   [socksClient.clientBindHost](#socksclient-clientbindhost)
    -   [socksClient.clientBindPortRanges](#socksclient-clientbindportranges)
    -   [socksClient.clientConnectTimeout](#socksclient-clientconnecttimeout)
    -   [socksClient.clientSocketSettings](#socksclient-clientsocketsettings)
-   [DTLS Properties](#dtls-properties)
    -   [socksClient.dtls.enabled](#socksclient-dtls-enabled)
    -   [socksClient.dtls.enabledCipherSuites](#socksclient-dtls-enabledciphersuites)
    -   [socksClient.dtls.enabledProtocols](#socksclient-dtls-enabledprotocols)
    -   [socksClient.dtls.keyStoreFile](#socksclient-dtls-keystorefile)
    -   [socksClient.dtls.keyStorePassword](#socksclient-dtls-keystorepassword)
    -   [socksClient.dtls.keyStoreType](#socksclient-dtls-keystoretype)
    -   [socksClient.dtls.maxPacketSize](#socksclient-dtls-maxpacketsize)
    -   [socksClient.dtls.protocol](#socksclient-dtls-protocol)
    -   [socksClient.dtls.trustStoreFile](#socksclient-dtls-truststorefile)
    -   [socksClient.dtls.trustStorePassword](#socksclient-dtls-truststorepassword)
    -   [socksClient.dtls.trustStoreType](#socksclient-dtls-truststoretype)
-   [SOCKS5 Properties](#socks5-properties)
    -   [socksClient.socks5.clientUdpAddressAndPortUnknown](#socksclient-socks5-clientudpaddressandportunknown)
    -   [socksClient.socks5.gssapimethod.mechanismOid](#socksclient-socks5-gssapimethod-mechanismoid)
    -   [socksClient.socks5.gssapimethod.necReferenceImpl](#socksclient-socks5-gssapimethod-necreferenceimpl)
    -   [socksClient.socks5.gssapimethod.protectionLevels](#socksclient-socks5-gssapimethod-protectionlevels)
    -   [socksClient.socks5.gssapimethod.serviceName](#socksclient-socks5-gssapimethod-servicename)
    -   [socksClient.socks5.methods](#socksclient-socks5-methods)
    -   [socksClient.socks5.useResolveCommand](#socksclient-socks5-useresolvecommand)
    -   [socksClient.socks5.userpassmethod.password](#socksclient-socks5-userpassmethod-password)
    -   [socksClient.socks5.userpassmethod.username](#socksclient-socks5-userpassmethod-username)
-   [SSL/TLS Properties](#ssl-tls-properties)
    -   [socksClient.ssl.enabled](#socksclient-ssl-enabled)
    -   [socksClient.ssl.enabledCipherSuites](#socksclient-ssl-enabledciphersuites)
    -   [socksClient.ssl.enabledProtocols](#socksclient-ssl-enabledprotocols)
    -   [socksClient.ssl.keyStoreFile](#socksclient-ssl-keystorefile)
    -   [socksClient.ssl.keyStorePassword](#socksclient-ssl-keystorepassword)
    -   [socksClient.ssl.keyStoreType](#socksclient-ssl-keystoretype)
    -   [socksClient.ssl.protocol](#socksclient-ssl-protocol)
    -   [socksClient.ssl.trustStoreFile](#socksclient-ssl-truststorefile)
    -   [socksClient.ssl.trustStorePassword](#socksclient-ssl-truststorepassword)
    -   [socksClient.ssl.trustStoreType](#socksclient-ssl-truststoretype)

## SOCKS Server URI Properties

### socksServerUri.port

**Syntax:**

```text
socksServerUri.port=PORT
```

**Description:**

The port of the SOCKS server URI (default is 1080)

**Value:** [Port](value-syntaxes.md#port)

### socksServerUri.host

**Syntax:**

```text
socksServerUri.host=HOST
```

**Description:**

The host name or address of the SOCKS server URI

**Value:** [Host](value-syntaxes.md#host)

### socksServerUri.scheme

**Syntax:**

```text
socksServerUri.scheme=SCHEME
```

**Description:**

The scheme of the SOCKS server URI

**Value:** [Scheme](value-syntaxes.md#scheme)

## General Properties

### socksClient.clientBindHost

**Syntax:**

```text
socksClient.clientBindHost=HOST
```

**Description:**

The binding host name or address for the client socket that is used to connect to the SOCKS server (used for the SOCKS5 commands RESOLVE, BIND and UDP ASSOCIATE) (default is 0.0.0.0)

**Value:** [Host](value-syntaxes.md#host)

### socksClient.clientBindPortRanges

**Syntax:**

```text
socksClient.clientBindPortRanges=PORT_RANGES
```

**Description:**

The comma separated list of binding port ranges for the client socket that is used to connect to the SOCKS server (used for the SOCKS5 commands RESOLVE, BIND and UDP ASSOCIATE) (default is 0)

**Value:** [Port Ranges](value-syntaxes.md#port-ranges)

### socksClient.clientConnectTimeout

**Syntax:**

```text
socksClient.clientConnectTimeout=POSITIVE_INTEGER
```

**Description:**

The timeout in milliseconds on waiting for the client socket to connect to the SOCKS server (used for the SOCKS5 commands RESOLVE, BIND and UDP ASSOCIATE) (default is 60000)

**Value:** [Positive Integer](value-syntaxes.md#positive-integer)

### socksClient.clientSocketSettings

**Syntax:**

```text
socksClient.clientSocketSettings=SOCKET_SETTINGS
```

**Description:**

The comma separated list of socket settings for the client socket that is used to connect to the SOCKS server (used for the SOCKS5 command RESOLVE and UDP ASSOCIATE)

**Value:** [Socket Settings](value-syntaxes.md#socket-settings)

## DTLS Properties

### socksClient.dtls.enabled

**Syntax:**

```text
socksClient.dtls.enabled=true|false
```

**Description:**

The boolean value to indicate if DTLS connections to the SOCKS server are enabled (default is false)

**Value:** java.lang.Boolean

### socksClient.dtls.enabledCipherSuites

**Syntax:**

```text
socksClient.dtls.enabledCipherSuites=COMMA_SEPARATED_VALUES
```

**Description:**

The comma separated list of acceptable cipher suites enabled for DTLS connections to the SOCKS server

**Value:** [Comma Separated Values](value-syntaxes.md#comma-separated-values)

### socksClient.dtls.enabledProtocols

**Syntax:**

```text
socksClient.dtls.enabledProtocols=COMMA_SEPARATED_VALUES
```

**Description:**

The comma separated list of acceptable protocol versions enabled for DTLS connections to the SOCKS server

**Value:** [Comma Separated Values](value-syntaxes.md#comma-separated-values)

### socksClient.dtls.keyStoreFile

**Syntax:**

```text
socksClient.dtls.keyStoreFile=FILE
```

**Description:**

The key store file for the DTLS connections to the SOCKS server

**Value:** java.io.File

### socksClient.dtls.keyStorePassword

**Syntax:**

```text
socksClient.dtls.keyStorePassword=PASSWORD
```

**Description:**

The password for the key store for the DTLS connections to the SOCKS server

**Value:** java.lang.String

### socksClient.dtls.keyStoreType

**Syntax:**

```text
socksClient.dtls.keyStoreType=TYPE
```

**Description:**

The type of key store file for the DTLS connections to the SOCKS server (default is PKCS12)

**Value:** java.lang.String

### socksClient.dtls.maxPacketSize

**Syntax:**

```text
socksClient.dtls.maxPacketSize=POSITIVE_INTEGER
```

**Description:**

The maximum packet size for the DTLS connections to the SOCKS server (default is 32768)

**Value:** [Positive Integer](value-syntaxes.md#positive-integer)

### socksClient.dtls.protocol

**Syntax:**

```text
socksClient.dtls.protocol=PROTOCOL
```

**Description:**

The protocol version for the DTLS connections to the SOCKS server (default is DTLSv1.2)

**Value:** java.lang.String

### socksClient.dtls.trustStoreFile

**Syntax:**

```text
socksClient.dtls.trustStoreFile=FILE
```

**Description:**

The trust store file for the DTLS connections to the SOCKS server

**Value:** java.io.File

### socksClient.dtls.trustStorePassword

**Syntax:**

```text
socksClient.dtls.trustStorePassword=PASSWORD
```

**Description:**

The password for the trust store for the DTLS connections to the SOCKS server

**Value:** java.lang.String

### socksClient.dtls.trustStoreType

**Syntax:**

```text
socksClient.dtls.trustStoreType=TYPE
```

**Description:**

The type of trust store file for the DTLS connections to the SOCKS server (default is PKCS12)

**Value:** java.lang.String

## SOCKS5 Properties

### socksClient.socks5.clientUdpAddressAndPortUnknown

**Syntax:**

```text
socksClient.socks5.clientUdpAddressAndPortUnknown=true|false
```

**Description:**

The boolean value to indicate that the client UDP address and port for sending UDP datagrams to the SOCKS5 server is unknown (default is false)

**Value:** java.lang.Boolean

### socksClient.socks5.gssapimethod.mechanismOid

**Syntax:**

```text
socksClient.socks5.gssapimethod.mechanismOid=OID
```

**Description:**

The object ID for the GSS-API authentication mechanism to the SOCKS5 server (default is 1.2.840.113554.1.2.2)

**Value:** org.ietf.jgss.Oid

### socksClient.socks5.gssapimethod.necReferenceImpl

**Syntax:**

```text
socksClient.socks5.gssapimethod.necReferenceImpl=true|false
```

**Description:**

The boolean value to indicate if the exchange of the GSS-API protection level negotiation must be unprotected should the SOCKS5 server use the NEC reference implementation (default is false)

**Value:** java.lang.Boolean

### socksClient.socks5.gssapimethod.protectionLevels

**Syntax:**

```text
socksClient.socks5.gssapimethod.protectionLevels=SOCKS5_GSSAPIMETHOD_PROTECTION_LEVELS
```

**Description:**

The comma separated list of acceptable protection levels after GSS-API authentication with the SOCKS5 server (The first is preferred. The remaining are acceptable if the server does not accept the first.) (default is REQUIRED_INTEG_AND_CONF,REQUIRED_INTEG,NONE)

**Value:** [SOCKS5 GSS-API Method Protection Levels](value-syntaxes.md#socks5-gss-api-method-protection-levels)

### socksClient.socks5.gssapimethod.serviceName

**Syntax:**

```text
socksClient.socks5.gssapimethod.serviceName=SERVICE_NAME
```

**Description:**

The GSS-API service name for the SOCKS5 server

**Value:** java.lang.String

### socksClient.socks5.methods

**Syntax:**

```text
socksClient.socks5.methods=SOCKS5_METHODS
```

**Description:**

The comma separated list of acceptable authentication methods to the SOCKS5 server (default is NO_AUTHENTICATION_REQUIRED)

**Value:** [SOCKS5 Methods](value-syntaxes.md#socks5-methods)

### socksClient.socks5.useResolveCommand

**Syntax:**

```text
socksClient.socks5.useResolveCommand=true|false
```

**Description:**

The boolean value to indicate that the RESOLVE command is to be used on the SOCKS5 server for resolving host names (default is false)

**Value:** java.lang.Boolean

### socksClient.socks5.userpassmethod.password

**Syntax:**

```text
socksClient.socks5.userpassmethod.password=PASSWORD
```

**Description:**

The password to be used to access the SOCKS5 server

**Value:** java.lang.String

### socksClient.socks5.userpassmethod.username

**Syntax:**

```text
socksClient.socks5.userpassmethod.username=USERNAME
```

**Description:**

The username to be used to access the SOCKS5 server

**Value:** java.lang.String

## SSL/TLS Properties

### socksClient.ssl.enabled

**Syntax:**

```text
socksClient.ssl.enabled=true|false
```

**Description:**

The boolean value to indicate if SSL/TLS connections to the SOCKS server are enabled (default is false)

**Value:** java.lang.Boolean

### socksClient.ssl.enabledCipherSuites

**Syntax:**

```text
socksClient.ssl.enabledCipherSuites=COMMA_SEPARATED_VALUES
```

**Description:**

The comma separated list of acceptable cipher suites enabled for SSL/TLS connections to the SOCKS server

**Value:** [Comma Separated Values](value-syntaxes.md#comma-separated-values)

### socksClient.ssl.enabledProtocols

**Syntax:**

```text
socksClient.ssl.enabledProtocols=COMMA_SEPARATED_VALUES
```

**Description:**

The comma separated list of acceptable protocol versions enabled for SSL/TLS connections to the SOCKS server

**Value:** [Comma Separated Values](value-syntaxes.md#comma-separated-values)

### socksClient.ssl.keyStoreFile

**Syntax:**

```text
socksClient.ssl.keyStoreFile=FILE
```

**Description:**

The key store file for the SSL/TLS connections to the SOCKS server

**Value:** java.io.File

### socksClient.ssl.keyStorePassword

**Syntax:**

```text
socksClient.ssl.keyStorePassword=PASSWORD
```

**Description:**

The password for the key store for the SSL/TLS connections to the SOCKS server

**Value:** java.lang.String

### socksClient.ssl.keyStoreType

**Syntax:**

```text
socksClient.ssl.keyStoreType=TYPE
```

**Description:**

The type of key store file for the SSL/TLS connections to the SOCKS server (default is PKCS12)

**Value:** java.lang.String

### socksClient.ssl.protocol

**Syntax:**

```text
socksClient.ssl.protocol=PROTOCOL
```

**Description:**

The protocol version for the SSL/TLS connections to the SOCKS server (default is TLSv1.2)

**Value:** java.lang.String

### socksClient.ssl.trustStoreFile

**Syntax:**

```text
socksClient.ssl.trustStoreFile=FILE
```

**Description:**

The trust store file for the SSL/TLS connections to the SOCKS server

**Value:** java.io.File

### socksClient.ssl.trustStorePassword

**Syntax:**

```text
socksClient.ssl.trustStorePassword=PASSWORD
```

**Description:**

The password for the trust store for the SSL/TLS connections to the SOCKS server

**Value:** java.lang.String

### socksClient.ssl.trustStoreType

**Syntax:**

```text
socksClient.ssl.trustStoreType=TYPE
```

**Description:**

The type of trust store file for the SSL/TLS connections to the SOCKS server (default is PKCS12)

**Value:** java.lang.String

