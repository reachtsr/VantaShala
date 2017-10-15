package com.vs.api.menu;

import com.vs.api.common.BaseControllerTest;
import com.vs.bootstrap.ApplicationBootstrap;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by GeetaKrishna on 10/7/2016.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ApplicationBootstrap.class)
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

// Todo write tests
public class ItemControllerTest extends BaseControllerTest {



}
