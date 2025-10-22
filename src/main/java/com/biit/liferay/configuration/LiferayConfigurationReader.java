package com.biit.liferay.configuration;

/*-
 * #%L
 * Liferay Configuration Reader
 * %%
 * Copyright (C) 2022 - 2025 BiiT Sourcing Solutions S.L.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import com.biit.liferay.log.LiferayClientLogger;
import com.biit.liferay.security.PasswordEncryptationAlgorithmType;
import com.biit.utils.configuration.ConfigurationReader;
import com.biit.utils.configuration.PropertiesSourceFile;
import com.biit.utils.configuration.SystemVariablePropertiesSourceFile;
import com.biit.utils.configuration.exceptions.PropertyNotFoundException;

import java.util.Objects;

public class LiferayConfigurationReader extends ConfigurationReader {

    private static final String CONFIG_FILE = "liferay.conf";
    private static final String LIFERAY_SYSTEM_VARIABLE_CONFIG = "LIFERAY_CONFIG";

    private static final String ID_USER = "user";
    private static final String ID_PASSWORD = "password";
    private static final String ID_VIRTUAL_HOST = "virtualhost";
    private static final String ID_HOST = "host";
    private static final String ID_SITE = "site";
    private static final String ID_WEBAPP = "webapp";
    private static final String ID_PORT = "port";
    private static final String ID_PASSWORD_ALGORITHM = "passwordEncryptionAlgorithm";
    private static final String ID_WEBSERVICES_PATH = "webservices";
    private static final String ID_CUSTOM_WEBSERVICES_PATH = "custom_webservices";
    private static final String ID_LIFERAY_PROTOCOL = "liferayProtocol";
    private static final String ID_AUTH_TOKEN = "p_auth";
    private static final String ID_PROXY_PREFIX = "proxy_prefix";
    private static final String ID_LIFERAY_VERSION = "liferay_version";

    private static final String DEFAULT_USER = "user";
    private static final String DEFAULT_PASSWORD = "pass";
    private static final String DEFAULT_VIRTUAL_HOST = "localhost";
    private static final String DEFAULT_HOST = "";
    private static final String DEFAULT_SITE = "Guest";
    private static final String DEFAULT_WEBAPP = "";
    private static final String DEFAULT_PORT = "8080";
    private static final String DEFAULT_PASSWORD_ALGORITHM = "PBKDF2";
    private static final String DEFAULT_WEBSERVICES_PATH = "api/jsonws/";
    private static final String DEFAULT_LIFERAY_PROTOCOL_PATH = "http";
    private static final String DEFAULT_AUTH_TOKEN = "";
    private static final String DEFAULT_PROXY_PREFIX = "";
    private static final String DEFAULT_LIFERAY_VERSION = "6.2-ce-ga6";

    private static LiferayConfigurationReader instance;

    private LiferayConfigurationReader() {
        super();

        addProperty(ID_USER, DEFAULT_USER);
        addProperty(ID_HOST, DEFAULT_HOST);
        addProperty(ID_PASSWORD, DEFAULT_PASSWORD);
        addProperty(ID_VIRTUAL_HOST, DEFAULT_VIRTUAL_HOST);
        addProperty(ID_SITE, DEFAULT_SITE);
        addProperty(ID_WEBAPP, DEFAULT_WEBAPP);
        addProperty(ID_PORT, DEFAULT_PORT);
        addProperty(ID_PASSWORD_ALGORITHM, DEFAULT_PASSWORD_ALGORITHM);
        addProperty(ID_WEBSERVICES_PATH, DEFAULT_WEBSERVICES_PATH);
        addProperty(ID_CUSTOM_WEBSERVICES_PATH, null);
        addProperty(ID_LIFERAY_PROTOCOL, DEFAULT_LIFERAY_PROTOCOL_PATH);
        addProperty(ID_AUTH_TOKEN, DEFAULT_AUTH_TOKEN);
        addProperty(ID_PROXY_PREFIX, DEFAULT_PROXY_PREFIX);
        addProperty(ID_LIFERAY_VERSION, DEFAULT_LIFERAY_VERSION);

        addPropertiesSource(new PropertiesSourceFile(CONFIG_FILE));
        addPropertiesSource(new SystemVariablePropertiesSourceFile(LIFERAY_SYSTEM_VARIABLE_CONFIG, CONFIG_FILE));

        readConfigurations();
    }

    public static LiferayConfigurationReader getInstance() {
        if (instance == null) {
            synchronized (LiferayConfigurationReader.class) {
                if (instance == null) {
                    instance = new LiferayConfigurationReader();
                }
            }
        }
        return instance;
    }

    public String getAuthToken() {
        return getPropertyLogException(ID_AUTH_TOKEN);
    }

    public String getConnectionPort() {
        return getPropertyLogException(ID_PORT);
    }

    public String getLiferayProtocol() {
        return getPropertyLogException(ID_LIFERAY_PROTOCOL);
    }

    public String getPassword() {
        return getPropertyLogException(ID_PASSWORD);
    }

    public PasswordEncryptationAlgorithmType getPasswordEncryptationAlgorithm() {
        return PasswordEncryptationAlgorithmType
                .getPasswordEncryptationAlgorithms(getPropertyLogException(ID_PASSWORD_ALGORITHM));
    }

    private String getPropertyLogException(String propertyId) {
        try {
            return getProperty(propertyId);
        } catch (PropertyNotFoundException e) {
            LiferayClientLogger.errorMessage(this.getClass().getName(), e);
            return null;
        }
    }

    public String getUser() {
        return getPropertyLogException(ID_USER);
    }

    public String getVirtualHost() {
        return getPropertyLogException(ID_VIRTUAL_HOST);
    }

    public String getHost() {
        // For compatibility with previous liferay.conf, we return virtual host
        // if not defined.
        String host = getPropertyLogException(ID_HOST);
        if (Objects.equals(host, "")) {
            return getVirtualHost();
        }
        return host;
    }

    public String getSite() {
        return getPropertyLogException(ID_SITE);
    }

    public String getWebAppName() {
        String webappName = getPropertyLogException(ID_WEBAPP);
        if (webappName != null && webappName.length() > 0) {
            return webappName + "/";
        }
        return "";
    }

    public String getWebServicesPath() {
        return getPropertyLogException(ID_WEBSERVICES_PATH);
    }

    public String getCustomWebServicesPath() {
        String customWebservicesPath = getPropertyLogException(ID_CUSTOM_WEBSERVICES_PATH);
        if (customWebservicesPath == null) {
            return getWebServicesPath();
        }
        return customWebservicesPath;
    }

    public String getProxyPrefix() {
        return getPropertyLogException(ID_PROXY_PREFIX);
    }

    public String getLiferayVersion() {
        return getPropertyLogException(ID_LIFERAY_VERSION);
    }
}
