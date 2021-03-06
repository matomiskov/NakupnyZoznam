package matomiskov.nakupnyzoznam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.design.internal.NavigationSubMenu;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        //NA TESTY
        Zoznam zoznam1 = new Zoznam("Nakupovanie");
        Zoznam zoznam2 = new Zoznam("Sporenie");

        long tag1_id = db.createZoznam(zoznam1);
        long tag2_id = db.createZoznam(zoznam2);
//jeden zoznam
        Zoznam zoznamOne = db.getZoznam(tag1_id);
        System.out.println("ID zoznamu " + zoznamOne.getId());
        // System.out.println("::: POZOR :::" + db.getZoznam(tag1_id).getName());
//vsetky zoznamy
        List<Zoznam> allToDos = db.getAllZoznamy();
        for (Zoznam zoznam : allToDos) {
            Log.d("ToDo", zoznam.getName());
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
        menu.add(R.id.group_item, Menu.NONE, Menu.FIRST, "Môj nákup").setIcon(R.drawable.ic_list);
        listOfList.add("Môj nákup"); //PRIDA DO ZOZNAMU NAKUPNYZOZNAM
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    List<String> listOfList = new ArrayList<>(); //ZOZNAM NAKUPNYCHZOZNAOV
    int pocitadlo = 0; //DOCASNE POCITADLO

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        String nazov = "Nove " + pocitadlo;

        int itemId = item.getItemId();

        if (itemId == R.id.create_list) {
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu menu = navigationView.getMenu();
            menu.add(R.id.group_item, pocitadlo, Menu.FIRST, nazov).setIcon(R.drawable.ic_list);
            listOfList.add(nazov);
            pocitadlo++;
            System.out.println(item.getTitle());
        } else if (itemId != R.id.create_list) {
            okno(item);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void okno(MenuItem menuItem) {
        final MenuItem item = menuItem;
        System.out.println(item.getTitle());
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Chcete odstrániť túto položku?\n" + item.getTitle());
        dialog.setPositiveButton("Áno", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                Menu menu = navigationView.getMenu();
                menu.removeItem(item.getItemId());
                listOfList.remove(item);
            }
        }).setNegativeButton("Nie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        final AlertDialog alert = dialog.create();
        alert.show();

    }

}
