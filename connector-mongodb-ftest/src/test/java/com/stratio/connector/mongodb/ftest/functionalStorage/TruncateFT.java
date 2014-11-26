package com.stratio.connector.mongodb.ftest.functionalStorage;

import com.stratio.connector.commons.connection.exceptions.CreateNativeConnectionException;
import com.stratio.connector.commons.ftest.functionalMetadata.GenericMetadataAlterTableFT;
import com.stratio.connector.commons.ftest.functionalStorage.GenericTruncateFT;
import com.stratio.connector.commons.ftest.helper.IConnectorHelper;
import com.stratio.connector.mongodb.ftest.helper.MongoConnectorHelper;
import com.stratio.crossdata.common.exceptions.ConnectionException;
import com.stratio.crossdata.common.exceptions.InitializationException;

public class TruncateFT extends GenericTruncateFT{

    @Override
    protected IConnectorHelper getConnectorHelper() {
        MongoConnectorHelper mongoConnectorHelper = null;
        try {
            mongoConnectorHelper = new MongoConnectorHelper(getClusterName());
        } catch (ConnectionException e) {
            e.printStackTrace();
        } catch (InitializationException e) {
            e.printStackTrace();
        } catch (CreateNativeConnectionException e) {
            e.printStackTrace();
        }
        return mongoConnectorHelper;
    }
}
