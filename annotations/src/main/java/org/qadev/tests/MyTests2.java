package org.qadev.tests;

import org.qadev.annotations.After;
import org.qadev.annotations.Before;
import org.qadev.annotations.Suite;
import org.qadev.annotations.Test;
import org.qadev.core.Action;
import org.qadev.core.Assertion;

@Suite("Suit #2: Select products from the catalog")
public class MyTests2 {

    @Before("Before test: Open browser and find catalog")
    public void before() {
        Action.openBrowser();
    }

    @After("After test: Close browser")
    public void after() {
        Action.closeBrowser();
    }

    @Test("Test #4: Find and open a product of potato")
    public void test4() {
        Assertion.success();
    }

    @Test("Test #5: Find and open a product of carrot")
    public void test5() throws Exception {
        Assertion.fail();
    }

    @Test("Test #6: Find and open a product of apple")
    public void test6() {
        Assertion.success();
    }

    @Test("Test #7: Find and open a product of orange")
    public void test7() {
        Assertion.success();
    }

    @Test("Test #8: Find and open a product of strawberry")
    public void test8() throws Exception {
        Assertion.fail();
    }

}
