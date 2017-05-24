package com.example.pau.talaya;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import static com.example.pau.talaya.home.CasaList;
import static com.example.pau.talaya.home.FavoritsList;
import static com.example.pau.talaya.home.teFavorits;
import static com.example.pau.talaya.home.teReserves;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link favorits.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link favorits#newInstance} factory method to
 * create an instance of this fragment.
 */
public class favorits extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View view;
    private String Lnom;
    private String Lid;

    public favorits() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment favorits.
     */
    // TODO: Rename and change types and number of parameters
    public static favorits newInstance(String param1, String param2) {
        favorits fragment = new favorits();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_favorits, container, false);

        ListView llista = (ListView)view.findViewById(R.id.listFav);

        LinearLayout noData = (LinearLayout)view.findViewById(R.id.noData);

        if (teFavorits){

            AdapterFav adapter = new AdapterFav(getContext(),R.layout.casa_row,FavoritsList);

            llista.setAdapter(adapter);

            llista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Bundle b = new Bundle();

                    TextView txtId = (TextView)view.findViewById(R.id.textID);

                    Lid = txtId.getText().toString();

                    b.putString("id",Lid);

                    Intent intencio = new Intent(getActivity(),DescCasa.class);

                    intencio.putExtras(b);

                    startActivity(intencio);
                }
            });
        }else{
            noData.setVisibility(View.VISIBLE);
        }

        // Inflate the layout for this fragment
        return view
                ;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
