/*
 * Licensed to STRATIO (C) under one or more contributor license agreements.
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership. The STRATIO (C) licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.stratio.connector.mongodb.ftest.functionalMetadata;

import com.stratio.connector.commons.ftest.functionalMetadata.GenericMetadataDropFT;
import com.stratio.connector.commons.ftest.helper.IConnectorHelper;
import com.stratio.connector.mongodb.ftest.helper.DefaultConfigurationMongoConnectorHelper;

public class DropFT extends GenericMetadataDropFT {

    @Override
    protected IConnectorHelper getConnectorHelper() {
        return DefaultConfigurationMongoConnectorHelper.getInstance();
    }
    /*
     * @Test public void dropCollectionTest() throws UnsupportedOperationException,
     * com.stratio.connector.meta.exception.UnsupportedOperationException {
     * 
     * Row row = new Row(); Map<String, Cell> cells = new HashMap<>(); cells.put("name1", new Cell("value1"));
     * cells.put("name2", new Cell(2)); row.setCells(cells);
     * 
     * ((MongoStorageEngine) stratioMongoConnector.getStorageEngine()).insert( CATALOG, COLLECTION, row);
     * 
     * DBCollection collection = mongoClient.getDB(CATALOG).getCollection( COLLECTION);
     * 
     * ((IMetadataProvider) stratioMongoConnector.getMedatadaProvider()).dropTable(CATALOG, COLLECTION);
     * 
     * assertEquals("Catalog deleted", false, mongoClient.getDB(CATALOG).getCollectionNames().contains(COLLECTION) );
     * 
     * }
     * 
     * @Test public void dropCatalogTest() throws UnsupportedOperationException,
     * com.stratio.connector.meta.exception.UnsupportedOperationException {
     * 
     * Row row = new Row(); Map<String, Cell> cells = new HashMap<>(); cells.put("name1", new Cell("value1"));
     * cells.put("name2", new Cell(2)); row.setCells(cells);
     * 
     * ((MongoStorageEngine) stratioMongoConnector.getStorageEngine()).insert( CATALOG, COLLECTION, row);
     * 
     * DBCollection collection = mongoClient.getDB(CATALOG).getCollection( COLLECTION);
     * 
     * ((IMetadataProvider) stratioMongoConnector.getMedatadaProvider()).dropCatalog(CATALOG);
     * 
     * assertEquals("Catalog deleted", false, mongoClient.getDatabaseNames().contains(CATALOG) );
     * 
     * }
     */

}