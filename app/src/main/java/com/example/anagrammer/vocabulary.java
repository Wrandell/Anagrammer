package com.example.anagrammer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.HashMap;

public class vocabulary extends AppCompatActivity {

    private ListView myListView;
    ImageView backtBtn;
    ArrayAdapter<String> myAdapter;
    EditText inputSearch;
    ArrayList<HashMap<String, String>> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        String products[] = {"Integer - An integer is a number with no decimal or fractional part, from the set of negative and positive numbers, including zero.",
                "Variable - a variable is a value that can change, depending on conditions or on information passed to the program.",
                "Array - An array is a data structure consisting of a collection of elements (values or variables)",
                "Semi-Colon - A semicolon is used in programming to denote the end of a complete statement.",
                "Data Type - A data type is a classification that specifies which type of value a variable has and what type of mathematical, relational or logical operations can be applied to it without causing an error.",

                "String - A string is traditionally a sequence of characters, either as a literal constant or as some kind of variable.",
                "For - A For Loop is used to repeat a specific block of code a known number of times.",
                "Loop - a loop is a sequence of instruction s that is continually repeated until a certain condition is reached.",
                "If  - If statements are logical blocks used within programming.",
                "Iteration - It is a repetitive action or command typically created with programming loops.",

                "Colon - A colon is used to represent an indented block. It is also used to fetch data and index ranges or arrays",
                "Number - A number is an arithmetic value used for representing the quantity and used in making calculations.",
                "Boolean - Boolean is a primitive data type that takes either “true” or “false” values.",
                "Char - It is data type stores character data in a fixed-length field.",
                "Return - return is a statement that instructs a program to leave the subroutine and go back to the return address.",

                "Break - Break statement ends the loop immediately when it is encountered.",
                "Switch - A switch statement is a type of selection control mechanism used to allow the value of a variable or expression to change the control flow of program execution via search and map.",
                "Else - Else statement is an alternative statement that is executed if the result of a previous test condition evaluates to false.",
                "Operator - It is a symbol that tells the compiler or interpreter to perform specific mathematical, relational or logical operation and produce final result.",
                "Package - a package is a module that can be added to any program to add additional options, features, or functionality.",

                "Pseudocode - It is a computer programming language that resembles plain English that cannot be compiled or executed, but explains a resolution to a problem.",
                "Bug - An error in a program that prevents the program from running as expected.",
                "Conditionals - Statements that only run under certain conditions.",
                "Debugging - It is finding and fixing problems in an algorithm or program.",
                "Input - A way to give information to a computer.",

                "Output - A way to get information out of a computer.",
                "Packets  - Small chunks of information that have been carefully formed from larger chunks of information.",
                "Parameter - An extra piece of information passed to a function to customize it for a specific need.",
                "Float - A floating-point number is one where the position of the decimal point can \"float\" rather than being in a fixed position within a number.",
                "While - A while loop or repeat loop is a loop statement in programming that performs a pre-defined task repeatedly until a condition is met."};


        backtBtn = (ImageView) findViewById(R.id.backBtn);
        myListView = (ListView) findViewById(R.id.editlist_view);
        inputSearch = (EditText) findViewById(R.id.itemSearch);
        myAdapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name,products);
        myListView.setAdapter(myAdapter);

        backtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), home.class);
                startActivity(intent);
            }
        });

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                vocabulary.this.myAdapter.getFilter().filter(cs);
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }
            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(vocabulary.this, home.class));
    }
}