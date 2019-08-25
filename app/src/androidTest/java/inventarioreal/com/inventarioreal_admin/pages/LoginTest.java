package inventarioreal.com.inventarioreal_admin.pages;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.UiThreadTestRule;
import android.util.Log;

import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import inventarioreal.com.inventarioreal_admin.R;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.LoginResponse;
import inventarioreal.com.inventarioreal_admin.pojo.WebServices.answers.SyncResponse;
import inventarioreal.com.inventarioreal_admin.util.Constants;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceFail;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceInterface;
import inventarioreal.com.inventarioreal_admin.util.WebServices.ResultWebServiceOk;
import inventarioreal.com.inventarioreal_admin.util.WebServices.WebServices;

import static org.junit.Assert.*;

public class LoginTest {
    @Rule
    public ActivityTestRule<Login> activityTestRule = new ActivityTestRule<Login>(Login.class);
    private Login login = null;


    @Before
    public void setUp() throws Exception {
        login = activityTestRule.getActivity();
    }

    @Test
    public void initGui() {
        //There must be 9 elements
        assertNotNull(login.getElemento(R.id.edtEmail).getElemento());
        assertNotNull(login.getElemento(R.id.edtPass).getElemento());
        assertNotNull(login.getElemento(R.id.btnLogin).getElemento());
    }

    @Test
    @UiThreadTest
    public void login() throws InterruptedException {

        final Object syncObject = new Object();
        WebServices.login(
                "jamper91",
                "12345",
                login,
                login.admin,
                new ResultWebServiceInterface() {
                    @Override
                    public void ok(ResultWebServiceOk ok) {
                            assertNull(ok);
                            Gson gson=new Gson();
                            LoginResponse data = (LoginResponse)ok.getData();
                            Log.d("data", gson.toJson(data));
                            assertNotNull(data);
                            synchronized (syncObject){
                                syncObject.notify();
                            }
                    }

                    @Override
                    public void fail(ResultWebServiceFail fail) {
                        assertNull(fail);
                        synchronized (syncObject){
                            syncObject.notify();
                        }
                    }
                });

        synchronized (syncObject){
            syncObject.wait();
        }

    }

    @Test
    public void sync(){
//        WebServices.sync(
//                login,
//                login.admin,
//                new ResultWebServiceInterface() {
//                    @Override
//                    public void ok(ResultWebServiceOk ok) {
//                        assertNotNull(ok);
//                        SyncResponse data = (SyncResponse)ok.getData();
//                        assertNotNull(data);
//                    }
//
//                    @Override
//                    public void fail(ResultWebServiceFail fail) {
//                        assertNull(fail);
//                    }
//                }
//        );
    }

    @After
    public void tearDown() throws Exception {
        login = null;
    }
}