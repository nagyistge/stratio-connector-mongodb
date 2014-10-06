/**
 * Copyright (C) 2014 Stratio (http://stratio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stratio.connector.mongodb.ftest.helper;

import static com.stratio.connector.mongodb.core.configuration.ConfigurationOptions.HOST;
import static com.stratio.connector.mongodb.core.configuration.ConfigurationOptions.PORT;

import java.util.HashMap;
import java.util.Map;

import com.stratio.connector.commons.connection.exceptions.CreateNativeConnectionException;
import com.stratio.meta.common.connector.ConnectorClusterConfig;
import com.stratio.meta.common.exceptions.ConnectionException;
import com.stratio.meta.common.exceptions.InitializationException;
import com.stratio.meta2.common.data.ClusterName;

/**
 * @author darroyo
 *
 */
public class DefaultConfigurationMongoConnectorHelper extends MongoConnectorHelper {

    /**
     * @param clusterName
     * @throws ConnectionException
     * @throws InitializationException
     * @throws CreateNativeConnectionException
     */
    public DefaultConfigurationMongoConnectorHelper(ClusterName clusterName) throws ConnectionException,
                    InitializationException, CreateNativeConnectionException {
        super(clusterName);
    }

    @Override
    public ConnectorClusterConfig getConnectorClusterConfig() {
        Map<String, String> optionsNode = new HashMap<>();
        optionsNode.put(HOST.getOptionName(), SERVER_IP);
        optionsNode.put(PORT.getOptionName(), SERVER_PORT);
        return new ConnectorClusterConfig(clusterName, optionsNode);
    }
}