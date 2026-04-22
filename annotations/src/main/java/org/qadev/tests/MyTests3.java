package org.qadev.tests;

import org.qadev.annotations.After;
import org.qadev.annotations.Before;
import org.qadev.annotations.Suite;
import org.qadev.annotations.Test;
import org.qadev.core.Action;
import org.qadev.core.Assertion;

@Suite("Suit #3: Pay items from the cart")
public class MyTests3 {

    @Before("Before test: Open browser and setup payment cart")
    public void before() {
        Action.openBrowser();
    }

    @After("After test: Close browser")
    public void after() {
        Action.closeBrowser();
    }

    @Test("Test #9: Pay when the balance less than sum")
    public void test9() {
        Assertion.success();
    }

    @Test("Test #10: Pay when the balance equals than sum")
    public void test10() throws Exception {
        Assertion.fail();
    }

    @Test("Test #11: Pay when the balance more than sum")
    public void test11() {
        Assertion.success();
    }

    @Test("Test #12: Pay if card is blocked")
    public void test12() {
        Assertion.success();
    }
}
