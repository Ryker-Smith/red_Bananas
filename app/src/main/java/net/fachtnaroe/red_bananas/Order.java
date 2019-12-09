package net.fachtnaroe.red_bananas;

import android.provider.ContactsContract;

import androidx.core.view.KeyEventDispatcher;

import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.VerticalScrollArrangement;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.ListView;
import com.google.appinventor.components.runtime.Web;
import com.google.appinventor.components.runtime.File;
import com.google.appinventor.components.runtime.Notifier;

import java.util.ArrayList;
import java.util.List;


public class Order extends Form implements HandlesEventDispatching {
    private Button BTN_Test, BTN_BuyItem;
    private VerticalScrollArrangement VArr;
    private Label LBL_Title, LBL_UserN, LBL_UserTXT, LBL_pID, LBL_pIDTXT, LBL_AvToOrdr, LBL_Credit, LBL_CreditTXT, LBL_Ordered;
    private HorizontalArrangement HArr1, HArr2, HArr3, HArr4, HArr5, HArr6, HArr7;
    private ListView LST_ThingsA, LST_ThingsO, LST_Temp;
    private String baseURL = "https://fachtnaroe.net/bananas?",
            SessionID = MainActivity.getSessionID(),
            pID = MainActivity.getPID(),
            username=MainActivity.getUsername(),
            getCreditURL=baseURL+"sessionID="+ SessionID + "&entity=person&method=GET&pID="+ pID;
    private Web Web_TfS, Web_TB,Web_Credit,Web_Credit2,Web_PlaceOrder;
    private File FileDBS;
    private Notifier messages;
    private Double newCredit;


    protected void $define() {
        VArr = new VerticalScrollArrangement(this);

        HArr1 = new HorizontalArrangement(VArr);
        HArr1.WidthPercent(100);
        HArr1.HeightPercent(10);
        HArr1.BackgroundColor(Component.COLOR_BLACK);

        HArr2 = new HorizontalArrangement(VArr);
        HArr2.WidthPercent(100);
        HArr2.HeightPercent(5);
        HArr2.BackgroundColor(Component.COLOR_BLACK);

        HArr3 = new HorizontalArrangement(VArr);
        HArr3.WidthPercent(100);
        HArr3.HeightPercent(5);
        HArr3.BackgroundColor(Component.COLOR_PINK);

        HArr4 = new HorizontalArrangement(VArr);
        HArr4.WidthPercent(100);
        HArr4.HeightPercent(40);
        HArr4.BackgroundColor(Component.COLOR_LTGRAY);

        HArr5 = new HorizontalArrangement(VArr);
        HArr5.WidthPercent(100);
        HArr5.HeightPercent(7);
        HArr5.BackgroundColor(Component.COLOR_CYAN);

        HArr6 = new HorizontalArrangement(VArr);
        HArr6.WidthPercent(100);
        HArr6.HeightPercent(5);
        HArr6.BackgroundColor(Component.COLOR_MAGENTA);

        HArr7 = new HorizontalArrangement(VArr);
        HArr7.WidthPercent(100);
        HArr7.HeightPercent(25);
        HArr7.BackgroundColor(Component.COLOR_YELLOW);

        LBL_Title = new Label(HArr1);
        LBL_Title.WidthPercent(100);
        LBL_Title.FontSize(30);
        LBL_Title.Text("Food Delivery Service");
        LBL_Title.TextColor(Component.COLOR_ORANGE);

        LBL_UserN = new Label(HArr2);
        LBL_UserN.WidthPercent(25);
        LBL_UserN.FontSize(15);
        LBL_UserN.Text("Username:");
        LBL_UserN.TextColor(COLOR_BLUE);

        LBL_UserTXT = new Label(HArr2);
        LBL_UserTXT.WidthPercent(50);
        LBL_UserTXT.FontSize(15);
        LBL_UserTXT.Text(username);
        LBL_UserTXT.TextColor(COLOR_RED);

        LBL_pID = new Label(HArr2);
        LBL_pID.WidthPercent(12);
        LBL_pID.FontSize(15);
        LBL_pID.Text("pID:");
        LBL_pID.TextColor(COLOR_BLUE);

        LBL_pIDTXT = new Label(HArr2);
        LBL_pIDTXT.WidthPercent(13);
        LBL_pIDTXT.FontSize(15);
        LBL_pIDTXT.Text(pID);
        LBL_pIDTXT.TextColor(COLOR_RED);

        LBL_AvToOrdr = new Label(HArr3);
        LBL_AvToOrdr.WidthPercent(100);

        LBL_AvToOrdr.FontSize(12);
        LBL_AvToOrdr.Text("Things Available For Purchase:");
        LBL_AvToOrdr.TextColor(Component.COLOR_ORANGE);

        LST_ThingsA = new ListView(HArr4);
        LST_ThingsA.WidthPercent(100);

        LST_ThingsA.TextColor(Component.COLOR_ORANGE);

        LBL_Credit = new Label(HArr5);
        LBL_Credit.FontSize(16);
        LBL_Credit.WidthPercent(15);
        LBL_Credit.TextColor(Component.COLOR_MAGENTA);
        LBL_Credit.Text("Credit  ");

        LBL_CreditTXT = new Label(HArr5);
        LBL_CreditTXT.FontSize(16);
        LBL_CreditTXT.TextColor(Component.COLOR_BLACK);
        LBL_CreditTXT.WidthPercent(35);

        BTN_BuyItem = new Button(HArr5);
        BTN_BuyItem.Text("Buy");
        BTN_BuyItem.WidthPercent(50);
        BTN_BuyItem.TextColor(COLOR_BLACK);

        LBL_Ordered = new Label(HArr6);
        LBL_Ordered.WidthPercent(100);

        LBL_Ordered.FontSize(12);
        LBL_Ordered.Text("Things I've Ordered:");
        LBL_Ordered.TextColor(Component.COLOR_ORANGE);

        LST_ThingsO = new ListView(HArr7);
        LST_ThingsO.WidthPercent(100);
        LST_ThingsO.TextColor(Component.COLOR_WHITE);

        messages = new Notifier(this);
        messages.BackgroundColor(Component.COLOR_RED);
        messages.TextColor(Component.COLOR_WHITE);

        Web_TfS = new Web(this);
        Web_TfS.Url(baseURL + "sessionID=" + SessionID + "&entity=thing&method=GET");
        Web_TfS.Get();

        Web_TB = new Web(this);
        Web_TB.Url(baseURL + "sessionID=" + SessionID + "&entity=prettyorders&method=GET");
        Web_TB.Get();

        Web_Credit= new Web(this);
        Web_Credit.Url(baseURL+"sessionID="+ SessionID + "&entity=person&method=GET&pID="+ pID);
        Web_Credit.Get();

        Web_Credit2= new Web(this);

        Web_PlaceOrder= new Web(this);

        EventDispatcher.registerEventForDelegation(this, formName, "Click");
        EventDispatcher.registerEventForDelegation(this, formName, "Initialize");
        EventDispatcher.registerEventForDelegation(this, "GotTextEvent", "GotText" );
    }

    public boolean dispatchEvent(Component component, String componentName, String eventName, Object[] params) {
        if (eventName.equals("Click")) {
            if (component.equals(BTN_BuyItem)) {
                buyThis(LST_ThingsA.Selection());
                return true;
            } else {
                return false;
            }
        }
        if (component.equals(Web_TfS) && eventName.equals("GotText")) {
            JsonSortThingsListView((String) params[3]);
            return true;
        }
        if (component.equals(Web_TB) && eventName.equals("GotText")
        ) {
            sortJsonShite((String)params[3]);
            return true;
        }
        if (component.equals(Web_Credit) && eventName.equals("GotText")) {
            JsonCreditThings((String) params[3]);
            return true;
        }
        if (component.equals(Web_PlaceOrder) && eventName.equals("GotText")) {
            String response=((String) params[3]);
            if(response.contains("OK")){
                String oid =response.substring(22,response.length()-2);
                messages.ShowAlert("Your Order Has Been Placed : "+oid);

            }
            return true;
        }
        return false;
    }
    public void buyThis(String x) {
        if((LST_ThingsA.Selection().isEmpty())) {
            messages.ShowAlert("No Item Selected");
        }
        else {
            int i = x.indexOf("]");
            int euro = x.indexOf("€")+1;
            String price =x.substring(euro,x.length());


            String oldCredit = LBL_CreditTXT.Text().replace("€","");
//            newCredit=Double.parseDouble(oldCredit)-Double.parseDouble(price);
//            String s=Double
//            LBL_Ordered.Text(Double.toString(newCredit));
//            LBL_CreditTXT.Text("€"+newCredit);
//            LBL_Ordered.Text(oldCredit+" "+price);
            String y = x.substring(1,i);
            String[] url_IDs =y.split(":");
            Web_PlaceOrder.Url(baseURL+"sessionID="+SessionID+"&entity=orders&method=POST&tID="+url_IDs[0]+"&sellerID="+url_IDs[1]+"&slotNum=1&buyerID="+pID);
            Web_PlaceOrder.Get();
//            creditUpdateAfterBuy(Integer.parseInt(oldCredit), Integer.parseInt(price));
            creditUpdateAfterBuy(Double.parseDouble(oldCredit), Double.parseDouble(price));
        }
    }
    public void creditUpdateAfterBuy(Double x, Double y){
       Double i = x-y;
       String p = Double.toString(i);
        LBL_CreditTXT.Text("€"+p);
        Web_Credit2.Url(baseURL+"sessionID="+ SessionID + "&entity=person&method=PUT&pID="+ pID+"&Credit="+p);
        Web_Credit2.Get();
    }
    public void JsonSortThingsListView(String jsonString) {

// for loop to sort by pID
        String Temp1 = "";
        //Used https://stackoverflow.com/questions/48449004/java-storing-the-output-of-a-for-loop-into-an-array/48449039 and https://www.w3schools.com/java/java_ref_string.asp
        List<String> jsonIsMySon = new ArrayList<String>();
        char start = '{';
        char finish = '}';
        int e = 0;
        for (int i = 0; i < jsonString.length(); i++) {
            char thisChar = jsonString.charAt(i);
            if (thisChar == start) {

                e = i+1;
            }
            else if ((thisChar == finish)) {
                String Temp2 = jsonString.substring(e, i);;
                if (!(Temp2.contains("]"))){
                    if (Temp2.contains("tSoldBy\":\"")) {
                        jsonIsMySon.add(Temp2);
                    }
                }
            }

        }

//        LBL_AvToOrdr.Text(jsonIsMySon.get(1));

//        String loge = "";
        //For Loop to Rearrange Data To How I want
        String Temp3="";
        for (int a=0;a<jsonIsMySon.size();a++){
//            String tempi="";

                String r1 = jsonIsMySon.get(a).replace("\",\"", "<SPLIT>");
                String r2 = r1.replace(",", "-");
//                tempi = r2.replace("*@*", ",");

            String[] keyValueArray = r2.split("<SPLIT>");
            jsonIsMySon.set(a,"["+keyValueArray[1]+":"+keyValueArray[5]+"]"+keyValueArray[2]+"("+keyValueArray[0]+")€"+keyValueArray[4]);


            if(a==0){
                //Rearrange Json data [0]=tDescription,[1]=tID,[2]=tName,[3]=tPicture,[4]=tPrice,[5]=tSoldBy  logetiddy
//                String[] keyValueArray = r1.split(",");
//                jsonIsMySon.set(a,"["+keyValueArray[2]+":"+keyValueArray[6]+"]"+keyValueArray[3]+"("+keyValueArray[1]+")€"+keyValueArray[5]);
                Temp3+=jsonIsMySon.get(a);
            }
            else{
                //Rearrange Json data [0]=tDescription,[1]=tID,[2]=tName,[3]=tPicture,[4]=tPrice,[5]=tSoldBy  logetiddy
//                String[] keyValueArray = r1.split(",");
//                jsonIsMySon.set(a,"["+keyValueArray[1]+":"+keyValueArray[5]+"]"+keyValueArray[2]+"("+keyValueArray[0]+")€"+keyValueArray[4]);
                Temp3+=","+jsonIsMySon.get(a);
            }
        }

        //Format for use in listView-Remove KeyNames
        String r2 = Temp3.replace("\":\"","");
        String r3 = r2.replace("\"tDescription","");
        String r4 = r3.replace("tID","");
        String r5 = r4.replace("tName","");
        String r6 = r5.replace("tPrice","");
        String r7 = r6.replace("tSoldBy","");
        String r8 = r7.replace("\"","");

        LST_ThingsA.ElementsFromString(r8);
       // String y=jsonIsMySon.get(0);


    }
    public void JsonCreditThings(String Y) {
        int start = Y.lastIndexOf("Credit")+9;
        int finish = Y.lastIndexOf("Email")-3;
        String Rep1 = Y.substring(start,finish);
        LBL_CreditTXT.Text("€"+Rep1);
    }

    public void sortJsonShite(String jsonString){
        List<String> jsonIsMySon = new ArrayList<String>();
        String Temp1 = "";
        char start = '{';
        char finish = '}';
        int e = 0;
        for     (int i = 0; i < jsonString.length(); i++) {
            char thisChar = jsonString.charAt(i);
            if (thisChar == start) {
                e = i+1;
            }
            else if ((thisChar == finish)) {
                String Temp2 = jsonString.substring(e, i);
                if (!(Temp2.contains("]"))){
                    if (Temp2.contains("buyerID\":\"" + pID)) {
                        jsonIsMySon.add(Temp2);
                    }
                }
            }

        }
//        for (int a=0;a<jsonIsMySon.size();a++){
//            Temp1+=jsonIsMySon.get(a)+"*-*";
//        }
//        String Loge="";

        String Temp3="";
        for (int a=0;a<jsonIsMySon.size();a++){
            String r1 = jsonIsMySon.get(a).replace("\",\"",",");

            //Rearrange Json data [0]=buyerID,[1]=oID,[2]=seller name ,[3]=tID,[4]=tName-ItemName
            String[] keyValueArray = r1.split(",");
            jsonIsMySon.set(a,"["+keyValueArray[1]+"] "+keyValueArray[4]+" from "+keyValueArray[2]);
            if(a==0){
                Temp3+=jsonIsMySon.get(a);
            }
            else{
                Temp3+=","+jsonIsMySon.get(a);
            }
        }

        String r1 = Temp3.replace("\"", "");
        String r2 = r1.replace(":", "");
        String r3 = r2.replace("oID", "");
        String r4 = r3.replace("tName", "");
        String r5 = r4.replace("seller", "");
        LST_ThingsO.ElementsFromString(r5);
    }

}