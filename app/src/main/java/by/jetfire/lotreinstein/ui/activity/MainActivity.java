package by.jetfire.lotreinstein.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.jetfire.lotreinstein.R;
import by.jetfire.lotreinstein.entity.TaskItem;
import by.jetfire.lotreinstein.utils.Utils;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_container)
    protected LinearLayout container;

    private List<TaskItem> taskItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        taskItems = Utils.getTaskItems(this);
        buildTable(container);
    }

    private void buildTable(ViewGroup view) {
        if (taskItems != null && !taskItems.isEmpty()) {
            buildTitleColumn(view);
            for (TaskItem taskItem : taskItems) {
                buildColumn(view, taskItem.getNumber());
            }
        }
    }

    private void buildTitleColumn(ViewGroup view) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.column_item, view, false);
        buildTextView(linearLayout, R.string.number);
        buildTextView(linearLayout, R.string.name);
        buildTextView(linearLayout, R.string.color);
        buildTextView(linearLayout, R.string.alcohol);
        buildTextView(linearLayout, R.string.smoke);
        buildTextView(linearLayout, R.string.thing);
        view.addView(linearLayout);
    }

    private void buildColumn(ViewGroup view, int number) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.column_item, view, false);
        buildTextView(linearLayout, String.valueOf(number));
        buildSpinner(linearLayout, R.array.names);
        buildSpinner(linearLayout, R.array.colors);
        buildSpinner(linearLayout, R.array.alcohols);
        buildSpinner(linearLayout, R.array.smokes);
        buildSpinner(linearLayout, R.array.things);
        view.addView(linearLayout);
    }

    private void buildTextView(ViewGroup view, int textRes) {
        buildTextView(view, getString(textRes));
    }

    private void buildTextView(ViewGroup view, String text) {
        TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.text_item, view, false);
        textView.setText(text);
        view.addView(textView);
    }

    private void buildSpinner(ViewGroup view, int arrayRes) {
        Spinner spinner = (Spinner) LayoutInflater.from(this).inflate(R.layout.spinner_item, view, false);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(arrayRes));
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        view.addView(spinner);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.undo:
                undo();
                return true;
            case R.id.redo:
                redo();
                return true;
            case R.id.conditions:
                showConditionsDialog();
                return true;
            case R.id.restart:
                showRestartDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void undo() {

    }

    private void redo() {

    }

    private void showConditionsDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.conditions)
                .setMessage(getConditions())
                .show();
    }

    private String getConditions() {
        return getString(R.string.condition_1) + "\n" +
                getString(R.string.condition_2) + "\n" +
                getString(R.string.condition_3) + "\n" +
                getString(R.string.condition_4) + "\n" +
                getString(R.string.condition_5) + "\n" +
                getString(R.string.condition_6) + "\n" +
                getString(R.string.condition_7) + "\n" +
                getString(R.string.condition_8) + "\n" +
                getString(R.string.condition_9) + "\n" +
                getString(R.string.condition_10) + "\n" +
                getString(R.string.condition_11) + "\n" +
                getString(R.string.condition_12) + "\n" +
                getString(R.string.condition_13) + "\n" +
                getString(R.string.condition_14) + "\n" +
                getString(R.string.condition_15);
    }

    private void showRestartDialog() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.restart_message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}
