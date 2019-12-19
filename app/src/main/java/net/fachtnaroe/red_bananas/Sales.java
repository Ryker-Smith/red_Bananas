package net.fachtnaroe.red_bananas;

import android.graphics.Color;
import android.util.Log;

import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
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
    private Button btnDelete, btnAddNew, btnOrderIsCompleted;
    private VerticalArrangement VArr;
    private HorizontalArrangement HArr_User_pID, HArr2Btn;
    private Label titleFDS, Label_Username, Username_L, Label_pID, pId_L, things4Sale_Label, thingsSold_Label;
    private ListView thingsWeSell_ListView, OrdersPlaced_ListView;
    private String baseURL = "https://fachtnaroe.net/bananas?",
            sID = "sessionID=",
            getThingsForSaleURL = "&entity=thing&method=GET",
            getThingsSoldURL = "&entity=orders&method=GET",
            webThingDeleteURL =  "&entity=thing&method=DELETE&tID=",
            webOrderIsCompleteURL = "&entity=orders&method=DELETE&oID=";
    private String[] startValue;
    private Web webGetThings4Sale, webGetThingsSold, webThingDelete, webOrderIsComplete;
    private Notifier GotTextNotifier;


    protected void $define() {
        this.BackgroundColor(Component.COLOR_ORANGE);
        //[0]=extra quotation mark(not to be used) [1]=pId [2]=Username [3]=SessionID [4]=extra quotation mark(not to be used)
        startValue = this.startupValue.split("<SPLIT>");
        GotTextNotifier = new Notifier(this);
        GotTextNotifier.BackgroundColor(Component.COLOR_RED);
        GotTextNotifier.TextColor(Component.COLOR_WHITE);

        VArr = new VerticalArrangement(this);
        VArr.Height(LENGTH_FILL_PARENT);
        VArr.Width(LENGTH_FILL_PARENT);
        VArr.BackgroundColor(Component.COLOR_ORANGE);
        VArr.Image("FDS_PossibleLogo_04.png");

        titleFDS = new Label(VArr);
        titleFDS.Text("Food Delivery Service");
        titleFDS.FontSize(20);
        titleFDS.FontBold(true);
        titleFDS.Width(LENGTH_FILL_PARENT);
        titleFDS.TextAlignment(Component.ALIGNMENT_CENTER);
        titleFDS.TextColor(COLOR_WHITE);
        titleFDS.BackgroundColor(Color.parseColor("#005200"));

        HArr_User_pID = new HorizontalArrangement(VArr);
        HArr_User_pID.Width(LENGTH_FILL_PARENT);
        HArr_User_pID.BackgroundColor(Component.COLOR_NONE);

        Label_Username = new Label(HArr_User_pID);
        Label_Username.TextAlignment(Component.ALIGNMENT_NORMAL);
        Label_Username.FontSize(14);
        Label_Username.Text("Username ");

        Username_L = new Label(HArr_User_pID);
        Username_L.Width(LENGTH_FILL_PARENT);
        Username_L.TextAlignment(Component.ALIGNMENT_NORMAL);
        Username_L.FontSize(14);
        Username_L.TextColor(COLOR_BLUE);
        Username_L.Text(startValue[2]);

        Label_pID = new Label(HArr_User_pID);
        Label_pID.TextAlignment(Component.ALIGNMENT_OPPOSITE);
        Label_pID.FontSize(14);
        Label_pID.Text("pID ");

        pId_L = new Label(HArr_User_pID);
        pId_L.TextAlignment(Component.ALIGNMENT_NORMAL);
        pId_L.FontSize(14);
        pId_L.TextColor(COLOR_BLUE);
        pId_L.Text(startValue[1]);

        things4Sale_Label = new Label(VArr);
        things4Sale_Label.FontSize(12);
        things4Sale_Label.TextColor(COLOR_WHITE);
        things4Sale_Label.Width(LENGTH_FILL_PARENT);
        things4Sale_Label.Text("My things for sale");
        things4Sale_Label.BackgroundColor(Color.parseColor("#005200"));

        thingsWeSell_ListView = new ListView(VArr);
        thingsWeSell_ListView.Width(LENGTH_FILL_PARENT);
        thingsWeSell_ListView.Height(LENGTH_FILL_PARENT);
        thingsWeSell_ListView.TextSize(14);
        thingsWeSell_ListView.TextColor(Component.COLOR_BLACK);
        thingsWeSell_ListView.SelectionColor(Color.parseColor("#009F00"));
        thingsWeSell_ListView.BackgroundColor(Component.COLOR_NONE);

        HArr2Btn = new HorizontalArrangement(VArr);
        HArr2Btn.Width(LENGTH_FILL_PARENT);

        btnDelete = new Button(HArr2Btn);
        btnDelete.Text("Delete");
        btnDelete.Width(LENGTH_FILL_PARENT);
        btnDelete.FontSize(14);

        btnAddNew = new Button(HArr2Btn);
        btnAddNew.Text("Add New");
        btnAddNew.Width(LENGTH_FILL_PARENT);
        btnAddNew.FontSize(14);

        thingsSold_Label = new Label(VArr);
        thingsSold_Label.Text("My things Sold");
        thingsSold_Label.Width(LENGTH_FILL_PARENT);
        thingsSold_Label.FontSize(12);
        thingsSold_Label.TextColor(COLOR_WHITE);
        thingsSold_Label.BackgroundColor(Color.parseColor("#005200"));

        OrdersPlaced_ListView = new ListView(VArr);
        OrdersPlaced_ListView.Width(LENGTH_FILL_PARENT);
        OrdersPlaced_ListView.HeightPercent(30);
        OrdersPlaced_ListView.TextSize(14);
        OrdersPlaced_ListView.TextColor(Component.COLOR_BLACK);
        OrdersPlaced_ListView.SelectionColor(Color.parseColor("#009F00"));
        OrdersPlaced_ListView.BackgroundColor(Component.COLOR_NONE);

        btnOrderIsCompleted = new Button(VArr);
        btnOrderIsCompleted.Width(LENGTH_FILL_PARENT);
        btnOrderIsCompleted.Text("Order is completed");
        btnOrderIsCompleted.FontSize(14);

        webGetThings4Sale = new Web(this);
        webGetThings4Sale.Url(baseURL+sID+startValue[3]+getThingsForSaleURL);
        webGetThings4Sale.Get();

        webGetThingsSold = new Web(this);
        webGetThingsSold.Url(baseURL+sID+startValue[3]+getThingsSoldURL);
        webGetThingsSold.Get();

        webThingDelete = new Web(this);

        webOrderIsComplete = new Web(this);

        EventDispatcher.registerEventForDelegation(this, formName, "Click");
        EventDispatcher.registerEventForDelegation(this, formName, "GotText");
    }

    public boolean dispatchEvent(Component component, String componentName, String eventName, Object[] params) {
        if (eventName.equals("Click")) {
            if (component.equals(btnAddNew)) {
                //go to NewSaleItem Screen with StartValue
                switchFormWithStartValue("NewSaleItem",this.startupValue);
                return true;
            }
            else if (component.equals(btnDelete)) {
                //delete from top ListView
                deleteItemFromThingsForSale(thingsWeSell_ListView.Selection());
                return true;
            }
            else if (component.equals(btnOrderIsCompleted)) {
                //delete from bottom listView
                removeCompletedOrderFromThingsSold(OrdersPlaced_ListView.Selection());
                return true;
            }
        }
        else if (component.equals(webGetThings4Sale) && eventName.equals("GotText")) {
            //calling the procedure For the ListView containing the Items that the seller has for sale
            jsonSortAndListViewForSellerScreen(params[1].toString(), (String) params[3],"thing", "tSoldBy");
            return true;
        }
        else if (component.equals(webGetThingsSold) && eventName.equals("GotText")) {
            //calling the procedure For the ListView containing the Orders that have been placed to the seller
            jsonSortAndListViewForSellerScreen(params[1].toString(), (String) params[3],"orders", "sellerID");
            return true;
        }
        else if (component.equals(webThingDelete) && eventName.equals("GotText")) {
            //Update Things for sale ListView
            webGetThings4Sale.Get();
            return true;
        }
        else if (component.equals(webOrderIsComplete) && eventName.equals("GotText")) {
            //Update Things sold ListView
            webGetThingsSold.Get();
            return true;
        }
        return false;
    }
    public void removeCompletedOrderFromThingsSold(String selection) {
        if ((OrdersPlaced_ListView.Selection().isEmpty())) {
            GotTextNotifier.ShowAlert("No Order Selected");
        }
        else {
            int endPoint = selection.indexOf(']');
            String oIDForURL = selection.substring(1, endPoint);
            webOrderIsComplete.Url(baseURL+sID+startValue[3]+webOrderIsCompleteURL + oIDForURL);
            webOrderIsComplete.Get();
            GotTextNotifier.ShowAlert("Order " + oIDForURL + " removed");
        }
    }
    public void deleteItemFromThingsForSale(String selection) {
        if ((thingsWeSell_ListView.Selection().isEmpty())) {
            GotTextNotifier.ShowAlert("No Item Selected");
        }
        else {
            int endPoint = selection.indexOf(']');
            String tIDForURL = selection.substring(1, endPoint);
            webThingDelete.Url(baseURL+sID+startValue[3]+ webThingDeleteURL + tIDForURL);
            webThingDelete.Get();
            GotTextNotifier.ShowAlert("Item " + tIDForURL + " removed");
        }
    }
    //this procedure can be called for both listViews, (uses the kawa-1.7 library)
    public void jsonSortAndListViewForSellerScreen(String status, String textOfResponse, String tableName, String fieldName) {
        List<String> ListViewItemArray;
        if (status.equals("200")) try {
            ListViewItemArray = new ArrayList<String>();
            // See:  https://stackoverflow.com/questions/5015844/parsing-json-object-in-java
            JSONObject parser = new JSONObject(textOfResponse);
            if (!parser.getString(tableName).equals("")) {
                JSONArray jsonIsMySon = parser.getJSONArray(tableName);
                for (int i = 0; i < jsonIsMySon.length(); i++) {
                    if (Integer.valueOf(jsonIsMySon.getJSONObject(i).getString(fieldName)).equals( Integer.valueOf(startValue[1]))) {
                        String oneEntryInTheListView = "";
                        //add data from table to the sting above by getting the field name you want from the brief ( example where field name is "sellerID": oneEntryInTheListView = jsonIsMySon.getJSONObject(i).getString("sellerID"); )
                        //formats entries the ListView containing the orders
                        if (tableName.equals("orders") && fieldName.equals("sellerID")){
                                oneEntryInTheListView = "[" + jsonIsMySon.getJSONObject(i).getString("oID")
                                    + "] buyer: " + jsonIsMySon.getJSONObject(i).getString("buyerID")
                                    + " (slotNum: " + jsonIsMySon.getJSONObject(i).getString("slotNum")
                                    + ") [" + jsonIsMySon.getJSONObject(i).getString("tID") + "]";
                        }
                        //formats entries the ListView containing the items sold by seller
                        if (tableName.equals("thing") && fieldName.equals("tSoldBy")){
                            oneEntryInTheListView = "[" + jsonIsMySon.getJSONObject(i).getString("tID")
                                    + "] " + jsonIsMySon.getJSONObject(i).getString("tName")
                                    + " (" + jsonIsMySon.getJSONObject(i).getString("tDescription")
                                    + ") €" + jsonIsMySon.getJSONObject(i).getString("tPrice");
                        }
                        ListViewItemArray.add(oneEntryInTheListView);
                    }
                }
                YailList tempData = YailList.makeList(ListViewItemArray);
                if (tableName.equals("orders") && fieldName.equals("sellerID")) {
                    OrdersPlaced_ListView.Elements(tempData);
                }
                if (tableName.equals("thing") && fieldName.equals("tSoldBy")) {
                    thingsWeSell_ListView.Elements(tempData);
                }
            }
        } catch (JSONException e) {
            // if an exception occurs, code for it in here
            GotTextNotifier.ShowMessageDialog("Error 3.353; JSON Exception (check password) ", "Information", "OK");
        }
        else {
            GotTextNotifier.ShowMessageDialog("Error 3.356; Problem connecting with server", "Information", "OK");
        }
    }
}