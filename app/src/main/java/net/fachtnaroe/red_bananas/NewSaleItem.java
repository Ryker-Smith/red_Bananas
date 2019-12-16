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
import com.google.appinventor.components.runtime.VerticalArrangement;
import com.google.appinventor.components.runtime.Web;

public class NewSaleItem extends Form implements HandlesEventDispatching {
    private Button butt1;//Delete Later
    private VerticalArrangement VArr, VArr_EmptySpace;
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
        VArr = new VerticalArrangement(this);
        VArr.Height(LENGTH_FILL_PARENT);
        VArr.Width(LENGTH_FILL_PARENT);
        VArr.BackgroundColor(Component.COLOR_PINK);

        titleLabel = new Label(VArr);
        titleLabel.Width(LENGTH_FILL_PARENT);
        titleLabel.HeightPercent(20);
        titleLabel.Text("Food Delivery Service");
        titleLabel.FontSize(36);
        titleLabel.FontBold(true);
        titleLabel.TextAlignment(Component.ALIGNMENT_CENTER);

        nameLabel = new Label(VArr);
        nameLabel.Width(LENGTH_FILL_PARENT);
        nameLabel.Text("Name");
        nameLabel.FontSize(14);

        nameTxtBox = new TextBox(VArr);
        nameTxtBox.Width(LENGTH_FILL_PARENT);
        nameTxtBox.FontSize(14);

        descriptionLabel = new Label(VArr);
        descriptionLabel.Width(LENGTH_FILL_PARENT);
        descriptionLabel.Text("Description");
        descriptionLabel.FontSize(14);

        descriptionTxtBox = new TextBox(VArr);
        descriptionTxtBox.Width(LENGTH_FILL_PARENT);
        descriptionTxtBox.FontSize(14);

        priceLabel = new Label(VArr);
        priceLabel.Width(LENGTH_FILL_PARENT);
        priceLabel.Text("Price");
        priceLabel.FontSize(14);

        HArr_Price = new HorizontalArrangement(VArr);
        HArr_Price.Width(LENGTH_FILL_PARENT);

        euroSymbolLablel = new Label(HArr_Price);
        euroSymbolLablel.WidthPercent(5);
        euroSymbolLablel.Text("€");
        euroSymbolLablel.FontSize(14);

        priceTxtBox = new TextBox(HArr_Price);
        priceTxtBox.Width(LENGTH_FILL_PARENT);
        priceTxtBox.FontSize(14);

        VArr_EmptySpace = new VerticalArrangement(VArr);
        VArr_EmptySpace.Height(LENGTH_FILL_PARENT);

        saveThisBtn = new Button(VArr);
        saveThisBtn.Width(LENGTH_FILL_PARENT);
        saveThisBtn.Text("Save This");
        saveThisBtn.FontSize(14);

        webSaveNewItem = new Web(this);

        butt1 = new Button(VArr);
        butt1.Text("Go Back");
        butt1.Width(LENGTH_FILL_PARENT);
        butt1.HeightPercent(10);
        butt1.FontSize(14);

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