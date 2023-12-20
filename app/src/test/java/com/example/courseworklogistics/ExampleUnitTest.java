package com.example.courseworklogistics;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

import android.content.Context;
import android.os.Build;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.courseworklogistics.user.User;
import com.example.courseworklogistics.user.UsersData;
import com.example.courseworklogistics.user.Route;
import com.example.courseworklogistics.user.RoutesData;

import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExampleUnitTest {
    private UsersData usersData;
    private RoutesData routesData;
    private Context context;
    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        usersData = new UsersData(context);
        routesData = new RoutesData(context);
        usersData.deleteAll();
        routesData.deleteAll();
    }
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testGetUser() {
        User user = new User();
        user.setId(1);
        usersData.addUser("TestAdmin", "admin", "admin");
        User result = usersData.getUser(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("TestAdmin", result.getLogin());
    }
    @Test
    public void testFindAllUsers() {
        usersData.addUser("TestAdmin1", "admin", "admin");
        usersData.addUser("TestAdmin2", "admin", "admin");
        List<User> result = usersData.findAllUsers();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("TestAdmin1", result.get(0).getLogin());
        assertEquals("TestAdmin2", result.get(1).getLogin());
    }
    @Test
    public void testAddUser() {
        usersData.addUser("TestAdmin", "admin", "admin");
        List<User> result = usersData.findAllUsers();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("TestAdmin", result.get(0).getLogin());
    }
    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setId(1);
        usersData.addUser("TestAdmin", "admin", "admin");
        usersData.updateUser(1, "UpdatedAdmin", "admin", "admin");
        User updatedUser = usersData.getUser(1);
        assertNotNull(updatedUser);
        assertEquals(1, updatedUser.getId());
        assertEquals("UpdatedAdmin", updatedUser.getLogin());
    }
    @Test
    public void testDeleteCategory() {
        User user = new User();
        user.setId(1);
        usersData.addUser("TestAdmin", "admin", "admin");
        usersData.deleteUser(1);
        List<User> result = usersData.findAllUsers();
        assertTrue(result.isEmpty());
    }
    @Test
    public void findAllRoutes() {
        testAddUser();
        routesData.addRoute("Msc", "Uly","TestAdmin","Сборка");
        routesData.addRoute("Msc1", "Uly","TestAdmin","Сборка");
        List<Route> result = routesData.findAllRoutes();
        assertNotNull(result);
        assertEquals(2, result.size());
    }
    @Test
    public void testAddRoute() {
        testAddUser();
        routesData.addRoute("Msc", "Uly","TestAdmin","Сборка");
        List<Route> result = routesData.findAllRoutes();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Msc", result.get(0).getRouteStartPoint());
    }
    @Test
    public void testUpdateRoute() {
        testAddUser();
        Route route = new Route();
        route.setId(1);
        routesData.addRoute("Msc", "Uly","TestAdmin","Сборка");
        routesData.updateRoute(1,"Msc1", "Uly1","TestAdmin","Сборка");
        Route updatedRoute = routesData.getRoute(1);
        assertNotNull(updatedRoute);
        assertEquals(1, updatedRoute.getId());
        assertEquals("Msc1", updatedRoute.getRouteStartPoint());
    }
    @Test
    public void testDeleteRoute() {
        Route route = new Route();
        route.setId(1);
        routesData.addRoute("Msc", "Uly","TestAdmin","Сборка");
        routesData.deleteRoute(1);
        List<Route> result = routesData.findAllRoutes();
        assertTrue(result.isEmpty());
    }
}