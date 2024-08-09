package com.linux.authentication.control;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "account-authentication-api")
//(baseUri = "https://dev.dev.safenow-tech.io/processors")
public interface AuthenticationClient {

}
