package net.fachtnaroe.red_bananas;

import android.content.Intent;
import android.util.Log;

import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.ListView;
import com.google.appinventor.components.runtime.Notifier;
import com.google.appinventor.components.runtime.VerticalArrangement;
import com.google.appinventor.components.runtime.Web;
import com.google.appinventor.components.runtime.util.YailList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Sales extends Form implements HandlesEventDispatching {
    private Button butt1, btnDelete,btnAddNew,btnOrderIsCompleted;
    private VerticalArrangement VArr;
    private HorizontalArrangement HArr_User_pID, HArr2Btn, HArr1Btn;
    private Label titleFDS,Label_Username, Username_L,Label_pID, pId_L, things4Sale_Label, thingsSold_Label;
    private ListView thingsWeSell,thingsSold;
    private String baseURL ="https://fachtnaroe.net/bananas?",
            TheUsername=MainActivity.getUsername(),
            pID =MainActivity.getPID(),
            SessionID=MainActivity.getSessionID(),
            getThingsForSaleURL =baseURL+"sessionID="+SessionID+"&entity=thing&method=GET",
            getThingsSoldURL=baseURL+"sessionID="+SessionID+"&entity=orders&method=GET",
            webThingDeletURL=baseURL+"sessionID="+SessionID+"&entity=thing&method=DELETE&tID=",
            webOrderIsCompleteURL=baseURL+"sessionID="+SessionID+"&entity=orders&method=DELETE&oID=";
    private Web webGetThings4Sale,webGetThingsSold,webThingDelete,webOrderIsComplete;
    private Notifier  GotTextNotifier;



    protected void $define() {

        GotTextNotifier = new Notifier(this);
        GotTextNotifier.BackgroundColor(Component.COLOR_RED);
        GotTextNotifier.TextColor(Component.COLOR_WHITE);

        VArr = new VerticalArrangement(this);
        VArr.Height(LENGTH_FILL_PARENT);
        VArr.Width(LENGTH_FILL_PARENT);

        titleFDS = new Label(VArr);
        titleFDS.Text("Food Delivery Service");
        titleFDS.FontSize(15);
        titleFDS.WidthPercent(100);
        titleFDS.TextAlignment(Component.ALIGNMENT_CENTER);
        titleFDS.BackgroundColor(COLOR_GREEN);
        titleFDS.TextColor(COLOR_WHITE);

        HArr_User_pID = new HorizontalArrangement(VArr);
        HArr_User_pID.WidthPercent(100);
       // HArr_User_pID.HeightPercent(10);
        HArr_User_pID.BackgroundColor(Component.COLOR_LTGRAY);

        Label_Username = new Label(HArr_User_pID);
        Label_Username.WidthPercent(30);
        Label_Username.TextAlignment(Component.ALIGNMENT_NORMAL);
        Label_Username.FontSize(14);
        Label_Username.Text("Username");

        Username_L = new Label(HArr_User_pID);
        Username_L.WidthPercent(50);
        Username_L.TextAlignment(Component.ALIGNMENT_NORMAL);
        Username_L.FontSize(14);
        Username_L.TextColor(COLOR_BLUE);
        Username_L.Text(TheUsername);

        Label_pID = new Label(HArr_User_pID);
        Label_pID.WidthPercent(10);
        Label_pID.TextAlignment(Component.ALIGNMENT_OPPOSITE);
        Label_pID.FontSize(14);
        Label_pID.Text("pID");

        pId_L = new Label(HArr_User_pID);
        pId_L.WidthPercent(10);
        pId_L.TextAlignment(Component.ALIGNMENT_NORMAL);
        pId_L.FontSize(14);
        pId_L.TextColor(COLOR_BLUE);
        pId_L.Text(pID);

        things4Sale_Label = new Label(VArr);
        things4Sale_Label.FontSize(12);
        things4Sale_Label.TextColor(COLOR_WHITE);
        things4Sale_Label.BackgroundColor(COLOR_GREEN);
        things4Sale_Label.WidthPercent(100);
        things4Sale_Label.Text("My things for sale");

        //put a listview here(muoy impotante)
        thingsWeSell = new ListView(VArr);
        thingsWeSell.WidthPercent(100);
        thingsWeSell.HeightPercent(30);
        thingsWeSell.TextSize(18);
        thingsWeSell.BackgroundColor(COLOR_DKGRAY);

        HArr2Btn = new HorizontalArrangement(VArr);
        HArr2Btn.WidthPercent(100);

        btnDelete = new Button(HArr2Btn);
        btnDelete.Text("Delete");
        btnDelete.WidthPercent(50);
        btnDelete.FontSize(12);

        btnAddNew = new Button(HArr2Btn);
        btnAddNew.Text("Add New");
        btnAddNew.WidthPercent(50);
        btnAddNew.FontSize(12);

        thingsSold_Label = new Label(VArr);
        thingsSold_Label.Text("My things Sold");
        thingsSold_Label.WidthPercent(100);
        thingsSold_Label.BackgroundColor(COLOR_GREEN);
        thingsSold_Label.FontSize(12);
        thingsSold_Label.TextColor(COLOR_WHITE);

        //Listview Here
        thingsSold = new ListView(VArr);
        thingsSold.WidthPercent(100);
        thingsSold.HeightPercent(30);
        thingsSold.TextSize(35);
        thingsSold.BackgroundColor(COLOR_DKGRAY);

        btnOrderIsCompleted = new Button(VArr);
        btnOrderIsCompleted.WidthPercent(100);
        btnOrderIsCompleted.Text("Order is completed");
        btnOrderIsCompleted.FontSize(12);

        webGetThings4Sale = new Web(this);
        webGetThings4Sale.Url(getThingsForSaleURL);
        webGetThings4Sale.Get();

        webGetThingsSold = new Web(this);
        webGetThingsSold.Url(getThingsSoldURL);
        webGetThingsSold.Get();

        webThingDelete = new Web(this);

        webOrderIsComplete  = new Web(this);

        butt1 = new Button(VArr);
        butt1.Text("Go Back");
        butt1.WidthPercent(100);
        butt1.HeightPercent(10);

        EventDispatcher.registerEventForDelegation( this, formName, "Click" );
        EventDispatcher.registerEventForDelegation( this, formName, "GotText" );
    }
    public boolean dispatchEvent(Component component, String componentName, String eventName, Object[] params){
        if (eventName.equals("Click")) {
            if (component.equals(butt1)) {
                mainActGo();
                return true;
            }
            if (component.equals(btnAddNew)) {
                NewSaleItemScreenGo();
                return true;
            }
            if (component.equals(btnDelete)) {
                things4Sale_Label.Text(things4Sale_Label.Text()+" >");
                deletThis(thingsWeSell.Selection());
                return true;
            }
            if (component.equals(btnOrderIsCompleted)) {
                thingsSold_Label.Text(thingsSold_Label.Text()+" >");
                removeCompletedOrder(thingsSold.Selection());
                return true;
            }
        }
        if(component.equals(webGetThings4Sale) && eventName.equals("GotText")){
//            JSONObject  obj = new  JSONObject((String)params[3]);
//            JSONArray arr = obj.getJSONArray("thing");
//            //String temporary =obj.getJSONObject("thing").getString("tName");
            sortJson4GetThings4Sale((String)params[3], pID);
            return true;
        }
        if(component.equals(webGetThingsSold) && eventName.equals("GotText")){
//            sortJson4GetThingsSold((String)params[3], pID);
            webGotText((String) params[1],(String) params[3]);
            return true;
        }

        return false;
    }
    public void mainActGo(){
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }
    public void NewSaleItemScreenGo(){
        Intent i = new Intent(getApplicationContext(),NewSaleItem.class);
        startActivity(i);
    }
    public void removeCompletedOrder(String selection){
        if((thingsSold.Selection().isEmpty())) {
            GotTextNotifier.ShowAlert("No Order Selected");
        }
        else {
            String oIDForURL = selection.substring(1, 3);
            webOrderIsComplete.Url(webOrderIsCompleteURL+oIDForURL);
            webOrderIsComplete.Get();
            GotTextNotifier.ShowAlert("Order "+oIDForURL+" removed");
            //webGetThingsSold.Get();
            //https://stackoverflow.com/questions/3053761/reload-activity-in-android
            finish();
            startActivity(getIntent());
        }
    }
    public void deletThis(String selection){
        if((thingsWeSell.Selection().isEmpty())) {
            GotTextNotifier.ShowAlert("No Item Selected");
        }
        else {
            String tIDForURL = selection.substring(1, 3);
            webThingDelete.Url(webThingDeletURL+tIDForURL);
            webThingDelete.Get();
            GotTextNotifier.ShowAlert("Item "+tIDForURL+" removed");
            //webGetThings4Sale.Get();
            //thingsWeSell.ElementsFromString("");
//            thingsWeSell.SelectionColor(Component.COLOR_RED);
//            titleFDS.Text("delet");
            finish();
            startActivity(getIntent());
        }
    }
    public void sortJson4GetThingsSold(String jsonString, String pID){

        // for loop to sort by sellerID with pID
        String Temp1 = "";
        //Used https://stackoverflow.com/questions/48449004/java-storing-the-output-of-a-for-loop-into-an-array/48449039 and https://www.w3schools.com/java/java_ref_string.asp
        List<String> jsonIsMySon = new ArrayList<String>();
        Log.w("PUTSOMETHINGHERE","IN");
        Log.w("PROG",pID);
        Log.w("fdg",jsonString);
        char start = '{';
        char finish = '}';
        int e = 0;
        for (int i = 0; i < jsonString.length(); i++) {
            char thisChar = jsonString.charAt(i);
            if (thisChar == start) {
                e = i;
            }
            else if ((thisChar == finish)) {
                String Temp2 = jsonString.substring(e, i);
                if (!(Temp2.contains("]"))){
                    if (Temp2.contains("sellerID\":\""+ pID)) {
                        jsonIsMySon.add(Temp2);
                    }
                    else {
                        Log.w("pID not matched ",Temp2);
                    }
                }
            }

        }
        //For Loop to Rearrange Data To How I want
        String Temp3="";
        for (int a=0;a<jsonIsMySon.size();a++){
            String r1 = jsonIsMySon.get(a).replace("\",\"", "<SPLIT>");
            String r2 = r1.replace(",", "-");
            String[] keyValueArray = r2.split("<SPLIT>");
            //Rearrange Json data [0]=Name,[1]=oID,[2]=sellerID,[3]=slotNum,[4]=tName
            jsonIsMySon.set(a,"["+keyValueArray[1]+"]"+keyValueArray[4]+" for "+keyValueArray[0]+keyValueArray[3]+"["+ keyValueArray[2] + "]");
            if(a==0){
                Temp3+=jsonIsMySon.get(a);
            }
            else{
                Temp3+=","+jsonIsMySon.get(a);
            }
        }
        //Format for use in listView-Remove KeyNames
        String r2 = Temp3.replace("\":\"","");
        String r3 = r2.replace("{\"Name","");
        String r4 = r3.replace("oID","");
        String r5 = r4.replace("sellerID","");
        String r6 = r5;//.replace(pID,"");
        String r7 = r6.replace("slotNum"," Slot:");
        String r8 = r7.replace("tName","");
        String r9 = r8.replace("\"","");
        thingsSold.ElementsFromString(r9);
    }
    public void sortJson4GetThings4Sale(String jsonString, String pID) {
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
                    if (Temp2.contains("tSoldBy\":\"" + pID)) {
                        jsonIsMySon.add(Temp2);
                    }
                }
            }

        }
        //For Loop to Rearrange Data To How I want
        String Temp3="";
        for (int a=0;a<jsonIsMySon.size();a++){
            String r1 = jsonIsMySon.get(a).replace("\",\"", "<SPLIT>");
            String r2 = r1.replace(",", "-");
            String[] keyValueArray = r2.split("<SPLIT>");
            //Rearrange Json data [0]=tDescription,[1]=tID,[2]=tName,[3]=tPicture,[4]=tPrice,[5]=tSoldBy
            jsonIsMySon.set(a,"["+keyValueArray[1]+"]"+keyValueArray[2]+"("+keyValueArray[0]+")€"+keyValueArray[4]);
            if(a==0){
                Temp3+=jsonIsMySon.get(a);
            }
            else{
                Temp3+=","+jsonIsMySon.get(a);
            }
        }

        //Format for use in listView-Remove KeyNames
        String r2 = Temp3.replace("\":\"","");
        String r3 = r2.replace("\"tDescription","");
        String r4 = r3.replace("tID","");
        String r5 = r4.replace("tName","");
        String r6 = r5.replace("tPrice","");
       thingsWeSell.ElementsFromString(r6);
    }

    public void webGotText(String status, String textOfResponse) {
//        String temp=new String();
        List<String> MyOrders;
        if (status.equals("200") ) try {
            MyOrders = new ArrayList<String>();
            // See:  https://stackoverflow.com/questions/5015844/parsing-json-object-in-java
            JSONObject parser = new JSONObject(textOfResponse);
            if (!parser.getString("orders").equals("")) {
                JSONArray jsonIsMySon = parser.getJSONArray("orders");
                for (int i = 0; i < jsonIsMySon.length(); i++) {

                  if (jsonIsMySon.getJSONObject(i).getString("sellerID")==pID){
                    MyOrders.add(jsonIsMySon.getJSONObject(i).toString());
                  }
                }
                YailList tempData = YailList.makeList(MyOrders);
                thingsSold.Elements(tempData);
            }
        } catch (JSONException e) {
            // if an exception occurs, code for it in here
            GotTextNotifier.ShowMessageDialog("Error 3.353; JSON Exception (check password) ", "Information", "OK");
        }
        else {
            GotTextNotifier.ShowMessageDialog("Error 3.356; Problem connecting with server","Information", "OK");
        }
    }
 }