package com.example.pau.talaya;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.example.pau.talaya.LoginActivity.usuariActiu;
import static com.example.pau.talaya.R.id.idenvia;
import static com.example.pau.talaya.R.id.idrep;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link missatges.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link missatges#newInstance} factory method to
 * create an instance of this fragment.
 */

public class missatges extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;

    public SimpleCursorAdapter adapter;
    private OnFragmentInteractionListener mListener;
    private String[] from;
    private int[] to;
    public Cursor cursor;

    public missatges() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment missatges.
     */
    // TODO: Rename and change types and number of parameters
    public static missatges newInstance(String param1, String param2) {
        missatges fragment = new missatges();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final DataBaseManager a = new DataBaseManager(getContext());
        Context context = getActivity();



        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //ENVIO EL MEU ID, el ID del que ho rep i el seu nom.

        //a.crearconversa(usuariActiu.getIdUsuari(),141,"Gindalf");

        view = inflater.inflate(R.layout.fragment_missatges, container, false);

        TextView envia = (TextView)view.findViewById(idenvia);
        TextView rep = (TextView)view.findViewById(idrep);



        final ListView llista = (ListView) view.findViewById(R.id.listConverses);
        final DinsConversa dins = new DinsConversa();
        DataBaseManager a = new DataBaseManager(getActivity());
        from = new String[]{OpenHelper.C_Perfil2, OpenHelper.C_Perfil, OpenHelper.C_IDConversa};
        to = new int[]{idenvia, idrep, R.id.nomcasa};

        // final Button boto =(Button)view.findViewById(R.id.button4);
        getconverses(usuariActiu.getIdUsuari());
        cursor = a.getConverses();
        mostrarconverses(cursor, from, to);

        llista.setAdapter(adapter);
        llista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView envia = (TextView) view.findViewById(idenvia);
                TextView rep = (TextView) view.findViewById(idrep);
                String senvia=envia.getText().toString();
                Bundle bundle = new Bundle();

                dins.idsconversa(Integer.parseInt(senvia));

                Intent intent = new Intent(view.getContext(), DinsConversa.class);
                intent.putExtras(bundle);





                startActivity(intent);
            }
        });

        // Inflate the layout for this fragmen


        return view;
    }
    private void getconverses(int id){

        final DataBaseManager a = new DataBaseManager(getActivity());
        final AsyncHttpClient converses;

        String url = "http://talaiaapi.azurewebsites.net/api/conversa/"+id;

        converses = new AsyncHttpClient();

        converses.setMaxRetriesAndTimeout(0,10000);

        converses.get(getActivity(), url, new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                JSONArray jsonArray = null;
                JSONObject conv = null;
                String str = new String(responseBody);
                String id1, id2, nomcasa;
                try {

                    jsonArray = new JSONArray(str);

                    for (int i = 0; i < jsonArray.length();i++){

                        conv = jsonArray.getJSONObject(i);
                        id1 = (String) conv.getString("FKUsuari1");
                        id2 = (String) conv.getString("FKUsuari2");
                        nomcasa = (String) conv.getString("NomConversa");

                        if(a.getCount(id2,id1)==0) {
                            a.crearconversa(id2, id1, nomcasa);
                            //getconverses(usuariActiu.getIdUsuari());

                        }
                        cursor = a.getConverses();
                        mostrarconverses(cursor, from, to);


                    }

                }catch (JSONException e) {

                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Snackbar.make(view, "Error de conexió", Snackbar.LENGTH_LONG)
                        .show();

            }
        });

    }

    public void mostrarconverses(Cursor cursor, String[] from, int[] to) {

        ListView llista = (ListView)view.findViewById(R.id.listConverses);

        adapter = new AdapterConverses(getContext(), R.layout.rowconversa, cursor, from, to, 1);
        llista.setAdapter(adapter);

        llista.setSelection(llista.getAdapter().getCount()-1);


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