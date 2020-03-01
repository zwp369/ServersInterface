import static io.restassured.RestAssured.given;
import static org.hamcrest.core.StringContains.containsString;

public class ConfigFile {
    public String corpId="wwada2095e6cc62409";
    public Integer agentId=1000002;
    public String corpSecret="ARQj3Sgdm7abfno_ffCmrPMjZJIUs_wHPWS9ZFtoycg";
    public String token="";


    static ConfigFile config; //创建懒汉单列模式，自己类中new实例，一个静态的，使用的时候不需要在实例化
    public static ConfigFile getInstance(){
        if(config == null){
            config = new ConfigFile();
            config.token=config.getToken();
        }
        return config;
    }

    private String  getToken(){
        return given()
                .queryParam("corpid",ConfigFile.getInstance().corpId).
                queryParam("corpsecret",ConfigFile.getInstance().corpSecret)
        .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
        .then()
                .statusCode(200)
                .body(containsString("ok"))
        .extract().body().path("access_token");

    }



}
