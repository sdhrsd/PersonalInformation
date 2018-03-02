package com.herasiddiqui.personalinformation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class DegreeFragment extends ListFragment implements AdapterView.OnItemClickListener{

    Button cancel;
    String[] degreeList;
    public OnDegreeSelectListener getListener;
    public OnCancelClickListener cancelListener;
    public DegreeFragment() {}
    public void setDegrees(String[] degree){
        degreeList = degree;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View degreeSelection =  inflater.inflate(R.layout.fragment_degree, container, false);
        cancel = degreeSelection.findViewById(R.id.cancelDegree);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelListener = (OnCancelClickListener) getActivity();
                if (cancelListener!= null)
                {
                    cancelListener.onCancelClick();
                }
            }
        });
        return degreeSelection;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,degreeList);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        Log.i("hs", "position of click " + position );
        Log.i("hs", "id of click " + id );
        String degreeName = parent.getItemAtPosition(position).toString();
        getListener= (OnDegreeSelectListener) getActivity();
        if (getListener!= null)
        {
            getListener.onDegreeSelect(position,degreeName);
        }
    }

    public interface OnDegreeSelectListener{
         void onDegreeSelect(int position,String degreeName);
    }

    public interface OnCancelClickListener{
        void onCancelClick();
    }

}
