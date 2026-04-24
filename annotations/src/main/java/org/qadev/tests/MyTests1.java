package org.qadev.tests;

import org.qadev.core.Action;
import org.qadev.core.Assertion;
import org.qadev.annotations.After;
import org.qadev.annotations.Before;
import org.qadev.annotations.Suite;
import org.qadev.annotations.Test;

@Suite("Suit #1: Test products cart")
public class MyTests1 {

    @Before("Before test: Open browser and create a cart")
    public void before() {
        Action.openBrowser();
    }

    @After("After test: Close browser and delete a cart")
    public void after() {
        Action.closeBrowser();
    }

    @Test("Test #1: Check user can add one item into the cart")
    public void test1() {
        Assertion.success();
    }

    @Test("Test #2: Check user can remove an item from the cart")
    public void test2() throws Exception {
        Assertion.fail();
    }

    @Test("Test #3: Check user can make an order")
    public void test3() {
        Assertion.success();
    }

}
