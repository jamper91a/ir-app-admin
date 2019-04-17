package inventarioreal.com.inventarioreal_admin.pages;

import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import inventarioreal.com.inventarioreal_admin.R;
import jamper91.com.easyway.Util.CicloActivity;

import static org.junit.Assert.*;

public class HomeTest{

    @Rule
    public ActivityTestRule<Home> activityTestRule = new ActivityTestRule<Home>(Home.class);
    private Home home = null;

    @Before
    public void setUp() throws Exception {
        home = activityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        home = null;
    }

    @Test
    public void initGui() {
        //There must be 9 elements
        assertNotNull(home.getElemento(R.id.btnIng).getElemento());
        assertNotNull(home.getElemento(R.id.btnInv).getElemento());
        assertNotNull(home.getElemento(R.id.btnInvCoo).getElemento());
        assertNotNull(home.getElemento(R.id.btnTrans).getElemento());
        assertNotNull(home.getElemento(R.id.btnDev).getElemento());
        assertNotNull(home.getElemento(R.id.btnRep).getElemento());
        assertNotNull(home.getElemento(R.id.btnUsu).getElemento());
        assertNotNull(home.getElemento(R.id.btnBusYGeo).getElemento());
        assertNotNull(home.getElemento(R.id.btnSal).getElemento());
    }

    @Test
    public void getData() {

    }

    @Test
    public void initOnClick() {

    }

    @Test
    public void hasAllPermissions() {

    }
}