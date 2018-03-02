package com.herasiddiqui.personalinformation;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class MajorFragment extends ListFragment implements AdapterView.OnItemClickListener{

    Button cancelMajorSelection;
    Button backToDegreeSelection;
    String[] majorList;
    String majorHeading;
    TextView heading;
    public OnMajorSelectListener majorListener;
    public OnCancelMajorClickListener cancelMajorListener;
    public OnBackToDegreeClickListener backToDegreeListener;
    public void setMajors(String[] majors){
        majorList = majors;
    }
    public void setMajorHeading(String headingPassed){
        majorHeading = headingPassed;
    }
    public MajorFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View majorSelection = inflater.inflate(R.layout.fragment_major, container, false);
        heading = majorSelection.findViewById(R.id.headingInMajor);
        heading.setText(majorHeading);
        cancelMajorSelection = majorSelection.findViewById(R.id.cancelMajor);
        backToDegreeSelection = majorSelection.findViewById(R.id.backToDegree);
        cancelMajorSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelMajorListener = (OnCancelMajorClickListener) getActivity();
                if (cancelMajorListener!= null)
                {
                    cancelMajorListener.onCancelMajorClick();
                }
            }
        });
        backToDegreeSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToDegreeListener = (OnBackToDegreeClickListener) getActivity();
                if(backToDegreeListener != null){
                    backToDegreeListener.onBackToDegreeClick();
                }
            }
        });
        return majorSelection;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,majorList);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String majorName = parent.getItemAtPosition(position).toString();
        majorListener = (OnMajorSelectListener) getActivity();
        if (majorListener!= null)
        {
            majorListener.onMajorSelect(position,majorName);
        }
    }

    public interface OnMajorSelectListener{
         void onMajorSelect(int position,String majorName);
    }

    public interface OnCancelMajorClickListener{
        void onCancelMajorClick();
    }

    public interface OnBackToDegreeClickListener{
        void onBackToDegreeClick();
    }
}
