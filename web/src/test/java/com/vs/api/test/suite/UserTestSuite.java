package com.vs.api.test.suite;

import com.vs.api.test.AdminUserControllerTest;
import com.vs.api.test.UserControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by GeetaKrishna on 9/15/2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ UserControllerTest.class, AdminUserControllerTest.class })
public class UserTestSuite {
}
