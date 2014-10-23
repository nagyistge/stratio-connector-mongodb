/*
 * Licensed to STRATIO (C) under one or more contributor license agreements.
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  The STRATIO (C) licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.stratio.connector.mongodb.core.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.stratio.connector.commons.connection.Connection;
import com.stratio.connector.commons.engine.CommonsMetadataEngine;
import com.stratio.connector.mongodb.core.connection.MongoConnectionHandler;
import com.stratio.connector.mongodb.core.engine.metadata.IndexUtils;
import com.stratio.connector.mongodb.core.engine.metadata.SelectorOptionsUtils;
import com.stratio.connector.mongodb.core.engine.metadata.ShardUtils;
import com.stratio.crossdata.common.data.CatalogName;
import com.stratio.crossdata.common.data.TableName;
import com.stratio.crossdata.common.exceptions.ExecutionException;
import com.stratio.crossdata.common.exceptions.UnsupportedException;
import com.stratio.crossdata.common.metadata.CatalogMetadata;
import com.stratio.crossdata.common.metadata.IndexMetadata;
import com.stratio.crossdata.common.metadata.TableMetadata;


/**
 * The Class MongoMetadataEngine.
 *
 * @author darroyo
 */
public class MongoMetadataEngine extends CommonsMetadataEngine<MongoClient> {

    /**
     * The Log.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Instantiates a new mongo metadata engine.
     *
     * @param connectionHandler
     *            the connection handler
     */
    public MongoMetadataEngine(MongoConnectionHandler connectionHandler) {
        super(connectionHandler);
    }

    /**
     * Create a database in MongoDB.
     *
     * @param catalogMetadata
     *            the catalogMetadata
     * @param connection
     *            the connection which contains the native connector
     * @throws UnsupportedException
     *             if the specified operation is not supported
     */
    @Override
    protected void createCatalog(CatalogMetadata catalogMetadata, Connection<MongoClient> connection)
                    throws UnsupportedException {
        throw new UnsupportedException("Create catalog is not supported");
    }

    /**
     * Create a collection in MongoDB.
     *
     * @param tableMetadata
     *            the tableMetadata
     * @param connection
     *            the connection which contains the native connector
     * @throws UnsupportedException
     *             if the specified operation is not supported
     * @throws ExecutionException
     *             if an error exist when running the database command
     */
    @Override
    protected void createTable(TableMetadata tableMetadata, Connection<MongoClient> connection)
                    throws UnsupportedException, ExecutionException {

        if (tableMetadata == null) {
            throw new UnsupportedException("the table metadata is required");
        }

        if (tableMetadata.getName() == null) {
            throw new UnsupportedException("the table name is required");
        }

        if (ShardUtils.collectionIsSharded(SelectorOptionsUtils.processOptions(tableMetadata.getOptions()))) {
            ShardUtils.shardCollection((MongoClient) connection.getNativeConnection(), tableMetadata);
        }
    }

    /**
     * Drop a database in MongoDB.
     *
     * @param name
     *            the database name
     * @param connection
     *            the connection which contains the native connector
     * @throws ExecutionException
     *             if an error exist when running the database command
     */
    @Override
    protected void dropCatalog(CatalogName name, Connection<MongoClient> connection) throws ExecutionException {
        try {
            connection.getNativeConnection().dropDatabase(name.getName());
        } catch (MongoException e) {
            throw new ExecutionException(e.getMessage(), e);
        }
    }

    /**
     * Drop a collection in MongoDB.
     *
     * @param name
     *            the collection name
     * @param connection
     *            the connection
     * @throws ExecutionException
     *             if an error exist when running the database command
     */
    @Override
    protected void dropTable(TableName name, Connection<MongoClient> connection) throws ExecutionException {

        DB db = connection.getNativeConnection().getDB(name.getCatalogName().getName());
        try {
            db.getCollection(name.getName()).drop();
        } catch (MongoException e) {
            throw new ExecutionException(e.getMessage(), e);
        }
    }

    /**
     * Create an index.
     *
     * @param indexMetadata
     *            the index metadata
     * @param connection
     *            the connection
     * @throws UnsupportedException
     *             if the specified index is not supported
     * @throws ExecutionException
     *             if an error exist when running the database command
     */
    @Override
    protected void createIndex(IndexMetadata indexMetadata, Connection<MongoClient> connection)
                    throws UnsupportedException, ExecutionException {

        DB db = connection.getNativeConnection().getDB(
                        indexMetadata.getName().getTableName().getCatalogName().getName());

        DBObject indexDBObject = IndexUtils.getIndexDBObject(indexMetadata);
        DBObject indexOptionsDBObject = IndexUtils.getCustomOptions(indexMetadata);

        try {
            db.getCollection(indexMetadata.getName().getTableName().getName()).createIndex(indexDBObject,
                            indexOptionsDBObject);
        } catch (MongoException e) {
            throw new ExecutionException(e.getMessage(), e);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Index created " + indexDBObject.toString() + indexOptionsDBObject);
        }
    }

    /**
     * Drop an index.
     *
     * @param indexMetadata
     *            the index metadata
     * @param connection
     *            the connection
     * @throws ExecutionException
     *             if an error exist when running the database command
     * @throws UnsupportedException
     *             if the specified operation is not supported
     */
    @Override
    protected void dropIndex(IndexMetadata indexMetadata, Connection<MongoClient> connection)
                    throws ExecutionException, UnsupportedException {
        DB db = connection.getNativeConnection().getDB(
                        indexMetadata.getName().getTableName().getCatalogName().getName());

        String indexName = null;
        if (indexMetadata.getName() != null) {
            indexName = indexMetadata.getName().getName();
        }

        if (indexName != null) {
            try {
                db.getCollection(indexMetadata.getName().getTableName().getName()).dropIndex(indexName);
            } catch (MongoException e) {
                throw new ExecutionException(e.getMessage(), e);
            }
        } else {
            IndexUtils.dropIndexWithDefaultName(indexMetadata, db);
        }

    }

}
