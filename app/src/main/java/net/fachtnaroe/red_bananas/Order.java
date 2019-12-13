package net.fachtnaroe.red_bananas;

import android.util.Log;

import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.File;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.ListView;
import com.google.appinventor.components.runtime.Notifier;
import com.google.appinventor.components.runtime.VerticalScrollArrangement;
import com.google.appinventor.components.runtime.Web;
import com.google.appinventor.components.runtime.util.YailList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
            username = MainActivity.getUsername(),
            getCreditURL = baseURL + "sessionID=" + SessionID + "&entity=person&method=GET&pID=" + pID;
    private Web Web_TfS, Web_TB, Web_Credit, Web_Credit2, Web_PlaceOrder;
    private Notifier messages;

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
        LST_ThingsA.TextSize(35);

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
        LST_ThingsO.TextSize(35);

        messages = new Notifier(this);
        messages.BackgroundColor(Component.COLOR_RED);
        messages.TextColor(Component.COLOR_WHITE);

        Web_TfS = new Web(this);
        Web_TfS.Url(baseURL + "sessionID=" + SessionID + "&entity=thing&method=GET");
        Web_TfS.Get();

        Web_TB = new Web(this);
        Web_TB.Url(baseURL + "sessionID=" + SessionID + "&entity=prettyorders&method=GET");
        Web_TB.Get();

        Web_Credit = new Web(this);
        Web_Credit.Url(getCreditURL);
        Web_Credit.Get();

        Web_Credit2 = new Web(this);

        Web_PlaceOrder = new Web(this);

        EventDispatcher.registerEventForDelegation(this, formName, "Click");
        EventDispatcher.registerEventForDelegation(this, formName, "Initialize");
        EventDispatcher.registerEventForDelegation(this, "GotTextEvent", "GotText");
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
            jsonSortAndListViewForBuyerScreen(params[1].toString(), (String) params[3],"thing", "null");
            return true;
        }
        if (component.equals(Web_TB) && eventName.equals("GotText")
        ) {
            jsonSortAndListViewForBuyerScreen(params[1].toString(), (String) params[3],"prettyorders", "buyerID");
            return true;
        }
        if (component.equals(Web_Credit) && eventName.equals("GotText")) {
            JsonCreditThings((String) params[3]);
            return true;
        }
        if (component.equals(Web_PlaceOrder) && eventName.equals("GotText")) {
            String response = ((String) params[3]);
            if (response.contains("OK")) {
                String oid = response.substring(22, response.length() - 2);
                messages.ShowAlert("Your Order Has Been Placed : " + oid);

            }
            return true;
        }
        return false;
    }

    public void buyThis(String x) {
        if ((LST_ThingsA.Selection().isEmpty())) {
            messages.ShowAlert("No Item Selected");
        } else {
            int i = x.indexOf("]");
            int euro = x.indexOf("€") + 1;
            String price = x.substring(euro);
            String oldCredit = LBL_CreditTXT.Text().replace("€", "");
            String y = x.substring(1, i);
            String[] url_IDs = y.split(":");
            Web_PlaceOrder.Url(baseURL + "sessionID=" + SessionID + "&entity=orders&method=POST&tID=" + url_IDs[0] + "&sellerID=" + url_IDs[1] + "&slotNum=1&buyerID=" + pID);
            Web_PlaceOrder.Get();
            creditUpdateAfterBuy(Double.parseDouble(oldCredit), Double.parseDouble(price));
        }
    }

    public void creditUpdateAfterBuy(Double x, Double y) {
        Double i = x - y;
        String p = Double.toString(i);
        LBL_CreditTXT.Text("€" + p);
        Web_Credit2.Url(getCreditURL+ "&Credit=" + p);
        Web_Credit2.Get();
    }
    public void JsonCreditThings(String Y) {
        int start = Y.lastIndexOf("Credit") + 9;
        int finish = Y.lastIndexOf("Email") - 3;
        String Rep1 = Y.substring(start, finish);
        LBL_CreditTXT.Text("€" + Rep1);
    }
    public void jsonSortAndListViewForBuyerScreen(String status, String textOfResponse, String tableName, String fieldName) {
        List<String> ListViewItemArray;
        if (status.equals("200")) try {
            ListViewItemArray = new ArrayList<String>();
            // See:  https://stackoverflow.com/questions/5015844/parsing-json-object-in-java
            JSONObject parser = new JSONObject(textOfResponse);
            if (!parser.getString(tableName).equals("")) {
                JSONArray jsonIsMySon = parser.getJSONArray(tableName);
                for (int i = 0; i < jsonIsMySon.length(); i++) {
                    String oneEntryInTheListView = "";
                    //add data from table to the sting above by getting the field name you want from the brief ( example where field name is "sellerID": oneEntryInTheListView = jsonIsMySon.getJSONObject(i).getString("sellerID"); )
                    //formats entries the ListView containing the items in thing table
                    if (tableName.equals("thing") && fieldName.equals("null")){
                        oneEntryInTheListView = "[" + jsonIsMySon.getJSONObject(i).getString("tID")
                                + " : " + jsonIsMySon.getJSONObject(i).getString("tSoldBy")
                                + "] " + jsonIsMySon.getJSONObject(i).getString("tName")
                                + " (" + jsonIsMySon.getJSONObject(i).getString("tDescription")
                                + ") €" + jsonIsMySon.getJSONObject(i).getString("tPrice");
                        ListViewItemArray.add(oneEntryInTheListView);
                    }
                    //formats entries the ListView containing the orders buyer has placed
                    else if ((tableName.equals("prettyorders") && fieldName.equals("buyerID")) && (Integer.valueOf(jsonIsMySon.getJSONObject(i).getString(fieldName)).equals( Integer.valueOf(pID)))) {
                        oneEntryInTheListView = "[" + jsonIsMySon.getJSONObject(i).getString("oID")
                                + "] " + jsonIsMySon.getJSONObject(i).getString("tName")
                                + " from " + jsonIsMySon.getJSONObject(i).getString("seller")
                                + " [ tID: " + jsonIsMySon.getJSONObject(i).getString("tID") + " ]";
                        ListViewItemArray.add(oneEntryInTheListView);
                    }
                }
                YailList tempData = YailList.makeList(ListViewItemArray);
                if (tableName.equals("prettyorders") && fieldName.equals("buyerID")) {
                    LST_ThingsO.Elements(tempData);
                }
                if (tableName.equals("thing") && fieldName.equals("null")) {
                    LST_ThingsA.Elements(tempData);
                }
            }
        } catch (JSONException e) {
            // if an exception occurs, code for it in here
            messages.ShowMessageDialog("Error 3.353; JSON Exception (check password) ", "Information", "OK");
        }
        else {
            messages.ShowMessageDialog("Error 3.356; Problem connecting with server", "Information", "OK");
        }
    }
}