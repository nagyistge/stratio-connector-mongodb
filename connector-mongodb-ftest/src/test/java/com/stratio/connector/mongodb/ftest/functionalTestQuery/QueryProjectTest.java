package com.stratio.connector.mongodb.ftest.functionalTestQuery;


import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.MongoException;
import com.stratio.connector.meta.MongoResultSet;
import com.stratio.connector.mongodb.core.engine.MongoQueryEngine;
import com.stratio.connector.mongodb.ftest.ConnectionTest;
import com.stratio.meta.common.data.Row;
import com.stratio.meta.common.exceptions.ExecutionException;
import com.stratio.meta.common.exceptions.UnsupportedException;
import com.stratio.meta.common.logicalplan.LogicalPlan;
import com.stratio.meta.common.logicalplan.LogicalStep;
import com.stratio.meta.common.logicalplan.Project;
import com.stratio.meta.common.metadata.structures.ColumnMetadata;
import com.stratio.meta.common.result.QueryResult;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;


/**
 * Created by jmgomez on 17/07/14.
 */
public class QueryProjectTest extends ConnectionTest {

    public static final String COLUMN_1 = "bin1";
    public static final String COLUMN_2 = "bin2";
    public static final String COLUMN_3 = "bin3";

    @Test
    public void selectFilterProject() throws MongoException, UnsupportedException, UnsupportedOperationException, ExecutionException {


        insertRow(1);
        insertRow(2);
        insertRow(3);
        insertRow(4);

        LogicalPlan logicalPlan = createLogicalPlan();
        MongoResultSet queryResult = (MongoResultSet) ((MongoQueryEngine)stratioMongoConnector.getQueryEngine()).execute(logicalPlan);
        
        
        Set<Object> proveSet = new HashSet<>();
        for (Row row :queryResult.getRows()){
            for (String cell:row.getCells().keySet()){
                proveSet.add(cell+row.getCell(cell).getValue());
            }
        }

        assertEquals("The record number is correct",8,proveSet.size());
        assertTrue("Return correct record",proveSet.contains("bin1ValueBin1_r1"));
        assertTrue("Return correct record",proveSet.contains("bin2ValueBin2_r1"));
        assertFalse("This record should not be returned",proveSet.contains("bin3ValueBin3_r1"));
        assertTrue("Return correct record",proveSet.contains("bin1ValueBin1_r2"));
        assertTrue("Return correct record",proveSet.contains("bin2ValueBin2_r2"));
        assertFalse("This record should not be returned",proveSet.contains("bin3ValueBin3_r2"));
        assertTrue("Return correct record",proveSet.contains("bin1ValueBin1_r3"));
        assertTrue("Return correct record",proveSet.contains("bin2ValueBin2_r3"));
        assertFalse("This record should not be returned",proveSet.contains("bin3ValueBin3_r3"));
        assertTrue("Return correct record",proveSet.contains("bin1ValueBin1_r4"));
        assertTrue("Return correct record",proveSet.contains("bin2ValueBin2_r4"));
        assertFalse("This record should not be returned",proveSet.contains("bin3ValueBin3_r4"));

    }






    private LogicalPlan createLogicalPlan() {
        List<LogicalStep> stepList = new ArrayList<>();
        List<ColumnMetadata> columns = new ArrayList<>();
        columns.add(new ColumnMetadata(COLLECTION,COLUMN_1));
        columns.add(new ColumnMetadata(COLLECTION,COLUMN_2));
        Project project = new Project(CATALOG, COLLECTION,columns);
        stepList.add(project);
        return new LogicalPlan(stepList);
    }

    private void insertRow(int ikey) throws MongoException {
    	
        
        DBCollection collection = mongoClient.getDB(CATALOG).getCollection(COLLECTION);
        collection.insert(BasicDBObjectBuilder.start().append(COLUMN_1,"ValueBin1_r"+ikey).append(COLUMN_2,"ValueBin2_r"+ikey).append(COLUMN_3,"ValueBin3_r"+ikey).get() );
        
    }


}
