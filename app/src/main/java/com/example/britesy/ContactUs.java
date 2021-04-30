package com.example.britesy;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class ContactUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);


        Element adsElement = new Element();
        adsElement.setTitle("Advertise with us");
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .enableDarkMode(false)
                .setDescription("Britesy company is over two decades old art gallery based in coimbatore,India. The basic aim of this online art gallery is to promote ,advertise and sell the exclusive range of indian art all over the world")
                .addItem(new Element().setTitle("Version 6.2"))
                .addItem(adsElement)
                .addGroup("Connect with us")
                .addEmail("harinisanthanam0@gmail.com")
                .addWebsite("")
                .addFacebook("harinisanthanam")
                .addTwitter("medyo80")
                .addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
                .addPlayStore("com.example.britesy")
                .addInstagram("harini_4444")
                .addItem(getCopyRightsElement())
                .create();

        setContentView(aboutPage);
    }


    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        final String copyrights = String.format(getString(R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIconDrawable(R.drawable.about_icon_copy_right);
        copyRightsElement.setAutoApplyIconTint(true);
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ContactUs.this, copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }
}