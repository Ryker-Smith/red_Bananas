package net.fachtnaroe.red_bananas;

import android.content.Intent;

import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.Notifier;
import com.google.appinventor.components.runtime.TextBox;
import com.google.appinventor.components.runtime.VerticalScrollArrangement;
import com.google.appinventor.components.runtime.Web;

public class NewSaleItem extends Form implements HandlesEventDispatching {
    private Button butt1;//Delete Later
    private VerticalScrollArrangement VArr;
    private HorizontalArrangement HArr_Price;
    private Button saveThisBtn;
    private TextBox nameTxtBox, descriptionTxtBox, priceTxtBox;
    private Label nameLabel, descriptionLabel, priceLabel, euroSymbolLablel, titleLabel;
    private Web webSaveNewItem;
    private Notifier savedNotifier;
    private String baseURL = "https://fachtnaroe.net/bananas?",
            pID = MainActivity.getPID(),
            sessionID = MainActivity.getSessionID();

    protected void $define() {
        VArr = new VerticalScrollArrangement(this);

        titleLabel = new Label(VArr);
        titleLabel.WidthPercent(100);
        titleLabel.HeightPercent(20);
        titleLabel.Text("Food Delivery Service");
        titleLabel.FontSize(36);
        titleLabel.TextAlignment(Component.ALIGNMENT_CENTER);

        nameLabel = new Label(VArr);
        nameLabel.WidthPercent(100);
        nameLabel.Text("Name");

        nameTxtBox = new TextBox(VArr);
        nameTxtBox.WidthPercent(100);

        descriptionLabel = new Label(VArr);
        descriptionLabel.WidthPercent(100);
        descriptionLabel.Text("Description");

        descriptionTxtBox = new TextBox(VArr);
        descriptionTxtBox.WidthPercent(100);

        priceLabel = new Label(VArr);
        priceLabel.WidthPercent(100);
        priceLabel.Text("Price");

        HArr_Price = new HorizontalArrangement(VArr);
        HArr_Price.WidthPercent(100);

        euroSymbolLablel = new Label(HArr_Price);
        euroSymbolLablel.WidthPercent(5);
        euroSymbolLablel.Text("€");

        priceTxtBox = new TextBox(HArr_Price);
        priceTxtBox.WidthPercent(100);

        saveThisBtn = new Button(VArr);
        saveThisBtn.WidthPercent(100);
        saveThisBtn.Text("Save This");

        webSaveNewItem = new Web(this);

        butt1 = new Button(VArr);
        butt1.Text("Go Back");
        butt1.WidthPercent(100);
        butt1.HeightPercent(10);

        savedNotifier = new Notifier(this);

        EventDispatcher.registerEventForDelegation(this, formName, "Click");
        EventDispatcher.registerEventForDelegation(this, formName, "GotText");
    }

    public boolean dispatchEvent(Component component, String componentName, String eventName, Object[] params) {
        if (eventName.equals("Click")) {
            if (component.equals((butt1))) {
                SalesGo();
                return true;
            }
            if (component.equals((saveThisBtn))) {
                saveThis();
                return true;
            }
            return true;
        }
        if (eventName.equals("GotText")) {
            titleLabel.Text((String) params[3]);
            nameLabel.Text("Saved?");
            savedNotifier.ShowAlert("Item has been saved");
            SalesGo();
            return true;
        } else {
            return false;
        }
    }

    public void saveThis() {
        webSaveNewItem.Url(baseURL + "sessionID=" + sessionID + "&method=POST&entity=thing&tName=" + nameTxtBox.Text() + "&tDescription=" + descriptionTxtBox.Text() + "&tPrice=" + priceTxtBox.Text() + "&tSoldBy=" + pID);
        webSaveNewItem.Get();
    }

    public void SalesGo() {
        Intent i = new Intent(getApplicationContext(), Sales.class);
        startActivity(i);
    }
}