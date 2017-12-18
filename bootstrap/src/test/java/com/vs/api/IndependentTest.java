package com.vs.api;

import com.mongodb.MongoClient;
import com.vs.model.enums.*;
import com.vs.model.geo.ZipData;
import com.vs.model.menu.Item;
import com.vs.model.menu.Menu;
import com.vs.model.order.Order;
import com.vs.model.user.Cook;
import com.vs.model.user.User;
import com.vs.repository.*;
//import com.vs.repository.unused.ItemRepository;
import com.vs.service.geo.GeoSpatial;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Created by gopi on 10/23/16.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringMongoConfiguration.class})
@Slf4j
@ComponentScan(basePackages = "com.vs")
public class IndependentTest {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private CookRepository cookRepository;

    @Autowired
    private OrderRepository orderRepository;


//    @Autowired
//    private ItemRepository itemRepository;

    @Autowired
    private USZipCodesRepository usZipCodesRepository;

    @Autowired
    private MongoTemplate template;

    @Test
    public void  getCooksNearBy() {
        log.info("User: {} searching for cooks near by: {} miles", "75024", 20);
        ZipData zipData = usZipCodesRepository.findBy_id("75024");
        Point point = new Point(zipData.getLoc()[0], zipData.getLoc()[1]);
        log.info("ZipData: {} - Point: {}", zipData, point);
        NearQuery query = NearQuery.near(point).maxDistance(new Distance(20, Metrics.MILES));
        GeoResults<ZipData> cooks = template.geoNear(query, ZipData.class);
        log.info("--> {}", cooks.getContent().size());
    }

    @Test
    public void  getCooksNearByUsersTable() {
        log.info("User: {} searching for cooks near by: {} miles", "75024", 20);
        ZipData zipData = usZipCodesRepository.findBy_id("75024");
        Point point = new Point(zipData.getLoc()[0], zipData.getLoc()[1]);
        log.info("ZipData: {} - Point: {}", zipData, point);
        NearQuery query = NearQuery.near(point).maxDistance(new Distance(5, Metrics.MILES));
        GeoResults<User> cooks = template.geoNear(query, User.class);
        log.info("--> {}", cooks.getContent().size());
    }




    public void m12() {

        ZipData data = usZipCodesRepository.findBy_id("75024");
        log.info("{}", data);

    }

    public void m11() {

        String orderedBy = "";
        String loggedInCook = "haigopi@gmail.com";
        OrderStatusEnum status = OrderStatusEnum.PLACED;
        ObjectId menuId = new ObjectId("59ff81bc44b7922cc8946150");

        Query query = Query.query(where("orderStatus").is(status).and("cookMenuItems.cookUserName").is(loggedInCook));
        List<Order> orders = mongoTemplate.find(query, Order.class);
        log.info("{}", orders);

        // orders = orderRepository.findByCookMenuItems_CookUserNameAndOrderStatus(loggedInCook, OrderStatusEnum.PLACED);
        log.info("{}", orders);

    }


    public void m10() {

        ObjectId menuId = new ObjectId("59ff81bc44b7922cc8946150");

        String userName = "saradaKitchen";
        String menuName = "Oct15-SaradaKitchen";
        menuRepository.findByUserNameAndId(userName, menuId);
        List<Menu> menus = menuRepository.findByUserName(userName);


        menuRepository.findByUserNameAndName(userName, menuName);


    }

    public void m9() {

        ObjectId menuId = new ObjectId("59ff81bc44b7922cc8946150");
        ObjectId itemId = new ObjectId("59ff81bc44b7922cc8946151");
        //Menu item = itemRepository.findByUserNameAndIdAndItems_Id("saradaKitchen", menuId, itemId);
        //log.info("{}", item);

    }

    public void m8() {

        ObjectId menuId = new ObjectId("59ff33fd44b7921484670dd3");
        ObjectId itemId = new ObjectId("59ff33fd44b7921484670dd4");

        Update mergeUserUpdate = new Update();
        mergeUserUpdate.set("items.$.picture", "ITEM TEST");
        mergeUserUpdate.set("items.$.status", "MY STAUS");
        //mergeUserUpdate.set(AppConstants.MENU_ITEM_PICTURE_LOCATION, "LOCATION");

        mongoTemplate.findAndModify(Query.query(where("id").is(menuId).
                and("items.id").is(itemId)), mergeUserUpdate, Menu.class);


//        mongoTemplate.findAndModify(
//                Query.query(where("id").is(menuId).
//                        and("items.id").is(itemId)), update("items.$.status", ItemStatusEnum.ORDER_IN_PLACE), Menu.class
//        );
//
//        mongoTemplate.findAndModify(
//                Query.query(where("id").is(menuId).
//                        and("items.id").is(itemId)), update("items.$.picture", "ITEM"), Menu.class
//        );


//        Update mergeUserUpdate = new Update();
//        mergeUserUpdate.set("item.$.picture", "ITEM");
//        mergeUserUpdate.set("item.$.test", "LOCATION");
//       // mergeUserUpdate.set(AppConstants.MENU_ITEM_PICTURE_LOCATION, "LOCATION");
//
//        mongoTemplate.findAndModify(Query.query(where("id").is(menuId).
//                and("items.id").is(itemId)), mergeUserUpdate, ITEM.class);
    }

    public void m7() {

        String menuId = "59f7da7b5bf6cf0588f3d8e5";
        String itemId = "59f7da7b5bf6cf0588f3d8e6";


        mongoTemplate.findAndModify(
                Query.query(where("id").is(menuId).
                        and("items.id").is(itemId)), update("items.$.picture", "ITEM"), Menu.class
        );


//        Update mergeUserUpdate = new Update();
//        mergeUserUpdate.set("item.$.picture", "ITEM");
//        mergeUserUpdate.set("item.$.test", "LOCATION");
//       // mergeUserUpdate.set(AppConstants.MENU_ITEM_PICTURE_LOCATION, "LOCATION");
//
//        mongoTemplate.findAndModify(Query.query(where("id").is(menuId).
//                and("items.id").is(itemId)), mergeUserUpdate, ITEM.class);
    }

    public void m6() {

        String menuId = "59f54ddf5bf6cf07142d6f3c";

        Item item = new Item();
        item.setName("Gopi");
        item.setMeasurement(Measurement.COUNT);
        item.setQuantity(1);
        item.setDescription("saasdada");


        Update update = new Update();
        update.addToSet("items", item);
        Criteria criteria = Criteria.where("_id").is(menuId);
        template.updateFirst(Query.query(criteria), update, Menu.class);

//
//        Query query = Query.query(where("id").is(menuId));
//        Update update = new Update().addToSet("items.$", item);
//        mongoTemplate.findAndModify(query, update, Menu.class);
    }

    public void m5() {

        ArrayList l = new ArrayList();
        l.add("d3d01414-c416-431a-ab08-fc650054598f");
        Menu item = null;// menuRepository.findByMenuIdAndItems_Id("e0fc5f20-86e1-456b-9515-b411c576c62f","d3d01414-c416-431a-ab08-fc650054598f");
        log.info("List: {}", item);
    }

    public void m2() {

        List<Cook> cook = cookRepository.findByKitchenName("KITCHEN_b9044429-7e1d-4bc2-a69e-358cf23fa237", Role.COOK);
        log.info("===>{} - {}", cook.size(), cook);
    }

    public void m4() {

//        String collectionName = Menu.class.getSimpleName().toLowerCase();
//
        String menuId = "39e6eb9a-38b9-4acf-9ee0-39ff30b960e5";
        String itemId = "f4c6a141-deb4-44dd-a127-7d040cf4d095";
//
//        BasicDBObject toAdd=new BasicDBObject();
//        BasicDBObject q=new BasicDBObject("id",id).append("items.id", itemId);
//
//        DBCursor cursor =  template.getCollection(collectionName).find(q);
//        DBCursor cursor1 =  template.getCollection("items").find(q);
//        while (cursor.hasNext()) {
//            System.out.println(cursor.next());
//        }
//        while (cursor1.hasNext()) {
//            System.out.println(cursor1.next());
//        }
//        toAdd.put(AppConstants.PROFILE_PICTURE,"gopi.jpg");
//
//        template.getCollection(collectionName).update(q, new BasicDBObject("$set",toAdd), true, false);

        template.findAndModify(
                Query.query(where("id").is(menuId).
                        and("items.id").is(itemId)), update("items.$.profilePicture", "gopi.jpg"), Menu.class
        );
    }

    public void m3() {


        template.findAndModify(
                Query.query(where("id").is("f972c125-792d-4f6d-b74b-ca8776da84a9").
                        and("items.id").is("87a6b16b-db9f-4827-9ccd-7dc6d25918d1")), update("items.$.status", ItemStatusEnum.ORDER_LIMIT_REACHED), Menu.class
        );


//        MongoConverter converter = template.getConverter();
//        Query query = Query.query(Criteria.where("id").is("f972c125-792d-4f6d-b74b-ca8776da84a9").and("items.id").is("87a6b16b-db9f-4827-9ccd-7dc6d25918d1"));
//        query.fields().include("items.id");
//
//        Item item = new Item();// template.findOne(query, Item.class);
//        item.setStatus(ItemStatusEnum.ORDER_LIMIT_REACHED);
//        DBObject newSectionRec = (DBObject) converter.convertToMongoType(item);
//
//
//        Update update = new Update().addToSet("items.$.status", newSectionRec);
//        template.findAndModify(query, update, Item.class);
//


//
//        UUID id = UUID.fromString("5b5d4740-eb05-4242-8dd7-494bb9569fac");
//        Item item = new Item();
//        item.setStatus(ItemStatusEnum.ORDER_LIMIT_REACHED);
//
//        Query query = Query.query(Criteria.where("id").is(id.toString()));
//
//        Update update = new Update();
//        update.addToSet("items", item);
//
//        this.template.findAndModify(query, update, Menu.class);

    }


    public void m1() {

        boolean exists = menuRepository.exists(new ObjectId("b7470eee-c1c9-a393-3b0b-842cefe42299"));
        log.info("===>{} - {}", exists, null);
    }
}

@EnableMongoRepositories({"com.vs.repository"})
class SpringMongoConfiguration {

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(new MongoClient("localhost"), "VantaShala");
    }

}