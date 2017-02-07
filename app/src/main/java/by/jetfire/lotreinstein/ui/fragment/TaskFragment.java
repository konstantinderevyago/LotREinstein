package by.jetfire.lotreinstein.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import by.jetfire.lotreinstein.R;
import by.jetfire.lotreinstein.entity.TaskItem;
import by.jetfire.lotreinstein.utils.Utils;

/**
 * Created by Konstantin on 06.02.2017.
 */

public class TaskFragment extends Fragment {

    private List<TaskItem> taskItems;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        ButterKnife.bind(this, view);

        taskItems = Utils.getTaskItems(getContext());
        buildTable((ViewGroup) view);

        return view;
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
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.column_item, view, false);
        buildTextView(linearLayout, R.string.number);
        buildTextView(linearLayout, R.string.name);
        buildTextView(linearLayout, R.string.color);
        buildTextView(linearLayout, R.string.alcohol);
        buildTextView(linearLayout, R.string.smoke);
        buildTextView(linearLayout, R.string.thing);
        view.addView(linearLayout);
    }

    private void buildColumn(ViewGroup view, int number) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.column_item, view, false);
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
        TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.text_item, view, false);
        textView.setText(text);
        view.addView(textView);
    }

    private void buildSpinner(ViewGroup view, int arrayRes) {
        Spinner spinner = (Spinner) LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, view, false);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(arrayRes));
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        view.addView(spinner);
    }
}
