/*
 * Copyright (c) 2007, PostgreSQL Global Development Group
 * See the LICENSE file in the project root for more information.
 */

package org.postgresql.test.hostchooser;

import org.postgresql.test.TestUtil;
import org.postgresql.util.PSQLException;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/*
 * Executes multi host tests (aka master/slave connectivity selection).
 */
public class MultiHostTestSuite extends TestSuite {

  public static java.sql.Connection openSlaveDB() throws Exception {
    TestUtil.initDriver();

    Properties props = new Properties();

    props.setProperty("user", TestUtil.getUser());
    props.setProperty("password", TestUtil.getPassword());

    return DriverManager.getConnection(TestUtil.getURL(getSlaveServer(), getSlavePort()), props);
  }

  public static java.sql.Connection openSlaveDB2() throws Exception {
    TestUtil.initDriver();

    Properties props = new Properties();

    props.setProperty("user", TestUtil.getUser());
    props.setProperty("password", TestUtil.getPassword());

    return DriverManager.getConnection(TestUtil.getURL(getSlaveServer2(), getSlavePort2()), props);
  }

  /*
   * Returns the Test server
   */
  public static String getSlaveServer() {
    return System.getProperty("slaveServer", TestUtil.getServer());
  }

  /*
   * Returns the Test port
   */
  public static int getSlavePort() {
    return Integer
        .parseInt(System.getProperty("slavePort", String.valueOf(TestUtil.getPort() + 1)));
  }

  /*
   * Returns the Test server
   */
  public static String getSlaveServer2() {
    return System.getProperty("slaveServer2", TestUtil.getServer());
  }

  /*
   * Returns the Test port
   */
  public static int getSlavePort2() {
    return Integer
        .parseInt(System.getProperty("slavePort2", String.valueOf(TestUtil.getPort() + 2)));
  }

  /*
   * The main entry point for JUnit
   */
  public static TestSuite suite() throws Exception {
    TestSuite suite = new TestSuite();

    try {
      Connection connection = openSlaveDB();
      TestUtil.closeDB(connection);

      connection = openSlaveDB2();
      TestUtil.closeDB(connection);
    } catch (PSQLException ex) {
      // replication instance is not available, but suite must have at lest one test case
      suite.addTestSuite(DummyTest.class);
      return suite;
    }

    suite.addTestSuite(MultiHostsConnectionTest.class);
    return suite;
  }

  public static class DummyTest extends TestCase {
    public DummyTest(String name) {
      super(name);
    }

    public void testDummy() {
    }
  }
}

