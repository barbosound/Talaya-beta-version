package com.example.pau.talaya;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.pau.talaya.home.teReserves;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link pendents.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link pendents#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pendents extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<String> idReserva = new ArrayList<>();
    private ArrayList<String> nom = new ArrayList<>();
    private ArrayList<String> preuReserva = new ArrayList<>();
    private ArrayList<String> diesReserva = new ArrayList<>();
    private ArrayList<String> DataEntrada = new ArrayList<>();
    private ArrayList<String> DataSortida = new ArrayList<>();
    private ArrayList<String> FKUsuari = new ArrayList<>();
    private ArrayList<String> FKCasa = new ArrayList<>();
    private ArrayList<String> Estat = new ArrayList<>();


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public pendents() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment pendents.
     */
    // TODO: Rename and change types and number of parameters
    public static pendents newInstance(String param1, String param2) {
        pendents fragment = new pendents();
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

            idReserva = getArguments().getStringArrayList("id");
            nom = getArguments().getStringArrayList("nom");
            DataEntrada = getArguments().getStringArrayList("DE");
            DataSortida = getArguments().getStringArrayList("DS");
            FKUsuari = getArguments().getStringArrayList("FKUsuari");
            FKCasa = getArguments().getStringArrayList("FKCasa");
            Estat = getArguments().getStringArrayList("Estat");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pendents, container, false);

        ListView llista = (ListView)view.findViewById(R.id.listReserva);

        LinearLayout noData = (LinearLayout)view.findViewById(R.id.noData);

        if (teReserves){

            AdapterReserva adapter = new AdapterReserva(getContext(),R.layout.row_reserva,idReserva,nom,preuReserva,diesReserva,DataEntrada,DataSortida,FKCasa,Estat,FKUsuari);

            llista.setAdapter(adapter);

        }else {

            noData.setVisibility(View.VISIBLE);

        }


        // Inflate the layout for this fragment
        return view;
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
