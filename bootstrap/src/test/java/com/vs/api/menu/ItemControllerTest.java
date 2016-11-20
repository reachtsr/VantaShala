package com.vs.api.menu;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.vs.api.common.BaseControllerTest;
import com.vs.api.common.MenuConstantGenerator;
import com.vs.bootstrap.ApplicationBootstrap;
import com.vs.common.filters.AppConstants;
import com.vs.model.enums.ItemStatus;
import com.vs.model.enums.Measurment;
import com.vs.model.enums.OrderCutOffHours;
import com.vs.model.menu.Item;
import com.vs.model.menu.Menu;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

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
