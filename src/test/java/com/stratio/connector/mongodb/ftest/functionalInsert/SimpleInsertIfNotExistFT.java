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
package com.stratio.connector.mongodb.ftest.functionalInsert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

import com.stratio.connector.commons.ftest.functionalInsert.GenericSimpleInsertIfNotExistFT;
import com.stratio.connector.commons.ftest.helper.IConnectorHelper;
import com.stratio.connector.commons.metadata.TableMetadataBuilder;
import com.stratio.connector.mongodb.ftest.helper.DefaultConfigurationMongoConnectorHelper;
import com.stratio.crossdata.common.data.Cell;
import com.stratio.crossdata.common.data.ClusterName;
import com.stratio.crossdata.common.data.ResultSet;
import com.stratio.crossdata.common.data.Row;
import com.stratio.crossdata.common.exceptions.ConnectorException;
import com.stratio.crossdata.common.metadata.ColumnType;
import com.stratio.crossdata.common.metadata.TableMetadata;

public class SimpleInsertIfNotExistFT extends GenericSimpleInsertIfNotExistFT {

    @Override
    protected IConnectorHelper getConnectorHelper() {
        return DefaultConfigurationMongoConnectorHelper.getInstance();
    }

    @Test
    public void testInsertFloat() throws ConnectorException {
        ClusterName clusterName = getClusterName();
        System.out.println("*********************************** INIT FUNCTIONAL TEST testInsertSamePK "
                        + clusterName.getName() + " ***********************************");
        Object value4 = new Float(25.32);
        insertRow(clusterName, value4, ColumnType.FLOAT, VALUE_1);

        ResultSet resultIterator = createResultSetTyped(clusterName, ColumnType.FLOAT);
        assertEquals("It has only one result", 1, resultIterator.size());
        for (Row recoveredRow : resultIterator) {

            String canonicalName = recoveredRow.getCell(COLUMN_4).getValue().getClass().getCanonicalName();
            boolean typeCorrect = Float.class.getCanonicalName().equals(canonicalName);
            assertTrue("The type is correct ", typeCorrect);
            assertEquals("The value is correct ", value4, recoveredRow.getCell(COLUMN_4).getValue());
        }

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testInsertSet() throws ConnectorException {
        ClusterName clusterName = getClusterName();
        System.out.println("*********************************** INIT FUNCTIONAL TEST testInsertSamePK "
                        + clusterName.getName() + " ***********************************");
        Set<Integer> valueSet = new HashSet<Integer>();
        valueSet.add(5);
        valueSet.add(8);
        Object value4 = valueSet;

        ColumnType colType = ColumnType.SET;
        colType.setDBCollectionType(ColumnType.INT);

        insertRow(clusterName, value4, colType, VALUE_1);

        ResultSet resultIterator = createResultSetTyped(clusterName, colType);
        assertEquals("It has only one result", 1, resultIterator.size());
        for (Row recoveredRow : resultIterator) {
            Object obj = recoveredRow.getCell(COLUMN_4).getValue();
            List<Integer> valueL = (List<Integer>) obj;
            assertTrue(valueL.contains(5));
            assertTrue("The type is correct ", obj instanceof List<?>);
            // assertEquals("The value is correct ", value4, recoveredRow.getCell(COLUMN_4).getValue());
        }

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testInsertList() throws ConnectorException {
        ClusterName clusterName = getClusterName();
        System.out.println("*********************************** INIT FUNCTIONAL TEST testInsertSamePK "
                        + clusterName.getName() + " ***********************************");
        List<Integer> al = Arrays.asList(1, 2, 3, 4);
        Object value4 = al;

        ColumnType colType = ColumnType.LIST;
        colType.setDBCollectionType(ColumnType.INT);

        insertRow(clusterName, value4, colType, VALUE_1);

        ResultSet resultIterator = createResultSetTyped(clusterName, colType);
        assertEquals("It has only one result", 1, resultIterator.size());
        for (Row recoveredRow : resultIterator) {
            Object obj = recoveredRow.getCell(COLUMN_4).getValue();
            List<Integer> valueL = (List<Integer>) obj;
            assertTrue(valueL.contains(3));
            assertTrue("The type is correct ", obj instanceof List<?>);
            assertEquals("The value is correct ", value4, recoveredRow.getCell(COLUMN_4).getValue());
        }

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testInsertMap() throws ConnectorException {
        ClusterName clusterName = getClusterName();
        System.out.println("*********************************** INIT FUNCTIONAL TEST testInsertSamePK "
                        + clusterName.getName() + " ***********************************");
        Map<String, Integer> map = new HashMap<String, Integer>(2);
        map.put("a", 1);
        map.put("b", 2);

        Object value4 = map;

        ColumnType colType = ColumnType.MAP;
        colType.setDBMapType(ColumnType.VARCHAR, ColumnType.INT);

        insertRow(clusterName, value4, colType, VALUE_1);

        ResultSet resultIterator = createResultSetTyped(clusterName, colType);
        assertEquals("It has only one result", 1, resultIterator.size());
        for (Row recoveredRow : resultIterator) {
            Object obj = recoveredRow.getCell(COLUMN_4).getValue();
            Map<String, Integer> valueMap = (Map<String, Integer>) obj;
            assertEquals((Object) valueMap.get("a"), 1);
            assertTrue("The type is correct ", obj instanceof Map<?, ?>);
            assertEquals("The value is correct ", value4, recoveredRow.getCell(COLUMN_4).getValue());
        }

    }

    @SuppressWarnings("static-access")
    @Override
    @Test
    @Ignore
    public void insertDateTest() throws ConnectorException {
        ClusterName clusterName = getClusterName();
        System.out.println("*********************************** INIT FUNCTIONAL TEST testInsertSamePK "
                        + clusterName.getName() + " ***********************************");
        Object value4 = new Date();
        ColumnType colType = ColumnType.NATIVE;

        insertRow(clusterName, value4, colType.NATIVE, VALUE_1);

        ResultSet resultIterator = createResultSet(clusterName);
        assertEquals("It has only one result", 1, resultIterator.size());
        for (Row recoveredRow : resultIterator) {
            assertEquals("The type is correct ", Date.class.getCanonicalName(), recoveredRow.getCell(COLUMN_4)
                            .getValue().getClass().getCanonicalName());
            assertEquals("The value is correct ", value4, recoveredRow.getCell(COLUMN_4).getValue());
        }
    }

    @Test
    public void testInsertNull() throws ConnectorException {
        ClusterName clusterName = getClusterName();
        System.out.println("*********************************** INIT FUNCTIONAL TEST testInsertSamePK "
                        + clusterName.getName() + " ***********************************");
        Object value4 = null;
        ColumnType colType = ColumnType.INT;

        insertRow(clusterName, value4, colType, VALUE_1);

        ResultSet resultIterator = createResultSet(clusterName);
        assertEquals("It has only one result", 1, resultIterator.size());
        for (Row recoveredRow : resultIterator) {
            Cell cell = recoveredRow.getCell(COLUMN_4);
            assertEquals("The value is correct ", value4, cell.getValue());
        }
    }

    @Test
    public void testInsertWithouthColumn4() throws ConnectorException {
        ClusterName clusterName = getClusterName();
        System.out.println("*********************************** INIT FUNCTIONAL TEST testInsertSamePK "
                        + clusterName.getName() + " ***********************************");
        Object value4 = null;
        ColumnType colType = ColumnType.INT;

        insertRowWithoutColumn4(clusterName, value4, colType, VALUE_1, true);

        ResultSet resultIterator = createResultSet(clusterName);
        assertEquals("It has only one result", 1, resultIterator.size());
        for (Row recoveredRow : resultIterator) {
            assertEquals("The value is correct ", value4, recoveredRow.getCell(COLUMN_4).getValue());
        }
    }

    protected void insertRowWithoutColumn4(ClusterName cluesterName, Object value_4, ColumnType colType_4,
                    String PK_VALUE, boolean withPK) throws ConnectorException {
        Row row = new Row();
        Map<String, Cell> cells = new HashMap<>();

        cells.put(COLUMN_1, new Cell(PK_VALUE));
        cells.put(COLUMN_2, new Cell(VALUE_2));
        cells.put(COLUMN_3, new Cell(VALUE_3));
        // cells.put(COLUMN_4, new Cell(value_4));

        row.setCells(cells);

        TableMetadataBuilder tableMetadataBuilder = new TableMetadataBuilder(CATALOG, TABLE);
        tableMetadataBuilder.addColumn(COLUMN_1, ColumnType.VARCHAR).addColumn(COLUMN_2, ColumnType.VARCHAR)
                        .addColumn(COLUMN_3, ColumnType.VARCHAR).addColumn(COLUMN_4, colType_4);
        if (withPK) {
            tableMetadataBuilder.withPartitionKey(COLUMN_1);
        }
        TableMetadata targetTable = tableMetadataBuilder.build(true);

        if (getConnectorHelper().isTableMandatory()) {
            connector.getMetadataEngine().createTable(getClusterName(), targetTable);
        }
        connector.getStorageEngine().insert(cluesterName, targetTable, row, false);
        refresh(CATALOG);
    }

    // TODO BINARY

    // TODO UUID
}
