import com.github.mustachejava.DefaultMustacheFactory;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.StringContains.containsString;


public class Demotest {

    @Test
    void  getToken(){
        given().
                log().all().
                queryParam("corpid","wwada2095e6cc62409").
                queryParam("corpsecret","ARQj3Sgdm7abfno_ffCmrPMjZJIUs_wHPWS9ZFtoycg")
        .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
        .then()
                .log().all()
                .statusCode(200)
                .body(containsString("ok"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "hello",
            "2"
    })
    void sendMeaasge(String msg){

        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("touser","@all");
        data.put("toparty","");
        data.put("totag","");
        data.put("msgtype","text");
        data.put("agentid",ConfigFile.getInstance().agentId);
        HashMap<String,Object> content = new HashMap<String, Object>();
        content.put("content", msg);
        data.put("text",content);
        data.put("safe","0");
        data.put("enable_id_trans","");
        data.put("enable_duplicate_check","");
        Meaasge message =new Meaasge();
        message.send("@all",msg,ConfigFile.getInstance().agentId)
                .then()
                .body("errcode", equalTo(0));



    }
    @ParameterizedTest
    @ValueSource(strings = {
            "hello1",
            "21"
    })
    void sendMeaasge3(String msg){

        Meaasge message =new Meaasge();
        message.send("@all",msg,ConfigFile.getInstance().agentId)
                .then()
                .body("errcode", equalTo(0));

    }




    @Test
    void getInstance(){

        System.out.println(ConfigFile.getInstance().token);
        System.out.println(ConfigFile.getInstance().corpId);
    }


 @Test
    void template(){
     Writer writer = new StringWriter();
     HashMap<String, Object> data = new HashMap<String, Object>();
     data.put("to","@all");
     data.put("msg","Xxxxxxxx");
      new DefaultMustacheFactory()
              .compile("data/meassge.json")
              .execute(writer,data);
     System.out.println(writer.toString());
 }


}
