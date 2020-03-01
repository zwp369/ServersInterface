import com.github.mustachejava.DefaultMustacheFactory;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.StringContains.containsString;
/**
 * 联系写发送message
 */
public class Meaasge {


public Response send(String data){
    //  useRelaxedHTTPSValidation(); 走代理被https拦截，增加这个
    return given()
            //.proxy(8080)//走本地代理工具
            .log().all()
                  .queryParam("access_token",ConfigFile.getInstance().token)
                   .contentType(ContentType.JSON)
                .body(data)
            .when()
                   .post("https://qyapi.weixin.qq.com/cgi-bin/message/send")
            .then()
             .log().all().extract().response();
}

    /**
     *组装数据，调用api访问请求
     * @param to
     * @param msg
     * @param agentId
     * @return
     */
    public Response send(String to, String msg,Integer agentId){
    HashMap<String,Object> data =new HashMap<String, Object>();
    data.put("to",to);
    data.put("msg",msg);
    data.put("agentId",agentId);
   String body =template("data/meassge.json",data);
    return  send(body);
    }


    String template(String path,HashMap<String, Object> data){
        Writer writer = new StringWriter();
        new DefaultMustacheFactory()
                .compile(path)
                .execute(writer,data);
        return writer.toString();
        //System.out.println(writer.toString());
    }
}
